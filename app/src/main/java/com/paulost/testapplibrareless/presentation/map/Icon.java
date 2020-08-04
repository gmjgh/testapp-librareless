package com.paulost.testapplibrareless.presentation.map;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

public class Icon {
    LatLng location;
    BitmapDescriptor icon;
    WeatherResponse weatherResponse;

    public Icon(LatLng location, BitmapDescriptor icon, WeatherResponse weatherResponse) {
        this.location = location;
        this.icon = icon;
        this.weatherResponse = weatherResponse;
    }
}
