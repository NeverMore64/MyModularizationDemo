package com.example.moduledemo;

import android.app.Application;

import com.github.mzule.activityrouter.annotation.Modules;

@Modules({"app"})
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
