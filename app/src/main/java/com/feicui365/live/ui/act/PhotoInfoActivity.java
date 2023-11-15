package com.feicui365.live.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.ui.fragment.PhotoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PhotoInfoActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View{

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.rl_back2)
    RelativeLayout rl_back2;

    private List<Fragment> mFragments = new ArrayList<>();
    private PalyTabFragmentPagerAdapter palyTabFragmentPagerAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_info;
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        hideOther(true);
        hideTitle(true);
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        Intent intent = getIntent();
        ArrayList<String> urls = (ArrayList<String>) intent.getSerializableExtra("data");
        int positions=intent.getIntExtra("positions",0);
        for (String url : urls){
            PhotoFragment photoFragment =  new PhotoFragment();
            Bundle bundle = new Bundle();
            bundle.putString("url",url);
            photoFragment.setArguments(bundle);
            mFragments.add(photoFragment);
        }

        palyTabFragmentPagerAdapter = new PalyTabFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setOffscreenPageLimit(mFragments.size());
        mViewPager.setAdapter(palyTabFragmentPagerAdapter);
        mViewPager.setCurrentItem(positions);  //初始化显示第一个页面

        rl_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
}
