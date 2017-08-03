package com.johan.library.simplerxjava.scheduler;

/**
 * Created by johan on 2017/8/3.
 * 线程调度接口
 */

public interface Scheduler {
    void schedule(Runnable command);
}
