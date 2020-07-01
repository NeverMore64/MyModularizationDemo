package com.example.basemodule;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.example.basemodule.network.components.ApiComponent;
import com.example.basemodule.network.components.DaggerApiComponent;
import com.example.basemodule.network.components.DaggerNetComponent;
import com.example.basemodule.network.components.NetComponent;
import com.example.basemodule.network.modules.ApiModule;
import com.example.basemodule.network.modules.AppModule;
import com.example.basemodule.network.modules.NetModule;
import com.example.basemodule.utils.log.Log;

import java.util.List;

/**
 * create by zy on 2019/9/27
 * </p>
 */
public class BaseApplication extends Application {

    public static ApiComponent apiComponent;
    public static NetComponent netComponent;
    public static BaseApplication mInstance;

    private boolean mIsDebugMode = BuildConfig.DEBUG;

    public static BaseApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initPublicApplication();
        if (shouldInit()) {
            initApplication();
        }
    }

    private boolean shouldInit() {
        ActivityManager activityManager = (ActivityManager) mInstance.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
        String mainProcessName = mInstance.getPackageName();
        int myPid = Process.myPid();
        if (processInfos != null && processInfos.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo processInfo : processInfos) {
                if (processInfo.pid == myPid && processInfo.processName.equals(mainProcessName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initPublicApplication() {
        Log.getInstanceLog().init();
        // 测试环境设置为true 正式环境为false
//        Log.getInstanceLog().setIsLog(mIsDebugMode);
//        Log.getInstanceLog().setIsLogToSdcard(mIsDebugMode);
        Log.getInstanceLog().setIsLog(true);
        Log.getInstanceLog().setIsLogToSdcard(true);
    }

    private void initApplication() {
        netComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule("http://192.168.2.3:8088"))
                .build();

        apiComponent = DaggerApiComponent.builder()
                .netComponent(netComponent)
                .apiModule(new ApiModule())
                .build();
    }

}
