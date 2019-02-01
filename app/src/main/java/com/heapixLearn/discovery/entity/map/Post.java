package com.heapixLearn.discovery.entity.map;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post implements PostInterface{
    private String title;
    private long accountId;
    private Date createDate;
    private String description;
    private Location location;
    private int access;
    private List<Uri> photos;
    private List<Uri> videos;
    private String nameLocation;

    public Post(){
        title = "";
        accountId = -1;
        createDate = new Date();
        description = "";
        location = new Location();
        access = 0;
        photos = new ArrayList<>();
        videos = new ArrayList<>();
        nameLocation = "";
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setVideos(List<Uri> videos) {
        this.videos = videos;
    }

    @Override
    public void setPhotos(List<Uri> photos) {
        this.photos = photos;
    }

    @Override
    public void setLocation(double latitude, double longitude) {
        this.location = new Location(latitude, longitude);
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public long getAccountId() {
        return accountId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public List<Uri> getPhotos() {
        return photos;
    }

    @Override
    public List<Uri> getVideos() {
        return videos;
    }

    @Override
    public Date getCreateDate() {
        return createDate;
    }

    @Override
    public int getAccess() {
        return access;
    }

    @Override
    public String getNameLocation() {
        return nameLocation;
    }
    @Override
    public void setAccess(int access) {
        this.access = access;
    }

    @Override
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    @Override
    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    @Override
    public void setPhoto(Uri photo){
        photos.add(photo);
    }

    @Override
    public void setVideo(Uri video){
        videos.add(video);
    }
}
