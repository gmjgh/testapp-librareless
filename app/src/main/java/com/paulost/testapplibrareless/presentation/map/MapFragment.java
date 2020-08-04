package com.paulost.testapplibrareless.presentation.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paulost.testapplibrareless.App;
import com.paulost.testapplibrareless.databinding.FragmentMapBinding;
import com.paulost.testapplibrareless.presentation.base.BaseFragment;

public class MapFragment extends BaseFragment<FragmentMapBinding, MapViewModel> implements OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_CODE = 13;

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    public Class<MapViewModel> provideViewModelClass() {
        return MapViewModel.class;
    }

    @Override
    protected void beforeViewInit() {
        fusedLocationProviderClient = App.get().provideBaseModule().provideFusedLocationClient(activityContext);
    }

    @Override
    protected void onViewInit(View view, Bundle savedInstanceState) {
        requestPermission();
    }

    @Override
    protected void listenDataChanges() {
        viewModel.iconData.observe(this, bitmapDescriptorEvent -> {
            hideProgress();
        });

        viewModel.error.observe(this, stringEvent -> {
            hideProgress();
        });

        viewModel.weatherResponseLiveData.observe(this, weatherResponseEvent -> {
            hideProgress();
        });
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    activityContext, Manifest.permission.ACCESS_FINE_LOCATION) ==
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

    private void sendRequest() {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(activityContext, location -> {
                    Log.d("Location is null ", "!" + (location == null) + "!");
                    if (location != null) {
                        showProgress();
                        viewModel.loadWeatherForLocation(location);
                    }
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void addMarker(Location location, BitmapDescriptor icon, String name) {
        LatLng markerPosition = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(markerPosition).title(name).icon(icon));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(markerPosition));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE)
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendRequest();
            }
    }
}
