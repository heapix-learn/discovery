package com.heapixLearn.discovery;

public class RunnableWithObject<T> implements Runnable {
    T description;
    public Runnable init(T object){
        this.description = object;
        return this;
    }

    @Override
    public void run() {
    }
}