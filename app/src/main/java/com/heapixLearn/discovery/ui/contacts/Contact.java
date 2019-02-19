package com.heapixLearn.discovery.ui.contacts;

public interface Contact {
    int getId();
    String getName();
    int getFollowers();
    String getAvatar();
    boolean isFriend();
    void setFriend(boolean friend);
}
