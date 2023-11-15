package com.feicui365.live.ui.act;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dueeeke.videoplayer.player.VideoViewManager;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.ui.adapter.PersonalCenterAdapter;

import com.feicui365.live.ui.fragment.UserTrendsFragment;

public class MyTrendActivity extends OthrBase2Activity {
    ViewPager viewpager;
    TabLayout sliding_tabs;
    PersonalCenterAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.my_short_video_activity;
    }

    @Override
    protected void initData() {
        viewpager=findViewById(R.id.viewpager);
        sliding_tabs=findViewById(R.id.sliding_tabs);
        setupViewPager(viewpager);

    }




    private void setupViewPager(ViewPager mViewPager) {
        if (adapter == null) {
            setTitle("我的动态");
            adapter = new PersonalCenterAdapter(getSupportFragmentManager());
            initFragment();
            mViewPager.setAdapter(adapter);
            if (sliding_tabs.getTabCount() == 0) {
                sliding_tabs.addTab(sliding_tabs.newTab().setText("已审核"));
                sliding_tabs.addTab(sliding_tabs.newTab().setText("待审核"));
                sliding_tabs.addTab(sliding_tabs.newTab().setText("未通过"));
                sliding_tabs.setTabTextColors(Color.parseColor("#777777"), getResources().getColor(R.color.color_theme));
                sliding_tabs.setupWithViewPager(viewpager);

            }
        }

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                VideoViewManager.instance().release();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initFragment() {

        UserTrendsFragment userTrendsFragment = new UserTrendsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 14);
        bundle.putString("status", "1");
        userTrendsFragment.setArguments(bundle);
        adapter.addFragment(userTrendsFragment, "已审核"  );

        UserTrendsFragment userTrendsFragment2 = new UserTrendsFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 14);
        bundle2.putString("status", "0");
        userTrendsFragment2.setArguments(bundle2);
        adapter.addFragment(userTrendsFragment2,  "待审核" );

        UserTrendsFragment userTrendsFragment3 = new UserTrendsFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("type", 14);
        bundle3.putString("status", "2");
        userTrendsFragment3.setArguments(bundle3);
        adapter.addFragment(userTrendsFragment3, "未通过"  );
    }
}
