package com.johan.library.simplerxjava;

/**
 * Created by johan on 2017/8/2.
 * 消费者
 */

public interface Consumer <T> {

    void onCompleted();

    void onNext(T result);

    void onError(Exception exception);

}
