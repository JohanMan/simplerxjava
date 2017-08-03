package com.johan.library.simplerxjava.datafactory;

import com.johan.library.simplerxjava.Consumer;

/**
 * Created by johan on 2017/8/2.
 * 数据工厂
 */

public interface DataFactory <T> {
    void create(Consumer<T> consumer);
}
