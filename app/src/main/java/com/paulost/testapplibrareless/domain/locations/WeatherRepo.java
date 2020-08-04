package com.paulost.testapplibrareless.domain.locations;

import android.graphics.Bitmap;
import android.location.Location;

import com.paulost.testapplibrareless.data.base.ResponseCallback;
import com.paulost.testapplibrareless.domain.model.ForecastResponse;
import com.paulost.testapplibrareless.domain.model.Units;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

public interface WeatherRepo {

    public void getWeatherForLocation(Location location, ResponseCallback<WeatherResponse> callback);

    public void getWeatherForLocation(Location location, Units units, ResponseCallback<WeatherResponse> callback);

    public void getFiveDaysForecastForLocation(Location location, ResponseCallback<ForecastResponse> callback);

    public void getFiveDaysForecastForLocation(Location location, Units units, ResponseCallback<ForecastResponse> callback);

    public void loadImage(String urlString, ResponseCallback<Bitmap> callback);

}
