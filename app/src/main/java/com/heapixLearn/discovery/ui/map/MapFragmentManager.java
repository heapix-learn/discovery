package com.heapixLearn.discovery.ui.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.heapixLearn.discovery.R;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdate;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;

public class MapFragmentManager implements OnMapReadyCallback, View.OnClickListener {
    private static MapFragmentManager instance;
    private MapView mapView;
    private List<MapItem> mapItemList;
    private Bitmap markerIcon;
    private MapManager mapManager;
    private MapboxMap map;
    private Thread featureThread;
    private FeatureCollection featureCollection;

    private MapFragmentManager(MapView mapView, Bitmap markerIcon) {
        this.mapView = mapView;
        this.markerIcon = markerIcon;

        mapManager = new MapManager();
        getMapItemList();
        initMapView();
    }

    public static synchronized MapFragmentManager getInstance() {
        return instance;
    }

    public static synchronized MapFragmentManager getInstance(MapView mapView, Bitmap markerIcon) {
        if (instance == null) {
            instance = new MapFragmentManager(mapView, markerIcon);
        }
        return instance;
    }

    @Override
    public void onClick(View v) {
        double currentZoom = map.getCameraPosition().zoom;
        switch (v.getId()){
            case R.id.zoom_in_button:
                map.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder().zoom(currentZoom + 1).build()));
                break;
            case R.id.zoom_out_button:
                map.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder().zoom(currentZoom - 1).build()));
                break;
        }
    }

    private void getMapItemList() {
        featureThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mapItemList = mapManager.getAllMapItems();
                getFeatureCollection();
            }
        });
        featureThread.start();
    }

    private void initMapView() {
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        map = mapboxMap;
        setStyle();
    }

    private void setStyle() {
        map.setStyle(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                style.addImage("marker_icon", markerIcon);
                addSource(style);
                addBaseLayer(style);
                addClusterLayer(style);
                addCountLayer(style);
            }
        });
    }

    private void addSource(Style style) {
        joinThread(featureThread);
        style.addSource(new GeoJsonSource("marker-source", featureCollection,
                new GeoJsonOptions()
                        .withCluster(true)
                        .withClusterMaxZoom(14)
                        .withClusterRadius(60)
        ));
    }

    private void getFeatureCollection() {
        featureCollection = FeatureCollection.fromFeatures(getFeatureList());
    }

    private void addBaseLayer(Style style) {
        style.addLayer(new SymbolLayer("marker-layer", "marker-source")
                .withProperties(
                        iconImage("marker_icon"),
                        iconSize(7f)
                ));
    }

    private void addClusterLayer(Style style) {
        style.addLayer(new SymbolLayer("cluster", "marker-source")
                .withProperties(
                        iconImage("marker_icon"),
                        iconSize(getCircleRadius())
                ));
    }

    private Expression getCircleRadius() {
        return interpolate(exponential(1.0f), get("point_count"),
                stop(0, 0f),
                stop(1, 1.6f),
                stop(100, 2.5f),
                stop(500, 3f),
                stop(1000, 4.5f),
                stop(10000, 6f),
                stop(100000, 8f),
                stop(1000000, 9f)
        );
    }

    private void addCountLayer(Style style) {
        SymbolLayer layer = new SymbolLayer("count", "marker-source").withProperties(
                textField(Expression.toString(get("point_count"))),
                textSize(11f),
                textColor(Color.BLACK),
                textIgnorePlacement(true),
                textAllowOverlap(true));
        style.addLayer(layer);
    }

    private List<Feature> getFeatureList() {
        List<Feature> featureList = new ArrayList<>();
        for (MapItem item : mapItemList) {
            Point point = Point.fromLngLat(item.getLng(), item.getLat());
            Feature feature = Feature.fromGeometry(point);
            featureList.add(feature);
        }
        return featureList;
    }

    private void joinThread(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
        }
    }

    public void onResume() {
        mapView.onResume();
    }

    public void onPause() {
        mapView.onPause();
    }

    public void onLowMemory() {
        mapView.onLowMemory();
    }

    public void onSaveInstanceState(Bundle outState) {
        mapView.onSaveInstanceState(outState);
    }

    public void onStart() {
        mapView.onStart();
    }

    public void onStop() {
        mapView.onStop();
    }

    public void onDestroy() {
        mapView.onDestroy();
    }
}
