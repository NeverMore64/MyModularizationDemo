package com.example.modulea.mvp.model;

import com.example.basemodule.basemvp.BaseMImpl;
import com.example.modulea.mvp.contract.TestContract;

import io.reactivex.Observable;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public class TestMImpl extends BaseMImpl implements TestContract.IModel {

    @Override
    public Observable<String> getData() {
        return mApiService.getTestData();
    }
}
