package com.example.personaltracker;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.personaltracker.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private static double currentLongitude;
    private static double currentLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
        mMap.addMarker(new MarkerOptions().position(myLoc).title("Marker in My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc));
    }
}