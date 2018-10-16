package com.ruijing.zl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruijing.zl.R;
import com.ruijing.zl.base.BaseUI;
import com.ruijing.zl.bean.ProvinceBean;
import com.ruijing.zl.views.dialog.PayTypeDialog;
import com.ruijing.zl.views.widget.MyAddressSelector;
import com.ruijing.zl.views.widget.MyBottomDialog;
import com.ruijing.zl.views.widget.OnAddressSelectedListener;

public class IndentActivity extends BaseUI implements View.OnClickListener, PayTypeDialog.OnDialogClickListener, OnAddressSelectedListener, MyAddressSelector.OnDialogCloseListener {

    private ImageView iv_clear;
    private EditText et_tel;
    private EditText et_tel1;
    private TextView tv_choose_location;
    private ImageButton ib_back;
    private PayTypeDialog payTypeDialog;
    private TextView tv_yanshi_hint;
    private TextView tv_pay_type;
    private MyBottomDialog addressChooseDialog;
    private String address;

    @Override
    protected int setLayout() {
        return R.layout.activity_indent;
    }

    @Override
    protected void initView() {
        ib_back = bindView(R.id.ib_back);
        iv_clear = bindView(R.id.iv_clear);
        et_tel1 = bindView(R.id.et_tel);
        tv_choose_location = bindView(R.id.tv_choose_location);
        tv_yanshi_hint = bindView(R.id.tv_yanshi_hint);
        tv_pay_type = bindView(R.id.tv_pay_type);
        bindView(R.id.rel_pay_type).setOnClickListener(this);
        bindView(R.id.rel_location).setOnClickListener(this);

        iv_clear.setOnClickListener(this);
        ib_back.setOnClickListener(this);
        tv_choose_location.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        payTypeDialog = new PayTypeDialog(this);
        payTypeDialog.setOnDialogClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_clear:
                et_tel.setText("");
                break;
            case R.id.tv_choose_location:
                showAddressPicker();
                break;
            case R.id.ib_back:
                finish();
                break;
            case R.id.rel_pay_type:
                payTypeDialog.show();
                break;
            case R.id.rel_location:
                openActivity(LocationActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void dialogClickListener(int type) {
        tv_yanshi_hint.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
        tv_pay_type.setVisibility(type == 1 ? View.VISIBLE : View.GONE);
    }

    private void showAddressPicker() {
        if (addressChooseDialog != null) {
            addressChooseDialog.show();
        } else {
            addressChooseDialog = new MyBottomDialog(this);
            addressChooseDialog.setOnAddressSelectedListener(this);
            addressChooseDialog.setDialogDismisListener(this);
            addressChooseDialog.show();
        }
    }

    @Override
    public void onAddressSelected(ProvinceBean province, ProvinceBean city, ProvinceBean county) {
        address = province.getAreaname() + city.getAreaname() + county.getAreaname();
        if (addressChooseDialog != null) addressChooseDialog.dismiss();
        tv_choose_location.setText(address);
    }

    @Override
    public void dialogclose() {
        if (addressChooseDialog != null) addressChooseDialog.dismiss();
    }
}
