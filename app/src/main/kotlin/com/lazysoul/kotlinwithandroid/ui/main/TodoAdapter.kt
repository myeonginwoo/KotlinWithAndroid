package com.lazysoul.kotlinwithandroid.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.lazysoul.kotlinwithandroid.R
import com.lazysoul.kotlinwithandroid.datas.Todo
import kotlinx.android.synthetic.main.item_todo.view.*
import java.util.ArrayList

/**
 * Created by Lazysoul on 2017. 7. 12..
 */

class TodoAdapter(val todoListener: TodoListener)
    : RecyclerView.Adapter<TodoAdapter.TodoHolder>() {

    private val todoList = ArrayList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder =
            TodoHolder(parent)

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.draw(todoList[position])
    }

    override fun getItemCount(): Int = todoList.size

    fun addItems(list: List<Todo>) {
        todoList.addAll(list)
        notifyDataSetChanged()
    }

    fun update(todoId: Int) {
        val position = todoList.indexOfFirst { todoId == it.id }
        notifyItemChanged(position)
    }

    fun addItem(todo: Todo) {
        todoList.add(todo)
        notifyItemInserted(todoList.size - 1)
    }

    fun clear() {
        todoList.clear()
        notifyDataSetChanged()
    }

    inner class TodoHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)) {

        fun draw(todo: Todo) {
            with(itemView) {
                cb_item.isChecked = todo.isChecked
                cb_item.setOnCheckedChangeListener { _, isChecked ->
                    todoListener.onChecked(todo.id, isChecked)
                }
                cv_item_todo.setOnClickListener { todoListener.onClicked(todo.id) }
                tv_item_todo_body.text = todo.body
            }
        }
    }
}
