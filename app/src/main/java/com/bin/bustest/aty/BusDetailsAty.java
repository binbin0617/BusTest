package com.bin.bustest.aty;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

public class BusDetailsAty extends BaseAty {

    private String name;

    private TextView tv_zhandian;

    private List<String> mList;

    private BusDetailsAdapter busDetailsAdapter;

    private RecyclerView busRv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_bus_details);
        mList = new ArrayList<>();
        busRv = findViewById(R.id.rv);
        tv_zhandian = findViewById(R.id.tv_zhandian);

        if (getIntent() != null) {
            if (getIntent().getStringExtra("name") != null) {
                name = getIntent().getStringExtra("name");
                tv_zhandian.setText(String.valueOf("当前站点是：" + name));
            }
            if (getIntent().getStringExtra("busid") != null) {
                String busid = getIntent().getStringExtra("busid");
                if (busid.equals("")) {
                    showToast("当前公交站没有公交", 1);
                    return;
                }
                if (busid.contains(";")) {
                    String[] bus = busid.split(";");
                    for (int i = 0; i < bus.length; i++) {
                        mList.add(bus[i]);
                    }
                } else {
                    mList.add(busid);
                }
                initBusDetailsAdapter();
            }
        }
    }

    private void initBusDetailsAdapter() {
//        View view = View.inflate(this, R.layout.item_his_no, null);
        busDetailsAdapter = new BusDetailsAdapter(R.layout.item_bus_details, mList);
        busRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        busDetailsAdapter.setEmptyView(view);
        busRv.setAdapter(busDetailsAdapter);
        busDetailsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent = new Intent(BusDetailsAty.this, BusMapAty.class);
                intent.putExtra("name", mList.get(position));
                startActivity(intent);
            }
        });
    }

    public class BusDetailsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public BusDetailsAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_id, item);
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }

}
