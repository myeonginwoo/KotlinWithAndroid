package com.lazysoul.kotlinwithandroid;

import com.lazysoul.kotlinwithandroid.injection.components.ApplicationComponent;
import com.lazysoul.kotlinwithandroid.injection.components.DaggerApplicationComponent;
import com.lazysoul.kotlinwithandroid.injection.module.ApplicationModule;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Lazysoul on 2017. 7. 15..
 */

public class KotlinWithAndroid extends Application {

    protected ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        initRealm();
    }

    private void initRealm() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }

}
