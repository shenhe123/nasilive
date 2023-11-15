package com.feicui365.live.live.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ReplacementSpan;
import android.util.Log;
import android.view.View;


import java.util.List;

public class TextColorSizeHelper {
    /**
     * 更改字体大小、颜色、增加点击事件
     *
     * @param context
     * @param text
     * @param spanInfos
     * @return
     */
    public static SpannableStringBuilder getTextSpan(Context context,
                                                     String text,
                                                     List<SpanInfo> spanInfos
    ) {
        if (context == null
                || TextUtils.isEmpty(text)
                || spanInfos == null || spanInfos.size() == 0) {
            return null;
        }
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        int begin = 0;
        int end = 0;
        for (int i = 0; i < spanInfos.size(); i++) {
            final SpanInfo spanBean = spanInfos.get(i);
            //
            if (spanBean != null && !TextUtils.isEmpty(spanBean.subText)) {
                // 从后边向前寻找
                if (spanBean.findFromEnd) {
                    begin = text.lastIndexOf(spanBean.subText);
                } else {
                    begin = text.indexOf(spanBean.subText, end);
                }
                end = begin + spanBean.subText.length();
                // 字号有变动
                if (spanBean.subTextSize > 0) {
                    style.setSpan(new AbsoluteSizeSpan(spanBean.subTextSize), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                // 字体颜色
                if (spanBean.subTextColor != 0) {
                    // 背景圆角
                    if (spanBean.subTextBgRadius > 0 && spanBean.subTextBgColor != 0) {
                        style.setSpan(new RadiusBackgroundSpan(
                                spanBean.subTextColor,
                                spanBean.subTextBgColor,
                                spanBean.subTextBgRadius
                        ), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    }
                    // 字体点击
                    if (spanBean.clickableSpan != null) {
                        style.setSpan(new ClickableSpan() {
                            @Override
                            public void onClick(@NonNull View widget) {
                                if (spanBean.clickableSpan != null) {
                                    spanBean.clickableSpan.onClick(widget);
                                }
                            }

                            @Override
                            public void updateDrawState(TextPaint ds) {
                                // set textColor
                                ds.setColor(spanBean.subTextColor);
                                // 下划线
                                ds.setUnderlineText(spanBean.clickSpanUnderline);
                            }
                        }, begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                    // 字体颜色
                    else {
                        style.setSpan(new ForegroundColorSpan(spanBean.subTextColor), begin, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
            }
        }
        return style;
    }

    public static class SpanInfo {

        // 文字
        private String subText;
        // 像素
        private int subTextSize;
        // Color.parseColor("#af5050");
        private int subTextColor;
        //
        // sub txt背景相关
        private int subTextBgColor;
        private int subTextBgRadius;
        //
        // 点击相关
        private ClickableSpan clickableSpan;
        // 是否展示下划线
        private boolean clickSpanUnderline;
        //
        // 从前向后寻找 or 从后向前寻找
        private boolean findFromEnd;

        // 不展示背景圆角 不可点击
        public SpanInfo(String subText, int subTextSize, int subTextColor,
                        boolean findFromEnd) {
            this(subText, subTextSize, subTextColor,
                    0, -1,
                    null, false, findFromEnd);
        }

        // 不展示背景圆角 可点击
        public SpanInfo(String subText, int subTextSize, int subTextColor,
                        ClickableSpan clickableSpan, boolean clickSpanUnderline,
                        boolean findFromEnd) {
            this(subText, subTextSize, subTextColor,
                    0, -1,
                    clickableSpan, clickSpanUnderline, findFromEnd);
        }

        // 展示背景圆角 不可点击
        public SpanInfo(String subText, int subTextSize, int subTextColor,
                        int subTextBgColor, int subTextBgRadius,
                        boolean findFromEnd) {
            this(subText, subTextSize, subTextColor,
                    subTextBgColor, subTextBgRadius,
                    null, false, findFromEnd);
        }

        // 展示背景圆角
        public SpanInfo(String subText, int subTextSize, int subTextColor,
                        int subTextBgColor, int subTextBgRadius,
                        ClickableSpan clickableSpan, boolean clickSpanUnderline,
                        boolean findFromEnd) {
            // sub txt
            this.subText = subText;
            this.subTextSize = subTextSize;
            this.subTextColor = subTextColor;
            // sub txt背景相关
            this.subTextBgColor = subTextBgColor;
            this.subTextBgRadius = subTextBgRadius;
            // 点击相关
            this.clickableSpan = clickableSpan;
            this.clickSpanUnderline = clickSpanUnderline;
            // 从前向后寻找 or 从后向前寻找
            this.findFromEnd = findFromEnd;
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("subText: ");
            sb.append(subText);
            sb.append(" subTextSize: ");
            sb.append(subTextSize);
            sb.append(" findFromEnd: ");
            sb.append(findFromEnd);
            return sb.toString();
        }
    }


    public static class RadiusBackgroundSpan extends ReplacementSpan {

        private int mSize;
        private int mTextColor;
        private int mRadiusBgColor;
        private int mRadius;

        /**
         * @param radiusBgColor 背景颜色
         * @param radius        圆角半径
         */
        public RadiusBackgroundSpan(int textColor, int radiusBgColor, int radius) {
            this.mTextColor = textColor;
            this.mRadiusBgColor = radiusBgColor;
            this.mRadius = radius;
        }

        @Override
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
            mSize = (int) (paint.measureText(text, start, end) + mRadius);
            return mSize;
        }

        @Override
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            int color = paint.getColor();//保存文字颜色
            paint.setColor(mRadiusBgColor);//设置背景颜色
            paint.setAntiAlias(true);// 设置画笔的锯齿效果
            Log.i("pyt", y + "");
            RectF oval = new RectF(x, y + paint.ascent(), x + mSize, y + paint.descent());
            //设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
            canvas.drawRoundRect(oval, mRadius, mRadius, paint);//绘制圆角矩形，第二个参数是x半径，第三个参数是y半径
            paint.setColor(mTextColor);//恢复画笔的文字颜色
            canvas.drawText(text, start, end, x + mRadius / 2, y, paint);//绘制文字
        }
    }
}
