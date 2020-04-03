package com.bin.bustest.base;

import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseAty extends AppCompatActivity {

    public void showToast(String toast, int flg) {
        // 短toast
        if (flg == 1) {
            Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
        } else {
            // 长toast
            Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
        }
    }

    public void finish(View view) {
        this.finish();
    }
}
