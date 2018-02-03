package com.lazysoul.kotlinwithandroid.injection.components

import com.lazysoul.kotlinwithandroid.KotlinWithAndroid
import com.lazysoul.kotlinwithandroid.injection.module.ApplicationModule

import android.content.SharedPreferences

import javax.inject.Singleton

import dagger.Component

/**
 * Created by Lazysoul on 2017. 7. 17..
 */

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    val sharedPreferences: SharedPreferences

    val editor: SharedPreferences.Editor

    fun inject(kotlinWithAndroid: KotlinWithAndroid)
}
