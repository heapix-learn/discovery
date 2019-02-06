package com.heapixLearn.discovery.ui.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.heapixLearn.discovery.R;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.expressions.Expression;
import com.mapbox.mapboxsdk.style.layers.CircleLayer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonOptions;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.expressions.Expression.exponential;
import static com.mapbox.mapboxsdk.style.expressions.Expression.get;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.gte;
import static com.mapbox.mapboxsdk.style.expressions.Expression.has;
import static com.mapbox.mapboxsdk.style.expressions.Expression.interpolate;
import static com.mapbox.mapboxsdk.style.expressions.Expression.literal;
import static com.mapbox.mapboxsdk.style.expressions.Expression.log2;
import static com.mapbox.mapboxsdk.style.expressions.Expression.lt;
import static com.mapbox.mapboxsdk.style.expressions.Expression.stop;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleBlur;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.circleRadius;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textAnchor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textField;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textSize;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.textTranslate;

public class MapFragmentManager implements OnMapReadyCallback {
    private MapView mapView;
    private Context context;
    private List<MapItem> mapItemList;
    private MapManager mapManager;
    private MapboxMap map;
    private Thread featureThread;
    private FeatureCollection featureCollection;

    private static MapFragmentManager instance;

    public static synchronized MapFragmentManager getInstance() {
        return instance;
    }

    public static synchronized MapFragmentManager getInstance(Context context) {
        if (instance == null) {
            instance = new MapFragmentManager(context);
        }
        return instance;
    }

    private MapFragmentManager(Context context) {
        this.context = context;

        mapManager = new MapManager();
        getMapItemList();
        initMapView();
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
        mapView = new MapView(context);
        mapView.onCreate(null);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        mapboxMap.setStyle(Style.DARK, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                style.addImage("marker_icon", getBitmap());
                addSource(style);
                addBaseLayer(style);
                addClusterLayer(style);
                addCountLayer(style);
            }
        });
    }

    private Bitmap getBitmap() {
        Drawable drawable = context.getResources().getDrawable(R.drawable.map_marker);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void addSource(Style style) {
        joinThread(featureThread);
        style.addSource(new GeoJsonSource("marker-source", featureCollection,
                new GeoJsonOptions()
                        .withCluster(true)
                        .withClusterMaxZoom(14)
                        .withClusterRadius(100)
        ));
    }

    private void getFeatureCollection(){
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
                stop(100, 2f),
                stop(500, 3f),
                stop(1000, 4f),
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

    public View getMapView() {
        return mapView;
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
