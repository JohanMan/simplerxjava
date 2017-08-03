package com.johan.simplerxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.johan.library.simplerxjava.Consumer;
import com.johan.library.simplerxjava.datafactory.DataFactory;
import com.johan.library.simplerxjava.DataProcessor;
import com.johan.library.simplerxjava.Producer;
import com.johan.library.simplerxjava.scheduler.Scheduler;
import com.johan.library.simplerxjava.scheduler.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(getClass().getName(), "origin thread name ---> " + Thread.currentThread().getName());
        Producer.create(new DataFactory<String>() {
            @Override
            public void create(Consumer<String> consumer) {
                Log.e(getClass().getName(), "create thread name ---> " + Thread.currentThread().getName());
                consumer.onNext("Hello");
                consumer.onNext("World");
                consumer.onCompleted();
            }
        }).produceOn(Schedulers.getWorkScheduler()).consumeOn(Schedulers.getMainScheduler()).add(new Consumer<String>() {
            @Override
            public void onCompleted() {
                Log.e(getClass().getName(), "completed is called");
            }
            @Override
            public void onNext(String result) {
                Log.e(getClass().getName(), "onNext thread name ---> " + Thread.currentThread().getName());
                Log.e(getClass().getName(), "course name : " + result);
            }
            @Override
            public void onError(Exception exception) {
                Log.e(getClass().getName(), "error is called");
            }
        });

    }

    class Student {
        String name;
        int age;
        String[] courses;
        public Student(String name, int age, String... courses) {
            this.name = name;
            this.age = age;
            this.courses = courses;
        }
    }

}
