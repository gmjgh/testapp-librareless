
package com.paulost.testapplibrareless.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MainData implements Parcelable {

    public double temp;
    public double feelsLike;
    public double tempMin;
    public int tempMax;
    public int pressure;
    public int humidity;

    protected MainData(Parcel in) {
        temp = in.readDouble();
        feelsLike = in.readDouble();
        tempMin = in.readDouble();
        tempMax = in.readInt();
        pressure = in.readInt();
        humidity = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(temp);
        dest.writeDouble(feelsLike);
        dest.writeDouble(tempMin);
        dest.writeInt(tempMax);
        dest.writeInt(pressure);
        dest.writeInt(humidity);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MainData> CREATOR = new Creator<MainData>() {
        @Override
        public MainData createFromParcel(Parcel in) {
            return new MainData(in);
        }

        @Override
        public MainData[] newArray(int size) {
            return new MainData[size];
        }
    };
}
