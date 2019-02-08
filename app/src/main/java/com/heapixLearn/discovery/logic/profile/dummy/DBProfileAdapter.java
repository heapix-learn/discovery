package com.heapixLearn.discovery.logic.profile.dummy;

import com.heapixLearn.discovery.logic.profile.entity.UserPost;

import java.util.List;

public class DBProfileAdapter implements com.heapixLearn.discovery.logic.profile.service.DBProfileAdapter {
    @Override
    public List<UserPost> getAllByUserId(int id) {
        return null;
    }

    @Override
    public UserPost getById(int id) {
        return null;
    }

    @Override
    public void insert(UserPost post) {

    }

    @Override
    public void delete(UserPost post) {

    }

    @Override
    public void update(UserPost post) {

    }
}
