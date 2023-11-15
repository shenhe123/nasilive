package com.feicui365.live.live.utils;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;


/**
 *
 * Recycleview layout 工具
 */
public class AdapterLayoutUtils {

    public static GridLayoutManager getGrid(Context context, int count, int ori){

        GridLayoutManager layoutManager = new GridLayoutManager(context, count, ori, false);
        return layoutManager;

    }
    public static GridLayoutManager getGrid(Context context,int count){

        GridLayoutManager layoutManager = new GridLayoutManager(context, count);
        return layoutManager;

    }

    public static GridLayoutManager getGridRe(Context context,int count){

        GridLayoutManager layoutManager = new GridLayoutManager(context, count);

        return layoutManager;

    }

    public static LinearLayoutManager getLin(Context context){

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        return layoutManager;

    }

    public static LinearLayoutManager getLinHor(Context context){

        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false);
        return layoutManager;

    }
    public static LinearLayoutManager getLinHor2(Context context){

        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setStackFromEnd(true);
        return layoutManager;

    }

    public static LinearLayoutManager getLinHORIZONTAL(Context context){

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        return layoutManager;

    }

    public static StaggeredGridLayoutManager getStaggeredGridLayout(int count, int ori){

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(count, ori);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        return layoutManager;

    }






}
