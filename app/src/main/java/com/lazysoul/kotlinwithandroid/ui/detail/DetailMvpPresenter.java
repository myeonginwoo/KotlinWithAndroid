package com.lazysoul.kotlinwithandroid.ui.detail;

import com.lazysoul.kotlinwithandroid.common.BaseMvpPresenter;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;

import android.content.Intent;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

interface DetailMvpPresenter<MvpView extends BaseMvpView> extends BaseMvpPresenter<MvpView> {

    void loadTodo(Intent intent);

    void onTextChanged(String s);

    boolean isFixed();

    void saveTodo(String text);
}
