package com.lazysoul.kotlinwithandroid.injection.components;

import com.lazysoul.kotlinwithandroid.injection.module.ActivityModule;
import com.lazysoul.kotlinwithandroid.injection.scopes.ActivityScope;
import com.lazysoul.kotlinwithandroid.ui.detail.DetailActivity;
import com.lazysoul.kotlinwithandroid.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by Lazysoul on 2017. 7. 17..
 */
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
@ActivityScope
public interface ActivityComponent extends ApplicationComponent {

    void inject(MainActivity activity);

    void inject(DetailActivity activity);
}
