package com.paulost.testapplibrareless.data.weather;

import android.location.Location;

import com.paulost.testapplibrareless.domain.locations.WeatherRepo;
import com.paulost.testapplibrareless.domain.model.ForecastResponse;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

public class WeatherRepoRest implements WeatherRepo {
    @Override
    public WeatherResponse getWeatherForLocation(Location location) {
        return null;
    }

    @Override
    public ForecastResponse getFiveDaysForecastForLocation(Location location) {
        return null;
    }
}
