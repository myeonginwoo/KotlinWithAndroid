package com.lazysoul.kotlinwithandroid.common;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

public class RxPresenter {

    CompositeDisposable disposables;

    public RxPresenter() {
        disposables = new CompositeDisposable();
    }

    public void dispose() {
        if (null != disposables && !disposables.isDisposed()) {
            disposables.dispose();
            disposables = null;
        }
    }

    public void add(Disposable disposable) {
        disposables.add(disposable);
    }
}
