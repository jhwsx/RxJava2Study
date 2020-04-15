package com.example.rxjava2study;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.operators.observable.ObservableObserveOn;
import io.reactivex.internal.operators.observable.ObservableSubscribeOn;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 装饰器模式的应用
        // 自定义操作符需要 extends AbstractObservableWithUpstream<T, U>，查看 map 源码。
        // 1，创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello RxJava");
                emitter.onNext("Hello World");
                emitter.onComplete();
            }
        });
        // 2，创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: s = " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: e", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
        // 3, 线程切换
        ObservableSubscribeOn observableSubscribeOn = (ObservableSubscribeOn) observable.subscribeOn(Schedulers.io());
        ObservableObserveOn observableObserveOn = (ObservableObserveOn) observableSubscribeOn .observeOn(AndroidSchedulers.mainThread());
                // 4, 订阅
        observableObserveOn.subscribe(observer);
    }
}
