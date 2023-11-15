package com.feicui365.live.live.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.feicui365.live.util.ComputerUtils;


/**
 *
 */
public class PkScreenUtils {


    public static int widthPx;
    public static int pkVideoHeight;//PK视频播放高度

    public static int pkInfoUtilsHeight;//PK信息框高度
    // public static int pkInfoUtilsMarginTop;//PK信息框距离顶部高度
    public static int pkFrameHeight;//PK VIP框架高度
    // public static int pkBackRoundHeight;//PK 背景图片高度
    public static int pkTimeHeight;//PK 时间距离顶部高度
    public static int pkScoruseHeight;//PK 比分距离顶部高度

    public static double defaultValue1 = 1.125;//获取视频播放高度宽高比
    public static double defaultValue2 = 1.03;//获取PK显示信息框架宽高比
    public static double defaultValue3 = 1.171;//PK框架宽高比
    public static double defaultValue4 = 1.259;//PK 比分距离顶部高度
    public static double defaultValue5 = 1.395;//PK 比分距离顶部高度


    public static void getAndroiodScreenProperty(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        widthPx = dm.widthPixels;         // 屏幕宽度（像素）


        initPkVideoHeight();
    }


    private static void initPkVideoHeight() {
        //获取默认视频高
        pkVideoHeight = ComputerUtils.div(widthPx, defaultValue1);
        //PK信息框高度
        pkInfoUtilsHeight = ComputerUtils.div(pkVideoHeight, defaultValue2);
        //PK信息框距离顶部高度
        pkFrameHeight = ComputerUtils.div(pkInfoUtilsHeight, defaultValue3);
        //PK VIP框架高度
        pkTimeHeight = ComputerUtils.div(pkInfoUtilsHeight, defaultValue4);
        //PK 背景图片高度
        pkScoruseHeight = ComputerUtils.div(pkInfoUtilsHeight, defaultValue5);



    }

}
