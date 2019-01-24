package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.ViewablePost;

import java.util.List;

public interface DBPostAdapterI {
    List<ViewablePost> getAll();
    ViewablePost getById(int id);
    void insert(ViewablePost post);
    void delete(ViewablePost post);
    void update(ViewablePost post);
}
