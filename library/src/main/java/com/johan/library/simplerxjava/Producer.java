package com.johan.library.simplerxjava;

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

}
