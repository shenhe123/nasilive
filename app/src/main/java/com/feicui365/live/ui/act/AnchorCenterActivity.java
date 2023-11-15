package com.feicui365.live.ui.act;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.dialog.ChatGiftDialogFragment;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.interfaces.OnSendGiftFinish;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.ui.fragment.UserTrendsFragment;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.model.entity.ChatGiftBean;
import com.feicui365.live.model.entity.ChatReceiveGiftBean;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.PersonalAnchorInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.PersonalCenterAdapter;

import com.feicui365.live.ui.fragment.InformationFragment;

import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.GiftAnimViewHolder;
import com.feicui365.live.widget.MyTabLayout;

import com.feicui365.live.widget.pagerlayoutmanager.OnViewPagerListener;
import com.feicui365.live.util.MyUserInstance;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.stx.xhb.xbanner.XBanner;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import butterknife.BindView;
import butterknife.OnClick;

/*
 * 主播個人中心
 *
 * */
public class AnchorCenterActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View, OnViewPagerListener, OnSendGiftFinish {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.sliding_tabs)
    MyTabLayout sliding_tabs;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.iv_level)
    ImageView iv_level;
    @BindView(R.id.iv_attention)
    ImageView iv_attention;
    @BindView(R.id.iv_sex)
    ImageView iv_sex;
    @BindView(R.id.tv_sign)
    TextView tv_sign;
    @BindView(R.id.tv_guanzhu)
    TextView tv_guanzhu;
    @BindView(R.id.tv_fans)
    TextView tv_fans;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.xb_ad)
    XBanner xb_ad;

    @BindView(R.id.root)
    RelativeLayout root;


    @BindView(R.id.iv_vip_level)
    ImageView iv_vip_level;
    @BindView(R.id.iv_anchor_level)
    ImageView iv_anchor_level;
    @BindView(R.id.tv_id)
    TextView tv_id;
    @BindView(R.id.cl_top)
    CoordinatorLayout cl_top;
    @BindView(R.id.rl_back2)
    RelativeLayout rl_back2;

    @BindView(R.id.iv_into_live)
    ImageView iv_into_live;

    @BindView(R.id.iv_chat)
    ImageView iv_chat;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.rl_back3)
    RelativeLayout rl_back3;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;


    private String count = "1";

    private String type;
    private HotLive liveInfo;
    private String anchor_id;
    private ConcurrentLinkedQueue<GiftInfo> mGifQueue = new ConcurrentLinkedQueue<>();
    public ChatGiftDialogFragment chatGiftDialogFragment;
    private LinkedHashMap<Integer, ArrayList<GiftInfo>> gridGiftList = new LinkedHashMap<>(20);
    private ArrayList<Fragment> fragmentList;

    private GiftAnimViewHolder mGiftAnimViewHolder;
    private PersonalAnchorInfo personalAnchorInfo;
    private InformationFragment informationFragment;
    private UserTrendsFragment userTrendsFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_homepage;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setAndroidNativeLightStatusBar(true);
    }

    @Override
    protected void initData() {
        mPresenter.getPersonalAnchorInfo(anchor_id);

    }

    @Override
    protected void initView() {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        hideTitle(true);
        Intent intent = getIntent();
        anchor_id = intent.getStringExtra("data");
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getPersonalAnchorInfo(anchor_id);
            }
        });

        collapsingToolbar.setCollapsedTitleGravity(Gravity.CENTER);//设置收缩后标题的位置

        setupViewPager(viewpager);
        viewpager.setOffscreenPageLimit(3);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                if (i != 1) {
                    VideoViewManager.instance().release();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });


    }


    @OnClick({R.id.chat_gitf_iv, R.id.rl_back2, R.id.rl_back3, R.id.iv_into_live, R.id.iv_chat, R.id.iv_attention})
    public void onClick(View view) {
        if(isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.chat_gitf_iv:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    showGiftList();
                }
                break;
            case R.id.rl_back2:
                finish();
                break;

            case R.id.rl_back3:
                finish();
                break;
            case R.id.iv_attention:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    attendAnchor(anchor_id);
                }
                break;

            case R.id.iv_into_live:

                if (liveInfo == null) {
                    ToastUtils.showT("主播休息中");
                    return;
                }
                ArmsUtils.goLive(liveInfo,this);

             /*   switch (liveInfo.getRoom_type()) {
                    case "0":
                        goLive(liveInfo.getOrientation());
                        break;
                    case "1":
                        if (liveInfo.getPassword() == null) {
                            ToastUtils.showT(WordUtil.getString(R.string.Anchor_No_Live));
                            return;
                        }
                        PassWordDialog.Builder builder = new PassWordDialog.Builder(this);
                        builder.setOnFinishListener(new PassWordDialog.OnFinishListener() {
                            @Override
                            public void onFinish(String price, PassWordDialog passWordDialog) {
                                if (StringUtil.md5(price).equals(liveInfo.getPassword().toUpperCase())) {
                                    goLive(liveInfo.getOrientation());
                                    passWordDialog.dismiss();
                                } else {
                                    ToastUtils.showT(WordUtil.getString(R.string.Input_Error));
                                    passWordDialog.dismiss();
                                }


                            }
                        });
                        builder.create().show();
                        builder.setHint(WordUtil.getString(R.string.Enter_new_password));
                        builder.setTitle(WordUtil.getString(R.string.Password));
                        break;
                    case "2":
                        String my_gold = MyUserInstance.getInstance().getUserinfo().getGold();
                        String live_gold = liveInfo.getPrice();
                        initShouFei(live_gold, liveInfo, my_gold);
                        break;
                }*/
                break;
            case R.id.iv_chat:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    if (personalAnchorInfo != null) {
                        TxImUtils.getSocketManager().startChat(personalAnchorInfo.getId(),personalAnchorInfo.getNick_name(),personalAnchorInfo.getAvatar());
                    }
                }
                break;


        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.getUserInfo();

    }

    @Override
    public void setUserInfo(UserRegist bean) {
        MyUserInstance.getInstance().setUserInfo(bean);
        mPresenter.getPersonalAnchorInfo(anchor_id);

    }

    @Override
    public void setPersonalAnchorInfo(PersonalAnchorInfo bean) {
        refreshLayout.finishRefresh();
        liveInfo = bean.getLive();
        if (liveInfo == null) {
            Glide.with(this).load(R.mipmap.ic_nolive).into(iv_into_live);
        }
        personalAnchorInfo = bean;
        //名字
        tv_name.setText(bean.getNick_name());
        //用户等级
        iv_level.setImageResource(LevelUtils.getUserLevel(bean.getUser_level()));
        //主播等级
        if (bean.getIs_anchor() != null) {
            if (bean.getIs_anchor().equals("1")) {
                iv_anchor_level.setImageResource(LevelUtils.getAnchorLevel(bean.getAnchor_level()));
            }
        }
        //VIP等级

        if (bean.getVip_level() != 0 & bean.getVip_date() != null) {
            if (bean.getVip_level()!=0) {
                if (MyUserInstance.getInstance().OverTime(bean.getVip_date())) {
                    Glide.with(this).load(ArmsUtils.getVipLevelIcon(bean.getVip_level(),bean.getVip_date())).into(iv_vip_level);
                } else {
                    iv_vip_level.setVisibility(View.GONE);
                }
            } else {
                iv_vip_level.setVisibility(View.GONE);
            }
        } else {
            iv_vip_level.setVisibility(View.GONE);
        }


        //性别
        if ( bean.getGender()==1) {
            iv_sex.setImageResource(R.mipmap.boy);
        } else {
            iv_sex.setImageResource(R.mipmap.girl);
        }

        //签名
        if (bean.getProfile().getSignature() == null) {
            tv_sign.setText("当前用户暂无签名");
        } else if (bean.getProfile().getSignature().equals("")) {
            tv_sign.setText("当前用户暂无签名");
        } else {
            tv_sign.setText(bean.getProfile().getSignature());
        }
        toolbar_title.setText(bean.getNick_name());
        //ID
        tv_id.setText("ID:" + bean.getId());
        //关注数
        if (Integer.valueOf(bean.getAttent_count()) > 10000) {
            tv_guanzhu.setText(Integer.valueOf(bean.getAttent_count()) / 10000 + "万");
        } else {
            tv_guanzhu.setText(bean.getAttent_count());
        }
        //关注
        if (bean.getIsattent() == null || bean.getIsattent().equals("0")) {

            iv_attention.setImageDrawable(getResources().getDrawable(R.mipmap.personal_guanzhu));
            type = "1";
        } else {
            iv_attention.setImageDrawable(getResources().getDrawable(R.mipmap.personal_yiguanzhu));
            type = "0";
        }

        //粉丝
        if (Integer.valueOf(bean.getFans_count()) > 10000) {
            tv_fans.setText(Integer.valueOf(bean.getFans_count()) / 10000 + "万");
        } else {
            tv_fans.setText(bean.getFans_count());
        }
        //送出
        if (Integer.valueOf(bean.getGift_spend()) > 10000) {
            tv_send.setText(Integer.valueOf(bean.getGift_spend()) / 10000 + "万");
        } else {
            tv_send.setText(bean.getGift_spend());
        }


        if (bean.getProfile().getPhotos() != null) {

            //照片墙
            List<String> imgesUrl = new ArrayList<>();
            String[] images = bean.getProfile().getPhotos().split(",");
            for (int i = 0; i < images.length; i++) {
                imgesUrl.add(images[i]);
            }
            if (imgesUrl.size() != 0) {
                xb_ad.setData(R.layout.image_fresco2, imgesUrl, null);
                xb_ad.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        ImageView mSimpleView = view.findViewById(R.id.my_image_view);
                        Glide.with(AnchorCenterActivity.this).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.zhanwei)).load(imgesUrl.get(position)).into(mSimpleView);
                    }
                });
            } else {
                List<String> temp = new ArrayList<>();
                temp.add(bean.getAvatar());
                xb_ad.setData(R.layout.image_fresco2, temp, null);
                xb_ad.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        ImageView mSimpleView = view.findViewById(R.id.my_image_view);
                        Glide.with(AnchorCenterActivity.this).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.zhanwei)).load(temp.get(position)).into(mSimpleView);
                    }
                });
            }
        } else {

            List<String> temp = new ArrayList<>();
            temp.add(bean.getAvatar());
            xb_ad.setData(R.layout.image_fresco2, temp, null);
            xb_ad.loadImage(new XBanner.XBannerAdapter() {
                @Override
                public void loadBanner(XBanner banner, Object model, View view, int position) {
                    ImageView mSimpleView = view.findViewById(R.id.my_image_view);
                    Glide.with(AnchorCenterActivity.this).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.zhanwei)).load(temp.get(position)).into(mSimpleView);
                }
            });
        }
        //资料填充数据
        InformationFragment.getInstance().setPersonalAnchorInfo(bean);
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
                    rl_back3.setVisibility(View.VISIBLE);
                    toolbar_title.setVisibility(View.VISIBLE);
                    isShow = true;
                } else if (isShow) {
                    if (verticalOffset > -500) {
                        rl_back3.setVisibility(View.GONE);
                        toolbar_title.setVisibility(View.GONE);
                        toolbar.setBackgroundColor(Color.TRANSPARENT);
                        isShow = false;
                    }
                }
            }
        });
    }


    public void showGiftList() {
        if (null == chatGiftDialogFragment) {
            chatGiftDialogFragment = new ChatGiftDialogFragment();
            chatGiftDialogFragment.setOnSendGiftFinish(this);
        }
        chatGiftDialogFragment.show(getSupportFragmentManager(), "ChatGiftDialogFragment");
    }


    private void setupViewPager(ViewPager mViewPager) {

        informationFragment = InformationFragment.newInstance(anchor_id);
        userTrendsFragment = new UserTrendsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 16);
        bundle.putString("authorId", anchor_id);
        userTrendsFragment.setArguments(bundle);

        PersonalCenterAdapter adapter = new PersonalCenterAdapter(getSupportFragmentManager());
        adapter.addFragment(informationFragment, WordUtil.getString(R.string.Info));
        adapter.addFragment(userTrendsFragment, WordUtil.getString(R.string.Moment));

        mViewPager.setAdapter(adapter);

        sliding_tabs.addTab(sliding_tabs.newTab().setText(WordUtil.getString(R.string.Info)));
        sliding_tabs.addTab(sliding_tabs.newTab().setText(WordUtil.getString(R.string.Moment)));
        sliding_tabs.setTabIndicatorFullWidth(false);
        sliding_tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                TextView textView = new TextView(AnchorCenterActivity.this);
                float selectedSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics());
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, selectedSize);
                textView.setTextColor(getResources().getColor(R.color.black));
                textView.setText(tab.getText());
                TextPaint tp = textView.getPaint();
                tp.setFakeBoldText(true);
                tab.setCustomView(textView);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //未选中tab的逻辑
                tab.setCustomView(null);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //再次选中tab的逻辑
            }
        });
        sliding_tabs.setupWithViewPager(viewpager);
    }

    private void attendAnchor(String anchorid) {

        HttpUtils.getInstance().attentAnchor(anchorid, type, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                JSONObject data = HttpUtils.getInstance().check(response);
                if (HttpUtils.getInstance().swtichStatus(data)) {
                    if (type.equals("0")) {
                        type = "1";
                        iv_attention.setImageDrawable(getResources().getDrawable(R.mipmap.personal_guanzhu));
                    } else {
                        type = "0";

                        iv_attention.setImageDrawable(getResources().getDrawable(R.mipmap.personal_yiguanzhu));
                    }

                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);

                hideLoading();
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {
        String a = throwable.toString();
    }

    @Override
    public void onInitComplete() {

    }

    @Override
    public void onPageSelected(int position, boolean isBottom) {

    }

    @Override
    public void onPageRelease(boolean isNext, int position) {

    }

    @Override
    public void sendSuccess(String gold, ChatGiftBean bean) {

        chatGiftDialogFragment.setmCoin(Integer.parseInt(gold) + "");
        MyUserInstance.getInstance().getUserinfo().setGold(gold);
        showGift(initGift(bean));
    }

    public void showGift(ChatReceiveGiftBean giftBean) {

        if (mGiftAnimViewHolder == null) {
            mGiftAnimViewHolder = new GiftAnimViewHolder(this, root);
            mGiftAnimViewHolder.addToParent();
        }
        mGiftAnimViewHolder.showGiftAnim(giftBean);
    }

    @Override
    public void onSendClick(ChatGiftBean bean, String count) {
        this.count = count;

        mPresenter.sendGift(count, personalAnchorInfo.getId(), "", bean.getId() + "", bean);
    }

    private ChatReceiveGiftBean initGift(ChatGiftBean giftData) {
        ChatReceiveGiftBean chatReceiveGiftBean = new ChatReceiveGiftBean();
        chatReceiveGiftBean.setUid(MyUserInstance.getInstance().getUserinfo().getId());
        chatReceiveGiftBean.setAvatar(MyUserInstance.getInstance().getUserinfo().getAvatar());
        chatReceiveGiftBean.setUserNiceName(MyUserInstance.getInstance().getUserinfo().getNick_name());
        chatReceiveGiftBean.setLevel(MyUserInstance.getInstance().getUserinfo().getUser_level());
        chatReceiveGiftBean.setGiftId(giftData.getId() + "");
        chatReceiveGiftBean.setGiftCount(Integer.parseInt(count));
        chatReceiveGiftBean.setGiftName(giftData.getTitle());
        chatReceiveGiftBean.setGiftIcon(giftData.getIcon());
        if (giftData.getAnimat_type().equals("0")) {
            chatReceiveGiftBean.setGif(0);
        } else {
            chatReceiveGiftBean.setGif(1);
            chatReceiveGiftBean.setGifUrl(giftData.getAnimation());
            if (giftData.getAnimat_type().equals("1")) {
                chatReceiveGiftBean.setGitType(0);
            }
            if (giftData.getAnimat_type().equals("2")) {
                chatReceiveGiftBean.setGitType(1);
            }
        }


        return chatReceiveGiftBean;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VideoViewManager.instance().release();
        if (null != mGiftAnimViewHolder) {
            mGiftAnimViewHolder.cancelAllAnim();
        }
    }






}
