package com.bin.bustest.aty;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.bin.bustest.BaseApplication;
import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;
import com.bin.bustest.bean.SearchBean;
import com.bin.bustest.util.HisUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchAty extends BaseAty {

    private String TAG = SearchAty.class.getSimpleName();


    private List<SearchBean> resultList;

    private RecyclerView hisRv;

    private RecyclerView resultRv;

    private LinearLayout ll_his;

    private LinearLayout ll_result;

    private RelativeLayout rl_his;

    private EditText et_point;

    private HisAdapter hisAdapter;

    private ResultAdapter resultAdapter;

    private String type = "";

    private SuggestionSearch mSuggestionSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_search);
        initView();
        if (getIntent() != null) {
            if (getIntent().getStringExtra("type") != null) {
                type = getIntent().getStringExtra("type");
            }
        }
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);

        et_point.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    if (s.length() == 0) {
                        ll_his.setVisibility(View.VISIBLE);
                        ll_result.setVisibility(View.GONE);
                    } else {
                        ll_his.setVisibility(View.GONE);
                        ll_result.setVisibility(View.VISIBLE);
                        mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                                .city(BaseApplication.getCity())
                                .citylimit(true)
                                .keyword(s.toString()));
                    }
                }
            }
        });
        initHisAdapter();
    }


    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            //处理sug检索结果
            if (suggestionResult.getAllSuggestions() == null) {
                if (resultList.size() != 0) {
                    resultList.clear();
                }
                if (resultAdapter != null) {
                    resultAdapter.notifyDataSetChanged();
                }
//                Toast.makeText(SearchAty.this, "抱歉，未找到结果", Toast.LENGTH_LONG).show();
                return;
            }
            for (SuggestionResult.SuggestionInfo s : suggestionResult.getAllSuggestions()) {
                SearchBean searchBean = new SearchBean();
                searchBean.setKey(s.getKey());
                searchBean.setShi(s.getCity());
                searchBean.setQu(s.getDistrict());
                resultList.add(searchBean);
                Log.e(TAG, "-->getAddress   " + s.getAddress());
                Log.e(TAG, "-->getCity   " + s.getCity());
                Log.e(TAG, "-->getDistrict   " + s.getDistrict());
                Log.e(TAG, "-->getKey   " + s.getKey());
                Log.e(TAG, "-->getTag   " + s.getTag());
                Log.e(TAG, "-->getUid   " + s.getUid());
                Log.e(TAG, "-->getPt   " + s.getPt());
            }
            initResultAdapter();
        }
    };

    private void initView() {
        resultList = new ArrayList<>();
        hisRv = findViewById(R.id.rv);
        resultRv = findViewById(R.id.rv_result);
        ll_his = findViewById(R.id.ll_his);
        ll_result = findViewById(R.id.ll_result);
        et_point = findViewById(R.id.et_point);
        rl_his = findViewById(R.id.rl_his);
    }

    private void initHisAdapter() {
        View view = View.inflate(this, R.layout.item_his_no, null);
        hisAdapter = new HisAdapter(R.layout.item_his, HisUtil.getSearchHistory(SearchAty.this));
        hisRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        hisAdapter.setEmptyView(view);
        hisRv.setAdapter(hisAdapter);
        hisAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (("my").equals(type)) {
                    Intent intent = new Intent();
                    intent.putExtra("my", HisUtil.getSearchHistory(SearchAty.this).get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (("go".equals(type))) {
                    Intent intent = new Intent();
                    intent.putExtra("go", HisUtil.getSearchHistory(SearchAty.this).get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void initResultAdapter() {
        resultAdapter = new ResultAdapter(R.layout.item_his, resultList);
        resultRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        resultRv.setAdapter(resultAdapter);
        resultAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HisUtil.saveSearchHistory(resultList.get(position).getKey() + "-"
                        + resultList.get(position).getShi() + resultList.get(position).getQu(), SearchAty.this);
                if (("my").equals(type)) {
                    Intent intent = new Intent();
                    intent.putExtra("my", resultList.get(position).getKey());
                    setResult(RESULT_OK, intent);
                    finish();
                } else if (("go".equals(type))) {
                    Intent intent = new Intent();
                    intent.putExtra("go", resultList.get(position).getKey());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }

    public void click(View view) {
        HisUtil.deleteHistory(SearchAty.this);
        initHisAdapter();
    }


    public class HisAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public HisAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            String[] temp = null;
            temp = item.split("-");
            helper.setText(R.id.tv_zi, temp[0]);
            helper.setText(R.id.tv_shi, temp[1]);
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }

    public class ResultAdapter extends BaseQuickAdapter<SearchBean, BaseViewHolder> {
        public ResultAdapter(int layoutResId, List data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, SearchBean item) {
            helper.setText(R.id.tv_zi, item.getKey());
            helper.setText(R.id.tv_shi, item.getShi() + item.getQu());
//            helper.setImageResource(R.id.icon, item.getImageResource());
            // 加载网络图片
//            Glide.with(getContext()).load(item.getImgId()).into((ImageView) helper.getView(R.id.iv));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuggestionSearch.destroy();
    }
}
