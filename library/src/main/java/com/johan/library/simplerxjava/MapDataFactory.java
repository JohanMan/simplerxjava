package com.johan.library.simplerxjava;

/**
 * Created by johan on 2017/8/2.
 */

public class MapDataFactory <T, R> implements DataFactory <R> {

    private Producer<T> source;
    private DataProcessor<T, R> processor;

    public MapDataFactory(Producer<T> source, DataProcessor<T, R> processor) {
        this.source = source;
        this.processor = processor;
    }

    @Override
    public void create(Consumer<R> consumer) {
        MapConsumer<T, R> mapConsumer = new MapConsumer<>(consumer, processor);
        source.add(mapConsumer);
    }

    public class MapConsumer<T, R> implements Consumer <T> {

        private Consumer<R> source;
        private DataProcessor<T, R> processor;

        public MapConsumer(Consumer<R> source, DataProcessor<T, R> processor) {
            this.source = source;
            this.processor = processor;
        }

        @Override
        public void onCompleted() {
            source.onCompleted();
        }

        @Override
        public void onNext(T result) {
            try {
                R processResult = processor.process(result);
                source.onNext(processResult);
            } catch (Exception e) {
                onError(e);
            }
        }

        @Override
        public void onError(Exception exception) {
            source.onError(exception);
        }

    }

}
