package com.lazysoul.kotlinwithandroid.injection.module;

import com.lazysoul.kotlinwithandroid.injection.scopes.ActivityScope;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Lazysoul on 2017. 7. 17..
 */

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }
}
