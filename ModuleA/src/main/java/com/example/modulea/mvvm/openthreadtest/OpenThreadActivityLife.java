package com.example.modulea.mvvm.openthreadtest;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.example.basemodule.utils.log.Log;
import com.example.modulea.R;

/**
 * create by zy on 2020/7/15
 * Android中开启线程的4中方式
 */
public class OpenThreadActivityLife implements LifecycleObserver {

    private OpenThreadActivity mActivity;
    private OpenThreadModel mViewModel;

    private MyHandler mHandler;

    private Button mBtnStart, mBtnCancel;
    private ProgressBar mProgressBar;
    private TextView mTvStatus;

    private int mCount = 0;

    private MyThread mThread;

    public OpenThreadActivityLife(OpenThreadActivity activity, OpenThreadModel model) {
        this.mActivity = activity;
        this.mViewModel = model;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {

        initView();

        // 继承Thread方式开启一个线程
//        extendsThreadWay();

        // 实现Runnable接口方式开启一个线程
//        implementsRunnableWay();


        // Handler 方式
        handlerWay();


        // AsyncTask 方式
//        asyncTaskWay();

    }


    /*-----------------------------------------------------------继承Thread方式-------------------------------------------------------------------------**/
    private void extendsThreadWay() {
        mThread = new MyThread();
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnStart.setEnabled(false);
                mThread.start();
            }
        });
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThread.interrupt();
                mTvStatus.setText("已取消");
            }
        });
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Log.d("AAA", "当前线程：" + Thread.currentThread().getName());
                Log.d("AAA", "继承Thread 方式开启线程");
                int length = 1;
                do {
                    mCount += length;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mCount);
                            mTvStatus.setText("loading..." + mCount + "%");
                        }
                    });
                    Thread.sleep(50);
                } while (mCount < 99);
                {
                    mThread.interrupt();
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mCount);
                            mTvStatus.setText("已完成");
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /*-----------------------------------------------------------继承Thread方式 end-------------------------------------------------------------------------**/


    /*-----------------------------------------------------------实现Runnable方式-------------------------------------------------------------------------**/
    private void implementsRunnableWay() {
        new Thread(new MyRunnable()).start();
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            Log.d("AAA", "当前线程：" + Thread.currentThread().getName());
            Log.d("AAA", "实现Runnable接口方式开启线程");
        }
    }
    /*-----------------------------------------------------------实现Runnable方式 end-------------------------------------------------------------------------**/


    /*-----------------------------------------------------------Handler方式-------------------------------------------------------------------------**/
    private void handlerWay() {

        // 子线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Log.d("AAA", "当前线程   run：" + Thread.currentThread().getName());


                mHandler = new MyHandler() {
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        Log.d("AAA", "handleMessage" + msg.what);
                        if (mCount <= 99) {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(mCount);
                                    mTvStatus.setText("loading..." + mCount + "%");
                                }
                            });
                        } else {
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTvStatus.setText("已完成");
                                }
                            });
                            mHandler.removeCallbacks(myRunnable);
                        }
                    }
                };
                mHandler.post(myRunnable);
                Looper.loop();
            }
        }.start();


//        // 主线程
//        mHandler = new MyHandler();
//        mHandler.post(myRunnable);

    }

    private Runnable myRunnable = new Runnable() {
        @Override
        public void run() {
            int length = 1;
            mCount += length;
            Message message = Message.obtain();
            message.what = mCount;
            mHandler.sendMessage(message);
            mHandler.postDelayed(myRunnable, 50);


            // 这种写法不行 因为 handler.post(runnable) 是往消息队列里放了一个事件， 这个runnable在队列里都是一个事件，
            // 在下面的do循环中 handler.sendMessage(message) 方法 和runnable事件是并列关系 所以会等整个runnable执行完 才会执行发送操作
            // 所以下面会执行完 才会执行上面的handleMessage 方法
//            try {
//                Log.d("AAA", "当前线程：" + Thread.currentThread().getName());
//                int length = 1;
//                do {
//                    Message message = Message.obtain();
//                    mCount += length;
//                    message.what = mCount;
//                    Log.d("AAA", "handler 准备发送消息");
//                    mHandler.sendMessage(message);
//                    Log.d("AAA", "handler 已经发送消息");
//                    Thread.sleep(50);
//                } while (mCount < 99);
//                {
//                    mActivity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            mTvStatus.setText("已完成");
//                        }
//                    });
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    };

    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        if (mHandler != null) {
            mHandler.removeCallbacks(myRunnable);
            mHandler = null;
        }
    }
    /*-----------------------------------------------------------Handler方式 end-------------------------------------------------------------------------**/


    /*-----------------------------------------------------------AsyncTask方式 -------------------------------------------------------------------------**/

    private void initView() {
        mBtnStart = mActivity.findViewById(R.id.button);
        mBtnCancel = mActivity.findViewById(R.id.cancel);
        mProgressBar = mActivity.findViewById(R.id.progress_bar);
        mTvStatus = mActivity.findViewById(R.id.text);
    }

    private void asyncTaskWay() {
        final MyTask myTask = new MyTask();
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask.execute();
                mBtnStart.setEnabled(false);
            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask.cancel(true);
            }
        });
    }

    class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mTvStatus.setText("加载中…………");
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                int count = 0;
                int length = 1;
                while (count < 99) {
                    count += length;
                    publishProgress(count);
                    Thread.sleep(50);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
            mTvStatus.setText("loading..." + values[0] + "%");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mTvStatus.setText("加载完毕");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            mTvStatus.setText("已取消");
            mProgressBar.setProgress(0);
        }
    }

    /*-----------------------------------------------------------AsyncTask方式 end-------------------------------------------------------------------------**/

}
