package com.johan.library.simplerxjava.scheduler;

/**
 * Created by johan on 2017/8/3.
 * 总调度器
 */

public class  Schedulers {

    private static class MainSchedulerHolder {
        private static final MainScheduler INSTANCE = new MainScheduler();
    }

    private static class WorkSchedulerHolder {
        private static final WorkScheduler INSTANCE = new WorkScheduler();
    }

    public static Scheduler getMainScheduler() {
        return MainSchedulerHolder.INSTANCE;
    }

    public static Scheduler getWorkScheduler() {
        return WorkSchedulerHolder.INSTANCE;
    }

}
