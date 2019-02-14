package com.heapixLearn.discovery;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.heapixLearn.discovery.ui.post.preview.PostPreviewFragment;

public class MainActivity extends AppCompatActivity {

    final FragmentManager fragmentManager = getSupportFragmentManager();
    PostPreviewFragment postPreviewFragment = new PostPreviewFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        fragmentManager.beginTransaction().add(R.id.main_container, postPreviewFragment).commit();

    }
}
