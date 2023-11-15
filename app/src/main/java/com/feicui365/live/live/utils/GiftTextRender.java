package com.feicui365.live.live.utils;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;

import com.feicui365.live.base.MyApp;


public class GiftTextRender {

    private static ForegroundColorSpan sColorSpan;

    static {
        sColorSpan = new ForegroundColorSpan(0xffffdd00);
    }



    public static SpannableStringBuilder renderGiftInfo2(String giftName) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String s1 = "送";
        String content = s1 + " " + giftName;
        int index1 = s1.length();
        builder.append(content);
        builder.setSpan(sColorSpan, index1, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }



    public static SpannableStringBuilder renderGiftCount(int count) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String s = String.valueOf(count);
        builder.append(s);
        for (int i = 0, length = s.length(); i < length; i++) {
            String c = String.valueOf(s.charAt(i));
            if (StringUtil.isInt(c)) {
                int icon = CommonIconUtil.getGiftCountIcon(Integer.parseInt(c));
                Drawable drawable = MyApp.getInstance().getDrawable(icon);
                if (drawable != null) {
                    drawable.setBounds(0, 0, ArmsUtils.dip2px(MyApp.getInstance(),24), ArmsUtils.dip2px(MyApp.getInstance(),32));
                    builder.setSpan(new ImageSpan(drawable), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }

    public static SpannableStringBuilder renderGiftCount2(int count) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String s = String.valueOf(count);
        builder.append(s);
        for (int i = 0, length = s.length(); i < length; i++) {
            String c = String.valueOf(s.charAt(i));
            if (StringUtil.isInt(c)) {
                int icon = CommonIconUtil.getGiftCountIcon(Integer.parseInt(c));
                Drawable drawable = MyApp.getInstance().getDrawable( icon);
                if (drawable != null) {
                    drawable.setBounds(0, 0, ArmsUtils.dip2px(MyApp.getInstance(),18), ArmsUtils.dip2px(MyApp.getInstance(),24));
                    builder.setSpan(new ImageSpan(drawable), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return builder;
    }


}
