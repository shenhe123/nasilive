package com.feicui365.live.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.feicui365.live.R;
import com.feicui365.live.ui.adapter.HomePagerAdapter;
import com.feicui365.live.ui.fragment.RoomManagerFragment;
import com.feicui365.live.ui.fragment.RoomNoTalkFragment;
import com.lxj.xpopup.core.BottomPopupView;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;


public class RoomMangePopup extends BottomPopupView{

    ViewPager vp_room_manager;
    MagicIndicator magic_indicator;
    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    String anchorid="";
    String type="1";//1主播 2管理员

    public RoomMangePopup(@NonNull Context context,String type,String anchorid) {
        super(context);
        this.type=type;
        this.anchorid=anchorid;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_manage;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        vp_room_manager=findViewById(R.id.vp_room_manager);
        magic_indicator=findViewById(R.id.magic_indicator);
        initFragment(type);
        initData();
    }

    private void initFragment(String type) {
        switch (type){
            case "1":
                RoomManagerFragment roomManagerFragment=new RoomManagerFragment();
                Bundle bundle1 = new Bundle();
                roomManagerFragment.setArguments(bundle1);
                fragments.add(roomManagerFragment);

                RoomNoTalkFragment noTalkFragment=new RoomNoTalkFragment();
                Bundle bundle2 = new Bundle();
                bundle2.putString("anchorid", anchorid);
                noTalkFragment.setArguments(bundle2);
                fragments.add(noTalkFragment);
                titles.add("管理员列表");
                titles.add("禁言列表");
                break;
            case "2":
                RoomNoTalkFragment noTalkFragment2=new RoomNoTalkFragment();
                Bundle bundle3 = new Bundle();
                bundle3.putString("anchorid", anchorid);
                noTalkFragment2.setArguments(bundle3);

                fragments.add(noTalkFragment2);
                titles.add("禁言列表");
                break;
        }
    }

    private void initData(){



        vp_room_manager.setAdapter(new HomePagerAdapter(((FragmentActivity)getContext()).getSupportFragmentManager(), fragments));
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView colorTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#000000"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                colorTransitionPagerTitleView.setTextSize(16);
                colorTransitionPagerTitleView.setText(titles.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp_room_manager.setCurrentItem(index);
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
        magic_indicator.setNavigator(commonNavigator);

        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getContext(), 65);
            }
        });
        FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magic_indicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        vp_room_manager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
            }
        });

    }


}