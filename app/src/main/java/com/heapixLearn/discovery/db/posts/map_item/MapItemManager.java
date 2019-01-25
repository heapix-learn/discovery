package com.heapixLearn.discovery.db.posts.map_item;

import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class MapItemManager {
    private static MapItemManager instance;

    public static MapItemManager getInstance() {
        if (instance == null) {
            instance = new MapItemManager();
        }
        return instance;
    }

    public MapItem getById(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        MapItemDAO mapItemDao = db.getMapItemDao();
        return mapItemDao.getByID(id);
    }


    public ArrayList<MapItem> getAll() {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        MapItemDAO mapItemDao = db.getMapItemDao();
        Log.d("!!!!!LOG!!", "doInBackground: " + mapItemDao.getAll().toString());
        return new ArrayList<>(mapItemDao.getAll());
    }


    public void create(MapItem post) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        MapItemDAO mapItemDao = db.getMapItemDao();
        mapItemDao.insert(post);
    }


    public void update(MapItem post) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        MapItemDAO mapItemDao = db.getMapItemDao();
        mapItemDao.update(post);
    }


    public void delete(MapItem mapItem) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        MapItemDAO mapItemDao = db.getMapItemDao();
        mapItemDao.delete(mapItem);
    }
}
