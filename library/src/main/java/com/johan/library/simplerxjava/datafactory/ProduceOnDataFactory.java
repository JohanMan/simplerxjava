package com.johan.library.simplerxjava.datafactory;

import com.johan.library.simplerxjava.Consumer;
import com.johan.library.simplerxjava.scheduler.Scheduler;

/**
 * Created by johan on 2017/8/3.
 */

public class ProduceOnDataFactory <T> implements DataFactory <T> {

    private DataFactory<T> source;
    private Scheduler scheduler;

    public ProduceOnDataFactory(DataFactory<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    public void create(final Consumer<T> consumer) {
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                source.create(consumer);
            }
        });
    }

}
