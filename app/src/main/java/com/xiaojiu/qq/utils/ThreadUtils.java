package com.xiaojiu.qq.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 作者：${xiaojiukeji} on 17/1/2 23:18
 * 作用:
 */

public class ThreadUtils {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Executor executors = Executors.newSingleThreadExecutor();
    public static void runOnSubThread(Runnable runnable){
        executors.execute(runnable);
    }
    public static void runOnMainThread(Runnable runnable){
        handler.post(runnable);
    }
}
