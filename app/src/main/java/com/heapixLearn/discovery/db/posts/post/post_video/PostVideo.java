package com.heapixLearn.discovery.db.posts.post.post_video;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;

@Entity(tableName = "post_video",
        primaryKeys = ("_id")
       )
public class PostVideo {
    @ColumnInfo(name = "_id")
    private int id;
    @ColumnInfo(name = "video_reference")
    private String videoReference;
    private String preview;

    public PostVideo(int id, String videoReference, String preview) {
        this.id = id;
        this.videoReference = videoReference;
        this.preview = preview;
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
}
