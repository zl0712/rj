package com.ruijing.zl.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruijing.zl.R;
import com.ruijing.zl.bean.LocationBean;

import java.util.List;

/**
 * Created by Admin on 2016/8/9.
 */
public class LocationAdapter extends BaseQuickAdapter<LocationBean, BaseViewHolder> {

    public LocationAdapter(int layoutResId, @Nullable List<LocationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocationBean item) {
        helper.setImageResource(R.id.iv_choose, item.isChoose() ? R.mipmap.iv_check : R.mipmap.iv_not_check);
        helper.setText(R.id.tv_name, item.getName() + "\t" + item.getTel());
        helper.setText(R.id.tv_location, item.getLocation() + item.getLocationInfo());

        helper.addOnClickListener(R.id.iv_up)
                .addOnClickListener(R.id.tv_menu);
    }
}