package com.bin.bustest.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationBean implements Parcelable {
    private double latitude;

    private double longitude;

    private float radius;

    private float direction;

    public LocationBean(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        radius = in.readFloat();
        direction = in.readFloat();
    }

    public static final Creator<LocationBean> CREATOR = new Creator<LocationBean>() {
        @Override
        public LocationBean createFromParcel(Parcel in) {
            return new LocationBean(in);
        }

        @Override
        public LocationBean[] newArray(int size) {
            return new LocationBean[size];
        }
    };

    public LocationBean() {

    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getDirection() {
        return direction;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeFloat(radius);
        dest.writeFloat(direction);
    }
}
