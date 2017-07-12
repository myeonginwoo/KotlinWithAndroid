package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;
import com.lazysoul.kotlinwithandroid.managers.TodoManager;

import java.util.ArrayList;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

class MainMvpPresenterImpl<MvpView extends BaseMvpView>
        implements MainMvpPresenter<MvpView> {

    private MainMvpView view;

    @Override
    public void attachView(MvpView view) {
        this.view = (MainMvpView) view;
    }

    @Override
    public void stop(boolean isFinishing) {
        if (isFinishing) {
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void loadTotoList(boolean fromRefresh) {
        TodoManager todoManager = TodoManager.getInstance();
        ArrayList<Todo> todoList = todoManager.getTodoList();
        if (null != todoList && todoList.isEmpty()) {
            view.showEmtpyView();
        } else {
            if (fromRefresh) {
                view.onRefresh(todoList);
            } else {
                view.onUpdateTodoList(todoList);
            }
        }
    }

    @Override
    public void search(String keyword) {

    }

    @Override
    public void update(Todo todo) {

    }

    @Override
    public void remove(Todo todo) {

    }

    @Override
    public void delete(Todo todo) {

    }
}
