package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.common.BaseMvpPresenter;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

interface
MainMvpPresenter<MvpView extends BaseMvpView> extends BaseMvpPresenter<MvpView> {

    void loadTotoList(boolean isFirst);

    void search(String keyword);

    void update(Todo todo);

    void remove(Todo todo);

    void delete(Todo todo);

    void checked(int id, boolean isChecked);
}
