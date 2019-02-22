package com.heapixLearn.discovery;

public class RunnableWithObject<T> implements Runnable {
    private T object;
    public void init(T object){
        this.object = object;
    }
    public T getObject() {
        return object;
    }

    @Override
    public void run() {
    }
}