package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.Post;

import java.util.List;

public interface DBPostManagerI {
    List<Post> getAll();
    Post getById(int id);
    int size();
    void clear();
    void insert(Post post);
    void delete(Post post);
    void update(Post post);
}
