package com.heapixLearn.discovery.Service;

import com.heapixLearn.discovery.Entity.ViewablePost;

import java.util.List;

public class ServerPostAdapter implements ServerPostAdapterI {
    @Override
    public List<ViewablePost> getFirst(int amount) {
        return null;
    }

    @Override
    public ViewablePost getNext() {
        return null;
    }

    @Override
    public ViewablePost getById(int id) {
        return null;
    }

    @Override
    public boolean hasNewPosts() {
        return false;
    }

    @Override
    public ViewablePost insert(ViewablePost post) {
        return null;
    }

    @Override
    public boolean delete(ViewablePost post) {
        return true;
    }

    @Override
    public ViewablePost update(ViewablePost post) {
        return null;
    }
}
