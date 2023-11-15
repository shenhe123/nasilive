package com.feicui365.live.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ImageSpan;

public class MyImageSpan3 extends ImageSpan {

    public MyImageSpan3(Drawable drawable) {
        super(drawable);

    }


    public MyImageSpan3(Bitmap b) {
        super(b);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {

        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        x = 5 + x;
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;//计算y方向的位移
        canvas.save();
        canvas.translate(x, transY);//绘制图片位移一段距离
        b.draw(canvas);
        canvas.restore();
    }

    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable
            Paint.FontMetricsInt fm) {
//        Drawable d = getCachedDrawable();
//        Rect rect = d.getBounds();
//
//        if (fm != null) {
//            fm.ascent = -rect.bottom;
//            fm.descent = 0;
//
//            fm.top = fm.ascent;
//            fm.bottom = 0;
//        }
//
//        return mMarginLeft + rect.right + mMarginRight;
        return 5 + super.getSize(paint, text, start, end, fm) + 5;
    }
}