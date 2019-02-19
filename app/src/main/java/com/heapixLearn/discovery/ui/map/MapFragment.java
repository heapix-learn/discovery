package com.heapixLearn.discovery.ui.map;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heapixLearn.discovery.R;
import com.mapbox.mapboxsdk.maps.MapView;

import java.util.List;

public class MapFragment extends Fragment implements View.OnClickListener {
    private MapView mapView;
    private TextView accessView;
    private MapManager mapManager;
    private ImageButton zoomInButton;
    private ImageButton zoomOutButton;
    private ConstraintLayout rootView;
    private FloatingActionButton addPostButton;
    private FloatingActionButton postListButton;
    private MapFragmentManager mapFragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ConstraintLayout) inflater.inflate(R.layout.fragment_map, null);

        initViewFields();
        initManagers();
        setOnClickListeners();

        return rootView;
    }

    private void initManagers() {
        mapFragmentManager = MapFragmentManager.getInstance(mapView, getMarkerIcon());
        mapManager = MapManager.getInstance();
    }

    private void initViewFields() {
        mapView = rootView.findViewById(R.id.mapView);
        zoomInButton = rootView.findViewById(R.id.zoom_in_button);
        zoomOutButton = rootView.findViewById(R.id.zoom_out_button);
        addPostButton = rootView.findViewById(R.id.map_add_button);
        postListButton = rootView.findViewById(R.id.map_list_button);
        accessView = rootView.findViewById(R.id.access_text_view);
    }

    private void setOnClickListeners() {
        zoomInButton.setOnClickListener(this);
        zoomOutButton.setOnClickListener(this);
        addPostButton.setOnClickListener(this);
        postListButton.setOnClickListener(this);
        accessView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zoom_in_button:
                mapFragmentManager.moveCamera(null, 1);
                break;
            case R.id.zoom_out_button:
                mapFragmentManager.moveCamera(null, -1);
                break;
            case R.id.map_add_button:
                mapManager.addPost();
                break;
            case R.id.map_list_button:
                startPostListDialog();
                break;
            case R.id.access_text_view:
                startPrivacySettingsDialog();
                break;
        }
    }

    private void startPostListDialog() {
        List<Integer> postIds = mapFragmentManager.getPostList();
        PostListDialog dialog = new PostListDialog(postIds, getContext());
        dialog.showPostList();
    }

    private void startPrivacySettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(R.array.privacy_settings, (dialog, which) -> {
            switch (which) {
                case 0:
                    mapFragmentManager.changeAccess(3);
                    accessView.setText(R.string.access_global);
                    break;
                case 1:
                    mapFragmentManager.changeAccess(2);
                    accessView.setText(R.string.access_public);
                    break;
                case 2:
                    mapFragmentManager.changeAccess(1);
                    accessView.setText(R.string.access_private);
                    break;
            }
        });
        builder.show();
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
