package com.lazysoul.kotlinwithandroid.singletons;

import com.lazysoul.kotlinwithandroid.datas.Todo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lazysoul on 2017. 7. 12..
 */

public class TodoManager {

    public static final String KEY_ID = "id";

    public static final String KEY_BODY = "body";

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
        for (int i = 0; i < 10; i++) {
            Todo todo = new Todo();
            todo.setId(i);
            todo.setChecked(false);
            todo.setBody("Todo " + i);
            todoList.add(todo);
        }
        return todoList;
    }

    public static int getMaxId() {
        int max = -1;
        for (Todo todo : todoList) {
            if (todo.getId() > max) {
                max = todo.getId();
            }
        }
        return max;
    }

    @Nullable
    public static Todo getTodo(int id) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }

    @NonNull
    public static ArrayList<Todo> search(String text) {
        ArrayList<Todo> result = new ArrayList<>();
        if (text.isEmpty()) {
            result.addAll(todoList);
        } else {

            for (Todo todo : todoList) {
                if (todo.getBody().contains(text)) {
                    result.add(todo);
                }
            }
        }
        return result;
    }

    @NonNull
    public static Todo insert(int id, String body) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setBody(body);
        todo.setChecked(false);
        todoList.add(todo);
        return todo;
    }

    @Nullable
    public static Todo update(int id, String body) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                todo.setBody(body);
                return todo;
            }
        }
        return null;
    }

    public static void checked(int id, boolean isChecked) {
        for (Todo todo : todoList) {
            if (todo.getId() == id) {
                todo.setChecked(isChecked);
                break;
            }
        }
    }
}
