package com.heapixLearn.discovery.ui.map;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

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

public class MapFragmentManager implements OnMapReadyCallback, MapboxMap.OnMapClickListener {
    private static MapFragmentManager instance;
    private MapView mapView;
    private List<MapItem> mapItemList;
    private Bitmap markerIcon;
    private MapManager mapManager;
    private MapboxMap map;
    private Thread globalFeatureThread;
    private Thread additionalFeatureThread;
    private FeatureCollection globalFeatureCollection;
    private FeatureCollection publicFeatureCollection;
    private FeatureCollection privateFeatureCollection;
    private SymbolLayer mainLayer;
    private SymbolLayer globalLayer;
    private SymbolLayer publicLayer;
    private SymbolLayer privateLayer;
    private Style style;
    private int access = 3;

    private MapFragmentManager(MapView mapView, Bitmap markerIcon) {
        this.mapView = mapView;
        this.markerIcon = markerIcon;

        mapManager = MapManager.getInstance();
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

    public void changeAccess(int newAccess) {
        if (access != newAccess) {
            access = newAccess;
            style.removeLayer(mainLayer);
            replaceLayer();
            style.addLayer(mainLayer);
        }
    }

    private void setStyle() {
        map.setStyle(Style.DARK, style -> {
            style.addImage("marker_icon", markerIcon);
            this.style = style;
            initMainLayer();
            initAdditionalLayers();
            style.addLayer(mainLayer);
        });
    }

    private void initAdditionalLayers() {
        getAdditionalFeatureCollections();
        joinThread(additionalFeatureThread);
        publicLayer = getLayer(publicFeatureCollection, "public");
        privateLayer = getLayer(privateFeatureCollection, "private");
    }

    private void addSource(FeatureCollection collection, String name) {
        joinThread(globalFeatureThread);
        joinThread(additionalFeatureThread);
        style.addSource(new GeoJsonSource("source_" + name, collection,
                new GeoJsonOptions()
                        .withCluster(true)
                        .withClusterMaxZoom(14)
                        .withClusterRadius(60)
        ));
    }

    private SymbolLayer getLayer(FeatureCollection collection, String name) {
        addSource(collection, name);
        return new SymbolLayer("markers_" + name, "source_" + name)
                .withProperties(
                        iconImage("marker_icon"),
                        iconSize(getCircleRadius()),
                        textField(Expression.toString(get("point_count"))),
                        textSize(11f),
                        textColor(Color.BLACK),
                        textIgnorePlacement(true),
                        textAllowOverlap(true));
    }

    private List<Feature> getFeatureList(int privacy) {
        List<Feature> featureList = new ArrayList<>();
        for (MapItem item : mapItemList) {
            if (item.getAccess() < privacy) {
                Point point = Point.fromLngLat(item.getLng(), item.getLat());
                Feature feature = Feature.fromGeometry(point);
                feature.addNumberProperty("access", item.getAccess());
                feature.addStringProperty("id", item.getId() + "");
                featureList.add(feature);
            }
        }
        return featureList;
    }

    private void getGlobalFeatureCollection() {
        globalFeatureThread = new Thread(() -> {
            mapItemList = mapManager.getAllMapItems();
            globalFeatureCollection = FeatureCollection.fromFeatures(getFeatureList(3));
        });
        globalFeatureThread.start();
    }

    private void getAdditionalFeatureCollections() {
        joinThread(globalFeatureThread);
        additionalFeatureThread = new Thread(() -> {
            publicFeatureCollection = FeatureCollection.fromFeatures(getFeatureList(2));
            privateFeatureCollection = FeatureCollection.fromFeatures(getFeatureList(1));
        });
        additionalFeatureThread.start();
    }

    private void initMainLayer() {
        getGlobalFeatureCollection();
        joinThread(globalFeatureThread);
        globalLayer = getLayer(globalFeatureCollection, "global");
        mainLayer = globalLayer;
    }

    private void replaceLayer() {
        joinThread(globalFeatureThread);
        joinThread(additionalFeatureThread);
        switch (access) {
            case 3:
                setMainLayer(globalLayer);
                break;
            case 2:
                setMainLayer(publicLayer);
                break;
            case 1:
                setMainLayer(privateLayer);
                break;
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

    private void initMapView() {
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        map = mapboxMap;
        setStyle();
        map.addOnMapClickListener(this);
    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        PointF screenPoint = map.getProjection().toScreenLocation(point);
        List<Feature> features = map.queryRenderedFeatures(screenPoint, "markers_global", "markers_public", "markers_private");
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

    private void setMainLayer(SymbolLayer layer) {
        mainLayer = layer;
    }

    public void moveCamera(LatLng latLng, double zoom) {
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

    private void joinThread(Thread thread) {
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
            }
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
