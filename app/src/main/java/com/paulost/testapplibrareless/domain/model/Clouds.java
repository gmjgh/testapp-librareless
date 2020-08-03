package com.paulost.testapplibrareless.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Clouds implements Parcelable {

    public int all;

    protected Clouds(Parcel in) {
        all = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(all);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Clouds> CREATOR = new Creator<Clouds>() {
        @Override
        public Clouds createFromParcel(Parcel in) {
            return new Clouds(in);
        }

        @Override
        public Clouds[] newArray(int size) {
            return new Clouds[size];
        }
    };
}