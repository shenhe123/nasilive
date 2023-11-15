package com.feicui365.live.live.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.dialog.AudienceListDialog;
import com.feicui365.live.dialog.BaseDialog;
import com.feicui365.live.dialog.SetDialog;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.dialog.ShareLiveDialog;
import com.feicui365.live.eventbus.ChangeCarmerBus;
import com.feicui365.live.eventbus.OnLiveFinishBus;
import com.feicui365.live.eventbus.OpenAudioBus;
import com.feicui365.live.eventbus.OpenBeautyBus;
import com.feicui365.live.eventbus.PauseAudioBus;
import com.feicui365.live.im.TxImSendUtils;
import com.feicui365.live.interfaces.ChatClickLinstener;
import com.feicui365.live.interfaces.OnAnchorBottomClickListener;
import com.feicui365.live.interfaces.OnLiveUtilsListener;
import com.feicui365.live.live.adapter.LiveChatVerticalAdapter;

import com.feicui365.live.live.dialog.AnchorBottomDialog;
import com.feicui365.live.live.dialog.LiveRankDialog;
import com.feicui365.live.live.dialog.LiveRoomManagerDialog;
import com.feicui365.live.live.utils.AnchorLivePushUtils;
import com.feicui365.live.live.utils.AnchorPushLayoutUtils2;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.live.utils.LinkUtils;
import com.feicui365.live.live.weight.GiftAnimView;
import com.feicui365.live.live.weight.SpacingItemDecoration;
import com.feicui365.live.model.entity.AllUser;
import com.feicui365.live.model.entity.CheckAttend;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveAudienceListBean;
import com.feicui365.live.model.entity.LivePoster;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.tencent.live2.V2TXLiveDef;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.OnClick;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 *
 */
public class LivePushFragment2 extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View {

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


    @BindView(R.id.rl_push_fragment_root)
    RelativeLayout mPushRoot;

    @BindView(R.id.tv_watchers)
    TextView mTvWatchers;


    @BindView(R.id.tv_income_diamonds)
    TextView mIncomeDimonds;
    @BindView(R.id.rv_chat)
    RecyclerView mRvChat;
    @BindView(R.id.tv_time)
    Chronometer mChrTime;
    @BindView(R.id.rl_pk_status)
    CardView mCvPkStatus;
    @BindView(R.id.giv_bg_pk)
    GifImageView mPkStatusBg;
    @BindView(R.id.tv_pk_status)
    TextView mTvPkStatus;
    @BindView(R.id.iv_mute)
    TextView mIvMute;


    private GiftAnimView mAnimView;
    private LiveChatVerticalAdapter mChatAdapter;
    private ArrayList<ImMessage> mChatList;
    private HotLive mLiveInfo;
    private GifDrawable mPkRunningDrawable;
    private AnchorBottomDialog mBottomDialog;
    private AnchorPushLayoutUtils2 mPushLayoutUtils;
    private LinearLayoutManager mLayoutManager;
    private LinkUtils mLinkUtils;
    private int mPkStatus;
    private boolean isInit = false;
    private boolean mLinkType = false;
    private View.OnClickListener onClickListener;
    private static AnchorLivePushUtils mAnchorPushUtils;
    private int mirrorType = 1;
    LiveRoomManagerDialog liveRoomManagerDialog;
    AudienceListDialog audienceListDialog;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public static LivePushFragment2 newInstance(AnchorLivePushUtils mAnchorPushUtil) {
        LivePushFragment2 fragment = new LivePushFragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        mAnchorPushUtils = mAnchorPushUtil;
        return fragment;
    }


    //主要是确定是否有过初始化,如果没有的话,就初始化以后在重新刷新页面
    @Override
    protected void lazyLoad() {
        if (!isInit) {
            isInit = true;
            initPage();
        }
    }

    public void setLiveInfo(HotLive mLiveInfo) {
        this.mLiveInfo = mLiveInfo;
        if (isInit) {
            initPage();
        }

    }


    //初始化基本页面
    private void initPage() {
        initList();
        initPushLayout();
        initLiveNums();
    }


    public void setLiveIncomeNums(int profit) {
        if (mIncomeDimonds != null) {
            mIncomeDimonds.setText(String.valueOf(profit));
        }
    }

    //设置直播观看人数
    private void initLiveNums() {
        mIncomeDimonds.setText(String.valueOf(mLiveInfo.getProfit()));
        mTvWatchers.setText(getString(R.string.st_live_watcher, String.valueOf(0)));

    }

    @Override
    protected void initView(View view) {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        initLiveTime();

    }

    //直播界面初始化
    private void initPushLayout() {
        if (mPushLayoutUtils == null) {
            mPushLayoutUtils = new AnchorPushLayoutUtils2(getContext());
            mPushLayoutUtils.addView(mPushRoot);
        }

        mPushLayoutUtils.startLive(mLiveInfo);
        mPushLayoutUtils.setOnLiveUtilsListener(new OnLiveUtilsListener() {
            @Override
            public void onPkEnd() {
                endPk();
            }

            @Override
            public void onPkStart(String id) {
                mPresenter.getUserInfoById(id, 0);
                mPresenter.checkAttent(id, 1);
            }

            @Override
            public void onPkAnchoorAttend(String id) {
                mPresenter.attentAnchor(id, "1");
            }
        });
    }

    //根据type来确定获取到的用户数据是做什么的
    @Override
    public void getUserInfoById(UserRegist bean, int type) {
        mPushLayoutUtils.setAwayUserInfo(bean);
    }


    //检查PK对象是否已关注,如果尚未关注,显示按钮
    @Override
    public void checkAttent(CheckAttend bean, int type) {
        if (type == 0) {

        } else {
            if (bean.getAttented() == 0) {
                mPushLayoutUtils.showFollowButton();
            }
        }
    }

    //初始化聊天界面
    private void initList() {
        if (mChatAdapter == null) {
            mChatList = new ArrayList<>();
            mChatAdapter = new LiveChatVerticalAdapter();
            mChatAdapter.addData(mChatList);

            mLayoutManager = new LinearLayoutManager(getContext());
            mLayoutManager.setStackFromEnd(true);
            mLayoutManager.scrollToPositionWithOffset(mChatAdapter.getItemCount() - 1, Integer.MIN_VALUE);
            if (mRvChat.getItemDecorationCount() == 0) {
                mRvChat.addItemDecoration(new SpacingItemDecoration(getActivity(), 3, 3, 0, 0));
            }
            mRvChat.setLayoutManager(mLayoutManager);
            mRvChat.setAdapter(mChatAdapter);
            mChatAdapter.setChatClickLinstener(new ChatClickLinstener() {
                @Override
                public void onChatClick(ImMessage messageBean) {


                    //  initUserInfoDialog(messageBean);
                }
            });
            //  mChatAdapter.addData(TxImSendUtils.getNotice());

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
        ArmsUtils.initUserInfoDialog(userInfo.getId(), 2, mLiveInfo.getAnchorid(), this);

    }


    @Override
    protected int getLayoutId() {
        return R.layout.live_push_fragment_2;
    }

    /**
     * 注释
     * 初始化直播时间
     */
    private void initLiveTime() {


        mChrTime.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer cArg) {
                long time = System.currentTimeMillis() - cArg.getBase();
                Date d = new Date(time);
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                mChrTime.setText(sdf.format(d));
            }
        });
        mChrTime.setBase(System.currentTimeMillis());
        mChrTime.start();
    }


    /**
     * 界面更新
     */
    public void setMemberCount(int data) {
        if (getActivity() == null) {
            return;
        }
        if (getActivity().isFinishing()) {
            return;
        }
        loadAllUserData();
        if (liveRoomManagerDialog != null && liveRoomManagerDialog.getShowsDialog()) {
            liveRoomManagerDialog.refreshData();
        }
        if (audienceListDialog != null && audienceListDialog.isShowing()) {
            if (mPresenter != null && mLiveInfo != null) {
                mPresenter.getLiveViewerList(mLiveInfo.getLiveid());
            }
        }
//        mTvWatchers.setText(getString(R.string.st_live_watcher, String.valueOf(data)));
    }


    public void loadAllUserData() {
        mPresenter.getAllUserList();
    }

    @Override
    public void getAllUserList(AllUser bean) {
        if (bean != null) {
            Log.e("IMTAGAnchor", "=====在线人数=" + bean.getCount());
            mTvWatchers.setText(getString(R.string.st_live_watcher, String.valueOf(bean.getCount())));
        }
    }

    public void setScoreChange(ImMessage messageBean) {

        mPushLayoutUtils.changeScoure(messageBean);
    }


    public void showGift(ImMessage bean) {
        if (bean.getData().getGift() == null) {
            return;
        }

        if (mAnimView == null) {
            mAnimView = new GiftAnimView(getContext(), mPushRoot);
            mAnimView.addToParent();
        }
        if (!getActivity().isFinishing()) {
            mAnimView.showGiftAnim(bean.getData().getGift());
        }


    }

    //添加聊天内容
    public void addChat(ImMessage bean) {
        Log.e("111111", "==聊天消息=" + bean);
        if (mChatAdapter != null) {
            mChatAdapter.addData(bean);
            mRvChat.scrollToPosition(mChatAdapter.getData().size() - 1);
        }

    }


    @Override
    public void onDestroyView() {
        //关闭计时
        if (mChrTime != null) {
            mChrTime.stop();
        }
        if (mPushLayoutUtils != null) {
            mPushLayoutUtils.relase();
        }


        endLink();
        super.onDestroyView();
    }

    private GifDrawable initPKRuningDrawable() {
        try {
            if (mPkRunningDrawable == null) {
                mPkRunningDrawable = new GifDrawable(getActivity().getAssets(), "pk_finding.gif");
            }
            return mPkRunningDrawable;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initBottomDialog() {
        if (mBottomDialog == null) {
            mBottomDialog = new AnchorBottomDialog(mLinkType);
            mBottomDialog.setmAnchorId(mLiveInfo.getAnchorid());
            mBottomDialog.setOnAnchorBottomClickListener(new OnAnchorBottomClickListener() {
                @Override
                public void onLinkClick() {
                    mLinkType = !mLinkType;
                }

                @Override
                public void onPkClick() {
                    if (mPkStatus == 0) {
                        mPkStatus = 1;
                        mPresenter.enterPkMode();
                        initPkStatus();
                    } else {
                        ToastUtils.showT(getString(R.string.st_pk_is_running));
                    }
                }
            });
        }

        mBottomDialog.show(getChildFragmentManager(), "");

    }

    @OnClick({R.id.iv_more, R.id.rl_pk_status, R.id.tv_live_rank, R.id.ll_watcher,
            R.id.iv_camera, R.id.iv_beauty, R.id.iv_share, R.id.iv_close, R.id.iv_mute, R.id.iv_set, R.id.tv_watchers})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_more:
                if (mLiveInfo == null) {
                    return;
                }
                if (!ArmsUtils.isStringEmpty(mLiveInfo.getAnchorid())) {
                    return;
                }

                liveRoomManagerDialog = new LiveRoomManagerDialog(mLiveInfo.getAnchorid(), 1);
                liveRoomManagerDialog.show(getChildFragmentManager(), "");
                break;
            case R.id.rl_pk_status:
                if (mPkStatus == 0) {
                    return;
                }
                endPk();
                break;
            case R.id.iv_mute:
                String close = mIvMute.getText().toString();
                if (TextUtils.equals(close, "闭麦")) {
                    mIvMute.setText("开麦");
                    Drawable drawable = getResources().getDrawable(R.mipmap.live_close_mic);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mIvMute.setCompoundDrawables(null, drawable, null, null);
                    EventBus.getDefault().post(new PauseAudioBus());
                } else {
                    mIvMute.setText("闭麦");
                    Drawable drawable = getResources().getDrawable(R.mipmap.live_mic);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    mIvMute.setCompoundDrawables(null, drawable, null, null);
                    EventBus.getDefault().post(new OpenAudioBus());
                }
                break;
            case R.id.tv_live_rank:
                LiveRankDialog rankDialog = new LiveRankDialog(mLiveInfo.getLiveid());
                rankDialog.show(getChildFragmentManager(), "");
                break;
            case R.id.iv_camera:
                EventBus.getDefault().post(new ChangeCarmerBus());
                break;
            case R.id.iv_beauty:
                EventBus.getDefault().post(new OpenBeautyBus());
                break;
            case R.id.iv_share:
              /*  if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    GlideUtils.setImageTarget(getContext(), mLiveInfo.getThumb(), new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                            initShareDialog(resource);
                        }

                        @Override
                        public void onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable Drawable errorDrawable) {
                            super.onLoadFailed(errorDrawable);
                            initShareDialog(null);
                        }
                    });

                }*/

                if (mLiveInfo == null) {
                    return;
                }
                if (!ArmsUtils.isStringEmpty(mLiveInfo.getAnchorid())) {
                    return;
                }

                mPresenter.getLivePoster(mLiveInfo.getLiveid());

                break;
            case R.id.iv_close:
                EventBus.getDefault().post(new OnLiveFinishBus());
                break;
            case R.id.iv_set:
                if (isFastClick()) {
                    return;
                }
                SetDialog setDialog = SetDialog.create(getContext(), mirrorType);
                setDialog.setOnClickCallback(new BaseDialog.OnClickCallback() {
                    @Override
                    public void onClickType(int type) {
                        mirrorType = type;
                        if (type == 1) {
                            mAnchorPushUtils.getLivePusher().setRenderMirror(V2TXLiveDef.V2TXLiveMirrorType.V2TXLiveMirrorTypeEnable);
                        } else {
                            mAnchorPushUtils.getLivePusher().setRenderMirror(V2TXLiveDef.V2TXLiveMirrorType.V2TXLiveMirrorTypeDisable);
                        }
                    }
                });
                break;
            case R.id.tv_watchers:
            case R.id.ll_watcher:
                if (isFastClick()) {
                    return;
                }
                audienceListDialog = AudienceListDialog.create(getContext());
                mPresenter.getLiveViewerList(mLiveInfo.getLiveid());
                loadAllUserData();
                break;
        }
    }

    @Override
    public void getLiveViewerList(ArrayList<LiveAudienceListBean> bean) {
        audienceListDialog.addData(bean);
    }

    private void initShareDialog(Drawable resource) {

        ShareDialog.Builder builder = new ShareDialog.Builder(getActivity());
        builder.setShare_url(MyUserInstance.getInstance().getUserConfig().getConfig().getShare_live_url() + mLiveInfo.getAnchorid());
        builder.create().show();
        builder.showBottom2();
        builder.setContent(mLiveInfo.getTitle());
        builder.setTitle("推荐你看这个直播");
        builder.hideCollect();
        if (resource != null) {
            builder.setTumb(ArmsUtils.DrawableToBitmap(resource));
        }

        builder.setType("1");
        builder.setId(mLiveInfo.getAnchorid());


    }

    //更新PK状态
    private void initPkStatus() {
        //0终止或者未开始,1搜索中,2正式开始
        switch (mPkStatus) {
            case 0:

                if (mPkRunningDrawable != null) {
                    mPkRunningDrawable.stop();
                    mPkRunningDrawable.recycle();
                    mPkRunningDrawable = null;
                }

                mCvPkStatus.setVisibility(View.GONE);
                mPkStatusBg.setImageDrawable(null);
                mTvPkStatus.setText(getString(R.string.st_pk_searching));
                break;

            case 1:
                mCvPkStatus.setVisibility(View.VISIBLE);
                mPkStatusBg.setImageDrawable(initPKRuningDrawable());
                mTvPkStatus.setText(getString(R.string.st_pk_searching));
                break;
            case 2:
                if (mCvPkStatus.getVisibility() != View.VISIBLE) {
                    mCvPkStatus.setVisibility(View.VISIBLE);
                    mPkStatusBg.setImageDrawable(initPKRuningDrawable());
                }
                mTvPkStatus.setText(getString(R.string.st_pk_end));


                break;
        }

    }

    //切换PK开始状态
    public void startPk() {
        mPkStatus = 2;
        initPkStatus();
    }

    //切换PK结束状态
    public void endPk() {
        mPkStatus = 0;
        initPkStatus();
        mPresenter.endPk();
    }

    //开始连麦
    public void startLink(String url, String id) {
        //主播端主要负责播放
        if (mLinkUtils != null) {
            return;
        }
        mLinkUtils = new LinkUtils(getContext(), mPushRoot, id);
        mLinkUtils.initLinkView(url);
        mLinkUtils.initPlay();
        mLinkUtils.setOnClickListener(new LinkUtils.OnStopClickListener() {
            @Override
            public void onStop(String id) {
                mPresenter.stopMlvbLink(mLiveInfo.getAnchorid(), id);
                endLink();
                if (onClickListener != null) {
                    onClickListener.onClick(getView());
                }
            }
        });
    }

    //结束连麦
    public void endLink() {
        if (mLinkUtils != null) {
            mLinkUtils.initClose();
            mLinkUtils = null;
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void LivePoster(LivePoster bean) {
        ShareLiveDialog shareLiveDialog = ShareLiveDialog.create(getContext());
        shareLiveDialog.setNickName(bean.getNick_name())
                .setDesc(bean.getEwm_desc())
                .setIntroduce(bean.getDesc())
                .setHeadUrl(bean.getAvatar())
                .setThumbUrl(bean.getThumb())
                .setQrCode(bean.getEwm_url());
        shareLiveDialog.setOnClickCallback(new BaseDialog.OnClickCallback() {
            @Override
            public void onClickType(int type) {
                switch (type) {
                    case BaseDialog.SELECT_SECOND:
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                        // 创建普通字符型
                        ClipData mClipData = ClipData.newPlainText("Label", MyUserInstance.getInstance().getUserConfig().getConfig().getShare_live_url() + mLiveInfo.getAnchorid());
                        // 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        ToastUtils.showT("复制成功");
                        break;
                }
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }


}
