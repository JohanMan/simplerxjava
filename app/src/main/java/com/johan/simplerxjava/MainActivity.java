package com.johan.simplerxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.johan.library.simplerxjava.Consumer;
import com.johan.library.simplerxjava.DataFactory;
import com.johan.library.simplerxjava.DataProcessor;
import com.johan.library.simplerxjava.Producer;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Producer.create(new DataFactory<Student>() {
            @Override
            public void create(Consumer<Student> consumer) {
                Student student1 = new Student("小明", 20, "高数", "线性代数", "统计学");
                Student student2 = new Student("晓晓", 21, "英语", "电子信息技术", "统计学");
                consumer.onNext(student1);
                consumer.onNext(student2);
                consumer.onCompleted();
            }
        }).flapMap(new DataProcessor<Student, Producer<String>>() {
            @Override
            public Producer<String> process(Student data) {
                return Producer.from(data.courses);
            }
        }).add(new Consumer<String>() {
            @Override
            public void onCompleted() {
                Log.e(getClass().getName(), "completed is called");
            }
            @Override
            public void onNext(String result) {
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
