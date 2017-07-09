package com.lazysoul.kotlinwithandroid.ui;

import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import java.util.List;

/**
 * Created by Lazysoul on 2017. 7. 9..
 */

interface MainMvpView extends BaseMvpView {

    public void updateTodoList(List<Todo> todoList);

    public void onRefresh(List<Todo> todoList);

    public void onUpdatedTodo(Todo todo);

    public void onRemovedTodo(Todo todo);

    public void onCreatedTodo(Todo todo);
}
