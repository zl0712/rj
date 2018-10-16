package com.ruijing.zl.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ruijing.zl.R;
import com.ruijing.zl.bean.GoodImageBean;

import java.util.List;

public class GoodImageAdapter extends BaseQuickAdapter<GoodImageBean, BaseViewHolder> {

    public GoodImageAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodImageBean item) {
        helper.setImageResource(R.id.iv_icon, R.mipmap.shop_icon);
    }
}