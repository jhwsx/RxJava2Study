package com.example.rxjava2study.observer;

/**
 * @author wzc
 * @date 2020/4/6
 */
public interface Subject {
    void attach(Observer observer);

    void detach(Observer observer);

    void myNotify();

    String getAction();

    void setAction(String action);
}
