
package com.paulost.testapplibrareless.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Coord implements Parcelable {

    public double lon;
    public double lat;

    protected Coord(Parcel in) {
        lon = in.readDouble();
        lat = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lon);
        dest.writeDouble(lat);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Coord> CREATOR = new Creator<Coord>() {
        @Override
        public Coord createFromParcel(Parcel in) {
            return new Coord(in);
        }

        @Override
        public Coord[] newArray(int size) {
            return new Coord[size];
        }
    };
}
