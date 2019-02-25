package com.heapixLearn.discovery.Entity;

public abstract class RunnableWithObject<T> implements Runnable {
    private T object;

    public void init(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }
}

