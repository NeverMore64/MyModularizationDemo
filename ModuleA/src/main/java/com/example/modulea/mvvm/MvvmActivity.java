package com.example.modulea.mvvm;

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
@Router("moduleA_mvvm_activity")
public class MvvmActivity extends BaseActivity {

    @Override
    protected int setLayoutId() {
        return R.layout.module_a_activity_mvvm;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MvvmViewModel viewModel = new ViewModelProvider(this).get(MvvmViewModel.class);
        getLifecycle().addObserver(new MvvmActivityLife(viewModel, MvvmActivity.this));
    }
}
