package com.heapixLearn.discovery;

import android.app.Application;

import com.mapbox.mapboxsdk.Mapbox;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Mapbox.getInstance(getApplicationContext(), getResources().getString(R.string.mapbox_access_token));
    }
}
