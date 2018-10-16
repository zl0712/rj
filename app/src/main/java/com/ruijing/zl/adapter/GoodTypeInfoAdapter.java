package com.ruijing.zl.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruijing.zl.R;
import com.ruijing.zl.bean.GoodTypeInfoBean;
import com.ruijing.zl.bean.LocationBean;

import java.util.List;

/**
 * Created by Admin on 2016/8/9.
 */
public class GoodTypeInfoAdapter extends BaseQuickAdapter<GoodTypeInfoBean, BaseViewHolder> {

    public GoodTypeInfoAdapter(int layoutResId, @Nullable List<GoodTypeInfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodTypeInfoBean item) {

    }
}