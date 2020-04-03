package com.bin.bustest.aty;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;

public class MapSearchAty extends BaseAty {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_search_map);
    }
}
