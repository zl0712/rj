package com.ruijing.zl.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by bbx on 2016/5/6.
 */
public class ToastUtil {
    private static Toast mToast;

    public static void showToast(Context context, String text, int duration) {
        if (mToast != null) {
            mToast.setText(text);
            mToast.setDuration(duration);
        } else {
            mToast = Toast.makeText(context,text, duration);
        }
        mToast.show();
    }

    public static void showToast(Context context, String message){
        showToast(context,message, Toast.LENGTH_SHORT);
    }
}
