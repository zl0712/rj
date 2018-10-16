package com.ruijing.zl.views.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ruijing.zl.R;
import com.ruijing.zl.utils.ScreenUtil;


/**
 * Created by smartTop on 2016/10/19.
 */

public class MyBottomDialog extends Dialog {

    private MyAddressSelector selector;

    public MyBottomDialog(Context context) {
        super(context, R.style.bottom_dialog);
        init(context);
    }

    public MyBottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public MyBottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        selector = new MyAddressSelector(context);
        setContentView(selector.getView());

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //params.height = Dev.dp2px(context, 256);
        params.height = ScreenUtil.getScreenHeight(context) / 3 * 2;
        window.setAttributes(params);

        window.setGravity(Gravity.BOTTOM);
    }

    public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
        this.selector.setOnAddressSelectedListener(listener);
    }

    public static MyBottomDialog show(Context context) {
        return show(context, null);
    }

    public static MyBottomDialog show(Context context, OnAddressSelectedListener listener) {
        MyBottomDialog dialog = new MyBottomDialog(context, R.style.bottom_dialog);
        dialog.selector.setOnAddressSelectedListener(listener);
        dialog.show();

        return dialog;
    }
    public void setDialogDismisListener(MyAddressSelector.OnDialogCloseListener listener){
        this.selector.setOnDialogCloseListener(listener);
    }
    /**
     *设置字体选中的颜色
     */
    public void setTextSelectedColor(int selectedColor){
        this.selector.setTextSelectedColor(selectedColor);
    }
    /**
     *设置字体没有选中的颜色
     */
    public void setTextUnSelectedColor(int unSelectedColor){
        this.selector.setTextUnSelectedColor(unSelectedColor);
    }
    /**
     * 设置字体的大小
     */
    public void setTextSize(float dp){
       this.selector.setTextSize(dp);
    }
    /**
     * 设置字体的背景
     */
    public void setBackgroundColor(int colorId){
       this.selector.setBackgroundColor(colorId);
    }
    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(int colorId){
        this.selector.setIndicatorBackgroundColor(colorId);
    }
    /**
     * 设置指示器的背景
     */
    public void setIndicatorBackgroundColor(String color){
        this.selector.setIndicatorBackgroundColor(color);
    }
}
