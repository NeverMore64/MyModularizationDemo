package com.example.modulea.mvvm.openthreadtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.basemodule.basemvp.BaseActivity;
import com.example.modulea.R;
import com.github.mzule.activityrouter.annotation.Router;

/**
 * create by zy on 2020/7/15
 * </p>
 */
@Router("moduleA_open_thread_activity")
public class OpenThreadActivity extends BaseActivity {

    public static void start(Activity activity){
        activity.startActivity(new Intent(activity,OpenThreadActivity.class));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.module_a_activity_open_thread;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OpenThreadModel viewModel = new ViewModelProvider(this).get(OpenThreadModel.class);
        getLifecycle().addObserver(new OpenThreadActivityLife(this, viewModel));
    }
}
