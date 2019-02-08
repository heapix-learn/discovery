package com.heapixLearn.discovery.server.map_item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerMapItem {
    @SerializedName("user_id")
    @Expose
    private String userId;

    @SerializedName("post_id")
    @Expose
    private String postId;

    @SerializedName("location")
    @Expose
    private String location;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
