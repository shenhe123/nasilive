package com.feicui365.live.live.weight;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.feicui365.live.live.utils.ArmsUtils;


/**
 *  注释
 * Recycleview列表间隔
 */
public class SpacingItemDecoration extends RecyclerView.ItemDecoration {


    int top,bottom,left,right;
    Context context;
    boolean is_setting;

    public SpacingItemDecoration(Context context) {
       this.context=context;
    }

    public SpacingItemDecoration(Context context, int top, int bottom, int left, int right) {
        this.context=context;
        this.top=top;
        this.bottom=bottom;
        this.left=left;
        this.right=right;
        is_setting=true;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if(is_setting){
            outRect.set(ArmsUtils.dip2px(context,left),
                    ArmsUtils.dip2px(context,top),
                    ArmsUtils.dip2px(context,right),
                    ArmsUtils.dip2px(context,bottom));
        }else{
            outRect.set(0,
                    ArmsUtils.dip2px(context,5),
                    0,
                    ArmsUtils.dip2px(context,5));
        }

    }
}
