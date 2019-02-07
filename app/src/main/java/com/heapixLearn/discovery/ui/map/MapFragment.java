package com.heapixLearn.discovery.ui.map;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.heapixLearn.discovery.R;
import com.mapbox.mapboxsdk.maps.MapView;

public class MapFragment extends Fragment {
    private ConstraintLayout rootView;
    private MapFragmentManager mapFragmentManager;
    private MapView mapView;
    private ImageButton zoomInButton;
    private ImageButton zoomOutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ConstraintLayout) inflater.inflate(R.layout.fragment_map, null);

        initViewFields();
        mapFragmentManager = MapFragmentManager.getInstance(mapView, getMarkerIcon());
        setOnClickListeners();

        return rootView;
    }

    private void initViewFields(){
        mapView = rootView.findViewById(R.id.mapView);
        zoomInButton = rootView.findViewById(R.id.zoom_in_button);
        zoomOutButton = rootView.findViewById(R.id.zoom_out_button);
    }

    private void setOnClickListeners(){
        zoomInButton.setOnClickListener(mapFragmentManager);
        zoomOutButton.setOnClickListener(mapFragmentManager);
    }

    private Bitmap getMarkerIcon() {
        Drawable drawable = getResources().getDrawable(R.drawable.map_marker);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
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
