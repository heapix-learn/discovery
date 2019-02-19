package com.heapixLearn.discovery.ui.map;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private static MapManager instance;

    private MapManager() {
    }

    public static synchronized MapManager getInstance() {
        if (instance == null) {
            instance = new MapManager();
        }
        return instance;
    }

    public List<MapItem> getAllMapItems() {
        ArrayList<MapItem> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            double lat = -90 + Math.random() * 180;
            double lng = -180 + Math.random() * 360;
            list.add(new MapItem(i, i, i, lat, lng, (byte) (Math.random() * 3)));
        }
        return list;
    }

    public MapItem addPost() {
        return new MapItem(1, 2, 3, 15, 66, MapItem.ACCESS_GLOBAL);
    }
}


