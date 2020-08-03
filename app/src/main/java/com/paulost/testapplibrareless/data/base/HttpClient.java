package com.paulost.testapplibrareless.data.base;

import com.paulost.testapplibrareless.data.weather.ForecastRequest;
import com.paulost.testapplibrareless.data.weather.WeatherRequest;
import com.paulost.testapplibrareless.domain.model.ForecastResponse;
import com.paulost.testapplibrareless.domain.model.Units;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpClient {

    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public void getCurrentWeather(ResponseCallback<WeatherResponse> responseCallback, double lat, double lon) {
        getCurrentWeather(responseCallback, lat, lon, Units.metric);
    }

    public void getCurrentWeather(ResponseCallback<WeatherResponse> responseCallback, double lat, double lon, Units units) {
        new WeatherRequest(responseCallback, lat, lon, units).loadData(executor);
    }

    public void getFiveDaysForecast(ResponseCallback<ForecastResponse> responseCallback, double lat, double lon) {
        getFiveDaysForecast(responseCallback, lat, lon, Units.metric);
    }

    public void getFiveDaysForecast(ResponseCallback<ForecastResponse> responseCallback, double lat, double lon, Units units) {
        new ForecastRequest(responseCallback, lat, lon, units).loadData(executor);
    }

}
