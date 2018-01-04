package com.lazysoul.kotlinwithandroid.singletons;

import com.lazysoul.kotlinwithandroid.datas.Todo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lazysoul on 2017. 7. 12..
 */

public class TodoManager {

    public static final String KEY_ID = "id";

    public static final String KEY_REQUEST_TYPE = "request_type";

    public static final String KEY_RESULT_TYPE = "result_type";

    public static final int REQUEST_TYPE_CREATE = 100;

    public static final int REQUEST_TYPE_VIEW = 101;

    public static final int RESULT_TYPE_CREATED = 200;

    public static final int RESULT_TYPE_UPDATED = 201;

    private static ArrayList<Todo> todoList = new ArrayList<>();

    public static List<Todo> getTodoList() {
        return todoList;
    }

    public static ArrayList<Todo> createSamples() {
        todoList.clear();
        for (int i = 0; i < 10; i++) {
            Todo todo = new Todo();
            todo.setId(i);
            todo.setChecked(false);
            todo.setBody("Todo " + i);
            todo.setCreatedAt(Calendar.getInstance().getTime());
            todoList.add(todo);
        }
        return todoList;
    }

    public static int getMaxId() {
        int max = 0;
        for (Todo todo : todoList) {
            if (todo.getId() > max) {
                max = todo.getId();
            }
        }
        return max;
    }

    public static Todo getTodo(int id) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }

    public static ArrayList<Todo> search(String text) {
        ArrayList<Todo> result = new ArrayList<>();
        for (Todo todo : todoList) {
            if (todo.getBody().contains(text)) {
                result.add(todo);
            }
        }
        return result;
    }

    public static Todo insert(int id) {
        Todo todo = new Todo();
        todo.setId(id);
        todoList.add(todo);
        return todo;
    }
}
