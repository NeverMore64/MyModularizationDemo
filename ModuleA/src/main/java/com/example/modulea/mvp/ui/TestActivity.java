package com.example.modulea.mvp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.basemodule.basemvp.BaseActivity;
import com.example.basemodule.utils.BaseUtils;
import com.example.modulea.R;
import com.example.modulea.mvp.contract.TestContract;
import com.example.modulea.mvp.presenter.TestPImpl;
import com.github.mzule.activityrouter.annotation.Router;

/**
 * create by zy on 2019/9/26
 * </p>
 */
@Router("moduleA_test_activity")
public class TestActivity extends BaseActivity implements TestContract.IView {

    private TestContract.IPresenter mPresenter;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, TestActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPresenter().getData();
    }

    private TestContract.IPresenter getPresenter() {
        if (mPresenter == null) {
            mPresenter = new TestPImpl(this);
        }
        return mPresenter;
    }

    @Override
    public void showData(String data) {
        BaseUtils.showShortToast(mContext, data);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.layout_modulea_activity_test;
    }

    @Override
    protected void initTitle() {

    }
}
