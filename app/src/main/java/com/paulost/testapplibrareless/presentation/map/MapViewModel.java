package com.paulost.testapplibrareless.presentation.map;

import android.graphics.Bitmap;
import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.paulost.testapplibrareless.App;
import com.paulost.testapplibrareless.data.base.ResponseCallback;
import com.paulost.testapplibrareless.domain.locations.WeatherRepo;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;
import com.paulost.testapplibrareless.presentation.base.BaseViewModel;
import com.paulost.testapplibrareless.presentation.base.Event;

public class MapViewModel extends BaseViewModel {

    private WeatherRepo repo;

    public MapViewModel() {
        repo = App.get().provideRepoBindModule().provideWeatherRepoRest();
    }

    private ResponseCallback<WeatherResponse> weatherResponseResponseCallback = new ResponseCallback<WeatherResponse>() {
        @Override
        public void success(WeatherResponse data) {
            weatherResponseLiveData.postValue(new Event<>(data));
            if (data.weather != null && !data.weather.isEmpty())
                loadWeatherImage(data.weather.get(0).getFormattedIcon());
        }

        @Override
        public void failure(Exception e) {
            error.postValue(new Event<>(e.getMessage()));
        }
    };

    private ResponseCallback<Bitmap> iconResponseCallback = new ResponseCallback<Bitmap>() {
        @Override
        public void success(Bitmap data) {
            iconData.postValue(new Event<>(BitmapDescriptorFactory.fromBitmap(data)));
        }

        @Override
        public void failure(Exception e) {
            error.postValue(new Event<>(e.getMessage()));
        }
    };

    MutableLiveData<Event<BitmapDescriptor>> iconData = new MutableLiveData<>();
    MutableLiveData<Event<WeatherResponse>> weatherResponseLiveData = new MutableLiveData<>();
    MutableLiveData<Event<String>> error = new MutableLiveData<>();

    public void loadWeatherForLocation(Location location) {
        repo.getWeatherForLocation(location, weatherResponseResponseCallback);
    }

    private void loadWeatherImage(String url) {
        repo.loadImage(url, iconResponseCallback);
    }

}
