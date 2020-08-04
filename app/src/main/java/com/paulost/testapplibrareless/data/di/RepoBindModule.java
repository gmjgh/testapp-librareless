package com.paulost.testapplibrareless.data.di;

import com.paulost.testapplibrareless.data.locations.LocationsRepoStorage;
import com.paulost.testapplibrareless.data.weather.WeatherRepoRest;
import com.paulost.testapplibrareless.domain.locations.LocationsRepo;
import com.paulost.testapplibrareless.domain.locations.WeatherRepo;

public class RepoBindModule {

    private BaseModule baseModule;

    public RepoBindModule(BaseModule baseModule) {
        this.baseModule = baseModule;
    }

    public LocationsRepo provideLocationsRepoStorage() {
        return new LocationsRepoStorage();
    }

    public WeatherRepo provideWeatherRepoRest() {
        return new WeatherRepoRest(baseModule.provideHttpClient());
    }

}