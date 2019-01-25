package com.heapixLearn.discovery.db.posts.post.post_video;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface VideoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PostVideo data);

    @Update
    void update(PostVideo data);

    @Delete
    void delete(PostVideo data);

    @Query("SELECT * FROM post_video")
    List<PostVideo> getAll();

    @Query("SELECT * FROM post_video WHERE _id = :id")
    PostVideo getByID(int id);
}
