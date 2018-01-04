package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.R;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
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

    private TodoListener todoListener;

    TodoAdapter(TodoListener todoListener) {
        this.todoListener = todoListener;
    }

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
        notifyDataSetChanged();
    }

    void update(int todoId) {
        int position = -1;
        for (int i = 0; i < todoList.size(); i++) {
            if (todoId == todoList.get(i).getId()) {
                position = i;
                break;
            }
        }
        notifyItemChanged(position);
    }

    void addItem(Todo todo) {
        todoList.add(todo);
        notifyItemInserted(todoList.size() - 1);
    }

    void clear() {
        todoList.clear();
        notifyDataSetChanged();
    }

    class TodoHolder extends RecyclerView.ViewHolder {

        private AppCompatCheckBox cb;

        private TextView tv;

        private CardView cv;

        TodoHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_todo, parent, false));
            cv = (CardView) itemView.findViewById(R.id.cv_item_todo);
            cb = (AppCompatCheckBox) itemView.findViewById(R.id.cb_item);
            tv = (TextView) itemView.findViewById(R.id.tv_item_todo_body);

        }

        void draw(final Todo todo) {
            cb.setChecked(todo.isChecked());
            cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    todoListener.onChecked(todo.getId(), isChecked);
                }
            });
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    todoListener.onClicked(todo.getId());
                }
            });
            tv.setText(todo.getBody());
        }
    }
}
