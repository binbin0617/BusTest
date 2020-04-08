package com.bin.bustest.fgt;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bin.bustest.R;
import com.bin.bustest.aty.SearchAty;
import com.bin.bustest.aty.SearchPointAty;
import com.bin.bustest.base.BaseFgt;
import com.bin.bustest.util.HisUtil2;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import static android.app.Activity.RESULT_OK;


public class Secondfgt extends BaseFgt {

    private TextView tv_my;

    private TextView tv_go;

    private ImageView iv_search;

    private ImageView iv_delete;

    private String startName;

    private String endName;

    private RecyclerView rv;

    private ResultAdapter2 adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fgt_second, container, false);
        tv_my = view.findViewById(R.id.tv_my);
        tv_go = view.findViewById(R.id.tv_go);
        iv_search = view.findViewById(R.id.iv_search);
        iv_delete = view.findViewById(R.id.iv_delete);
        rv = view.findViewById(R.id.rv);
        click();
        initAdapter();
        return view;
    }

    private void initAdapter() {
        View view = View.inflate(getContext(), R.layout.item_his_no, null);
        adapter = new ResultAdapter2(R.layout.item_hisw, HisUtil2.getSearchHistory(getContext()));
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(adapter);
        adapter.setEmptyView(view);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                String[] temp = HisUtil2.getSearchHistory(getContext()).get(position).split("-->");
                if (temp.length == 2) {
                    startName = temp[0];
                    endName = temp[1];
                    HisUtil2.saveSearchHistory(startName + "-->" + endName, getContext());
                    Intent intent = new Intent(getContext(), SearchPointAty.class);
                    intent.putExtra("startName", startName);
                    intent.putExtra("endName", endName);
                    startActivity(intent);
                }

            }
        });
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

        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HisUtil2.deleteHistory(getContext());
//                if (adapter != null) {
//                    adapter.notifyDataSetChanged();
//                } else {
                    initAdapter();
//                }
            }
        });

        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startName = tv_my.getText().toString().trim();
                endName = tv_go.getText().toString().trim();
                if (startName.equals("我的位置")) {
                    showToast("请输入你的起点", 1);
                    return;
                }
                if (endName.equals("你要去哪儿？")) {
                    showToast("请输入你的目的地", 1);
                    return;
                }
                HisUtil2.saveSearchHistory(startName + "-->" + endName, getContext());
                Intent intent = new Intent(getContext(), SearchPointAty.class);
                intent.putExtra("startName", startName);
                intent.putExtra("endName", endName);
                startActivity(intent);
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

    @Override
    public void onResume() {
        super.onResume();
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        } else {
            initAdapter();
//        }
    }

    public class ResultAdapter2 extends BaseQuickAdapter<String, BaseViewHolder> {
        public ResultAdapter2(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_zi, item);
//            helper.setText(R.id.tv_shi, item.getShi() + item.getQu());
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }
}
