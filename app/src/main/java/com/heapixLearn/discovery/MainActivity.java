package com.heapixLearn.discovery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.heapixLearn.discovery.server.TypeOfServerError;
import com.heapixLearn.discovery.server.contacts.ServerContact;
import com.heapixLearn.discovery.server.contacts.ServerContactManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
