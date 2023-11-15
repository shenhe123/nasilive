package com.feicui365.live.ui.act;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.ui.adapter.PersonalCenterAdapter;
import com.feicui365.live.ui.fragment.LiveLogFragment;
import com.feicui365.live.ui.fragment.RoomManagerFragment;
import com.feicui365.live.ui.fragment.RoomNoTalkFragment;
import com.feicui365.live.util.MyUserInstance;

public class LiveManageActivity extends OthrBase2Activity {
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
        viewpager.setOffscreenPageLimit(3);
    }




    private void setupViewPager(ViewPager mViewPager) {
        if (adapter == null) {
            setTitle("直播管理");
            adapter = new PersonalCenterAdapter(getSupportFragmentManager());
            initFragment();
            mViewPager.setAdapter(adapter);
            if (sliding_tabs.getTabCount() == 0) {
                sliding_tabs.addTab(sliding_tabs.newTab().setText("管理员列表"));
                sliding_tabs.addTab(sliding_tabs.newTab().setText("禁言列表"));
                sliding_tabs.addTab(sliding_tabs.newTab().setText("直播记录"));
                sliding_tabs.setTabTextColors( getResources().getColor(R.color.white), getResources().getColor(R.color.white));
                sliding_tabs.setupWithViewPager(viewpager);

            }
        }


    }

    private void initFragment() {


        RoomManagerFragment roomManagerFragment=new RoomManagerFragment();
        Bundle bundle1 = new Bundle();
        roomManagerFragment.setArguments(bundle1);
        adapter.addFragment(roomManagerFragment, "管理员列表"  );

        RoomNoTalkFragment noTalkFragment=new RoomNoTalkFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putString("anchorid", MyUserInstance.getInstance().getUserinfo().getId());
        noTalkFragment.setArguments(bundle2);
        adapter.addFragment(noTalkFragment,  "禁言列表" );


        LiveLogFragment liveLogFragment = new LiveLogFragment();

        adapter.addFragment(liveLogFragment, "直播记录"  );
    }
}
