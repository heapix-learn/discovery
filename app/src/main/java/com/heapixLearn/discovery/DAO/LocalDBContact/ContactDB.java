package com.heapixLearn.discovery.DAO.LocalDBContact;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {LocalDBContact.class}, version = 1, exportSchema = false)
public abstract class ContactDB extends RoomDatabase {
    public abstract ContactDAO getContactDAO();
}
