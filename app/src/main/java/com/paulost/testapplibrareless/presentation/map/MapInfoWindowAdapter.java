package com.paulost.testapplibrareless.presentation.map;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.paulost.testapplibrareless.R;

public class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private View infoWindow = null;
    private LayoutInflater inflater = null;

    MapInfoWindowAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return (null);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        if (infoWindow == null) {
            infoWindow = inflater.inflate(R.layout.map_info_window, null);
        }

        TextView title = (TextView) infoWindow.findViewById(R.id.title);
        TextView snippet = (TextView) infoWindow.findViewById(R.id.snippet);
        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());

        return (infoWindow);
    }
}
