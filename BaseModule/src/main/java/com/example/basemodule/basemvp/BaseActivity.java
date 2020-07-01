package com.example.basemodule.basemvp;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.basemodule.utils.log.Log;

/**
 * create by zy on 2019/9/25
 * </p>
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected TextView mTitleTv;
    protected Toolbar mToolbar;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutId());
        initExtra(savedInstanceState == null ? getIntent().getExtras() : savedInstanceState);
        if (isFinishing()) return;
        mContext = this;
        initTitle();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(this.getClass().getSimpleName(), "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(this.getClass().getSimpleName(), "onPause");
    }

    protected abstract int setLayoutId();

    protected void initExtra(Bundle bundle) {

    }

    protected abstract void initTitle();

}
