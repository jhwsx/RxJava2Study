package com.example.rxjava2study.observer;


import java.util.ArrayList;
import java.util.List;

/**
 * @author wzc
 * @date 2020/4/6
 */
public class ConcreteSubject implements Subject {
    List<Observer> list = new ArrayList<>();
    private String action;
    @Override
    public void attach(Observer observer) {
        list.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        list.remove(observer);
    }

    @Override
    public void myNotify() {
        for (Observer observer : list) {
            observer.update();
        }
    }

    @Override
    public String getAction() {
        return action;
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }
}
