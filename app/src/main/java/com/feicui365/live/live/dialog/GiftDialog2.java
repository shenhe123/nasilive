package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.feicui365.nasinet.utils.AppManager;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment2;

import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.eventbus.OnBagGiftChoseBus;
import com.feicui365.live.eventbus.OnGiftChoseBus;
import com.feicui365.live.interfaces.OnChoseGiftClickListener;
import com.feicui365.live.interfaces.OnItemClickListener;
import com.feicui365.live.live.adapter.ChatGiftCountAdapter;
import com.feicui365.live.live.fragment.NormalGiftFragment;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.BagGiftInfo;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.ui.act.ToPayActivity;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
@SuppressLint("ValidFragment")
public class GiftDialog2 extends BaseDialogFragment2 implements LivePushContrat.View, OnItemClickListener {


    @BindView(R.id.tv_choose)
    TextView mTvChoose;
    @BindView(R.id.ic_arrow)
    ImageView mIcArrow;

    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.tv_gold)
    TextView mTvGold;

    @BindView(R.id.fl_fragment)
    FrameLayout flContent;
    @BindView(R.id.tv_gift)
    TextView mTvGift;


    private ArrayList<Fragment> mFragments;
    private ArrayList<String> mTitles;

    LivePushPresenter mPresenter;
    boolean isVertical = false;

    int mCount = 1;
    int isGuardian;

    public void setIsGuardian(int isGuardian) {
        this.isGuardian = isGuardian;
    }

    GiftInfo mChoseBean;
    BagGiftInfo mChoseBagBean;
    private PopupWindow mGiftCountPopupWindow;//选择分组数量的popupWindow
    OnChoseGiftClickListener onChoseGiftClickListener;
    NormalGiftFragment mGiftFragment1;


    public void setOnChoseGiftClickListener(OnChoseGiftClickListener onChoseGiftClickListener) {
        this.onChoseGiftClickListener = onChoseGiftClickListener;
    }

    public GiftDialog2(boolean isVertical) {
        this.isVertical = isVertical;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.gift_vertical_dialog_2;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.transparentBackgroundDiaolg;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        if (isVertical) {
            params.height = ArmsUtils.dip2px(getContext(), 320);
        } else {
            params.height = ArmsUtils.dip2px(getContext(), 220);
        }

        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }


    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        EventBus.getDefault().register(this);
        mTvGold.setText(String.valueOf(MyUserInstance.getInstance().getUserGold()));
        initViewPager();

    }

    private void initViewPager() {
        //聊天 主播 贡献榜
        mFragments = new ArrayList<>();
        mGiftFragment1 = new NormalGiftFragment(isVertical, 0);
        mFragments.add(mGiftFragment1);
        addFragment(mFragments.get(0));

    }


    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
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

    @OnClick({R.id.tv_choose, R.id.tv_send,R.id.tv_charge})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_choose:
                showGiftCount();
                break;


            case R.id.tv_send:
                if (mChoseBean == null&mChoseBagBean == null) {
                    ToastUtils.showT(getString(R.string.st_gift_tips_1));
                    return;
                }else if(mChoseBean!=null&mChoseBagBean == null){
                    sendNormalGift();
                }




                break;
            case R.id.tv_charge:
                AppManager.getAppManager().startActivity(ToPayActivity.class);
                break;
        }
    }

    private void sendNormalGift(){

        if(isGuardian==0&mChoseBean.getType()==2){
            ToastUtils.showT("您还没有守护当前主播");
            return;
        }
        int price = mChoseBean.getPrice() * mCount;
        if (price > MyUserInstance.getInstance().getUserGold()) {
            ToastUtils.showT(getString(R.string.st_gift_tips_2));
            return;
        }
        //如果通过了,那边直接发,这边本地计算一下金额
        MyUserInstance.getInstance().setUserGold(MyUserInstance.getInstance().getUserGold() - price);
        mTvGold.setText(String.valueOf(MyUserInstance.getInstance().getUserGold()));
        if (onChoseGiftClickListener != null) {
            onChoseGiftClickListener.onGiftClick(mChoseBean, mCount);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGiftChose(OnGiftChoseBus message) {

        if (message.giftInfo == null) {
            mTvSend.setEnabled(false);
            mChoseBean = message.giftInfo;
            mChoseBagBean = null;
            mCount = 1;
            mTvChoose.setText(String.valueOf(mCount));
            mTvChoose.setVisibility(View.GONE);
            mIcArrow.setVisibility(View.GONE);

            mTvSend.setBackgroundResource(R.drawable.bg_chat_gift_send_2);

            return;
        }

        mTvSend.setEnabled(true);
        mChoseBean = message.giftInfo;
        mChoseBagBean = null;
        //0普通礼物
        switch (mChoseBean.getAnimat_type()) {
            case 0:
                mCount = 1;
                mTvChoose.setText(String.valueOf(mCount));

                mTvChoose.setVisibility(View.VISIBLE);
                mIcArrow.setVisibility(View.VISIBLE);
                mTvSend.setBackgroundResource(R.drawable.bg_chat_gift_send);

                break;
            default:
                mCount = 1;
                mTvChoose.setText(String.valueOf(mCount));
                mTvChoose.setVisibility(View.GONE);
                mIcArrow.setVisibility(View.GONE);

                mTvSend.setBackgroundResource(R.drawable.bg_chat_gift_send_2);


                break;

        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBagGiftChose(OnBagGiftChoseBus message) {

        if (message.giftInfo == null) {
            mTvSend.setEnabled(false);
            mChoseBean=null;
            mChoseBagBean = message.giftInfo;
            mCount = 1;
            mTvChoose.setText(String.valueOf(mCount));
            mTvChoose.setVisibility(View.GONE);
            mIcArrow.setVisibility(View.GONE);

            mTvSend.setBackgroundResource(R.drawable.bg_chat_gift_send_2);

            return;
        }

        mTvSend.setEnabled(true);
        mChoseBean=null;
        mChoseBagBean = message.giftInfo;

        //0普通礼物
        mCount = 1;
        mTvChoose.setText(String.valueOf(mCount));
        mTvChoose.setVisibility(View.GONE);
        mIcArrow.setVisibility(View.GONE);

        mTvSend.setBackgroundResource(R.drawable.bg_chat_gift_send_2);
    }

    private void showGiftCount() {


        View v = LayoutInflater.from(getContext()).inflate(R.layout.gift_count_layout, null);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true));
        ChatGiftCountAdapter adapter = new ChatGiftCountAdapter(getContext());
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        if (isVertical) {
            mGiftCountPopupWindow = new PopupWindow(v, ArmsUtils.dip2px(getContext(), 70),
                    ViewGroup.LayoutParams.WRAP_CONTENT, true);
        } else {
            mGiftCountPopupWindow = new PopupWindow(v, ArmsUtils.dip2px(getContext(), 70),
                    ArmsUtils.dip2px(getContext(), 150), true);
        }


        mGiftCountPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mGiftCountPopupWindow.setOutsideTouchable(true);
        mGiftCountPopupWindow.showAtLocation(mTvChoose, Gravity.BOTTOM | Gravity.RIGHT,
                ArmsUtils.dip2px(getContext(), 70), ArmsUtils.dip2px(getContext(), 40));


    }


    @Override
    public void onItemClick(Object bean, int position) {
        if (mGiftCountPopupWindow != null) {
            mGiftCountPopupWindow.dismiss();
        }
        String result= (String) bean;
        mCount =Integer.valueOf(result);
        mTvChoose.setText(String.valueOf(mCount));
    }


    /**
     * 注释
     * 显示添加fragment
     */

    public void addFragment(Fragment tabFragment) {
        if (tabFragment == null) {
            return;
        }
        if (tabFragment.isAdded()) {
            showFragment(tabFragment);
        } else {
            hideElseFragment(tabFragment);
            getChildFragmentManager().beginTransaction()
                    .add(R.id.fl_fragment, tabFragment)
                    .commitAllowingStateLoss();
        }
    }

    /**
     * 注释
     * 显示页面
     */
    public void showFragment(Fragment fragment) {
        hideElseFragment(fragment);
        getChildFragmentManager().beginTransaction()

                .show(fragment)
                .commitAllowingStateLoss();
    }

    /**
     * 注释
     * 隐藏页面
     */
    public void hideElseFragment(Fragment fragment) {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {
            Fragment tabFragmen = mFragments.get(i);
            if (fragment == tabFragmen) {
                continue;
            }
            if (fragment != null && tabFragmen.isAdded()) {
                transaction.hide(tabFragmen);
            }
        }
        transaction.commitAllowingStateLoss();
    }
}
