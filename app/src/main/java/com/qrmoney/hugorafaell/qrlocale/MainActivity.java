package com.qrmoney.hugorafaell.qrlocale;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String latitude;
    private String longitude;
    private String cidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ArrayList<String> locais = getIntent().getStringArrayListExtra("locais");
        ArrayList<String> latitudes = getIntent().getStringArrayListExtra("latitudes");
        ArrayList<String> longitudes = getIntent().getStringArrayListExtra("longitudes");
        for (int i = 0; i <locais.size(); i++) {
            LatLng radMas = new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i)));
            mMap.addMarker(new MarkerOptions().position(radMas).title(locais.get(i))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(radMas).zoom(15).build()));
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latitudes.get(i)), Double.parseDouble(longitudes.get(i))))
                    .title(locais.get(i)).snippet("Centro de recarga"));
        }
    }
}
