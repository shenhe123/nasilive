package com.feicui365.live.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.eventbus.MessageBus;
import com.feicui365.live.eventbus.UnReadBus;
import com.feicui365.live.model.entity.LiveCategory;
import com.feicui365.live.model.entity.UserConfig;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.act.AllMessageActivity;

import com.feicui365.live.ui.act.SearchActivity;
import com.feicui365.live.ui.adapter.TabFragmentAdapter;
import com.feicui365.live.util.MyUserInstance;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @BindView(R.id.ll_search)
    LinearLayout ll_search;
    @BindView(R.id.magic_indicator)
    MagicIndicator magic_indicator;
    @BindView(R.id.rl_message)
    RelativeLayout rl_message;
    @BindView(R.id.tv_red_hot)
    TextView tv_red_hot;



    private ArrayList<LiveCategory> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private TabFragmentAdapter mTabFragmentAdapter;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        UserConfig userConfig = MyUserInstance.getInstance().getUserConfig();
        LiveCategory liveCategory = new LiveCategory();
        liveCategory.setTitle("热门");
        mTitles.add(liveCategory);
        mTitles.addAll(userConfig.getLive_category());


        for (int i = 0; i < mTitles.size(); i++) {

            Fragment fragment;
            if (i == 0) {
                //首页上面的推荐页面
                fragment = new HotFragment(0);
            } else {
                //直播列表页面
                fragment = new HotFragment(1, mTitles.get(i));
            }
            Bundle bundle = new Bundle();
            bundle.putString("id", mTitles.get(i).getId());
            fragment.setArguments(bundle);
            mFragments.add(fragment);

        }
        EventBus.getDefault().register(this);

        mTabFragmentAdapter = new TabFragmentAdapter(mFragments, mTitles, getChildFragmentManager(), getActivity());

        mViewPager.setOffscreenPageLimit(mFragments.size());// 设置预加载Fragment个数
        mViewPager.setAdapter(mTabFragmentAdapter);
        mViewPager.setCurrentItem(0);// 设置当前显示标签页为第一页


        ll_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUserInstance.getInstance().visitorIsLogin()) {


                    startActivity(new Intent(getActivity(), SearchActivity.class));
                }
            }
        });
        initTab(view);

        rl_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyUserInstance.getInstance().visitorIsLogin()){
                    Intent i = new Intent(getContext(), AllMessageActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetMessage(MessageBus message) {


            int num = Integer.valueOf(message.message);
            String result = "0";
            if (num > 99) {
                result = "99+";
            } else {
                result = String.valueOf(num);
            }
            if (!result.equals("0")) {
                tv_red_hot.setVisibility(View.VISIBLE);
                tv_red_hot.setText(result);
            } else {
                tv_red_hot.setVisibility(View.GONE);
            }



    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUnreadCount(UnReadBus message) {
        if(message.unread>0){
            tv_red_hot.setVisibility(View.VISIBLE);
            tv_red_hot.setText(String.valueOf(message.unread));
        }else{
            tv_red_hot.setVisibility(View.GONE);
        }
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
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#ffffff"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#ffffff"));
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
                indicator.setLineWidth(UIUtil.dip2px(context, 12));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setColors(Color.parseColor("#ffffff"));
                return indicator;
            }

        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator, mViewPager);
        mViewPager.setCurrentItem(0);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home2;
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
        EventBus.getDefault().unregister(this);
    }
}
