package com.heapixLearn.discovery.db.posts.map_item;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MapItemDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MapItem mapItem);

    @Update
    void update(MapItem mapItem);

    @Delete
    void delete(MapItem mapItem);

    @Query("SELECT * FROM map_item")
    List<MapItem> getAll();

    @Query("SELECT * FROM map_item WHERE _id = :id")
    MapItem getByID(int id);
}
