package com.lazysoul.kotlinwithandroid

import android.app.Application
import com.lazysoul.kotlinwithandroid.injection.components.ApplicationComponent
import com.lazysoul.kotlinwithandroid.injection.components.DaggerApplicationComponent
import com.lazysoul.kotlinwithandroid.injection.module.ApplicationModule
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

open class KotlinWithAndroid : Application() {

    lateinit var component: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()

        initRealm()
    }

    private fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().build())
    }

}
