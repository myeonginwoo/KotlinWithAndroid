package com.lazysoul.kotlinwithandroid.ui;

import com.lazysoul.kotlinwithandroid.R;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainMvpView {

    private MainMvpPresenterImpl<MainActivity> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPresenter(this);

        presenter.loadTotoList();
    }


    @Override
    public void initPresenter(BaseMvpView view) {
        presenter = new MainMvpPresenterImpl<>();
        presenter.attachView(this);
    }

    @Override
    public void updateTodoList(List<Todo> todoList) {

    }

    @Override
    public void onRefresh(List<Todo> todoList) {

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
}
