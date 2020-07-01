package com.example.moduledemo;

import com.example.basemodule.BaseApplication;
import com.github.mzule.activityrouter.annotation.Modules;

@Modules({"app", "moduleA", "moduleB"})
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
