package com.bin.bustest.base;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFgt extends Fragment {

    public void showToast(String toast, int flg) {
        // 短toast
        if (flg == 1) {
            Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
        } else {
            // 长toast
            Toast.makeText(getContext(), toast, Toast.LENGTH_LONG).show();
        }
    }
}
