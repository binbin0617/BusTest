package com.bin.bustest.aty;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bin.bustest.BaseApplication;
import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;
import com.bin.bustest.util.ContactInjfoDao;

public class SettingAty extends BaseAty {

    private TextView tv_name_te;

    private EditText tv_sex_te;

    private EditText tv_gexing_te;

    private ContactInjfoDao mDao;

    private String name;

    private String pass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_setting);
        tv_name_te = findViewById(R.id.tv_name_te);
        tv_sex_te = findViewById(R.id.tv_sex_te);
        tv_gexing_te = findViewById(R.id.tv_gexing_te);
        mDao = new ContactInjfoDao(SettingAty.this);
        name = BaseApplication.getPreferences().getString("name", "");
        String pass1 = mDao.alterDate(name);
        tv_name_te.setText(name);
        String[] temp = pass1.split("-");
        Log.e("-->temp.length", temp.length + "");
        if (temp.length == 3) {
            pass = temp[0];
            if (!TextUtils.isEmpty(temp[1])) {
                tv_sex_te.setText(temp[1]);
            } else {
                tv_sex_te.setHint("未填写");
            }
            if (!TextUtils.isEmpty(temp[2])) {
                tv_gexing_te.setText(temp[2]);
            } else {
                tv_gexing_te.setHint("未填写");
            }
        } else {
            pass = temp[0];
            tv_sex_te.setHint("未填写");
            tv_gexing_te.setHint("未填写");
        }


    }

    public void tijiao(View view) {
        String sex = tv_sex_te.getText().toString().trim();
        String geren = tv_gexing_te.getText().toString().trim();
        if (TextUtils.isEmpty(sex)) {
            showToast("性别不能为空", 1);
            return;
        }
        if (TextUtils.isEmpty(geren)) {
            showToast("个性签名不能为空", 1);
            return;
        }
        showLoadingDialog();
        long addLong = mDao.updateData(name, pass, sex, geren);
        if (addLong == -1) {
            dismissLoadingDialog();
            showToast("保存失败", 1);
        } else {
            dismissLoadingDialog();
            Log.e("SettingAty", "数据添加在第  " + addLong + "   行");
            showToast("保存成功!", 1);
//            startActivity(new Intent(SettingAty.this, LoginAty.class));
            finish();
        }

    }
}
