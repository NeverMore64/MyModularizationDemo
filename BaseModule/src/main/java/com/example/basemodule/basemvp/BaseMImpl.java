package com.example.basemodule.basemvp;

import com.example.basemodule.BaseApplication;
import com.example.basemodule.network.ApiService;

import javax.inject.Inject;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public class BaseMImpl {

    public BaseMImpl() {
        BaseApplication.apiComponent.inject(this);
    }

    @Inject
    protected ApiService mApiService;

}
