package com.heapixLearn.discovery.logic.profile.service;

import com.heapixLearn.discovery.logic.profile.entity.Profile;
import com.heapixLearn.discovery.logic.profile.entity.UserPost;

import java.util.List;

public interface ServerProfileAdapter {
    Profile getUserByToken(String token);
    List<UserPost> getAllByUserId(int userId);
    UserPost getById(int id);
    UserPost update(UserPost post);
    boolean delete(int id);
}
