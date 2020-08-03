package com.paulost.testapplibrareless.data.weather;

import android.graphics.Bitmap;

import com.paulost.testapplibrareless.data.base.ImageRequest;
import com.paulost.testapplibrareless.data.base.ResponseCallback;

public class WeatherImageRequest extends ImageRequest {
    String url;

    public WeatherImageRequest(ResponseCallback<Bitmap> responseCallback, String url) {
        super(responseCallback);
        this.url = url;
    }

    @Override
    protected String getUrlString() {
        return url;
    }
}
