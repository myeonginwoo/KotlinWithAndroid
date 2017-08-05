package com.lazysoul.kotlinwithandroid.ui.main

import com.lazysoul.kotlinwithandroid.common.BaseMvpPresenter

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

interface MainMvpPresenter<in MvpView : MainMvpView> : BaseMvpPresenter<MvpView> {

    fun loadTotoList(isFirst: Boolean)

    fun insert(id: Int)

    fun checked(id: Int, isChecked: Boolean)

    fun searchQuery(text: String)

    fun searchFinish()
}
