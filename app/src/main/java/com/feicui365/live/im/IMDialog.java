package com.feicui365.live.im;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.tencent.qcloud.tuicore.TUILogin;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment2;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.util.ToastUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 */
public class IMDialog extends BaseDialogFragment2 {
    @BindView(R.id.fl_fragment)
    FrameLayout fl_fragment;

    private ArrayList<Fragment> mFragments;
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_im_list;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.transparentBackgroundDiaolg;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(TUILogin.isUserLogined()){
            mFragments = new ArrayList<>();
            mFragments.add(new MyTUIConversationFragment());
            addFragment(mFragments.get(0));
        }else{
            ToastUtils.showT("您尚未登录IM");

        }
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        params.height = ArmsUtils.dip2px(getContext(), 500);


        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }


    /**
     * 注释
     * 显示添加fragment
     */

    public void addFragment(Fragment tabFragment) {
        if (tabFragment == null) {
            return;
        }
        if (tabFragment.isAdded()) {
            showFragment(tabFragment);
        } else {
            hideElseFragment(tabFragment);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, tabFragment)
                    .commitAllowingStateLoss();
        }
    }

    /**
     * 注释
     * 显示页面
     */
    public void showFragment(Fragment fragment) {
        hideElseFragment(fragment);
        getChildFragmentManager().beginTransaction()

                .show(fragment)
                .commitAllowingStateLoss();
    }

    /**
     * 注释
     * 隐藏页面
     */
    public void hideElseFragment(Fragment fragment) {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment tabFragmen = mFragments.get(i);
            if (fragment == tabFragmen) {
                continue;
            }
            if (fragment != null && tabFragmen.isAdded()) {
                transaction.hide(tabFragmen);
            }
        }
        transaction.commitAllowingStateLoss();
    }
}
