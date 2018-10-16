package com.ruijing.zl.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ViewPagerSlide extends ViewPager {

    public ViewPagerSlide(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    //这个构造方法必须有要不然xml中无法加载 会报错
    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //这个方法是为了避免手势滑动的时候产生异常
	/*@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		try {
			return super.onInterceptTouchEvent(ev);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return false;
		}
	}*/
    //禁止滑动切换fragment
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;//不要拦截事件   事件要分发给后面的控件
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;//不自己去处理事件
    }
}

