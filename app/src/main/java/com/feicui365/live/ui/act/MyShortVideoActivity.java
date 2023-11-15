package com.feicui365.live.ui.act;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.ui.adapter.PersonalCenterAdapter;
import com.feicui365.live.ui.fragment.ShortVideoListFragment;
import com.feicui365.live.util.MyUserInstance;

public class MyShortVideoActivity extends OthrBase2Activity {
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

            setTitle("我的短视频");
            adapter = new PersonalCenterAdapter(getSupportFragmentManager());

            ShortVideoListFragment verify_1=new ShortVideoListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", 4);
            bundle.putString("status", "1");
            bundle.putString("authorId", MyUserInstance.getInstance().getUserinfo().getId());
            verify_1.setArguments(bundle);


            ShortVideoListFragment verify_2=new ShortVideoListFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("type", 4);
            bundle2.putString("status", "0");
            bundle2.putString("authorId", MyUserInstance.getInstance().getUserinfo().getId());
            verify_2.setArguments(bundle2);

            ShortVideoListFragment verify_3=new ShortVideoListFragment();
            Bundle bundle3= new Bundle();
            bundle3.putInt("type", 4);
            bundle3.putString("status", "2");
            bundle3.putString("authorId", MyUserInstance.getInstance().getUserinfo().getId());
            verify_3.setArguments(bundle3);

            adapter.addFragment(verify_1,"已审核" );
            adapter.addFragment(verify_2, "待审核" );
            adapter.addFragment(verify_3, "未通过" );
            mViewPager.setAdapter(adapter);
            if (sliding_tabs.getTabCount() == 0) {
                sliding_tabs.addTab(sliding_tabs.newTab().setText("已审核"));
                sliding_tabs.addTab(sliding_tabs.newTab().setText("待审核"));
                sliding_tabs.addTab(sliding_tabs.newTab().setText("未通过"));
                sliding_tabs.setTabTextColors(Color.parseColor("#777777"), getResources().getColor(R.color.color_theme));
                sliding_tabs.setupWithViewPager(viewpager);
            }
        }
    }
}
