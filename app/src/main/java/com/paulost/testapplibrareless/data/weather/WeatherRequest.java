package com.paulost.testapplibrareless.data.weather;

import android.util.Log;

import com.google.gson.Gson;
import com.paulost.testapplibrareless.BuildConfig;
import com.paulost.testapplibrareless.data.base.API;
import com.paulost.testapplibrareless.data.base.HttpRequest;
import com.paulost.testapplibrareless.data.base.ResponseCallback;
import com.paulost.testapplibrareless.domain.model.Units;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

import java.util.concurrent.ExecutorService;

public class WeatherRequest extends HttpRequest<WeatherResponse> {
    double lat;
    double lon;
    Units units;

    public WeatherRequest(ResponseCallback<WeatherResponse> responseCallback, double lat, double lon, Units units) {
        super(responseCallback);
        this.lat = lat;
        this.lon = lon;
        this.units = units;
    }

    @Override
    public String getUrlString() {
        return BuildConfig.BASE_ENDPOINT + API.WEATHER + "?" + "appid=" + BuildConfig.API_TOKEN + "&lat=" + lat + "&lon=" + lon + "&units=" + units.name();
    }

    @Override
    public void loadData(ExecutorService executorService) {
        try {
            String data = execute(executorService).get();
            Log.d("WeatherResponse loaded", data);
            WeatherResponse result = new Gson().fromJson(data, WeatherResponse.class);
            success(result);
        } catch (Exception e) {
            Log.d("WeatherResponse failed", e.toString());
            failure(e);
        }
    }
}
