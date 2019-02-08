package com.heapixLearn.discovery.logic.profile.entity;

import android.graphics.Bitmap;

public interface VideoItem {
    boolean isCached();
    String getVideoRef();
    String getVideoUri();
    int getDuration();
    String getThumbnailRef();
    Bitmap getThumbnail();
}
