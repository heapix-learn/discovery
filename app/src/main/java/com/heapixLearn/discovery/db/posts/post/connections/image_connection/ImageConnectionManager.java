package com.heapixLearn.discovery.db.posts.post.connections.image_connection;

import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class ImageConnectionManager {
    private static ImageConnectionManager instance;

    public static ImageConnectionManager getInstance() {
        if (instance == null) {
            instance = new ImageConnectionManager();
        }
        return instance;
    }

    public ArrayList<PostImageConnection> getByPostId(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostImageConnectionDAO dao = db.getImageConnectionDao();
        return new ArrayList<>(dao.getByPostID(id));
    }

    public ArrayList<PostImageConnection> getByImageId(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostImageConnectionDAO dao = db.getImageConnectionDao();
        return new ArrayList<>(dao.getByImageID(id));
    }


    public ArrayList<PostImageConnection> getAll() {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostImageConnectionDAO dao = db.getImageConnectionDao();
        Log.d("!!!!!LOG!!", "doInBackground: " + dao.getAll().toString());
        return new ArrayList<>(dao.getAll());
    }


    public void create(PostImageConnection connection) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostImageConnectionDAO dao = db.getImageConnectionDao();
        dao.insert(connection);
    }


    public void update(PostImageConnection connection) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostImageConnectionDAO dao = db.getImageConnectionDao();
        dao.update(connection);
    }


    public void deleteByPostId(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostImageConnectionDAO dao = db.getImageConnectionDao();
        dao.deleteByPostId(id);
    }

    public void deleteByImageId(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostImageConnectionDAO dao = db.getImageConnectionDao();
        dao.deleteByImageId(id);
    }
}
