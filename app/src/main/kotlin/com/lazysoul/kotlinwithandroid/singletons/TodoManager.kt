package com.lazysoul.kotlinwithandroid.singletons

import com.lazysoul.kotlinwithandroid.datas.Todo

import java.util.ArrayList

/**
 * Created by Lazysoul on 2017. 7. 12..
 */

object TodoManager {

    const val KEY_ID = "id"

    const val KEY_BODY = "body"

    const val KEY_REQUEST_TYPE = "request_type"

    const val KEY_RESULT_TYPE = "result_type"

    const val REQUEST_TYPE_CREATE = 100

    const val REQUEST_TYPE_VIEW = 101

    const val RESULT_TYPE_CREATED = 200

    const val RESULT_TYPE_UPDATED = 201

    private val todoList = ArrayList<Todo>()

    val maxId: Int
        get() = todoList.maxBy { it.id }?.id ?: -1

    fun getTodoList(): List<Todo> = todoList

    fun createSamples(): ArrayList<Todo> {
        (0..9).mapTo(todoList) {
            Todo(it).apply {
                isChecked = false
                body = "Todo " + it
            }
        }
        return todoList
    }

    fun getTodo(id: Int): Todo = todoList.firstOrNull { it.id == id } ?: throw  NullPointerException("todo is null")

    fun search(text: String): ArrayList<Todo> {
        val result = ArrayList<Todo>()
        if (text.isEmpty()) {
            result.addAll(todoList)
        } else {
            todoList.filterTo(result) { it.body.contains(text) }
        }
        return result
    }

    fun insert(id: Int, body: String): Todo =
        Todo(id, body, true)
            .also { todoList.add(it) }

    fun update(id: Int, body: String): Todo? = todoList
        .firstOrNull { it.id == id }
        ?.also { it.body = body }

    fun checked(id: Int, isChecked: Boolean) {
        todoList.firstOrNull { it.id == id }
            ?.also { it.isChecked = isChecked }
    }
}
