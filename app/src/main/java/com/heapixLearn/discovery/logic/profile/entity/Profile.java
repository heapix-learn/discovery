package com.heapixLearn.discovery.logic.profile.entity;

public class Profile {
    private int id;
    private String name;
    private String avatar;
    private String username;
    private String phone;
    private String email;
    private String password;
    private String followers;

    public Profile(int id, String name, String avatar, String username, String phone, String followers) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.username = username;
        this.phone = phone;
        this.followers = followers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }
}
