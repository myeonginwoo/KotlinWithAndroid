package com.lazysoul.kotlinwithandroid.ui;

import com.lazysoul.kotlinwithandroid.common.BaseMvpPresenter;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import java.util.List;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

interface MainMvpPresenter<MvpView extends BaseMvpView> extends BaseMvpPresenter<MvpView> {

    List<Todo> loadTotoList();

    List<Todo> search(String keyword);

    void update(Todo todo);

    void remove(Todo todo);

    void delete(Todo todo);
}
