package com.lazysoul.kotlinwithandroid.common;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

public interface BaseMvpPresenter<T extends BaseMvpView> {

    void attachView(T view);

    void destroy();
}
