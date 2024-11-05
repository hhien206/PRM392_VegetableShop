package com.example.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap gmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_map);
        mapFragment.getMapAsync(this);


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        gmap = googleMap;
        LatLng location = new LatLng(10.84559766123562, 106.79380889662852);
        googleMap.addMarker((new MarkerOptions().position(location).title("HA NOI")));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,20));

        gmap.getUiSettings().setZoomGesturesEnabled(true); // Cử chỉ zoom
        gmap.getUiSettings().setScrollGesturesEnabled(true); // Cử chỉ cuộn
        gmap.getUiSettings().setZoomControlsEnabled(true); // Hiển thị nút zoom
        gmap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true); // Cho phép cuộn khi đang phóng to/thu nhỏ hoặc xoay

        // Thiết lập mức độ zoom tối thiểu và tối đa (tuỳ chọn)
        gmap.setMinZoomPreference(5.0f);
        gmap.setMaxZoomPreference(20.0f);
    }
}
