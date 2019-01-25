package com.heapixLearn.discovery.db.posts.map_item;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.heapixLearn.discovery.db.posts.post.Post;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "map_item",
        foreignKeys = {
        @ForeignKey(entity = Post.class,
                parentColumns = "_id",
                childColumns = "post_id",
                onDelete = CASCADE),
        @ForeignKey(entity = Post.class,
                parentColumns = "user_id",
                childColumns = "user_id",
                onDelete = CASCADE)}
        )
public class MapItem {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    private int id;
    @ColumnInfo(name = "post_id")
    private int postId;
    @ColumnInfo(name = "user_id")
    private int userId;
    private String location;

    public MapItem(int id, int postId, int userId, String location) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public int getPostId() {
        return postId;
    }

    public int getUserId() {
        return userId;
    }

    public String getLocation() {
        return location;
    }
}
