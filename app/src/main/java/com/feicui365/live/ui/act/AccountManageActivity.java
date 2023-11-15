package com.feicui365.live.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.androidkun.xtablayout.XTabLayout;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.live.adapter.SwiftMessageAdapter;
import com.feicui365.live.model.entity.AccountDetailBean;
import com.feicui365.live.model.entity.SwiftMessageBean;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.fragment.DetailFragment;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AccountManageActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View, View.OnClickListener {
    @BindView(R.id.account_tv)
    TextView mAccountTv;
    @BindView(R.id.account_tb)
    XTabLayout mAccountTb;
    @BindView(R.id.account_vp)
    ViewPager mAccountVp;

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> title =new ArrayList<>();
    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_manage;
    }

    @Override
    protected void initData() {

    }



    @Override
    protected void initView() {
        mAccountTv.setText(MyUserInstance.getInstance().getUserinfo().getBalance());
        setTitle("账户管理");
        mAccountTb.setTabMode(TabLayout.MODE_FIXED);
        title.add("充值明细");
        title.add("支出明细");
        for (int i = 0; i < title.size(); i++) {
            DetailFragment orderFragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("detail_type",i+1);
            orderFragment.setArguments(bundle);//数据传递到fragment中
            fragments.add(orderFragment);

        }
        for (int i = 0; i < title.size(); i++) {
            XTabLayout.Tab tab = mAccountTb.newTab();
            tab.setTag(title.get(i));
            mAccountTb.addTab(tab);
        }


        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());
        mAccountVp.setAdapter(tabAdapter);
        mAccountTb.setupWithViewPager(mAccountVp);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    class TabAdapter extends FragmentPagerAdapter {

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }
    }
}
