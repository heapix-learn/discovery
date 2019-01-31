package com.heapixLearn.discovery.DAO.LocalDBContact;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contacts")
public class LocalDBContact {

    public LocalDBContact(
            String name, String nick, String email, String phone,
            int remoteID, byte[] avatar, boolean isFriend) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.phone = phone;
        this.remoteID = remoteID;
        this.avatar = avatar;
        this.isFriend = isFriend;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;
    private String name;
    private String nick;
    private String email;
    private String phone;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] avatar;
    @ColumnInfo(name = "is_friend")
    private boolean isFriend;
    @ColumnInfo(name = "remote_id")
    private int remoteID;

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

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public int getRemoteID() {
        return remoteID;
    }

    public void setRemoteID(int remoteID) {
        this.remoteID = remoteID;
    }
}
