package com.ruijing.zl.views.carousel;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Administrator on 2017/6/9.
 */

public class CarouselViewPager extends ViewPager {
    @IntDef({RESUME, PAUSE, DESTROY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LifeCycle {
    }

    public static final int RESUME = 0;
    public static final int PAUSE = 1;
    public static final int DESTROY = 2;
    /**
     * 生命周期状态，保证{@link #mCarouselTimer}在各生命周期选择执行策略
     */
    private int mLifeCycle = RESUME;
    /**
     * 是否正在触摸状态，用以防止触摸滑动和自动轮播冲突
     */
    /**
     * 超时时间
     */
    private int delayTime = 3000;
    private boolean canPlay = true;

    /**
     * 轮播定时器
     */
    private ScheduledExecutorService mCarouselTimer;

    /**
     * 有数据时，才开始进行轮播
     */
    //private boolean hasData;

    //private Timer mTimer;
    //private TimerTask mTimerTask;

    public CarouselViewPager(Context context) {
        super(context);
    }

    public CarouselViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLifeCycle(@LifeCycle int lifeCycle) {
        this.mLifeCycle = lifeCycle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                canPlay = false;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_OUTSIDE:
                canPlay = true;
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        if (canPlay)
        super.setCurrentItem(item);
    }

    private Handler mChangeHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (getAdapter() != null)
                if (getAdapter().getCount() > 1){
                    setCurrentItem(getCurrentItem() + 1);
                    mChangeHandler.sendEmptyMessageDelayed(0,delayTime);
                }
        }
    };

    private void startTimer(){
        mChangeHandler.sendEmptyMessageDelayed(0,delayTime);
        /*if(this.mTimer != null) {
            this.mTimer.cancel();
        }
        mTimer = new Timer();
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    UIUtils.post(new Runnable() {
                        @Override
                        public void run() {
                            if (getAdapter() != null)
                            if (getAdapter().getCount() > 1){
                                setCurrentItem(getCurrentItem() + 1);
                            }
                        }
                    });
                }
            };
        }
        if(mTimer != null && mTimerTask != null){
            mTimer.schedule(mTimerTask, 1000, delayTime);
        }*/

    }

    private void stopTimer(){
        mChangeHandler.removeMessages(0);
        /*if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }*/
    }

    public void resume(){
        canPlay = true;
        startTimer();
    }

    public void pause(){
        canPlay = false;
        stopTimer();
    }


    /*public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }*/

    public void setDelay(int delayTime) {
        this.delayTime = delayTime;
    }
}
