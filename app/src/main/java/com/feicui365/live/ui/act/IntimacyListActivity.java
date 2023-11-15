package com.feicui365.live.ui.act;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.ui.fragment.RankListFragment;
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
//亲密榜
public class IntimacyListActivity extends OthrBase2Activity {


    private PalyTabFragmentPagerAdapter adapter;
    private ArrayList list = new ArrayList<>();
    private ArrayList<String> mTitles = new ArrayList<>();
    private MagicIndicator magicIndicator;
    ViewPager myViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_intimacy_list;
    }

    @Override
    protected void initData() {


        initFragment();
        hideTitle(true);
        rl_title.setVisibility(View.GONE);
        myViewPager = findViewById(R.id.viewPager);
        magicIndicator = findViewById(R.id.magic_indicator);
        mTitles.add("亲密总榜");
        mTitles.add("亲密周榜");




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
        RankListFragment totalIntimacy = new RankListFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 2);

        totalIntimacy.setArguments(bundle1);
        list.add(totalIntimacy);

        RankListFragment rankWeeklyListFragment = new RankListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);

        rankWeeklyListFragment.setArguments(bundle);
        list.add(rankWeeklyListFragment);
    }





}
