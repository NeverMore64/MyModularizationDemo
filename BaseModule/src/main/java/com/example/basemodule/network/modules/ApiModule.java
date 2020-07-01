package com.example.basemodule.network.modules;

import com.example.basemodule.network.ApiService;
import com.example.basemodule.network.scopes.ActivityScope;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * create by zy on 2019/9/26
 * </p>
 */
@Module
public class ApiModule {

    @Provides
    @ActivityScope
    public ApiService proviceApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

}
