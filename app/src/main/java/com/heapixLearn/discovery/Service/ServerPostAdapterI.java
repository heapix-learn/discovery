package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.Post;

import java.util.List;

public interface ServerPostAdapterI {

    List<Post> getFirst(int amount);
    Post getNext();
    Post getById(int id);
    List<Post> getByUserId(int userId);
    boolean hasNewPosts();
    Post insert(Post post);
    boolean delete(Post post);
    Post update(Post post);
}
