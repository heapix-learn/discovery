package com.heapixLearn.discovery.ui.post.preview.fullscreen_media;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.heapixLearn.discovery.R;
import com.heapixLearn.discovery.ui.post.preview.VideoAdapter;

import java.util.Arrays;
import java.util.List;

public class FullscreenVideoActivity extends AppCompatActivity {
    private static final String VIDEO_URL="videosURL";
    private static final String POSITION="position";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_video);
        int position = getIntent().getExtras().getInt(POSITION);
        List<String> videos = Arrays.asList(getIntent().getExtras().getStringArray(VIDEO_URL));
        CustomViewPager viewPager = (CustomViewPager) findViewById(R.id.view_pager_video);
        FSVideoAdapter adapter = new FSVideoAdapter(this, videos);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setCurrentItem(position);
    }
}
