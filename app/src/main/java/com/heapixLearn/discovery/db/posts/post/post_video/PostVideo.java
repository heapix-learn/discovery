package com.heapixLearn.discovery.db.posts.post.post_video;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.heapixLearn.discovery.db.posts.post.Post;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "post_video",
        primaryKeys = ("_id"),
        foreignKeys = @ForeignKey(entity = Post.class,
                parentColumns = "_id",
                childColumns = "post_id",
                onDelete = CASCADE)
       )
public class PostVideo {
    @ColumnInfo(name = "_id")
    private int id;
    @ColumnInfo(name = "video_reference")
    private String videoReference;
    private String preview;
    @ColumnInfo(name = "post_id")
    private int postId;

    public PostVideo(int id, String videoReference, String preview, int postId) {
        this.id = id;
        this.videoReference = videoReference;
        this.preview = preview;
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public String getVideoReference() {
        return videoReference;
    }

    public String getPreview() {
        return preview;
    }

    public int getPostId() {
        return postId;
    }
}
