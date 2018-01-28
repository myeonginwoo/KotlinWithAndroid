package com.lazysoul.kotlinwithandroid.common;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

public class RxPresenter {

    private CompositeDisposable disposables;

    protected RxPresenter() {
        disposables = new CompositeDisposable();
    }

    protected void dispose() {
        if (null != disposables && !disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    public void add(Disposable disposable) {
        disposables.add(disposable);
    }
}
