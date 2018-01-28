package com.lazysoul.kotlinwithandroid.ui.detail;


import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.common.RxPresenter;
import com.lazysoul.kotlinwithandroid.datas.Todo;
import com.lazysoul.kotlinwithandroid.singletons.TodoManager;

import android.content.Intent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

class DetailMvpPresentImpl<MvpView extends BaseMvpView> extends RxPresenter
    implements DetailMvpPresenter<MvpView> {

    private DetailMvpView view;

    private Todo beforeTodo;

    private PublishSubject<Boolean> textChangeSubject = PublishSubject.create();

    private int requestType = -1;

    DetailMvpPresentImpl() {
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
    }

    @Override
    public void loadTodo(Intent intent) {
        requestType = intent.getIntExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_CREATE);

        switch (requestType) {
            case TodoManager.REQUEST_TYPE_CREATE:
                beforeTodo = new Todo();
                beforeTodo.setId(-1);
                beforeTodo.setBody("");
                view.onUpdated(beforeTodo, true);
                break;
            case TodoManager.REQUEST_TYPE_VIEW:
                int id = intent.getIntExtra(TodoManager.KEY_ID, -1);
                if (id != -1) {
                    beforeTodo = TodoManager.getTodo(id);
                    view.onUpdated(beforeTodo, false);
                }
                break;
        }
    }

    @Override
    public void onTextChanged(final String s) {
        if (!beforeTodo.isFixed() && isChanged(s)) {
            beforeTodo.setFixed(true);
        }

        textChangeSubject.onNext(isChanged(s));
    }

    @Override
    public void saveTodo(String text) {
        beforeTodo.setBody(text);
        beforeTodo.setFixed(false);
        if (beforeTodo.getId() == -1) {
            beforeTodo.setId(TodoManager.getMaxId() + 1);
        }
        view.onSaved(requestType, beforeTodo);

        textChangeSubject.onNext(false);
    }

    private boolean isChanged(String s) {
        return !beforeTodo.getBody().equals(s);
    }

    @Override
    public boolean isFixed() {
        return beforeTodo.isFixed();
    }
}
