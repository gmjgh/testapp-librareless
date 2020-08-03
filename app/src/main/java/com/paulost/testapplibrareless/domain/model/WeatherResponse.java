package com.paulost.testapplibrareless.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class WeatherResponse implements Parcelable {

    public Coord coord;
    public List<Weather> weather;
    public String base;
    public MainData main;
    public int visibility;
    public Wind wind;
    public Clouds clouds;
    public int dt;
    public Sys sys;
    public int timezone;
    public int id;
    public String name;
    public int cod;

    protected WeatherResponse(Parcel in) {
        coord = in.readParcelable(Coord.class.getClassLoader());
        weather = in.createTypedArrayList(Weather.CREATOR);
        base = in.readString();
        main = in.readParcelable(MainData.class.getClassLoader());
        visibility = in.readInt();
        wind = in.readParcelable(Wind.class.getClassLoader());
        clouds = in.readParcelable(Clouds.class.getClassLoader());
        dt = in.readInt();
        sys = in.readParcelable(Sys.class.getClassLoader());
        timezone = in.readInt();
        id = in.readInt();
        name = in.readString();
        cod = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(coord, flags);
        dest.writeTypedList(weather);
        dest.writeString(base);
        dest.writeParcelable(main, flags);
        dest.writeInt(visibility);
        dest.writeParcelable(wind, flags);
        dest.writeParcelable(clouds, flags);
        dest.writeInt(dt);
        dest.writeParcelable(sys, flags);
        dest.writeInt(timezone);
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(cod);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WeatherResponse> CREATOR = new Creator<WeatherResponse>() {
        @Override
        public WeatherResponse createFromParcel(Parcel in) {
            return new WeatherResponse(in);
        }

        @Override
        public WeatherResponse[] newArray(int size) {
            return new WeatherResponse[size];
        }
    };
}
