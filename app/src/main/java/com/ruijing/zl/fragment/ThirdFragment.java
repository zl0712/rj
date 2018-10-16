package com.ruijing.zl.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ruijing.zl.R;
import com.ruijing.zl.adapter.ThirdAdapter;
import com.ruijing.zl.base.BaseFM;

public class ThirdFragment extends BaseFM {

    private RecyclerView recyclerView;

    public static ThirdFragment newInstance() {
        Bundle args = new Bundle();
        ThirdFragment fragment = new ThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        recyclerView = bindView(view, R.id.recyclerview);

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ThirdAdapter thirdAdapter = new ThirdAdapter(getActivity());
        recyclerView.setAdapter(thirdAdapter);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_third;
    }
}
