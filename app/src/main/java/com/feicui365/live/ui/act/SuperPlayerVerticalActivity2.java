/*
package com.feicui365.live.ui.act;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.feicui365.live.BuildConfig;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.bean.Message;
import com.feicui365.live.bean.MessageData;
import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.dialog.ChatGiftDialogFragment;
import com.feicui365.live.dialog.LivePriceDialog;
import com.feicui365.live.interfaces.OnSendGiftFinish;
import com.feicui365.live.liveroom.MLVBHttpUtils;
import com.feicui365.live.liveroom.roomutil.http.HttpRequests;
import com.feicui365.live.liveroom.roomutil.http.HttpResponse;
import com.feicui365.live.model.entity.Anchor;
import com.feicui365.live.model.entity.BaseLiveInfo;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.ChatGiftBean;
import com.feicui365.live.model.entity.ChatReceiveGiftBean;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveInfo;
import com.feicui365.live.model.entity.Notify;
import com.feicui365.live.presenter.SuperPlayerPresenter;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.ui.fragment.ChatVerticalFragment;
import com.feicui365.live.ui.fragment.ChatVerticalNothingFragment;
import com.feicui365.live.ui.uiinterfae.ShowGift;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.DYLoadingView;
import com.feicui365.live.widget.GiftAnimViewHolder;
import com.feicui365.live.widget.PkProgressBar;
import com.nasinet.nasinet.utils.AppManager;
import com.nasinet.nasinet.utils.DipPxUtils;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
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
import com.tencent.imsdk.v2.V2TIMGroupInfoResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.liteav.demo.play.bean.GiftData;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import butterknife.BindView;
import cn.tillusory.sdk.TiSDK;
import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.InitStatus;
import cn.tillusory.sdk.bean.TiRotation;
import cn.tillusory.tiui.TiPanelLayout;

public class SuperPlayerVerticalActivity2 extends BaseMvpActivity<SuperPlayerPresenter> implements SuperPlayerContrat.View
        , SuperPlayerView.OnSuperPlayerViewCallback, OnSendGiftFinish {
    private AlertDialog.Builder builder;
    @BindView(R.id.spv_main)
    SuperPlayerView spv_main;
    @BindView(R.id.spv_sub)
    SuperPlayerView spv_sub;
    @BindView(R.id.atv_ply_v_viewpage)
    ViewPager myViewPager;

    @BindView(R.id.tv_gift_info)
    TextView tv_gift_info;
    @BindView(R.id.group_1)
    RelativeLayout group_1;
    @BindView(R.id.root)
    RelativeLayout root;

    @BindView(R.id.cv_join)
    CardView cv_join;
    @BindView(R.id.txcv_join)
    SuperPlayerView txcv_join;
    @BindView(R.id.iv_stop_join)
    ImageView iv_stop_join;
    @BindView(R.id.rl_loading)
    public RelativeLayout rl_loading;
    @BindView(R.id.dy_loading)
    public DYLoadingView dy_loading;
    @BindView(R.id.iv_loading)
    public ImageView iv_loading;


    @BindView(R.id.rl_play)
    public RelativeLayout rl_play;

    @BindView(R.id.tv_time_pk)
    public TextView tv_time_pk;
    @BindView(R.id.ppb_live)
    public PkProgressBar ppb_live;
    @BindView(R.id.ll_play_view)
    public LinearLayout ll_play_view;

    @BindView(R.id.siv_center)
    public SVGAImageView siv_center;

    @BindView(R.id.siv_left)
    public SVGAImageView siv_left;

    @BindView(R.id.siv_right)
    public SVGAImageView siv_right;
    @BindView(R.id.siv_start)
    public SVGAImageView siv_start;

    public ChatGiftDialogFragment chatGiftDialogFragment;
    public boolean is_show_input = false;
    private TiPanelLayout tiPanelLayout;
    private List<Fragment> list;
    private PalyTabFragmentPagerAdapter adapter;
    private ChatVerticalFragment chatFragment;
    private ChatVerticalNothingFragment myfragment;
    private HotLive hotLive;
    private TIMConversation conversation;
    private boolean DAN_MU_FLAG = false;
    private ConcurrentLinkedQueue<Message> mGifQueue = new ConcurrentLinkedQueue<>();
    private GiftAnimViewHolder mGiftAnimViewHolder;
    private boolean is_stop = false;
    private RoundedCorners roundedCorners = new RoundedCorners(10);
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
    private Handler handler = new Handler();
    private Runnable runnable;
    private TIMMessageListener timMessageListener;
    private TXLivePusher mLivePusher;
    private String push_url = "";
    public String MLVB_token = "";
    //美颜参数
    private int mopiLevel = 8;
    private int meibaiLevel = 5;
    private int hongrunLevel = 3;
    private String live_anchorId = "";
    private Anchor live_anchor;
    private boolean is_join = false;
    private boolean is_fisrt = true;
    private CountDownTimer pk_time;
    private int local_pk_second = 360000;
    private int local_punish_second = 120000;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;

    private Handler myhandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {

                case 2:

                    if (SuperPlayerVerticalActivity2.this.isFinishing()) {
                        return;
                    }

                    //  mPresenter.getAnchorLiveInfo(live_anchorId);
                    if (hotLive != null) {
                        mPresenter.getLiveBasicInfo(hotLive.getLiveid());
                    }

                    android.os.Message message2 = new android.os.Message();
                    message2.what = 2;
                    sendMessageDelayed(message2, 60000);

                    break;
                case 3:
                    tiPanelLayout = new TiPanelLayout(context).init(TiSDKManager.getInstance());
                    mLivePusher.setVideoProcessListener(new TXLivePusher.VideoCustomProcessListener() {
                        @Override
                        public int onTextureCustomProcess(int i, int i1, int i2) {
                            return TiSDKManager.getInstance().renderTexture2D(i, i1, i2, TiRotation.CLOCKWISE_ROTATION_0, false);
                        }

                        @Override
                        public void onDetectFacePoints(float[] floats) {

                        }

                        @Override
                        public void onTextureDestoryed() {
                            TiSDKManager.getInstance().destroy();
                        }
                    });
                    break;

            }

        }
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setAndroidNativeLightStatusBar(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_player_vertical2;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void getLiveInfo(BaseResponse<LiveInfo> baseResponse) {
        hotLive = baseResponse.getData().getLive();
        mPresenter.getAnchorInfo(hotLive.getAnchorid());
        if (is_fisrt) {
            is_fisrt = false;
            initFirst();
            LiveInfo first_liveInfo = baseResponse.getData();
            first_liveInfo.getLive().setAnchor(live_anchor);
            chatFragment.setLiveInfoFirst(first_liveInfo);

        }


    }

    @Override
    public void getLiveBasicInfo(BaseResponse<BaseLiveInfo> baseResponse) {
        if (chatFragment != null) {
            chatFragment.setLiveInfo(baseResponse.getData());
        }

    }

    private void initFirst() {
        //初始化播放器模型
        initPlayer(hotLive);

        //房间类型2开启计时
        if (hotLive.getRoom_type().equals("2")) {
            mPresenter.getTimeBilling(hotLive.getLiveid());
        }
        //开启VIP入场监听
        setVipInFinishListener(new VipInFinishListener() {
            @Override
            public void vipinfinish() {
                if (mGifQueue.size() > 0) {
                    getVipIn(mGifQueue.poll(), 3);
                }
            }
        });
        //连麦
        MLVBHttpUtils.sharedInstance().init();
        MLVBHttpUtils.sharedInstance().login(new HttpRequests.OnResponseCallback<HttpResponse.LoginResponse>() {
            @Override
            public void onResponse(int retcode, @Nullable String retmsg, @Nullable HttpResponse.LoginResponse data) {
                if (retcode == 0) {
                    if (data.token != null) {
                        MLVB_token = data.token;
                    }

                }
            }
        });
        TXLiveBase.getInstance().setLicence(this, BuildConfig.licenceURL, BuildConfig.licenceKey);

        //初始化IM
        TIMGroupManager.getInstance().applyJoinGroup("LIVEROOM_" + hotLive.getAnchorid(), "some reason", new TIMCallBack() {
            @Override
            public void onError(int code, String desc) {
                Log.e("applyJoinGroup", "applyJoinGroup err code = " + code + ", desc = " + desc);
            }

            @Override
            public void onSuccess() {
                Log.i("applyJoinGroup", "applyJoinGroup success");
            }
        });
        //群组 ID                                                               //会话类型：群组
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, "LIVEROOM_" + hotLive.getAnchorid());
        timMessageListener = new TIMMessageListener() {
            @Override
            public boolean onNewMessages(List<TIMMessage> list) {
                for (TIMMessage msg : list) {
                    for (int i = 0; i < msg.getElementCount(); i++) {
                        if (msg.getElement(i).getType() != TIMElemType.GroupSystem) {
                            if (msg.getConversation().getPeer().equals("LIVEROOM_" + hotLive.getAnchorid())) {
                                handleRoomMessage(msg, i);
                                break;
                            }
                        }
                    }

                }
                return false;
            }
        };
        TIMManager.getInstance().addMessageListener(timMessageListener);

        List groupIDList = new ArrayList<String>();
        groupIDList.add("LIVEROOM_" + hotLive.getAnchorid());
        V2TIMManager.getGroupManager().getGroupsInfo(groupIDList, new V2TIMValueCallback<List<V2TIMGroupInfoResult>>() {
            @Override
            public void onError(int i, String s) {
                Log.e("getGroupsInfo", "getGroupsInfo err code = " + i + " " + s);
            }

            @Override
            public void onSuccess(List<V2TIMGroupInfoResult> v2TIMGroupInfoResults) {
                if (v2TIMGroupInfoResults == null) {
                    return;
                }
                if (v2TIMGroupInfoResults.get(0) == null) {
                    return;
                }
                if (chatFragment != null) {
                    chatFragment.setChatGroupNum(String.valueOf(v2TIMGroupInfoResults.get(0).getGroupInfo().getMemberCount()));
                }

            }
        });

    }

    private void initPlayer(HotLive hotLive) {
        //PK或者非PK
        switch (hotLive.getPk_status()) {
            default:
                //隐藏副PK舞台
                spv_sub.setVisibility(View.GONE);
                //调整界面,主舞台全显示,隐藏PK条,播放区全屏
                //播放区全屏
                RelativeLayout.LayoutParams lp_rl_root = (RelativeLayout.LayoutParams) rl_play.getLayoutParams();
                lp_rl_root.setMargins(0, 0, 0, 0);
                rl_play.setLayoutParams(lp_rl_root);
                //隐藏PK条,时间计时器
                tv_time_pk.setVisibility(View.GONE);
                ppb_live.setVisibility(View.GONE);
                //填充播放参数
                SuperPlayerModel model = new SuperPlayerModel();


                model.url = hotLive.getPull_url();
                model.title = hotLive.getTitle();
                spv_main.playWithModel(model);
                spv_main.setPlayerViewCallback(this);
                spv_main.setVideoStartListener(new SuperPlayerView.VideoStartListener() {
                    @Override
                    public void videoStart() {
                        if (!SuperPlayerVerticalActivity2.this.isFinishing()) {
                            rl_loading.setVisibility(View.GONE);
                            dy_loading.stop();
                        }
                    }

                    @Override
                    public void videoFail() {

                    }
                });
                break;

            case 1:
                startPk();

                break;

        }
    }


    @Override
    protected void initView() {
        hideTitle(true);
        mPresenter = new SuperPlayerPresenter();
        mPresenter.attachView(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //初始化相关控件
        live_anchor = (Anchor) getIntent().getSerializableExtra("studio_info");
        if (live_anchor == null) {
            finish();
        }
        live_anchorId = live_anchor.getId();

        //SVGA播放器相关设置
        SVGAParser.Companion.shareParser().init(this);
        //初始化2个fragment页面
        initPage();
        rl_loading.setVisibility(View.VISIBLE);
        dy_loading.start();

        iv_stop_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.stopMlvbLink(hotLive.getAnchorid(), MyUserInstance.getInstance().getUserinfo().getId());
            }
        });


        android.os.Message message2 = new android.os.Message();
        message2.what = 2;
        myhandler.sendMessageDelayed(message2, 60000);
        mPresenter.getAnchorLiveInfo(live_anchorId);


        RelativeLayout.LayoutParams lp_pk_start = (RelativeLayout.LayoutParams) siv_start.getLayoutParams();
        lp_pk_start.height = MyUserInstance.getInstance().device_width / 4 * 3;
        lp_pk_start.width = MyUserInstance.getInstance().device_width / 4 * 3;
        siv_start.setLayoutParams(lp_pk_start);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
    public void onStartFullScreenPlay() {

    }

    @Override
    public void onStopFullScreenPlay() {

    }

    @Override
    public void onClickFloatCloseBtn() {

    }

    @Override
    public void onClickSmallReturnBtn() {

    }

    @Override
    public void onStartFloatWindowPlay() {

    }


    public void showGift(ChatReceiveGiftBean giftBean) {

        if (mGiftAnimViewHolder == null) {
            mGiftAnimViewHolder = new GiftAnimViewHolder(this, root);
            mGiftAnimViewHolder.addToParent();
        }
        mGiftAnimViewHolder.showGiftAnim(giftBean);
    }

    @Override
    public void hideCahtGIftList() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", this.toString());
        if (is_stop) {
            spv_main.onResume();
            spv_sub.onResume();
        }
        if (onGlobalLayoutListener != null) {
            getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (spv_main != null) {
            spv_main.onPause();
        }
        if (spv_main != null) {
            spv_sub.onPause();
        }
        is_stop = true;
        if (onGlobalLayoutListener != null) {
            getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }


    public void exit() {

        if (pk_time != null) {
            pk_time.cancel();
        }
        if (hotLive != null) {
            //退群
            TIMGroupManager.getInstance().quitGroup(
                    "LIVEROOM_" + hotLive.getAnchorid(),  //群组 ID
                    null);
        }


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

        if (mLivePusher != null) {
            mLivePusher.stopPusher();
            mLivePusher.stopCameraPreview(true);
            mLivePusher = null;
        }
        if (spv_main != null) {
            spv_main.resetPlayer();
        }
        if (spv_sub != null) {
            spv_sub.resetPlayer();
        }
        myhandler.removeMessages(2);
        Log.e("EXIT", "extid");
    }

    @Override
    public void finish() {
        super.finish();
        exit();
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;
                if (diff > 0) {
                    //这个时候软键盘是弹起来的
                    //软键盘显示
                    if (chatFragment.newVideoInputDialogFragment5 == null) {
                        return;
                    }
                    //关闭下面栏位
                    if (!is_show_input) {
                        if (chatFragment != null) {
                            is_show_input = true;
                            chatFragment.ll_bottom.setVisibility(View.GONE);

                            chatFragment.newVideoInputDialogFragment5.openInput();


                        }
                    }

                } else {
                    //这个时候是收进去的
                    //只有确定软键盘是打开的时候
                    if (chatFragment.newVideoInputDialogFragment5 == null) {
                        return;
                    }
                    if (is_show_input) {
                        if (chatFragment != null) {
                            chatFragment.ll_bottom.setVisibility(View.VISIBLE);
                            chatFragment.hideInput();
                            is_show_input = false;
                        }
                    }
                }
            }
        };
    }

    private void initPage() {
        View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        onGlobalLayoutListener = getGlobalLayoutListener(decorView, contentView);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        //初始化ChatVertialFragment
        //chatFragment = new ....
        chatFragment = new ChatVerticalFragment(new ShowGift() {

            @Override
            public void show(GiftData data) {
                if (hotLive == null) {
                    return;
                }

                if (hotLive.getPkinfo() == null | hotLive.getPklive() == null) {
                    mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "", "", "");
                } else if (hotLive.getPkinfo() != null & hotLive.getPklive() != null) {
                    if (hotLive.getPkinfo().getHome_anchorid() == Integer.parseInt(hotLive.getAnchorid())) {
                        mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "", String.valueOf(hotLive.getPkinfo().getId()), "1");
                    } else {
                        mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "", String.valueOf(hotLive.getPkinfo().getId()), "2");
                    }

                }
                //  mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getAnchorid(), data.getId());
            }

            @Override
            public void setGift(ArrayList<GiftData> giftList) {
                spv_main.setGiftList(giftList);
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

                if (chatFragment.is_manager == false) {
                    messageChat.setIs_manager(0);
                } else {
                    messageChat.setIs_manager(1);
                }


                MessageData.MessageChat.Sender sender = new MessageData.MessageChat.Sender();
                sender.setAvatar(MyUserInstance.getInstance().getUserinfo().getAvatar());
                if (MyUserInstance.getInstance().hasToken()) {
                    sender.setId(MyUserInstance.getInstance().getUserinfo().getId());
                    sender.setUser_level(Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getUser_level()));
                } else {
                    sender.setId("0");
                    sender.setUser_level(1);
                }

                sender.setIs_anchor(MyUserInstance.getInstance().getUserinfo().getIs_anchor());
                sender.setNick_name(MyUserInstance.getInstance().getUserinfo().getNick_name());
                sender.setVip_date(MyUserInstance.getInstance().getUserinfo().getVip_date());
                sender.setVip_level(Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getVip_level()));
                messageChat.setSender(sender);
                messageData.setChat(messageChat);


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
                                //先确定是不是进入直播间的消息
                                if (res.getData().getChat().getMessage().equals("进入直播间")) {
                                    MessageData.MessageChat.Sender sender = res.getData().getChat().getSender();
                                    MessageData.MessageChat messageChat = res.getData().getChat();
                                    if (sender != null | messageChat != null) {
                                        //判断这个人的相关状态
                                        if (isVip(sender) | messageChat.getIs_guardian() == 1) {
                                            //1,vip
                                            mGifQueue.offer(res);
                                            if (mGifQueue.size() == 1) {
                                                if (!is_show_vip) {
                                                    getVipIn(mGifQueue.poll(), 3);
                                                }
                                            }
                                        }
                                    }
                                }
                                if (DAN_MU_FLAG) {
                                    spv_main.addDanmu(res.getData().getChat().getMessage());
                                }
                            }
                        }
                    }
                });
            }
        });
        chatFragment.setJoinAnchor(new ChatVerticalFragment.JoinAnchor() {
            @Override
            public void join() {
                //请求连麦
                if (hotLive.getLink_on().equals("0")) {
                    ToastUtils.showT("主播尚未开启连麦");
                } else if (hotLive.getLink_status().equals("1")) {
                    ToastUtils.showT("主播正在连麦中");
                } else {
                    mPresenter.requestMlvbLink(hotLive.getAnchorid(), MLVB_token);
                    ToastUtils.showT("申请连麦中,请等待主播回应");
                }

            }

            @Override
            public void stopjoin() {
                if (is_join) {
                    mPresenter.stopMlvbLink(hotLive.getAnchorid(), MyUserInstance.getInstance().getUserinfo().getId());
                }

            }
        });


        myfragment = new ChatVerticalNothingFragment();
        //把Fragment添加到List集合里面
        list = new ArrayList<>();
        list.add(myfragment);
        list.add(chatFragment);

        adapter = new PalyTabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setOffscreenPageLimit(list.size());
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(1);  //初始化显示第一个页面


    }


    @Override
    public void sendSuccess(String gold) {
        chatGiftDialogFragment.setmCoin(Integer.parseInt(gold) + "");
        MyUserInstance.getInstance().getUserinfo().setGold(gold);

    }


    public interface SendText {
        void send(String Text);
    }


    @Override
    public void timeBilling(BaseResponse baseResponse) {
        String temp = ((LinkedTreeMap) baseResponse.getData()).get("gold").toString();
        int now_gold = Double.valueOf(temp).intValue();
        int price = Integer.parseInt(hotLive.getPrice());

        if (now_gold < price) {
            LivePriceDialog.Builder builder = new LivePriceDialog.Builder(SuperPlayerVerticalActivity2.this);
            builder.create();
            builder.setOnSubmit(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SuperPlayerVerticalActivity2.this, ToPayActivity.class);
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

                    spv_main.onPause();
                    is_stop = false;
                    LivePriceDialog.Builder builder = new LivePriceDialog.Builder(SuperPlayerVerticalActivity2.this);
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
        handler.postDelayed(runnable, 60000);
    }


    private void handleRoomMessage(TIMMessage msg, int i) {
        TIMCustomElem elem = (TIMCustomElem) msg.getElement(i);
        String json = new String(elem.getData());
        JSONObject jsonObject = JSON.parseObject(json);
        switch (jsonObject.getString("Action")) {
            case "LiveGroupMemberJoinExit":
                TIMGroupManager.getInstance().getGroupMembers(hotLive.getChatroomid(), new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
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
                if (!SuperPlayerVerticalActivity2.this.isFinishing()) {
                    stopPushJoin();
                    builder = new AlertDialog.Builder(SuperPlayerVerticalActivity2.this).setTitle("直播结束").setCancelable(false)
                            .setMessage("当前直播已经结束,主播已下播").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //ToDo: 你想做的事情
                                    finish();
                                }
                            });
                    builder.show();
                }


                break;
            case "ExplainingGoods":
                JSONObject shop_item = jsonObject.getJSONObject("Data").getJSONObject("goods");
                Good shopItem = JSON.toJavaObject(shop_item, Good.class);
                if (shopItem.getId() == null) {
                    break;
                }

                if (chatFragment != null) {
                    chatFragment.rl_shop_item.setVisibility(View.VISIBLE);
                    chatFragment.now_chose = shopItem;
                    String[] images = shopItem.getThumb_urls().split(",");
                    Glide.with(SuperPlayerVerticalActivity2.this).applyDefaultRequestOptions(options).asBitmap().load(images[0]).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            chatFragment.iv_item_acvatar.setImageBitmap(resource);
                        }
                    });
                    chatFragment.tv_shop_price.setText("￥ " + shopItem.getPrice());
                }
                break;
            default:
                Gson gson = new Gson();
                Message res = gson.fromJson(json, Message.class);
                if (res.getAction().equals("RoomMessage") || res.getAction().equals("RoomAttentAnchor")) {

                    //先确定是不是进入直播间的消息
                    if (res.getData().getChat().getMessage().equals("进入直播间")) {
                        MessageData.MessageChat.Sender sender = res.getData().getChat().getSender();
                        MessageData.MessageChat messageChat = res.getData().getChat();
                        if (sender != null | messageChat != null) {
                            //判断这个人的相关状态
                            if (isVip(sender) | messageChat.getIs_guardian() == 1) {
                                //1,vip
                                mGifQueue.offer(res);
                                if (mGifQueue.size() == 1) {
                                    if (!is_show_vip) {
                                        getVipIn(mGifQueue.poll(), 3);
                                    }
                                }
                            }
                        }
                    }


                    if (!msg.isSelf()) {
                        chatFragment.setCaht(res);
                    }
                    if (DAN_MU_FLAG) {
                        spv_main.addDanmu(res.getData().getChat().getMessage());
                    }
                } else if (res.getAction().equals("GiftAnimation")) {

                    if (null == group_1) {
                        break;
                    }
                    if (null == tv_gift_info) {
                        break;
                    }
                    chatFragment.setCaht(res);
                    if (!SuperPlayerVerticalActivity2.this.isFinishing()) {
                        showGift(initGift(res.getData().getGift()));
                    }
                } else if (res.getAction().equals("RoomNotification")) {
                    //房管
                    if (res.getData().getNotify() != null) {
                        Notify notify = res.getData().getNotify();
                        switch (notify.getType()) {
                            case "RoomNotifyTypeSetManager":
                                //设置管理员
                                chatFragment.setCaht(res);
                                if ((res.getData().getNotify().getUser().getId() + "").equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                                    chatFragment.ll_no_talk.setVisibility(View.VISIBLE);
                                    chatFragment.is_manager = true;
                                }
                                break;
                            case "RoomNotifyTypeCancelManager":
                                //取消管理员
                                chatFragment.setCaht(res);
                                if ((res.getData().getNotify().getUser().getId() + "").equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                                    chatFragment.ll_no_talk.setVisibility(View.GONE);
                                    chatFragment.is_manager = false;
                                }
                                break;
                            //小主播向大主播申请连麦
                            case "RoomNotificationReciveLinkRequest":
                                //小主播向大主播申请连麦

                                break;
                            case "RoomNotificationAcceptLinkRequest":
                                //通知用户连麦申请被接受
                                if (notify.getTouid().equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                                    starPushJoin();
                                    spv_main.mLivePlayer.stopPlay(true);
                                    spv_main.mLivePlayer.startPlay(hotLive.getAcc_pull_url(), TXLivePlayer.PLAY_TYPE_LIVE_RTMP_ACC);
                                }
                                break;
                            case "RoomNotificationRefuseLinkRequest":
                                //通知用户连麦申请被拒接
                                if (notify.getTouid().equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                                    ToastUtils.showT("主播拒绝了你的连麦请求");
                                }

                                break;
                            case "RoomNotificationStopLink":
                                //通知主播/用户连麦结束
                                if (notify.getTouid().equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                                    stopPushJoin();
                                }
                                break;
                            case "RoomNotifyTypeLinkOn":
                                //主播开启连麦功能通知
                                chatFragment.setCaht(res);
                                hotLive.setLink_on("1");
                                break;
                            case "RoomNotifyTypeLinkOff":
                                //主播关闭连麦功能通知
                                chatFragment.setCaht(res);
                                hotLive.setLink_on("0");
                                break;
                            case "RoomNotifyTypePkScoreChange":
                                //分数变化通知
                                if (notify.getPkinfo() == null) {
                                    return;
                                }

                                if (ppb_live.getVisibility() == View.VISIBLE) {
                                    ppb_live.cpmputerValue(notify.getPkinfo().getHome_score(), notify.getPkinfo().getAway_score());
                                }
                                hotLive.getPkinfo().setAway_score(notify.getPkinfo().getAway_score());
                                hotLive.getPkinfo().setHome_score(notify.getPkinfo().getHome_score());
                                break;
                            case "RoomNotifyTypePkStart":
                                //开启一轮新的PK
                                if (notify.getPkinfo() == null) {
                                    return;
                                }
                                if (notify.getPklive() == null) {
                                    return;
                                }
                                hotLive.setPkinfo(notify.getPkinfo());
                                hotLive.setPklive(notify.getPklive());
                                hotLive.setPk_status(1);
                                hotLive.setPkid(notify.getPkinfo().getId());
                                startPk();
                                break;

                            case "RoomNotifyTypePkEnd":
                                //开启一轮新的PK
                                endPk();
                                break;
                            case "RoomNotifyTypeGuardAnchor":

                                chatFragment.setCaht(res);
                                break;
                        }
                    }

                }
                break;
        }

    }


    //申请
    @Override
    public void requestMlvbLink(BaseResponse baseResponse) {
        //获得自己的推流地址
        LinkedTreeMap linkedTreeMap = (LinkedTreeMap) baseResponse.getData();
        push_url = (String) linkedTreeMap.get("push_url");

    }

    //停止
    @Override
    public void stopMlvbLink(BaseResponse baseResponse) {
        stopPushJoin();
        if (push_url != null) {
            push_url = "";
        }
        is_join = false;
    }


    //主播同意连麦,打开本地预览 开始推送
    private void starPushJoin() {
        //打开本地预览,推送本地画面
        is_join = true;
        if (push_url == null) {
            return;
        }
        if (push_url.equals("")) {
            return;
        }

        mLivePusher = new TXLivePusher(this);
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.setSpecialRatio(0.5f);
        mLivePusher.startCameraPreview(txcv_join.mTXCloudVideoView);
        mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_LINKMIC_SUB_PUBLISHER, false, false);
        int ret = mLivePusher.startPusher(push_url);
        if (0 != ret) {
            ToastUtils.showT("推流失败");
            stopPushJoin();
        } else {
            cv_join.setVisibility(View.VISIBLE);
            txcv_join.setJoinListener(new SuperPlayerView.JoinListener() {
                @Override
                public void joinStart() {

                }

                @Override
                public void joinClose() {

                    mPresenter.stopMlvbLink(hotLive.getAnchorid(), MyUserInstance.getInstance().getUserinfo().getId());
                    stopPushJoin();
                }
            });

            initBeautifull();
        }

    }

    private void stopPushJoin() {
        is_join = false;
        if (mLivePusher == null) {
            return;
        }
        if (cv_join == null) {
            return;
        }
        if (spv_main == null) {
            return;
        }
        if (spv_main.mLivePlayer == null) {
            return;
        }
        cv_join.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SuperPlayerVerticalActivity2.this.isFinishing()) {
                    mLivePusher.stopPusher();
                    mLivePusher.stopCameraPreview(true);
                    mLivePusher = null;
                    spv_main.mLivePlayer.stopPlay(true);
                    spv_main.mLivePlayer.startPlay(hotLive.getPull_url(), TXLivePlayer.PLAY_TYPE_LIVE_FLV);
                }
            }
        }, 200);


    }


    //同意连麦给与默认美颜
    private void initBeautifull() {
        if (MyUserInstance.getInstance().getUserConfig().getConfig().getBeauty_channel().equals("1")) {
            TiSDK.init(MyUserInstance.getInstance().getUserConfig().getConfig().getTisdk_key(), this, new TiSDK.TiInitCallback() {
                @Override
                public void finish(InitStatus initStatus) {
                    if (initStatus.getCode() == 100) {
                        android.os.Message message = new android.os.Message();
                        message.what = 3;
                        myhandler.sendMessage(message);
                    }
                }
            });

        } else {
            mLivePusher.setBeautyFilter(0, mopiLevel, meibaiLevel, hongrunLevel);
        }
    }

    @Override
    public void onBackPressed() {
        if (is_join) {
            mPresenter.stopMlvbLink(hotLive.getAnchorid(), MyUserInstance.getInstance().getUserinfo().getId());

        }

        super.onBackPressed();
    }


    private void initPayDialog(String Content, String title, View.OnClickListener onClickListener) {
        LivePriceDialog.Builder builder = new LivePriceDialog.Builder(context);
        builder.create();

        builder.setContent(Content);
        builder.setTitle(title);
        builder.setSubmitText(WordUtil.getString(R.string.Submit));
        builder.setOnSubmit(onClickListener);
        builder.setCanCancel(true);
        builder.livePriceDialog.show();
    }

    */
/*
     * 礼物相关开始
     *//*

    //展示礼物动画
    public void showGiftList() {
        if (null == chatGiftDialogFragment) {
            chatGiftDialogFragment = new ChatGiftDialogFragment();
            chatGiftDialogFragment.setOnSendGiftFinish(this);
        }
        chatGiftDialogFragment.show(getSupportFragmentManager(), "ChatGiftDialogFragment");
    }


    */
/**
     * 提交礼物请求
     *
     * @param data
     *//*

    @Override
    public void showGift(GiftData data) {
        if (TextUtils.isEmpty(MyUserInstance.getInstance().getUserinfo().getGold()) &&
                Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getGold()) > Integer.parseInt(data.getPrice())) {
            //mPresenter.sendGift("1", hotLive.getAnchorid(), hotLive.getLiveid(), data.getId() + "");
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

            initPayDialog("您的金币不足,是否前往充值?", "金币不足", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppManager.getAppManager().startActivity(ToPayActivity.class);
                }
            });
        }

    }


    //礼物点击
    @Override
    public void onSendClick(ChatGiftBean bean, String count) {
        if (MyUserInstance.getInstance().getUserinfo().getGold() == null) {
            return;
        }
        if (MyUserInstance.getInstance().getUserinfo().getGold().equals("")) {
            return;
        }
        if (chatFragment == null) {
            return;
        }

        if (bean.getUse_type().equals("2")) {
            if (chatFragment.getGuardianInfos() == null) {
                initPayDialog("该礼物是主播守护专属礼物", "请开通守护", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (chatGiftDialogFragment != null) {
                            chatGiftDialogFragment.dismiss();
                        }
                        chatFragment.initGuardianlist(1);

                    }
                });
                return;
            } else {
                if (chatFragment.getGuardianInfos().getType().equals("2")) {
                    initPayDialog("该礼物为月守护以上专属", "请开通守护", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (chatGiftDialogFragment != null) {
                                chatGiftDialogFragment.dismiss();
                            }
                            chatFragment.initGuardianlist(1);

                        }
                    });
                    return;
                }
            }
        }

        if (Integer.parseInt(MyUserInstance.getInstance().getUserinfo().getGold()) > Integer.parseInt(bean.getPrice())) {
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
            initPayDialog("您的金币不足,是否前往充值?", "金币不足", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppManager.getAppManager().startActivity(ToPayActivity.class);
                }
            });

        }

    }


    */
/*
     * 礼物相关结束
     *//*

    */
/*
     * pk相关开始
     *//*

    private void pkNormalTime(String time) {
        if (pk_time != null) {
            pk_time.cancel();
        }

        long now_second = local_pk_second - MyUserInstance.getInstance().computerTime(time);
        if (now_second < 0) {
            pkPunishTime(local_pk_second + local_punish_second - MyUserInstance.getInstance().computerTime(time));

        } else {
            pk_time = new CountDownTimer(now_second, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    try {
                        tv_time_pk.setText("倒计时 " + MyUserInstance.getInstance().getTime((int) (millisUntilFinished / 1000)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFinish() {
                    pkPunishTime(120000);

                }
            };
            pk_time.start();
        }


    }

    private void pkPunishTime(long punishTime) {
        if (pk_time != null) {
            pk_time.cancel();
        }
        getResult(hotLive.getPkinfo().getHome_score(), hotLive.getPkinfo().getAway_score());
        pk_time = new CountDownTimer(punishTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                try {
                    tv_time_pk.setText("惩罚中 " + MyUserInstance.getInstance().getTime((int) (millisUntilFinished / 1000)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                if (!SuperPlayerVerticalActivity2.this.isFinishing()) {
                    endPk();
                }
            }
        };
        pk_time.start();
    }

    private void endPk() {
        //释放播放器,并隐藏
        if (spv_sub != null) {
            spv_sub.resetPlayer();
        }
        SuperPlayerModel model = new SuperPlayerModel();
        model.url = hotLive.getPull_url();
        model.title = hotLive.getTitle();
        spv_main.playWithModel(model);
        spv_sub.setVisibility(View.GONE);
        //调整界面,主舞台全显示,隐藏PK条,播放区全屏
        //播放区全屏
        RelativeLayout.LayoutParams lp_rl_root = (RelativeLayout.LayoutParams) rl_play.getLayoutParams();
        lp_rl_root.setMargins(0, 0, 0, 0);
        rl_play.setLayoutParams(lp_rl_root);

        RelativeLayout.LayoutParams lp_ll_play = (RelativeLayout.LayoutParams) ll_play_view.getLayoutParams();
        lp_ll_play.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        ll_play_view.setLayoutParams(lp_ll_play);
        //隐藏PK条,时间计时器
        tv_time_pk.setVisibility(View.GONE);
        ppb_live.setVisibility(View.GONE);
        ppb_live.rest();
        hotLive.setPklive(null);
        hotLive.setPkinfo(null);
        hotLive.setPk_status(0);
        hotLive.setPkid(0);
        if (pk_time != null) {
            pk_time.cancel();
        }
        siv_center.setVisibility(View.GONE);
        siv_left.setVisibility(View.GONE);
        siv_right.setVisibility(View.GONE);
        siv_center.stopAnimation(true);
        siv_left.stopAnimation(true);
        siv_right.stopAnimation(true);
        if (chatFragment != null) {
            chatFragment.hidePkPlayerInfo();
        }

    }

    private void getResult(int home, int away) {

        if (home > away) {
            siv_center.setVisibility(View.GONE);
            siv_left.setVisibility(View.VISIBLE);
            siv_right.setVisibility(View.VISIBLE);
            SVGAParser.Companion.shareParser().decodeFromAssets("pk_winner.svga", new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                    siv_left.setImageDrawable(drawable);
                    siv_left.startAnimation();
                }

                @Override
                public void onError() {

                }
            });
            siv_right.setImageDrawable(getDrawable(R.mipmap.pk_lose));

        } else if (home < away) {
            siv_center.setVisibility(View.GONE);
            siv_left.setVisibility(View.VISIBLE);
            siv_right.setVisibility(View.VISIBLE);

            SVGAParser.Companion.shareParser().decodeFromAssets("pk_winner.svga", new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                    siv_right.setImageDrawable(drawable);
                    siv_right.startAnimation();
                }

                @Override
                public void onError() {

                }
            });
            siv_left.setImageDrawable(getDrawable(R.mipmap.pk_lose));
        } else if (home == away) {
            siv_center.setVisibility(View.VISIBLE);
            siv_left.setVisibility(View.GONE);
            siv_right.setVisibility(View.GONE);
            siv_center.setImageDrawable(getDrawable(R.mipmap.pk_draw));
        }


    }


    private void startPk() {
        //清理计时器
        if (pk_time != null) {
            pk_time.cancel();
        }
        //重开计时器
        pkNormalTime(hotLive.getPkinfo().getCreate_time());
        //隐藏胜败动画
        siv_center.setVisibility(View.GONE);
        siv_left.setVisibility(View.GONE);
        siv_right.setVisibility(View.GONE);
        siv_center.stopAnimation(true);
        siv_left.stopAnimation(true);
        siv_right.stopAnimation(true);


        //调整播放舞台
        RelativeLayout.LayoutParams lp_rl_play = (RelativeLayout.LayoutParams) rl_play.getLayoutParams();
        lp_rl_play.setMargins(0, DipPxUtils.dip2px(context, 140), 0, 0);
        rl_play.setLayoutParams(lp_rl_play);


        RelativeLayout.LayoutParams lp_ll_play = (RelativeLayout.LayoutParams) ll_play_view.getLayoutParams();
        lp_ll_play.height = (MyUserInstance.getInstance().device_width / 2) / 3 * 4;
        ll_play_view.setLayoutParams(lp_ll_play);

        //显示PK条,计时器
        ppb_live.setVisibility(View.VISIBLE);
        tv_time_pk.setVisibility(View.VISIBLE);

        //填充播放数据
        if (hotLive.getPkinfo().getHome_anchorid() == Integer.parseInt(hotLive.getAnchorid())) {
            SuperPlayerModel model_main = new SuperPlayerModel();
            model_main.url = hotLive.getPull_url();
            model_main.title = hotLive.getTitle();

            spv_main.playWithModel(model_main);
            spv_main.setPlayerViewCallback(this);

            if (hotLive.getPklive() != null) {
                SuperPlayerModel model_sub = new SuperPlayerModel();
                model_sub.url = hotLive.getPklive().getPull_url();
                model_sub.title = hotLive.getPklive().getTitle();
                spv_sub.playWithModel(model_sub);
                spv_sub.setPlayerViewCallback(this);
            }
            spv_sub.setVisibility(View.VISIBLE);

        } else if (hotLive.getPkinfo().getAway_anchorid() == Integer.parseInt(hotLive.getAnchorid())) {
            SuperPlayerModel model_main = new SuperPlayerModel();
            model_main.url = hotLive.getPull_url();
            model_main.title = hotLive.getTitle();
            spv_sub.playWithModel(model_main);
            spv_sub.setPlayerViewCallback(this);
            spv_sub.setVisibility(View.VISIBLE);


            if (hotLive.getPklive() != null) {


                SuperPlayerModel model_sub = new SuperPlayerModel();
                model_sub.url = hotLive.getPklive().getPull_url();
                model_sub.title = hotLive.getPklive().getTitle();
                spv_main.playWithModel(model_sub);
                spv_main.setPlayerViewCallback(this);
            }
        }
        pkNormalTime(hotLive.getPkinfo().getCreate_time());
        ppb_live.cpmputerValue(hotLive.getPkinfo().getHome_score(), hotLive.getPkinfo().getAway_score());

        if (chatFragment != null) {
            if (chatFragment.hotLive != null) {
                chatFragment.showPkPlayerInfo();
            }

        }
        SVGAParser.Companion.shareParser().decodeFromAssets("pk_start.svga", new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                siv_start.setVisibility(View.VISIBLE);
                siv_start.setImageDrawable(drawable);
                siv_start.startAnimation();
            }

            @Override
            public void onError() {

            }
        });
        spv_main.setVideoStartListener(new SuperPlayerView.VideoStartListener() {
            @Override
            public void videoStart() {
                if (!SuperPlayerVerticalActivity2.this.isFinishing()) {
                    rl_loading.setVisibility(View.GONE);
                    dy_loading.stop();
                }
            }

            @Override
            public void videoFail() {

            }
        });
        spv_sub.setVideoStartListener(new SuperPlayerView.VideoStartListener() {
            @Override
            public void videoStart() {
                if (!SuperPlayerVerticalActivity2.this.isFinishing()) {
                    rl_loading.setVisibility(View.GONE);
                    dy_loading.stop();
                }
            }

            @Override
            public void videoFail() {

            }
        });
    }

    */
/*
     * pk相关结束
     *//*

}*/
