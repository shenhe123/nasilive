package com.feicui365.live.ui.act;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.im.MyTUIConversationFragment;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.util.ToastUtils;

import com.tencent.qcloud.tuicore.TUILogin;


import java.util.ArrayList;

import butterknife.BindView;

public class AllMessageActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {

    @BindView(R.id.fl_fragment)
    FrameLayout fl_fragment;

    private ArrayList<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mPresenter=new HomePresenter();
        mPresenter.attachView(this);
        setTitle("消息中心");
        initList();
    }

    private void initList() {


        if(TUILogin.isUserLogined()){
            mFragments = new ArrayList<>();
            mFragments.add(new MyTUIConversationFragment());
            addFragment(mFragments.get(0));
        }else{
            ToastUtils.showT("您尚未登录IM");

        }

    }

    @Override
    public void onError(Throwable throwable) {

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
            getSupportFragmentManager().beginTransaction()
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
        getSupportFragmentManager().beginTransaction()

                .show(fragment)
                .commitAllowingStateLoss();
    }

    /**
     * 注释
     * 隐藏页面
     */
    public void hideElseFragment(Fragment fragment) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
