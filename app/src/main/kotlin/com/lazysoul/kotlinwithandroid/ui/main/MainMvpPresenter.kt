package com.lazysoul.kotlinwithandroid.ui.main

import com.lazysoul.kotlinwithandroid.common.BaseMvpPresenter
import com.lazysoul.kotlinwithandroid.common.BaseMvpView

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

interface MainMvpPresenter<in MvpView : BaseMvpView> : BaseMvpPresenter<MvpView> {

    fun createTodoSamples()

    fun loadTodoList()

    fun insert(id: Int, body: String)

    fun update(id: Int, body: String)

    fun searchQuery(text: String)

    fun searchFinish()

    fun checked(id: Int, isChecked: Boolean)
}
