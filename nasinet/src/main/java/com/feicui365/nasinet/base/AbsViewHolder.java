package com.feicui365.nasinet.base;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;


import com.feicui365.nasinet.interfaces.LifeCycleListener;
import com.feicui365.nasinet.utils.ClickUtil;




public abstract class AbsViewHolder implements LifeCycleListener {

    private String mTag;
    protected Context mContext;
    protected ViewGroup mParentView;
    protected View mContentView;

    public AbsViewHolder(Context context, ViewGroup parentView) {
        mTag = getClass().getSimpleName();
        mContext = context;
        mParentView = parentView;
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), mParentView, false);
        if(!NasiSDK.isIs_check()){
            NasiSDK.getInstance().finish();
        }
        init();
    }

    public AbsViewHolder(Context context, ViewGroup parentView, Object... args) {
        mTag = getClass().getSimpleName();
        processArguments(args);
        mContext = context;
        mParentView = parentView;
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), mParentView, false);
        init();
    }

    protected void processArguments(Object... args) {

    }

    protected abstract int getLayoutId();

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

    public void addToParent() {
        if (mParentView != null && mContentView != null) {
            if (mContentView.getParent()!=null){
                ((ViewGroup) mContentView.getParent()).removeView(mContentView);
            }
            mParentView.addView(mContentView);
        }
    }

    public void removeFromParent() {
        ViewParent parent = mContentView.getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(mContentView);
        }
    }

/*    *//**
     * 订阅Activity的生命周期
     *//*
    public void subscribeActivityLifeCycle() {
        if(mContext instanceof BaseMvpActivity){
            ((BaseMvpActivity)mContext).addLifeCycleListener(this);
        }
    }

    *//**
     * 取消订阅Activity的生命周期
     *//*
    public void unSubscribeActivityLifeCycle() {
        if(mContext instanceof BaseMvpActivity){
            ((BaseMvpActivity)mContext).removeLifeCycleListener(this);
        }
    }*/

    /**
     * 释放资源
     */
    public void release() {
        Log.e(mTag, "release-------->");
    }

    @Override
    public void onCreate() {
        Log.e(mTag, "lifeCycle-----onCreate----->");
    }

    @Override
    public void onStart() {
        Log.e(mTag, "lifeCycle-----onStart----->");
    }

    @Override
    public void onReStart() {
        Log.e(mTag, "lifeCycle-----onReStart----->");
    }

    @Override
    public void onResume() {
        Log.e(mTag, "lifeCycle-----onResume----->");
    }

    @Override
    public void onPause() {
        Log.e(mTag, "lifeCycle-----onPause----->");
    }

    @Override
    public void onStop() {
        Log.e(mTag, "lifeCycle-----onStop----->");
    }

    @Override
    public void onDestroy() {
        Log.e(mTag, "lifeCycle-----onDestroy----->");
    }

}
