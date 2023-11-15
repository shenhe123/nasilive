package com.feicui365.live.util;

import android.content.res.Resources;

import com.feicui365.live.base.MyApp;




public class WordUtil {

    private static Resources sResources;

    static {
        sResources = MyApp.getInstance().getResources();
    }

    public static String getString(int res) {
        return sResources.getString(res);
    }
}
