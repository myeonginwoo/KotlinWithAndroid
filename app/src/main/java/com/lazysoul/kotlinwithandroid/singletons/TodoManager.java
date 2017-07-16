package com.lazysoul.kotlinwithandroid.singletons;

import com.lazysoul.kotlinwithandroid.datas.Todo;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

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

    public static RealmResults<Todo> getTodoList(Realm realm) {
        return realm.where(Todo.class).findAllSorted("id");
    }

    public static Todo load(Realm realm, int id) {
        return realm.where(Todo.class).equalTo("id", id).findFirst();
    }

    public static void createSamleTodo(Realm realm) {
        realm.beginTransaction();
        for (int i = 0; i < 10; i++) {
            Todo todo = realm.createObject(Todo.class, i);
            todo.setChecked(false);
            todo.setBody("Todo " + i);
            todo.setCreatedAt(Calendar.getInstance().getTime());
        }
        realm.commitTransaction();
    }

    public static int getMaxId(Realm realm) {
        return realm.where(Todo.class).max("id").intValue();
    }
}
