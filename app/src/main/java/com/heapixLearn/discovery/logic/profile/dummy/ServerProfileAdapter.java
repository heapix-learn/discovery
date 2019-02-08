package com.heapixLearn.discovery.logic.profile.dummy;

import com.heapixLearn.discovery.logic.profile.entity.Profile;
import com.heapixLearn.discovery.logic.profile.entity.UserPost;

import java.util.List;

public class ServerProfileAdapter implements com.heapixLearn.discovery.logic.profile.service.ServerProfileAdapter {
    @Override
    public Profile getUserByToken(String token) {
        return null;
    }

    @Override
    public List<UserPost> getAllByUserId(int userId) {
        return null;
    }

    @Override
    public UserPost getById(int id) {
        return null;
    }

    @Override
    public UserPost update(UserPost post) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return true;
    }
}
