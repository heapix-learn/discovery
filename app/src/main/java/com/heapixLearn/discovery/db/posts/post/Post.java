package com.heapixLearn.discovery.db.posts.post;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

@Entity(tableName = "posts",
        primaryKeys = "_id",
        indices = {@Index(value = {"user_id"},
                   unique = true)})
public class Post {
    @NonNull
    @ColumnInfo(name = "_id")
    private int id;
    private int access;
    @ColumnInfo(name = "user_id")
    private int userId;
    private String description;
    private String title;

    public Post(int id, int access, int userId, String description, String title) {
        this.id = id;
        this.access = access;
        this.userId = userId;
        this.description = description;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getAccess() {
        return access;
    }

    public int getUserId() {
        return userId;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }
}
