package com.feicui365.live.live.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;


import android.support.annotation.NonNull;


import com.feicui365.live.R;


public class GiftFrameLayout extends FrameLayout {

    private float mRatio;
    private float mOffestY;

    public GiftFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public GiftFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GiftFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyFrameLayout2);
        mRatio = ta.getFloat(R.styleable.MyFrameLayout2_mfl_ratio, 1);
        mOffestY = ta.getDimension(R.styleable.MyFrameLayout2_mfl_offestY, 0);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (widthSize * mRatio + mOffestY), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
