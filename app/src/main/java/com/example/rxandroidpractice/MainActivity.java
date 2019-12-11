package com.example.rxandroidpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscriber;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Andy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mObservable.subscribe(observer);

        // normal
        Log.d("Andy", "Type 1-> normal");
        mObservable.subscribe(observer);
        // just
        Log.d("Andy", "Type 2 -> just");
        Observable observable = Observable.just("Hello", "World");
        observable.subscribe(observer);
        // from
        Log.d("Andy", "Type 3 -> from");
        String[] list = {"Hello", "World"};
        Observable observable1 = Observable.fromArray(list);
        observable1.subscribe(observer);
        // Action0, Action1
        Log.d("Andy", "Type 4 -> Action, Consumer");
        mObservable.subscribe(
                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Log.d(TAG, "onNext data is :" + s);
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                },
                new Action() {
                    @Override
                    public void run() {
                        Log.d(TAG, "onCompleteAction");
                    }
                });
        // Mix
        Log.d("Andy", "Type 5 -> Mix");
        Observable.just("Hello", "World").subscribe(
                new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Log.d(TAG, "onNext data is :" + s);
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {

                    }
                },
                new Action() {
                    @Override
                    public void run() {
                        Log.d(TAG, "onCompleteAction");
                    }
                });

        // Map
        Log.d("Andy", "Type 6 -> Map");
        Student Amy = new Student("Amy", 0);
        Student Guy = new Student("Guy", 1);
        Observable.just(Amy, Guy)
                .map(new Function<Student, String>() {
                    @Override
                    public String apply(Student student) {
                        return student.name;
                    }
                })
//                .map(new Function<String, Integer>() {
//                    @Override
//                    public Integer apply(String s) {
//                        return s.hashCode();
//                    }
//                })
                .subscribe(observer);
    }

    Observer observer = new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            Log.d(TAG, "onSubscribe");
        }


        @Override
        public void onNext(@NonNull String s) {
            Log.d(TAG, "onNext data is :" + s);

        }

        @Override
        public void onError(@NonNull Throwable e) {
            Log.d(TAG, "onError data is :" + e.toString());
        }

        @Override
        public void onComplete() {
            Log.d(TAG, "onComplete");
        }
    };

    Observable<String> mObservable = Observable.create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(ObservableEmitter<String> emitter) throws Exception {
            emitter.onNext("Hello");
            emitter.onNext("World");
            emitter.onComplete();
        }
    });

    static class Student {
        String name;
        int id;
        Student(String name, int id) {
            this.name = name;
            this.id = id;
        }
    }
}
