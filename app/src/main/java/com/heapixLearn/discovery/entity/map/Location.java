package com.heapixLearn.discovery.entity.map;

public class Location {
    private double latitude;
    private double longitude;

    Location(){
        latitude = -1000;
        longitude = -1000;
    }

    Location(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
