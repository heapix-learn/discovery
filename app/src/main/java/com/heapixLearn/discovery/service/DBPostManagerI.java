package com.heapixLearn.discovery.service;

import com.heapixLearn.discovery.entity.Post;

import java.util.List;

public interface DBPostManagerI {
    Post getById(int id);
    Post getByIndex(int index);
    List<Post> getList(int start, int stop);
    int size();
    int indexOf(Post post);
    void clear();
    void insert(Post post);
    void delete(Post post);
    void update(Post post);
}
