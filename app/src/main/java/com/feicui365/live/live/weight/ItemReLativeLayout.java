package com.feicui365.live.live.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class ItemReLativeLayout extends RelativeLayout {

    public ItemReLativeLayout(Context context, AttributeSet attrs,
                              int defStyle) {
        super(context, attrs, defStyle);
    }

    public ItemReLativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ItemReLativeLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        // 高度和宽度一样
        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(
                childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
