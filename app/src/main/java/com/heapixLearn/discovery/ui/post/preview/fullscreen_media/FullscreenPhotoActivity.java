package com.heapixLearn.discovery.ui.post.preview.fullscreen_media;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.heapixLearn.discovery.R;

import java.util.Arrays;
import java.util.List;

public class FullscreenPhotoActivity extends AppCompatActivity {
    private static final String PHOTOS_URL="photosURL";
    private static final String POSITION="position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);
        List<String> photos = Arrays.asList(getIntent().getExtras().getStringArray(PHOTOS_URL));
        int position = getIntent().getExtras().getInt(POSITION);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_photo);
        FSImageAdapter adapter = new FSImageAdapter(this, photos);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}