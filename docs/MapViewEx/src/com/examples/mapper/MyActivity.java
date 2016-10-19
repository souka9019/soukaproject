package com.examples.mapper;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

public class MyActivity extends MapActivity {

    MapView map;
    MapController controller;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        map = (MapView)findViewById(R.id.map);
        controller = map.getController();
        
        ArrayList<GeoPoint> locations = new ArrayList<GeoPoint>();
        ArrayList<Drawable> images = new ArrayList<Drawable>();
        //Google HQ 37.427,-122.099
        locations.add(new GeoPoint(35672428,139480501));
        images.add(getResources().getDrawable(R.drawable.ic_launcher));
        //Subtract 0.01 degrees
        locations.add(new GeoPoint(35669883,139481151));
        images.add(getResources().getDrawable(R.drawable.logo));
        //Add 0.01 degrees
        locations.add(new GeoPoint(35669452,139478125));
        images.add(getResources().getDrawable(R.drawable.ic_launcher));
        
        LocationOverlay myOverlay = new LocationOverlay(this, getResources().getDrawable(R.drawable.ic_launcher));
        myOverlay.setItems(locations, images);
        map.getOverlays().add(myOverlay);
        controller.setCenter(locations.get(0));
        controller.setZoom(17);
    }
    
    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}