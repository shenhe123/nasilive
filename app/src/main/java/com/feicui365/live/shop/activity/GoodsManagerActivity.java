package com.feicui365.live.shop.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.entity.LiveCategory;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.fragment.GoodsManagerFragment;
import com.feicui365.live.ui.adapter.TabFragmentAdapter;

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

public class GoodsManagerActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {
    @BindView(R.id.viewPager)
    ViewPager vp_order;
    @BindView(R.id.magic_indicator)
    MagicIndicator magic_indicator;




    private ArrayList<LiveCategory> mTitles = new ArrayList<>();
    private TabFragmentAdapter mTabFragmentAdapter;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("商品管理");
        initTab();
        initFragment();


    }

    private void initFragment() {
        mFragments.add(new GoodsManagerFragment("0"));
        mFragments.add(new GoodsManagerFragment("1"));
        mFragments.add(new GoodsManagerFragment("2"));

        mFragments.add(new GoodsManagerFragment("3"));

        mTabFragmentAdapter = new TabFragmentAdapter(mFragments, mTitles, getSupportFragmentManager(), this);
        vp_order.setOffscreenPageLimit(mFragments.size());// 设置预加载Fragment个数
        vp_order.setAdapter(mTabFragmentAdapter);
        vp_order.setCurrentItem(0);// 设置当前显示标签页为第一页


    }

    private void initTab() {


        mTitles.add(new LiveCategory("待审核"));
        mTitles.add(new LiveCategory("已上架"));
        mTitles.add(new LiveCategory("已下架"));
        mTitles.add(new LiveCategory("审核被拒"));

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView colorTransitionPagerTitleView = new SimplePagerTitleView(context);

                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#000000"));
                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                colorTransitionPagerTitleView.setTextSize(15);
                colorTransitionPagerTitleView.setText(mTitles.get(index).getTitle());
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        vp_order.setCurrentItem(index);

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
                indicator.setColors(getResources().getColor(R.color.color_FE2C55));
                return indicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {

                return 1.0f;

            }
        });
        magic_indicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magic_indicator, vp_order);
    }

    @Override
    public void onError(Throwable throwable) {

    }


}
