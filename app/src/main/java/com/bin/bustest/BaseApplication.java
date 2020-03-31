package com.bin.bustest;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(this);
    }
}
