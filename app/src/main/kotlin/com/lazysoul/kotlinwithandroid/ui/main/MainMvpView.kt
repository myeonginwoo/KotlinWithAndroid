package com.lazysoul.kotlinwithandroid.ui.main

import com.lazysoul.kotlinwithandroid.common.BaseMvpView
import com.lazysoul.kotlinwithandroid.datas.Todo

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

interface MainMvpView : BaseMvpView {

    fun onUpdateTodoList(todoList: List<Todo>)

    fun onCreatedTodo(todo: Todo)

    fun showEmtpyView()

    fun onSuccessCreateSampes()
}
