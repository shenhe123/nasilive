package com.feicui365.live.widget;

import android.content.Context;
import android.view.ViewGroup;

import com.feicui365.live.contract.MainAppBarLayoutListener;
import com.feicui365.nasinet.interfaces.LifeCycleListener;


import java.util.List;

public abstract class ViewHolder_1 extends ViewHolder_0 {

    protected boolean mFirstLoadData = true;
    //是否切换到了当前页面
    protected boolean mShowed;

    public ViewHolder_1(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    public ViewHolder_1(Context context, ViewGroup parentView, Object object) {
        super(context, parentView, object);
    }

    public ViewHolder_1(Context context, ViewGroup parentView, Object object, Object object2) {
        super(context, parentView, object, object2);
    }

    public void setAppBarLayoutListener(MainAppBarLayoutListener appBarLayoutListener) {
    }


    public List<LifeCycleListener> getLifeCycleListenerList() {
        return null;
    }

    //onresume调用
    public void loadData() {
    }

    protected boolean isFirstLoadData() {
        if (mFirstLoadData) {
            mFirstLoadData = false;
            return true;
        }
        return false;
    }

    public void setShowed(boolean showed) {
        mShowed = showed;
    }
}
