package com.paulost.testapplibrareless.data.di;

import android.content.Context;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.paulost.testapplibrareless.App;
import com.paulost.testapplibrareless.data.base.HttpClient;

public class BaseModule {

    private HttpClient httpClient;

    public BaseModule() {
        this.httpClient = new HttpClient();
    }

    public App provideApp() {
        return App.get();
    }

    public Context provideContext() {
        return provideApp().getApplicationContext();
    }

    public FusedLocationProviderClient provideFusedLocationClient(Context activityContext) {
        return LocationServices.getFusedLocationProviderClient(activityContext);
    }

    public HttpClient provideHttpClient() {
        return httpClient;
    }

}