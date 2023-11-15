package com.feicui365.live.live.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.feicui365.nasinet.utils.AppManager;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.rtmp.ui.TXCloudVideoView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.base.SocketConstants;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.contract.LivePushContrat;

import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.live.dialog.TipsDialog;
import com.feicui365.live.live.fragment.LivePlayVerticalFragment2;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.StreamerUtils;
import com.feicui365.live.live.utils.UserLivePlayerUtils;
import com.feicui365.live.live.weight.PagerFragmentAdapter;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveConsume;
import com.feicui365.live.model.entity.LiveInfo;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.ui.act.ToPayActivity;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 */
public class LivePlayVerticalActivity2 extends BaseMvpActivity<LivePushPresenter> implements LivePushContrat.View {


    @BindView(R.id.vp_live)
    ViewPager mViewPager;
    @BindView(R.id.txcv_home)
    TXCloudVideoView mTxcvHome;

    private PagerFragmentAdapter mFragmentAdapter;
    private ArrayList<Fragment> mFragments;
    private LivePlayVerticalFragment2 mLivePlayFragment;
    private UserLivePlayerUtils mHomePlayer;

    private HotLive mLiveInfo;
    private String mAnchorid;

    private int mLiveType = 0;//0正常,1PK,2连麦
    private String mMySelfPushUrl;//申请连麦后,用户自己的推流地址

    private StreamerUtils mStreamerUtils;
    private final int LIVE_CONSUME = 1000000;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ImMessage bean = (ImMessage) msg.obj;
            switch (msg.what) {


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
                    //这算是强制结束

                    initLiveLayout(null);
                    break;
                case SocketConstants.LIVE_PK_SCORE_CHANGE:
                    //这算是强制结束
                    if (bean == null) {
                        return;
                    }
                    //PK比分变化
                    setScoreChange(bean);
                    break;
                case SocketConstants.LIVE_GIFT_ANIMATE:
                    if (bean == null) {
                        return;
                    }
                    addChat(bean);
                    showGift(bean);
                    break;
                case SocketConstants.LIVE_GUARDIAN_ANCHOR:
                case SocketConstants.LIVE_ADD_CHAT:
                    if (bean == null) {
                        return;
                    }
                    addChat(bean);
                    break;

                case SocketConstants.LIVE_STREAMER_VIP:

                    if (mStreamerUtils != null) {
                        mStreamerUtils.addToViplist(bean, (Activity) context);
                    }
                    break;
                case SocketConstants.LIVE_LINK_ON:
                    if (bean == null) {
                        return;
                    }
                    setLink(true);
                    addChat(bean);
                    break;
                case SocketConstants.LIVE_LINK_OFF:
                    if (bean == null) {
                        return;
                    }
                    setLink(false);
                    addChat(bean);
                    break;
                case SocketConstants.LIVE_LINK_ACCEPT:
                    if (bean == null) {
                        return;
                    }
                    mLiveType = 2;

                    startLink();
                    if (mHomePlayer != null) {
                        mHomePlayer.startLive(context, bean.getData().getNotify().getLink_acc_url());
                    }

                    break;
                case SocketConstants.LIVE_LINK_STOP:
                    mMySelfPushUrl = null;
                    initLiveLayout(null);
                    endLink();
                    break;
                case SocketConstants.LIVE_SET_MANAGER:
                    if (bean == null) {
                        return;
                    }
                    if (bean.getData().getNotify().getUser().getId().equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                        setManager(true);
                    }
                    addChat(bean);
                    break;
                case SocketConstants.LIVE_CANCEL_MANAGER:
                    if (bean == null) {
                        return;
                    }
                    if (bean.getData().getNotify().getUser().getId().equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                        setManager(false);
                    }
                    addChat(bean);


                    break;
                case LIVE_CONSUME:
                    if (Integer.valueOf(mLiveInfo.getPrice()) > MyUserInstance.getInstance().getUserGold()) {
                        initConsumeDialog();
                    } else {
                        mPresenter.liveChosume(mLiveInfo.getLiveid());
                    }


                    break;
                case SocketConstants.LIVE_SHOP_ITEM:
                    if (bean == null) {
                        return;
                    }
                    if (bean.getData().getGoods() == null) {
                        return;
                    }
                    showGoodsDialog(bean);


                    break;

            }
        }
    };

    private void showGoodsDialog(ImMessage bean) {

        if (mLivePlayFragment != null) {
            mLivePlayFragment.showShopItemDialog(bean.getData().getGoods());
        }

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_vertical_activity_2;
    }

    @Override
    protected void initData() {
        mPresenter.getAnchorLiveInfo(mAnchorid);
        mHomePlayer = new UserLivePlayerUtils();
        mHomePlayer.setVideoView(mTxcvHome);

    }

    @Override
    protected void initView() {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        setActionBarTextColor(false);
        hideTitle(true);
        if (!getIntent().hasExtra(Constants.ANCHOR_ID)) {
            finish();
        }
        mAnchorid = getIntent().getStringExtra(Constants.ANCHOR_ID);

        mStreamerUtils = new StreamerUtils();


        initLiveSocket();
        EventBus.getDefault().register(this);
    }


    private void initViewPager() {
        mFragments = new ArrayList<>();
        mLivePlayFragment = LivePlayVerticalFragment2.newInstance(mLiveInfo);
        mLivePlayFragment.setOnUrlSetListener(new LivePlayVerticalFragment2.OnUrlSetListener() {
            @Override
            public void setPushUrl(String url) {
                mMySelfPushUrl = url;
            }
        });
        mFragments.add(mLivePlayFragment);
        mFragmentAdapter = new PagerFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mFragmentAdapter);

    }

    @Override
    public void getAnchorLiveInfo(LiveInfo bean) {
        mLiveInfo = bean.getLive();
        if (mLiveInfo.getRoom_type().equals("2")) {
            mPresenter.liveChosume(mLiveInfo.getLiveid());

        }

        initPlayer();
        initViewPager();
        initLiveLayoutFirst(mLiveInfo);
        if (mLivePlayFragment != null) {
            mLivePlayFragment.setLiveInfo(mLiveInfo);
        }
        if (mLivePlayFragment != null) {
            mLivePlayFragment.initPageRank(bean.getContribute());
        }
    }

    @Override
    public void liveChosume(LiveConsume bean) {
        if (bean == null) {
            ToastUtils.showT("扣费失败");
            finish();
            return;
        }


        MyUserInstance.getInstance().setUserGold(bean.getGold());

        initConsume();

    }

    private void initConsumeDialog() {

        TipsDialog tipsDialog = new TipsDialog();
        tipsDialog.setmContent("您的金额不足观看接下来的直播,是否前往充值?");
        tipsDialog.setmTitle(getString(R.string.st_tips));
        tipsDialog.setCancelClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tipsDialog.setSubmitClick(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppManager.getAppManager().startActivity(ToPayActivity.class);
                finish();
            }
        });
        tipsDialog.show(getSupportFragmentManager(), "");
    }

    private void initLiveSocket() {

        TxImUtils.getSocketManager().initLiveListener(mAnchorid, new V2TIMSimpleMsgListener() {


            @Override
            public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
                super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);
                String result = new String(customData);
                if (!result.contains("action")) {
                    return;
                }
                initMessage(result);
            }
        });


    }

    private void initMessage(String result) {
        Log.e("IMTAGUser", result);
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
                V2TIMManager.getGroupManager().getGroupOnlineMemberCount(ArmsUtils.initLiveGroupId(mAnchorid), new V2TIMValueCallback<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        // 获取直播群在线人数成功
                        setMemberCount(integer);
                    }

                    @Override
                    public void onError(int code, String desc) {
                        // 获取直播群在线人数失败
                    }
                });

                break;
            case SocketConstants.LIVE_ACTION_LiveFinished:
                //结束直播
                initExitDialog();

                break;
            case SocketConstants.LIVE_ACTION_RoomAttentAnchor:
                //关注主播
                break;
            case SocketConstants.LIVE_ACTION_ExplainingGoods:
                //关注主播
                initHandlerMessage(SocketConstants.LIVE_SHOP_ITEM, bean);
                break;
            case SocketConstants.LIVE_ACTION_GiftAnimation:
                if (bean.getData().getGift() == null) {
                    return;
                }
                initHandlerMessage(SocketConstants.LIVE_GIFT_ANIMATE, bean);
                break;
            case SocketConstants.LIVE_ACTION_RoomNotification:
                switch (bean.getData().getNotify().getType()) {
                    case SocketConstants.SOCKET_NOTIFY_RoomNotificationReciveLinkRequest:

                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotificationAcceptLinkRequest:
                        //同意,主播同意了你的连麦请求,在这里拿播放url
                        initHandlerMessage(SocketConstants.LIVE_LINK_ACCEPT, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotificationRefuseLinkRequest:
                        //拒绝,清除参数
                        mMySelfPushUrl = "";
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

                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeLinkOn:
                        initHandlerMessage(SocketConstants.LIVE_LINK_ON, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeLinkOff:
                        initHandlerMessage(SocketConstants.LIVE_LINK_OFF, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeGuardAnchor:
                        initHandlerMessage(SocketConstants.LIVE_GUARDIAN_ANCHOR, bean);
                        break;

                }


                break;

        }
    }

    private void addChat(ImMessage bean) {

        if (bean.getData().getChat() != null) {
            if (bean.getData().getChat().getMessage().equals(getString(R.string.st_vip_enter_room))) {
                mStreamerUtils.addToViplist(bean, this);
            }

        }

        if (mLivePlayFragment != null) {
            mLivePlayFragment.addChat(bean);
        }
    }

    @Override
    protected void onDestroy() {

        if (mHomePlayer != null) {
            mHomePlayer.release();
        }

        if (mHandler != null) {
            mHandler.removeMessages(LIVE_CONSUME);
        }
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initConsume() {

        Message message = new Message();
        message.what = LIVE_CONSUME;
        mHandler.sendMessageDelayed(message, 60000);
    }

    private void initHandlerMessage(int what, ImMessage bean) {
        Message message = new Message();
        message.what = what;
        message.obj = bean;
        mHandler.sendMessage(message);
    }


    private void endPk() {
        if (mLivePlayFragment != null) {
            mLivePlayFragment.endPk();
        }
    }

    private void startPk() {
        if (mLivePlayFragment != null) {
            mLivePlayFragment.startPk();
        }
    }

    private void setMemberCount(int bean) {
        if (mLivePlayFragment != null) {
            mLivePlayFragment.setMemberCount(bean);
        }
    }

    private void showGift(ImMessage bean) {
        if (mLivePlayFragment != null) {
            mLivePlayFragment.showGift(bean);
        }
    }

    private void setScoreChange(ImMessage bean) {
        if (mLivePlayFragment != null) {
            mLivePlayFragment.setScoreChange(bean);
        }
    }

    private void initLiveLayout(ImMessage bean) {

        if (bean == null) {
            normalLayout();
        } else {
            mLiveInfo.setPk_status(1);
            mLiveInfo.setPklive(bean.getData().getNotify().getPklive());
            mLiveInfo.setPkid(bean.getData().getNotify().getPklive().getPkid());
            mLiveInfo.setPkinfo(bean.getData().getNotify().getPkinfo());

            if (mLivePlayFragment != null) {
                mLivePlayFragment.setLiveInfo(mLiveInfo);
            }
            startPk();
            //这里就涉及一个问题,用户看到的主播到底在哪边


            RelativeLayout.LayoutParams flRootParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    ArmsUtils.dip2px(context, 285));
            flRootParams.setMargins(RelativeLayout.LayoutParams.MATCH_PARENT, ArmsUtils.dip2px(context, 160), 0, 0);
            mTxcvHome.setLayoutParams(flRootParams);


        }


        initPlayer();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mHomePlayer != null) {
            mHomePlayer.pause();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHomePlayer != null) {
            mHomePlayer.resume();
        }


    }

    private void initLiveLayoutFirst(HotLive bean) {

        if (bean.getPkinfo() == null) {
            normalLayout();
        } else {
            mLiveInfo.setPk_status(bean.getPk_status());
            mLiveInfo.setPklive(bean.getPklive());
            mLiveInfo.setPkid(bean.getPklive().getPkid());
            mLiveInfo.setPkinfo(bean.getPkinfo());
            mLiveType=1;
            if (mLivePlayFragment != null) {
                mLivePlayFragment.setLiveInfo(mLiveInfo);
            }
            startPk();
            //这里就涉及一个问题,用户看到的主播到底在哪边


            RelativeLayout.LayoutParams flRootParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    ArmsUtils.dip2px(context, 285));
            flRootParams.setMargins(RelativeLayout.LayoutParams.MATCH_PARENT, ArmsUtils.dip2px(context, 160), 0, 0);
            mTxcvHome.setLayoutParams(flRootParams);


        }


        initPlayer();

    }

    private void initPlayer() {

        if (mHomePlayer == null) {
            return;
        }
        if (mHomePlayer != null) {
            mHomePlayer.release();
        }
        mHomePlayer.startLive(context, mLiveInfo);


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
        RelativeLayout.LayoutParams flRootParams2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        mTxcvHome.setLayoutParams(flRootParams2);
    }

    private void endLink() {
        if (mLivePlayFragment != null) {
            mLivePlayFragment.endLink();
        }
    }

    private void startLink() {
        if (ArmsUtils.isStringEmpty(mMySelfPushUrl)) {
            if (mLivePlayFragment != null) {
                mLivePlayFragment.startLink(mMySelfPushUrl);
            }
        }

    }

    private void setLink(boolean isLink) {
        if (mLivePlayFragment != null) {
            mLivePlayFragment.setLink(isLink);
        }
    }

    private void setManager(boolean status) {
        if (mLivePlayFragment != null) {
            mLivePlayFragment.setIsManager(status);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEnterRoomMessageMySelf(ImMessage message) {
        addChat(message);
    }

    private void initExitDialog() {


        TipsDialog tipsDialog = new TipsDialog();
        tipsDialog.setmContent("直播已结束");
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


}
