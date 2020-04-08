package com.bin.bustest.fgt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bin.bustest.adapter.ViewPagerAdapter;
import com.bin.bustest.base.BaseFgt;
import com.bin.bustest.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class ThirdFgt extends BaseFgt {



    private ViewPager viewPager;
    private TabLayout tabLayout1;


    private ViewPagerAdapter carTypeViewAdapter;
    private ArrayList<String> carStringList =new ArrayList<>();
    private ArrayList<Fragment> carTypeFragmentList = new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_third, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        carStringList.add("头条");
        carStringList.add("社会");
        carStringList.add("国内");
        carStringList.add("娱乐");
        carStringList.add("国际");
        NewsFgt carTypeFragment1=NewsFgt.newInstance("toutiao");
        NewsFgt carTypeFragment2=NewsFgt.newInstance("shehui");
        NewsFgt carTypeFragment3=NewsFgt.newInstance("guonei");
        NewsFgt carTypeFragment4=NewsFgt.newInstance("yule");
        NewsFgt carTypeFragment5=NewsFgt.newInstance("guoji");
        carTypeFragmentList.add(carTypeFragment1);
        carTypeFragmentList.add(carTypeFragment2);
        carTypeFragmentList.add(carTypeFragment3);
        carTypeFragmentList.add(carTypeFragment4);
        carTypeFragmentList.add(carTypeFragment5);

        carTypeViewAdapter=new ViewPagerAdapter(getChildFragmentManager(), carStringList,carTypeFragmentList,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        viewPager= view.findViewById(R.id.vp_cartype);
        viewPager.setAdapter(carTypeViewAdapter);
        tabLayout1=view.findViewById(R.id.huoyun_tablayout);
        tabLayout1.setupWithViewPager(viewPager);

    }
}
