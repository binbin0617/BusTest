package com.bin.bustest.aty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
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
import com.bin.bustest.bean.BusLuxianBean;
import com.bin.bustest.util.SecondsTest;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchPointAty extends BaseAty implements OnGetRoutePlanResultListener {

    private String TAG = SearchAty.class.getSimpleName();

    private RecyclerView rv;

    private SearchPointAdapter adapter;

    private List<BusLuxianBean> mList;

    private TransitRouteResult mTransitRouteResult;

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
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(SearchPointAty.this, MapSearchAty.class);
                intent.putExtra("list", mTransitRouteResult);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
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
        mTransitRouteResult = transitRouteResult;
        Log.e(TAG, transitRouteResult.getRouteLines().size() + "-->");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < transitRouteResult.getRouteLines().size(); i++) {
            for (int j = 0; j < transitRouteResult.getRouteLines().get(i).getAllStep().size(); j++) {
//                Log.e(TAG, transitRouteResult.getRouteLines().get(i).getAllStep().get(j).getInstructions() + "-->getTaxitInfo");
                sb.append(transitRouteResult.getRouteLines().get(i).getAllStep().get(j).getInstructions() + "-->");
            }
            BusLuxianBean busLuxianBean = new BusLuxianBean();
            busLuxianBean.setXina(sb.toString());
            String time = SecondsTest.secondToTime(transitRouteResult.getRouteLines().get(i).getDuration());
            busLuxianBean.setTime(time);
            mList.add(busLuxianBean);
            sb.delete(0, sb.length());
        }
        initAdapter();
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

    public class SearchPointAdapter extends BaseQuickAdapter<BusLuxianBean, BaseViewHolder> {
        public SearchPointAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BusLuxianBean item) {
            helper.setText(R.id.tv_info, item.getXina());
            helper.setText(R.id.tv_time, "当前路线耗时"+item.getTime());
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
