package com.example.basemodule.network.components;

import android.content.SharedPreferences;

import com.example.basemodule.network.modules.AppModule;
import com.example.basemodule.network.modules.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * create by zy on 2019/9/26
 * </p>
 */
@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {

    public Retrofit retrofit();

    public OkHttpClient okHttpClient();

    public SharedPreferences sharedPreferences();

}
