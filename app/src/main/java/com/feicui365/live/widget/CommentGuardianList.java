/*
package com.feicui365.live.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.GuardianInfo;

import com.feicui365.live.ui.adapter.GuardianListAdapter;
import com.feicui365.live.ui.adapter.HomePagerAdapter;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.ui.adapter.ShopItemAdapter;
import com.feicui365.live.ui.fragment.BuyGuardianFragment;
import com.feicui365.live.ui.fragment.GuardianListFragment;

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


public class CommentGuardianList extends BottomPopupView implements View.OnClickListener {
    MagicIndicator magic_indicator;
    ViewPager vp_guardian;
    List<Fragment> list = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    String anchorid;
    GuardianInfo guardianInfo;
    BuyFinish buyFinish;

    int postion = 99;
    boolean is_ver = true;
    GuardianListFragment guardianListFragment;
    public void setBuyFinish(BuyFinish buyFinish) {
        this.buyFinish = buyFinish;
    }

    public interface BuyFinish {
        void BuySuccess(GuardianInfo guardianInfo);
    }

    //表情结束
    public CommentGuardianList(@NonNull Context context, String anchorid, GuardianInfo guardianInfo) {
        super(context);
        this.anchorid = anchorid;
        this.guardianInfo = guardianInfo;

    }

    public CommentGuardianList(@NonNull Context context, String anchorid, GuardianInfo guardianInfo, int postion) {
        super(context);
        this.anchorid = anchorid;
        this.guardianInfo = guardianInfo;
        this.postion = postion;
    }

    public CommentGuardianList(@NonNull Context context, String anchorid, GuardianInfo guardianInfo, boolean is_ver) {
        super(context);
        this.anchorid = anchorid;
        this.guardianInfo = guardianInfo;
        this.postion = postion;
        this.is_ver = is_ver;
    }


    public CommentGuardianList(@NonNull Context context, String anchorid) {
        super(context);
        this.anchorid = anchorid;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_guardian_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        magic_indicator = findViewById(R.id.magic_indicator);
        vp_guardian = findViewById(R.id.vp_guardian);


        if (is_ver) {
            titles.add("守护");
             guardianListFragment = GuardianListFragment.newInstance(anchorid, true);
            list.add(guardianListFragment);
        }else{
            magic_indicator.setVisibility(GONE);

        }


            BuyGuardianFragment buyGuardianFragment = new BuyGuardianFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("GuardianInfo", guardianInfo);
            bundle.putString("anchorid", anchorid);
            buyGuardianFragment.setArguments(bundle);
            list.add(buyGuardianFragment);

            buyGuardianFragment.setBuyFinish(new BuyGuardianFragment.BuyFinish() {
                @Override
                public void BuySuccess(GuardianInfo guardianInfo) {
                    if (buyFinish != null) {
                        buyFinish.BuySuccess(guardianInfo);
                    }
                    if(guardianListFragment!=null){
                        guardianListFragment.initData();
                    }
                }
            });
            titles.add("开通守护");



        vp_guardian.setOffscreenPageLimit(list.size());
        vp_guardian.setAdapter(new HomePagerAdapter(((FragmentActivity) getContext()).getSupportFragmentManager(), list));
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView colorTransitionPagerTitleView = new SimplePagerTitleView(context);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setNormalColor(getResources().getColor(R.color.color_E89EFF));
                colorTransitionPagerTitleView.setSelectedColor(getResources().getColor(R.color.white));
                colorTransitionPagerTitleView.setTextSize(16);
                colorTransitionPagerTitleView.setText(titles.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp_guardian.setCurrentItem(index);

                    }
                });

                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                return null;
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
        vp_guardian.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);

            }
        });

        if (postion != 99) {
            vp_guardian.setCurrentItem(postion);
        }
    }


    @Override
    public void onClick(View v) {

    }
}*/
