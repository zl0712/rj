package com.ruijing.zl.views.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.graphics.ColorUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruijing.zl.R;
import com.ruijing.zl.activity.IndentActivity;
import com.ruijing.zl.bean.GoodTypeBean;
import com.ruijing.zl.utils.ColorUtil;
import com.ruijing.zl.views.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class BuyDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private TextView tv_price;
    private FlowLayout flowlayout;
    private TextView tv_location;
    private TextView tv_yanshi;
    private TextView tv_zhijie;
    private ImageView iv_buy;
    private ImageView iv_close;

    public BuyDialog(@NonNull Context context) {
        this(context, R.style.dialogNoBg);
    }

    public BuyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_buy_dialog, null);
        setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

        tv_price = view.findViewById(R.id.tv_price);
        flowlayout = view.findViewById(R.id.flowlayout);
        tv_location = view.findViewById(R.id.tv_location);
        tv_yanshi = view.findViewById(R.id.tv_yanshi);
        tv_zhijie = view.findViewById(R.id.tv_zhijie);
        iv_close = view.findViewById(R.id.iv_close);
        iv_buy = view.findViewById(R.id.iv_buy);

        tv_yanshi.setOnClickListener(this);
        tv_zhijie.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        iv_buy.setOnClickListener(this);

        List<GoodTypeBean> goodTypeBeans = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GoodTypeBean goodTypeBean = new GoodTypeBean();
            if (i == 0) goodTypeBean.setChoose(true);
            else goodTypeBean.setChoose(false);
            goodTypeBean.setType("dfadf" + i);
            goodTypeBeans.add(goodTypeBean);
        }
        initGoodType(goodTypeBeans);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_yanshi:
                buyType(1);
                break;
            case R.id.tv_zhijie:
                buyType(2);
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.iv_buy:
                dismiss();
                context.startActivity(new Intent(context, IndentActivity.class));
                break;
            default:
                break;
        }
    }

    private void buyType(int type) {
        if (type == 1) {
            tv_zhijie.setTextColor(ColorUtil.getResourceColor(context, R.color.text_black));
            tv_zhijie.setBackgroundResource(R.drawable.payment_type_gray);
            tv_yanshi.setTextColor(ColorUtil.getResourceColor(context, R.color.text_orange));
            tv_yanshi.setBackgroundResource(R.drawable.payment_type_orange);
        } else {
            tv_zhijie.setTextColor(ColorUtil.getResourceColor(context, R.color.text_orange));
            tv_zhijie.setBackgroundResource(R.drawable.payment_type_orange);
            tv_yanshi.setTextColor(ColorUtil.getResourceColor(context, R.color.text_grey));
            tv_yanshi.setBackgroundResource(R.drawable.payment_type_gray);
        }
    }

    //初始化商品类型
    private void initGoodType(final List<GoodTypeBean> goodTypeBeans) {
        for (int i = 0; i < goodTypeBeans.size(); i++) {
            final int id = i;
            TextView tv;
            final GoodTypeBean goodTypeBean = goodTypeBeans.get(i);
            if (goodTypeBean.isChoose()) {
                tv = (TextView) LayoutInflater.from(context).inflate(
                        R.layout.item_good_type_orange, flowlayout, false);
            } else {
                tv = (TextView) LayoutInflater.from(context).inflate(
                        R.layout.item_good_type, flowlayout, false);
            }
            tv.setText(goodTypeBean.getType());
            //点击事件
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flowlayout.deleteAllView();
                    for (int j = 0; j < goodTypeBeans.size(); j++) {
                        if (id == j) goodTypeBeans.get(j).setChoose(true);
                        else goodTypeBeans.get(j).setChoose(false);
                    }
                    initGoodType(goodTypeBeans);
                }
            });
            flowlayout.addView(tv);
        }
    }
}
