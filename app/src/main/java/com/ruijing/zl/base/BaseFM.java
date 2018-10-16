package com.ruijing.zl.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruijing.zl.R;

import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFM extends SupportFragment {

    //当前页,每页显示的条数
    public int currentPage = 1, pageSize = 10;

    protected final String TAG_LOAD = "load";
    protected final String TAG_REFRESH = "refresh";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);

        initView(view);
        initClickListener();
        return view;
    }

    public void initClickListener() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    protected abstract void initData();

    protected abstract void initView(View view);

    protected abstract int getLayout();

    public <T extends View> T bindView(View view, int id) {
        return (T) view.findViewById(id);
    }

    //跳转页面的基础方法
    private void goToBase(Class clz, Bundle bundle, int requestCode, boolean isFinish) {
        Intent intent = new Intent(getActivity(), clz);
        //设置bundle
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        //设置requestcode
        startActivityForResult(intent, requestCode);
        //动画效果
        //overridePendingTransition(R.anim.activity_open_common, 0);
        //设置是否结束当前页面
        if (isFinish) {
            getActivity().finish();
        }
    }

    public void goTo(Class clz) {
        goTo(clz, null);
    }

    public void goTo(Class clz, Bundle bundle) {
        goToBase(clz, bundle, -1, false);
    }

    public void goTo(Class clz, int requestCode) {
        goToBase(clz, null, requestCode, false);
    }

    public void goTo(Class clz, int requestCode, Bundle bundle) {
        goToBase(clz, bundle, requestCode, false);
    }

    public void goToAndFinish(final Class clz) {
        goToBase(clz, null, -1, true);
    }

    public void goToAndFinish(final Class clz, Bundle bundle) {
        goToBase(clz, bundle, -1, true);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void steepStatysBar() {//状态栏沉浸
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
