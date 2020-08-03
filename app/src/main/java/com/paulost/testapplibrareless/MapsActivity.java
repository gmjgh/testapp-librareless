package com.paulost.testapplibrareless;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paulost.testapplibrareless.data.base.ResponseCallback;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_CODE = 13;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        requestPermission();

    }

    void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
                sendRequest();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE);
            }
        } else {
            sendRequest();
        }
    }

    ResponseCallback<WeatherResponse> weatherResponseResponseCallback = new ResponseCallback<WeatherResponse>() {
        @Override
        public void success(WeatherResponse data) {
            Toast.makeText(MapsActivity.this, data.name, Toast.LENGTH_LONG).show();
        }

        @Override
        public void failure(Exception e) {
            Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    void sendRequest() {
        App.get().provideBaseModule().provideFusedLocationClient(this).getLastLocation()
                .addOnSuccessListener(this, location -> {
                    Log.d("Location", "!" + (location == null) + "!");
                    if (location != null) {
                        App.get().provideBaseModule().provideHttpClient().getCurrentWeather(weatherResponseResponseCallback, location.getLatitude(), location.getLongitude());
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return;
        }
    }
}
