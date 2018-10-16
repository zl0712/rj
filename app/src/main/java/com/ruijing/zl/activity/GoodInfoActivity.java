package com.ruijing.zl.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ruijing.zl.R;
import com.ruijing.zl.adapter.GoodImageAdapter;
import com.ruijing.zl.adapter.ImagePagerAdapter;
import com.ruijing.zl.base.BaseUI;
import com.ruijing.zl.bean.GoodImageBean;
import com.ruijing.zl.views.carousel.CarouselViewPager;
import com.ruijing.zl.views.dialog.BuyDialog;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class GoodInfoActivity extends BaseUI implements View.OnClickListener, ViewPager.OnPageChangeListener, View.OnScrollChangeListener {
    private CarouselViewPager viewPager;
    private ImageButton ib_back;
    private TextView tv_indicator;
    private TextView tv_new_price;
    private TextView tv_old_price;
    private List<String> urls;
    private RelativeLayout rel_title;
    private NestedScrollView scrollView;
    private TextView tv_middle;
    private RecyclerView recyclerView;
    private ImageButton iv_promptly;
    private ImageButton iv_postpone;
    private TextView tv_type;
    private BuyDialog buyDialog;
    private TextView tv_location;//如果选择了地址之后显示地址
    private TextView tv_guige;

    @Override
    protected int setLayout() {
        return R.layout.activity_good_info;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initView() {
        viewPager = bindView(R.id.viewpager);
        ib_back = bindView(R.id.ib_back);
        iv_promptly = bindView(R.id.iv_promptly);
        iv_postpone = bindView(R.id.iv_postpone);
        tv_indicator = bindView(R.id.tv_indicator);
        tv_old_price = bindView(R.id.tv_old_price);
        tv_new_price = bindView(R.id.tv_new_price);
        rel_title = bindView(R.id.rel_title);
        scrollView = bindView(R.id.scrollView);
        tv_middle = bindView(R.id.tv_middle);
        recyclerView = bindView(R.id.recyclerview);
        tv_type = bindView(R.id.tv_type);
        tv_location = bindView(R.id.tv_location);
        tv_guige = bindView(R.id.tv_guige);

        tv_middle.setTextColor(Color.argb((int) 0, 100, 100, 100));
        tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中间横线（删除线）
        ib_back.setOnClickListener(this);
        iv_promptly.setOnClickListener(this);
        iv_postpone.setOnClickListener(this);
        tv_location.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        tv_guige.setOnClickListener(this);
        scrollView.setOnScrollChangeListener(this);
    }

    @Override
    protected void initData() {
        initFirstBanner();
        initRecycler();

        tv_old_price.setText("￥7999");
        tv_new_price.setText("4999");
        buyDialog = new BuyDialog(this);
    }

    private void initRecycler() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
        List<GoodImageBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GoodImageBean goodImageBean = new GoodImageBean();
            goodImageBean.setUrl("1");
            list.add(goodImageBean);
        }
        recyclerView.setAdapter(new GoodImageAdapter(R.layout.item_good_image, list));
    }

    private void initFirstBanner() {
        urls = new ArrayList<>();
        urls.add("11");
        urls.add("11");
        urls.add("11");
        ImagePagerAdapter imageShowAdapter = new ImagePagerAdapter(this, urls, viewPager, this);
        viewPager.setAdapter(imageShowAdapter);
        viewPager.setOffscreenPageLimit(urls.size());
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.iv_promptly:
            case R.id.iv_postpone:
            case R.id.tv_type:
            case R.id.tv_guige:
            case R.id.tv_location:
                buyDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        viewPager.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPager.resume();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }

    @Override
    public void onPageSelected(int i) {
        tv_indicator.setText(i % 3 + 1 + "/" + urls.size());
    }

    @Override
    public void onPageScrollStateChanged(int i) {
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
}
