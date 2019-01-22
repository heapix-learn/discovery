package com.heapixLearn.discovery;

public class RunnableWithObject<T> implements Runnable {
    private T object;
    public Runnable init(T object){
        this.object = object;
        return this;
    }

    @Override
    public void run() {
    }

    protected T getDescription() {
        return object;
    }

}
