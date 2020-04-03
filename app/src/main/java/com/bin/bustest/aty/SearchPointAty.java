package com.bin.bustest.aty;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRoutePlanOption;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.bin.bustest.BaseApplication;
import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchPointAty extends BaseAty implements OnGetRoutePlanResultListener {

    private String TAG = SearchAty.class.getSimpleName();

    private RecyclerView rv;

    private SearchPointAdapter adapter;

    private List<String> mList;

    private RoutePlanSearch mRoutePlanSearch;

    private String startName;

    private String endName;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_search_point);
        if (getIntent() != null) {
            if (getIntent().getStringExtra("startName") != null) {
                startName = getIntent().getStringExtra("startName");
            }
            if (getIntent().getStringExtra("endName") != null) {
                endName = getIntent().getStringExtra("endName");
            } else {
                startName = "上海大学(宝山校区)";
                endName = "宝安公路828号";
            }
        } else {
            startName = "上海大学(宝山校区)";
            endName = "宝安公路828号";
        }
        mList = new ArrayList<>();
        initView();
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        mRoutePlanSearch = RoutePlanSearch.newInstance();
        ArrayList<PlanNode> arg0 = new ArrayList<PlanNode>();
        //设置起终点、途经点信息，对于tranist search 来说，城市名无意义
        PlanNode stNode = PlanNode.withCityNameAndPlaceName(BaseApplication.getCity(), startName);
        PlanNode enNode = PlanNode.withCityNameAndPlaceName(BaseApplication.getCity(), endName);

        mRoutePlanSearch.setOnGetRoutePlanResultListener(this);
        // 实际使用中请对起点终点城市进行正确的设定
//        drivingSearch
        mRoutePlanSearch.transitSearch((new TransitRoutePlanOption())
                .from(stNode)//起点
                .to(enNode)//终点
                .city(BaseApplication.getCity()));

    }


    private void initAdapter() {
        adapter = new SearchPointAdapter(R.layout.item_search_point, mList);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
    }

    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

    }

    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {
        if (transitRouteResult == null
                || transitRouteResult.error != SearchResult.ERRORNO.NO_ERROR) {
            showToast("抱歉，未找到结果", 1);
        }
        if (transitRouteResult.error == SearchResult.ERRORNO.AMBIGUOUS_ROURE_ADDR) {
            // 起终点或途经点地址有岐义，通过以下接口获取建议查询信息
            // drivingRouteResult.getSuggestAddrInfo()
            return;
        }
        Log.e(TAG, transitRouteResult.getRouteLines().size() + "-->");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < transitRouteResult.getRouteLines().size(); i++) {
            for (int j = 0; j < transitRouteResult.getRouteLines().get(i).getAllStep().size(); j++) {
//                Log.e(TAG, transitRouteResult.getRouteLines().get(i).getAllStep().get(j).getInstructions() + "-->getTaxitInfo");
                sb.append(transitRouteResult.getRouteLines().get(i).getAllStep().get(j).getInstructions() + "-->");
            }
            mList.add(sb.toString());
            sb.delete(0, sb.length());
        }
        initAdapter();
        //创建TransitRouteOverlay实例
//        TransitRouteOverlay overlay = new TransitRouteOverlay(mBaiduMap);
//        //获取路径规划数据,(以返回的第一条数据为例)
//        //为TransitRouteOverlay实例设置路径数据
//        if (transitRouteResult.getRouteLines().size() > 0) {
//            overlay.setData(transitRouteResult.getRouteLines().get(0));
//            //在地图上绘制TransitRouteOverlay
//            overlay.addToMap();
//        }
    }

    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }

    public class SearchPointAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public SearchPointAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_info, item);
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRoutePlanSearch.destroy();
    }
}
