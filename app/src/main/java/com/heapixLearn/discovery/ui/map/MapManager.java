package com.heapixLearn.discovery.ui.map;

import java.util.ArrayList;
import java.util.List;

public class MapManager implements MapManagerI {
    @Override
    public List<MapItem> getAllMapItems() {
        ArrayList<MapItem> list = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            double lat = -90 + Math.random() * 181;
            double lng = -180 + Math.random() * 361;
            list.add(new MapItem(i, i, i, lat, lng));
        }
        return list;
    }

    @Override
    public MapItem getMapItemById(int id) {
        return new MapItem(1, 2, 3, 15, 66);
    }

    @Override
    public Post getPostById(int id) {
        return new Post();
    }
}
