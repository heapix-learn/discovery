package com.heapixLearn.discovery.server;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerAnswer {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("access_token")
    private String token;
    @SerializedName("error")
    @Expose
    private String error;

    @SerializedName("has_new_post")
    @Expose
    private Boolean hasNewPost;

    public String getError() {
        return error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getHasNewPost() {
        return hasNewPost;
    }

    public void setHasNewPost(Boolean hasNewPost) {
        this.hasNewPost = hasNewPost;
    }
}