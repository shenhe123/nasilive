/*
package com.feicui365.live.ui.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.internal.LinkedTreeMap;
import com.lxj.xpopup.core.BasePopupView;
import com.feicui365.live.base.Constants;
import com.feicui365.live.bean.Message;
import com.feicui365.live.model.entity.BaseLiveInfo;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.shop.activity.GoodsInfoActivity;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.ui.act.LivePushActivity;
import com.feicui365.live.widget.CommentContributionList;
import com.feicui365.live.widget.CommentGuardianList;
import com.feicui365.live.widget.RoomMangePopup;
import com.lxj.xpopup.XPopup;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.dialog.ChatVerticalDialog;
import com.feicui365.live.net.APIService;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.dialog.UserInfoDialog;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveInfo;

import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.SuperPlayerPresenter;
import com.feicui365.live.ui.act.SuperPlayerVerticalActivity;
import com.feicui365.live.ui.act.ToPayActivity;
import com.feicui365.live.ui.act.WebShopActivity;
import com.feicui365.live.ui.adapter.ChatListVerticalViewerAdapter;
import com.feicui365.live.ui.adapter.ContributionAdapter;
import com.feicui365.live.ui.adapter.LiveContributeAdapter;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.ui.uiinterfae.ShowGift;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.widget.ChatPopup;
import com.feicui365.live.widget.CommentShopList;
import com.feicui365.live.util.MyUserInstance;
import com.nasinet.nasinet.utils.AppManager;
import com.nasinet.nasinet.utils.DipPxUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.support.v4.util.Preconditions.checkNotNull;

@SuppressLint("ValidFragment")
public class ChatVerticalFragment extends BaseMvpFragment<SuperPlayerPresenter> implements SuperPlayerContrat.View {


    @BindView(R.id.chat_ll)
    LinearLayout chat_ll;

    @BindView(R.id.anchor_info_ll)
    LinearLayout anchor_info_ll;
    @BindView(R.id.chat_list_view)
    RecyclerView chat_list_view;
    @BindView(R.id.chat_et)
    TextView chat_et;
    @BindView(R.id.send_chat_tv)
    TextView send_chat_tv;
    @BindView(R.id.chat_gitf_iv)
    ImageView chat_gitf_iv;

    @BindView(R.id.anchor_ll)
    LinearLayout anchor_ll;
    @BindView(R.id.head_iv)
    CircleImageView head_iv;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.hot_tv)
    TextView hot_tv;
    @BindView(R.id.follow_iv)
    ImageView follow_iv;
    @BindView(R.id.chat_mess_iv)
    ImageView chat_mess_iv;

    @BindView(R.id.iv_share)
    ImageView iv_share;

    @BindView(R.id.chat_gouwuche_iv)
    ImageView chat_gouwuche_iv;


    @BindView(R.id.close_iv)
    ImageView close_iv;
    @BindView(R.id.ll_bottom)
    public LinearLayout ll_bottom;

    @BindView(R.id.contribution_tv)
    TextView contribution_tv;


    @BindView(R.id.hot_right_tv)
    TextView hot_right_tv;
    @BindView(R.id.rank_ll)
    RelativeLayout rank_ll;
    @BindView(R.id.user_list)
    RecyclerView user_list;

    @BindView(R.id.rl_shop_item)
    public RelativeLayout rl_shop_item;

    @BindView(R.id.iv_item_acvatar)
    public ImageView iv_item_acvatar;
    @BindView(R.id.iv_shop_close)
    public ImageView iv_shop_close;

    @BindView(R.id.tv_shop_price)
    public TextView tv_shop_price;
    @BindView(R.id.ll_no_talk)
    public LinearLayout ll_no_talk;

    @BindView(R.id.garudian_tv)
    TextView garudian_tv;

    @BindView(R.id.ll_get_info_view)
    LinearLayout ll_get_info_view;

    @BindView(R.id.v_left)
    View v_left;
    @BindView(R.id.v_right)
    View v_right;

    @BindView(R.id.ll_user_info_right)
    LinearLayout ll_user_info_right;
    @BindView(R.id.ll_user_info_left)
    LinearLayout ll_user_info_left;

    @BindView(R.id.civ_avatar_right)
    CircleImageView civ_avatar_right;
    @BindView(R.id.tv_name_right)
    TextView tv_name_right;
    @BindView(R.id.iv_attend_right)
    ImageView iv_attend_right;
    @BindView(R.id.civ_avatar_left)
    CircleImageView civ_avatar_left;
    @BindView(R.id.tv_name_left)
    TextView tv_name_left;
    @BindView(R.id.iv_attend_left)
    ImageView iv_attend_left;

    List<Good> shopItems;

    public ChatVerticalDialog newVideoInputDialogFragment5;

    public HotLive hotLive;

    private LinkedHashMap<Integer, ArrayList<GiftData>> gridGiftList = new LinkedHashMap<>(20);

    public SuperPlayerVerticalActivity.SendText sendText;
    private ArrayList<Message> chatList;
    private ChatListVerticalViewerAdapter chatListAdapter;
    private int preEndIndex = 1;
    private RoundedCorners roundedCorners = new RoundedCorners(10);
    //通过RequestOptions扩展功能
    private RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
    public Good now_chose;
    public boolean is_manager = false;
    public JoinAnchor joinAnchor;
    private int dialog_type = 0;
    private boolean is_first = true;
    private GuardianInfo guardianInfo;


    public boolean isIs_first() {
        return is_first;
    }

    public void setGuardianInfo(GuardianInfo guardianInfo) {
        this.guardianInfo = guardianInfo;
    }

    public GuardianInfo getGuardianInfos() {
        return guardianInfo;
    }

    public void setIs_first(boolean is_first) {
        this.is_first = is_first;
    }

    public interface JoinAnchor {
        void join();

        void stopjoin();
    }

    public void setJoinAnchor(JoinAnchor joinAnchor) {
        this.joinAnchor = joinAnchor;
    }

    */
/**
     * 构造函数
     *
     * @param hotLive
     * @param showGift
     * @param sendText
     *//*

    @SuppressLint("ValidFragment")
    public ChatVerticalFragment(HotLive hotLive, ShowGift showGift, SuperPlayerVerticalActivity.SendText sendText) {
        this.hotLive = hotLive;
        this.sendText = sendText;

    }

    @SuppressLint("ValidFragment")
    public ChatVerticalFragment(ShowGift showGift, SuperPlayerVerticalActivity.SendText sendText) {
        this.sendText = sendText;

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        mPresenter = new SuperPlayerPresenter();
        mPresenter.attachView(this);
        initRecyclerView();


    }

    private void initGuardianCount() {
        mPresenter.getGuardianCount(hotLive.getAnchorid());
    }

    private void checkIsManager() {
        if (MyUserInstance.getInstance().hasToken()) {
            HttpUtils.getInstance().checkIsMgr(hotLive.getAnchorid(), new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    JSONObject data = HttpUtils.getInstance().check(response);
                    if (data.get("status").toString().equals("0")) {
                        ll_no_talk.setVisibility(View.VISIBLE);
                        is_manager = true;
                    }
                }
            });
        }
    }

    private void getGuardianInfo() {
        if (MyUserInstance.getInstance().hasToken()) {
            mPresenter.getGuardInfo(hotLive.getAnchorid());
        }
    }


    private void checkAttent() {
        if (MyUserInstance.getInstance().hasToken()) {

            HttpUtils.getInstance().checkAttent(hotLive.getAnchorid() + "", new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    JSONObject jsonObject = JSON.parseObject(response.body());
                    if (jsonObject.getString("status").equals("0")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        if (jsonObject1.getString("attented").equals("0")) {
                            Glide.with(getContext()).load(R.mipmap.button_guanzhu_vertical).into(follow_iv);
                        } else {
                            Glide.with(getContext()).load(R.mipmap.button_yiguanzhu_vertical).into(follow_iv);
                        }
                    }


                }
            });
        } else {
            Glide.with(getContext()).load(R.mipmap.button_guanzhu_vertical).into(follow_iv);
        }

    }

    private void initshop() {
        mPresenter.getGoodsList(hotLive.getAnchorid());
    }

    private void showInput() {
        if (null != newVideoInputDialogFragment5) {
            newVideoInputDialogFragment5.dismiss();
        }
        ChatVerticalDialog dialogFragment = new ChatVerticalDialog(this);
        newVideoInputDialogFragment5 = dialogFragment;
        newVideoInputDialogFragment5.show(getActivity().getSupportFragmentManager(), "myAlert");
        newVideoInputDialogFragment5.setOnComentSendListener(new ChatVerticalDialog.OnComentSendListener() {
            @Override
            public void onSendSucess(String comment) {

                sendText.send(comment);
            }
        });
    }

    public void hideInput() {
        if (null != newVideoInputDialogFragment5) {
            newVideoInputDialogFragment5.dismiss();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_chat_verical;
    }

    public void setLiveInfoFirst(LiveInfo liveInfo) {

        //初始化部分
        hotLive = liveInfo.getLive();
        //头像
        Glide.with(this).setDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.moren)).load(hotLive.getAnchor().getAvatar()).into(head_iv);
        //名字
        name_tv.setText(hotLive.getAnchor().getNick_name());
        //热度
        if (hotLive.getHot() > 10000) {
            DecimalFormat df = new DecimalFormat("#.#");
            String obj1 = df.format(hotLive.getHot() * 1f / 10000);
            hot_tv.setText("热度：" + obj1 + "w");
        } else {
            hot_tv.setText("热度：" + String.valueOf(hotLive.getHot()));
        }

        //商店
        initshop();
        //守护信息
        getGuardianInfo();
        initGuardianCount();
        //关注,管理
        checkAttent();
        checkIsManager();


        //右上角
        LiveContributeAdapter chatVerticalUserAdapter = new LiveContributeAdapter(liveInfo.getContribute(), getContext());
        LinearLayoutManager ms = new LinearLayoutManager(getContext());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        user_list.setAdapter(chatVerticalUserAdapter);
        user_list.setLayoutManager(ms);
        chatVerticalUserAdapter.setOnItemClikeListener(new LiveContributeAdapter.OnItemClikeListener() {
            @Override
            public void onItemClick(String id) {
                dialog_type = 0;
                mPresenter.getUserBasicInfo(id);
            }
        });
        ((SuperPlayerVerticalActivity) getActivity()).dy_loading.stop();
        ((SuperPlayerVerticalActivity) getActivity()).rl_loading.setVisibility(View.GONE);

        //获取聊天框到顶部距离
        ((SuperPlayerVerticalActivity) getActivity()).live_chat_h = chat_list_view.getTop();
        if (null != sendText) {
            sendText.send("进入直播间");
        }
        RelativeLayout.LayoutParams lp_rl_play = (RelativeLayout.LayoutParams) ll_get_info_view.getLayoutParams();
        lp_rl_play.setMargins(0, DipPxUtils.dip2px(getContext(), 140), 0, 0);
        lp_rl_play.height = (MyUserInstance.getInstance().device_width / 2) / 3 * 4;
        ll_get_info_view.setLayoutParams(lp_rl_play);
        if (hotLive.getPk_status() == 1) {
            showPkPlayerInfo();
        }
    }


    public void setLiveInfo(BaseLiveInfo liveInfo) {

        if (liveInfo.getLive().getHot() > 10000) {
            DecimalFormat df = new DecimalFormat("#.#");
            String obj1 = df.format(hotLive.getHot() * 1f / 10000);
            hot_tv.setText("热度：" + obj1 + "w");
        } else {
            hot_tv.setText("热度：" + String.valueOf(hotLive.getHot()));
        }
        //右上角
        LiveContributeAdapter chatVerticalUserAdapter = new LiveContributeAdapter(liveInfo.getContribute(), getContext());
        LinearLayoutManager ms = new LinearLayoutManager(getContext());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        user_list.setAdapter(chatVerticalUserAdapter);
        user_list.setLayoutManager(ms);
        chatVerticalUserAdapter.setOnItemClikeListener(new LiveContributeAdapter.OnItemClikeListener() {
            @Override
            public void onItemClick(String id) {
                dialog_type = 0;
                mPresenter.getUserBasicInfo(id);
            }
        });
    }

    public void setChatGroupNum(String num) {
        if (num == null) {
            return;
        }
        if (hot_right_tv == null) {
            return;
        }
        hot_right_tv.setText(num);
    }


    @Override
    public void setContributeRank(BaseResponse<ArrayList<ContributeRank>> contributeRank) {
        if (contributeRank.isSuccess()) {
            if (contributeRank.getData() == null) {
                ToastUtils.showT("当前主播贡献榜没有内容");
                return;
            }
            if (contributeRank.getData().size() == 0) {
                ToastUtils.showT("当前主播贡献榜没有内容");
                return;
            }

            showXPop(new CommentContributionList(getContext(), contributeRank.getData()), true);
        }
    }


    */
/**
     * 初始化聊天信息列表
     *//*

    private void initRecyclerView() {
        chatList = new ArrayList<>();
        chatList.add(new Message());
        chatListAdapter = new ChatListVerticalViewerAdapter(chatList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);
        linearLayoutManager.scrollToPositionWithOffset(chatListAdapter.getItemCount() - 1, Integer.MIN_VALUE);
        chat_list_view.setLayoutManager(linearLayoutManager);
        chat_list_view.setAdapter(chatListAdapter);
        chatListAdapter.setOnItemClickListener(new ChatListVerticalViewerAdapter.OnItemClickListener() {
            @Override
            public void Onclick(String id) {
                dialog_type = 0;
                mPresenter.getUserBasicInfo(id);
            }
        });

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


    */
/**
     * 发送聊天信息
     * 它是暴露在外面给{@SuperPlayerVerticalActivity} 提供的发送消息的方法，方便播放器界面使用回调函数。
     *
     * @param chat
     *//*

    public void setCaht(Message chat) {
        if (chatList == null) {
            return;
        }
        preEndIndex = chatList.size();
        chatList.add(chat);
        chatListAdapter.notifyItemRangeInserted(chatList.size(), 1);
        if (null != chat_list_view) {
            if (!chat_list_view.canScrollVertically(1)) {
                //滑动到底部了
                chat_list_view.scrollToPosition(chatListAdapter.getItemCount() - 1);
            }
        }

    }


    @OnClick({R.id.v_left, R.id.v_right, R.id.garudian_tv, R.id.chat_join_iv, R.id.ll_no_talk, R.id.chat_gouwuche_iv, R.id.rl_shop_item, R.id.iv_shop_close, R.id.chat_et, R.id.iv_share, R.id.chat_mess_iv, R.id.head_iv, R.id.close_iv, R.id.chat_gitf_iv, R.id.contribution_tv, R.id.follow_iv*/
/*, R.id.chat_gitf_ll*//*
*/
/*, R.id.atv_ply_v_root_ll, R.id.atv_ply_v_send_chat_tv*//*
})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_join_iv:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                        .subscribe(granted -> {
                            if (granted) {
                                if (hotLive.getPk_status() == 0) {
                                    if (joinAnchor != null) {
                                        joinAnchor.join();
                                    }
                                } else {
                                    ToastUtils.showT("当前主播PK中,暂时无法连麦");
                                }
                            } else {
                                ToastUtils.showT("用户没有授权相机权限,请授权在开始连麦");
                            }
                        });

                break;

            case R.id.head_iv:
                dialog_type = 1;
                mPresenter.getUserBasicInfo(hotLive.getAnchorid());

                break;

            case R.id.chat_et:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    showInput();
                }
                break;

            case R.id.iv_shop_close:
                rl_shop_item.setVisibility(View.GONE);
                break;

            case R.id.rl_shop_item:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    if (now_chose != null) {
                        Intent i = new Intent(getContext(), WebShopActivity.class);
                        i.putExtra("jump_url", APIService.Goods + now_chose.getId() + "?token=" + MyUserInstance.getInstance().getUserinfo().getToken() + "&uid=" + MyUserInstance.getInstance().getUserinfo().getId());

                        getContext().startActivity(i);
                   */
/*     Intent i = new Intent(getActivity(), GoodsInfoActivity.class);
                        i.putExtra(Constants.GOODS_INFO, now_chose);

                        startActivity(i);*//*

                    }
                }
                break;
            case R.id.chat_gitf_iv:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    ((SuperPlayerVerticalActivity) getActivity()).showGiftList();
                }
                break;


            case R.id.contribution_tv:

                mPresenter.getContributeRank(hotLive.getLiveid());
                break;


            case R.id.follow_iv:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    HttpUtils.getInstance().checkAttent(hotLive.getAnchorid() + "", new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());

                            if (jsonObject.getString("status").equals("0")) {
                                JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                                if (jsonObject1.getString("attented").equals("0")) {
                                    HttpUtils.getInstance().attentAnchor(hotLive.getAnchorid() + "", "1", new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                            follow_iv.setImageDrawable(getActivity().getDrawable(R.mipmap.button_yiguanzhu_vertical));

                                        }
                                    });

                                } else {
                                    HttpUtils.getInstance().attentAnchor(hotLive.getAnchorid() + "", "0", new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                            follow_iv.setImageDrawable(getActivity().getDrawable(R.mipmap.button_guanzhu_vertical));
                                        }
                                    });

                                }
                            }


                        }
                    });
                }
                break;

            case R.id.close_iv:
                if (joinAnchor != null) {
                    joinAnchor.stopjoin();
                }

                getActivity().finish();
                break;
            case R.id.chat_mess_iv:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    new XPopup.Builder(getContext())

                            .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                            .asCustom(new ChatPopup(getContext())*/
/*.enableDrag(false)*//*
)
                            .show();
                }
                break;
            case R.id.iv_share:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    Glide.with(getContext()).load(hotLive.getThumb()).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            ShareDialog.Builder builder = new ShareDialog.Builder(getContext());
                            builder.setShare_url(MyUserInstance.getInstance().getUserConfig().getConfig().getShare_live_url() + hotLive.getAnchorid());
                            builder.create().show();
                            builder.showBottom2();
                            //builder.setImage_url(my_trend.getUser());
                            builder.setContent(hotLive.getTitle());
                            builder.setTitle(getString(R.string.live_recommend));
                            builder.hideCollect();
                            builder.setTumb(MyUserInstance.getInstance().drawableToBitmap(resource));
                            builder.setType("1");
                            builder.setId(hotLive.getAnchorid());


                        }
                    });
                }

                break;

            case R.id.chat_gouwuche_iv:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    if (shopItems == null) {
                        ToastUtils.showT(getString(R.string.live_no_shop));
                    } else {
                        CommentShopList commentShopList = new CommentShopList(getContext(), shopItems);
                        new XPopup.Builder(getContext())
                                .autoDismiss(false)
                                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                                .asCustom(commentShopList*/
/*.enableDrag(false)*//*
)
                                .show();
                    }
                }
                break;

            case R.id.ll_no_talk:
                new XPopup.Builder(getContext())
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .asCustom(new RoomMangePopup(getContext(), "2", hotLive.getAnchorid())*/
/*.enableDrag(false)*//*
)
                        .show();
                break;
            case R.id.garudian_tv:
                initGuardianlist(0);
                break;
            case R.id.v_left:
                if (hotLive.getPk_status() == 1) {
                    dialog_type = 1;

                    mPresenter.getUserBasicInfo(String.valueOf(hotLive.getPkinfo().getHome_anchorid()));
                }

                break;
            case R.id.v_right:
                if (hotLive.getPk_status() == 1) {
                    dialog_type = 1;

                    mPresenter.getUserBasicInfo(String.valueOf(hotLive.getPkinfo().getAway_anchorid()));
                }


                break;
        }
    }

    public void initGuardianlist(int postion) {
        CommentGuardianList commentGuardianList = new CommentGuardianList(getContext(), hotLive.getAnchorid(), guardianInfo, postion);
        new XPopup.Builder(getContext())

                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .asCustom(commentGuardianList*/
/*.enableDrag(false)*//*
)
                .show();
        commentGuardianList.setBuyFinish(new CommentGuardianList.BuyFinish() {
            @Override
            public void BuySuccess(GuardianInfo guardianInfo) {
                ChatVerticalFragment.this.guardianInfo = guardianInfo;
                initGuardianCount();
            }
        });
    }


    private void showXPop(BasePopupView basePopupView, boolean isAuto) {
        new XPopup.Builder(getContext())
                .autoDismiss(isAuto)
                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .asCustom(basePopupView)
                .show();
    }


    @Override
    public void getUserBasicInfo(BaseResponse<UserRegist> baseResponse) {
        //0,普通用户,1主播,2管理员
        switch (dialog_type) {
            case 0:
                UserInfoDialog.Builder builder_user = new UserInfoDialog.Builder(getContext());

                builder_user.setType("1");
                builder_user.setAnchorid(hotLive.getAnchorid());
                if (is_manager) {
                    builder_user.setStatus("3");
                }
                builder_user.setUserRegist(baseResponse.getData());
                builder_user.create().show();
                break;
            case 1:
                UserInfoDialog.Builder builder_anchor = new UserInfoDialog.Builder(getContext());

                builder_anchor.setType("2");
                builder_anchor.setUserRegist(baseResponse.getData());
                builder_anchor.create().show();
                break;

            case 3:
                //表示主队是自己
                ll_user_info_right.setVisibility(View.VISIBLE);
                Glide.with(getContext()).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.moren)).load(baseResponse.getData().getAvatar()).into(civ_avatar_right);
                tv_name_right.setText(baseResponse.getData().getNick_name());

                HttpUtils.getInstance().checkAttent(baseResponse.getData().getId() + "", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (getActivity().isFinishing()) {
                            return;
                        }
                        JSONObject data = HttpUtils.getInstance().check(response);
                        if (data.get("status").toString().equals("0")) {
                            String temp = data.getJSONObject("data").getString("attented");
                            if (temp.equals("0")) {

                                iv_attend_right.setVisibility(View.VISIBLE);
                                iv_attend_right.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HttpUtils.getInstance().attentAnchor(baseResponse.getData().getId() + "", "1", new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {
                                                JSONObject data = HttpUtils.getInstance().check(response);
                                                if (data.get("status").toString().equals("0")) {
                                                    iv_attend_right.setVisibility(View.GONE);
                                                }

                                            }
                                        });
                                    }
                                });
                            } else {
                                iv_attend_right.setVisibility(View.GONE);
                            }
                        }
                    }
                });
                break;
            case 4:
                //表示客队是自己

                ll_user_info_left.setVisibility(View.VISIBLE);
                Glide.with(getContext()).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.moren)).load(baseResponse.getData().getAvatar()).into(civ_avatar_left);
                tv_name_left.setText(baseResponse.getData().getNick_name());
                HttpUtils.getInstance().checkAttent(baseResponse.getData().getId() + "", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        if (getActivity().isFinishing()) {
                            return;
                        }
                        JSONObject data = HttpUtils.getInstance().check(response);
                        if (data.get("status").toString().equals("0")) {

                            String temp = data.getJSONObject("data").getString("attented");

                            if (temp.equals("0")) {

                                iv_attend_left.setVisibility(View.VISIBLE);
                                iv_attend_left.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HttpUtils.getInstance().attentAnchor(baseResponse.getData().getId() + "", "1", new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {
                                                JSONObject data = HttpUtils.getInstance().check(response);
                                                if (data.get("status").toString().equals("0")) {
                                                    iv_attend_left.setVisibility(View.GONE);
                                                }

                                            }
                                        });
                                    }
                                });
                            } else {
                                iv_attend_left.setVisibility(View.GONE);
                            }
                        }
                    }
                });
                break;
        }
    }


    @Override
    public void getGoodsList(BaseResponse<ArrayList<Good>> baseResponse) {
        shopItems = baseResponse.getData();
        if (shopItems == null) {

            return;
        }
        if (shopItems.size() == 0) {
            shopItems = null;
            return;
        }
        if (shopItems.get(0).getLive_explaining().equals("1")) {
            rl_shop_item.setVisibility(View.VISIBLE);
            String[] images = shopItems.get(0).getThumb_urls().split(",");
            Glide.with(getContext()).applyDefaultRequestOptions(options).asBitmap().load(images[0]).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    iv_item_acvatar.setImageBitmap(resource);
                }
            });
            tv_shop_price.setText("￥ " + shopItems.get(0).getPrice());
            now_chose = shopItems.get(0);
        } else {
            rl_shop_item.setVisibility(View.GONE);
        }
    }


    @Override
    public void getGuardianCount(BaseResponse baseResponse) {
        LinkedTreeMap result = (LinkedTreeMap) baseResponse.getData();
        int temp = ((Double) result.get("count")).intValue();
        if (temp > 0) {
            garudian_tv.setText("守护 " + temp + "人");

        } else {
            garudian_tv.setText("守护 0人");
        }
    }


    @Override
    public void getGuardInfo(BaseResponse<GuardianInfo> baseResponse) {
        guardianInfo = baseResponse.getData();
    }

    public void showPkPlayerInfo() {
        ll_user_info_left.setVisibility(View.GONE);
        ll_user_info_right.setVisibility(View.GONE);
        if (hotLive.getPkinfo().getHome_anchorid() == Integer.parseInt(hotLive.getAnchorid())) {
            dialog_type = 3;
            mPresenter.getUserBasicInfo(String.valueOf(hotLive.getPkinfo().getAway_anchorid()));

        } else {
            dialog_type = 4;
            mPresenter.getUserBasicInfo(String.valueOf(hotLive.getPkinfo().getHome_anchorid()));

        }
    }

    public void hidePkPlayerInfo() {
        ll_user_info_left.setVisibility(View.GONE);
        ll_user_info_right.setVisibility(View.GONE);
    }
}
*/
