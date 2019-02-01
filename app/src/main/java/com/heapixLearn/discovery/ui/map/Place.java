package com.heapixLearn.discovery.ui.map;
public class Place {
    private String placeId;
    private String placeText;

    public String getPlaceId() {
        return placeId;
    }

    void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    String getPlaceText() {
        return placeText;
    }

    void setPlaceText(String placeText) {
        this.placeText = placeText;
    }

    public String toString() {
        return placeText;
    }
}