package com.ruijing.zl.activity;

import com.ruijing.zl.R;
import com.ruijing.zl.base.BaseUI;
import com.ruijing.zl.fragment.FristFragment;
import com.ruijing.zl.fragment.MineFragment;
import com.ruijing.zl.fragment.SecondFragment;
import com.ruijing.zl.fragment.ThirdFragment;
import com.ruijing.zl.fragment.ViewPagerFragment;
import com.ruijing.zl.views.bottombar.BottomBar;
import com.ruijing.zl.views.bottombar.BottomBarTab;

import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseUI implements BottomBar.OnTabSelectedListener {
    private BottomBar bottomBar;
    private SupportFragment[] fragments = new SupportFragment[4];
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOUR = 3;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        bottomBar = bindView(R.id.bottomBar);
        bottomBar.addItem(new BottomBarTab(this, R.mipmap.home_first_black, R.mipmap.home_first_red, getString(R.string.zhuye)));
        bottomBar.addItem(new BottomBarTab(this, R.mipmap.home_second_black, R.mipmap.home_second_red, getString(R.string.fenlei)));
        bottomBar.addItem(new BottomBarTab(this, R.mipmap.home_third_black, R.mipmap.home_third_red, getString(R.string.faxian)));
        bottomBar.addItem(new BottomBarTab(this, R.mipmap.home_mine_black, R.mipmap.home_mine_red, getString(R.string.wode)));
        bottomBar.setOnTabSelectedListener(this);

        SupportFragment firstFragment = findFragment(ViewPagerFragment.class);
        if (firstFragment == null) {
            fragments[FIRST] = ViewPagerFragment.newInstance();
            fragments[SECOND] = SecondFragment.newInstance();
            fragments[THIRD] = ThirdFragment.newInstance();
            fragments[FOUR] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_tab_container, FIRST, fragments[FIRST], fragments[SECOND], fragments[THIRD], fragments[FOUR]);
        } else {
            fragments[FIRST] = firstFragment;
            fragments[SECOND] = findFragment(SecondFragment.class);
            fragments[THIRD] = findFragment(ThirdFragment.class);
            fragments[FOUR] = findFragment(MineFragment.class);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onTabSelected(int position, int prePosition) {
        showHideFragment(fragments[position], fragments[prePosition]);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {
        final SupportFragment currentFragment = fragments[position];
        int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

        if (count > 1) {
            if (currentFragment instanceof FristFragment) {
                currentFragment.popToChild(FristFragment.class
                        , false);
            } else if (currentFragment instanceof SecondFragment) {
                currentFragment.popToChild(SecondFragment.class
                        , false);
            } else if (currentFragment instanceof ThirdFragment) {
                currentFragment.popToChild(ThirdFragment.class, false);
            } else if (currentFragment instanceof MineFragment) {
                currentFragment.popToChild(MineFragment.class, false);
            }
        }
    }
}
