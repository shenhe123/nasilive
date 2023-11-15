package com.feicui365.live.util;

import com.feicui365.live.base.MyApp;



public class DpUtil {

    private static float scale;

    static {
        scale = MyApp.getInstance().getResources().getDisplayMetrics().density;
    }

    public static int dp2px(int dpVal) {
        return (int) (scale * dpVal + 0.5f);
    }

    public static int sp2px( float spValue) {

        return (int) (spValue * scale + 0.5f);
    }

}
