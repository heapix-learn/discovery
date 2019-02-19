package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.Post;

import java.util.List;

public interface DBPostAdapterI {
    List<Post> getAll();
    Post getById(int id);
    void insert(Post post);
    void delete(Post post);
    void update(Post post);
}
