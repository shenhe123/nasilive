package com.feicui365.live.live.weight;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 *
 */
public class MyViewpager extends ViewPager {

    boolean isCanSlide = true;

    public MyViewpager(@NonNull Context context) {
        super(context);
    }

    public MyViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!isCanSlide){//设置为false时，viewpager不能横向滑动
            requestDisallowInterceptTouchEvent(true);//使viewpager不再通过onInterceptTouchEvent捕获触摸事件
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setCanSlide(boolean canSlide) {
        isCanSlide = canSlide;
    }

    public boolean isCanSlide() {
        return isCanSlide;
    }


}
