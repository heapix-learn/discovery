package com.heapixLearn.discovery.db.posts.post;

import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class PostManager {
    private static PostManager instance;

    public static PostManager getInstance() {
        if (instance == null) {
            instance = new PostManager();
        }
        return instance;
    }

    public Post getById(int id) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostDAO postDao = db.getPostDao();
        return postDao.getByID(id);
    }


    public ArrayList<Post> getAll() {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostDAO postDao = db.getPostDao();
        Log.d("!!!!!LOG!!", "doInBackground: " + postDao.getAll().toString());
        return new ArrayList<>(postDao.getAll());
    }


    public void create(Post post) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostDAO postDao = db.getPostDao();
        postDao.insert(post);
    }


    public void update(Post post) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostDAO postDao = db.getPostDao();
        postDao.update(post);
    }


    public void delete(Post post) {
        DumbAppDB db = DumbApp.getInstance().getDatabase();
        PostDAO postDao = db.getPostDao();
        postDao.delete(post);
    }
}
