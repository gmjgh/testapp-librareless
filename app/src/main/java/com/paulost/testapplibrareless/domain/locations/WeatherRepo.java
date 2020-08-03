package com.paulost.testapplibrareless.domain.locations;

import android.location.Location;

import com.paulost.testapplibrareless.domain.model.ForecastResponse;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

public interface WeatherRepo {

    public WeatherResponse getWeatherForLocation(Location location);

    public ForecastResponse getFiveDaysForecastForLocation(Location location);

}
