package com.tencent.qcloud.tuicore.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.tencent.qcloud.tuicore.R;

public class RoundCornerImageView extends AppCompatImageView {
    private final Path path = new Path();
    private final RectF rectF = new RectF();
    private final PaintFlagsDrawFilter aliasFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
    private int radius;
    private int leftTopRadius;
    private int rightTopRadius;
    private int rightBottomRadius;
    private int leftBottomRadius;

    public RoundCornerImageView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public RoundCornerImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RoundCornerImageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        setLayerType(View.LAYER_TYPE_HARDWARE, null);
        int defaultRadius = 0;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);
            radius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_corner_radius, defaultRadius);
            leftTopRadius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_left_top_corner_radius, defaultRadius);
            rightTopRadius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_right_top_corner_radius, defaultRadius);
            rightBottomRadius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_right_bottom_corner_radius, defaultRadius);
            leftBottomRadius = array.getDimensionPixelOffset(R.styleable.RoundCornerImageView_left_bottom_corner_radius, defaultRadius);
            array.recycle();
        }

        if (defaultRadius == leftTopRadius) {
            leftTopRadius = radius;
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = radius;
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = radius;
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = radius;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        path.reset();
        canvas.setDrawFilter(aliasFilter);
        rectF.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
        // left-top -> right-top -> right-bottom -> left-bottom
        float[] radius = {leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius,
                rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
        path.addRoundRect(rectF, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}
