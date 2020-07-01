package com.example.modulea.mvp.presenter;

import android.util.Log;

import com.example.basemodule.basemvp.BasePImpl;
import com.example.modulea.mvp.contract.TestContract;
import com.example.modulea.mvp.model.TestMImpl;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public class TestPImpl extends BasePImpl<TestContract.IView, TestContract.IModel> implements TestContract.IPresenter {

    public TestPImpl(TestContract.IView view) {
        this.mView = view;
        this.mModel = new TestMImpl();
    }

    @Override
    public void getData() {
        addSubscription(mModel.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mView.showData(s);
                        Log.d("AAA", s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showData(throwable.getMessage());
                        Log.d("AAA", throwable.getMessage());
                    }
                }));
    }
}
