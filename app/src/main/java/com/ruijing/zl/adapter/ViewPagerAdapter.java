package com.ruijing.zl.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Admin on 2016/8/9.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragmentList;
    FragmentManager fragmentManager;

    public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}