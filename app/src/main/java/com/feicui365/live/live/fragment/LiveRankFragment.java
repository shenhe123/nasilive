package com.feicui365.live.live.fragment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.live.adapter.LiveRankAdapter;
import com.feicui365.live.live.utils.AdapterLayoutUtils;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.presenter.LivePushPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class LiveRankFragment extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View{


    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srf_layout)
    SmartRefreshLayout srfLayout;
    int mPage = 1;
    ArrayList<ContributeRank> mDatas;
    LiveRankAdapter mAdapter;
    String mLiveId;



    @SuppressLint("ValidFragment")
    public LiveRankFragment(String mLiveId) {
        this.mLiveId = mLiveId;
    }

    @Override
    protected void lazyLoad() {
        mPresenter.getContributeRank(mLiveId);

    }

    @Override
    public void setContributeRank(BaseResponse<ArrayList<ContributeRank>> bean) {
        if(!bean.isSuccess()){
            return;
        }



        srfLayout.finishRefresh();
        srfLayout.finishLoadMore();
        if (mAdapter.getEmptyViewCount()==0) {
            mAdapter.setEmptyView(R.layout.view_nothing_layout);
        }
        if (!ArmsUtils.isArrEmpty(bean.getData())) {
            if (mPage == 1) {
                srfLayout.setEnableLoadMore(false);
                mDatas.clear();
            } else {
                mPage--;
                srfLayout.setEnableLoadMore(false);
            }

            return;
        }
        if (mPage == 1) {
            mDatas.clear();
        }
        mDatas.addAll(bean.getData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initView(View view) {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        initList();
        initRefresh();
    }


    private void initList() {
        mDatas = new ArrayList<>();
        mAdapter = new LiveRankAdapter();
        mAdapter.setNewData(mDatas);
        mAdapter.bindToRecyclerView(rvList);
        rvList.setLayoutManager(AdapterLayoutUtils.getLin(getActivity()));
        rvList.setAdapter(mAdapter);

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
