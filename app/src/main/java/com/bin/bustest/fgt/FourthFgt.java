package com.bin.bustest.fgt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bin.bustest.R;
import com.bin.bustest.base.BaseFgt;


public class FourthFgt extends BaseFgt {

    private TextView tv_xinxi;

    private TextView tv_baojing;

    private TextView tv_haoping;

    private TextView tv_yijian;

    private TextView tv_jiancha;

    private TextView tv_guanyu;

    private LinearLayout ll_shezhi;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_fourth, container, false);
        tv_xinxi = view.findViewById(R.id.tv_xinxi);
        tv_baojing = view.findViewById(R.id.tv_baojing);
        tv_haoping = view.findViewById(R.id.tv_haoping);
        tv_yijian = view.findViewById(R.id.tv_yijian);
        tv_jiancha = view.findViewById(R.id.tv_jiancha);
        tv_guanyu = view.findViewById(R.id.tv_guanyu);
        ll_shezhi = view.findViewById(R.id.ll_shezhi);
        return view;
    }

    private void initClick() {
        tv_xinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_baojing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_haoping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_yijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_jiancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tv_guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ll_shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
