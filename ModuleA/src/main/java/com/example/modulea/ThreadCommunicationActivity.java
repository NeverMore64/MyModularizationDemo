package com.example.modulea;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.basemodule.basemvp.BaseActivity;
import com.example.basemodule.utils.log.Log;
import com.github.mzule.activityrouter.annotation.Router;

/**
 * create by zy on 2020/7/23
 * 线程间通信的几种方式
 */
@Router("moduleA_thread_communication_activity")
public class ThreadCommunicationActivity extends BaseActivity {

    private Handler handler;
    private TextView tvText;

    @Override
    protected int setLayoutId() {
        return R.layout.module_a_activity_thread_communication;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tvText = findViewById(R.id.tvText);
//        one();
//        two();
//        three();
        four();
    }

    /***
     * 通过Handler方式实现线程通信
     * 主线程中定义Handler,子线程发消息，通知Handler完成UI更新,Handler对象必须定义在主线程中，如果是多个类直接互相调用，就不是很方便
     * ，需要传递content对象或通过接口调用。另外Handler机制与Activity生命周期不一致的原因，容易导致内存泄漏，不推荐使用
     */
    private void one() {
        Log.d("AAA", "1" + Thread.currentThread().getName());
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 123:
                        Log.d("AAA", "2" + Thread.currentThread().getName());
                        tvText.setText("" + msg.obj);
                        break;
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Message message = Message.obtain();
                message.what = 123;
                message.obj = "通过Handler实现线程间通信";
                Log.d("AAA", "3" + Thread.currentThread().getName());
                handler.sendMessage(message);
            }
        }.start();

    }

    /***
     * 用Activity对象的runOnUiThread方法更新，在子线程中通过runOnUiThread()方法更新UI，强烈推荐使用。
     */
    private void two() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 3; i++) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvText.setText("通过RunOnUiThread方法");
                    }
                });
            }
        }.start();
    }

    /***
     * 通过View.post方式实现线程间通信
     * 这种方法更简单，但需要传递要更新的View过去,推荐使用
     */
    private void three() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (int i = 0; i < 3; i++) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                tvText.post(new Runnable() {
                    @Override
                    public void run() {
                        tvText.setText("通过View.post(Runnable r) 方法");
                    }
                });
            }
        }.start();
    }

    private void four() {
        new MyAsyncTask().execute("通过AsyncTask方法实现线程间通信");
    }

    /***
     * 第四种方式 使用AsyncTask方式
     */
    private class MyAsyncTask extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            for (int i = 0; i < 3; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return objects[0].toString();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            tvText.setText(o.toString());
        }
    }

}
