package com.heapixLearn.discovery.entity;

public class VideoItem {
    String videoURL;
    String thumbnailURL;
    String duration;

    public String getDuration() {
        return duration;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}
