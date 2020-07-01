package com.example.moduleb;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.basemodule.basemvp.BaseActivity;
import com.example.basemodule.utils.BaseUtils;
import com.example.basemodule.utils.log.Log;
import com.github.mzule.activityrouter.annotation.Router;
import com.github.mzule.activityrouter.router.Routers;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * create by zy on 2019/9/25
 * </p>
 */
@Router("moduleB_main_activity")
public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseUtils.showShortToast(MainActivity.this, "我是ModuleB的MainActivity");
        EventBus.getDefault().register(this);
        findViewById(R.id.tvModuleBTv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EventBus.getDefault().post("我是BBB传过来的数据");
//                finish();
                Routers.open(MainActivity.this, "jump_module://moduleA_test_activity");
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_moduleb_activity_main;
    }

    @Override
    protected void initTitle() {

    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getMessage(String evt) {
        Log.d("AAA", evt);
//        BaseUtils.showShortToast(MainActivity.this, evt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
