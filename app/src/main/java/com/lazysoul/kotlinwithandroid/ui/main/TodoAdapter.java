package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.R;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lazysoul on 2017. 7. 12..
 */

class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    private ArrayList<Todo> todoList = new ArrayList<>();

    @Override
    public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TodoHolder(parent);
    }

    @Override
    public void onBindViewHolder(TodoHolder holder, int position) {
        holder.draw(todoList.get(position));
    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    void addItems(List<Todo> list) {
        todoList.addAll(list);
    }

    void clear() {
        todoList.clear();
    }

    class TodoHolder extends RecyclerView.ViewHolder {

        private AppCompatCheckBox cb;

        private TextView tv;

        TodoHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_todo, parent, false));

            cb = (AppCompatCheckBox) itemView.findViewById(R.id.cb_item);
            tv = (TextView) itemView.findViewById(R.id.tv_item_todo_body);
        }

        void draw(final Todo todo) {
            cb.setChecked(todo.isChecked());
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    todo.setChecked(isChecked);
                }
            });
            tv.setText(todo.getBody());
        }
    }
}
