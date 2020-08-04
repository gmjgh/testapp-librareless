package com.paulost.testapplibrareless.presentation.weather;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.paulost.testapplibrareless.R;
import com.paulost.testapplibrareless.databinding.FragmentWeatherBinding;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;
import com.paulost.testapplibrareless.presentation.base.BaseFragment;
import com.paulost.testapplibrareless.presentation.weather.list.WeatherAdapter;

// everything here is not very combed due to the lack of time and absence of nice libraries
public class WeatherFragment extends BaseFragment<FragmentWeatherBinding, WeatherViewModel> {
    @Nullable
    @Override
    public Class<WeatherViewModel> provideViewModelClass() {
        return WeatherViewModel.class;
    }

    WeatherAdapter adapter;
    RecyclerView list;

    @Override
    protected void onViewInit(View view, Bundle savedInstanceState) {
        WeatherResponse response = getArguments().getParcelable("weatherResponse");
        if (response != null) {
            viewModel.title.set(response.name);
            viewModel.snippet.set(response.formatWeatherData());
            viewModel.currentResponse = response;
            showProgress();
            viewModel.loadForecastForLocation(new LatLng(response.coord.lat, response.coord.lon));
        }
        list = view.findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(activityContext, RecyclerView.VERTICAL, false));

    }

    @Override
    protected void listenDataChanges() {
        viewModel.forecastResponseLiveData.observe(this, forecastResponseEvent -> {
            hideProgress();
            if (!forecastResponseEvent.isConsumed()) {
                adapter = new WeatherAdapter(forecastResponseEvent.consume().list);
                list.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_weather;
    }
}
