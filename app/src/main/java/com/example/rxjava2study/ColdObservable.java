package com.example.rxjava2study;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;

/**
 * @author wzc
 * @date 2020/3/25
 */
public class ColdObservable {
    public static void main(String[] args) {
//         testInterval();
//         testJust();
//         testRange();
         testCreate();
//        testFromArray();
    }

    private static void testFromArray() {
        Observable observable = Observable.fromArray(1L, 2L, 3L, 4L, 5L);
        common(observable);
    }

    private static void testCreate() {
        Observable observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> emitter) throws Exception {
                System.out.println(emitter.hashCode());
                Thread.sleep(10);
                emitter.onNext(System.currentTimeMillis());
                Thread.sleep(10);
                emitter.onNext(System.currentTimeMillis());
                Thread.sleep(10);
                emitter.onNext(System.currentTimeMillis());
                Thread.sleep(10);
                emitter.onNext(System.currentTimeMillis());
                Thread.sleep(10);
                emitter.onNext(System.currentTimeMillis());
            }
        });
        common(observable);
    }

    private static void testRange() {
        Observable<Long> observable =  Observable.rangeLong(1, 5);
        common(observable);
    }

    private static void testJust() {
        Observable<Long> observable = Observable.just(1L, 2L, 3L, 4L, 5L);
        common(observable);
    }

    private static void testInterval() {
        Observable<Long> observable = Observable.interval(10, TimeUnit.MILLISECONDS) // 每隔一段时间发送一个事件
                .take(5);// 这里是指发射器最多发射的数量

        common(observable);
    }

    private static void common(Observable<Long> observable) {
        observable.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("First: " + aLong + ", time=" + System.currentTimeMillis());
            }
        });
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        observable.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("  Second: " + aLong + ", time=" + System.currentTimeMillis());
            }
        });
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        observable.subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println("    Third: " + aLong + ", time=" + System.currentTimeMillis());
            }
        });
        // 休眠 2000 ms, 保证事件接收完成打印
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
