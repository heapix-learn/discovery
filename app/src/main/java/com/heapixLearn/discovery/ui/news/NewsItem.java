package com.heapixLearn.discovery.ui.news;

import com.heapixLearn.discovery.entity.VideoItem;

import java.util.Date;
import java.util.List;

public class NewsItem {
    private String description;
    private String title;
    private List<String> photos;
    private List<VideoItem> videoItems;
    private Date date;
    private String avatar;
    private String nameOfOwner;

    public List<VideoItem> getVideoItems() {
        return videoItems;
    }

    public void setVideoItems(List<VideoItem> videoItems) {
        this.videoItems = videoItems;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNameOfOwner() {
        return nameOfOwner;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setNameOfOwner(String nameOfOwner) {
        this.nameOfOwner = nameOfOwner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
