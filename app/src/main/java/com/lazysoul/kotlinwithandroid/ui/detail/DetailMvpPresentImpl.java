package com.lazysoul.kotlinwithandroid.ui.detail;


import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.common.RxPresenter;
import com.lazysoul.kotlinwithandroid.datas.Todo;
import com.lazysoul.kotlinwithandroid.singletons.TodoManager;

import android.content.Intent;

import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import io.realm.Realm;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

class DetailMvpPresentImpl<MvpView extends BaseMvpView> extends RxPresenter
        implements DetailMvpPresenter<MvpView> {

    private DetailMvpView view;

    private Realm realm = Realm.getDefaultInstance();

    private Todo beforeTodo;

    private PublishSubject<Boolean> textChangeSubject = PublishSubject.create();

    public DetailMvpPresentImpl() {
        add(textChangeSubject
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isChanged) throws Exception {
                        beforeTodo.setFixed(isChanged);
                        view.onChagedSaveBt();
                    }
                }));
    }

    @Override
    public void attachView(MvpView view) {
        this.view = (DetailMvpView) view;
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
                beforeTodo = new Todo();
                beforeTodo.setId(-1);
                beforeTodo.setChecked(false);
                beforeTodo.setCreatedAt(Calendar.getInstance().getTime());
                beforeTodo.setBody("");
                view.onUpdated(beforeTodo, true);
                break;
            case TodoManager.REQUEST_TYPE_VIEW:
                int id = intent.getIntExtra(TodoManager.KEY_ID, -1);
                if (id != -1) {
                    beforeTodo = TodoManager.load(realm, id);
                    view.onUpdated(beforeTodo, false);
                }
                break;
        }
    }

    @Override
    public void onTextChanged(final String s) {
        if (!beforeTodo.isFixed() && isChanged(s)) {
            realm.beginTransaction();
            beforeTodo.setFixed(true);
            realm.commitTransaction();
        }

        textChangeSubject.onNext(isChanged(s));
    }

    private boolean isChanged(String s) {
        return !beforeTodo.getBody().equals(s);
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

    @Override
    public boolean isFixed() {
        return beforeTodo.isFixed();
    }
}
