package com.heapixLearn.discovery.Entity;

import android.location.Location;

import java.util.List;

public class ViewablePost implements Comparable<ViewablePost> {
    private int id;
    private int remoteID;
    private int access;
    private int userId;
    private String description;
    private String title;
    private List<String> imgRefList;
    private List<String> videoRefList;
    private Location location;

    private static final int ACCESS_PRIVATE = 0;
    private static final int ACCESS_PUBLIC = 1;
    private static final int ACCESS_GLOBAL = 2;

    public ViewablePost(int id, int access, int userId, String description, String title, List<String> imgRefList, List<String> videoRefList, Location location) {
        this.id = id;
        this.access = access;
        this.userId = userId;
        this.description = description;
        this.title = title;
        this.imgRefList = imgRefList;
        this.videoRefList = videoRefList;
        this.location = location;
    }

    @Override
    public int compareTo(ViewablePost o) {
        return o.getRemoteID() - remoteID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRemoteID() {
        return remoteID;
    }

    public void setRemoteID(int remoteID) {
        this.remoteID = remoteID;
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

    public List<String> getVideoRefList() {
        return videoRefList;
    }

    public void setVideoRefList(List<String> videoRefList) {
        this.videoRefList = videoRefList;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
