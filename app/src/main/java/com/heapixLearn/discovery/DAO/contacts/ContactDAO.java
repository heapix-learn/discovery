package com.heapixLearn.discovery.DAO.contacts;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {
    @Query("SELECT * FROM contacts")
    List<LocalDBContact> getAll();

    @Query("SELECT * FROM contacts WHERE _id = :id")
    LocalDBContact getById(int id);

    @Insert
    void insert(LocalDBContact contact);

    @Update
    void update(LocalDBContact contact);

    @Delete
    void delete(LocalDBContact contact);
}
