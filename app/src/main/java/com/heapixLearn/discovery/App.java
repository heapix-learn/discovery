package com.heapixLearn.discovery;

import android.app.Application;

import com.heapixLearn.discovery.logic.authorization.AuthManager;

public class App extends Application {
    private static App instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static App getInstance() {
        return instance;
    }
    public static void checkAuthorization(Runnable onSuccess, Runnable onFailure){
        AuthManager authManager = new AuthManager();
        authManager.tryLoginWithStoredInfo(onSuccess, onFailure);
    }

}
