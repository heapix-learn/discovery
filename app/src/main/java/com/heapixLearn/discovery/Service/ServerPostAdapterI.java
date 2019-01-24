package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.ViewablePost;

import java.util.List;

public interface ServerPostAdapterI {

    List<ViewablePost> getFirst(int amount);
    ViewablePost getNext();
    ViewablePost getById(int id);
    boolean hasNewPosts();
    ViewablePost insert(ViewablePost post);
    boolean delete(ViewablePost post);
    ViewablePost update(ViewablePost post);
}
