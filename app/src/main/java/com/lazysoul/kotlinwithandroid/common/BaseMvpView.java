package com.lazysoul.kotlinwithandroid.common;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

public interface BaseMvpView<View extends BaseMvpView> {

    void inject();
    void initPresenter(View view);
}
