package com.heapixLearn.discovery.Entity;

import java.util.List;

public class Post implements Comparable<Post> {
    private int id;
    private int access;
    private int userId;
    private String description;
    private String title;
    private List<String> imgRefList;
    private List<VideoItem> videoList;
    private double lat;
    private double lng;
    private int date;

    public Post(
            int access, int userId, String description, String title,
            List<String> imgRefList, List<VideoItem> videoList, double lat, double lng, int date) {
        this.access = access;
        this.userId = userId;
        this.description = description;
        this.title = title;
        this.imgRefList = imgRefList;
        this.videoList = videoList;
        this.lat = lat;
        this.lng = lng;
        this.date = date;
    }

    @Override
    public int compareTo(Post o) {
        return o.getDate() - date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImgRefList() {
        return imgRefList;
    }

    public void setImgRefList(List<String> imgRefList) {
        this.imgRefList = imgRefList;
    }

    public List<VideoItem> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoItem> videoList) {
        this.videoList = videoList;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }
}
