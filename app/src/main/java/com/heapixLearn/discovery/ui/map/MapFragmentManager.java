package com.heapixLearn.discovery.ui.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.heapixLearn.discovery.R;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
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

import static com.mapbox.mapboxsdk.style.expressions.Expression.all;
import static com.mapbox.mapboxsdk.style.expressions.Expression.eq;
import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;

public class MapFragmentManager implements OnMapReadyCallback, View.OnClickListener, MapboxMap.OnMapClickListener {
    private static MapFragmentManager instance;
    private MapView mapView;
    private List<MapItem> mapItemList;
    private Bitmap markerIcon;
    private MapManager mapManager;
    private MapboxMap map;
    private Thread featureThread;
    private FeatureCollection featureCollection;
    private SymbolLayer baseLayer;
    private Style style;
    private byte access = 3;
    private Expression filter = eq(has("access"), lte(get("access"), access));

    private MapFragmentManager(MapView mapView, Bitmap markerIcon) {
        this.mapView = mapView;
        this.markerIcon = markerIcon;

        mapManager = new MapManager();
        getFeatureCollection();
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
    public boolean onMapClick(@NonNull LatLng point) {
        PointF screenPoint = map.getProjection().toScreenLocation(point);
        List<Feature> features = map.queryRenderedFeatures(screenPoint, "markers");
        if (!features.isEmpty()) {
            Feature selectedFeature = features.get(0);
            if (selectedFeature.hasProperty("id")) {
                moveCamera(point, 0);
                String id = selectedFeature.getStringProperty("access");
                Toast.makeText(mapView.getContext(), id, Toast.LENGTH_SHORT).show();
            } else {
                moveCamera(point, 1);
            }
        }
        return false;
    }

    private void moveCamera(LatLng latLng, double zoom) {
        CameraPosition.Builder newPosition = new CameraPosition.Builder();
        if (latLng != null) {
            newPosition.target(latLng);
        }
        if (zoom != 0) {
            double currentZoom = map.getCameraPosition().zoom;
            newPosition.zoom(currentZoom + zoom);
        }
        map.animateCamera(CameraUpdateFactory.newCameraPosition(newPosition.build()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zoom_in_button:
                moveCamera(null, 1);
                break;
            case R.id.zoom_out_button:
                moveCamera(null, -1);
                break;
            case R.id.map_add_button:
                if (access > 0) {
                    access--;
                    updateLayerFilter();
                }
                break;
            case R.id.map_list_button:
                if (access < 2) {
                    access++;
                    updateLayerFilter();
                }
                break;
        }
    }

    private void updateLayerFilter() {
        style.removeLayer(baseLayer);
        baseLayer.withFilter(eq(has("access"), lte(get("access"), access)));
        style.addLayer(baseLayer);
    }

    private void getFeatureCollection() {
        featureThread = new Thread(() -> {
            mapItemList = mapManager.getAllMapItems();
            featureCollection = FeatureCollection.fromFeatures(getFeatureList());
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
        map.addOnMapClickListener(this);
    }

    private void setStyle() {
        map.setStyle(Style.DARK, style -> {
            style.addImage("marker_icon", markerIcon);
            this.style = style;
            addSource();
            addBaseLayer();
            addClusterLayers();
        });
    }

    private void addSource() {

        joinThread(featureThread);
        style.addSource(new GeoJsonSource("marker-source", featureCollection,
                new GeoJsonOptions()
                        .withCluster(true)
                        .withClusterMaxZoom(14)
                        .withClusterRadius(60)
        ));
    }

    private void addBaseLayer() {
        baseLayer = new SymbolLayer("markers", "marker-source")
                .withProperties(
                        iconImage("marker_icon"),
                        iconSize(1.6f))
                .withFilter(eq(has("access"), lte(get("access"), access)));
        style.addLayer(baseLayer);
    }

    private void addClusterLayers() {
        int[] layers = new int[]{1000000, 100000, 10000, 1000, 500, 100, 0};

        for (int i = 0; i < layers.length; i++) {
            SymbolLayer cluster = new SymbolLayer("cluster-" + i, "marker-source");
            cluster.withProperties(
                    iconImage("marker_icon"),
                    iconSize(getCircleRadius()),
                    textField(Expression.toString(get("point_count"))),
                    textSize(11f),
                    textColor(Color.BLACK),
                    textIgnorePlacement(true),
                    textAllowOverlap(true)
            );
            style.addLayer(cluster);
        }
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

    private List<Feature> getFeatureList() {
        List<Feature> featureList = new ArrayList<>();
        for (MapItem item : mapItemList) {
            Point point = Point.fromLngLat(item.getLng(), item.getLat());
            Feature feature = Feature.fromGeometry(point);
            feature.addNumberProperty("access", item.getAccess());
            feature.addStringProperty("id", item.getId() + "");
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
