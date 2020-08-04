package com.paulost.testapplibrareless.presentation.weather.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paulost.testapplibrareless.R;
import com.paulost.testapplibrareless.domain.model.WeatherResponse;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {

    List<WeatherResponse> weatherResponses = new ArrayList<>();

    public WeatherAdapter(List<WeatherResponse> weatherResponses) {
        this.weatherResponses = weatherResponses;
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new WeatherHolder(inflater.inflate(R.layout.item_weather, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        holder.bind(weatherResponses.get(position));
    }

    @Override
    public int getItemCount() {
        return weatherResponses.size();
    }

    class WeatherHolder extends RecyclerView.ViewHolder {

        void bind(WeatherResponse weatherResponse) {
            TextView date = (TextView) itemView.findViewById(R.id.date);
            TextView title = (TextView) itemView.findViewById(R.id.title);
            TextView snippet = (TextView) itemView.findViewById(R.id.snippet);
            date.setText(weatherResponse.dt);
            title.setText(weatherResponse.name);
            snippet.setText(weatherResponse.formatWeatherData());
        }

        public WeatherHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
