package com.lazysoul.kotlinwithandroid.singletons

import com.lazysoul.kotlinwithandroid.datas.Todo
import io.realm.Case
import io.realm.Realm
import io.realm.RealmResults
import java.util.Calendar

/**
 * Created by Lazysoul on 2017. 7. 12..
 */

object TodoManager {

    const val KEY_ID = "id"

    const val KEY_REQUEST_TYPE = "request_type"

    const val KEY_RESULT_TYPE = "result_type"

    const val REQUEST_TYPE_CREATE = 100

    const val REQUEST_TYPE_VIEW = 101

    const val RESULT_TYPE_CREATED = 200

    const val RESULT_TYPE_UPDATED = 201

    fun getTodoList(realm: Realm): RealmResults<Todo> =
            realm.where(Todo::class.java).findAllSorted("id")

    fun load(realm: Realm, id: Int): Todo =
            realm.where(Todo::class.java).equalTo("id", id).findFirst()

    fun createSamleTodo(realm: Realm) {
        realm.beginTransaction()
        for (i in 0..9) {
            realm.createObject(Todo::class.java, i).apply {
                isChecked = false
                body = "Todo " + i
                createdAt = Calendar.getInstance().time
            }
        }
        realm.commitTransaction()
    }

    fun getMaxId(realm: Realm): Int {
        return realm.where(Todo::class.java)
                ?.max("id")?.toInt() ?: -1
    }

    fun search(realm: Realm, text: String): RealmResults<Todo> {
        return realm.where(Todo::class.java)
                .contains("body", text, Case.INSENSITIVE)
                .findAll()
    }
}
