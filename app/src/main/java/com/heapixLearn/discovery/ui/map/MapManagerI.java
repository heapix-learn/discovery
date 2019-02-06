package com.heapixLearn.discovery.ui.map;

import java.util.List;

public interface MapManagerI {
    List<MapItem> getAllMapItems();
    MapItem getMapItemById(int id);
    Post getPostById(int id);

}
