package com.ruijing.zl.utils;

import android.content.Context;


public class ColorUtil {
    @SuppressWarnings("deprecation")
	public static int getResourceColor(Context context, int resource){
        return context.getResources().getColor(resource);
    }
}
