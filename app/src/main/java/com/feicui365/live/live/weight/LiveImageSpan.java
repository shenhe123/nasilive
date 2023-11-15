package com.feicui365.live.live.weight;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.style.ImageSpan;


/**
 * 注释
 * 文字 图片 混编工具
 */
public class LiveImageSpan extends ImageSpan {

    public LiveImageSpan(Drawable drawable) {
        super(drawable);

    }

    public LiveImageSpan(Bitmap b) {
        super(b);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,
                     @NonNull Paint paint) {

        Drawable b = getDrawable();
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2 - b.getBounds().bottom / 2;//计算y方向的位移
        canvas.save();
        canvas.translate(x, transY);//绘制图片位移一段距离
        b.draw(canvas);
        canvas.restore();
    }
    @Override
    public int getSize(@NonNull Paint paint, CharSequence text, int start, int end, @Nullable
            Paint.FontMetricsInt fm) {

        return 4 + super.getSize(paint, text, start, end, fm) + 4;
    }



}