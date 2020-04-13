package com.bin.bustest.aty;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bin.bustest.BaseApplication;
import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;
import com.bin.bustest.bean.BusDetailsTimeBean;
import com.bin.bustest.bean.ShiBusBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.kongzue.baseokhttp.HttpRequest;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.kongzue.baseokhttp.util.Parameter;

import java.util.ArrayList;
import java.util.List;

public class BusDetailsTimeAty extends BaseAty {

    private List<BusDetailsTimeBean.ReturlListBean.StationsBean> mList;

    private BusDetailsTimeAdapter adapter;

    private RecyclerView rv;

    private ShiBusBean.ReturlListBean returlListBean;


    private BusDetailsTimeBean busDetailsTimeBean;

    private String fanBus_linestrid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_bus_details_time);
        mList = new ArrayList<>();
        rv = findViewById(R.id.rv);
        if (getIntent() != null) {
            if (getIntent().getParcelableExtra("bus") != null) {
                returlListBean = getIntent().getParcelableExtra("bus");
                initHttp2(returlListBean.getBus_linestrid(), returlListBean.getBus_linenum(), returlListBean.getBus_staname());
            }
        }

    }

    private void initAdapter() {
        for (int j = 0; j < busDetailsTimeBean.getReturl_list().getBuses().size(); j++) {
            for (int i = 0; i < mList.size(); i++) {
                if (busDetailsTimeBean.getReturl_list().getBuses().get(j).getDis_stat() == i) {
                    mList.get(i).setDao(false);
                    mList.get(i).setJuli(busDetailsTimeBean.getReturl_list().getBuses().get(j).getDis_stat());
                    break;
                }
            }
        }
        adapter = new BusDetailsTimeAdapter(R.layout.item_bus_time, mList);
        rv.setLayoutManager(new LinearLayoutManager(BusDetailsTimeAty.this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

            }
        });
    }


    private void initHttp2(String bus_linestrid, String bus_linenum, String bus_staname) {
        showLoadingDialog();
        String keySecret = md5Decode32(BaseApplication.getuName() + BaseApplication.getKey() + "rtbus");
        HttpRequest.GET(BusDetailsTimeAty.this, "http://api.dwmm136.cn/z_busapi/BusApi.php",
                new Parameter().add("optype", "rtbus")
                        .add("uname", BaseApplication.getuName())
                        .add("cityid", BaseApplication.getCityid()).add("bus_linestrid", bus_linestrid)
                        .add("bus_linenum", bus_linenum).add("bus_staname", bus_staname).add("keySecret", keySecret), new ResponseListener() {
                    @Override
                    public void onResponse(String main, Exception error) {
                        if (error == null) {
                            if (mList.size() != 0) {
                                mList.clear();
                            }
                            dismissLoadingDialog();
                            busDetailsTimeBean = new Gson().fromJson(main, BusDetailsTimeBean.class);
                            fanBus_linestrid = busDetailsTimeBean.getReturl_list().getLineinfo().getOther_lineid();
                            if (("0").equals(busDetailsTimeBean.getError_code()) && ("ok").equals(busDetailsTimeBean.getReturn_code())) {
                                mList.addAll(busDetailsTimeBean.getReturl_list().getStations());
                                for (int i = 0; i < mList.size(); i++) {
                                    mList.get(i).setDao(true);
                                }
                                initAdapter();
                            } else {
                                showToast("请求失败", 1);
                            }
                        }
                    }
                });
    }

    public void finish2(View view) {
        initHttp2(fanBus_linestrid, returlListBean.getBus_linenum(), returlListBean.getBus_staname());
    }

    public class BusDetailsTimeAdapter extends BaseQuickAdapter<BusDetailsTimeBean.ReturlListBean.StationsBean, BaseViewHolder> {

        public BusDetailsTimeAdapter(int layoutResId, List<BusDetailsTimeBean.ReturlListBean.StationsBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, BusDetailsTimeBean.ReturlListBean.StationsBean item) {
            helper.setText(R.id.tv_zhandian, item.getBus_staname());
            if (!item.isDao()) {
                ((TextView) helper.getView(R.id.tv_juli)).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_juli, "公交车距离本站还有" + item.getJuli() + "米");
            } else {
                ((TextView) helper.getView(R.id.tv_juli)).setVisibility(View.GONE);
            }
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }
}
