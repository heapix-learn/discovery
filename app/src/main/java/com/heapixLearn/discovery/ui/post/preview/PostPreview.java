package com.heapixLearn.discovery.ui.post.preview;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heapixLearn.discovery.R;

public class PostPreview extends Fragment {
    View previewFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        previewFragment = inflater.inflate(R.layout.post_preview_item, container, false);
        return previewFragment;
    }
}
