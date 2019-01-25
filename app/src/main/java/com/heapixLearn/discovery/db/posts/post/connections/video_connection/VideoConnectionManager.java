package com.heapixLearn.discovery.db.posts.post.connections.video_connection;

import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class VideoConnectionManager {
    private static VideoConnectionManager instance;

    public static VideoConnectionManager getInstance() {
        if (instance == null) {
            instance = new VideoConnectionManager();
        }
        return instance;
    }

    public ArrayList<PostVideoConnection> getByPostId(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostVideoConnectionDAO dao = db.getVideoConnectionDao();
        return new ArrayList<>(dao.getByPostId(id));
    }

    public ArrayList<PostVideoConnection> getByImageId(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostVideoConnectionDAO dao = db.getVideoConnectionDao();
        return new ArrayList<>(dao.getByVideoId(id));
    }


    public ArrayList<PostVideoConnection> getAll() {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostVideoConnectionDAO dao = db.getVideoConnectionDao();
        Log.d("!!!!!LOG!!", "doInBackground: " + dao.getAll().toString());
        return new ArrayList<>(dao.getAll());
    }


    public void create(PostVideoConnection connection) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostVideoConnectionDAO dao = db.getVideoConnectionDao();
        dao.insert(connection);
    }


    public void update(PostVideoConnection connection) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostVideoConnectionDAO dao = db.getVideoConnectionDao();
        dao.update(connection);
    }


    public void deleteByPostId(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostVideoConnectionDAO dao = db.getVideoConnectionDao();
        dao.deleteByPostId(id);
    }

    public void deleteByImageId(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostVideoConnectionDAO dao = db.getVideoConnectionDao();
        dao.deleteByImageId(id);
    }
}
