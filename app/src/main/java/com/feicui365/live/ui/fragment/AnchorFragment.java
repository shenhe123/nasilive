package com.feicui365.live.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.SuperPlayerPresenter;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.widget.MyViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnchorFragment extends BaseMvpFragment<SuperPlayerPresenter> implements SuperPlayerContrat.View {

    @BindView(R.id.viewpager)
    MyViewPager viewpager;

    private List<Fragment> mFragments = new ArrayList<>();
    private String anchorid;
    private String roomid;
    PalyTabFragmentPagerAdapter adapter;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        if (getArguments() != null) {
            anchorid = (String) getArguments().getSerializable("anchor");
            roomid=getArguments().getString("roomid");
        }
        mPresenter = new SuperPlayerPresenter();
        mPresenter.attachView(this);
        mPresenter.getAnchorInfo(anchorid);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor;
    }


    @Override
    public void setAnchorInfo(BaseResponse<UserRegist> anchorInfo) {
        if (anchorInfo.isSuccess()) {
            UserRegist anchorInfo1 = anchorInfo.getData();

            UserTrendsFragment userTrendsFragment = new UserTrendsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", 15);
            bundle.putSerializable("authorInfo", anchorInfo1);
            bundle.putString("roomid", roomid);
            userTrendsFragment.setArguments(bundle);
            mFragments.add(userTrendsFragment);
            adapter = new PalyTabFragmentPagerAdapter(getChildFragmentManager(), mFragments);
            viewpager.setOffscreenPageLimit(mFragments.size());
            viewpager.setAdapter(adapter);
            viewpager.setCurrentItem(0, false);
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }



}
