package com.feicui365.live.live.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.chad.library.adapter.base.BaseQuickAdapter;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.eventbus.BuyGuardianBus;
import com.feicui365.live.live.adapter.LiveGuardianBuyListAdapter;
import com.feicui365.live.live.adapter.LiveGuardianTipsListAdapter;
import com.feicui365.live.live.bean.BaseGuardianBuyInfo;
import com.feicui365.live.live.dialog.NotEnoughGoldDialog;
import com.feicui365.live.live.utils.AdapterLayoutUtils;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.BaseInitUtils;
import com.feicui365.live.model.entity.BuyGuard;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.MyUserInstance;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
@SuppressLint("ValidFragment")
public class LiveGuardianInfoFragment extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View {

    @BindView(R.id.rv_buy_guardian)
    RecyclerView rvList;
    @BindView(R.id.rv_guardian_info)
    RecyclerView rvTipsList;
    @BindView(R.id.tv_my_coin)
    TextView mTvCoin;
    @BindView(R.id.tv_over_time)
    TextView mTvTime;
    @BindView(R.id.tv_pay)
    TextView mTvPay;

    LiveGuardianBuyListAdapter mAdapter;
    LiveGuardianTipsListAdapter mTipsAdapter;
    GuardianInfo mGuardianInfo;
    BaseGuardianBuyInfo mChoseBean;
    String mAnchorId;

    public LiveGuardianInfoFragment(String mAnchorId) {
        this.mAnchorId = mAnchorId;
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
    protected void lazyLoad() {
        mPresenter.getGuardInfo(mAnchorId);
    }

    @Override
    public void getGuardInfo(GuardianInfo bean) {

        this.mGuardianInfo = bean;
        initType(bean);
    }

    @Override
    protected void initView(View view) {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        initList();
        mTvCoin.setText(getString(R.string.st_my_gold,String.valueOf(MyUserInstance.getInstance().getUserGold())));
    }

    private void initList() {
        if (mAdapter == null) {
            mAdapter = new LiveGuardianBuyListAdapter();
            mAdapter.setNewData(BaseInitUtils.getBaseGuardianBuyInfo());
            rvList.setLayoutManager(AdapterLayoutUtils.getGrid(getContext(), 3));
            mAdapter.bindToRecyclerView(rvList);
            rvList.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    mChoseBean = (BaseGuardianBuyInfo) adapter.getData().get(position);
                    if (mChoseBean.isCheck()) {
                        return;
                    }

                    mAdapter.setCheck(position);
                    if (mTipsAdapter != null) {
                        mTipsAdapter.setType(position);
                    }
                }
            });

            mTipsAdapter = new LiveGuardianTipsListAdapter();
            mTipsAdapter.setNewData(BaseInitUtils.getBaseGuardianTipsInfo());
            rvTipsList.setLayoutManager(AdapterLayoutUtils.getLin(getContext()));
            mTipsAdapter.bindToRecyclerView(rvTipsList);
            rvTipsList.setAdapter(mTipsAdapter);

        }


    }

    private void initType(GuardianInfo bean) {
        if (bean == null) {
            return;
        }

        mTipsAdapter.setType(Integer.valueOf(bean.getType()));
        mAdapter.setCheck(Integer.valueOf(bean.getType()));
        mChoseBean = mAdapter.getData().get(Integer.valueOf(bean.getType()));

        mTvPay.setText(getString(R.string.st_open_9off));
        mTvTime.setText(getString(R.string.st_over_time, bean.getExpire_time()));

    }

    @OnClick({R.id.tv_pay})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_pay:

                if (mChoseBean == null) {
                    return;
                }
                int price;
                String renew;
                if (mGuardianInfo == null) {
                    price = mChoseBean.getGold();
                    renew = "0";
                } else {
                    Double result = mChoseBean.getGold() * 0.9;
                    price = result.intValue();
                    renew = "1";
                }

                if (MyUserInstance.getInstance().getUserGold() < price) {
                    showGoldLowDialog();
                    return;
                }
                showLoading();
                mPresenter.buyGuard(mAnchorId, mChoseBean.getType(), renew);
                break;

        }
    }


    @Override
    public void buyGuard(BuyGuard bean) {
        this.mGuardianInfo = bean.getGuard();
        showLoading();
        initType(bean.getGuard());
        mPresenter.getUserInfo();
    }

    @Override
    public void getUserInfo(UserRegist bean) {
        hideLoading();
        MyUserInstance.getInstance().setUserInfo(bean);

        mTvCoin.setText(getString(R.string.st_my_gold,String.valueOf(MyUserInstance.getInstance().getUserGold())));
        EventBus.getDefault().post(new BuyGuardianBus());


    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_guardian_info_layout;
    }


    private void showGoldLowDialog(){
        NotEnoughGoldDialog notEnoughGoldDialog=new NotEnoughGoldDialog();
        notEnoughGoldDialog.show(getChildFragmentManager(),"");

    }
}
