package com.paulost.testapplibrareless.presentation.map;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.paulost.testapplibrareless.App;
import com.paulost.testapplibrareless.data.base.ResponseCallback;
import com.paulost.testapplibrareless.domain.locations.WeatherRepo;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;
import com.paulost.testapplibrareless.presentation.base.BaseViewModel;
import com.paulost.testapplibrareless.presentation.base.Event;

public class MapViewModel extends BaseViewModel {

    private WeatherRepo repo;
    public ObservableField<String> title = new ObservableField<>("");
    public ObservableField<String> snippet = new ObservableField<>("");
    public ObservableField<Bitmap> icon = new ObservableField<>();
    public WeatherResponse currentResponse;

    public ObservableArrayList<WeatherResponse> items = new ObservableArrayList<>();

    public MapViewModel(Application application) {
        super(application);
        repo = App.get().provideRepoBindModule().provideWeatherRepoRest();
    }

    private ResponseCallback<WeatherResponse> weatherResponseResponseCallback = new ResponseCallback<WeatherResponse>() {
        @Override
        public void success(WeatherResponse data) {
            weatherResponseLiveData.postValue(new Event<>(data));
            if (data.weather != null && !data.weather.isEmpty())
                loadWeatherImage(data.weather.get(0).getFormattedIcon(), new LatLng(data.coord.lat, data.coord.lon), data);
        }

        @Override
        public void failure(Exception e) {
            error.postValue(new Event<>(e.getMessage()));
        }
    };


    MutableLiveData<Event<Icon>> iconData = new MutableLiveData<>();
    MutableLiveData<Event<WeatherResponse>> weatherResponseLiveData = new MutableLiveData<>();
    MutableLiveData<Event<String>> error = new MutableLiveData<>();

    public void loadWeatherForLocation(LatLng location) {
        repo.getWeatherForLocation(location, weatherResponseResponseCallback);
    }

    private void loadWeatherImage(String url, LatLng latLng, WeatherResponse weather) {
        repo.loadImage(url, new ResponseCallback<Bitmap>() {
            @Override
            public void success(Bitmap data) {
                weather.icon = data;
                iconData.postValue(new Event<>(new Icon(latLng, BitmapDescriptorFactory.fromBitmap(data), weather)));
            }

            @Override
            public void failure(Exception e) {
                error.postValue(new Event<>(e.getMessage()));
            }
        });
    }

}
