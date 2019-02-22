package com.heapixLearn.discovery.server.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoItem {
    @SerializedName("video_url")
    @Expose
    private String videoURL;

    @SerializedName("thumbnail_url")
    @Expose
    private String thumbnailURL;

    @SerializedName("duration")
    @Expose
    private String duration;

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
