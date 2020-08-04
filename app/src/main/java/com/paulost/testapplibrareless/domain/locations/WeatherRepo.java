package com.paulost.testapplibrareless.domain.locations;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.paulost.testapplibrareless.data.base.ResponseCallback;
import com.paulost.testapplibrareless.domain.model.ForecastResponse;
import com.paulost.testapplibrareless.domain.model.Units;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

public interface WeatherRepo {

    public void getWeatherForLocation(LatLng location, ResponseCallback<WeatherResponse> callback);

    public void getWeatherForLocation(LatLng location, Units units, ResponseCallback<WeatherResponse> callback);

    public void getFiveDaysForecastForLocation(LatLng location, ResponseCallback<ForecastResponse> callback);

    public void getFiveDaysForecastForLocation(LatLng location, Units units, ResponseCallback<ForecastResponse> callback);

    public void loadImage(String urlString, ResponseCallback<Bitmap> callback);

}
