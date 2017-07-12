package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.R;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainMvpView {

    private MainMvpPresenterImpl<MainActivity> presenter;

    private RecyclerView recyclerView;

    private TodoAdapter todoAdapter;

    private SwipeRefreshLayout refreshLayout = null;

    private View emptyView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPresenter(this);

        recyclerView = (RecyclerView) findViewById(R.id.rlv_activity_main);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_activity_main);
        emptyView = findViewById(R.id.tv_activity_main_empty);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter();
        recyclerView.setAdapter(todoAdapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadTotoList(true);
            }
        });

        presenter.loadTotoList(false);
    }


    @Override
    public void initPresenter(BaseMvpView view) {
        presenter = new MainMvpPresenterImpl<>();
        presenter.attachView(this);
    }

    @Override
    public void onUpdateTodoList(List<Todo> todoList) {
        todoAdapter.addItems(todoList);
        todoAdapter.notifyDataSetChanged();
        emptyView.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh(List<Todo> todoList) {
        todoAdapter.clear();
        todoAdapter.addItems(todoList);
        todoAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        emptyView.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
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
        refreshLayout.setVisibility(View.GONE);
    }
}
