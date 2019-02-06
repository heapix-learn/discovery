package com.heapixLearn.discovery.ui.map;

public class Post {
    private int id;

    public void showPost(){

    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((Post)obj).getId();
    }
}
