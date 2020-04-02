package com.bin.bustest.fgt;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bin.bustest.R;
import com.bin.bustest.aty.SearchAty;
import com.bin.bustest.base.BaseFgt;

import static android.app.Activity.RESULT_OK;


public class Secondfgt extends BaseFgt {

    private TextView tv_my;

    private TextView tv_go;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_second, container, false);
        tv_my = view.findViewById(R.id.tv_my);
        tv_go = view.findViewById(R.id.tv_go);
        click();
        return view;
    }

    public void click() {
        tv_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchAty.class);
                intent.putExtra("type", "my");
                startActivityForResult(intent, 111);
            }
        });

        tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchAty.class);
                intent.putExtra("type", "go");
                startActivityForResult(intent, 222);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 111) {
                if (data.getStringExtra("my") != null) {
                    tv_my.setText(data.getStringExtra("my"));
                }
            } else if (requestCode == 222) {
                if (data.getStringExtra("go") != null) {
                    tv_go.setText(data.getStringExtra("go"));
                }
            }
        }
    }
}
