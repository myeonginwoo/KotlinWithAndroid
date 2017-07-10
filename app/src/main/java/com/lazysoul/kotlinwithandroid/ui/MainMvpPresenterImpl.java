package com.lazysoul.kotlinwithandroid.ui;

import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import java.util.List;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

class MainMvpPresenterImpl<MvpView extends BaseMvpView>
        implements MainMvpPresenter<MvpView> {

    private MainMvpView view;

    private List<Todo> todoList;

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
        if (null != todoList && todoList.isEmpty()) {
            view.showEmtpyView();
        } else {
            if (fromRefresh) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
