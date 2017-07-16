package com.lazysoul.kotlinwithandroid.common;

import com.lazysoul.kotlinwithandroid.KotlinWithAndroid;
import com.lazysoul.kotlinwithandroid.injection.components.ApplicationComponent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseMvpView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        initPresenter(this);
    }

    public ApplicationComponent getApplicationComponet() {
        return ((KotlinWithAndroid) getApplication()).getComponent();
    }
}
