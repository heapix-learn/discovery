package com.heapixLearn.discovery.ui.dummy_fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heapixLearn.discovery.R;

public class SupportFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View supportFragment = inflater.inflate(R.layout.support_fragment, container, false);
        return supportFragment;
    }
}