package com.heapixLearn.discovery.logic.profile.service;

import com.heapixLearn.discovery.logic.profile.entity.Profile;
import com.heapixLearn.discovery.logic.profile.entity.UserPost;

import java.util.List;

public interface DBProfileAdapter {
    List<UserPost> getAllByUserId(int id);
    UserPost getById(int id);
    void insert(UserPost post);
    void delete(UserPost post);
    void update(UserPost post);

}
