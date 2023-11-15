/*
package com.feicui365.live.ui.act;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.net.http.HttpResponseCache;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.bean.Message;
import com.feicui365.live.bean.MessageData;
import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.dialog.ChatGiftDialogFragment;
import com.feicui365.live.dialog.ChatGiftDialogHorizontalFragment;
import com.feicui365.live.dialog.LivePriceDialog;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.interfaces.OnSendGiftFinish;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.ChatGiftBean;
import com.feicui365.live.model.entity.ChatReceiveGiftBean;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.presenter.SuperPlayerPresenter;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.ui.fragment.AnchorFragment;
import com.feicui365.live.ui.fragment.ChatFragment;
import com.feicui365.live.ui.fragment.ContributionFragment;
import com.feicui365.live.ui.uiinterfae.ShowGift;
import com.feicui365.live.util.DateUtil;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.live.widget.GiftAnimViewHolder;
import com.nasinet.nasinet.utils.AppManager;
import com.opensource.svgaplayer.SVGAParser;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.liteav.demo.play.bean.GiftData;
import com.tencent.liteav.demo.play.controller.TCVodControllerLarge;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import butterknife.BindView;
import butterknife.OnClick;

public class SuperPlayerActivity2 extends BaseMvpActivity<SuperPlayerPresenter> implements SuperPlayerContrat.View,
        View.OnClickListener
        , SuperPlayerView.OnSuperPlayerViewCallback, OnSendGiftFinish {
    private List<Fragment> list;
    private AlertDialog.Builder builder;
    private PalyTabFragmentPagerAdapter adapter;
    private ChatFragment chatFragment;
    private AnchorFragment anchorFragment;

    private ContributionFragment contributionFragment;
    public boolean is_show_input = false;
    @BindView(R.id.superVodPlayerView)
    SuperPlayerView mSuperPlayerView;
    @BindView(R.id.viewPager)
    ViewPager myViewPager;
    @BindView(R.id.chat_tv)
    TextView chat_tv;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.anchor_tv)
    TextView anchor_tv;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.contribution_tv)
    TextView contribution_tv;
    @BindView(R.id.line3)
    View line3;



    @BindView(R.id.title_bar)
    RelativeLayout title_bar;

    @BindView(R.id.tv_gift_info)
    TextView tv_gift_info;
    @BindView(R.id.attention_tv)
    TextView attention_tv;
    @BindView(R.id.group_1)
    RelativeLayout group_1;
    @BindView(R.id.root)
    RelativeLayout root;



    public HotLive hotLive;
    public ChatGiftDialogFragment chatGiftDialogFragment;
    private TIMConversation conversation;
    private boolean DAN_MU_FLAG = false;
    private ConcurrentLinkedQueue<Message> mGifQueue = new ConcurrentLinkedQueue<>();
    private ChatGiftDialogHorizontalFragment chatGiftDialogHorizontalFragment;
    private GiftAnimViewHolder mGiftAnimViewHolder;
    private OrientationEventListener mOrientationListener; // 屏幕方向改变监听器
    private int vip_in_type = 1;

    private Handler handler = new Handler();
    private Runnable runnable;
    private boolean is_stop = false;
    private boolean is_vertical = true;
    private TIMMessageListener timMessageListener;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_player;
    }

    @Override
    protected void initData() {


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        hotLive = (HotLive) getIntent().getSerializableExtra("studio_info");
        SVGAParser.Companion.shareParser().init(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //设置SVGA缓存
        File httpCacheDir = new File(getCacheDir(), "http");
        long httpCacheSize = 1024 * 1024 * 128;
        try {
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SuperPlayerModel model = new SuperPlayerModel();

        model.url = hotLive.getPull_url();
        model.title = hotLive.getTitle();

        mSuperPlayerView.playWithModel(model);
        mSuperPlayerView.setPlayerViewCallback(this);
        mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_FULLSCREEN);
        mSuperPlayerView.mVodControllerSmall.show();
        initPage();


        HttpUtils.getInstance().getLiveInfo(hotLive.getLiveid(), new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
            }
        });

        if (hotLive.getRoom_type().equals("2")) {
            mPresenter.getTimeBilling(hotLive.getLiveid());
        }

        changeScreen();
        //   removeButMemberInfo();
        mSuperPlayerView.mVodControllerLarge.overage_tv.setText(MyUserInstance.getInstance().getUserinfo().getGold());
        mSuperPlayerView.mVodControllerLarge.setGotoCharge(new TCVodControllerLarge.gotoCharge() {
            @Override
            public void goCharge() {
                AppManager.getAppManager().startActivity(ToPayActivity.class);

            }

            @Override
            public void showGiftlist() {
                showGiftList2();
            }
        });
        if (MyUserInstance.getInstance().hasToken()) {
            mSuperPlayerView.mVodControllerLarge.gift_iv.setClickable(true);
            mSuperPlayerView.mVodControllerLarge.danmu_edit.setEnabled(true);
        } else {
            mSuperPlayerView.mVodControllerLarge.gift_iv.setClickable(false);
            mSuperPlayerView.mVodControllerLarge.danmu_edit.setEnabled(false);
        }
    }

    public void showGiftList() {
        if (null == chatGiftDialogFragment) {
            chatGiftDialogFragment = new ChatGiftDialogFragment();
            chatGiftDialogFragment.setOnSendGiftFinish(this);
        }
        chatGiftDialogFragment.show(getSupportFragmentManager(), "ChatGiftDialogFragment");
    }

    public void showGiftList2() {
        if (null == chatGiftDialogHorizontalFragment) {
            chatGiftDialogHorizontalFragment = new ChatGiftDialogHorizontalFragment();
            chatGiftDialogHorizontalFragment.setOnSendGiftFinish(this);
        }
        chatGiftDialogHorizontalFragment.show(getSupportFragmentManager(), "ChatGiftDialogFragment");
    }


    @Override
    protected void initView() {
        mPresenter = new SuperPlayerPresenter();
        mPresenter.attachView(this);
        hideTitle(true);

        setVipInFinishListener(new VipInFinishListener() {
            @Override
            public void vipinfinish() {
                if (mGifQueue.size() > 0) {
                    getVipIn(mGifQueue.poll(), vip_in_type);
                }
            }
        });
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);

                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;

                if (diff != 0) {
                 */
/*   if (contentView.getPaddingBottom() != diff) {
                        //   contentView.setPadding(0, 0, 0, diff);
                    }*//*


                    if (diff < 0) {

                        if (is_show_input) {
                            if (chatFragment.newVideoInputDialogFragment6 != null) {
                                is_show_input = true;
                                chatFragment.newVideoInputDialogFragment6.dismiss();
                            }
                        }
                    } else {
                        if (!is_show_input) {
                            if (chatFragment != null) {
                                if (chatFragment.newVideoInputDialogFragment6 != null) {
                                    chatFragment.newVideoInputDialogFragment6.openInput();
                                    is_show_input = false;
                                }
                            }
                        }
                    }


                } else {
                    if (contentView.getPaddingBottom() != 0) {
                        //    contentView.setPadding(0, 0, 0, 0);
                    }
                }
            }
        };
    }


    private void initPage() {
        View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
        //绑定点击事件
        myViewPager.setOnPageChangeListener(new MyPagerChangeListener());
        //把Fragment添加到List集合里面
        chatFragment = new ChatFragment(new ShowGift() {

            @Override
            public void show(GiftData data) {
                //  mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId());
                if (hotLive.getPkinfo() == null | hotLive.getPklive() == null) {
                    mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "", "", "");
                } else if (hotLive.getPkinfo() != null & hotLive.getPklive() != null) {
                    if (hotLive.getPkinfo().getHome_anchorid() == Integer.parseInt(hotLive.getAnchorid())) {
                        mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "", String.valueOf(hotLive.getPkinfo().getId()), "1");
                    } else {
                        mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "", String.valueOf(hotLive.getPkinfo().getId()), "2");
                    }

                }


            }

            @Override
            public void setGift(ArrayList<GiftData> giftList) {
                mSuperPlayerView.setGiftList(giftList);
            }
        }, new SendText() {
            @Override
            public void send(String Text) {
                TIMMessage msg = new TIMMessage();
                TIMCustomElem elem = new TIMCustomElem();
                Message message = new Message();
                if (Text.equals("关注了主播")) {
                    message.setAction("RoomAttentAnchor");
                } else {
                    message.setAction("RoomMessage");
                }
                MessageData messageData = new MessageData();

                MessageData.MessageChat messageChat = new MessageData.MessageChat();
                messageChat.setLevel(MyUserInstance.getInstance().getUserinfo().getUser_level());
                messageChat.setNick_name(MyUserInstance.getInstance().getUserinfo().getNick_name());
                messageChat.setMessage(Text);
                MessageData.MessageChat.Sender sender = new MessageData.MessageChat.Sender();
                sender.setAvatar(MyUserInstance.getInstance().getUserinfo().getAvatar());
                if (MyUserInstance.getInstance().hasToken()) {
                    sender.setId(MyUserInstance.getInstance().getUserinfo().getId());
                    sender.setUser_level(Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getUser_level()));
                } else {
                    sender.setId("0");
                    sender.setUser_level(1);
                }
                if (chatFragment.is_manager == false) {
                    messageChat.setIs_manager(0);
                } else {
                    messageChat.setIs_manager(1);
                }


                if (chatFragment.isIs_first()) {
                    //首次进入直播间
                    //是否是守護
                    chatFragment.setIs_first(false);
                    if (MyUserInstance.getInstance().isGuardianFirst(hotLive.getAnchorid())) {
                        messageChat.setIs_guardian(1);
                    } else {
                        messageChat.setIs_guardian(0);
                    }
                } else {
                    //非首次
                    if (MyUserInstance.getInstance().isGuardian(chatFragment.getGuardianInfos())) {
                        messageChat.setIs_guardian(1);
                    } else {
                        messageChat.setIs_guardian(0);
                    }
                }

                sender.setIs_anchor(MyUserInstance.getInstance().getUserinfo().getIs_anchor());
                sender.setNick_name(MyUserInstance.getInstance().getUserinfo().getNick_name());

                sender.setVip_date(MyUserInstance.getInstance().getUserinfo().getVip_date());

                sender.setVip_level(Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getVip_level()));
                messageChat.setSender(sender);

                messageData.setChat(messageChat);
                if (!TextUtils.isEmpty(MyUserInstance.getInstance().getUserinfo().getVip_date())) {
                    if (System.currentTimeMillis() < DateUtil.date2TimeStamp(MyUserInstance.getInstance().getUserinfo().getVip_date())) {
                        messageChat.setIsVip(true);
                    } else {
                        messageChat.setIsVip(false);
                    }
                } else {
                    messageChat.setIsVip(false);
                }
                message.setData(messageData);
                String json = new Gson().toJson(message);
                elem.setData(json.getBytes());
                if (msg.addElement(elem) != 0) {
                    Log.d("addElement", "addElement failed");
                    return;
                }
                conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
                    @Override
                    public void onError(int i, String s) {
                        Log.d("i", i + "");
                        Log.d(" s", s + "");
                    }

                    @Override
                    public void onSuccess(TIMMessage timMessage) {
                        for (int i = 0; i < timMessage.getElementCount(); i++) {
                            TIMCustomElem elem = (TIMCustomElem) msg.getElement(i);
                            String json = new String(elem.getData());
                            Gson gson = new Gson();
                            Message res = gson.fromJson(json, Message.class);
                            if (res.getAction().equals("RoomMessage") || res.getAction().equals("RoomAttentAnchor")) {
                                chatFragment.setCaht(res);
                                if (res.getData().getChat().isVip() & res.getData().getChat().getMessage().equals("进入直播间")) {
                                    mGifQueue.offer(res);
                                    if (mGifQueue.size() == 1) {
                                        if (!is_show_vip) {
                                            getVipIn(mGifQueue.poll(), vip_in_type);
                                        }
                                    }
                                }
                                if (DAN_MU_FLAG) {
                                    mSuperPlayerView.addDanmu(res.getData().getChat().getMessage());
                                }
                            }
                        }
                    }
                });
            }
        });
        chatFragment.setHotLive(hotLive);


        TIMGroupManager.getInstance().applyJoinGroup("LIVEROOM_" + hotLive.getAnchorid(), "some reason", new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //接口返回了错误码 code 和错误描述 desc，可用于原因
                //错误码 code 列表请参见错误码表
                Log.e("applyJoinGroup", "applyJoinGroup err code = " + code + ", desc = " + desc);
            }

            @Override
            public void onSuccess() {
                Log.i("applyJoinGroup", "applyJoinGroup success");
            }
        });

        //群组 ID
        conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,      //会话类型：群组
                "LIVEROOM_" + hotLive.getAnchorid());
        timMessageListener = new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                for (TIMMessage msg : list) {
                    for (int i = 0; i < msg.getElementCount(); i++) {
                        if (msg.getElement(i).getType() != TIMElemType.GroupSystem) {
                            if (!msg.getConversation().getPeer().equals("LIVEROOM_" + hotLive.getAnchorid())) {
                                break;
                            }
                            TIMCustomElem elem = (TIMCustomElem) msg.getElement(i);
                            String json = new String(elem.getData());
                            JSONObject jsonObject = JSON.parseObject(json);
                            switch (jsonObject.getString("Action")) {
                                case "LiveGroupMemberJoinExit":
                                    TIMGroupManager.getInstance().getGroupMembers("LIVEROOM_" + hotLive.getAnchorid(), new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
                                        @Override
                                        public void onError(int i, String s) {
                                        }

                                        @Override
                                        public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                                            Log.e("applyJoinGroup", "applyJoinGroup err code = " + timGroupMemberInfos.size());

                                        }
                                    });
                                    break;
                                case "LiveFinished":
                                    if (!SuperPlayerActivity2.this.isFinishing()) {
                                        builder = new AlertDialog.Builder(SuperPlayerActivity2.this).setTitle("直播结束").setCancelable(false)
                                                .setMessage("当前直播已经结束,主播已下播").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        //ToDo: 你想做的事情
                                                        finish();
                                                    }
                                                });
                                        builder.create().show();
                                    }

                                    break;
                                default:
                                    Gson gson = new Gson();
                                    Message res = gson.fromJson(json, Message.class);
                                    if (res.getAction().equals("RoomMessage") || res.getAction().equals("RoomAttentAnchor")) {
                                        if (res.getData().getChat().isVip() & res.getData().getChat().getMessage().equals("进入直播间")) {
                                            mGifQueue.offer(res);
                                            if (mGifQueue.size() == 1) {
                                                if (!is_show_vip) {
                                                    getVipIn(mGifQueue.poll(), vip_in_type);
                                                }
                                            }
                                        }

                                        if (!msg.isSelf()) {
                                            chatFragment.setCaht(res);
                                        }
                                        if (DAN_MU_FLAG) {
                                            mSuperPlayerView.addDanmu(res.getData().getChat().getMessage());
                                        }
                                    } else if (res.getAction().equals("GiftAnimation")) {
                                        if (null == group_1) {
                                            break;
                                        }
                                        if (null == tv_gift_info) {
                                            break;
                                        }
                                        chatFragment.setCaht(res);
                                        if (!SuperPlayerActivity2.this.isFinishing()) {
                                            showGift(initGift(res.getData().getGift()));
                                        }
                                    } else if (res.getAction().equals("RoomNotification")) {
                                        switch (res.getData().getNotify().getType()) {
                                            case "RoomNotifyTypeSetManager":
                                                //设置管理员
                                                chatFragment.setCaht(res);
                                                if ((res.getData().getNotify().getUser().getId() + "").equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                                                    chatFragment.talk_manager.setVisibility(View.VISIBLE);
                                                    chatFragment.is_manager = true;
                                                }
                                                break;
                                            case "RoomNotifyTypeCancelManager":
                                                //取消管理员
                                                chatFragment.setCaht(res);
                                                if ((res.getData().getNotify().getUser().getId() + "").equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                                                    chatFragment.talk_manager.setVisibility(View.GONE);
                                                    chatFragment.is_manager = false;
                                                }
                                                break;
                                            case "RoomNotifyTypeGuardAnchor":

                                                chatFragment.setCaht(res);
                                                break;
                                        }


                                    }
                                    break;
                            }


                        }
                    }

                }
                return false;
            }
        };
        TIMManager.getInstance().addMessageListener(timMessageListener);


        anchorFragment = new AnchorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("anchor", hotLive.getAnchor());
        bundle.putString("roomid", hotLive.getLiveid());
        anchorFragment.setArguments(bundle);

        contributionFragment = new ContributionFragment();
        bundle.putString("id", hotLive.getLiveid());
        contributionFragment.setArguments(bundle);

        list = new ArrayList<>();
        list.add(chatFragment);
        list.add(anchorFragment);
        list.add(contributionFragment);

        adapter = new PalyTabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setOffscreenPageLimit(list.size());
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面
        checkAttent();

    }

    private void checkAttent() {
        if (MyUserInstance.getInstance().hasToken()) {
            HttpUtils.getInstance().checkAttent(hotLive.getAnchorid(), new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    hideLoading();
                    JSONObject jsonObject = JSON.parseObject(response.body());
                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        if (jsonObject1.getString("attented").equals("0")) {
                            attention_tv.setText("关注");
                        } else {
                            attention_tv.setText("已关注");
                        }
                    }
                }
            });
        } else {
            attention_tv.setText("关注");
        }

    }


    @Override
    public void onSendClick(ChatGiftBean bean, String count) {

        if (MyUserInstance.getInstance().getUserinfo().getGold() == null) {
            return;
        }
        if (MyUserInstance.getInstance().getUserinfo().getGold().equals("")) {
            return;
        }

        if (Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getGold()) > Integer.parseInt(bean.getPrice())) {
            // mPresenter.sendGift(count, hotLive.getAnchorid(), hotLive.getLiveid(), bean.getId() + "");
            if (hotLive.getPkinfo() == null | hotLive.getPklive() == null) {
                mPresenter.sendGift(count, hotLive.getAnchorid(), hotLive.getLiveid(), bean.getId() + "", "", "");
            } else if (hotLive.getPkinfo() != null & hotLive.getPklive() != null) {
                if (hotLive.getPkinfo().getHome_anchorid() == Integer.parseInt(hotLive.getAnchorid())) {
                    mPresenter.sendGift(count, hotLive.getAnchorid(), hotLive.getLiveid(), bean.getId() + "", String.valueOf(hotLive.getPkinfo().getId()), "1");
                } else {
                    mPresenter.sendGift(count, hotLive.getAnchorid(), hotLive.getLiveid(), bean.getId() + "", String.valueOf(hotLive.getPkinfo().getId()), "2");
                }

            }
        } else {
            ToastUtils.showT(WordUtil.getString(R.string.Gold_Not_Enough));

        }
    }


    */
/**
     * 设置一个ViewPager的侦听事件，当左右滑动ViewPager时菜单栏被选中状态跟着改变
     *//*

    public class MyPagerChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            if (arg0 == 0) {
                chatFragment.hideGift();
            }
            chat_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            line1.setVisibility(View.GONE);
            anchor_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            line2.setVisibility(View.GONE);
            contribution_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            line3.setVisibility(View.GONE);

            switch (arg0) {//状态改变时底部对应的字体颜色改变
                case 0:
                    chat_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    line1.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    anchor_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    line2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    contribution_tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    line3.setVisibility(View.VISIBLE);
                    break;

            }

        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mSuperPlayerView != null) {
            mSuperPlayerView.mVodControllerLarge.overage_tv.setText(MyUserInstance.getInstance().getUserinfo().getGold());
        }
        if (MyUserInstance.getInstance().hasToken()) {
            mSuperPlayerView.mVodControllerLarge.gift_iv.setClickable(true);
            mSuperPlayerView.mVodControllerLarge.danmu_edit.setEnabled(true);
        } else {
            mSuperPlayerView.mVodControllerLarge.gift_iv.setClickable(false);
            mSuperPlayerView.mVodControllerLarge.danmu_edit.setEnabled(false);
        }

        if (is_stop) {
            mSuperPlayerView.onResume();
        }
        if (mOrientationListener != null) {
            mOrientationListener.enable();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        mSuperPlayerView.onPause();
        is_stop = true;
        if (mOrientationListener != null) {
            mOrientationListener.disable();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpResponseCache cache = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            cache = HttpResponseCache.getInstalled();
            if (cache != null) {
                cache.flush();
            }
        }
        //创建回调
        TIMCallBack cb = new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.e("quitGroup", "quit group error");
            }

            @Override
            public void onSuccess() {
                Log.e("quitGroup", "quit group succ");
            }
        };
        TIMGroupManager.getInstance().quitGroup(
                "LIVEROOM_" + hotLive.getAnchorid(),  //群组 ID
                cb);      //回调
        if (timMessageListener != null) {
            TIMManager.getInstance().removeMessageListener(timMessageListener);
        }
        if (null != mGiftAnimViewHolder) {
            mGiftAnimViewHolder.cancelAllAnim();
        }
        if (builder != null) {
            if (builder.getContext() instanceof Activity) {
                if (!((Activity) builder.getContext()).isFinishing()) {
                    builder.show();
                }
            }
        }

        if (handler != null) {
            if (runnable != null) {
                handler.removeCallbacks(runnable);
            }

        }
    }


    @OnClick({R.id.attention_tv, R.id.chat_tv, R.id.anchor_tv, R.id.contribution_tv, R.id.iv_share})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attention_tv:
                if (MyUserInstance.getInstance().visitorIsLogin(SuperPlayerActivity2.this)) {
                    HttpUtils.getInstance().checkAttent(hotLive.getAnchorid(), new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                if (jsonObject1.getString("attented").equals("0")) {
                                    HttpUtils.getInstance().attentAnchor(hotLive.getAnchorid(), "1", new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            attention_tv.setText("已关注");
                                            chatFragment.attentionListener();
                                        }
                                    });
                                } else {
                                    HttpUtils.getInstance().attentAnchor(hotLive.getAnchorid(), "0", new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {
                                            attention_tv.setText("关注");
                                        }
                                    });

                                }
                            }
                        }
                    });
                }
                break;
            case R.id.chat_tv:
                myViewPager.setCurrentItem(0);
                break;
            case R.id.anchor_tv:
                myViewPager.setCurrentItem(1);
                break;
            case R.id.contribution_tv:
                myViewPager.setCurrentItem(2);
                break;
            case R.id.iv_share:
                if (MyUserInstance.getInstance().visitorIsLogin(this)) {
                    Glide.with(this).load(hotLive.getThumb()).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            ShareDialog.Builder builder = new ShareDialog.Builder(SuperPlayerActivity2.this);
                            builder.setShare_url(MyUserInstance.getInstance().getUserConfig().getConfig().getShare_live_url() + hotLive.getAnchorid());
                            builder.create().show();
                            builder.showBottom2();
                            //builder.setImage_url(my_trend.getUser());
                            builder.setContent(hotLive.getTitle());
                            builder.setTitle("推荐你看这个直播");
                            builder.hideCollect();
                            builder.setTumb(drawableToBitmap(resource));
                            builder.setType("1");
                            builder.setId(hotLive.getAnchorid());


                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onStartFullScreenPlay() {
        // 隐藏其他元素实现全屏
        DAN_MU_FLAG = true;
        vip_in_type = 2;
        title_bar.setVisibility(View.GONE);
        myViewPager.setVisibility(View.GONE);
        chatFragment.hideGift();
        if (chatGiftDialogFragment != null) {
            chatGiftDialogFragment.dismiss();
        }
        if (chatGiftDialogHorizontalFragment != null) {
            chatGiftDialogHorizontalFragment.dismiss();
        }
    }

    @Override
    public void onStopFullScreenPlay() {
        // 恢复原有元素
        DAN_MU_FLAG = false;
        vip_in_type = 1;
        title_bar.setVisibility(View.VISIBLE);
        myViewPager.setVisibility(View.VISIBLE);
        mSuperPlayerView.hideGiftList();
        if (chatGiftDialogFragment != null) {
            chatGiftDialogFragment.dismiss();
        }
        if (chatGiftDialogHorizontalFragment != null) {
            chatGiftDialogHorizontalFragment.dismiss();
        }
    }

    @Override
    public void onClickFloatCloseBtn() {
        // 点击悬浮窗关闭按钮，那么结束整个播放
        if (null != mSuperPlayerView) {
            mSuperPlayerView.resetPlayer();
        }

        finish();
    }

    @Override
    public void onBackPressed() {
        if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FULLSCREEN) {
            mSuperPlayerView.goBack();
        } else {
            if (null != mSuperPlayerView) {
                mSuperPlayerView.resetPlayer();
            }

            super.onBackPressed();
        }

    }

    @Override
    public void onClickSmallReturnBtn() {

    }


    @Override
    public void onStartFloatWindowPlay() {
    }

    //显示礼物
    @Override
    public void showGift(GiftData data) {
        if (TextUtils.isEmpty(MyUserInstance.getInstance().getUserinfo().getGold()) &&
                Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getGold()) > Integer.parseInt(data.getPrice())) {
            //  mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "");
            if (hotLive.getPkinfo() == null | hotLive.getPklive() == null) {
                mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "", "", "");
            } else if (hotLive.getPkinfo() != null & hotLive.getPklive() != null) {
                if (hotLive.getPkinfo().getHome_anchorid() == Integer.parseInt(hotLive.getAnchorid())) {
                    mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "", String.valueOf(hotLive.getPkinfo().getId()), "1");
                } else {
                    mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "", String.valueOf(hotLive.getPkinfo().getId()), "2");
                }

            }
        } else {
            ToastUtils.showT(WordUtil.getString(R.string.Gold_Not_Enough));

        }

    }


    public void showGift(ChatReceiveGiftBean giftBean) {

        if (mGiftAnimViewHolder == null) {
            mGiftAnimViewHolder = new GiftAnimViewHolder(this, root);
            mGiftAnimViewHolder.addToParent();
        }
        mGiftAnimViewHolder.showGiftAnim(giftBean);
    }

    @Override
    public void sendSuccess(String gold) {
        if (chatGiftDialogFragment != null) {
            chatGiftDialogFragment.setmCoin(Integer.parseInt(gold) + "");

        }
        if (chatGiftDialogHorizontalFragment != null) {
            chatGiftDialogHorizontalFragment.setmCoin(Integer.parseInt(gold) + "");
        }

        MyUserInstance.getInstance().getUserinfo().setGold(gold);
        mSuperPlayerView.mVodControllerLarge.overage_tv.setText(gold);
    }


    //隐藏窗口屏幕礼物列表
    @Override
    public void hideCahtGIftList() {
        chatFragment.hideGift();
    }


    public interface SendText {
        void send(String Text);
    }

    private Dialog dialog;


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

    }

    @Override
    public void showMessage(@NonNull String message) {

    }


    @Override
    public void timeBilling(BaseResponse baseResponse) {
        String temp = ((LinkedTreeMap) baseResponse.getData()).get("gold").toString();
        int now_gold = Double.valueOf(temp).intValue();
        int price = Integer.parseInt(hotLive.getPrice());

        if (now_gold < price) {
            LivePriceDialog.Builder builder = new LivePriceDialog.Builder(SuperPlayerActivity2.this);
            builder.create();
            builder.setOnSubmit(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SuperPlayerActivity2.this, ToPayActivity.class);
                    startActivity(intent);
                }
            });
            builder.setCanCancel(true);
            builder.livePriceDialog.show();

        }

        runnable = new Runnable() {
            @Override
            public void run() {
                if (now_gold >= price) {

                    mPresenter.getTimeBilling(hotLive.getLiveid());


                } else if (now_gold < price) {

                    mSuperPlayerView.onPause();
                    is_stop = false;
                    LivePriceDialog.Builder builder = new LivePriceDialog.Builder(SuperPlayerActivity2.this);
                    builder.create();
                    builder.setCancelHide(true);
                    builder.setContent("请充值后重新进入房间");
                    builder.setTitle("余额不足");
                    builder.setSubmitText("确定");
                    builder.setOnSubmit(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    });
                    builder.setCanCancel(false);
                    builder.livePriceDialog.show();
                }
            }
        };
        handler.postDelayed(runnable, 6000);


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    private void changeScreen() {
        mOrientationListener = new OrientationEventListener(this,
                SensorManager.SENSOR_DELAY_NORMAL) {
            @Override
            public void onOrientationChanged(int orientation) {
                Log.e("DEBUG_TAG", "Orientation changed to " + orientation);
                if (orientation > 350 || orientation < 10) { //0度


                    if (!is_vertical) {
                        mSuperPlayerView.mVodControllerLarge.mVodController.onBackPress(SuperPlayerConst.PLAYMODE_FULLSCREEN);
                        is_vertical = true;
                    }

                } else if (orientation > 80 && orientation < 100) { //90度


                    if (is_vertical) {
                        mSuperPlayerView.mVodControllerSmall.fullScreen();
                        is_vertical = false;
                    }
                } else if (orientation > 170 && orientation < 190) { //180度


                    if (!is_vertical) {
                        mSuperPlayerView.mVodControllerLarge.mVodController.onBackPress(SuperPlayerConst.PLAYMODE_FULLSCREEN);
                        is_vertical = true;
                    }

                } else if (orientation > 260 && orientation < 280) { //270度


                    if (is_vertical) {
                        mSuperPlayerView.mVodControllerSmall.fullScreen();
                        is_vertical = false;
                    }
                } else {
                    return;
                }
            }
        };

        mOrientationListener.enable();


    }

    */
/**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     *//*

    public final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}


*/
