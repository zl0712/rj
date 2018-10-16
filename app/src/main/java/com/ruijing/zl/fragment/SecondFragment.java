package com.ruijing.zl.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.ruijing.zl.R;
import com.ruijing.zl.activity.GoodTypeInfoActivity;
import com.ruijing.zl.adapter.ImagePagerAdapter;
import com.ruijing.zl.adapter.TypeAdapter;
import com.ruijing.zl.base.BaseFM;
import com.ruijing.zl.views.carousel.CarouselViewPager;

import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends BaseFM implements View.OnClickListener, TypeAdapter.OnItemClickListener {

    private CarouselViewPager viewPager;
    private RecyclerView recyclerview;

    public static SecondFragment newInstance() {
        Bundle args = new Bundle();
        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        List<String> urls = new ArrayList<>();
        urls.add("11");
        urls.add("11");
        urls.add("11");
        ImagePagerAdapter imageShowAdapter = new ImagePagerAdapter(getActivity(), urls, viewPager, this);
        viewPager.setAdapter(imageShowAdapter);
        viewPager.setOffscreenPageLimit(urls.size());
        viewPager.setPageMargin(20);

        TypeAdapter typeAdapter = new TypeAdapter(getActivity());
        recyclerview.setAdapter(typeAdapter);
        typeAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initView(View view) {
        recyclerview = bindView(view, R.id.recyclerview);
        viewPager = bindView(view, R.id.viewpager);

        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_second;
    }

    @Override
    public void onClick(View v) {

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
    public void onItemClick(int position) {
        goTo(GoodTypeInfoActivity.class);
    }
}
