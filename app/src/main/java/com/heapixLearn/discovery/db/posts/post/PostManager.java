package com.heapixLearn.discovery.db.posts.post;

import android.util.Log;

import com.heapixLearn.discovery.DumbApp;
import com.heapixLearn.discovery.DumbAppDB;

import java.util.ArrayList;

public class PostManager {
    private static PostManager instance;
    private DumbAppDB db;
    private PostDAO dao;

    private PostManager() {
        db = DumbApp.getInstance().getDatabase();
        dao = db.getPostDao();
    }

    public static PostManager getInstance() {
        if (instance == null) {
            instance = new PostManager();
        }
        return instance;
    }

    public Post getById(int id) {
        return dao.getByID(id);
    }


    public ArrayList<Post> getAll() {
        Log.d("!!!!!LOG!!", "doInBackground: " + dao.getAll().toString());
        return new ArrayList<>(dao.getAll());
    }


    public void create(Post post) {
        dao.insert(post);
    }


    public void update(Post post) {
        dao.update(post);
    }


    public void delete(Post post) {
        dao.delete(post);
    }
}
