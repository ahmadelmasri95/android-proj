package com.example.personaltracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.personaltracker.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static double currentLongitude;
    private static double currentLatitude;
    List<Pair<Double, Double>> mostVisitedPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mostVisitedPlaces = new ArrayList<Pair<Double, Double>>();
        mostVisitedPlaces.add(new Pair<>(51.0751181764925, -114.14846090472763)); //Alberta Children's Hospital
        mostVisitedPlaces.add(new Pair<>(51.07196001214994, -114.14182386981658)); //West Campus Park

        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Intent intent = getIntent();
        currentLongitude = intent.getDoubleExtra("Longitude", 0.0);
        currentLatitude = intent.getDoubleExtra("Latitude", 0.0);
        // Add a marker for My current location and move the camera
        LatLng myLoc = new LatLng(currentLatitude, currentLongitude);
        LatLng first_place = new LatLng(mostVisitedPlaces.get(0).first, mostVisitedPlaces.get(0).second);
        LatLng second_place = new LatLng(mostVisitedPlaces.get(1).first, mostVisitedPlaces.get(1).second);

        mMap.addMarker(new MarkerOptions().position(myLoc).title("Marker in My Location"));
        mMap.addMarker(new MarkerOptions().position(first_place).title("Marker in My Location"));
        mMap.addMarker(new MarkerOptions().position(second_place).title("Marker in My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
    }
}