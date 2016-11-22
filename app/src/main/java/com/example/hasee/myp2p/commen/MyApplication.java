package com.example.hasee.myp2p.commen;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by lzq on 2016/11/13.
 */
public class MyApplication extends Application {

    public static Context context;
    public static Handler handler;
    public static Thread mainThread;//获取主线程
    public static int mainThreadId;//获取主线程的id

    @Override
    public void onCreate() {
        super.onCreate();

        context = this.getApplicationContext();
        handler = new Handler();
        mainThread = Thread.currentThread();//当前用于初始化Application的线程，即为主线程
        mainThreadId = android.os.Process.myTid();//获取当前主线程的id

        //设置出现未捕获异常时的处理类
//        CrashHandler.getInstance().init();
    }
}
