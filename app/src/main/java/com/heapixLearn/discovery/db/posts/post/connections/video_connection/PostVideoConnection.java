package com.heapixLearn.discovery.db.posts.post.connections.video_connection;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import com.heapixLearn.discovery.db.posts.post.Post;
import com.heapixLearn.discovery.db.posts.post.post_video.PostVideo;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "post_video_connection",
        foreignKeys = {
                @ForeignKey(entity = Post.class,
                        parentColumns = "_id",
                        childColumns = "post_id",
                        onDelete = CASCADE),
                @ForeignKey(entity = PostVideo.class,
                        parentColumns = "_id",
                        childColumns = "video_id",
                        onDelete = CASCADE)},
        primaryKeys = {"post_id", "video_id"}
)

public class PostVideoConnection {
    @ColumnInfo(name = "post_id")
    private int postId;
    @ColumnInfo(name = "video_id")
    private int videoId;

    public PostVideoConnection(int postId, int videoId) {
        this.postId = postId;
        this.videoId = videoId;
    }

    public int getPostId() {
        return postId;
    }

    public int getVideoId() {
        return videoId;
    }
}
