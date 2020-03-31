package com.bin.bustest.fgt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bin.bustest.R;
import com.bin.bustest.adapter.ImageAdapter;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private Banner banner;
    private List<Integer> mList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_first, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        banner = view.findViewById(R.id.banner);
        mList = new ArrayList<>();
        mList.add(R.mipmap.ic_launcher);mList.add(R.mipmap.ic_launcher);mList.add(R.mipmap.ic_launcher);mList.add(R.mipmap.ic_launcher);
//        mList.add("http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
//        mList.add("http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
//        mList.add("http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg");
        banner.setAdapter(new ImageAdapter(mList))
                .setOrientation(Banner.HORIZONTAL)
                .setIndicator(new CircleIndicator(getContext()))
                .setUserInputEnabled(true);
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


}
