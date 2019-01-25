package com.heapixLearn.discovery.db.posts.post.connections.image_connection;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PostImageConnectionDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PostImageConnection connection);

    @Update
    void update(PostImageConnection connection);

    @Query("DELETE FROM post_image_connection WHERE post_id = :id")
    void deleteByPostId(int id);

    @Query("DELETE FROM post_image_connection WHERE image_id = :id")
    void deleteByImageId(int id);

    @Query("SELECT * FROM post_image_connection")
    List<PostImageConnection> getAll();

    @Query("SELECT * FROM post_image_connection WHERE post_id = :id")
    List<PostImageConnection> getByPostID(int id);

    @Query("SELECT * FROM post_image_connection WHERE image_id = :id")
    List<PostImageConnection> getByImageID(int id);
}
