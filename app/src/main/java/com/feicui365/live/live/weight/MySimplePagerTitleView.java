package com.feicui365.live.live.weight;

import android.content.Context;
import android.graphics.Typeface;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 *
 */
public class MySimplePagerTitleView extends SimplePagerTitleView {

    public MySimplePagerTitleView(Context context) {
        super(context);
    }


    @Override
    public void onSelected(int index, int totalCount) {
        super.onSelected(index, totalCount);
        setTextSize(16);
        setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        super.onDeselected(index, totalCount);
        setTextSize(14);
        setTypeface(Typeface.DEFAULT);
    }
}
