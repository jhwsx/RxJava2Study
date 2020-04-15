package com.example.rxjava2study.part1_observer.classical;

/**
 * @author wzc
 * @date 2020/4/6
 */
public class Client {
    public static void main(String[] args) {
        ConcreteSubject subject = new ConcreteSubject();

        ConcreteObserver concreteObserver = new ConcreteObserver("observer1", subject);

        subject.attach(concreteObserver);

        subject.setAction("发出通知");

        subject.myNotify();

    }
}
