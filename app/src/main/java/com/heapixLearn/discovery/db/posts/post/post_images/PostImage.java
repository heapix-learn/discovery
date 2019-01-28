package com.heapixLearn.discovery.db.posts.post.post_images;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.heapixLearn.discovery.db.posts.post.Post;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "post_images",
        primaryKeys = ("_id"),
        foreignKeys = @ForeignKey(entity = Post.class,
                                  parentColumns = "_id",
                                  childColumns = "post_id",
                                  onDelete = CASCADE)
)
public class PostImage {
    @ColumnInfo(name = "_id")
    private int id;
    @ColumnInfo(name = "image_reference")
    private String imageReference;
    @ColumnInfo(name = "post_id")
    private int postId;

    public PostImage(int id, String imageReference, int postId) {
        this.id = id;
        this.imageReference = imageReference;
        this.postId = postId;
    }


    public int getId() {
        return id;
    }

    public String getImageReference() {
        return imageReference;
    }

    public int getPostId() {
        return postId;
    }
}
