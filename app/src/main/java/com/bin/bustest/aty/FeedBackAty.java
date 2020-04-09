package com.bin.bustest.aty;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;


public class FeedBackAty extends BaseAty {

    private EditText et_feed_back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_feed_back);
        et_feed_back = findViewById(R.id.et_feed_back);
    }

    public void loginClick(View view) {
        String str = et_feed_back.getText().toString().trim();
        if (TextUtils.isEmpty(str)) {
            showToast("输入不能为空!", 1);
            return;
        }
        showLoadingDialog();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showToast("提交成功!", 1);
                        finish();
                    }
                });
            }
        }.start();
    }
}
