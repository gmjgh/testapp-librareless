package com.paulost.testapplibrareless.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Sys implements Parcelable {

    public int type;
    public int id;
    public int sunrise;
    public int sunset;

    protected Sys(Parcel in) {
        type = in.readInt();
        id = in.readInt();
        sunrise = in.readInt();
        sunset = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeInt(id);
        dest.writeInt(sunrise);
        dest.writeInt(sunset);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Sys> CREATOR = new Creator<Sys>() {
        @Override
        public Sys createFromParcel(Parcel in) {
            return new Sys(in);
        }

        @Override
        public Sys[] newArray(int size) {
            return new Sys[size];
        }
    };
}