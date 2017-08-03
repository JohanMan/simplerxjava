package com.johan.library.simplerxjava;

import com.johan.library.simplerxjava.datafactory.ConsumeOnDataFactory;
import com.johan.library.simplerxjava.datafactory.DataFactory;
import com.johan.library.simplerxjava.datafactory.FlapMapDataFactory;
import com.johan.library.simplerxjava.datafactory.MapDataFactory;
import com.johan.library.simplerxjava.datafactory.ProduceOnDataFactory;
import com.johan.library.simplerxjava.scheduler.Scheduler;

/**
 * Created by johan on 2017/8/2.
 * 生产者
 */

public class Producer <T> {

    protected DataFactory<T> dataFactory;

    private Producer(DataFactory<T> dataFactory) {
        this.dataFactory = dataFactory;
    }

    public static <T> Producer<T> create(DataFactory<T> dataFactory) {
        return new Producer<>(dataFactory);
    }

    public static <T> Producer<T> from(final T... dataSource) {
        return new Producer<>(new DataFactory<T>() {
            @Override
            public void create(Consumer<T> consumer) {
                for (T data : dataSource) {
                    consumer.onNext(data);
                }
            }
        });
    }

    public void add(Consumer<T> producer) {
        dataFactory.create(producer);
    }

    public <R> Producer<R> map(DataProcessor<T, R> processor) {
        return new Producer<>(new MapDataFactory<>(this, processor));
    }

    public <R> Producer<R> flapMap(DataProcessor<T, Producer<R>> processor) {
        Producer<Producer<R>> mapProducer = map(processor);
        return new Producer<>(new FlapMapDataFactory<>(mapProducer.dataFactory));
    }

    public Producer<T> produceOn(Scheduler scheduler) {
        return new Producer<>(new ProduceOnDataFactory<>(dataFactory, scheduler));
    }

    public Producer<T> consumeOn(Scheduler scheduler) {
        return new Producer<>(new ConsumeOnDataFactory<>(this, scheduler));
    }

}
