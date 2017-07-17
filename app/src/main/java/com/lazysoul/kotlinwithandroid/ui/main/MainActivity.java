package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.R;
import com.lazysoul.kotlinwithandroid.common.BaseActivity;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;
import com.lazysoul.kotlinwithandroid.singletons.TodoManager;
import com.lazysoul.kotlinwithandroid.ui.detail.DetailActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;

public class MainActivity extends BaseActivity implements MainMvpView, TodoListener {

    private TodoAdapter todoAdapter;

    private View emptyView;

    private final int REQUEST_CODE_DETAIL = 100;

    private final String KEY_IS_FIRST = "isFirst";

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferences.Editor editor;

    @Inject
    Realm realm;

    MainMvpPresenter<MainActivity> presenter;

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

        boolean isFirst = sharedPreferences.getBoolean(KEY_IS_FIRST, false);
        presenter.loadTotoList(isFirst);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DETAIL) {
            int resultType = data.getIntExtra(TodoManager.KEY_RESULT_TYPE, -1);
            int todoId = data.getIntExtra(TodoManager.KEY_ID, -1);
            switch (resultType) {
                case TodoManager.RESULT_TYPE_CREATED:
                    presenter.insert(todoId);
                    break;
                case TodoManager.RESULT_TYPE_UPDATED:
                    todoAdapter.update(todoId);
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void inject() {
        component.inject(this);
    }

    @Override
    public void initPresenter(BaseMvpView view) {
        presenter = new MainMvpPresenterImpl(realm);
        presenter.attachView(this);
    }

    @Override
    public void onUpdateTodoList(List<Todo> todoList) {
        todoAdapter.addItems(todoList);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onCreatedTodo(Todo todo) {
        todoAdapter.addItem(todo);
    }

    @Override
    public void showEmtpyView() {
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessCreateSampes() {
        editor.putBoolean(KEY_IS_FIRST, true).apply();
    }

    @Override
    public void onChecked(int id, boolean isChecked) {
        presenter.checked(id, isChecked);
    }

    @Override
    public void onClicked(int id) {
        goTodo(id);
    }

    private void createTodo() {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_CREATE);
        startActivityForResult(intent, REQUEST_CODE_DETAIL);
    }

    private void goTodo(int id) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(TodoManager.KEY_REQUEST_TYPE, TodoManager.REQUEST_TYPE_VIEW);
        intent.putExtra(TodoManager.KEY_ID, id);
        startActivityForResult(intent, REQUEST_CODE_DETAIL);
    }
}
