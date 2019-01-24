package com.heapixLearn.discovery;

import android.app.Activity;
import android.app.Application;

import com.heapixLearn.discovery.logic.authorization.AuthManager;

import java.util.concurrent.Semaphore;

public class AppContext extends Application {
    private static AppContext instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static AppContext getInstance() {
        return instance;
    }
    public static void checkAuthorization(Runnable onSuccess, Runnable onFailure){
        AuthManager authManager = AuthManager.getInstance();
        authManager.tryLoginWithStoredInfo(onSuccess, onFailure);
    }

}
