package com.paulost.testapplibrareless.presentation.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.paulost.testapplibrareless.App;
import com.paulost.testapplibrareless.R;
import com.paulost.testapplibrareless.databinding.FragmentMapBinding;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;
import com.paulost.testapplibrareless.presentation.base.BaseFragment;

// everything here is not very combed due to the lack of time and absence of nice libraries
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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        view.findViewById(R.id.view_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Bundle data = new Bundle();
                    data.putParcelable("weatherResponse", viewModel.currentResponse);
                    NavHostFragment.findNavController(MapFragment.this).navigate(R.id.action_fragment_map_to_fragment_weather, data);
            }
        });
    }

    @Override
    protected void listenDataChanges() {
        viewModel.iconData.observe(this, iconEvent -> {
            hideProgress();
            if (!iconEvent.isConsumed()) {
                Icon icon = iconEvent.consume();
                addMarker(icon);
            }
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
                getCurrentLocation();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE);
            }
        } else {
            getCurrentLocation();
        }
    }

    private void getCurrentLocation() {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(activityContext, location -> {
                    Log.d("Location is null ", "!" + (location == null) + "!");
                    if (location != null) {
                        sendRequest(new LatLng(location.getLatitude(), location.getLongitude()));
                    }
                });
    }

    private void sendRequest(LatLng location) {
        showProgress();
        viewModel.loadWeatherForLocation(location);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new MapInfoWindowAdapter(getLayoutInflater()));
        mMap.setOnMapClickListener(this::sendRequest);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                viewModel.title.set(((WeatherResponse)marker.getTag()).name);
                viewModel.snippet.set(((WeatherResponse)marker.getTag()).formatWeatherData());
                viewModel.icon.set(((WeatherResponse)marker.getTag()).icon);
                viewModel.currentResponse = ((WeatherResponse)marker.getTag());

                return false;
            }
        });
        requestPermission();
    }

    private void addMarker(Icon icon) {
        Marker marker = null;
        try {
            marker = mMap.addMarker(new MarkerOptions().position(icon.location).title(icon.weatherResponse.name).snippet(icon.weatherResponse.formatWeatherData()).icon(icon.icon));
        } catch (Exception e) {
            marker = mMap.addMarker(new MarkerOptions().position(icon.location).title(icon.weatherResponse.name).snippet(icon.weatherResponse.formatWeatherData()));
        }
        marker.setTag(icon.weatherResponse);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(icon.location, 10));
        viewModel.title.set(icon.weatherResponse.name);
        viewModel.snippet.set(icon.weatherResponse.formatWeatherData());
        viewModel.icon.set(icon.weatherResponse.icon);
        viewModel.currentResponse = icon.weatherResponse;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE)
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_map;
    }
}
