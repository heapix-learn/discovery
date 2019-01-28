package com.heapixLearn.discovery.db.posts.post.post_video;


import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class PostVideoManager {
    private static PostVideoManager instance;
    private DumbAppDB db;
    private VideoDAO dao;

    private PostVideoManager() {
        db = DumbApp.getInstance().getDatabase();
        dao = db.getVideoDao();
    }


    public static PostVideoManager getInstance() {
        if (instance == null) {
            instance = new PostVideoManager();
        }
        return instance;
    }

    public PostVideo getById(int id) {
        return dao.getByID(id);
    }


    public ArrayList<PostVideo> getAll() {
        Log.d("!!!!!LOG!!", "doInBackground: " + dao.getAll().toString());
        return new ArrayList<>(dao.getAll());
    }


    public void create(PostVideo post) {
        dao.insert(post);
    }


    public void update(PostVideo post) {
        dao.update(post);
    }


    public void delete(PostVideo data) {
        dao.delete(data);
    }
}
