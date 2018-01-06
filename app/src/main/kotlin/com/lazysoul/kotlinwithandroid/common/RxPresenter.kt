package com.lazysoul.kotlinwithandroid.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

open class RxPresenter protected constructor() {

    private val disposables: CompositeDisposable by lazy { CompositeDisposable() }

    protected fun dispose() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    fun add(disposable: Disposable) {
        disposables.add(disposable)
    }
}
