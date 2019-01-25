package com.heapixLearn.discovery.db.posts.post.connections.image_connection;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.heapixLearn.discovery.db.posts.post.Post;
import com.heapixLearn.discovery.db.posts.post.post_images.PostImage;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "post_image_connection",
        foreignKeys = {
                @ForeignKey(entity = Post.class,
                        parentColumns = "_id",
                        childColumns = "post_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = PostImage.class,
                        parentColumns = "_id",
                        childColumns = "image_id",
                        onDelete = CASCADE)},
        primaryKeys = {"post_id", "image_id"}
)


public class PostImageConnection {
    @ColumnInfo(name = "post_id")
    private int postId;
    @ColumnInfo(name = "image_id")
    private int imageId;

    public PostImageConnection(int postId, int imageId) {
        this.postId = postId;
        this.imageId = imageId;
    }

    public int getPostId() {
        return postId;
    }

    public int getImageId() {
        return imageId;
    }
}
