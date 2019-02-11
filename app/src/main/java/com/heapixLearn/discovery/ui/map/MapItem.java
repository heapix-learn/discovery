package com.heapixLearn.discovery.ui.map;

public class MapItem {
    private int id;
    private int postId;
    private int userId;
    private double lat;
    private double lng;
    private byte access;

    public static final byte ACCESS_PRIVATE = 0;
    public static final byte ACCESS_PUBLIC = 1;
    public static final byte ACCESS_GLOBAL = 2;

    public MapItem(int id, int postId, int userId, double lat, double lng, byte access) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.lat = lat;
        this.lng = lng;
        this.access = access;
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

    public byte getAccess() {
        return access;
    }
}
