package com.paulost.testapplibrareless.presentation.weather;

import android.app.Application;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.paulost.testapplibrareless.App;
import com.paulost.testapplibrareless.data.base.ResponseCallback;
import com.paulost.testapplibrareless.domain.locations.WeatherRepo;
import com.paulost.testapplibrareless.domain.model.ForecastResponse;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;
import com.paulost.testapplibrareless.presentation.base.BaseViewModel;
import com.paulost.testapplibrareless.presentation.base.Event;

public class WeatherViewModel extends BaseViewModel {

    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> snippet = new ObservableField<>("");
    public WeatherResponse currentResponse;

    private WeatherRepo repo;

    public WeatherViewModel(Application application) {
        super(application);
        repo = App.get().provideRepoBindModule().provideWeatherRepoRest();
    }

    private ResponseCallback<ForecastResponse> forecastResponseCallback = new ResponseCallback<ForecastResponse>() {
        @Override
        public void success(ForecastResponse data) {
            forecastResponseLiveData.postValue(new Event<>(data));
        }

        @Override
        public void failure(Exception e) {
            error.postValue(new Event<>(e.getMessage()));
        }
    };

    MutableLiveData<Event<ForecastResponse>> forecastResponseLiveData = new MutableLiveData<>();
    MutableLiveData<Event<String>> error = new MutableLiveData<>();

    public void loadForecastForLocation(LatLng location) {
        repo.getFiveDaysForecastForLocation(location, forecastResponseCallback);
    }
}
