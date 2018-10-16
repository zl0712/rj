package com.ruijing.zl.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 类描述：获取屏幕信息的工具类
 * 修改人：
 * 修改时间：
 * 修改备注：
 */

public class ScreenUtil {
	private Context mCtx;
	private static ScreenUtil mScreenTools;

	public static ScreenUtil instance(Context ctx) {
		if (null == mScreenTools) {
			mScreenTools = new ScreenUtil(ctx);
		}
		return mScreenTools;
	}

	private ScreenUtil(Context ctx) {
		mCtx = ctx.getApplicationContext();
	}

	//获取屏幕的宽度
	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context){
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		return width;
	}

	//dp转px
	public static int dp2px(float dp,Context context) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}

	//dp转px
	public static float dp2pxF(float dp,Context context) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
	

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 *
	 * @param pxValue
	 * @param context
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 *
	 * @param spValue
	 * @param context
	 *            （DisplayMetrics类中属性scaledDensity）
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	//获取屏幕高度
	public static int getScreenHeight(Context context){
		WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);

		@SuppressWarnings("deprecation")
		int height = wm.getDefaultDisplay().getHeight();
		return height;
	}

	//获取应用高度
	public static int getAppHeight(Context context){
		return getScreenHeight(context) - getStatusBarHeight(context);
	}

	//获取状态栏高度
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	//获取dimen资源的值
	public static float getResourceDimens(Context context, int resourceId){
		return context.getResources().getDimension(resourceId);
	}

	//判断activity是否在前台显示
	public static boolean isForeground(Activity context, String className) {
		if (context == null || TextUtils.isEmpty(className)) {
			return false;
		}
		if (context.isFinishing()){
			return false;
		}

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		@SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
		if (list != null && list.size() > 0) {
			ComponentName cpn = list.get(0).topActivity;
			if (className.equals(cpn.getClassName())) {
				return true;
			}
		}
		return false;
	}

	//获取某个字符串的宽度
	public static int getTextWidth(Paint paint, String str) {
		int iRet = 0;
		if (str != null && str.length() > 0) {
			int len = str.length();
			float[] widths = new float[len];
			paint.getTextWidths(str, widths);
			for (int j = 0; j < len; j++) {
				iRet += (int) Math.ceil(widths[j]);
			}
		}
		return iRet;
	}

	/**
	 * 得到设备的密度
	 */
	public static float getScreenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	//获取底部虚拟键的高度
	public static  int getBottomStatusHeight(Context context){
		int totalHeight = getDpi(context);
		int contentHeight = getScreenHeight(context);
		return totalHeight  - contentHeight;
	}

	//获取dpi
	public static int getDpi(Context context){
		int dpi = 0;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		@SuppressWarnings("rawtypes")
        Class c;
		try {
			c = Class.forName("android.view.Display");
			Method method = c.getMethod("getRealMetrics",DisplayMetrics.class);
			method.invoke(display, displayMetrics);
			dpi=displayMetrics.heightPixels;
		}catch(Exception e){
			e.printStackTrace();
		}
		return dpi;
	}


	//测量某个view的大小
	public static int measureView(int measureSpec){
		int result = 0;
		//分别获取测量模式 和 测量大小
		int specMode = View.MeasureSpec.getMode(measureSpec);
		int specSize = View.MeasureSpec.getSize(measureSpec);
		//如果是精确度模式，呢就按xml中定义的来
		if(specMode == View.MeasureSpec.EXACTLY){
			result = specSize;
		}
		//如果是最大值模式，就按我们定义的来
		else if(specMode == View.MeasureSpec.AT_MOST){
			result = 200;
		}
		return result;
	}

	//测量某个view的宽度
	public static int viewWidthMeasure(View view){
		int width = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(width, height);
		view.getMeasuredWidth(); // 获取宽度
		view.getMeasuredHeight(); // 获取高度
		return width;
	}

	//测量某个view的高度
	public static int viewHeightMeasure(View view){
		int height = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(0, height);
		view.getMeasuredWidth(); // 获取宽度
		return height;
	}


	//设置某个view的layoutparam
	public static void setViewLayoutParam(View view, int width, int height){
		if (view.getLayoutParams() instanceof ViewGroup.LayoutParams){
			ViewGroup.LayoutParams params = view.getLayoutParams();
			if (width >= 0)
				params.width = width;
			if (height >= 0)
				params.height = height;
			//view.setLayoutParams(params);
		}
	}


	//设置状态栏颜色
	public static void setWindowStatusBarColor(Activity activity, int color) {
		try {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				Window window = activity.getWindow();
				window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
				window.setStatusBarColor(color);

				//底部导航栏
				//window.setNavigationBarColor(activity.getResources().getColor(colorResId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 把密度转换为像素
	 */
	public static int dip2px(Context context, float px) {
		final float scale = getScreenDensity(context);
		return (int) (px * scale + 0.5);
	}

	public int getScreenWidth() {
		return mCtx.getResources().getDisplayMetrics().widthPixels;
	}

	public int dip2px(int dip) {
		float density = getDensity(mCtx);
		return (int) (dip * density + 0.5);
	}

	public int px2dip(int px) {
		float density = getDensity(mCtx);
		return (int) ((px - 0.5) / density);
	}

	public float getDensity(Context ctx) {
		return ctx.getResources().getDisplayMetrics().density;
	}

	/**
	 * ５40 的分辨率上是85 （
	 *
	 * @return
	 */
	public int getScal() {
		return (int) (getScreenWidth() * 100 / 480);
	}

	/**
	 * 宽全屏, 根据当前分辨率　动态获取高度
	 * height 在８００*４８０情况下　的高度
	 *
	 * @return
	 */
	public int get480Height(int height480) {
		int width = getScreenWidth();
		return (height480 * width / 480);
	}

	/**
	 * 获取状态栏高度
	 *
	 * @return
	 */
	public int getStatusBarHeight() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = mCtx.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return sbar;
	}

	public int getScreenHeight() {
		return mCtx.getResources().getDisplayMetrics().heightPixels;
	}

}
