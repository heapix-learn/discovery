package com.heapixLearn.discovery.db.posts.post.post_images;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "post_images",
        primaryKeys = ("_id")
       )
public class PostImage {
    @ColumnInfo(name = "_id")
    private int id;
    @ColumnInfo(name = "image_reference")
    private String imageReference;

    public PostImage(int id, String imageReference) {
        this.id = id;
        this.imageReference = imageReference;
    }


    public int getId() {
        return id;
    }

    public String getImageReference() {
        return imageReference;
    }
}
