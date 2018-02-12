package com.lazysoul.kotlinwithandroid.ui.detail

import android.content.Intent
import com.lazysoul.kotlinwithandroid.common.BaseMvpPresenter
import com.lazysoul.kotlinwithandroid.common.BaseMvpView

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

interface DetailMvpPresenter<in MvpView : BaseMvpView> : BaseMvpPresenter<MvpView> {

    val isFixed: Boolean

    fun loadTodo(intent: Intent)

    fun onTextChanged(s: String)

    fun saveTodo(text: String)
}
