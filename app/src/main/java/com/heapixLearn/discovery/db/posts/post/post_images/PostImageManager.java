package com.heapixLearn.discovery.db.posts.post.post_images;


import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class PostImageManager {
    private static PostImageManager instance;
    private DumbAppDB db;
    private ImageDAO dao;

    private PostImageManager() {
        db = DumbApp.getInstance().getDatabase();
        dao = db.getImageDao();
    }

    public static PostImageManager getInstance() {
        if (instance == null) {
            instance = new PostImageManager();
        }
        return instance;
    }

    public PostImage getById(int id) {
        return dao.getByID(id);
    }


    public ArrayList<PostImage> getAll() {
        Log.d("!!!!!LOG!!", "doInBackground: " + dao.getAll().toString());
        return new ArrayList<>(dao.getAll());
    }


    public void create(PostImage post) {
        dao.insert(post);
    }


    public void update(PostImage post) {
        dao.update(post);
    }


    public void delete(PostImage image) {
        dao.delete(image);
    }
}
