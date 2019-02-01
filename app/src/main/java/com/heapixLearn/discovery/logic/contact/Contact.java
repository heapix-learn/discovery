package com.heapixLearn.discovery.logic.contact;

public class Contact implements com.heapixLearn.discovery.ui.contact.Contact {

    private int id;
    private String name;
    private int followers;
    private String avatar;
    private boolean isFriend;

    public Contact(int id, String name, int followers, String avatar, boolean isFriend) {
        this.id = id;
        this.name = name;
        this.followers = followers;
        this.avatar = avatar;
        this.isFriend = isFriend;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getFollowers() {
        return followers;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }

    @Override
    public boolean isFriend() {
        return isFriend;
    }

    @Override
    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Contact)) return false;
        Contact document = (Contact) o;
        return id == document.getId();
    }
}
