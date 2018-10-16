package com.ruijing.zl.fragment;

import android.os.Bundle;
import android.view.View;

import com.ruijing.zl.R;
import com.ruijing.zl.base.BaseFM;

public class RecommendFragment extends BaseFM{

    public static RecommendFragment newInstance() {
        Bundle args = new Bundle();
        RecommendFragment fragment = new RecommendFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_recommend;
    }
}
