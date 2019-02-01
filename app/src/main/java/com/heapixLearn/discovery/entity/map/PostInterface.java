package com.heapixLearn.discovery.entity.map;

import android.net.Uri;

import java.util.Date;
import java.util.List;

public interface PostInterface {
    void setTitle(String title);

    void setVideos(List<Uri> videos);

    void setPhotos(List<Uri> photos);

    void setLocation(double latitude, double longitude);

    void setLocation(Location location);

    void setDescription(String description);

    String getTitle();

    long getAccountId();

    String getDescription();

    Location getLocation();

    List<Uri> getPhotos();

    List<Uri> getVideos();

    Date getCreateDate();

    int getAccess();

    String getNameLocation();

    void setAccess(int access);

    void setAccountId(long accountId);

    void setNameLocation(String nameLocation);

    void setPhoto(Uri photo);

    void setVideo(Uri video);
}
