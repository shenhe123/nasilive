package com.feicui365.live.live.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import com.dueeeke.videoplayer.player.VideoView;
import com.feicui365.live.eventbus.OnLiveCloseBus;
import com.feicui365.live.widget.MyStandardLiveController;
import com.feicui365.nasinet.utils.AppManager;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.base.SocketConstants;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.live.dialog.TipsDialog;
import com.feicui365.live.live.fragment.LivePlayHorChatFragment2;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.StreamerUtils;
import com.feicui365.live.live.weight.CommonNavigatorUtils;
import com.feicui365.live.live.weight.MySimplePagerTitleView;
import com.feicui365.live.live.weight.PagerFragmentAdapter;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveConsume;
import com.feicui365.live.model.entity.LiveInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.ui.act.ToPayActivity;
import com.feicui365.live.ui.fragment.AnchorFragment;
import com.feicui365.live.ui.fragment.ContributionFragment;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class LivePlayHorActivity2 extends BaseMvpActivity<LivePushPresenter> implements LivePushContrat.View {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.video_player)
    VideoView mVideoView;

    @BindView(R.id.tv_follow)
    TextView mTvFollow;

    @BindView(R.id.mic_layout)
    MagicIndicator mMicLayout;
    private  MyStandardLiveController mb;
    private PagerFragmentAdapter mFragmentAdapter;
    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;
    private LivePlayHorChatFragment2 mLivePlayFragment;
    private AnchorFragment anchorFragment;
    private ContributionFragment contributionFragment;

    private HotLive mLiveInfo;
    private String mAnchorid;
    private int isFollow;


    private StreamerUtils mStreamerUtils;
    private final int LIVE_CONSUME = 1000000;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ImMessage bean = (ImMessage) msg.obj;
            switch (msg.what) {

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
                    addChat(bean);
                    break;
                case SocketConstants.LIVE_STREAMER_VIP:
                    if (mStreamerUtils != null) {
                        mStreamerUtils.addToViplist(bean, (Activity) context);
                    }
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
                    }else{
                        mPresenter.liveChosume(mLiveInfo.getLiveid());
                    }
                    mPresenter.liveChosume(mLiveInfo.getLiveid());
                    break;
            }
        }
    };

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_hor_activity_2;
    }

    @Override
    protected void initData() {
        mPresenter.getAnchorLiveInfo(mAnchorid);
        mPresenter.getUserInfoById(mAnchorid,0);
    }

    @Override
    public void getUserInfoById(UserRegist bean, int type) {
        isFollow = bean.getIsattent();

        if (isFollow == 1) {
            mTvFollow.setText("已关注");
        } else {
            mTvFollow.setText("关注");
        }
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

    @Override
    public void onBackPressed() {

        if(mVideoView==null){
            super.onBackPressed();
        }else{
            if(mVideoView.isFullScreen()) {
                mVideoView.stopFullScreen();
            }else{
                super.onBackPressed();
            }

        }
    }

    private void initViewPager() {
        //聊天 主播 贡献榜
        mFragments = new ArrayList<>();
        mLivePlayFragment = LivePlayHorChatFragment2.newInstance();
        mLivePlayFragment.setLiveInfo(mLiveInfo);
        mFragments.add(mLivePlayFragment);


        anchorFragment = new AnchorFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("anchor", mAnchorid);
        bundle.putString("roomid", mLiveInfo.getLiveid());
        anchorFragment.setArguments(bundle);
        mFragments.add(anchorFragment);

        contributionFragment = new ContributionFragment();
        bundle.putString("id", mLiveInfo.getLiveid());
        contributionFragment.setArguments(bundle);
        mFragments.add(contributionFragment);
        mFragmentAdapter = new PagerFragmentAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mFragmentAdapter);

    }

    private void initTab() {
        mTitles = new ArrayList<>();
        mTitles.add("聊天");
        mTitles.add("主播");
        mTitles.add("贡献榜");


        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitles == null ? 0 : mTitles.size();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {

                MySimplePagerTitleView simplePagerTitleView = new MySimplePagerTitleView(context);

                CommonNavigatorUtils.initTitleView(simplePagerTitleView, context, mTitles.get(index),
                        R.color.black, R.color.black, 16);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {

                return CommonNavigatorUtils.initLine(context, 20, 0, R.color.color_AC74FF);
            }


        });
        mMicLayout.setNavigator(commonNavigator);

        ViewPagerHelper.bind(mMicLayout, mViewPager);


    }

    @Override
    public void getAnchorLiveInfo(LiveInfo bean) {
        mLiveInfo = bean.getLive();
        if (mLiveInfo.getRoom_type().equals("2")) {
            mPresenter.liveChosume(mLiveInfo.getLiveid());
        }

        initPlayer();
        mViewPager.post(new Runnable() {
            @Override
            public void run() {
                initViewPager();
                initTab();
            }
        });


        if (mLivePlayFragment != null) {
            mLivePlayFragment.setLiveInfo(mLiveInfo);
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

                break;
            case SocketConstants.LIVE_ACTION_LiveFinished:
                //结束直播
                initExitDialog();
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
                switch (bean.getData().getNotify().getType()) {
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeSetManager:
                        initHandlerMessage(SocketConstants.LIVE_SET_MANAGER, bean);
                        break;
                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeCancelManager:
                        initHandlerMessage(SocketConstants.LIVE_CANCEL_MANAGER, bean);
                        break;

                    case SocketConstants.SOCKET_NOTIFY_RoomNotifyTypeGuardAnchor:
                        break;

                }
                break;

        }
    }

    private void addChat(ImMessage bean) {

        if(bean.getData().getChat()!=null){
            if(bean.getData().getChat().getMessage().equals(getString(R.string.st_vip_enter_room))){
                mStreamerUtils.addToViplist(bean,this);
            }
        }

        if (mLivePlayFragment != null) {
            mLivePlayFragment.addChat(bean);


        }
    }

    @Override
    protected void onDestroy() {

        if (mVideoView != null) {
            mVideoView.release();
        }

        if (mHandler != null) {
            mHandler.removeMessages(LIVE_CONSUME);
        }
        EventBus.getDefault().post(new OnLiveCloseBus());
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




    private void showGift(ImMessage bean) {
        if (mLivePlayFragment != null) {
            mLivePlayFragment.showGift(bean);
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        if(mVideoView!=null){
            mVideoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mVideoView!=null){
            mVideoView.resume();
        }
    }



    private void initPlayer() {

        mb = new MyStandardLiveController(this);
        mVideoView.setVideoController(mb);
        mVideoView.setScreenScale(VideoView.SCREEN_SCALE_DEFAULT);
        mVideoView.setLooping(true);
        mVideoView.setUsingSurfaceView(false);
        mVideoView.setUrl(mLiveInfo.getPull_url());
        mVideoView.start();
        if(mLiveInfo!=null){
            mb.setHot(mLiveInfo.getHot());
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


    @OnClick({ R.id.tv_follow})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_follow:
                switch (isFollow) {

                    case 0:
                        mPresenter.attentAnchor(mAnchorid, "1");
                        isFollow = 1;
                        mTvFollow.setText("已关注");
                        break;
                    case 1:
                        mPresenter.attentAnchor(mAnchorid, "0");
                        isFollow = 0;
                        mTvFollow.setText("关注");
                        break;
                }
                break;
        }

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
