package com.lazysoul.kotlinwithandroid.injection.module

import android.app.Activity
import com.lazysoul.kotlinwithandroid.injection.scopes.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by Lazysoul on 2017. 7. 17..
 */

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @ActivityScope
    internal fun provideActivity(): Activity = activity
}
