package com.lazysoul.kotlinwithandroid.ui.detail;


import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.common.RxPresenter;
import com.lazysoul.kotlinwithandroid.datas.Todo;
import com.lazysoul.kotlinwithandroid.singletons.TodoManager;

import android.content.Intent;

import java.util.Calendar;

import io.realm.Realm;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

public class DetailMvpPresentImpl<MvpView extends BaseMvpView> extends RxPresenter
        implements DetailMvpPresenter<MvpView> {

    DetailMvpView view;

    Realm realm = Realm.getDefaultInstance();

    @Override
    public void attachView(MvpView view) {
        this.view = (DetailMvpView) view;
    }

    @Override
    public void stop(boolean isFinishing) {
        if (isFinishing) {
            dispose();
            realm.close();
        }
    }

    @Override
    public void destroy() {
        dispose();
        realm.close();
    }

    @Override
    public void loadTodo(Intent intent) {
        int requestType = intent
                .getIntExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_CREATE);

        switch (requestType) {
            case TodoManager.REQUEST_TYPE_CREATE:
                Todo todo = new Todo();
                todo.setId(-1);
                todo.setChecked(false);
                todo.setCreatedAt(Calendar.getInstance().getTime());
                todo.setBody("");
                view.onUpdated(todo, true);
                break;
            case TodoManager.REQUEST_TYPE_VIEW:
                int id = intent.getIntExtra(TodoManager.KEY_ID, -1);
                if (id != -1) {
                    view.onUpdated(TodoManager.load(realm, id), false);
                }
                break;
        }
    }

    @Override
    public void update(Todo todo) {
        // TODO: 2017. 7. 15.  ;
    }

    @Override
    public void delete(Todo todo) {
        view.onDeleted(todo);
    }

    @Override
    public void create(Todo todo) {
        view.onCreated(todo);
    }
}
