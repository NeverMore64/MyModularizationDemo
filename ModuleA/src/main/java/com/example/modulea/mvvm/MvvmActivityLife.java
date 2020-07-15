package com.example.modulea.mvvm;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.basemodule.utils.log.Log;
import com.example.modulea.R;

import java.util.Locale;

/**
 * create by zy on 2020/7/15
 * </p>
 */
public class MvvmActivityLife implements LifecycleObserver {

    private MvvmViewModel mainActivityViewModel;

    private boolean isAlive = true;

    private MvvmActivity mActivity;
    private TextView mTextView;

//    private Thread mThread;

    private Handler mHandler;
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            Log.d("AAA", "threadName: " + Thread.currentThread().getName());
            mainActivityViewModel.count++;
            Log.d("AAA", "onResume" + mainActivityViewModel.count);
            Message message = new Message();
            message.what = mainActivityViewModel.count;
            mHandler.sendMessage(message);
            mHandler.postDelayed(mRunnable, 1000);


//            while (isAlive) {
//                try {
//                    Thread.sleep(1000);
//                    Log.d("AAA", "threadName: " + Thread.currentThread().getName());
//                    mainActivityViewModel.count++;
//                    Log.d("AAA", "onResume" + mainActivityViewModel.count);
//                    mActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mTextView.setText(String.format(Locale.getDefault(), "%d", mainActivityViewModel.count));
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

        }
    };

    public MvvmActivityLife(MvvmViewModel mainActivityViewModel, MvvmActivity activity) {
        this.mainActivityViewModel = mainActivityViewModel;
        this.mActivity = activity;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        mTextView = mActivity.findViewById(R.id.tvCount);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextView.setText(String.format(Locale.getDefault(), "%d", mainActivityViewModel.count));
                            }
                        });
                    }
                };
                mHandler.post(mRunnable);
                Looper.loop();
            }
        }).start();

//        mThread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Thread.sleep(1000);
//                        mainActivityViewModel.count++;
//                        Log.d("AAA", "onResume" + mainActivityViewModel.count);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//        mThread.start();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
//        if (mThread != null)
//            mThread.interrupt();
        isAlive = false;
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
            mHandler = null;
        }
    }

}
