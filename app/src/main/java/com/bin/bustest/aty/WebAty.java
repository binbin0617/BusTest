package com.bin.bustest.aty;

import android.os.Bundle;
import android.widget.RelativeLayout;

import com.bin.bustest.R;
import com.bin.bustest.base.BaseAty;
import com.just.agentweb.AgentWeb;

public class WebAty extends BaseAty {
    protected AgentWeb mAgentWeb;
    private RelativeLayout mainLayout;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_web);
        if(getIntent()!=null){
            if(getIntent().getStringExtra("url")!=null){
                url = getIntent().getStringExtra("url");
            }else{
                url = "http://www.jd.com";
            }
        }else{
            url = "http://www.jd.com";
        }
        mainLayout = findViewById(R.id.main_layout);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((RelativeLayout) mainLayout, new RelativeLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }

}
