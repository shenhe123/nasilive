package com.feicui365.live.live.fragment;

import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.feicui365.live.eventbus.OnDanmuBus;
import com.feicui365.live.widget.Dialogs;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.base.SocketConstants;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.eventbus.OpenChatBus;
import com.feicui365.live.im.TxImSendUtils;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.interfaces.ChatClickLinstener;
import com.feicui365.live.interfaces.OnChoseGiftClickListener;
import com.feicui365.live.live.adapter.LiveChatHorAdapter;
import com.feicui365.live.live.bean.IsMgr;

import com.feicui365.live.live.dialog.GiftDialog2;
import com.feicui365.live.live.dialog.LiveChatDialog;
import com.feicui365.live.live.dialog.LiveGuardianDialog;
import com.feicui365.live.live.dialog.LiveRoomManagerDialog;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.weight.GiftAnimView;
import com.feicui365.live.live.weight.SpacingItemDecoration;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.MyUserInstance;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class LivePlayHorChatFragment2 extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View {

    /**
     * 需要分出来的点
     * 1,头像
     * 2,名字
     * 3,人气
     * 4,在线人数
     * 5,贡献榜
     * 6,守护人
     * 7,收入钻石
     * 8,聊天信息
     * 9,PK相关
     */
    @BindView(R.id.rv_chat)
    RecyclerView mRvChat;
    @BindView(R.id.rl_top)
    RelativeLayout mPlayRoot;
    @BindView(R.id.iv_manager)
    ImageView ivManager;


    private HotLive mLiveInfo;
    private LiveChatDialog mChatDialog;
    private LiveChatHorAdapter mChatAdapter;
    private ArrayList<ImMessage> mChatList;

    private int isGuardian = 0;
    private int isManager = 0;

    private String mAnchordID;

    private GiftAnimView mAnimView;
    private boolean isInitPage = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SocketConstants.LIVE_GET_LIVE_INFO:
                    mPresenter.getAnchorLiveInfo(mAnchordID);
                    break;
            }
        }
    };


    @Override
    public void onDestroyView() {


        TxImSendUtils.exitRoom(mAnchordID);
        TxImUtils.getSocketManager().cleanLiveListener();
        EventBus.getDefault().unregister(this);
        if (mHandler != null) {
            mHandler.removeMessages(SocketConstants.LIVE_GET_LIVE_INFO);
        }
        super.onDestroyView();
    }

    private Dialog dialog;

    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(getContext());
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


    public static LivePlayHorChatFragment2 newInstance() {
        LivePlayHorChatFragment2 fragment = new LivePlayHorChatFragment2();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        initList();
        EventBus.getDefault().register(this);
        if (!isInitPage) {
            initData();
        }
    }

    //懒加载出来后不做任何处理
    @Override
    protected void lazyLoad() {


    }

    //核心API,用以启动整个页面
    public void setLiveInfo(HotLive bean) {
        //如果mLiveinfo不为空表示可能是房间内切换,记得退出房间
        isManager = 0;
        isGuardian = 0;

        if (mLiveInfo != null) {
            //如果房间ID一致,表示没有换房间不用处理
            if (!mLiveInfo.getAnchorid().equals(bean.getAnchorid())) {
                TxImSendUtils.exitRoom(mLiveInfo.getAnchorid());
                TxImSendUtils.joinRoom(bean.getAnchorid(), 1);
            }
        } else {
            TxImSendUtils.joinRoom(bean.getAnchorid(), 1);
        }


        mLiveInfo = bean;
        mAnchordID = mLiveInfo.getAnchorid();

        initData();
    }


    @Override
    public void checkIsMgr2(IsMgr bean) {
        if (bean.getIs_mgr() == 0) {
            setIsManager(false);
        } else {
            setIsManager(true);
        }


    }

    @Override
    public void getGuardInfo(GuardianInfo bean) {
        if (bean == null) {
            isGuardian = 0;
        } else {
            isGuardian = 1;
        }
    }


    /**
     * 1,获取主播基本数据
     * 2,获取直播全部数据
     * 3,检查是否是管理员
     * 4,检查是否是守护
     * 只调用一次
     */
    private void initData() {
        if (mPresenter != null) {
            isInitPage = true;
            mPresenter.getAnchorLiveInfo(mAnchordID);
            mPresenter.checkIsMgr2(mLiveInfo.getAnchorid());
            mPresenter.getGuardInfo(mLiveInfo.getAnchorid());
        }


    }

    @Override
    public void getAnchorLiveInfo(LiveInfo response) {
        mHandler.sendEmptyMessageDelayed(SocketConstants.LIVE_GET_LIVE_INFO, 60000);
    }


    private void initList() {
        if (mChatAdapter == null) {
            mChatList = new ArrayList<>();
            mChatAdapter = new LiveChatHorAdapter();
            mChatAdapter.setNewData(mChatList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

            linearLayoutManager.scrollToPositionWithOffset(mChatAdapter.getItemCount() - 1, Integer.MIN_VALUE);
            if (mRvChat.getItemDecorationCount() == 0) {
                mRvChat.addItemDecoration(new SpacingItemDecoration(getActivity(), 3, 3, 0, 0));
            }

            mRvChat.setLayoutManager(linearLayoutManager);
            mRvChat.setAdapter(mChatAdapter);
            mChatAdapter.setChatClickLinstener(new ChatClickLinstener() {
                @Override
                public void onChatClick(ImMessage messageBean) {


                    initUserInfoDialog(messageBean);
                }
            });
           // mChatAdapter.addData(TxImSendUtils.getNotice());
        } else {
            mChatList.clear();
            mChatAdapter.notifyDataSetChanged();
        }


    }

    private void initUserInfoDialog(ImMessage bean) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        if (bean.getData().getChat() == null) {
            return;
        }
        if (bean.getData().getChat().getSender() == null) {
            return;
        }
        UserRegist userInfo = bean.getData().getChat().getSender();
        int type = 0;
        if (isManager == 1) {
            type = 1;
        }
        ArmsUtils.initUserInfoDialog(userInfo.getId(), type, mLiveInfo.getAnchorid(), this);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.live_play_hor_chat_fragment_2;
    }

    /**
     * 注释
     * 初始化直播时间
     */


    private void initChatDialog(int type) {
        if (mChatDialog == null) {
            mChatDialog = new LiveChatDialog();

            mChatDialog.setOnComentSendListener(new LiveChatDialog.OnComentSendListener() {
                @Override
                public void onSend(String comment) {
                    addChat(TxImSendUtils.sendMessage(comment, mAnchordID, isManager, isGuardian));
                }

                @Override
                public void onDismiss() {

                }
            });
        }

        if (!mChatDialog.isAdded()) {
            mChatDialog.setType(type);
            mChatDialog.show(getChildFragmentManager(), "");
        }

    }

    @OnClick({R.id.chat_et, R.id.iv_manager, R.id.iv_guardian, R.id.iv_gift})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.chat_et:
                initChatDialog(1);
                break;
            case R.id.iv_manager:
                LiveRoomManagerDialog dialog = new LiveRoomManagerDialog(mLiveInfo.getAnchorid(), 0);
                dialog.show(getChildFragmentManager(), "");
                break;
            case R.id.iv_guardian:
                if (mLiveInfo == null) {
                    return;
                }
                LiveGuardianDialog guardianDialog = new LiveGuardianDialog(mLiveInfo.getAnchorid());
                guardianDialog.show(getChildFragmentManager(), "");
                break;
            case R.id.iv_gift:
                if (mLiveInfo == null) {
                    return;
                }
                initGiftDialog();
                break;


        }
    }


    private void initGiftDialog() {
        GiftDialog2 giftDialog = new GiftDialog2(true);
        giftDialog.setIsGuardian(isGuardian);
        giftDialog.show(getChildFragmentManager(), "");
        giftDialog.setOnChoseGiftClickListener(new OnChoseGiftClickListener() {
            @Override
            public void onGiftClick(GiftInfo bean, int count) {

                mPresenter.sendGift2(mAnchordID, bean.getId(), mLiveInfo.getLiveid(), count, null, null);

            }


        });
    }


    //外部控制区
    public void showGift(ImMessage bean) {
        if (!MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
            return;
        }
        if (bean.getData().getGift() == null) {
            return;
        }
        if (mAnimView == null) {
            mAnimView = new GiftAnimView(getContext(), mPlayRoot);
            mAnimView.addToParent();
        }
        if (!getActivity().isFinishing()) {
            mAnimView.showGiftAnim(bean.getData().getGift());
        }
    }


    public void addChat(ImMessage bean) {
        if (mChatAdapter != null) {
            mChatAdapter.addData(bean);
            mRvChat.scrollToPosition(mChatAdapter.getData().size() - 1);
        }EventBus.getDefault().post(new OnDanmuBus(bean));

    }


    public void setIsManager(boolean status) {
        if (status) {
            isManager = 1;
            ivManager.setVisibility(View.VISIBLE);
        } else {
            isManager = 0;
            ivManager.setVisibility(View.GONE);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChatOpen(OpenChatBus message) {
        initChatDialog(2);
    }
}
