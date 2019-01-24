package com.heapixLearn.discovery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.heapixLearn.discovery.AppContext;
import com.heapixLearn.discovery.MainActivity;
import com.heapixLearn.discovery.R;
import com.heapixLearn.discovery.ui.authorization.LoginActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Runnable onSuccess = new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(myIntent);
                finish();
            }
        };

        Runnable onFailure = new Runnable() {
            @Override
            public void run() {
                Intent myIntent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(myIntent);
                finish();
            }
        };

        AppContext.checkAuthorization(onSuccess, onFailure);
    }
}
