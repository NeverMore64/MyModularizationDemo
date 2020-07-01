package com.example.basemodule.basemvp;

import android.text.TextUtils;
import android.util.ArrayMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * create by zy on 2019/9/26
 * </p>
 */
public class BasePImpl<V, M> {

    protected V mView;
    protected M mModel;

    private ArrayMap<String, Disposable> mSubMap;
    private CompositeDisposable mCompositeSubscription;

    public BasePImpl() {
        this.mSubMap = new ArrayMap<>();
    }

    public CompositeDisposable getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeDisposable();
        }
        return mCompositeSubscription;
    }

    public void addSubscription(Disposable s) {
        addSubscription(s, null);
    }

    public void addSubscription(Disposable s, String tag) {
        getCompositeSubscription().add(s);
        if (!TextUtils.isEmpty(tag)) {
            mSubMap.put(tag, s);
        }
    }

    public void cancelSubscription(String tag) {
        if (mSubMap.containsKey(tag)) {
            try {
                mSubMap.get(tag).dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onDestroy() {
        // 结束网络请求
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.clear();
        }
        if (mSubMap != null) {
            mSubMap.clear();
            mSubMap = null;
        }
    }

}
