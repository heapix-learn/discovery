package com.heapixLearn.discovery.ui.map;

public class Post {
    private int id;


    public void showPost(){
        PostPreviewI postPreview = null;
        postPreview.showPostPreview();
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        return id == ((Post)obj).getId();
    }
}
