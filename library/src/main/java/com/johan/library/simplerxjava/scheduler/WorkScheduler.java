package com.johan.library.simplerxjava.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by johan on 2017/8/3.
 * 工作（异步）线程调度器
 */

public class WorkScheduler implements Scheduler {

    private static ExecutorService executorService;

    @Override
    public void schedule(Runnable command) {
        getWorker().execute(command);
    }

    private ExecutorService getWorker() {
        synchronized (WorkScheduler.class) {
            if (executorService == null) {
                executorService = Executors.newCachedThreadPool();
            }
            return executorService;
        }
    }

}
