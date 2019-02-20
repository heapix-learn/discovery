package com.heapixLearn.discovery.ui.post.preview.entity;

public interface IUser {
    long getId();
    String getName();
    String getAvatar();
    int getFollowers();

    boolean isPostLiked(long postId);
    boolean isFriend();
    void setLiked(long postId, boolean liked);
    void setFriend(boolean friend);
}
