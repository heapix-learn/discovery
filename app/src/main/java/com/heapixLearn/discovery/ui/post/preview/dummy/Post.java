package com.heapixLearn.discovery.ui.post.preview.dummy;

import android.location.Location;
import android.net.Uri;

import com.heapixLearn.discovery.ui.post.preview.entity.IPost;

import java.util.Date;
import java.util.List;

public class Post implements IPost {
    private long id;
    private String title;
    private long accountId;

    private List<String> photos;
    private List<String> videos;
    private String nameLocation;

    private int likes;
    private int comments;


    public Post(String title, long accountId, List<String> photos, List<String> videos,
                String nameLocation, int likes, int comments, boolean isLiked, boolean isFollower) {
        this.title = title;
        this.accountId = accountId;
        this.photos = photos;
        this.videos = videos;
        this.nameLocation = nameLocation;
        this.likes = likes;
        this.comments = comments;
    }

    @Override
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public long getAccountId() {
        return accountId;
    }


    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
