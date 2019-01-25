package com.heapixLearn.discovery.db.posts.post.post_images;


import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class PostImageManager {
    private static PostImageManager instance;

    public static PostImageManager getInstance() {
        if (instance == null) {
            instance = new PostImageManager();
        }
        return instance;
    }

    public PostImage getById(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        ImageDAO imagesDao = db.getImageDao();
        return imagesDao.getByID(id);
    }


    public ArrayList<PostImage> getAll() {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        ImageDAO imagesDao = db.getImageDao();
        Log.d("!!!!!LOG!!", "doInBackground: " + imagesDao.getAll().toString());
        return new ArrayList<>(imagesDao.getAll());
    }


    public void create(PostImage post) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        ImageDAO imagesDao = db.getImageDao();
        imagesDao.insert(post);
    }


    public void update(PostImage post) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        ImageDAO imagesDao = db.getImageDao();
        imagesDao.update(post);
    }


    public void delete(PostImage image) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        ImageDAO imagesDao = db.getImageDao();
        imagesDao.delete(image);
    }
}
