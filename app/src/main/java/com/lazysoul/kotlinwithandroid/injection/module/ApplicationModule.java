package com.lazysoul.kotlinwithandroid.injection.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lazysoul on 2017. 7. 17..
 */

@Module
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application provideApplication() {
        return application;
    }

    @Provides
    SharedPreferences provideSharedPrefs() {
        return application.getSharedPreferences("kotlinWithAndroid", Context.MODE_PRIVATE);
    }

    @Provides
    SharedPreferences.Editor provideEditor(SharedPreferences pref) {
        return pref.edit();
    }

}
