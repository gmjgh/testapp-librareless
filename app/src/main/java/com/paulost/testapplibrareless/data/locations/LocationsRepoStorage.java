package com.paulost.testapplibrareless.data.locations;

import android.location.Location;

import com.paulost.testapplibrareless.data.base.BaseRepo;
import com.paulost.testapplibrareless.domain.locations.LocationsRepo;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

public class LocationsRepoStorage extends BaseRepo implements LocationsRepo {
    @Override
    public WeatherResponse getWeatherForLocation(Location location) {
        return null;
    }
}
