package com.paulost.testapplibrareless.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class ForecastResponse implements Parcelable {

    public List<WeatherResponse> list;

    protected ForecastResponse(Parcel in) {
        list = in.createTypedArrayList(WeatherResponse.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(list);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ForecastResponse> CREATOR = new Creator<ForecastResponse>() {
        @Override
        public ForecastResponse createFromParcel(Parcel in) {
            return new ForecastResponse(in);
        }

        @Override
        public ForecastResponse[] newArray(int size) {
            return new ForecastResponse[size];
        }
    };
}
