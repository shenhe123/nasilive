package com.feicui365.live.ui.act;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.LiveCategory;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.TabFragmentAdapter;
import com.feicui365.live.ui.fragment.AttentUserFragment;
import com.feicui365.live.ui.fragment.HotFragment;
import com.feicui365.live.ui.fragment.ShortVideoListFragment;
import com.feicui365.live.ui.fragment.UserTrendsFragment;
import com.feicui365.live.util.ToastUtils;
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
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.rl_null)
    RelativeLayout rl_null;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.magic_indicator)
    MagicIndicator magic_indicator;
    @BindView(R.id.iv_2)
    ImageView iv_2;
    @BindView(R.id.tv_cancel)
    TextView tv_cancel;

    private ArrayList<LiveCategory> mTitles = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private TabFragmentAdapter mTabFragmentAdapter;

    private UserTrendsFragment userTrendsFragment;
    private ShortVideoListFragment searchShort;
    private HotFragment hotFragment;
    private AttentUserFragment attentUserFragment;
    private String keyword = "";

    @Override
    protected int getLayoutId() {
        return R.layout.search_activity;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        hideTitle(true);
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    if (et_search.getText().toString().equals("")) {
                        ToastUtils.showT("请输入搜索内容");
                        return false;
                    } else {

                        userTrendsFragment.search(et_search.getText().toString());
                        searchShort.search(et_search.getText().toString());
                        hotFragment.search(et_search.getText().toString());
                        attentUserFragment.search(et_search.getText().toString());
                    }
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //得到InputMethodManager的实例
                    if (imm.isActive()) {//如果开启
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);//关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
                    }
                }
                return false;
            }
        });

        initIndicator();
        initFragments();


        mTabFragmentAdapter = new TabFragmentAdapter(mFragments, mTitles, getSupportFragmentManager(), this);
        mViewPager.setOffscreenPageLimit(mFragments.size());// 设置预加载Fragment个数
        mViewPager.setAdapter(mTabFragmentAdapter);
        mViewPager.setCurrentItem(0);// 设置当前显示标签页为第一页
        iv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_search.setFocusable(true);

        et_search.setFocusableInTouchMode(true);
        et_search.requestFocus();
    }


    private void initFragments() {
        userTrendsFragment = new UserTrendsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 13);
        bundle.putString("keyword", keyword);
        userTrendsFragment.setArguments(bundle);
        mFragments.add(userTrendsFragment);

        searchShort = new ShortVideoListFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 3);
        bundle1.putString("keyword", keyword);
        searchShort.setArguments(bundle1);
        mFragments.add(searchShort);

        hotFragment = new HotFragment(2);
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 2);
        bundle2.putString("keyword", keyword);
        hotFragment.setArguments(bundle2);
        mFragments.add(hotFragment);

        attentUserFragment = new AttentUserFragment("3");
        mFragments.add(attentUserFragment);
    }

    private void initIndicator() {

        mTitles.add(new LiveCategory("动态"));
        mTitles.add(new LiveCategory("短视频"));
        mTitles.add(new LiveCategory("直播"));
        mTitles.add(new LiveCategory("用户"));

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
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
                indicator.setLineWidth(UIUtil.dip2px(context, 13));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setColors(getResources().getColor(R.color.color_theme));
                return indicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {

                return 1.0f;

            }
        });
        magic_indicator.setNavigator(commonNavigator);

        ViewPagerHelper.bind(magic_indicator, mViewPager);
    }


    @Override
    public void onError(Throwable throwable) {

    }
}
