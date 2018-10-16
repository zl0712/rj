package com.ruijing.zl.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ruijing.zl.R;
import com.ruijing.zl.adapter.GoodImageAdapter;
import com.ruijing.zl.adapter.GoodTypeInfoAdapter;
import com.ruijing.zl.base.BaseUI;
import com.ruijing.zl.bean.GoodImageBean;
import com.ruijing.zl.bean.GoodTypeInfoBean;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class GoodTypeInfoActivity extends BaseUI implements View.OnScrollChangeListener, View.OnClickListener {

    private NestedScrollView scrollView;
    private RelativeLayout rel_title;
    private TextView tv_middle;
    private ImageButton ib_back;
    private RecyclerView recyclerView;

    @Override
    protected int setLayout() {
        return R.layout.activity_good_type_info;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initView() {
        rel_title = bindView(R.id.rel_title);
        scrollView = bindView(R.id.scrollView);
        tv_middle = bindView(R.id.tv_middle);
        ib_back = bindView(R.id.ib_back);
        recyclerView = bindView(R.id.recyclerview);

        tv_middle.setTextColor(Color.argb((int) 0, 100, 100, 100));
        scrollView.setOnScrollChangeListener(this);
        ib_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        initRecycler();
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        List<GoodTypeInfoBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new GoodTypeInfoBean());
        }
        recyclerView.setAdapter(new GoodTypeInfoAdapter(R.layout.item_good_type_info, list));
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int height = 400;
        if (scrollY <= 0) {
            ib_back.setImageResource(R.mipmap.info_back);
            rel_title.setBackgroundColor(Color.argb((int) 0, 255, 255, 255));
            tv_middle.setTextColor(Color.argb((int) 0, 100, 100, 100));
        } else if (scrollY > 0 && scrollY <= height) {  //在滑动高度中时，设置透明度百分比（当前高度/总高度）
            double d = (double) scrollY / height;
            double alpha = (d * 255);
            rel_title.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
            ib_back.setImageResource(R.mipmap.info_back);
            tv_middle.setTextColor(Color.argb((int) alpha, 100, 100, 100));
        } else { //滑出总高度 完全不透明
            rel_title.setBackgroundColor(Color.argb((int) 255, 255, 255, 255));
            tv_middle.setTextColor(Color.argb((int) 255, 100, 100, 100));
            ib_back.setImageResource(R.mipmap.white_bg_back);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            default:
                break;
        }
    }
}
