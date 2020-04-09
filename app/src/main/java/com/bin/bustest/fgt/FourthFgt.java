package com.bin.bustest.fgt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bin.bustest.BaseApplication;
import com.bin.bustest.R;
import com.bin.bustest.aty.FeedBackAty;
import com.bin.bustest.aty.LoginAty;
import com.bin.bustest.aty.SettingAty;
import com.bin.bustest.base.BaseFgt;


public class FourthFgt extends BaseFgt {

    private TextView tv_xinxi;

    private TextView tv_baojing;

    private TextView tv_haoping;

    private TextView tv_yijian;

    private TextView tv_jiancha;

    private TextView tv_guanyu;

    private LinearLayout ll_shezhi;

    private TextView tv_name;


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
        tv_name = view.findViewById(R.id.tv_name);
        String name = BaseApplication.getPreferences().getString("name", "");
        if (!TextUtils.isEmpty(name)) {
            tv_name.setText(name);
        }
        initClick();
        return view;
    }

    private void initClick() {
        tv_xinxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingAty.class));
            }
        });
        tv_baojing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:15810277571"));
                startActivity(intent);
            }
        });
        tv_yijian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), FeedBackAty.class));
            }
        });
        tv_jiancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("当前已是最新版本", 1);
            }
        });
        tv_guanyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("关于");
                builder.setMessage("Version 1.0");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        builder.create().dismiss();
                    }
                });
                builder.create().show();
            }
        });
        //退出登录
        ll_shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = BaseApplication.getPreferences().edit();
                editor.putString("name", "");
                editor.putBoolean("islogin", false);
                editor.commit();
                startActivity(new Intent(getContext(), LoginAty.class));
                showToast("退出登录成功！", 1);
                getActivity().finish();
            }
        });
    }
}
