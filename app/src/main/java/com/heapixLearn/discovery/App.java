package com.heapixLearn.discovery;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.heapixLearn.discovery.DAO.contacts.AppDB;

public class App extends Application {
    private static App instance;
    private AppDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(getApplicationContext(), AppDB.class, "appDB").build();
    }

    public static App getInstance(){
        return instance;
    }

    public AppDB getDatabase(){
        return database;
    }
}
