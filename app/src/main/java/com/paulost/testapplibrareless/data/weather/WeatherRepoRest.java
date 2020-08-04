package com.paulost.testapplibrareless.data.weather;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;
import com.paulost.testapplibrareless.data.base.HttpClient;
import com.paulost.testapplibrareless.data.base.ResponseCallback;
import com.paulost.testapplibrareless.domain.locations.WeatherRepo;
import com.paulost.testapplibrareless.domain.model.ForecastResponse;
import com.paulost.testapplibrareless.domain.model.Units;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

public class WeatherRepoRest implements WeatherRepo {

    private HttpClient client;

    public WeatherRepoRest(HttpClient client) {
        this.client = client;
    }

    @Override
    public void getWeatherForLocation(LatLng location, ResponseCallback<WeatherResponse> callback) {
        getWeatherForLocation(location, Units.metric, callback);
    }

    @Override
    public void getWeatherForLocation(LatLng location, Units units, ResponseCallback<WeatherResponse> callback) {
        client.getCurrentWeather(callback, location.latitude, location.longitude, units);
    }

    @Override
    public void getFiveDaysForecastForLocation(LatLng location, ResponseCallback<ForecastResponse> callback) {
        getFiveDaysForecastForLocation(location, Units.metric, callback);
    }

    @Override
    public void getFiveDaysForecastForLocation(LatLng location, Units units, ResponseCallback<ForecastResponse> callback) {
        client.getFiveDaysForecast(callback, location.latitude, location.longitude, units);
    }

    @Override
    public void loadImage(String urlString, ResponseCallback<Bitmap> callback) {
        client.loadImage(callback, urlString);
    }
}
