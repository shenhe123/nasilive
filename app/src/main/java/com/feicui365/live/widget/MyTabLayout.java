package com.feicui365.live.widget;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;


import com.feicui365.live.R;


import java.lang.reflect.Field;

public class MyTabLayout extends TabLayout {
    // 一屏显示多少个tab
    private static final int TabViewNumber = 8;
    // support 低版本可能不一样
    private static final String SCROLLABLE_TAB_MIN_WIDTH = "scrollableTabMinWidth";

    public MyTabLayout(Context context) {
        super(context);
        initTabMinWidth();
    }

    public MyTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initTabMinWidth();
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initTabMinWidth();
    }


    private void initTabMinWidth() {
//        int screenWidth = getResources().getDisplayMetrics().widthPixels;
//        int tabMinWidth = screenWidth / TabViewNumber;
        // scrollable模式时，tab的最小宽度
        int tabMinWidth = (int) getResources().getDimension(R.dimen.dp_23);

        Field field;
        try {
            field = TabLayout.class.getDeclaredField(SCROLLABLE_TAB_MIN_WIDTH);
            field.setAccessible(true);
            field.set(this, tabMinWidth);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }



}
