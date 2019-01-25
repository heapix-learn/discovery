package com.heapixLearn.discovery.db.posts.post.post_images;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ImageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PostImage image);

    @Update
    void update(PostImage image);

    @Delete
    void delete(PostImage image);

    @Query("SELECT * FROM post_images")
    List<PostImage> getAll();

    @Query("SELECT * FROM post_images WHERE _id = :id")
    PostImage getByID(int id);
}
