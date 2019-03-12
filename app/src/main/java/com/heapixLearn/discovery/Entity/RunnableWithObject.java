package com.heapixLearn.discovery.Entity;

public abstract class RunnableWithObject<T> implements Runnable {
    private T object;

    public RunnableWithObject<T> init(T object) {
        this.object = object;
        return this;
    }

    public T getObject() {
        return object;
    }
}

