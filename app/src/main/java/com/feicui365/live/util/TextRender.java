package com.feicui365.live.util;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.util.Log;


import com.feicui365.live.base.MyApp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class TextRender {

    private static final int FACE_WIDTH;
    private static final int VIDEO_FACE_WIDTH;
    private static final String REGEX = "\\[([\u4e00-\u9fa5\\w])+\\]";
    private static final Pattern PATTERN;
    private static StyleSpan sBoldSpan;
    private static StyleSpan sNormalSpan;
    private static ForegroundColorSpan sWhiteColorSpan;
    private static ForegroundColorSpan sGlobalColorSpan;
    private static ForegroundColorSpan sColorSpan1;
    private static AbsoluteSizeSpan sFontSizeSpan;
    private static AbsoluteSizeSpan sFontSizeSpan2;
    private static AbsoluteSizeSpan sFontSizeSpan3;
    private static AbsoluteSizeSpan sFontSizeSpan4;

    static {
        sBoldSpan = new StyleSpan(Typeface.BOLD);
        sNormalSpan = new StyleSpan(Typeface.NORMAL);
        sWhiteColorSpan = new ForegroundColorSpan(0xffffffff);
        sGlobalColorSpan = new ForegroundColorSpan(0xffffdd00);
        sColorSpan1 = new ForegroundColorSpan(0xffc8c8c8);
        sFontSizeSpan = new AbsoluteSizeSpan(17, true);
        sFontSizeSpan2 = new AbsoluteSizeSpan(13, true);
        sFontSizeSpan3 = new AbsoluteSizeSpan(14, true);
        sFontSizeSpan4 = new AbsoluteSizeSpan(12, true);
        FACE_WIDTH = DpUtil.dp2px(20);
        VIDEO_FACE_WIDTH = DpUtil.dp2px(16);
        PATTERN = Pattern.compile(REGEX);
    }



    /**
     * 聊天表情
     */
    public static CharSequence getFaceImageSpan(String content, int imgRes) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        Drawable faceDrawable = ContextCompat.getDrawable(MyApp.getInstance(), imgRes);
        faceDrawable.setBounds(0, 0, FACE_WIDTH, FACE_WIDTH);
        ImageSpan imageSpan = new ImageSpan(faceDrawable, ImageSpan.ALIGN_BOTTOM);
        builder.setSpan(imageSpan, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    /**
     * 聊天内容，解析里面的表情
     */
    public static CharSequence renderChatMessage(String content) {
        Matcher matcher = PATTERN.matcher(content);
        boolean hasFace = false;
        SpannableStringBuilder builder = null;
        while (matcher.find()) {
            // 获取匹配到的具体字符
            String key = matcher.group();
            Integer imgRes = FaceUtil.getFaceImageRes(key);
            if (imgRes != null && imgRes != 0) {
                hasFace = true;
                if (builder == null) {
                    builder = new SpannableStringBuilder(content);
                }
                Drawable faceDrawable = ContextCompat.getDrawable(MyApp.getInstance(), imgRes);
                if (faceDrawable != null) {
                    faceDrawable.setBounds(0, 0, FACE_WIDTH, FACE_WIDTH);
                    ImageSpan imageSpan = new ImageSpan(faceDrawable, ImageSpan.ALIGN_BOTTOM);
                    // 匹配字符串的开始位置
                    int startIndex = matcher.start();
                    builder.setSpan(imageSpan, startIndex, startIndex + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        if (hasFace) {
            return builder;
        } else {
            return content;
        }
    }

    /**
     * 聊天内容，解析里面的表情
     */
    public static CharSequence renderChatMessage2(CharSequence content) {
        Matcher matcher = PATTERN.matcher(content);
        boolean hasFace = false;
        SpannableStringBuilder builder = null;
        while (matcher.find()) {
            // 获取匹配到的具体字符
            String key = matcher.group();
            Integer imgRes = FaceUtil.getFaceImageRes(key);
            if (imgRes != null && imgRes != 0) {
                hasFace = true;
                if (builder == null) {
                    builder = new SpannableStringBuilder(content);
                }
                Drawable faceDrawable = ContextCompat.getDrawable(MyApp.getInstance(), imgRes);
                if (faceDrawable != null) {
                    faceDrawable.setBounds(0, 0, FACE_WIDTH, FACE_WIDTH);
                    ImageSpan imageSpan = new ImageSpan(faceDrawable, ImageSpan.ALIGN_BOTTOM);
                    // 匹配字符串的开始位置
                    int startIndex = matcher.start();
                    builder.setSpan(imageSpan, startIndex, startIndex + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        if (hasFace) {
            return builder;
        } else {
            return content;
        }
    }


    /**
     * 评论内容，解析里面的表情
     */
    public static CharSequence renderVideoComment(String content, String addTime) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        Matcher matcher = PATTERN.matcher(content);
        while (matcher.find()) {
            // 获取匹配到的具体字符
            String key = matcher.group();
            Integer imgRes = FaceUtil.getFaceImageRes(key);
            if (imgRes != null && imgRes != 0) {
                Drawable faceDrawable = ContextCompat.getDrawable(MyApp.getInstance(), imgRes);
                if (faceDrawable != null) {
                    faceDrawable.setBounds(0, 0, VIDEO_FACE_WIDTH, VIDEO_FACE_WIDTH);
                    ImageSpan imageSpan = new ImageSpan(faceDrawable, ImageSpan.ALIGN_BOTTOM);
                    // 匹配字符串的开始位置
                    int startIndex = matcher.start();
                    builder.setSpan(imageSpan, startIndex, startIndex + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        int startIndex = builder.length();
        builder.append(addTime);
        int endIndex = startIndex + addTime.length();
        builder.setSpan(sColorSpan1, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(sFontSizeSpan4, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static CharSequence repOther(String rep_user,String content){

        String temp="回复@"+rep_user+":";
        SpannableStringBuilder spannableString = new SpannableStringBuilder(temp);
        ForegroundColorSpan foregroundColorSpan=new ForegroundColorSpan(Color.parseColor("#459CEC"));
        spannableString.setSpan(foregroundColorSpan,0,temp.length(),Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.append(content);
        return spannableString;
    }

}
