package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.common.BaseMvpPresenter;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

interface MainMvpPresenter<MvpView extends BaseMvpView> extends BaseMvpPresenter<MvpView> {

    void createTodoSamples();

    void loadTodoList();

    void insert(int id, String body);

    void update(int id, String body);

    void searchQuery(String text);

    void searchFinish();

    void checked(int id, boolean isChecked);
}
