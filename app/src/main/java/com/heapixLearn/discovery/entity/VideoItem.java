package com.heapixLearn.discovery.entity;

import android.graphics.Bitmap;

public class VideoItem {
    private boolean isCached;
    private String videoRef;
    private String videoUri;
    private int duration;
    private String thumbnailRef;
    private Bitmap thumbnail;

    public VideoItem(String videoUri, int duration, Bitmap thumbnail) {
        this.videoUri = videoUri;
        this.duration = duration;
        this.thumbnail = thumbnail;
        isCached = true;
    }

    public VideoItem(String videoRef, int duration, String thumbnailRef) {
        this.videoRef = videoRef;
        this.duration = duration;
        this.thumbnailRef = thumbnailRef;
        isCached = false;
    }

    public boolean isCached() {
        return isCached;
    }

    public void setCached(boolean cached) {
        isCached = cached;
    }

    public String getVideoRef() {
        return videoRef;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public int getDuration() {
        return duration;
    }

    public String getThumbnailRef() {
        return thumbnailRef;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }
}
