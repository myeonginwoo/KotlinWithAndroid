package com.lazysoul.kotlinwithandroid.common;

import com.lazysoul.kotlinwithandroid.KotlinWithAndroid;
import com.lazysoul.kotlinwithandroid.injection.components.ActivityComponent;
import com.lazysoul.kotlinwithandroid.injection.components.ApplicationComponent;
import com.lazysoul.kotlinwithandroid.injection.components.DaggerActivityComponent;
import com.lazysoul.kotlinwithandroid.injection.module.ActivityModule;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

public abstract class BaseActivity extends AppCompatActivity implements BaseMvpView {

    protected ActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        component = DaggerActivityComponent
                .builder()
                .applicationComponent(getApplicationComponet())
                .activityModule(new ActivityModule(this))
                .build();

        inject();
        initPresenter();
    }

    public ApplicationComponent getApplicationComponet() {
        return ((KotlinWithAndroid) getApplication()).getComponent();
    }
}
