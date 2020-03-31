package com.bin.bustest.fgt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bin.bustest.bean.HomeBean;
import com.bin.bustest.R;
import com.bin.bustest.adapter.ImageAdapter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private Banner banner;

    private RecyclerView rv;

    private List<Integer> mList;

    private List<HomeBean> imgList;

    private HomeAdapter homeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_first, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        /**
         * 列表的上面布局
         */
        View view1 = View.inflate(getContext(), R.layout.rv_head, null);
        banner = view1.findViewById(R.id.banner);
        mList = new ArrayList<>();
        mList.add(R.mipmap.index1);
        mList.add(R.mipmap.index2);
        mList.add(R.mipmap.index3);
        mList.add(R.mipmap.index4);
        banner.setAdapter(new ImageAdapter(mList))
                .setOrientation(Banner.HORIZONTAL)
                .setIndicator(new CircleIndicator(getContext()))
                .setUserInputEnabled(true);
        /**
         * 列表的下面布局
         */
        View view2 = View.inflate(getContext(), R.layout.rv_foot, null);


        rv = view.findViewById(R.id.rv);
        imgList = new ArrayList<>();
        HomeBean homeBean1 = new HomeBean();
        homeBean1.setImgId(R.mipmap.index1);
        imgList.add(homeBean1);
        HomeBean homeBean2 = new HomeBean();
        homeBean2.setImgId(R.mipmap.index2);
        imgList.add(homeBean2);
        HomeBean homeBean3 = new HomeBean();
        homeBean3.setImgId(R.mipmap.index3);
        imgList.add(homeBean3);
        HomeBean homeBean4 = new HomeBean();
        homeBean4.setImgId(R.mipmap.index4);
        imgList.add(homeBean4);
        homeAdapter = new HomeAdapter(R.layout.item_img, imgList);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        homeAdapter.addHeaderView(view1);
        homeAdapter.addFooterView(view2);
        rv.setAdapter(homeAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stop();
    }


    public class HomeAdapter extends BaseQuickAdapter<HomeBean, BaseViewHolder> {
        public HomeAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, HomeBean item) {
//            helper.setText(R.id.text, item.getTitle());
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }


}
