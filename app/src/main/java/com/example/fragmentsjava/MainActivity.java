package com.example.fragmentsjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.fragmentsjava.databinding.ActivityMainBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap maps;

    ActivityMainBinding mBinding;

    Marker marker;

    CameraPosition cameraPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        maps = googleMap;

        maps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng point) {

                addMarker(point, "Title");
                animateCamera(point);
            }
        });

    }

    private void addMarker(LatLng latLng, String title) {
        if (marker != null)
            marker.remove();
        marker = maps.addMarker(new MarkerOptions().position(latLng).title(title));
    }

    private void animateCamera(LatLng latLng) {
        if (marker == null) {
            cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(maps.getCameraPosition().zoom)
                    .build();
            maps.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        } else {
            cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(7)
                    .build();
            maps.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }
    }



}