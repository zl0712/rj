package com.ruijing.zl.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ruijing.zl.R;
import com.ruijing.zl.activity.GoodInfoActivity;
import com.ruijing.zl.adapter.FirstAdapter;
import com.ruijing.zl.adapter.ImagePagerAdapter;
import com.ruijing.zl.base.BaseFM;
import com.ruijing.zl.bean.FirstShopBean;
import com.ruijing.zl.views.carousel.CarouselViewPager;

import java.util.ArrayList;
import java.util.List;

public class FristFragment extends BaseFM implements View.OnClickListener, FirstAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private CarouselViewPager viewPager;

    public static FristFragment newInstance() {
        Bundle args = new Bundle();
        FristFragment fragment = new FristFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        List<FirstShopBean> shops = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FirstShopBean firstShopBean = new FirstShopBean();
            firstShopBean.setName("国际潮牌馆" + i);
            List<FirstShopBean.Shop> list = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                FirstShopBean.Shop shop = new FirstShopBean.Shop();
                shop.setContent("红蓝限量版配色火热预售中！");
                shop.setName("FILA破坏者一代 秋冬新款 颜值最高的潮鞋~");
                shop.setPrice(500 + j);
                shop.setUrl("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E9%9E%8B%E5%AD%90&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&cs=2271880153,718902229&os=209714441,2378457945&simid=3527550769,370592165&pn=1&rn=1&di=12903480810&ln=1784&fr=&fmq=1536810387272_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F28%2F93%2F67E58PICdF7_1024.jpg&rpstart=0&rpnum=0&adpicid=0");
                list.add(shop);
            }
            firstShopBean.setList(list);
            shops.add(firstShopBean);
        }
        FirstAdapter firstAdapter = new FirstAdapter(getActivity(), shops);
        recyclerView.setAdapter(firstAdapter);
        firstAdapter.setOnItemClickListener(this);
        initFirstBanner();
    }

    @Override
    protected void initView(View view) {
        recyclerView = bindView(view, R.id.recyclerview);
        viewPager = bindView(view, R.id.viewpager);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_first;
    }

    private void initFirstBanner() {
        List<String> urls = new ArrayList<>();
        urls.add("11");
        urls.add("11");
        urls.add("11");
        ImagePagerAdapter imageShowAdapter = new ImagePagerAdapter(getActivity(), urls, viewPager, this);
        viewPager.setAdapter(imageShowAdapter);
        viewPager.setOffscreenPageLimit(urls.size());
        viewPager.setPageMargin(20);
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
        goTo(GoodInfoActivity.class);
    }
}
