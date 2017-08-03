package com.johan.library.simplerxjava.datafactory;

import com.johan.library.simplerxjava.Consumer;
import com.johan.library.simplerxjava.Producer;

/**
 * Created by johan on 2017/8/2.
 */

public class FlapMapDataFactory <R> implements DataFactory <R> {

    private DataFactory<Producer<R>> dataFactory;

    public FlapMapDataFactory(DataFactory<Producer<R>> dataFactory) {
        this.dataFactory = dataFactory;
    }

    @Override
    public void create(Consumer<R> consumer) {
        FlapMapConsumer flapMapConsumer = new FlapMapConsumer(consumer);
        dataFactory.create(flapMapConsumer);
    }

    public class FlapMapConsumer <R> implements Consumer<Producer<R>> {

        private Consumer<R> source;

        public FlapMapConsumer(Consumer<R> source) {
            this.source = source;
        }

        @Override
        public void onCompleted() {
            source.onCompleted();
        }

        @Override
        public void onNext(Producer<R> result) {
            result.add(source);
        }

        @Override
        public void onError(Exception exception) {
            source.onError(exception);
        }

    }

}
