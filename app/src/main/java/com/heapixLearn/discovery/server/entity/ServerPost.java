package com.heapixLearn.discovery.server.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerPost {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("user_id")
    @Expose
    private String user_id;

    @SerializedName("remove_id")
    @Expose
    private String remove_id;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("img_ref_list")
    @Expose
    private String[] img_ref_list;

    @SerializedName("video_ref_list")
    @Expose
    private VideoItem[] video_ref_list;

    @SerializedName("date")
    @Expose
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRemove_id() {
        return remove_id;
    }

    public void setRemove_id(String remove_id) {
        this.remove_id = remove_id;
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

    public String[] getImg_ref_list() {
        return img_ref_list;
    }

    public void setImg_ref_list(String[] img_ref_list) {
        this.img_ref_list = img_ref_list;
    }

    public VideoItem[] getVideo_ref_list() {
        return video_ref_list;
    }

    public void setVideo_ref_list(VideoItem[] video_ref_list) {
        this.video_ref_list = video_ref_list;
    }
}
