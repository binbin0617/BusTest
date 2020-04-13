package com.bin.bustest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;


import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.bin.bustest.aty.BusAty;
import com.bin.bustest.bean.CityBean;
import com.bin.bustest.bean.ShiBusBean;
import com.google.gson.Gson;
import com.kongzue.baseokhttp.HttpRequest;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.kongzue.baseokhttp.util.Parameter;

public class BaseApplication extends Application {

    private static String city;
    private static String cityid;
    private static SharedPreferences preferences;
    private static Boolean isLogin = false;

    public static String getCityid() {
        return cityid;
    }

    public static void setCityid(String cityid) {
        BaseApplication.cityid = cityid;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        BaseApplication.key = key;
    }

    public static String getuName() {
        return uName;
    }

    public static void setuName(String uName) {
        BaseApplication.uName = uName;
    }

    //笑园Api
    private static String uName;
    //笑园Api
    private static String key;


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
        uName = "15810277571";
        key = "93365df5d6772de2054619fc6c6d2b9a";
        preferences = getSharedPreferences("IpAndPort", Activity.MODE_PRIVATE);
        isLogin = preferences.getBoolean("islogin", false);
        HttpRequest.GET(getApplicationContext(), "http://api.dwmm136.cn/z_busapi/BusApi.php",
                new Parameter().add("optype", "city")
                        .add("uname", BaseApplication.getuName()), new ResponseListener() {
                    @Override
                    public void onResponse(String main, Exception error) {
                        if (error == null) {
                            CityBean cityBean = new Gson().fromJson(main, CityBean.class);
                            if (("0").equals(cityBean.getError_code()) && ("ok").equals(cityBean.getReturn_code())) {
                                for (int i = 0; i < cityBean.getReturl_list().size(); i++) {
                                    CityBean.ReturlListBean returlListBean = cityBean.getReturl_list().get(i);
                                    if (returlListBean.getCity().contains(BaseApplication.getCity())) {
                                        cityid = returlListBean.getCityid();
                                        Log.e("-->", "cityid" + cityid);

                                    }
                                }
                            } else {

                            }
                        }
                    }
                });
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }
}
