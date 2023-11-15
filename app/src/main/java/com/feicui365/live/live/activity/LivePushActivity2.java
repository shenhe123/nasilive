package com.feicui365.live.live.activity;

import static com.tencent.live2.V2TXLiveDef.V2TXLiveMirrorType.V2TXLiveMirrorTypeAuto;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;


import com.alibaba.fastjson.JSON;
import com.feicui365.live.eventbus.OpenAudioBus;
import com.feicui365.live.eventbus.PauseAudioBus;
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfoResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMMessageReceipt;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMUserInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePusher;
import com.tencent.live2.V2TXLivePusherObserver;
import com.tencent.rtmp.ui.TXCloudVideoView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.base.SocketConstants;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.eventbus.ChangeCarmerBus;
import com.feicui365.live.eventbus.OnLiveFinishBus;
import com.feicui365.live.eventbus.OpenBeautyBus;
import com.feicui365.live.im.TxImSendUtils;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.interfaces.OnDialogDissmissLinstener;
import com.feicui365.live.interfaces.OnLinkDialogLinstener;
import com.feicui365.live.live.bean.EndLiveInfo;
import com.feicui365.live.live.dialog.LinkUserInfoDialog;
import com.feicui365.live.live.dialog.NormalBeautylDialog;
import com.feicui365.live.live.dialog.TipsDialog;
import com.feicui365.live.live.fragment.LivePushFragment2;
import com.feicui365.live.live.utils.AnchorLivePushUtils;
import com.feicui365.live.live.utils.AnchorPkPlayerUtils;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.PkScreenUtils;
import com.feicui365.live.live.utils.StreamerUtils;
import com.feicui365.live.live.weight.PagerFragmentAdapter;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;

import com.feicui365.live.util.MyUserInstance;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import butterknife.BindView;
import cn.tillusory.sdk.TiSDK;
import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.InitStatus;
import cn.tillusory.sdk.bean.TiRotation;
import cn.tillusory.tiui.TiPanelLayout;


/**
 *
 */
public class LivePushActivity2 extends BaseMvpActivity<LivePushPresenter> implements LivePushContrat.View {


    @BindView(R.id.vp_live)
    ViewPager mViewPager;

    @BindView(R.id.txcv_home)
    TXCloudVideoView mTxcvHome;

    @BindView(R.id.txcv_away)
    TXCloudVideoView mTxcvAway;

    @BindView(R.id.rl_home)
    RelativeLayout mHomeLayout;
    @BindView(R.id.rl_away)
    RelativeLayout mAwayLayout;
    @BindView(R.id.rl_pk)
    RelativeLayout mPkLayout;


    //PK播放相关
    //推流简单封装
    private AnchorLivePushUtils mAnchorPushUtils;
    //播放简单封装
    private AnchorPkPlayerUtils mOtherPlayerUtils;
    //直播推流工具,主要
    private V2TXLivePusher mLivePusher;
    //连麦消息队列,主要用于开启连麦后,申请的排序
    private ConcurrentLinkedQueue<UserRegist> mLinkMessages;
    private PagerFragmentAdapter mFragmentAdapter;
    private ArrayList<Fragment> mFragments;
    private LivePushFragment2 mLivePushFragment;
    private HotLive mLiveInfo;
    private int mPkLayoutWidthPx = PkScreenUtils.widthPx / 2;
    private TiPanelLayout mPanelLayout;
    private NormalBeautylDialog normalBeautylDialog;
    private StreamerUtils mStreamerUtils;
    private int mLiveType = 0;//0正常,1PK,2连麦
    //连麦用户信息弹窗
    private LinkUserInfoDialog mLinkDialog;
    //连麦用户信息
    private UserRegist mLinkUser;

    private int mBeautyChannel;


    private final int INIT_BEAUTY = 10001;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (isFinishing()) {
                return;
            }
            ImMessage bean = (ImMessage) msg.obj;

            switch (msg.what) {
                case INIT_BEAUTY:
                    mPanelLayout = new TiPanelLayout(context).init(TiSDKManager.getInstance());
                    addContentView(mPanelLayout,
                            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mLivePusher.setObserver(new V2TXLivePusherObserver() {
                        @Override
                        public int onProcessVideoFrame(V2TXLiveDef.V2TXLiveVideoFrame srcFrame, V2TXLiveDef.V2TXLiveVideoFrame dstFrame) {
                            int textureId = TiSDKManager.getInstance().renderTexture2D(srcFrame.texture.textureId, srcFrame.width, srcFrame.height,
                                    TiRotation.CLOCKWISE_ROTATION_0, false);
                            dstFrame.texture.textureId = textureId;
                            return 0;
                        }
                    });

                    break;
                case SocketConstants.LIVE_BEAUTY:
                    mPanelLayout = new TiPanelLayout(context).init(TiSDKManager.getInstance());
                    addContentView(mPanelLayout,
                            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mAnchorPushUtils.getLivePusher().setObserver(new V2TXLivePusherObserver() {
                        @Override
                        public int onProcessVideoFrame(V2TXLiveDef.V2TXLiveVideoFrame srcFrame, V2TXLiveDef.V2TXLiveVideoFrame dstFrame) {
                            int textureId = TiSDKManager.getInstance().renderTexture2D(srcFrame.texture.textureId, srcFrame.width, srcFrame.height,
                                    TiRotation.CLOCKWISE_ROTATION_0, false);
                            dstFrame.texture.textureId = textureId;
                            return 0;
                        }
                    });

                    break;
                case SocketConstants.LIVE_PK_START:
                    if (bean == null) {
                        return;
                    }

                    /**
                     * 通知开始PK,当前页面切换基本页面,切换宽高,通知子页面更新页面
                     * 通知中包含了PK信息跟PK直播信息
                     * */
                    mLiveType = 1;
                    initLiveLayout(bean);

                    break;
                case SocketConstants.LIVE_PK_END:

                    //结束PK,通知fragment还原基本布局

                    initLiveLayout(null);
                    break;
                case SocketConstants.LIVE_PK_SCORE_CHANGE:
                    if (bean == null) {
                        return;
                    }
                    //PK比分变化
                    setScoreChange(bean);
                    // mAPLUtils.changeScoure(bean);
                    break;

                case SocketConstants.LIVE_GIFT_ANIMATE:
                    if (bean == null) {
                        return;
                    }
                    addChat(bean);
                    showGift(bean);
                    break;
                case SocketConstants.LIVE_ADD_CHAT:
                    if (bean == null) {
                        return;
                    }
                    if (bean.getData().getChat().getSender().getBanStatus() == 2) {
                        return;
                    }
                    addChat(bean);
                    break;

                case SocketConstants.LIVE_STREAMER_VIP:

                    if (mStreamerUtils != null) {
                        mStreamerUtils.addToViplist(bean, (Activity) context);
                    }
                    break;
                //申请
                case SocketConstants.LIVE_LINK_REVICE:
                    if (bean == null) {
                        return;
                    }
                    mLinkUser = bean.getData().getNotify().getUser();
                    initLinkDialog(mLinkUser);
                    break;
                //同意
                case SocketConstants.LIVE_LINK_ACCEPT:
                    break;
                //拒绝
                case SocketConstants.LIVE_LINK_REFUSE:
                    break;
                //停止
                case SocketConstants.LIVE_LINK_STOP:
                    initLiveLayout(null);
                    endLink();
                    mLinkUser = null;
                    break;
                case SocketConstants.UPDATE_LIVE_INFO:
                    mPresenter.getAnchorLiveInfo(mLiveInfo.getAnchorid());
                    break;
                case SocketConstants.LIVE_CANCEL_MANAGER:
                    if (bean == null) {
                        return;
                    }
                    addChat(bean);
                    break;
                case SocketConstants.LIVE_SET_MANAGER:
                    if (bean == null) {
                        return;
                    }
                    addChat(bean);
                    break;
            }
        }
    };

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.living_activity_2;
    }


    @Override
    protected void onPause() {
        super.onPause();
      /*  if(mAnchorPushUtils!=null){

            mAnchorPushUtils.pause();
        }*/

    }

    @Override
    protected void onResume() {
        super.onResume();
    /*    if(mAnchorPushUtils!=null){
            mAnchorPushUtils.resume();
        }*/
    }

    /*
     * 梳理基本流程
     *1,一共有2个关于直播信息的接口,getAnchorLiveinfo(包含直播相关内容),getBaseLiveInfo(包含跟直播间主播的相关内容,是否关注贡献榜等,不包含PK)
     *2,进直播间优先查询直播间信息,查询结束了开播
     *
     * */
    @Override
    protected void initView() {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        setActionBarTextColor(false);
        hideTitle(true);
        if (!getIntent().hasExtra(Constants.LIVE_INFO)) {
            finish();
        }
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initData() {
        mLiveInfo = (HotLive) getIntent().getSerializableExtra(Constants.LIVE_INFO);
        mStreamerUtils = new StreamerUtils();
        mLinkMessages = new ConcurrentLinkedQueue<>();
        initPlayerUtils();
        initViewPager();
        initLiveSocket();
        initPlayer();
        mPresenter.getAnchorLiveInfo(mLiveInfo.getAnchorid());
        mAnchorPushUtils.getLivePusher().setRenderMirror(V2TXLiveDef.V2TXLiveMirrorType.V2TXLiveMirrorTypeEnable);
    }

    @Override
    public void getAnchorLiveInfo(LiveInfo response) {
        if (mLivePushFragment != null) {
            mLivePushFragment.setLiveIncomeNums(response.getLive().getProfit());
        }
        initHandlerMessageDelayed(SocketConstants.UPDATE_LIVE_INFO, 60000);
    }

    //初始化播放器
    private void initPlayerUtils() {
        mAnchorPushUtils = new AnchorLivePushUtils();
        mAnchorPushUtils.setVideoView(mTxcvHome);
        mOtherPlayerUtils = new AnchorPkPlayerUtils();
        mOtherPlayerUtils.setVideoView(mTxcvAway);

    }

    //重置播放器,主要用于处理第一次播放,跟可能的重连
    private void initPlayer() {
        if (mOtherPlayerUtils != null) {
            mOtherPlayerUtils.release();
        }
        switch (mLiveType) {
            case 0:
                if (mLivePusher == null) {
                    mLivePusher = mAnchorPushUtils.startLive(context, mLiveInfo);
                    initBeauty();
                }
                break;
            case 1:
                if (mLivePusher == null) {
                    mLivePusher = mAnchorPushUtils.startLive(context, mLiveInfo);
                    initBeauty();
                }
                mOtherPlayerUtils.startLive(context, mLiveInfo.getPklive());
                break;
        }

    }

    //初始化美颜
    private void initBeauty() {
        if (MyUserInstance.getInstance().getBeautyChannel() == 1) {
            //高级美颜
            mBeautyChannel = 1;
            TiSDK.init(MyUserInstance.getInstance().getBeautyKey(), this, new TiSDK.TiInitCallback() {
                @Override
                public void finish(InitStatus initStatus) {
                    if (initStatus.getCode() == 100) {
                        if (mPanelLayout == null) {
                            Message message = new Message();
                            message.what = SocketConstants.LIVE_BEAUTY;
                            mHandler.sendMessage(message);
                        }
                    }
                }
            });
        } else {
            //普通美颜
            normalBeautylDialog = new NormalBeautylDialog();
            normalBeautylDialog.setLivePusher(mAnchorPushUtils.getLivePusher());
            mBeautyChannel = 0;
        }
    }

    //初始化辅助页面
    private void initViewPager() {
        mFragments = new ArrayList<>();
        mLivePushFragment = LivePushFragment2.newInstance(mAnchorPushUtils);
        mLivePushFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLiveLayout(null);
                endLink();
                mLinkUser = null;
            }
        });
        mFragments.add(mLivePushFragment);
        mFragmentAdapter = new PagerFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mFragmentAdapter);

        if (mLivePushFragment != null) {
            mLivePushFragment.setLiveInfo(mLiveInfo);
        }

    }

    //初始化socket
    private void initLiveSocket() {
        TxImSendUtils.joinRoom(MyUserInstance.getInstance().getUserinfo().getId(), 0);
        TxImUtils.getSocketManager().initLiveListener(MyUserInstance.getInstance().getUserinfo().getId(),
                new V2TIMSimpleMsgListener() {
                    @Override
                    public void onRecvGroupTextMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, String text) {
                        super.onRecvGroupTextMessage(msgID, groupID, sender, text);
                        Log.e("IMTAGAnchor222", text);
                        if (!text.contains("action")) {
                            return;
                        }
                        initMessage(text);
                    }

                    @Override
                    public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
                        super.onRecvC2CTextMessage(msgID, sender, text);
                        Log.e("IMTAGAnchor12", "====" + text);
                        if (!TextUtils.isEmpty(text) && text.equals("refreshVisitors")) {
                            setMemberCount(0);
//                            Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    V2TIMManager.getGroupManager().getGroupOnlineMemberCount(ArmsUtils.initLiveGroupId(MyUserInstance.getInstance().getUserinfo().getId()), new V2TIMValueCallback<Integer>() {
//                                        @Override
//                                        public void onSuccess(Integer integer) {
//                                            // 获取直播群在线人数成功、
//                                            Log.e("IMTAGAnchor", "=在线人数==" + integer);
//
//                                        }
//                                        @Override
//                                        public void onError(int code, String desc) {
//                                            // 获取直播群在线人数失败
//                                        }
//                                    });
//                                }
//                            }, 61000);
                        } else if (!TextUtils.isEmpty(text) && text.equals("AdminKillUserOut")) {
                            setMemberCount(0);
                        }
                    }

                    @Override
                    public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
                        super.onRecvC2CCustomMessage(msgID, sender, customData);
                        String result = new String(customData);
                        Log.e("IMTAGAnchor11", "=======" + result);
                        if (!TextUtils.isEmpty(result) && result.equals("refreshVisitors")) {
                            setMemberCount(0);
                        }
                    }

                    @Override
                    public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
                        super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);
                        String result = new String(customData);
                        Log.e("IMTAGAnchor333", result);
                        if (!result.contains("action")) {
                            if (result.equals("AdminKillUserOut")) {
                                setMemberCount(0);
                            }
                            return;
                        }
                        initMessage(result);
                    }
                });

//        TxImUtils.getSocketManager().initLiveAdvaceListener(MyUserInstance.getInstance().getUserinfo().getId(),
//                new V2TIMAdvancedMsgListener() {
//
//                    @Override
//                    public void onRecvNewMessage(V2TIMMessage msg) {
//                        super.onRecvNewMessage(msg);
//                        Log.e("IMTAGAnchor222", msg+"===");
//                    }
//
//                    @Override
//                    public void onRecvMessageReadReceipts(List<V2TIMMessageReceipt> receiptList) {
//                        super.onRecvMessageReadReceipts(receiptList);
//                        Log.e("IMTAGAnchor222", receiptList+"===");
//                    }
//
//                    @Override
//                    public void onRecvC2CReadReceipt(List<V2TIMMessageReceipt> receiptList) {
//                        super.onRecvC2CReadReceipt(receiptList);
//                        Log.e("IMTAGAnchor222", receiptList+"===");
//                    }
//
//                    @Override
//                    public void onRecvMessageRevoked(String msgID) {
//                        super.onRecvMessageRevoked(msgID);
//                        Log.e("IMTAGAnchor222", msgID);
//                    }
//
//                    @Override
//                    public void onRecvMessageModified(V2TIMMessage msg) {
//                        super.onRecvMessageModified(msg);
//                        Log.e("IMTAGAnchor222", msg.toString());
//                    }
//
////                    @Override
////                    public void onRecvGroupTextMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, String text) {
////                        super.onRecvGroupTextMessage(msgID, groupID, sender, text);
////                        Log.e("IMTAGAnchor222", text);
////                        if (!text.contains("action")) {
////                            return;
////                        }
////                        initMessage(text);
////                    }
////
////                    @Override
////                    public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
////                        super.onRecvC2CTextMessage(msgID, sender, text);
////                        Log.e("IMTAGAnchor11", text);
////                    }
////
////                    @Override
////                    public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
////                        super.onRecvC2CCustomMessage(msgID, sender, customData);
////                        String result = new String(customData);
////                        Log.e("IMTAGAnchor11", result);
////                    }
////
////                    @Override
////                    public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
////                        super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);
////                        String result = new String(customData);
////                        Log.e("IMTAGAnchor222", result);
////                        if (!result.contains("action")) {
////                            return;
////                        }
////                        initMessage(result);
////                    }
//                });

    }


    private void initMessage(String result) {
//        Log.e("IMTAGAnchor", result);
        ImMessage bean = JSON.parseObject(result, ImMessage.class);


        switch (bean.getAction()) {
            case SocketConstants.LIVE_ACTION_RoomMessage:
                if (bean.getData() == null) {
                    return;
                }
                if (bean.getData().getChat() == null) {
                    return;
                }
                initHandlerMessage(SocketConstants.LIVE_ADD_CHAT, bean);
                break;
            case SocketConstants.LIVE_ACTION_LiveGroupMemberJoinExit:
                setMemberCount(0);
//                V2TIMManager.getGroupManager().getGroupOnlineMemberCount(ArmsUtils.initLiveGroupId(MyUserInstance.getInstance().getUserinfo().getId()), new V2TIMValueCallback<Integer>() {
//                    @Override
//                    public void onSuccess(Integer integer) {
//                        // 获取直播群在线人数成功、
////                        Log.e("IMTAGAnchor", "=在线人数==" + integer);
////                        if (integer - 1 > 0) {
////                            setMemberCount(integer - 1);
////                        } else {
////                            setMemberCount(0);
////                        }
//                        setMemberCount(integer);
//                    }
//
//                    @Override
//                    public void onError(int code, String desc) {
//                        // 获取直播群在线人数失败
//                    }
//                });
                break;
            case SocketConstants.LIVE_ACTION_LiveFinished:
                //结束直播
                // finish();
                break;
            case SocketConstants.LIVE_ACTION_RoomAttentAnchor:
                //关注主播
                break;
            case SocketConstants.LIVE_ACTION_GiftAnimation:
                if (bean.getData().getGift() == null) {
                    return;
                }
                initHandlerMessage(SocketConstants.LIVE_GIFT_ANIMATE, bean);
                break;
            case SocketConstants.LIVE_ACTION_RoomNotification:
                if (bean.getData() == null) {
                    return;
                }
                if (bean.getData().getNotify() == null) {
                    return;
                }
                if (bean.getData().getNotify().getType() == null) {
                    return;
                }
                switch (bean.getData().getNotify().getType()) {
                    case SocketConstants.SOCKET_NOTIFY_RoomNotificationReciveLinkRequest:
                        initHandlerMessage(SocketConstants.LIVE_LINK_REVICE, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotificationAcceptLinkRequest:
                        //同意,主播同意了你的连麦请求,在这里拿播放url
                        initHandlerMessage(SocketConstants.LIVE_LINK_ACCEPT, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotificationRefuseLinkRequest:
                        initHandlerMessage(SocketConstants.LIVE_LINK_REFUSE, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotificationStopLink:
                        //停止
                        initHandlerMessage(SocketConstants.LIVE_LINK_STOP, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypePkStart:
                        initHandlerMessage(SocketConstants.LIVE_PK_START, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypePkEnd:
                        initHandlerMessage(SocketConstants.LIVE_PK_END, bean);
                        break;

                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypePkScoreChange:
                        initHandlerMessage(SocketConstants.LIVE_PK_SCORE_CHANGE, bean);
                        break;

                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeSetManager:
                        initHandlerMessage(SocketConstants.LIVE_SET_MANAGER, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeCancelManager:

                        initHandlerMessage(SocketConstants.LIVE_CANCEL_MANAGER, bean);
                        break;

                }


                break;

        }
    }


    private void initHandlerMessage(int what, ImMessage bean) {
        Message message = new Message();
        message.what = what;
        message.obj = bean;
        mHandler.sendMessage(message);
    }

    private void initHandlerMessageDelayed(int what, int time) {
        Message message = new Message();
        message.what = what;

        mHandler.sendMessageDelayed(message, time);
    }

    //页面结束,销毁直播相关内容
    @Override
    protected void onDestroy() {
        if (mAnchorPushUtils != null) {
            mAnchorPushUtils.release();
            mAnchorPushUtils = null;
        }
        if (mOtherPlayerUtils != null) {
            mOtherPlayerUtils.release();
            mOtherPlayerUtils = null;
        }
        if (mHandler != null) {
            mHandler.removeMessages(SocketConstants.UPDATE_LIVE_INFO);
        }

        TxImUtils.getSocketManager().cleanLiveListener();
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    //退出按钮,优先结束直播
    @Override
    public void onBackPressed() {
        if (!ArmsUtils.canClick()) {
            return;
        }
        initExitDialog();
    }


    private void initExitDialog() {


        TipsDialog tipsDialog = new TipsDialog();
        tipsDialog.setmContent("是否要结束直播");
        tipsDialog.setmTitle(getString(R.string.st_tips));
        tipsDialog.setSubmitClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLoading();
                mAnchorPushUtils.pause();
                mPresenter.endlive(mLiveInfo.getLiveid());
                finish();
            }
        });
        tipsDialog.show(getSupportFragmentManager(), "");


    }


    @Override
    public void endlive(EndLiveInfo bean) {
        hideLoading();
        finish();
        //   initEndDialog(bean);
    }

    private void initEndDialog(EndLiveInfo bean) {


        TipsDialog tipsDialog = new TipsDialog();
        tipsDialog.setmContent("直播获得钻石数：" + bean.getGift_profit() + "个");
        tipsDialog.setmTitle(getString(R.string.st_tips));
        tipsDialog.setHideCancel(true);
        tipsDialog.setSubmitClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tipsDialog.show(getSupportFragmentManager(), "");


    }

    /*
     * 界面更新
     * */

    private void showGift(ImMessage bean) {
        if (mLivePushFragment != null) {
            mLivePushFragment.showGift(bean);
        }
    }

    private void addChat(ImMessage bean) {
        Log.e("111111", "==聊进入=" + bean);
        if (bean.getData().getChat() != null) {
            if (bean.getData().getChat().getMessage().equals(getString(R.string.st_vip_enter_room))) {
                mStreamerUtils.addToViplist(bean, this);
            }

        }

        if (mLivePushFragment != null) {
            mLivePushFragment.addChat(bean);
        }
    }

    private void startPk() {
        if (mLivePushFragment != null) {
            mLivePushFragment.startPk();
        }
    }

    private void endPk() {
        if (mLivePushFragment != null) {
            mLivePushFragment.endPk();
        }
    }

    private void setMemberCount(int bean) {

        if (mLivePushFragment != null) {
            mLivePushFragment.setMemberCount(bean);
        }
    }

    private void setScoreChange(ImMessage bean) {
        if (mLivePushFragment != null) {
            mLivePushFragment.setScoreChange(bean);
        }
    }

    private void startLink(String url) {
        if (mLinkUser == null) {
            //这里是已经同意了,记得关闭LINK
            return;
        }
        if (!ArmsUtils.isStringEmpty(url)) {
            //这里是已经同意了,记得关闭LINK
            return;
        }
        if (mLivePushFragment != null) {
            mLivePushFragment.startLink(url, mLinkUser.getId());
        }
        mAnchorPushUtils.initMix(2, mLiveInfo, false, mLinkUser.getId());
    }

    private void endLink() {
        if (mLinkUser == null) {
            //这里是已经同意了,记得关闭LINK
            return;
        }
        if (mLivePushFragment != null) {
            mLivePushFragment.endLink();
        }
    }
    /*
     * 界面更新结束
     * */

    /**
     * 重置界面,主要用以PK结束前后,以及LINK结束后
     */
    private void initLiveLayout(ImMessage bean) {

        if (bean == null) {
            normalLayout();
        } else {
            mLiveInfo.setPk_status(1);
            mLiveInfo.setPklive(bean.getData().getNotify().getPklive());
            mLiveInfo.setPkid(bean.getData().getNotify().getPklive().getPkid());
            mLiveInfo.setPkinfo(bean.getData().getNotify().getPkinfo());
            if (mLivePushFragment != null) {
                mLivePushFragment.setLiveInfo(mLiveInfo);
            }
            startPk();
            boolean isHome;
            if (String.valueOf(bean.getData().getNotify().getPkinfo().getHome_anchorid()).equals(mLiveInfo.getAnchorid())) {
                isHome = true;
            } else {
                isHome = false;
            }

            mAnchorPushUtils.initMix(1, bean.getData().getNotify().getPklive(), isHome, null);

            RelativeLayout.LayoutParams flRootParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    ArmsUtils.dip2px(context, 285));
            flRootParams.setMargins(0, ArmsUtils.dip2px(context, 160), 0, 0);
            mPkLayout.setLayoutParams(flRootParams);


            if (isHome) {
                RelativeLayout.LayoutParams mParamsHome = new RelativeLayout.LayoutParams(mPkLayoutWidthPx
                        , RelativeLayout.LayoutParams.MATCH_PARENT);
                mHomeLayout.setLayoutParams(mParamsHome);
                RelativeLayout.LayoutParams mParamsAway = new RelativeLayout.LayoutParams(mPkLayoutWidthPx
                        , RelativeLayout.LayoutParams.MATCH_PARENT);
                mParamsAway.setMargins(mPkLayoutWidthPx, 0, 0, 0);
                mAwayLayout.setLayoutParams(mParamsAway);
            } else {
                RelativeLayout.LayoutParams mParamsHome = new RelativeLayout.LayoutParams(mPkLayoutWidthPx
                        , RelativeLayout.LayoutParams.MATCH_PARENT);
                mParamsHome.setMargins(mPkLayoutWidthPx, 0, 0, 0);
                mHomeLayout.setLayoutParams(mParamsHome);
                RelativeLayout.LayoutParams mParamsAway = new RelativeLayout.LayoutParams(mPkLayoutWidthPx
                        , RelativeLayout.LayoutParams.MATCH_PARENT);
                mAwayLayout.setLayoutParams(mParamsAway);
            }
            mAwayLayout.setVisibility(View.VISIBLE);

        }


        initPlayer();
    }


    //不管是啥,还原最基本的直播
    private void normalLayout() {
        mLiveInfo.setPk_status(0);
        mLiveInfo.setPklive(null);
        mLiveInfo.setPkid(null);
        mLiveInfo.setPkinfo(null);

        //如果是PK状态,关闭PK相关参数

        switch (mLiveType) {
            case 1:
                endPk();
                break;

        }
        mLiveType = 0;

        if (mLivePushFragment != null) {
            mLivePushFragment.setLiveInfo(mLiveInfo);
        }
        mAnchorPushUtils.initMix(0, null, false, null);
        RelativeLayout.LayoutParams mRootParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);

        mPkLayout.setLayoutParams(mRootParams);

        mHomeLayout.setLayoutParams(mRootParams);
        //隐藏其他所有内容
        mAwayLayout.setVisibility(View.GONE);
    }


    //连麦用户弹窗
    private void initLinkDialog(UserRegist bean) {
        if (mLinkDialog != null) {
            if (mLinkDialog.isAdded()) {
                mLinkMessages.offer(bean);
                return;
            }
        }
        mLinkDialog = new LinkUserInfoDialog(bean);
        mLinkDialog.show(getSupportFragmentManager(), "");
        mLinkDialog.setOnLinkDialogLinstener(new OnLinkDialogLinstener() {
            @Override
            public void onAccpet(String pullUrl) {
                mLinkMessages.clear();
                //这边开始准备连麦相关内容,这里有两个地方一个是设置fragment的连麦view,一个是自己开启混流
                startLink(pullUrl);
            }
        });
        mLinkDialog.setOnDialogDissmissLinstener(new OnDialogDissmissLinstener() {
            @Override
            public void onDismiss() {
                mLinkDialog = null;
                if (mLinkMessages.size() > 0) {
                    initLinkDialog(mLinkMessages.poll());
                }
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void swtichCamera(ChangeCarmerBus message) {
        if (mAnchorPushUtils != null) {
            mAnchorPushUtils.swtichCamera();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pauseAudio(PauseAudioBus message) {
        if (mAnchorPushUtils != null) {
            mAnchorPushUtils.stopMicrophone();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void startAudio(OpenAudioBus message) {
        if (mAnchorPushUtils != null) {
            mAnchorPushUtils.startMicrophone();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openBeauty(OpenBeautyBus message) {
        initBeayty();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openBeauty(OnLiveFinishBus message) {
        initExitDialog();
    }


    private void initBeayty() {
        switch (mBeautyChannel) {
            case 1:
                if (mPanelLayout != null) {
                    mPanelLayout.showView();
                }
                break;
            default:
                if (normalBeautylDialog == null) {
                    return;
                }
                if (normalBeautylDialog.isAdded()) {
                    return;
                }
                normalBeautylDialog.show(getSupportFragmentManager(), "");
                break;

        }
    }

}
