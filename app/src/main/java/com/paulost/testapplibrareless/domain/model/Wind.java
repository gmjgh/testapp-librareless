package com.paulost.testapplibrareless.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Wind implements Parcelable {

    public Double speed;
    public Integer deg;

    protected Wind(Parcel in) {
        if (in.readByte() == 0) {
            speed = null;
        } else {
            speed = in.readDouble();
        }
        if (in.readByte() == 0) {
            deg = null;
        } else {
            deg = in.readInt();
        }

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (speed == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(speed);
        }
        if (deg == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(deg);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Wind> CREATOR = new Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };
}