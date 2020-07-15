package com.example.modulea;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.basemodule.basemvp.BaseActivity;
import com.example.basemodule.utils.BaseUtils;
import com.example.modulea.mvvm.openthreadtest.OpenThreadActivity;
import com.github.mzule.activityrouter.annotation.Router;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * create by zy on 2019/9/25
 * </p>
 */
@Router("moduleA_main_activity")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        BaseUtils.showShortToast(MainActivity.this, "我是ModuleA的MainActivity");
        findViewById(R.id.tvModuleATv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                EventBus.getDefault().postSticky("我是从moduleA的MainActivity传递过来的数据");
//                Routers.open(MainActivity.this, "jump_module://moduleB_main_activity");

//                TestActivity.start(MainActivity.this);

                OpenThreadActivity.start(MainActivity.this);
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_modulea_activity_main;
    }

    @Override
    protected void initTitle() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(String evt) {
        BaseUtils.showShortToast(MainActivity.this, evt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
