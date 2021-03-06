package com.heapixLearn.discovery.DAO.contacts;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {LocalDBContact.class},version = 1, exportSchema = false)
public abstract class AppDB extends RoomDatabase {
    public abstract ContactDAO getContactDAO();
}
