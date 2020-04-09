package com.bin.bustest;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;

public class BaseApplication extends Application {

    private static String city;
    private static SharedPreferences preferences;
    private static Boolean isLogin = false;

    public static Boolean getIsLogin() {
        return isLogin;
    }

    public static void setIsLogin(Boolean isLogin) {
        BaseApplication.isLogin = isLogin;
    }

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static void setPreferences(SharedPreferences preferences) {
        BaseApplication.preferences = preferences;
    }

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
        preferences = getSharedPreferences("IpAndPort", Activity.MODE_PRIVATE);
        isLogin = preferences.getBoolean("islogin", false);
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
