package com.example.tinkofftestapp;

import android.app.Application;

import com.example.tinkofftestapp.dagger.AppComponent;
import com.example.tinkofftestapp.dagger.AppModule;
import com.example.tinkofftestapp.dagger.DaggerAppComponent;

import timber.log.Timber;

public class App extends Application {
    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
