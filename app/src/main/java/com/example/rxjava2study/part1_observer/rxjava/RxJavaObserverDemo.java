package com.example.rxjava2study.part1_observer.rxjava;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author wzc
 * @date 2020/4/15
 */
public class RxJavaObserverDemo {
    public static void main(String[] args) {
        // 创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello RxJava");
                emitter.onNext("Hello World");
                emitter.onComplete();
            }
        });
        // 创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                System.out.println("onNext: s = " + s);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: e"+ e);
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: ");
            }
        };
        // 订阅
        observable.subscribe(observer);
    }
}
