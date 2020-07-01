package com.example.basemodule.network.upload;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by kongnan on 15-12-28.
 */
public class GsonHttpResponseHandler<T> {

    private static final String TAG = GsonHttpResponseHandler.class.getSimpleName();

    private Callback callBack;

    public GsonHttpResponseHandler(final Class<T> tClass) {
        this.callBack = new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    onFailure(call, new IOException("Unexpected code " + response));
                } else {
                    final T rsp = new Gson().fromJson(response.body().charStream(), tClass);
//                    if (BuildConfig.DEBUG)
//                        Log.json(TAG, rsp.toString());

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onSuccess(rsp);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
//                if (BuildConfig.DEBUG)
//                    e.printStackTrace();

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // 失败设置进度-1，为的是清除进度
                        GsonHttpResponseHandler.this.onProgress(-1);
                        GsonHttpResponseHandler.this.onFailure();
                    }
                });
            }
        };
    }

    public void onSuccess(T response) {
        // Do not do anything
    }

    public void onFailure() {
        // Do not do anything
    }

    public void onProgress(int progress) {
        // Do not do anything
    }

    public Callback getCallBack() {
        return callBack;
    }

    public void setCallBack(Callback callBack) {
        this.callBack = callBack;
    }
}
