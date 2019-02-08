package com.heapixLearn.discovery.logic.profile.entity;

import java.util.Date;
import java.util.List;

public interface UserPost {
    int getId();
    int getUserId();
    String getDescription();
    String getTitle();
    List<String> getImageRefList();
    List<VideoItem> getVideoList();
    Date getDate();
    double getLat();
    double getLng();
}
