package com.ruijing.zl.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruijing.zl.R;
import com.ruijing.zl.activity.IndentActivity;
import com.ruijing.zl.bean.GoodTypeBean;
import com.ruijing.zl.utils.ColorUtil;
import com.ruijing.zl.views.FlowLayout;

import java.util.ArrayList;
import java.util.List;

public class PayTypeDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private RelativeLayout rel_liji;
    private RelativeLayout rel_yanchi;
    private ImageView iv_yanchi;
    private ImageView iv_liji;
    private ImageView iv_affirm;
    private ImageView iv_cancel;
    private OnDialogClickListener onDialogClickListener;
    private int payType;

    public PayTypeDialog(@NonNull Context context) {
        this(context, R.style.dialogNoBg);
    }

    public PayTypeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_pay_type, null);
        setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        view.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.BottomDialog_Animation);

        view.findViewById(R.id.rel_liji).setOnClickListener(this);
        view.findViewById(R.id.rel_yanchi).setOnClickListener(this);
        view.findViewById(R.id.iv_affirm).setOnClickListener(this);
        view.findViewById(R.id.iv_cancel).setOnClickListener(this);
        iv_liji = view.findViewById(R.id.iv_liji);
        iv_yanchi = view.findViewById(R.id.iv_yanchi);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rel_liji:
                payType = 0;
                initCheck(payType);
                break;
            case R.id.rel_yanchi:
                payType = 1;
                initCheck(payType);
                break;
            case R.id.iv_affirm:
                if (onDialogClickListener != null)
                    onDialogClickListener.dialogClickListener(payType);
                dismiss();
                break;
            case R.id.iv_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void initCheck(int payType) {
        iv_liji.setImageResource(payType == 0 ? R.mipmap.iv_check : R.mipmap.iv_not_check);
        iv_yanchi.setImageResource(payType == 1 ? R.mipmap.iv_check : R.mipmap.iv_not_check);
    }

    public void setOnDialogClickListener(OnDialogClickListener onDialogClickListener) {
        this.onDialogClickListener = onDialogClickListener;
    }

    public interface OnDialogClickListener {
        void dialogClickListener(int type);
    }
}
