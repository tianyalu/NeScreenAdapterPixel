package com.sty.ne.screen.adapter.pixel;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by tian on 2019/10/18.
 */

public class Utils {
    private static volatile Utils utils;

    //这里是设计稿参考宽高
//    private static final float STANDARD_WIDTH = 720;
//    private static final float STANDARD_HEIGHT = 1280;
    private static final float STANDARD_WIDTH = 1080;
    private static final float STANDARD_HEIGHT = 1920;

    //这里是屏幕先上宽高
    private int mDisplayWidth;
    private int mDisplayHeight;

    private Utils(Context context) {
        //获取屏幕的宽高
        if(mDisplayWidth == 0 || mDisplayHeight == 0) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if(manager != null) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                manager.getDefaultDisplay().getMetrics(displayMetrics);
                if(displayMetrics.widthPixels > displayMetrics.heightPixels) {
                    //横屏
                    mDisplayWidth = displayMetrics.heightPixels;
                    mDisplayHeight = displayMetrics.widthPixels;
                }else { //竖屏
                    mDisplayWidth = displayMetrics.widthPixels;
                    mDisplayHeight = displayMetrics.heightPixels - getStatusBarHeight(context);
                }
            }
        }

    }

    public int getStatusBarHeight(Context context) {
        int resID = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resID > 0) {
            return context.getResources().getDimensionPixelSize(resID);
        }
        return 0;
    }

    public static Utils getInstance(Context context) {
        if(utils == null) {
            synchronized (Utils.class) {
                if(utils == null) {
                    utils = new Utils(context.getApplicationContext());
                }
            }
        }
        return utils;
    }

    //获取水平方向上的缩放比例
    public float getHorizontalScale() {
        return mDisplayWidth / STANDARD_WIDTH;
    }

    //获取竖直方向上的缩放比例
    public float getVerticalScale() {
        return mDisplayHeight / STANDARD_HEIGHT;
    }
}
