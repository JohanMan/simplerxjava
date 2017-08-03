package com.johan.library.simplerxjava.scheduler;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by johan on 2017/8/3.
 * 主线程调度器
 */

public class MainScheduler implements Scheduler {

    private static Handler handler;

    @Override
    public void schedule(Runnable command) {
        getWorker().post(command);
    }

    private static Handler getWorker() {
        synchronized (MainScheduler.class) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            return handler;
        }
    }

}
