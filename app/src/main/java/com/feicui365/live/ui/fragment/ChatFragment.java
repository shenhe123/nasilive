/*
package com.feicui365.live.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.bean.Message;
import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.dialog.ChatDialog;
import com.feicui365.live.dialog.UserInfoDialog;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.SuperPlayerPresenter;
import com.feicui365.live.ui.act.SuperPlayerActivity;
import com.feicui365.live.ui.act.ToPayActivity;
import com.feicui365.live.ui.adapter.ChatListAdapter;
import com.feicui365.live.ui.adapter.PalyTabFragmentPagerAdapter;
import com.feicui365.live.ui.uiinterfae.ShowGift;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.widget.CommentGuardianList;
import com.feicui365.live.widget.RoomMangePopup;
import com.lxj.xpopup.XPopup;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.liteav.demo.play.bean.GiftData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class ChatFragment extends BaseMvpFragment<SuperPlayerPresenter> implements SuperPlayerContrat.View {
    @BindView(R.id.chat_gitf_iv)
    ImageView chat_gitf_iv;
    @BindView(R.id.content_view)
    FrameLayout content_view;
    @BindView(R.id.chat_gitf_ll)
    LinearLayout chat_gitf_ll;
    @BindView(R.id.gift_view_ager)
    ViewPager gift_view_ager;
    @BindView(R.id.send_grid_gift)
    ImageView send_grid_gift;
    @BindView(R.id.chat_list)
    RecyclerView chat_list_view;
    @BindView(R.id.send_chat_tv)
    TextView send_chat_tv;
    @BindView(R.id.chat_et)
    TextView chat_et;
    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R.id.rl_out)
    RelativeLayout rl_out;
    @BindView(R.id.overage_tv)
    TextView overage_tv;
    @BindView(R.id.recharge_tv)
    TextView recharge_tv;
    @BindView(R.id.talk_manager)
    public ImageView talk_manager;

    private HotLive hotLive;

    public void setHotLive(HotLive hotLive) {
        this.hotLive = hotLive;
    }

    public ShowGift showGift;
    private LinkedHashMap<Integer, ArrayList<GiftData>> gridGiftList = new LinkedHashMap<>(20);
    private ArrayList<Fragment> fragmentList;
    private GiftData giftData;
    public SuperPlayerActivity.SendText sendText;
    private ArrayList<Message> chatList;
    private ChatListAdapter chatListAdapter;
    public ChatDialog newVideoInputDialogFragment6;
    int preEndIndex = 1;
    public boolean is_manager = false;
    private boolean is_first = true;
    private GuardianInfo guardianInfo;

    public void setIs_first(boolean is_first) {
        this.is_first = is_first;
    }

    public GuardianInfo getGuardianInfos() {
        return guardianInfo;
    }

    public boolean isIs_first() {
        return is_first;
    }


    public ChatFragment(ShowGift showGift, SuperPlayerActivity.SendText sendText) {
        super();
        this.sendText = sendText;
        this.showGift = showGift;
    }

    @Override
    protected int getLayoutId() {

        return R.layout.fragment_chat;
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

    private void getGuardianInfo() {
        if (MyUserInstance.getInstance().hasToken()) {
            mPresenter.getGuardInfo(hotLive.getAnchorid());
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        mPresenter = new SuperPlayerPresenter();
        mPresenter.attachView(this);
        mPresenter.getGiftList();
        initRecyclerView();
        if (null != sendText) {
            sendText.send("进入直播间");
        }
        checkIsManager();
        //守护信息
        getGuardianInfo();
    }

    private void checkIsManager() {
        if (MyUserInstance.getInstance().hasToken()) {
            if (hotLive == null) {
                return;
            }
            HttpUtils.getInstance().checkIsMgr(hotLive.getAnchorid(), new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    JSONObject data = HttpUtils.getInstance().check(response);
                    if (data.get("status").toString().equals("0")) {
                        talk_manager.setVisibility(View.VISIBLE);
                        is_manager = true;
                    }
                }
            });
        }
    }

    //守护信息
    @Override
    public void getGuardInfo(BaseResponse<GuardianInfo> baseResponse) {
        if (baseResponse != null) {
            guardianInfo = baseResponse.getData();
        }

    }

    private void initRecyclerView() {
        chatList = new ArrayList<>();
        chatList.add(new Message());
        chatListAdapter = new ChatListAdapter(chatList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.scrollToPositionWithOffset(chatListAdapter.getItemCount() - 1, Integer.MIN_VALUE);

        chat_list_view.setLayoutManager(linearLayoutManager);
        chat_list_view.setAdapter(chatListAdapter);
        rl_out.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                if (chat_gitf_ll.getVisibility() == View.VISIBLE) {
                    int x = (int) event.getRawX();
                    int y = (int) event.getRawY();
                    if (!isTouchPointInView(chat_gitf_ll, x, y)) {

                        chat_gitf_ll.setVisibility(View.GONE);
                    }
                }
                return false;
            }
        });

        chatListAdapter.setOnItemClickListener(new ChatListAdapter.OnItemClickListener() {
            @Override
            public void Onclick(String id) {
                HttpUtils.getInstance().getUserBasicInfo(id, new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject data = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
                        if (null != data) {
                            UserInfoDialog.Builder builder = new UserInfoDialog.Builder(getContext());
                            UserRegist userRegist = (UserRegist) JSONObject.parseObject(data.toString(), UserRegist.class);
                            builder.setType("1");
                            builder.setAnchorid(hotLive.getAnchorid());
                            if (is_manager) {
                                builder.setStatus("3");
                            }
                            builder.setUserRegist(userRegist);
                            builder.create().show();
                        }
                    }
                });
            }
        });
    }


    @Override
    public void setGiftList(ArrayList<GiftData> giftList) {
        if (null != giftList) {
            ArrayList<GiftData> list = new ArrayList<>(20);
            for (int i = 0; i < giftList.size(); i++) {
                if (list.size() == 8) {
                    ArrayList<GiftData> datas = new ArrayList<>(10);
                    datas.addAll(list);
                    gridGiftList.put(i, datas);
                    list.clear();
                } else {
                    list.add(giftList.get(i));
                }
            }
            fragmentList = new ArrayList<>();
            for (Map.Entry<Integer, ArrayList<GiftData>> entry : gridGiftList.entrySet()) {
                GridGiftListFragment gridGiftListFragment = new GridGiftListFragment(new HideGiftList() {
                    @Override
                    public void selectedGift(GiftData data) {
                        //隐藏聊天界面礼物列表
                        giftData = data;
                    }
                });
                gridGiftListFragment.setGiftList(entry.getValue());
                fragmentList.add(gridGiftListFragment);
            }
            //判断是否是小数
            float pages = giftList.size() / 8f;
            Pattern pattern = Pattern.compile("^[+]?([0-9]+(.[0-9]{1,2})?)$");
            if (pattern.matcher(String.valueOf(pages)).matches()) {
                pages++;
            }
            PalyTabFragmentPagerAdapter adapter = new PalyTabFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList);
            gift_view_ager.setAdapter(adapter);
            gift_view_ager.setOffscreenPageLimit((int) pages);
            gift_view_ager.setCurrentItem(0);  //初始化显示第一个页面
            showGift.setGift(giftList);

        }

    }


    @OnClick({R.id.chat_gitf_iv, R.id.send_grid_gift, R.id.chat_gitf_ll, R.id.root_chat,
            R.id.send_chat_tv, R.id.rl_top, R.id.recharge_tv, R.id.chat_et, R.id.talk_manager, R.id.iv_guardian})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_gitf_ll:
                break;
            case R.id.chat_gitf_iv:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    ((SuperPlayerActivity) getActivity()).showGiftList();
                }
                break;
            case R.id.send_grid_gift:
                for (Fragment fragment : fragmentList) {
                    GridGiftListFragment gridGiftListFragment = (GridGiftListFragment) fragment;
                    gridGiftListFragment.unSelectedGift();
                }
                hideGift();
                if (null != giftData) {
                    showGift.show(giftData);
                    giftData = null;
                }
                break;
            case R.id.root_chat:
                hideGift();
                break;
            case R.id.rl_top:

                break;
            case R.id.recharge_tv:
                //TODO 支付页面
                Intent intent = new Intent(getContext(), ToPayActivity.class);
                startActivity(intent);
                break;
            case R.id.chat_et:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    if (null != newVideoInputDialogFragment6) {
                        newVideoInputDialogFragment6.dismiss();
                    }
                    ChatDialog dialogFragment = new ChatDialog(this);
                    newVideoInputDialogFragment6 = dialogFragment;
                    newVideoInputDialogFragment6.show(getActivity().getSupportFragmentManager(), "myAlert");
                    newVideoInputDialogFragment6.setOnComentSendListener(new ChatDialog.OnComentSendListener() {
                        @Override
                        public void onSendSucess(String comment) {
                            sendText.send(comment);
                        }
                    });
                }
                break;
            case R.id.talk_manager:
                new XPopup.Builder(getContext())
                        .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                        .asCustom(new RoomMangePopup(getContext(), "2", hotLive.getAnchorid())*/
/*.enableDrag(false)*//*
)
                        .show();
                break;
            case R.id.iv_guardian:
                initGuardianlist(99);
                break;
        }
    }

    public void showGiftList() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((SuperPlayerActivity) getActivity()).showGiftList();
            }
        }, 200);

    }

    public void hideGift() {
        gift_view_ager.setCurrentItem(0);
        chat_gitf_ll.setVisibility(View.GONE);
    }

    public void setCaht(Message chat) {

        preEndIndex = chatList.size();
        chatList.add(chat);
        chatListAdapter.notifyItemRangeInserted(chatList.size(), 1);

        //报错炸出来点
        if (null != chat_list_view) {

            if (chat_list_view.canScrollVertically(chatList.size() - 1)) {
                //滑动到底部了
                chat_list_view.scrollToPosition(chatList.size() - 1);
            }
        }

    }


    public interface HideGiftList {
        void selectedGift(GiftData data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
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
                ChatFragment.this.guardianInfo = guardianInfo;

            }
        });
    }

    public void attentionListener() {
        sendText.send("关注了主播");
    }
}

*/
