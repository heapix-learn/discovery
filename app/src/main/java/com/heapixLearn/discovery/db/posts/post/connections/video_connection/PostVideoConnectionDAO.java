package com.heapixLearn.discovery.db.posts.post.connections.video_connection;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;


@Dao
public interface PostVideoConnectionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PostVideoConnection connection);

    @Update
    void update(PostVideoConnection connection);

    @Query("DELETE FROM post_video_connection WHERE post_id = :id")
    void deleteByPostId(int id);

    @Query("DELETE FROM post_video_connection WHERE video_id = :id")
    void deleteByImageId(int id);

    @Query("SELECT * FROM post_video_connection")
    List<PostVideoConnection> getAll();

    @Query("SELECT * FROM post_video_connection WHERE post_id = :id")
    List<PostVideoConnection> getByPostId(int id);

    @Query("SELECT * FROM post_video_connection WHERE video_id = :id")
    List<PostVideoConnection> getByVideoId(int id);
}
