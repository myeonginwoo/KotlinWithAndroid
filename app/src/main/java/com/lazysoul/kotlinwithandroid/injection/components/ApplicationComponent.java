package com.lazysoul.kotlinwithandroid.injection.components;

import com.lazysoul.kotlinwithandroid.KotlinWithAndroid;
import com.lazysoul.kotlinwithandroid.injection.module.ApplicationModule;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Lazysoul on 2017. 7. 17..
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(KotlinWithAndroid kotlinWithAndroid);

    SharedPreferences getSharedPreferences();

    SharedPreferences.Editor getEditor();
}
