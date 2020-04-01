package com.bin.bustest.fgt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import com.bin.bustest.R;
import com.bin.bustest.bean.BusBean;
import com.bin.bustest.bean.HomeBean;
import com.bin.bustest.bean.LocationBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment implements OnGetBusLineSearchResultListener {

    private String TAG = SecondFragment.class.getSimpleName();

    private PoiSearch mPoiSearch;

    private BusLineSearch mBusLineSearch;

    private List<BusBean> mList;

    private RecyclerView rv;

    private BusActionAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_first, container, false);
        rv = view.findViewById(R.id.rv);
        EventBus.getDefault().register(this);
        mList = new ArrayList<>();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LocationBean locationBean) {
        Log.e(TAG, "-->" + locationBean.getLatitude() + "aaaaaaa");
        initView(locationBean);
    }

    private void initView(LocationBean locationBean) {

        mPoiSearch = PoiSearch.newInstance();

        mPoiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);

//        mPoiSearch.searchInCity(new PoiCitySearchOption()
//                .city("天津") //必填字段
//                .keyword("公交") //必填字段
//                .pageNum(10));

        mPoiSearch.searchNearby(new PoiNearbySearchOption()
                .location(new LatLng(locationBean.getLatitude(), locationBean.getLongitude()))
                .radius(1000)
                .keyword("公交")
                .pageNum(1));

    }


    //周边
    private OnGetPoiSearchResultListener onGetPoiSearchResultListener = new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getContext(), "OnGetPoiSearchResultListener抱歉，未找到结果", Toast.LENGTH_LONG).show();
                return;
            }
            Log.e(TAG, poiResult.getAllPoi().size() + "-->");
            // 遍历所有poi，找到类型为公交线路的poi
            for (PoiInfo poi : poiResult.getAllPoi()) {
//                Log.e(TAG,poi.getPoiDetailInfo().getTag()+"-->");
                BusBean busBean = new BusBean();
                busBean.setName(poi.getName());
                busBean.setBusId(poi.getAddress());
                mList.add(busBean);
                Log.e(TAG, poi.getAddress() + "getAddress-->");
                Log.e(TAG, poi.getArea() + "getArea-->");
                Log.e(TAG, poi.getCity() + "getCity-->");
                Log.e(TAG, poi.getDirection() + "getDirection-->");
                Log.e(TAG, poi.getName() + "getName-->");
                Log.e(TAG, poi.getDetail() + "getDetail-->");
            }

            adapter = new BusActionAdapter(R.layout.item_bus, mList);
            rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rv.setAdapter(adapter);
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


    public class BusActionAdapter extends BaseQuickAdapter<BusBean, BaseViewHolder> {
        public BusActionAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BusBean item) {
            helper.setText(R.id.tv_name, item.getName());
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }


}
