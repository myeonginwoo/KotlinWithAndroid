package com.lazysoul.kotlinwithandroid.ui.main;

import com.lazysoul.kotlinwithandroid.R;
import com.lazysoul.kotlinwithandroid.common.BaseActivity;
import com.lazysoul.kotlinwithandroid.datas.Todo;
import com.lazysoul.kotlinwithandroid.singletons.TodoManager;
import com.lazysoul.kotlinwithandroid.ui.detail.DetailActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainMvpView, TodoListener {

    private static final String KEY_IS_FIRST = "isFirst";

    private TodoAdapter todoAdapter;

    private View emptyView;

    private final int REQUEST_CODE_DETAIL = 100;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    SharedPreferences.Editor editor;

    MainMvpPresenter<MainMvpView> presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.tb_activity_main));

        emptyView = findViewById(R.id.tv_activity_main_empty);
        FloatingActionButton addBt = findViewById(R.id.fa_activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_activity_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        todoAdapter = new TodoAdapter(this);
        recyclerView.setAdapter(todoAdapter);

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTodo();
            }
        });

        if (sharedPreferences.getBoolean(KEY_IS_FIRST, true)) {
            presenter.createTodoSamples();
            editor.putBoolean(KEY_IS_FIRST, false).apply();
        } else {
            presenter.loadTodoList();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_DETAIL) {
            int resultType = data.getIntExtra(TodoManager.KEY_RESULT_TYPE, -1);
            int id = data.getIntExtra(TodoManager.KEY_ID, -1);
            String body = data.getStringExtra(TodoManager.KEY_BODY);
            switch (resultType) {
                case TodoManager.RESULT_TYPE_CREATED:
                    presenter.insert(id, body);
                    break;
                case TodoManager.RESULT_TYPE_UPDATED:
                    presenter.update(id, body);
                    break;
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        setSearchView(menu.findItem(R.id.menu_activity_main_search));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void inject() {
        component.inject(this);
    }

    @Override
    public void initPresenter() {
        presenter = new MainMvpPresenterImpl<>();
        presenter.attachView(this);
    }

    @Override
    public void onUpdateTodoList(List<Todo> todoList) {
        for (Todo todo : todoList) {
            Log.d("foo", "id : " + todo.getId() + ", body : " + todo.getBody() + ", checked : " + todo.isChecked());
        }
        todoAdapter.addItems(todoList);
        emptyView.setVisibility(View.GONE);
    }

    @Override
    public void onUpdateTodo(Todo todo) {
        todoAdapter.update(todo);
    }

    @Override
    public void onCreatedTodo(Todo todo) {
        todoAdapter.addItem(todo);
    }

    @Override
    public void showEmtpyView() {
        todoAdapter.clear();
        emptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCreatedSampes(List<Todo> todoList) {
        todoAdapter.addItems(todoList);
    }

    @Override
    public void onClicked(int id) {
        goTodo(id);
    }

    @Override
    public void onChecked(int id, boolean isChecked) {
        presenter.checked(id, isChecked);
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

    private void setSearchView(MenuItem searchMenuItem) {
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setQueryHint(getString(R.string.hint_search));
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.searchQuery(newText);
                return true;
            }
        });

        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                presenter.searchFinish();
                return true;
            }
        });
    }
}
