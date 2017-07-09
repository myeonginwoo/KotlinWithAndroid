package com.lazysoul.kotlinwithandroid.ui;

import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import java.util.ArrayList;
import java.util.List;

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
    public List<Todo> loadTotoList() {
        List<Todo> todoList = new ArrayList<>();
        return null;
    }

    @Override
    public List<Todo> search(String keyword) {
        return null;
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
