package com.ruijing.zl.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruijing.zl.R;
import com.ruijing.zl.adapter.ViewPagerAdapter;
import com.ruijing.zl.base.BaseFM;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends BaseFM implements View.OnClickListener {

    private ViewPager viewPager;
    private TextView tv_home;
    private TextView tv_recommend;
    private View indicator;

    public static ViewPagerFragment newInstance() {
        Bundle args = new Bundle();
        ViewPagerFragment fragment = new ViewPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(FristFragment.newInstance());
        fragmentList.add(RecommendFragment.newInstance());
        viewPager.setAdapter(new ViewPagerAdapter(getFragmentManager(), fragmentList));
    }

    @Override
    protected void initView(View view) {
        indicator = bindView(view, R.id.indicator);
        viewPager = bindView(view, R.id.viewpager);
        tv_home = bindView(view, R.id.tv_home);
        tv_recommend = bindView(view, R.id.tv_recommend);

        tv_home.setOnClickListener(this);
        tv_recommend.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_viewpager;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                viewPager.setCurrentItem(0);
                initTextColor(0);
                buildIndicatorAnimatorTowards(tv_home).start();
                break;
            case R.id.tv_recommend:
                viewPager.setCurrentItem(1);
                initTextColor(1);
                buildIndicatorAnimatorTowards(tv_recommend).start();
                break;
            default:
                break;
        }
    }

    private void initTextColor(int current) {
        tv_home.setTextColor(current == 0 ? getResources().getColor(R.color.text_black) : getResources().getColor(R.color.text_grey));
        tv_recommend.setTextColor(current == 1 ? getResources().getColor(R.color.text_black) : getResources().getColor(R.color.text_grey));
    }

    //切换动画
    private AnimatorSet buildIndicatorAnimatorTowards(TextView tab) {
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(indicator, "X", indicator.getX(), tab.getX());

        final ViewGroup.LayoutParams params = indicator.getLayoutParams();
        ValueAnimator widthAnimator = ValueAnimator.ofInt(params.width, tab.getMeasuredWidth());
        widthAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                params.width = (int) animation.getAnimatedValue();
                indicator.setLayoutParams(params);
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.playTogether(xAnimator, widthAnimator);

        return set;
    }
}
