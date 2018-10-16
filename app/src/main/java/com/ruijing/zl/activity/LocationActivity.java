package com.ruijing.zl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ruijing.zl.R;
import com.ruijing.zl.adapter.LocationAdapter;
import com.ruijing.zl.base.BaseUI;
import com.ruijing.zl.bean.LocationBean;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends BaseUI implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    private ImageButton ib_back;
    private RecyclerView recyclerView;
    private TextView tv_middle;
    private LocationAdapter locationAdapter;
    private List<LocationBean> locationBeanList;
    private TextView tv_create;
    private ImageView iv_empty;
    private View v_shade;

    @Override
    protected int setLayout() {
        return R.layout.activity_location;
    }

    @Override
    protected void initView() {
        ib_back = bindView(R.id.ib_back);
        tv_middle = bindView(R.id.tv_middle);
        recyclerView = bindView(R.id.recyclerview);
        tv_create = bindView(R.id.tv_create);
        iv_empty = bindView(R.id.iv_empty);
        v_shade = bindView(R.id.v_shade);

        tv_middle.setText("收货地址");
        ib_back.setOnClickListener(this);
        tv_create.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        locationBeanList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            LocationBean locationBean = new LocationBean();
            locationBean.setTel("123123123123");
            locationBean.setName("发发发");
            locationBean.setLocation("浙江省杭州市余杭区");
            locationBean.setLocationInfo("八方城" + i + "号楼");
            if (i == 0) locationBean.setChoose(true);
            locationBeanList.add(locationBean);
        }
        locationAdapter = new LocationAdapter(R.layout.item_location, locationBeanList);
        locationAdapter.setOnItemClickListener(this);
        locationAdapter.setOnItemChildClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(locationAdapter);


        iv_empty.setVisibility(locationBeanList.size() == 0 ? View.VISIBLE : View.GONE);
        v_shade.setVisibility(locationBeanList.size() == 0 ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.tv_create:
                openActivity(CreateLocationActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, final int position) {
        for (int i = 0; i < locationBeanList.size(); i++) {
            LocationBean locationBean = locationBeanList.get(i);
            if (i == position) locationBean.setChoose(true);
            else locationBean.setChoose(false);
        }
        locationAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        LocationBean locationBean = locationBeanList.get(position);
        switch (view.getId()) {
            case R.id.iv_up:
                Bundle bundle = new Bundle();
                bundle.putSerializable("locationBean", locationBean);
                openActivity(CreateLocationActivity.class, bundle);
                break;
            case R.id.tv_menu:
                locationBeanList.remove(locationBean);
                locationAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
}
