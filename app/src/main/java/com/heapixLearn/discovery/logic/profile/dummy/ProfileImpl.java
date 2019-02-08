package com.heapixLearn.discovery.logic.profile.dummy;

import com.heapixLearn.discovery.logic.profile.entity.Profile;

public class ProfileImpl implements Profile {
    private int id;
    private String name;
    private String avatar;
    private String username;
    private String phone;
    private String email;
    private String password;
    private int followers;

    public ProfileImpl(int id, String name, String avatar, String username, String phone, int followers) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.username = username;
        this.phone = phone;
        this.followers = followers;
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
    public String getAvatar() {
        return avatar;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPhone() {
        return phone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public int getFollowers() {
        return followers;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
