package com.heapixLearn.discovery.ui.post.preview.dummy;

import com.heapixLearn.discovery.ui.post.preview.entity.IUser;

public class User implements IUser {
    private long id;
    private String name;
    private String avatar;
    private int followers;

    boolean isLiked;
    boolean isFriend;

    public User(long id, String name, String avatar, int followers, boolean isLiked, boolean isFriend) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.followers = followers;
        this.isLiked = isLiked;
        this.isFriend = isFriend;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public int getFollowers() {
        return followers;
    }

    @Override
    public boolean isPostLiked(long postId) {
        return isLiked;
    }

    @Override
    public boolean isFriend() {
        return isFriend;
    }

    @Override
    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @Override
    public void setFriend(boolean friend) {
        isFriend = friend;
    }
}
