package com.lazysoul.kotlinwithandroid.ui.detail

import com.lazysoul.kotlinwithandroid.common.BaseMvpView
import com.lazysoul.kotlinwithandroid.datas.Todo

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

interface DetailMvpView : BaseMvpView {

    fun onUpdated(todo: Todo, editable: Boolean)

    fun onChagedSaveBt()

    fun onSaved(requestType: Int, todoId: Int)
}
