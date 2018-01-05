package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.common.RxPresenter;
import com.lazysoul.kotlinwithandroid.datas.Todo;
import com.lazysoul.kotlinwithandroid.singletons.TodoManager;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

class MainMvpPresenterImpl<MvpView extends BaseMvpView> extends RxPresenter implements MainMvpPresenter<MvpView> {

    private MainMvpView view;

    private PublishSubject<String> searchTextChangeSubject = PublishSubject.create();

    MainMvpPresenterImpl() {
        add(searchTextChangeSubject
            .throttleLast(1, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<String>() {
                @Override
                public void accept(String text) throws Exception {
                    ArrayList<Todo> searchedList = TodoManager.search(text);
                    if (searchedList.isEmpty()) {
                        view.showEmtpyView();
                    } else {
                        view.onUpdateTodoList(searchedList);
                    }
                }
            }));
    }

    @Override
    public void attachView(MvpView view) {
        this.view = (MainMvpView) view;
    }

    @Override
    public void destroy() {
        dispose();
    }

    @Override
    public void createTodoSamples() {
        view.onCreatedSampes(TodoManager.createSamples());
    }

    @Override
    public void loadTodoList() {
        List<Todo> todoList = TodoManager.getTodoList();
        if (null != todoList && todoList.isEmpty()) {
            view.showEmtpyView();
        } else {
            view.onUpdateTodoList(todoList);
        }
    }

    @Override
    public void insert(int id, String body) {
        view.onCreatedTodo(TodoManager.insert(id, body));
    }

    @Override
    public void update(int id, String body) {
        Todo todo = TodoManager.update(id, body);
        if (todo != null) {
            view.onUpdateTodo(todo);
        }
    }

    @Override
    public void checked(int id, boolean isChecked) {
        TodoManager.checked(id, isChecked);
    }

    @Override
    public void searchQuery(String text) {
        searchTextChangeSubject.onNext(text);
    }

    @Override
    public void searchFinish() {
        loadTodoList();
    }
}
