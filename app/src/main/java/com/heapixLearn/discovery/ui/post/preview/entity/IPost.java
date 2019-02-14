package com.heapixLearn.discovery.ui.post.preview.entity;

import android.location.Location;
import android.net.Uri;

import java.util.Date;
import java.util.List;

public interface IPost {
    long getId();
    String getTitle();
    long getAccountId();
    String getNameLocation();
    List<String> getPhotos();
    List<String> getVideos();

    int getLikes();
    int getComments();
}
