package com.feicui365.live.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {


    private int type; //列数
    private int spacing; //间隔
    private boolean includeEdge; //是否包含边缘

    public SpacingItemDecoration() {

    }
    public SpacingItemDecoration(int spacing) {
        this.spacing = spacing;
    }

    public SpacingItemDecoration(int spacing, int type) {
        this.type = type;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        switch (type){
            case 0:
                if(spacing==0){
                    outRect.set(5, 5, 5, 5);
                }else{
                    outRect.set(0, spacing/2, spacing, spacing/2);
                }
                break;
            case 1:

                outRect.set(DpUtil.dp2px(5), 0, DpUtil.dp2px(5), DpUtil.dp2px(12));
                break;
            case 2:

                outRect.set(0,0,0, DpUtil.dp2px(30));
                break;
            case 3:

                outRect.set(0,30,0, 0);
                break;
            case 4:

                outRect.set(DpUtil.dp2px(5),DpUtil.dp2px(5),DpUtil.dp2px(5), DpUtil.dp2px(5));
                break;
        }

    }
}
