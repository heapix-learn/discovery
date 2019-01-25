package com.heapixLearn.discovery.db.posts.post.post_video;


import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class PostVideoManager {
    private static PostVideoManager instance;

    public static PostVideoManager getInstance() {
        if (instance == null) {
            instance = new PostVideoManager();
        }
        return instance;
    }

    public PostVideo getById(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        VideoDAO videoDAO = db.getVideoDao();
        return videoDAO.getByID(id);
    }


    public ArrayList<PostVideo> getAll() {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        VideoDAO videoDAO = db.getVideoDao();
        Log.d("!!!!!LOG!!", "doInBackground: " + videoDAO.getAll().toString());
        return new ArrayList<>(videoDAO.getAll());
    }


    public void create(PostVideo post) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        VideoDAO videoDAO = db.getVideoDao();
        videoDAO.insert(post);
    }


    public void update(PostVideo post) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        VideoDAO videoDAO = db.getVideoDao();
        videoDAO.update(post);
    }


    public void delete(PostVideo data) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        VideoDAO videoDAO = db.getVideoDao();
        videoDAO.delete(data);
    }
}
