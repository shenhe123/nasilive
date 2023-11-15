package com.feicui365.live.live.weight;

import android.content.Context;

import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

/**
 *
 * TAB 标签下划线工具
 */
public class CommonNavigatorUtils {


    public static void initTitleView(SimplePagerTitleView simplePagerTitleView,
                                     Context context,
                                     String title,
                                     int normal_color,
                                     int select_color,

                                     int text_size){
        simplePagerTitleView.setNormalColor(context.getResources().getColor(normal_color));
        simplePagerTitleView.setSelectedColor(context.getResources().getColor(select_color));
        simplePagerTitleView.setTextSize(text_size);
        simplePagerTitleView.setText(title);

    }

    public static LinePagerIndicator  initLine(
                                     Context context,

                                     int width,
                                     int radius,
                                     int color){
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineWidth(UIUtil.dip2px(context, width));
        indicator.setRoundRadius(UIUtil.dip2px(context, radius));
        indicator.setColors(context.getResources().getColor(color));
        return indicator;

    }








}
