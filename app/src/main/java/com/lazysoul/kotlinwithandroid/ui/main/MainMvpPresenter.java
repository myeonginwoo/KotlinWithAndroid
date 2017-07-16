package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.common.BaseMvpPresenter;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

interface
MainMvpPresenter<MvpView extends BaseMvpView> extends BaseMvpPresenter<MvpView> {

    void loadTotoList(boolean isFirst);

    void search(String keyword);

    void insert(int id);

    void checked(int id, boolean isChecked);
}
