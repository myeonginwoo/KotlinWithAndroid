package com.lazysoul.kotlinwithandroid.injection.components

import android.content.SharedPreferences
import com.lazysoul.kotlinwithandroid.KotlinWithAndroid
import com.lazysoul.kotlinwithandroid.injection.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Lazysoul on 2017. 7. 17..
 */

@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun injet(kotlinWithAndroid: KotlinWithAndroid)

    val sharedPreferences: SharedPreferences

    val editor: SharedPreferences.Editor
}
