package com.bin.bustest.fgt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
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
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.bin.bustest.R;
import com.bin.bustest.aty.MainAty;
import com.bin.bustest.bean.LocationBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class SecondFragment extends Fragment implements OnGetBusLineSearchResultListener {

    private String TAG = SecondFragment.class.getSimpleName();

    private PoiSearch mPoiSearch;

    private BusLineSearch mBusLineSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_first, container, false);
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LocationBean locationBean) {
        Log.e(TAG, "-->" + locationBean.getLatitude() + "aaaaaaa");
        initView(locationBean);
    }

    private void initView(LocationBean locationBean) {

        mPoiSearch = PoiSearch.newInstance();

//        mBusLineSearch = BusLineSearch.newInstance();
//
//        mBusLineSearch.setOnGetBusLineSearchResultListener(this);

        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

        mPoiSearch.searchInCity((new PoiCitySearchOption())
                .city("天津")
                .keyword("ktv")
                .pageNum(10));
//        PoiNearbySearchOption option = new PoiNearbySearchOption();
//        option.keyword("华苑");
//        option.sortType(PoiSortType.distance_from_near_to_far);
//        option.location(new LatLng(locationBean.getLatitude(), locationBean.getLongitude()));
////        if (radius != 0) {
////            option.radius(radius);
////        } else {
//        option.radius(1000);
////        }
//
//        option.pageCapacity(20);
//        mPoiSearch.searchNearby(option);
    }


    //周边
    private OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getContext(), "OnGetPoiSearchResultListener抱歉，未找到结果", Toast.LENGTH_LONG).show();
                return;
            }
            // 遍历所有poi，找到类型为公交线路的poi
            for (PoiInfo poi : poiResult.getAllPoi()) {
                if (poi.type == PoiInfo.POITYPE.BUS_LINE
                        || poi.type == PoiInfo.POITYPE.SUBWAY_LINE) {
                    //如下代码为发起检索代码，定义监听者和设置监听器的方法与POI中的类似
                    mBusLineSearch.searchBusLine((new BusLineSearchOption()
                            //我这里的城市写死了，和我要查的是一样的
                            .city("天津")
                            .uid(poi.uid)));
                }
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
    };

    @Override
    public void onGetBusLineResult(BusLineResult busLineResult) {
        if (busLineResult == null || busLineResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getContext(), "抱歉，未找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getContext(), busLineResult.getBusLineName(),
                Toast.LENGTH_SHORT).show();
        Log.e("MainActivity-->", busLineResult.toString());
        List<BusLineResult.BusStation> steps = busLineResult.getStations();
        StringBuffer sb = new StringBuffer();
        for (BusLineResult.BusStation b : steps) {
            sb.append("-->");
            sb.append(b.getTitle());
        }
        Log.e("MainActivity-->", sb.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


}
