package com.bin.bustest.aty;

import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.BusLineSearchOption;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bin.bustest.BaseApplication;
import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;
import com.bin.bustest.util.BusLineOverlay;

public class BusMapAty extends BaseAty implements OnGetPoiSearchResultListener, OnGetBusLineSearchResultListener {

    private String TAG = BusMapAty.class.getSimpleName();

    private MapView mMapView = null;

    private BaiduMap mBaiduMap;

    private PoiSearch mPoiSearch;

    private String busLineId;

    private BusLineSearch mBusLineSearch;

    private BusLineOverlay overlay;

    private String busName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_bus_map);

        if (getIntent() != null) {
            if (getIntent().getStringExtra("name") != null) {
                busName = getIntent().getStringExtra("name");
            } else {
                busName = "620";
            }
        } else {
            busName = "620";
        }
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();

        mBaiduMap.setMyLocationEnabled(true);
        //显示卫星图层
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        mPoiSearch = PoiSearch.newInstance();

        mBusLineSearch = BusLineSearch.newInstance();

        mPoiSearch.setOnGetPoiSearchResultListener(this);

        mBusLineSearch.setOnGetBusLineSearchResultListener(this);

        overlay = new BusLineOverlay(mBaiduMap);

        mPoiSearch.searchInCity(new PoiCitySearchOption()
                .city(BaseApplication.getCity()) //必填字段
                .keyword(busName) //必填字段
                .scope(2));


    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        //遍历所有POI，找到类型为公交线路的POI
        for (PoiInfo poi : poiResult.getAllPoi()) {
            if (poi.getPoiDetailInfo().getTag().equals("公交车站") || poi.getPoiDetailInfo().getTag().equals("公交线路")) {
                //获取该条公交路线POI的UID
                busLineId = poi.uid;
                break;
            }
        }
        if (busLineId == null || BaseApplication.getCity() == null) {
            mBusLineSearch.searchBusLine(new BusLineSearchOption()
                    .city("天津")
                    .uid("7cee04b90c0014f8b6147ff2"));
        } else {
            mBusLineSearch.searchBusLine(new BusLineSearchOption()
                    .city(BaseApplication.getCity())
                    .uid(busLineId));
        }

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onGetBusLineResult(BusLineResult busLineResult) {
        if (busLineResult == null || busLineResult.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        MapStatusUpdate update = null;
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        Log.e("lyl", "update:" + latLng);
//
//        update = MapStatusUpdateFactory.zoomTo(18f);
//        mBaiduMap.animateMapStatus(update);
//
//        update = MapStatusUpdateFactory.newLatLng(latLng);
//        mBaiduMap.animateMapStatus(update);

        overlay.setData(busLineResult);
        overlay.addToMap();
        overlay.zoomToSpan();
    }


    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

}