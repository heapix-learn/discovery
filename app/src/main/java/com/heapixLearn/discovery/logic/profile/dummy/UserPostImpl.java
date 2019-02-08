package com.heapixLearn.discovery.logic.profile.dummy;

import com.heapixLearn.discovery.logic.profile.entity.UserPost;
import com.heapixLearn.discovery.logic.profile.entity.VideoItem;

import java.util.Date;
import java.util.List;

public class UserPostImpl implements UserPost {

    private int id;
    private int userId;
    private String description;
    private String title;
    private List<String> imageRefList;
    private List<VideoItem> videoList;
    private double lat;
    private double lng;
    private Date date;

    public UserPostImpl(int id, int userId, String description, String title,
                        List<String> imageRefList, List<VideoItem> videoList,
                        double lat, double lng, Date date) {
        this.id = id;
        this.userId = userId;
        this.description = description;
        this.title = title;
        this.imageRefList = imageRefList;
        this.videoList = videoList;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public List<String> getImageRefList() {
        return imageRefList;
    }

    @Override
    public List<VideoItem> getVideoList() {
        return videoList;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public double getLat() {
        return lat;
    }

    @Override
    public double getLng() {
        return lng;
    }

}
