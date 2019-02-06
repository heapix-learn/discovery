package com.heapixLearn.discovery.ui.map;

public class MapItem {
    private int id;
    private int postId;
    private int userId;
    private double lat;
    private double lng;

    public MapItem(int id, int postId, int userId, double lat, double lng) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
