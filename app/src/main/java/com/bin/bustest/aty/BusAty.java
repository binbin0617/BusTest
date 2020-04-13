package com.bin.bustest.aty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bin.bustest.BaseApplication;
import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;

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

public class BusAty extends BaseAty {

    private BussAdapter adapter;

    private RecyclerView rv;

    private List<ShiBusBean.ReturlListBean> mList;

    private String keySecret;


    private String word;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_bus);
        rv = findViewById(R.id.rv);
        mList = new ArrayList<>();
        keySecret = md5Decode32(BaseApplication.getuName() + BaseApplication.getKey() + "luxian");
        if (getIntent() != null) {
            if (getIntent().getStringExtra("bus") != null) {
                word = getIntent().getStringExtra("bus");
            } else {
                word = "311";
            }
        } else {
            word = "311";
        }

        initHttp();
    }

    private void initHttp() {
        showLoadingDialog();
        HttpRequest.GET(BusAty.this, "http://api.dwmm136.cn/z_busapi/BusApi.php",
                new Parameter().add("optype", "luxian")
                        .add("uname", BaseApplication.getuName())
                        .add("cityid", BaseApplication.getCityid()).add("keywords", word).add("keySecret", keySecret), new ResponseListener() {
                    @Override
                    public void onResponse(String main, Exception error) {
                        if (error == null) {
                            dismissLoadingDialog();
                            ShiBusBean shiBusBean = new Gson().fromJson(main, ShiBusBean.class);
                            if (("0").equals(shiBusBean.getError_code()) && ("ok").equals(shiBusBean.getReturn_code())) {
                                for (int i = 0; i < shiBusBean.getReturl_list().size(); i++) {
                                    mList.add(shiBusBean.getReturl_list().get(i));
                                }
                                Log.e("-->", "getBus_endstan" + shiBusBean.getReturl_list().get(0).getBus_endstan());
                                Log.e("-->", "getBus_linenum" + shiBusBean.getReturl_list().get(0).getBus_linenum());
                                Log.e("-->", "getBus_linestrid" + shiBusBean.getReturl_list().get(0).getBus_linestrid());
                                Log.e("-->", "getBus_staname" + shiBusBean.getReturl_list().get(0).getBus_staname());
                                initAdapter();
//                                initHttp2(shiBusBean.getReturl_list().get());
                            } else {
                                showToast("请求失败", 1);
                            }
                        }
                    }
                });
    }

    private void initAdapter() {
        adapter = new BussAdapter(R.layout.item_busw, mList);
        rv.setLayoutManager(new LinearLayoutManager(BusAty.this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(BusAty.this, BusDetailsTimeAty.class);
                intent.putExtra("bus", mList.get(position));
                startActivity(intent);
            }
        });
    }


//    public void click(View view) {
//        hideInput();
//
//    }

    public class BussAdapter extends BaseQuickAdapter<ShiBusBean.ReturlListBean, BaseViewHolder> {

        public BussAdapter(int layoutResId, List<ShiBusBean.ReturlListBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShiBusBean.ReturlListBean item) {
            helper.setText(R.id.tv_zi, item.getBus_staname());
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }
}
