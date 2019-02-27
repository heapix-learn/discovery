package com.heapixLearn.discovery.ui.post.preview.entity;

import java.util.List;

public interface IPost {
    long getId();
    String getTitle();
    long getAccountId();
    String getNameLocation();
    List<String> getPhotos();
    List<VideoItem> getVideos();

    int getLikes();
    int getComments();
}
