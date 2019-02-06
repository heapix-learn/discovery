package com.heapixLearn.discovery.ui.map;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.heapixLearn.discovery.R;

public class MapFragment extends Fragment {
    private ConstraintLayout rootView;
    private MapFragmentManager mapFragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ConstraintLayout) inflater.inflate(R.layout.fragment_map, null);

        initMap();

        return rootView;
    }

    private void initMap(){
        mapFragmentManager = MapFragmentManager.getInstance(getContext());
        rootView.removeAllViews();
        rootView.addView(mapFragmentManager.getMapView());
    }

    @Override
    public void onPause() {
        super.onPause();
        mapFragmentManager.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragmentManager.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapFragmentManager.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapFragmentManager.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mapFragmentManager.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapFragmentManager.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapFragmentManager.onDestroy();
    }
}
