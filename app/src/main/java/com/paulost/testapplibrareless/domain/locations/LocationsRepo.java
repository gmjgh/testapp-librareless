package com.paulost.testapplibrareless.domain.locations;

import android.location.Location;

import com.paulost.testapplibrareless.domain.model.WeatherResponse;

public interface LocationsRepo {

    public WeatherResponse getWeatherForLocation(Location location);

}
