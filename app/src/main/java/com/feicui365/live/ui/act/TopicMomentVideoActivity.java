package com.feicui365.live.ui.act;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.Topic;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.PersonalCenterAdapter;
import com.feicui365.live.ui.fragment.UserTrendsFragment;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.Dialogs;
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

import butterknife.BindView;
import butterknife.OnClick;

public class TopicMomentVideoActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.magic_indicator)
    MagicIndicator magic_indicator;
    @BindView(R.id.rl_close)
    RelativeLayout rl_close;
    @BindView(R.id.rl_back2)
    RelativeLayout rl_back2;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    @BindView(R.id.tv_user_time)
    TextView tv_user_time;
    @BindView(R.id.tv_topic_sign)
    TextView tv_topic_sign;
    @BindView(R.id.tv_topic_title)
    TextView tv_topic_title;
    @BindView(R.id.rl_top_bg)
    ImageView rl_top_bg;

    private String topic;
    private Dialog dialog;
    private UserTrendsFragment list_hot;
    private UserTrendsFragment list_new;
    private ArrayList<String> mTitles=new ArrayList<>();

    PersonalCenterAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_topic_short_video;
    }

    @Override
    protected void initData() {
        mPresenter.getTopicInfo(topic);
    }

    @Override
    protected void initView() {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        hideTitle(true);
        topic = getIntent().getStringExtra("topic");
        if (topic == null) {
            return;
        }
        mTitles.add("热门");
        mTitles.add("最新");
        setupViewPager();

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();

                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar_title.setVisibility(View.VISIBLE);
                    toolbar.setBackgroundColor(Color.WHITE);
                    rl_back2.setVisibility(View.VISIBLE);
                    toolbar_title.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    if (verticalOffset > -500) {
                        rl_back2.setVisibility(View.GONE);
                        toolbar_title.setVisibility(View.GONE);
                        toolbar.setBackgroundColor(Color.TRANSPARENT);
                        isShow = false;
                    }
                }
            }
        });
    }


    @Override
    public void getTopicInfo(Topic bean) {
        toolbar_title.setText(bean.getTitle());
        tv_user_time.setText(bean.getUsed_times() + " 人参与");
        tv_topic_sign.setText(bean.getDesc());
        tv_topic_title.setText(bean.getTitle());
        if(bean.getBack_img_url()!=null){
            if(!bean.getBack_img_url().equals("")){
                Glide.with(this).load(bean.getBack_img_url()).into(rl_top_bg);
            }

        }
    }

    private void setupViewPager() {
        viewpager.setOffscreenPageLimit(2);
        if (adapter == null) {
            adapter = new PersonalCenterAdapter(getSupportFragmentManager());
            list_hot = new UserTrendsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("type", 18);
            bundle.putString("topic", topic);
            bundle.putString("topic_type", "0");
            list_hot.setArguments(bundle);


            list_new = new UserTrendsFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putInt("type", 18);
            bundle2.putString("topic", topic);
            bundle.putString("topic_type", "1");
            list_new.setArguments(bundle2);

            adapter.addFragment(list_hot, "热门");
            adapter.addFragment(list_new, "最新");
            viewpager.setAdapter(adapter);
            initTab();
        }

    }

    private void initTab() {
        magic_indicator = findViewById(R.id.magic_indicator);
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
                colorTransitionPagerTitleView.setTextSize(18);

                colorTransitionPagerTitleView.setText(mTitles.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewpager.setCurrentItem(index);

                    }
                });

                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineWidth(UIUtil.dip2px(context, 10));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setColors(Color.parseColor("#FF6273"));
                return indicator;
            }

        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator, viewpager);

        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(context, 40);
            }
        });


        viewpager.setCurrentItem(0);

    }



    @OnClick({R.id.rl_close,R.id.rl_back2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_close:

                finish();
                break;
            case R.id.rl_back2:

                finish();
                break;
        }
    }


    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }


    @Override
    public void onError(Throwable throwable) {
        ToastUtils.showT(throwable.getMessage());
    }

}
