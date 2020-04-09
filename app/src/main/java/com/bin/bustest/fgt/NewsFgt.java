package com.bin.bustest.fgt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bin.bustest.R;
import com.bin.bustest.aty.WebAty;
import com.bin.bustest.base.BaseFgt;
import com.bin.bustest.bean.NewsBean;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.kongzue.baseokhttp.HttpRequest;
import com.kongzue.baseokhttp.listener.ResponseListener;
import com.kongzue.baseokhttp.util.Parameter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class NewsFgt extends BaseFgt {

    private boolean isLoaded = false;

    private RecyclerView rv;

    private SmartRefreshLayout sm;

    private NewsAdapter adapter;

    private List<NewsBean.ResultBean.DataBean> mList;

    private String mType;

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoaded) {
            initHttp();
            Log.d("NewsFgt", "lazyInit:!!!!!!!");
            isLoaded = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isLoaded = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_news, container, false);
        rv = view.findViewById(R.id.rv);
        sm = view.findViewById(R.id.sm);
        mList = new ArrayList<>();
        sm.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initHttp();
            }
        });
        sm.setEnableLoadMore(false);
        return view;
    }

    public void initHttp() {
        showLoadingDialog();
        HttpRequest.GET(getActivity(), "http://v.juhe.cn/toutiao/index",
                new Parameter().add("type", mType)
                        .add("key", "32b401557bdbb990a3c09a513bde9583"), new ResponseListener() {
                    @Override
                    public void onResponse(String main, Exception error) {
                        dismissLoadingDialog();
                        if (error == null) {
                            if (mList.size() != 0) {
                                mList.clear();
                            }
                            NewsBean bianBean = new Gson().fromJson(main, NewsBean.class);
                            if (bianBean.getResult() == null) {
                                return;
                            }
                            if (bianBean.getResult().getData() == null) {
                                return;
                            }
                            for (int i = 0; i < bianBean.getResult().getData().size(); i++) {
                                mList.add(bianBean.getResult().getData().get(i));
                                initAdapter();
                            }
                        }
                    }
                });
    }


    private void initAdapter() {
        adapter = new NewsAdapter(R.layout.item_news, mList);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(getContext(), WebAty.class);
                intent.putExtra("url", mList.get(position).getUrl());
                startActivity(intent);
            }
        });
    }


    public static NewsFgt newInstance(String type) {
        NewsFgt f = new NewsFgt();
        f.mType = type;
        Bundle args = new Bundle();
        args.putString("index", type);
        f.setArguments(args);
        return f;
    }

    public class NewsAdapter extends BaseQuickAdapter<NewsBean.ResultBean.DataBean, BaseViewHolder> {
        public NewsAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, NewsBean.ResultBean.DataBean item) {
            helper.setText(R.id.tv_title, item.getTitle());
            helper.setText(R.id.tv_time, item.getDate());
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
            Glide.with(getContext()).load(item.getThumbnail_pic_s()).into((ImageView) helper.getView(R.id.iv));
        }
    }
}
