package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.ViewablePost;

import java.util.List;

public interface ServerPostAdapterI {
    List<ViewablePost> getFirst(int amount);
    List<ViewablePost> getNext(int amount);
    ViewablePost getById(int id);
    int[] getNewIds();
    boolean hasNewPosts();
    void insert(ViewablePost post);
    void delete(ViewablePost post);
    void update(ViewablePost post);
}
