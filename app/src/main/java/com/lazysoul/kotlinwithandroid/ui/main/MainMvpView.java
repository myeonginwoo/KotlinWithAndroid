package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

interface MainMvpView extends BaseMvpView {

    void onUpdateTodoList(List<Todo> todoList);

    void onUpdateTodo(Todo todo);

    void onCreatedTodo(Todo todo);

    void showEmtpyView();

    void onCreatedSampes(List<Todo> todoList);
}
