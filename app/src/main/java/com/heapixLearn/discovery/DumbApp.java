package com.heapixLearn.discovery;

import android.app.Application;
import android.arch.persistence.room.Room;

public class DumbApp extends Application {
    public static DumbApp instance;

    private DumbAppDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, DumbAppDB.class, "database")
                .build();

    }

    public static DumbApp getInstance() {
        return instance;
    }

    public DumbAppDB getDatabase() {
        return database;
    }
}
