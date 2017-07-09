package com.lazysoul.kotlinwithandroid.common;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

public interface BaseMvpPresenter<T extends BaseMvpView> {

    public void attachView(T view);

    void stop(boolean isFinishing);

    void destroy();
}
