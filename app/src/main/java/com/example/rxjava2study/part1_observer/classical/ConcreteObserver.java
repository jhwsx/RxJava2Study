package com.example.rxjava2study.part1_observer.classical;

/**
 * @author wzc
 * @date 2020/4/6
 */
public class ConcreteObserver extends Observer {
    private String name;
    private Subject subject;
    public ConcreteObserver(String name, Subject subject) {
        this.name = name;
        this.subject = subject;
    }
    @Override
    public void update() {
        System.out.println("观察到：" + subject.getAction());
    }
}
