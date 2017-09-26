package com.lazysoul.kotlinwithandroid.common

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.lazysoul.kotlinwithandroid.KotlinWithAndroid
import com.lazysoul.kotlinwithandroid.injection.components.ActivityComponent
import com.lazysoul.kotlinwithandroid.injection.components.ApplicationComponent
import com.lazysoul.kotlinwithandroid.injection.components.DaggerActivityComponent
import com.lazysoul.kotlinwithandroid.injection.module.ActivityModule

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

abstract class BaseActivity : AppCompatActivity(), BaseMvpView {

    protected lateinit var component: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component = DaggerActivityComponent
                .builder()
                .applicationComponent(applicationComponet)
                .activityModule(ActivityModule(this))
                .build()

        inject()
        initPresenter()
    }

    val applicationComponet: ApplicationComponent
        get() = (application as KotlinWithAndroid).component

    abstract fun inject()

    abstract fun initPresenter()
}
