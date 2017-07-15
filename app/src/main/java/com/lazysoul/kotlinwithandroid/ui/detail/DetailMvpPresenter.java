package com.lazysoul.kotlinwithandroid.ui.detail;

import com.lazysoul.kotlinwithandroid.common.BaseMvpPresenter;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import android.content.Intent;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

interface DetailMvpPresenter<MvpView extends BaseMvpView> extends BaseMvpPresenter<MvpView> {

    void loadTodo(Intent intent);

    void update(Todo todo);

    void delete(Todo todo);

    void create(Todo todo);
}
