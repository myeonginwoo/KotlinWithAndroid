package com.lazysoul.kotlinwithandroid.ui.main

import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.lazysoul.kotlinwithandroid.R
import com.lazysoul.kotlinwithandroid.datas.Todo
import java.util.ArrayList

/**
 * Created by Lazysoul on 2017. 7. 12..
 */

class TodoAdapter(val todoListener: TodoListener) : RecyclerView.Adapter<TodoAdapter.TodoHolder>() {

    private val todoList = ArrayList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder = TodoHolder(parent)

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.draw(todoList[position])
    }

    override fun getItemCount(): Int = todoList.size

    fun addItems(list: List<Todo>) {
        with(todoList) {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun update(todo: Todo) {
        todoList.withIndex()
            .firstOrNull { it.value.id == todo.id }
            ?.let {
                //                todoList[it.index].body = todo.body
                it.value.body = todo.body
                notifyItemChanged(it.index)
            }
    }

    fun addItem(todo: Todo) {
        todoList.add(todo)
        notifyItemInserted(todoList.size - 1)
    }

    fun clear() {
        todoList.clear()
        notifyDataSetChanged()
    }

    inner class TodoHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)) {

        private val cb: AppCompatCheckBox = itemView.findViewById(R.id.cb_item)

        private val tv: TextView = itemView.findViewById(R.id.tv_item_todo_body)

        private val cv: CardView = itemView.findViewById(R.id.cv_item_todo)

        fun draw(todo: Todo) {
            with(cb) {
                isChecked = todo.isChecked
                setOnClickListener {
                    todo.isChecked = !todo.isChecked
                    todoListener.onChecked(todo.id, todo.isChecked)
                }
            }
            cv.setOnClickListener { todoListener.onClicked(todo.id) }
            tv.text = todo.body
        }
    }
}
