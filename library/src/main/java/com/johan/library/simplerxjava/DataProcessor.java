package com.johan.library.simplerxjava;

/**
 * Created by johan on 2017/8/2.
 * 数据加工接口
 */

public interface DataProcessor<T, R> {
    R process(T data);
}
