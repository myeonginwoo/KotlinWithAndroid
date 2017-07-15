package com.lazysoul.kotlinwithandroid.ui.detail;

import com.lazysoul.kotlinwithandroid.R;
import com.lazysoul.kotlinwithandroid.common.BaseActivity;
import com.lazysoul.kotlinwithandroid.common.BaseMvpView;
import com.lazysoul.kotlinwithandroid.datas.Todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

public class DetailActivity extends BaseActivity implements DetailMvpView {

    DetailMvpPresenter presenter;

    AppCompatEditText et;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        et = (AppCompatEditText) findViewById(R.id.et_activity_detail);

        presenter.loadTodo(getIntent());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initPresenter(BaseMvpView view) {
        presenter = new DetailMvpPresentImpl();
        presenter.attachView(this);
    }

    @Override
    public void onUpdated(Todo todo, boolean editable) {
        et.setText(todo.getBody());
        if (editable) {
            et.requestFocus();
        }
    }

    @Override
    public void onDeleted(Todo todo) {
        // TODO: 2017. 7. 15.
    }

    @Override
    public void onCreated(Todo todo) {
        // TODO: 2017. 7. 15.
    }
}
