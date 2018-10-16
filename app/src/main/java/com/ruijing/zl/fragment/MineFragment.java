package com.ruijing.zl.fragment;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ruijing.zl.R;
import com.ruijing.zl.base.BaseFM;
import com.ruijing.zl.utils.ScreenUtil;
import com.ruijing.zl.views.roundimageview.RoundedImageView;

public class MineFragment extends BaseFM {

    private RoundedImageView iv_icon;
    private ScrollView scrollView;
    private TextView tv_middle;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        ScreenUtil instance = ScreenUtil.instance(getActivity());
        iv_icon = bindView(view, R.id.iv_icon);
        scrollView = bindView(view, R.id.scrollView);
        tv_middle = bindView(view, R.id.tv_middle);

        iv_icon.setCornerRadius(instance.dip2px(60));

        initScrollView();
    }

    @SuppressLint("NewApi")
    private void initScrollView() {
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                tv_middle.setVisibility(scrollY > 200 ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }
}
