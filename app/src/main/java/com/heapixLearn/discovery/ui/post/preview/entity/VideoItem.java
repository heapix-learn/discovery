package com.heapixLearn.discovery.ui.post.preview.entity;

public class VideoItem {
    private String videoURL;
    private String thumbnailURL;
    private String duration;

    public VideoItem(String videoURL, String thumbnailURL, String duration) {
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
        this.duration = duration;
    }

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
