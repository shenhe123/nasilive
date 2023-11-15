package com.feicui365.live.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import android.support.v4.app.Fragment;


import com.dueeeke.videoplayer.player.VideoViewManager;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.ui.act.PublishTrendActivity;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.model.entity.LiveCategory;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.TabFragmentAdapter;
import com.feicui365.live.util.WordUtil;

import com.feicui365.live.widget.ScaleTransitionPagerTitleView;
import com.feicui365.nasinet.utils.AppManager;



import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeTrendsFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View, View.OnClickListener {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.iv_publish)
    ImageView iv_publish;
    @BindView(R.id.magic_indicator)
    MagicIndicator magic_indicator;
    public boolean action = false;


    private ArrayList<LiveCategory> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private TabFragmentAdapter mTabFragmentAdapter;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        LiveCategory liveCategory = new LiveCategory();
        liveCategory.setTitle(WordUtil.getString(R.string.Follow));
        mTitles.add(liveCategory);
        LiveCategory liveCategory2 = new LiveCategory();
        liveCategory2.setTitle(WordUtil.getString(R.string.Hot));
        mTitles.add(liveCategory2);


        UserTrendsFragment attentTrends = new UserTrendsFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 0);
        attentTrends.setArguments(bundle1);
        mFragments.add(attentTrends);

        UserTrendsFragment hotTrends = new UserTrendsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        hotTrends.setArguments(bundle);
        mFragments.add(hotTrends);

        mTabFragmentAdapter = new TabFragmentAdapter(mFragments, mTitles, getChildFragmentManager(), getActivity());

        mViewPager.setOffscreenPageLimit(mFragments.size());// 设置预加载Fragment个数
        mViewPager.setAdapter(mTabFragmentAdapter);


        initTab(view);
    }

    private void initTab(View view) {
        magic_indicator = view.findViewById(R.id.magic_indicator);
        CommonNavigator commonNavigator = new CommonNavigator(getContext());

        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);

                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.white));
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.white));
                colorTransitionPagerTitleView.setTextSize(20);
                colorTransitionPagerTitleView.setText(mTitles.get(index).getTitle());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);

                    }
                });

                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setColors(getResources().getColor(R.color.white));
                return indicator;
            }

        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator, mViewPager);
        mViewPager.setCurrentItem(1);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_trends;
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

    @OnClick({R.id.iv_publish})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_publish:
                if(MyUserInstance.getInstance().visitorIsLogin()) {
                   /* if(ArmsUtils.showInputCodeDiaolog(getActivity())){
                        return;
                    }*/
                    AppManager.getAppManager().startActivity(PublishTrendActivity.class);
                }
                break;

        }
    }



    public void paseVideo() {
        if (!action) {
            VideoViewManager.instance().pause();
        }

    }

}
