package com.feicui365.live.live.fragment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;


import com.feicui365.live.eventbus.LiveManageBus;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.interfaces.OnCancelClickLinstener;
import com.feicui365.live.live.adapter.LiveBanAdapter;
import com.feicui365.live.live.utils.AdapterLayoutUtils;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class LiveBanFragment extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srf_layout)
    SmartRefreshLayout srfLayout;
    int mPage = 1;
    ArrayList<UserRegist> mDatas;
    LiveBanAdapter mAdapter;
    String mAnchorId;


    public LiveBanFragment(String mAnchorId) {
        this.mAnchorId = mAnchorId;
    }

    @Override
    protected void lazyLoad() {
        mPresenter.getBannedUserList(mAnchorId, mPage);

    }

    @Override
    public void getBannedUserList(ArrayList<UserRegist> bean) {

        srfLayout.finishRefresh();
        srfLayout.finishLoadMore();
        if (mAdapter.getEmptyViewCount() == 0) {
            mAdapter.setEmptyView(R.layout.view_nothing_layout);
        }
        if (!ArmsUtils.isArrEmpty(bean)) {
            if (mPage == 1) {
                srfLayout.setEnableLoadMore(false);
                mDatas.clear();
            } else {
                mPage--;
                srfLayout.setEnableLoadMore(false);
            }
            mAdapter.notifyDataSetChanged();
            return;
        }

        if (mPage == 1) {
            mDatas.clear();
        }
        mDatas.addAll(bean);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView(View view) {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        initList();
        initRefresh();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChange(LiveManageBus message) {
        if (rvList == null) {
            return;
        }
        if (mAdapter == null) {
            return;
        }
        lazyLoad();
    }

    private void initList() {
        mDatas = new ArrayList<>();
        mAdapter = new LiveBanAdapter();
        mAdapter.setNewData(mDatas);
        rvList.setLayoutManager(AdapterLayoutUtils.getLin(getContext()));
        mAdapter.bindToRecyclerView(rvList);
        rvList.setAdapter(mAdapter);
        mAdapter.setOnCancelClickLinstener(new OnCancelClickLinstener() {
            @Override
            public void onCancelClick(String data1, int data2) {
                mPresenter.banUser(mAnchorId, data1, 2, 1 + "");
                mAdapter.remove(data2);
            }
        });
    }


    public void refreshData() {
        mPage = 1;
        lazyLoad();
    }


    private void initRefresh() {
        srfLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                mPage = 1;
                lazyLoad();
            }
        });
        srfLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                mPage++;
                lazyLoad();
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.list_layout;
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
}
