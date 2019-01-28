package com.heapixLearn.discovery.db.posts.map_item;

import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class MapItemManager {
    private static MapItemManager instance;
    private DumbAppDB db;
    private MapItemDAO dao;

    private MapItemManager() {
        db = DumbApp.getInstance().getDatabase();
        dao = db.getMapItemDao();
    }


    public static MapItemManager getInstance() {
        if (instance == null) {
            instance = new MapItemManager();
        }
        return instance;
    }

    public MapItem getById(int id) {
        return dao.getByID(id);
    }


    public ArrayList<MapItem> getAll() {
        Log.d("!!!!!LOG!!", "doInBackground: " + dao.getAll().toString());
        return new ArrayList<>(dao.getAll());
    }


    public void create(MapItem post) {
        dao.insert(post);
    }


    public void update(MapItem post) {
       dao.update(post);
    }


    public void delete(MapItem mapItem) {
       dao.delete(mapItem);
    }
}
