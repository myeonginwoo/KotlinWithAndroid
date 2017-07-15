package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.R;
import com.lazysoul.kotlinwithandroid.common.BaseActivity;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;
import com.lazysoul.kotlinwithandroid.singletons.TodoManager;
import com.lazysoul.kotlinwithandroid.ui.detail.DetailActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class MainActivity extends BaseActivity implements MainMvpView, TodoListener {

    private MainMvpPresenter<MainActivity> presenter;

    private TodoAdapter todoAdapter;

    private View emptyView;

    private final int REQUEST_CODE_DETAIL = 100;

    private final String KEY_IS_FIRST = "isFirst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tb_activity_main));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_activity_main);
        emptyView = findViewById(R.id.tv_activity_main_empty);
        FloatingActionButton addBt = (FloatingActionButton) findViewById(R.id.fa_activity_main);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter(this);
        recyclerView.setAdapter(todoAdapter);

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTodo();
            }
        });

        boolean isFirst = getPreferences(MODE_PRIVATE).getBoolean(KEY_IS_FIRST, false);
        presenter.loadTotoList(isFirst);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DETAIL) {
            int resultType = data.getIntExtra(TodoManager.KEY_REQUEST_TYPE, -1);
            switch (resultType) {
                case TodoManager.RESULT_TYPE_CREATED:
                    break;
                case TodoManager.RESULT_TYPE_UPDATED:
                    break;
                case TodoManager.RESULT_TYPE_DELETED:
                    break;
                case TodoManager.RESULT_TYPE_NONE:
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void createTodo() {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_CREATE);
        startActivityForResult(intent, REQUEST_CODE_DETAIL);
    }


    @Override
    public void initPresenter(BaseMvpView view) {
        presenter = new MainMvpPresenterImpl<>();
        presenter.attachView(this);
    }

    @Override
    public void onUpdateTodoList(List<Todo> todoList) {
        todoAdapter.addItems(todoList);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh(List<Todo> todoList) {
        todoAdapter.clear();
        todoAdapter.addItems(todoList);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onUpdatedTodo(Todo todo) {
    }

    @Override
    public void onRemovedTodo(Todo todo) {

    }

    @Override
    public void onCreatedTodo(Todo todo) {

    }

    @Override
    public void showEmtpyView() {
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessCreateSampes() {
        getPreferences(MODE_PRIVATE).edit().putBoolean(KEY_IS_FIRST, true).apply();
    }

    @Override
    public void onChecked(Todo todo, boolean isChecked) {
        presenter.checked(todo, isChecked);
    }

    @Override
    public void onClicked(Todo todo) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_VIEW);
        intent.putExtra(TodoManager.KEY_ID, todo.getId());
        startActivityForResult(intent, REQUEST_CODE_DETAIL);
    }
}
