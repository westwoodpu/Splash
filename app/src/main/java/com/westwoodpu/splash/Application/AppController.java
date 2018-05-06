package com.westwoodpu.splash.Application;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * realm appcontroller class for the entry of the application
 */

public class AppController extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("realmWallpaper")
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
