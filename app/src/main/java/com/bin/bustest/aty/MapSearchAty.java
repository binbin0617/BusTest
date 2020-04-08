package com.bin.bustest.aty;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;
import com.bin.bustest.util.TransitRouteOverlay;

public class MapSearchAty extends BaseAty {

    private TransitRouteResult mTransitRouteResult;

    private int position;

    private MapView mBaiduMap;

    private BaiduMap baiduMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_search_map);
        mBaiduMap = findViewById(R.id.bmapView);
        baiduMap = mBaiduMap.getMap();
        baiduMap.setMyLocationEnabled(true);
        //显示卫星图层
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
//        baiduMap.set
        if (getIntent() != null) {
            if (getIntent().getParcelableExtra("list") != null) {
                mTransitRouteResult = getIntent().getParcelableExtra("list");
                position = getIntent().getIntExtra("position", 0);
//                创建TransitRouteOverlay实例
                TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap.getMap());
                //获取路径规划数据,(以返回的第一条数据为例)
                //为TransitRouteOverlay实例设置路径数据
                if (mTransitRouteResult.getRouteLines().size() > 0) {
//                    TransitOverlay transitOverlay = new TransitOverlay(
//                            MainActivity.this, mapView);
//                    // 此处仅展示一个方案作为示例
//                    transitOverlay.setData(result.getPlan(0));
//                    mapView.getOverlays().add(transitOverlay);
//                    mapView.invalidate(); // 刷新地图
                    overlay.setData(mTransitRouteResult.getRouteLines().get(position));
                    //在地图上绘制TransitRouteOverlay
                    overlay.addToMap();
                    overlay.zoomToSpan();
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.zoom(15.0f);//这里设置了缩放的比例，float类型。
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
            }
        }
    }
}
