package com.johan.library.simplerxjava.datafactory;

import com.johan.library.simplerxjava.Consumer;
import com.johan.library.simplerxjava.Producer;
import com.johan.library.simplerxjava.scheduler.Scheduler;

/**
 * Created by johan on 2017/8/3.
 */

public class ConsumeOnDataFactory <T> implements DataFactory <T> {

    private Producer<T> source;
    private Scheduler scheduler;

    public ConsumeOnDataFactory(Producer<T> source, Scheduler scheduler) {
        this.source = source;
        this.scheduler = scheduler;
    }

    @Override
    public void create(Consumer<T> consumer) {
        ScheduleConsumer scheduleConsumer = new ScheduleConsumer(consumer, scheduler);
        source.add(scheduleConsumer);
    }

    public class ScheduleConsumer <T> implements Consumer <T> {

        private Consumer<T> source;
        private Scheduler scheduler;

        public ScheduleConsumer(Consumer<T> source, Scheduler scheduler) {
            this.source = source;
            this.scheduler = scheduler;
        }

        @Override
        public void onCompleted() {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    source.onCompleted();
                }
            });
        }
        @Override
        public void onNext(final T result) {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    source.onNext(result);
                }
            });
        }
        @Override
        public void onError(final Exception exception) {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    source.onError(exception);
                }
            });
        }
    }

}
