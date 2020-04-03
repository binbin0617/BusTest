package com.bin.bustest;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

public class BaseApplication extends Application {

    private static String city ;

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        BaseApplication.city = city;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        city = "上海";
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
