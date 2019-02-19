package com.heapixLearn.discovery.ui.map;

import android.content.Context;
import android.view.View;

public class PostPreview {
    private Context context;

    public PostPreview(Context context) {
        this.context = context;
    }

    public void showPreview(int postId) {

    }

    public View getPreview(int postId) {
        return new View(context);
    }
}
