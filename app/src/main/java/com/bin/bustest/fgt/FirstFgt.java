package com.bin.bustest.fgt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.busline.BusLineResult;
import com.baidu.mapapi.search.busline.BusLineSearch;
import com.baidu.mapapi.search.busline.OnGetBusLineSearchResultListener;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.bin.bustest.BaseApplication;
import com.bin.bustest.R;
import com.bin.bustest.adapter.ImageAdapter;
import com.bin.bustest.aty.BusDetailsAty;
import com.bin.bustest.aty.MapAty;
import com.bin.bustest.base.BaseFgt;
import com.bin.bustest.bean.BusBean;
import com.bin.bustest.bean.DaysBean;
import com.bin.bustest.bean.LocationBean;
import com.bin.bustest.bean.NewsBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.kongzue.baseokhttp.HttpRequest;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FirstFgt extends BaseFgt implements OnGetBusLineSearchResultListener {

    private String TAG = FirstFgt.class.getSimpleName();

    private PoiSearch mPoiSearch;

    private BusLineSearch mBusLineSearch;

    private List<BusBean> mList;

    private RecyclerView rv;

    private BusActionAdapter adapter;

    private ImageView iv_search;

    private LocationBean mLocationBean;

    private Banner banner;

    private List<Integer> mList1;

    private TextView tv_wendu;

    private TextView tv_shidu;

    private TextView tv_state;

    private TextView tv_fengli;

    private TextView tv_fengxiang;

    private TextView tv_title;

    private TextView tv_zhandian;

    private TextView tv_code;

    private RelativeLayout rl_code;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_first, container, false);

        rv = view.findViewById(R.id.rv);
        iv_search = view.findViewById(R.id.iv_search);
        tv_wendu = view.findViewById(R.id.tv_wendu);
        tv_shidu = view.findViewById(R.id.tv_shidu);
        tv_state = view.findViewById(R.id.tv_state);
        tv_fengli = view.findViewById(R.id.tv_fengli);
        tv_fengxiang = view.findViewById(R.id.tv_fengxiang);
        tv_title = view.findViewById(R.id.tv_title);
        tv_zhandian = view.findViewById(R.id.tv_zhandian);
        tv_code = view.findViewById(R.id.tv_code);
        rl_code = view.findViewById(R.id.rl_code);
        EventBus.getDefault().register(this);
        mList = new ArrayList<>();
        banner = view.findViewById(R.id.banner);
        mList1 = new ArrayList<>();
        mList1.add(R.mipmap.index1);
        mList1.add(R.mipmap.index2);
        mList1.add(R.mipmap.index3);
        mList1.add(R.mipmap.index4);
        banner.setAdapter(new ImageAdapter(mList1))
                .setOrientation(Banner.HORIZONTAL)
                .setIndicator(new CircleIndicator(getContext()))
                .setUserInputEnabled(true);
        initHttp();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LocationBean locationBean) {
//        Log.e(TAG, "-->" + locationBean.getLatitude() + "aaaaaaa");
        mLocationBean = locationBean;
        initView(locationBean);
    }

    private void initHttp() {
        HttpRequest.GET(getActivity(), "http://apis.juhe.cn/simpleWeather/query",
                new Parameter().add("city", BaseApplication.getCity())
                        .add("key", "36d9b4fa7d7198effd2ec5489655b849"), new ResponseListener() {
                    @Override
                    public void onResponse(String main, Exception error) {
                        if (error == null) {
                            if (mList.size() != 0) {
                                mList.clear();
                            }
                            DaysBean daysBean = new Gson().fromJson(main, DaysBean.class);
                            if (daysBean.getResult() == null) {
                                return;
                            }
                            if (daysBean.getResult().getRealtime() == null) {
                                return;
                            }
                            DaysBean.ResultBean.RealtimeBean realtimeBean = daysBean.getResult().getRealtime();
                            tv_title.setText(String.valueOf(BaseApplication.getCity() + "天气情況"));
                            tv_state.setText(String.valueOf("今天天气" + realtimeBean.getInfo()));
                            if (realtimeBean.getTemperature() != null) {
                                tv_wendu.setText(String.valueOf("温度:" + realtimeBean.getTemperature()));
                            }
                            if (realtimeBean.getHumidity() != null) {
                                tv_shidu.setText(String.valueOf("湿度:" + realtimeBean.getHumidity()));
                            }
                            if (realtimeBean.getPower() != null) {
                                tv_fengli.setText(String.valueOf("风力" + realtimeBean.getPower()));
                            }
                            if (realtimeBean.getPower() != null) {
                                tv_fengxiang.setText(String.valueOf("风向" + realtimeBean.getDirect()));
                            }
                        }
                    }
                });
    }

    private void initView(LocationBean locationBean) {

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapAty.class);
                intent.putExtra("mLocationBean", mLocationBean);
                startActivity(intent);
            }
        });

        tv_zhandian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_zhandian.setTextSize(16);
                rv.setVisibility(View.VISIBLE);
                tv_code.setTextSize(12);
                rl_code.setVisibility(View.GONE);
            }
        });

        tv_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_zhandian.setTextSize(12);
                rv.setVisibility(View.GONE);
                tv_code.setTextSize(16);
                rl_code.setVisibility(View.VISIBLE);
            }
        });


        mPoiSearch = PoiSearch.newInstance();

        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);


        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .location(new LatLng(39.094924, 117.13299))
                .radius(1000)
                .keyword("公交")
                .pageNum(1));

    }


    //周边
    private OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getContext(), "周边暂时没查找到公交站", Toast.LENGTH_LONG).show();
                return;
            }
//            Log.e(TAG, poiResult.getAllPoi().size() + "-->");
            // 遍历所有poi，找到类型为公交线路的poi
            if (mList.size() != 0) {
                mList.clear();
            }
            for (PoiInfo poi : poiResult.getAllPoi()) {
//                Log.e(TAG,poi.getPoiDetailInfo().getTag()+"-->");
                BusBean busBean = new BusBean();
                busBean.setName(poi.getName());
                busBean.setBusId(poi.getAddress());
                mList.add(busBean);
//                Log.e(TAG, poi.getAddress() + "getAddress-->");
//                Log.e(TAG, poi.getArea() + "getArea-->");
//                Log.e(TAG, poi.getCity() + "getCity-->");
//                Log.e(TAG, poi.getDirection() + "getDirection-->");
//                Log.e(TAG, poi.getName() + "getName-->");
//                Log.e(TAG, poi.getDetail() + "getDetail-->");
                initAdapter();
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

    private void initAdapter() {
        View view = View.inflate(getContext(), R.layout.bus_no, null);
        adapter = new BusActionAdapter(R.layout.item_bus, mList);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        adapter.setEmptyView(view);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getContext(), BusDetailsAty.class);
                intent.putExtra("name", mList.get(position).getName());
                intent.putExtra("busid", mList.get(position).getBusId());
//                intent.putExtra("busid", mList.get(position).getBusId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onGetBusLineResult(BusLineResult busLineResult) {
        if (busLineResult == null || busLineResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(getContext(), "抱歉，未找到结果", Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(getContext(), busLineResult.getBusLineName(),
                Toast.LENGTH_SHORT).show();
//        Log.e("MainActivity-->", busLineResult.toString());
        List<BusLineResult.BusStation> steps = busLineResult.getStations();
        StringBuffer sb = new StringBuffer();
        for (BusLineResult.BusStation b : steps) {
            sb.append("-->");
            sb.append(b.getTitle());
        }
//        Log.e("MainActivity-->", sb.toString());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPoiSearch.destroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    public class BusActionAdapter extends BaseQuickAdapter<BusBean, BaseViewHolder> {
        public BusActionAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BusBean item) {
            helper.setText(R.id.tv_name, item.getName());
            helper.setText(R.id.tv_bus, item.getBusId());
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        if (banner != null) {
            banner.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        if (banner != null) {
            banner.stop();
        }
    }

}
