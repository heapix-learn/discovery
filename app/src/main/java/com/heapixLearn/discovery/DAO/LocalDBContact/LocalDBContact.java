package com.heapixLearn.discovery.DAO.LocalDBContact;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

@Entity(tableName = "contacts")
public class LocalDBContact {

    public LocalDBContact(String name, String nick, String email, String phone, int remoteID, Bitmap avatar, boolean isFriend) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.phone = phone;
        this.remoteID = remoteID;
        this.avatar = avatar;
        this.isFriend = isFriend;
    }

    public LocalDBContact(String name, String nick, String email, String phone, int remoteID, byte[] avatarBlob, boolean isFriend) {
        this.name = name;
        this.nick = nick;
        this.email = email;
        this.phone = phone;
        this.remoteID = remoteID;
        this.avatarBlob = avatarBlob;
        this.isFriend = isFriend;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    private int id;
    private String name;
    private String nick;
    private String email;
    private String phone;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB, name = "avatar")
    private byte[] avatarBlob;
    @ColumnInfo(name = "is_friend")
    private boolean isFriend;
    @ColumnInfo(name = "remote_id")
    private int remoteID;
    @Ignore
    private Bitmap avatar;
    @Ignore
    private Thread converterToBlob;
    @Ignore
    private Thread converterToBitmap;

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

    public byte[] getAvatarBlob() {
        if (converterToBlob != null) {
            try {
                converterToBlob.join();
            } catch (InterruptedException e) {
            }
        }
        return avatarBlob;
    }

    public void setAvatarBlob(final byte[] avatarBlob) {
        this.avatarBlob = avatarBlob;
        converterToBitmap = new Thread(new Runnable() {
            @Override
            public void run() {
                avatar = convertBlobToBitmap(avatarBlob);
            }
        });
        converterToBitmap.start();
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean friend) {
        isFriend = friend;
    }

    public Bitmap getAvatar() {
        if (converterToBitmap != null) {
            try {
                converterToBitmap.join();
            } catch (InterruptedException e) {
            }
        }
        return avatar;
    }

    public void setAvatar(final Bitmap avatar) {
        this.avatar = avatar;
        converterToBlob = new Thread(new Runnable() {
            @Override
            public void run() {
                avatarBlob = convertBitmapToBlob(avatar);
            }
        });
        converterToBlob.start();
    }

    public int getRemoteID() {
        return remoteID;
    }

    public void setRemoteID(int remoteID) {
        this.remoteID = remoteID;
    }

    private byte[] convertBitmapToBlob(Bitmap image) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    private Bitmap convertBlobToBitmap(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}
