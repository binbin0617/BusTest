package com.bin.bustest.aty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BusAty2 extends BaseAty {

    private BussAdapter2 adapter;

    private RecyclerView rv;

    private List<String> mList;


    private String word;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_bus);
        rv = findViewById(R.id.rv);
        mList = new ArrayList<>();
        if (getIntent() != null) {
            if (getIntent().getStringExtra("bus") != null) {
                word = getIntent().getStringExtra("bus");
            } else {
                word = "311";
            }
        } else {
            word = "311";
        }
        initAdapter();
    }


    private void initAdapter() {
        String s = "   -->   ";
        String temp[] = new String[0];
        if (word.contains(s)) {
            temp = word.split(s);
        }
        for (int i = 0; i < temp.length; i++) {
            mList.add(temp[i]);
        }
        adapter = new BussAdapter2(R.layout.item_busw, mList);
        rv.setLayoutManager(new LinearLayoutManager(BusAty2.this, LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(BusAty2.this, BusAty.class);
                intent.putExtra("bus", mList.get(position));
                startActivity(intent);
            }
        });
    }



    public class BussAdapter2 extends BaseQuickAdapter<String, BaseViewHolder> {

        public BussAdapter2(int layoutResId, List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_zi, item);
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }
}
