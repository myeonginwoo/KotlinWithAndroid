package com.lazysoul.kotlinwithandroid.injection.module

import com.lazysoul.kotlinwithandroid.injection.scopes.ActivityScope

import android.app.Activity

import dagger.Module
import dagger.Provides
import io.realm.Realm

/**
 * Created by Lazysoul on 2017. 7. 17..
 */

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @ActivityScope
    internal fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @ActivityScope
    internal fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }
}
