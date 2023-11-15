package com.feicui365.live.live.fragment;

import android.Manifest;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.feicui365.live.live.dialog.LiveShopDialog;
import com.feicui365.live.live.dialog.ShowShopItemDialog;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.widget.Dialogs;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.base.SocketConstants;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.eventbus.BuyGuardianBus;
import com.feicui365.live.im.TxImSendUtils;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.interfaces.ChatClickLinstener;
import com.feicui365.live.interfaces.OnChoseGiftClickListener;
import com.feicui365.live.interfaces.OnLiveUtilsListener;
import com.feicui365.live.live.adapter.LiveChatVerticalAdapter;
import com.feicui365.live.live.adapter.LivePageRankAdaper;
import com.feicui365.live.live.adapter.LivePlayerBottomAdapter;
import com.feicui365.live.live.bean.BottomItem;
import com.feicui365.live.live.bean.IsMgr;
import com.feicui365.live.live.bean.LinkInfo;


import com.feicui365.live.live.dialog.GiftDialog2;
import com.feicui365.live.live.dialog.LiveChatDialog;
import com.feicui365.live.live.dialog.LiveGuardianDialog;
import com.feicui365.live.live.dialog.LiveRankDialog;
import com.feicui365.live.live.dialog.LiveRoomManagerDialog;
import com.feicui365.live.live.utils.AdapterLayoutUtils;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.BaseInitUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.live.utils.LinkUtils;
import com.feicui365.live.live.utils.UserPlayLayoutUtils2;
import com.feicui365.live.live.weight.GiftAnimView;
import com.feicui365.live.live.weight.SpacingItemDecoration;

import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.model.entity.Count;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
public class LivePlayVerticalFragment2 extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View {

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
    @BindView(R.id.iv_follow)
    ImageView mIvFollow;
    @BindView(R.id.civ_avatar)
    CircleImageView mLiveAvatar;
    @BindView(R.id.tv_anchor_name)
    TextView mLiveName;
    @BindView(R.id.tv_live_hot)
    TextView mLiveHot;
    @BindView(R.id.rv_rank)
    RecyclerView mRvRank;
    @BindView(R.id.tv_nums)
    TextView mTvWatchers;
    @BindView(R.id.ll_ban)
    LinearLayout mLiveBan;
    @BindView(R.id.rl_play_root)
    RelativeLayout mPlayRoot;
    @BindView(R.id.rv_bottom)
    RecyclerView mRvList;
    @BindView(R.id.tv_guardian)
    TextView mTvGuardianNum;


    private LivePlayerBottomAdapter mBottomAdapter;
    private ArrayList<BottomItem> bottomItems;
    private HotLive mLiveInfo;
    private LiveChatDialog mChatDialog;
    private LiveChatVerticalAdapter mChatAdapter;
    private ArrayList<ImMessage> mChatList;
    private UserPlayLayoutUtils2 mPlayerUtils;
    private int isGuardian = 0;
    private int isManager = 0;
    private int mPkStatus = 0;//0没PK,1主场,2客场
    private int isFollow = 0;
    private boolean isLink = false;
    private UserRegist mAnchor;
    private String mAnchordID;
    private LinkUtils mLinkUtils;
    private OnUrlSetListener onUrlSetListener;
    private GiftAnimView mAnimView;
    private LivePageRankAdaper mPageRankAdaper;
    private ShowShopItemDialog itemDialog;
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

    public void setOnUrlSetListener(OnUrlSetListener onUrlSetListener) {
        this.onUrlSetListener = onUrlSetListener;
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

    public interface OnUrlSetListener {
        void setPushUrl(String url);
    }

    public static LivePlayVerticalFragment2 newInstance(HotLive data) {
        LivePlayVerticalFragment2 fragment = new LivePlayVerticalFragment2();
        return fragment;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        //初始化底部内容,等待传入LiveInfo
        initBottomList();
        initList();
        EventBus.getDefault().register(this);
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
        initPlayLayout();
        initData();
        mPresenter.getGoodsList(mAnchordID);
    }

    @Override
    public void getGoodsList(ArrayList<Good> bean) {
        if(bean.get(0).getLive_explaining().equals("1")){
            showShopItemDialog(bean.get(0));
        }
    }

    public void showShopItemDialog(Good bean){

        if(bean.getLive_explaining().equals("1")){
            if(itemDialog!=null){
                if(itemDialog.isAdded()){
                    itemDialog.dismiss();
                }
            }
            itemDialog=new ShowShopItemDialog(bean);
            itemDialog.show(getChildFragmentManager(),"");
        }


    }

    public void initPageRank(ArrayList<ContributeRank> data) {
        if (!ArmsUtils.isArrEmpty(data)) {
            return;
        }
        if (mRvRank == null) {
            return;
        }
        if (mPageRankAdaper == null) {
            mPageRankAdaper = new LivePageRankAdaper();
            mRvRank.setLayoutManager(AdapterLayoutUtils.getLin(getContext()));
            mRvRank.setAdapter(mPageRankAdaper);
            mPageRankAdaper.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ContributeRank rank = (ContributeRank) adapter.getData().get(position);

                    int type = 0;
                    if (isManager == 1) {
                        type = 1;
                    }

                    ArmsUtils.initUserInfoDialog(rank.getUid(), type, mLiveInfo.getAnchorid(), LivePlayVerticalFragment2.this);

                }
            });
            mPageRankAdaper.replaceData(data);
        } else {
            mPageRankAdaper.replaceData(data);

        }

    }

    @Override
    public void getAnchorLiveInfo(LiveInfo bean) {
        mLiveInfo.setHot(bean.getLive().getHot());
        mLiveInfo.setLink_status(bean.getLive().getLink_status());
        mLiveHot.setText(String.valueOf(mLiveInfo.getHot()));
        mHandler.sendEmptyMessageDelayed(SocketConstants.LIVE_GET_LIVE_INFO, 60000);

        if (bean.getLive().getLink_on() == 1) {
            isLink = true;
        } else {
            isLink = false;
        }

    }

    public void setLink(boolean isLink) {
        this.isLink = isLink;
        if (mChatAdapter == null) {

            return;
        }
        mRvChat.scrollToPosition(mChatAdapter.getData().size() - 1);
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

    @Override
    public void getUserInfoById(UserRegist bean, int type) {
        switch (type) {
            //1用来查询PK时主播的相关内容
            case 1:
                mPlayerUtils.setAwayUserInfo(bean);
                break;
            default:
                mAnchor = bean;
                mLiveName.setText(bean.getNick_name());
                GlideUtils.setImage(getContext(), bean.getAvatar(), R.mipmap.moren, mLiveAvatar);
                isFollow = bean.getIsattent();

                if (isFollow == 1) {
                    mIvFollow.setImageResource(R.drawable.ic_live_followed);
                } else {
                    mIvFollow.setImageResource(R.drawable.ic_live_follow);
                }

                break;
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
        mPresenter.getUserInfoById(mAnchordID, 0);
        mPresenter.getAnchorLiveInfo(mAnchordID);
        mPresenter.checkIsMgr2(mLiveInfo.getAnchorid());
        mPresenter.getGuardInfo(mLiveInfo.getAnchorid());
        mPresenter.getGuardianCount(mAnchordID);
        if (mLiveInfo.getPkinfo() != null) {
            if (MyUserInstance.getInstance().getUserinfo().getId().equals(mLiveInfo.getPkinfo().getAway_anchorid())) {
                mPresenter.getUserInfoById(mLiveInfo.getPkinfo().getHome_anchorid(), 1);
            } else {
                mPresenter.getUserInfoById(mLiveInfo.getPkinfo().getAway_anchorid(), 1);
            }

        }
    }

    @Override
    public void getGuardianCount(Count bean) {
        if (bean == null) {
            mTvGuardianNum.setText(getString(R.string.st_live_guard_nums, "0"));
        } else {
            mTvGuardianNum.setText(getString(R.string.st_live_guard_nums, String.valueOf(bean.getCount())));
        }
    }

    @Override
    public void onDestroyView() {

        if (mPlayerUtils != null) {
            mPlayerUtils.relase();
        }
        endLink();
        TxImSendUtils.exitRoom(mAnchordID);
        TxImUtils.getSocketManager().cleanLiveListener();
        EventBus.getDefault().unregister(this);
        if (mHandler != null) {
            mHandler.removeMessages(SocketConstants.LIVE_GET_LIVE_INFO);
        }
        super.onDestroyView();
    }

    private void initPlayLayout() {
        if (mPlayerUtils == null) {
            mPlayerUtils = new UserPlayLayoutUtils2(getContext());
            mPlayerUtils.addView(mPlayRoot);
        }

        mPlayerUtils.startLive(mLiveInfo);
        mPlayerUtils.setOnLiveUtilsListener(new OnLiveUtilsListener() {
            @Override
            public void onPkEnd() {
                endPk();
            }

            @Override
            public void onPkStart(String id) {
                mPresenter.getUserInfoById(id, 1);
                mPresenter.checkAttent(id, 1);
            }

            @Override
            public void onPkAnchoorAttend(String id) {
                mPresenter.attentAnchor(id, "1");
            }
        });
    }

    private void initList() {
        if (mChatAdapter == null) {
            mChatList = new ArrayList<>();
            mChatAdapter = new LiveChatVerticalAdapter();
            mChatAdapter.addData(mChatList);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            linearLayoutManager.setStackFromEnd(true);
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
        //    mChatAdapter.addData(TxImSendUtils.getNotice());
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

    private void initBottomList() {
        if (bottomItems == null) {
            bottomItems = BaseInitUtils.getUserBottomList();
            mBottomAdapter = new LivePlayerBottomAdapter(bottomItems);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 6);
            mRvList.setLayoutManager(layoutManager);
            mRvList.setAdapter(mBottomAdapter);
            mBottomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    initBottomClick(position);
                }
            });

        }

    }

    final String st_mine_shop = "直播小店";
    final String st_link_on = "开启连麦";
    final String st_gift = "礼物";
    final String st_im = "私信";

    final String st_close = "关闭";

    private void initBottomClick(int position) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        //商品 连麦 礼物 私信 分享 关闭
        switch (mBottomAdapter.getData().get(position).getName()) {
            case st_mine_shop:
                LiveShopDialog liveShopDialog=new LiveShopDialog(1,mAnchordID);
                liveShopDialog.show(getChildFragmentManager(), "");
                break;
            case st_link_on:
                if(mPkStatus!=0){
                    ToastUtils.showT("当前主播PK中,暂时无法连麦");
                    return;
                }
                if (isLink) {
                    //申请连麦
                    RxPermissions rxPermissions = new RxPermissions(getActivity());
                    rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                            .subscribe(granted -> {
                                if (granted) {

                                    mPresenter.requestMlvbLink(mAnchordID);
                                    //    AppManager.getAppManager().startActivity(LivePushActivity3.class);
                                } else {
                                    ToastUtils.showT("用户没有授权相机权限,请授权");
                                }
                            });

                } else {
                    ToastUtils.showT("主播尚未开启连麦");
                }
                break;
            case st_gift:
                initGiftDialog();
                break;
            case st_im:
                if (mAnchor == null) {
                    return;
                }

                TxImUtils.getSocketManager().startChat(mAnchor.getId(), mAnchor.getNick_name(), mAnchor.getAvatar());
                break;

            case st_close:
                getActivity().finish();
                break;

        }

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

    @Override
    public void requestMlvbLink(LinkInfo bean) {
        if (onUrlSetListener != null) {
            onUrlSetListener.setPushUrl(bean.getPush_url());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_play_vertical_fragment_2;
    }

    /**
     * 注释
     * 初始化直播时间
     */


    private void initChatDialog() {
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
            mChatDialog.show(getChildFragmentManager(), "");
        }


    }

    @OnClick({R.id.chat_et, R.id.ll_ban, R.id.tv_guardian, R.id.ll_rank, R.id.iv_follow, R.id.civ_avatar
            , R.id.ll_guardian})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.chat_et:
                initChatDialog();
                break;
            case R.id.ll_ban:
                LiveRoomManagerDialog dialog = new LiveRoomManagerDialog(mLiveInfo.getAnchorid(), 0);
                dialog.show(getChildFragmentManager(), "");
                break;
            case R.id.tv_guardian:
                if (mLiveInfo == null) {
                    return;
                }
                LiveGuardianDialog guardianDialog = new LiveGuardianDialog(mLiveInfo.getAnchorid());
                guardianDialog.show(getChildFragmentManager(), "");
                break;
            case R.id.ll_rank:
                if (mLiveInfo == null) {
                    return;
                }
                LiveRankDialog rankDialog = new LiveRankDialog(mLiveInfo.getLiveid());
                rankDialog.show(getChildFragmentManager(), "");
                break;
            case R.id.iv_follow:
                switch (isFollow) {
                    case 0:
                        mPresenter.attentAnchor(mAnchordID, "1");
                        isFollow = 1;
                        mIvFollow.setImageResource(R.drawable.ic_live_followed);
                        break;
                    case 1:
                        mPresenter.attentAnchor(mAnchordID, "0");
                        isFollow = 0;
                        mIvFollow.setImageResource(R.drawable.ic_live_follow);
                        break;
                }

                break;
            case R.id.civ_avatar:
                ArmsUtils.initUserInfoDialog(mLiveInfo.getAnchorid(), 0, mLiveInfo.getAnchorid(), this);
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


                if (mPkStatus != 0) {
                    if (ArmsUtils.isStringEmpty(mLiveInfo.getPkid())) {
                        mPresenter.sendGift2(mAnchordID, bean.getId(), mLiveInfo.getLiveid(), count, mLiveInfo.getPkid(), String.valueOf(mPkStatus));
                    }
                } else {
                    mPresenter.sendGift2(mAnchordID, bean.getId(), mLiveInfo.getLiveid(), count, null, null);
                }


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
        }

    }

    public void setMemberCount(int data) {

        mTvWatchers.setText(getString(R.string.st_live_watcher, String.valueOf(data)));

    }

    public void setScoreChange(ImMessage messageBean) {
        if (mPlayerUtils != null) {
            mPlayerUtils.changeScoure(messageBean);
        }

    }

    public void endPk() {
        mPkStatus = 0;
        mLiveInfo.setPk_status(0);
        mLiveInfo.setPklive(null);
        mLiveInfo.setPkid(null);
        mLiveInfo.setPkinfo(null);
        mPlayerUtils.startLive(mLiveInfo);
    }

    public void startPk() {
        //这里跟主播端不太一样,主要区分1,2是否是主客场
        mPkStatus = 2;
        if (ArmsUtils.getIsPkingByBean(mLiveInfo) == 1) {
            if (mLiveInfo.getPkinfo().getHome_anchorid().equals(mLiveInfo.getAnchorid())) {
                mPkStatus = 1;
            } else {
                mPkStatus = 2;
            }
        } else {
            mPkStatus = 0;
        }

    }


    public void startLink(String url) {
        //主播端主要负责播放
        if (mLinkUtils != null) {
            return;
        }
        mLinkUtils = new LinkUtils(getContext(), mPlayRoot, MyUserInstance.getInstance().getUserinfo().getId());
        mLinkUtils.initLinkView(url);
        mLinkUtils.initPush();
        mLinkUtils.setOnClickListener(new LinkUtils.OnStopClickListener() {
            @Override
            public void onStop(String id) {
                mPresenter.stopMlvbLink(mLiveInfo.getAnchorid(), id);
                endLink();
            }
        });
    }

    public void endLink() {
        if (mLinkUtils != null) {
            mLinkUtils.initClose();
            mLinkUtils = null;
        }
    }

    public void setIsManager(boolean status) {
        if (status) {
            isManager = 1;
            mLiveBan.setVisibility(View.VISIBLE);
        } else {
            isManager = 0;
            mLiveBan.setVisibility(View.GONE);
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void openBeauty(BuyGuardianBus message) {
        mPresenter.getGuardInfo(mAnchordID);
    }

    //外部控制区
}
