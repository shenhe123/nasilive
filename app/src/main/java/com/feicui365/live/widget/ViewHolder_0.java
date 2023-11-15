package com.feicui365.live.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.feicui365.nasinet.interfaces.LifeCycleListener;
import com.feicui365.nasinet.utils.ClickUtil;


public abstract class ViewHolder_0 {

    protected Context mContext;
    protected ViewGroup mParentView;
    protected View mContentView;
    protected LifeCycleListener mLifeCycleListener;
    protected Object object, object2;


    public ViewHolder_0(Context context, ViewGroup parentView) {

        mContext = context;
        mParentView = parentView;

        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), mParentView, false);

        doBeforeInit();

        init();
    }

    protected abstract void doBeforeInit();


    public ViewHolder_0(Context context, ViewGroup parentView, Object object) {
        mContext = context;
        mParentView = parentView;
        this.object = object;
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), mParentView, false);

        doBeforeInit();

        init();
    }

    public ViewHolder_0(Context context, ViewGroup parentView, Object object, Object object2) {
        mContext = context;
        mParentView = parentView;
        this.object = object;
        this.object2 = object2;

        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), mParentView, false);

        doBeforeInit();


        init();
    }

    public ViewHolder_0(Context context, ViewGroup parentView, Object... args) {
        processArguments(args);
        mContext = context;
        mParentView = parentView;
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), mParentView, false);


        doBeforeInit();

        init();
    }

    protected void processArguments(Object... args) {

    }

    protected abstract int getLayoutId();

    /**
     * 初始化holder
     */
    public abstract void init();

    protected View findViewById(int res) {
        return mContentView.findViewById(res);
    }

    public View getContentView() {
        return mContentView;
    }

    protected boolean canClick() {
        return ClickUtil.canClick();
    }

    public LifeCycleListener getLifeCycleListener() {
        return mLifeCycleListener;
    }

    public void addToParent() {
        if (mParentView != null && mContentView != null) {
            mParentView.addView(mContentView);
        }
    }

    public void removeFromParent() {
        ViewParent parent = mContentView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mContentView);
        }
    }

}
