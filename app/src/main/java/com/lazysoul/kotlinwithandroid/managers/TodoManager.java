package com.lazysoul.kotlinwithandroid.managers;

import com.lazysoul.kotlinwithandroid.datas.Todo;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Lazysoul on 2017. 7. 12..
 */

public class TodoManager {

    static TodoManager instance = null;

    private TodoManager() {

    }

    public static TodoManager getInstance() {
        if (instance == null) {
            return new TodoManager();
        } else {
            return instance;
        }
    }

    public ArrayList<Todo> getTodoList() {
        ArrayList<Todo> todoList = new ArrayList<>();
        todoList.add(new Todo(0, "Todo1", false, Calendar.getInstance().getTime()));
        todoList.add(new Todo(1, "Todo2", false, Calendar.getInstance().getTime()));
        todoList.add(new Todo(2, "Todo3", false, Calendar.getInstance().getTime()));
        todoList.add(new Todo(3, "Todo4", true, Calendar.getInstance().getTime()));
        todoList.add(new Todo(4, "Todo5", false, Calendar.getInstance().getTime()));
        todoList.add(new Todo(5, "Todo6", true, Calendar.getInstance().getTime()));
        todoList.add(new Todo(6, "Todo7", false, Calendar.getInstance().getTime()));
        return todoList;
    }
}
