package com.heapixLearn.discovery.Server.Contacts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerContact {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("nick")
    @Expose
    private String nick;

    @SerializedName("avatarURL")
    @Expose
    private String avatarURL;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("isFriend")
    @Expose
    private boolean isFriend;

    public String getAvatarURL() {
        return avatarURL;
    }

    public String getEmail() {
        return email;
    }

    public boolean getIsFriend() {
        return isFriend;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNick() {
        return nick;
    }

    public String getPhone() {
        return phone;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsFriend(boolean friend) {
        isFriend = friend;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
