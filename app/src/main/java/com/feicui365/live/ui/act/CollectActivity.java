package com.feicui365.live.ui.act;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dueeeke.videoplayer.player.VideoViewManager;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.ui.fragment.ShortVideoListFragment;
import com.feicui365.live.ui.fragment.UserTrendsFragment;
import com.feicui365.live.widget.ScaleTransitionPagerTitleView;

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
//我的收藏
public class CollectActivity extends OthrBase2Activity {

    private PalyTabFragmentPagerAdapter adapter;
    private ArrayList list= new ArrayList<>();;
    private MagicIndicator magicIndicator;
    private ArrayList<String> mTitles = new ArrayList<>();
    ViewPager myViewPager;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect_list;
    }

    @Override
    protected void initData() {
        mTitles.add("短视频");
        mTitles.add("动态");
        initFragment();
        myViewPager = findViewById(R.id.viewPager);
        magicIndicator=findViewById(R.id.magic_indicator);


        adapter = new PalyTabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setOffscreenPageLimit(list.size());
        myViewPager.setAdapter(adapter);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#000000"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                colorTransitionPagerTitleView.setTextSize(20);
                colorTransitionPagerTitleView.setText(mTitles.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myViewPager.setCurrentItem(index);

                    }
                });

                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(UIUtil.dip2px(context, 13));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setYOffset(UIUtil.dip2px(context, 3));
                indicator.setColors(getResources().getColor(R.color.color_theme));
                return indicator;
            }

        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, myViewPager);
        myViewPager.setCurrentItem(1);

        findViewById(R.id.rl_back2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initFragment() {
        ShortVideoListFragment collectShort = new ShortVideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 2);
        collectShort.setArguments(bundle);
        list.add(collectShort);

        UserTrendsFragment userTrendsFragment=new UserTrendsFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 17);
        userTrendsFragment.setArguments(bundle2);
        list.add(userTrendsFragment);
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideTitle(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoViewManager.instance().release();
    }
}
