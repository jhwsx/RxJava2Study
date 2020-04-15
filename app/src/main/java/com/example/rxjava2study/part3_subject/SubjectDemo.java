package com.example.rxjava2study.part3_subject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

/**
 * 与 Subject 对应的 Processor，是支持被压操作的。
 *
 * @author wzc
 * @date 2020/4/13
 */
public class SubjectDemo {
    public static void main(String[] args) {
        // asyncSubject();
//         behaviorSubject();
//         replaySubject();
        publishSubject();
    }

    private static void publishSubject() {
        /*
        A Subject that emits (multicasts) items to currently subscribed {@link Observer}s and terminal events to current
        or late {@code Observer}s.
        只能观察到注册之后发送的事件

        多个观察者，观察的是同一组事件。
         */
        PublishSubject<String> publishSubject = PublishSubject.create();
        sleep();
        publishSubject.onNext("publishSubject1 " + System.currentTimeMillis());
        sleep();
        publishSubject.onNext("publishSubject2 " + System.currentTimeMillis());
//        publishSubject.onError(new NullPointerException());
//        publishSubject.onComplete();
        publishSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("subscribe1，onNext: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("subscribe1，onError: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("subscribe1，onComplete:");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("subscribe1，onSubscribe:");
            }
        });
        publishSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("subscribe2，onNext: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("subscribe2，onError: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("subscribe2，onComplete:");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("subscribe2，onSubscribe:");
            }
        });
        publishSubject.onNext("publishSubject3 " + System.currentTimeMillis());
        sleep();
        publishSubject.onNext("publishSubject4 " + System.currentTimeMillis());
        publishSubject.onError(new NullPointerException());
        publishSubject.onComplete();

    }

    private static void sleep() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void replaySubject() {
        /*
        Replays events (in a configurable bounded or unbounded manner) to current and late {@link Observer}s.
        多个观察者，观察的是同一组事件。
         */
//        ReplaySubject<String> replaySubject = ReplaySubject.create();
        // 限制在 subscribe 之前只能缓存的事件数量，但是onError, onComplete 不记在缓存的事件数量里。
        ReplaySubject<String> replaySubject = ReplaySubject.createWithSize(1);
        replaySubject.onNext("replay1 " + System.currentTimeMillis());
        replaySubject.onNext("replay2 " + System.currentTimeMillis());
//        replaySubject.onError(new RuntimeException());
        replaySubject.onComplete();
        replaySubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("subscribe1，onNext: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("subscribe1，onError: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("subscribe1，onComplete:");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("subscribe1，onSubscribe:");
            }
        });
        replaySubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("subscribe2，onNext: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("subscribe2，onError: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("subscribe2，onComplete:");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("subscribe2，onSubscribe:");
            }
        });
    }

    private static void behaviorSubject() {
        /*
        Subject that emits the most recent item it has observed and all subsequent observed items to each subscribed {@link Observer}.
        发射观察到的最近的事件以及所有后续的事件给每一个注册的观察者的主题。
        在调用 subscribe() 之前的事件，只可以观察到一条；
        在调用 subscribe() 之后的事件，都可以观察到。
        如果在 subscribe() 之前的事件，最后一个是 onComplete 或者 onError，那么只观察到 onComplete 或 onError。

        多个观察者观察的是同一组事件。
         */
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.createDefault("behaviorSubject default " + System.currentTimeMillis());
        behaviorSubject.onNext("behaviorSubject1 " + System.currentTimeMillis());
        behaviorSubject.onNext("behaviorSubject2 " + System.currentTimeMillis());
//        behaviorSubject.onError(new NullPointerException());
        behaviorSubject.onComplete();
        behaviorSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("subscribe1, onNext: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("subscribe1, onError: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("subscribe1, onComplete: ");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("subscribe1, onSubscribe: ");
            }
        });
        behaviorSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("subscribe2, onNext: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("subscribe2, onError: " + throwable.getMessage());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("subscribe2, onComplete: ");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("subscribe2, onSubscribe: ");
            }
        });
        behaviorSubject.onNext("behaviorSubject3 " + System.currentTimeMillis());
        behaviorSubject.onNext("behaviorSubject4 " + System.currentTimeMillis());
        behaviorSubject.onComplete();
    }

    private static void asyncSubject() {
        /*
        A Subject that emits the very last value followed by a completion event or the received error to Observers.
        发射后面有完成事件的最后一个值或者错误事件的 Subject。
        注意：如果最后一个值后面没有完成事件，它不会被发送。
        如果是第一种情况，发射后面有完成事件的最后一个值，那么观察者会接收一个值和一个完成事件；
        如果是第二种情况，观察者只会接收错误事件，哪怕发射错误事件时后面有完成事件。
        还有，事件是在 subscribe() 方法之后，还是在 subscribe() 方法之前，都是没有关系的。
         */
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext("async1 " + System.currentTimeMillis());
        asyncSubject.onNext("async2 " + System.currentTimeMillis());
//        asyncSubject.onError(new NullPointerException());
        asyncSubject.onComplete();
        asyncSubject.subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("subscribe1 async: " + s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("subscribe1 async: error");
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                System.out.println("subscribe1 async: complete");
            }
        }, new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                System.out.println("subscribe1 async: onSubscribe");
            }
        });
//        asyncSubject.subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                System.out.println("subscribe2 async: " + s);
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//                System.out.println("subscribe2 async: error");
//            }
//        }, new Action() {
//            @Override
//            public void run() throws Exception {
//                System.out.println("subscribe2 async: complete");
//            }
//        }, new Consumer<Disposable>() {
//            @Override
//            public void accept(Disposable disposable) throws Exception {
//                System.out.println("subscribe2 async: onSubscribe");
//            }
//        });
//        asyncSubject.onNext("async3 ");
//        asyncSubject.onNext("async4 ");
//        asyncSubject.onComplete();
    }
}
