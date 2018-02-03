package com.lazysoul.kotlinwithandroid.injection.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

/**
 * Created by Lazysoul on 2017. 7. 17..
 */

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    internal fun provideSharedPrefs(): SharedPreferences {
        return application.getSharedPreferences("kotlinWithAndroid", Context.MODE_PRIVATE)
    }

    @Provides
    internal fun provideEditor(pref: SharedPreferences): SharedPreferences.Editor {
        return pref.edit()
    }

}
