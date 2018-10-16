package com.ruijing.zl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.ruijing.zl.R;
import com.ruijing.zl.base.BaseUI;
import com.ruijing.zl.bean.LocationBean;
import com.ruijing.zl.bean.ProvinceBean;
import com.ruijing.zl.utils.ColorUtil;
import com.ruijing.zl.utils.StringUtil;
import com.ruijing.zl.utils.ToastUtil;
import com.ruijing.zl.views.widget.MyAddressSelector;
import com.ruijing.zl.views.widget.MyBottomDialog;
import com.ruijing.zl.views.widget.OnAddressSelectedListener;

public class CreateLocationActivity extends BaseUI implements View.OnClickListener, OnAddressSelectedListener, MyAddressSelector.OnDialogCloseListener {

    private ImageButton ib_back;
    private TextView tv_middle;
    private ImageView iv_clear;
    private EditText et_name;
    private EditText et_tel;
    private TextView tv_choose_location;
    private EditText et_info_location;
    private TextView tv_save;
    private boolean locationType = false;
    private MyBottomDialog addressChooseDialog;
    private String address;

    @Override
    protected int setLayout() {
        return R.layout.activity_create_location;
    }

    @Override
    protected void initView() {
        ib_back = bindView(R.id.ib_back);
        tv_middle = bindView(R.id.tv_middle);
        iv_clear = bindView(R.id.iv_clear);
        et_name = bindView(R.id.et_name);
        et_tel = bindView(R.id.et_tel);
        tv_choose_location = bindView(R.id.tv_choose_location);
        et_info_location = bindView(R.id.et_info_location);
        tv_save = bindView(R.id.tv_save);

        tv_middle.setText("创建地址");
        ib_back.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
        tv_choose_location.setOnClickListener(this);
        et_tel.addTextChangedListener(textWatcher);
        et_info_location.addTextChangedListener(textWatcher);
        et_name.addTextChangedListener(textWatcher);
    }

    @Override
    protected void initData() {
        LocationBean locationBean = (LocationBean) getIntent().getSerializableExtra("locationBean");
        if (locationBean != null) {
            tv_middle.setText("修改收货地址");

            locationType = true;
            et_name.setText(locationBean.getName());
            et_tel.setText(locationBean.getTel());
            tv_choose_location.setText(locationBean.getLocation());
            et_info_location.setText(locationBean.getLocationInfo());
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String nameStr = et_name.getText().toString().trim();
            String telStr = et_tel.getText().toString().trim();
            String locationInfoStr = et_info_location.getText().toString();

            boolean isSave = StringUtil.isEmpty(nameStr) || StringUtil.isEmpty(telStr) || StringUtil.isEmpty(locationInfoStr);
            tv_save.setBackgroundResource(isSave ? R.color.btn_bg_gray : R.color.btn_bg_red);
            tv_save.setEnabled(!isSave);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.iv_clear:
                et_tel.setText("");
                break;
            case R.id.tv_choose_location:
                showAddressPicker();
                break;
            case R.id.tv_save:
                if (StringUtil.isEmpty(address)) {
                    ToastUtil.showToast(this, "请选择所在地区");
                }else {

                }
                break;
            default:
                break;
        }
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
