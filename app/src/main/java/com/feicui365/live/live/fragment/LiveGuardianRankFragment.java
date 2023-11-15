package com.feicui365.live.live.fragment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;


import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.live.adapter.GuardianRankListAdapter;
import com.feicui365.live.live.utils.AdapterLayoutUtils;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.Guardian;
import com.feicui365.live.presenter.LivePushPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class LiveGuardianRankFragment extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.rv_list_top3)
    RecyclerView rvListTop3;


    @BindView(R.id.srf_layout)
    SmartRefreshLayout srfLayout;

    GuardianRankListAdapter mAdapter;
    GuardianRankListAdapter mAdapterTop3;
    String mAnchorId;
    int mPage = 1;
    ArrayList<Guardian> mDatas;
    ArrayList<Guardian> mDatasTop3;

    @SuppressLint("ValidFragment")
    public LiveGuardianRankFragment(String mAnchorId) {
        this.mAnchorId = mAnchorId;
    }

    @Override
    protected void lazyLoad() {
        mPresenter.getGuardianList(mAnchorId, String.valueOf(mPage));

    }


    @Override
    public void getGuardianList(ArrayList<Guardian> bean) {

        srfLayout.finishRefresh();
        srfLayout.finishLoadMore();
        if (mAdapterTop3.getEmptyViewCount() == 0) {
            mAdapterTop3.setEmptyView(R.layout.view_nothing_layout);
        }
        if (!ArmsUtils.isArrEmpty(bean)) {
            if (mPage == 1) {
                mDatas.clear();
                mDatasTop3.clear();
                srfLayout.setEnableLoadMore(false);
                mAdapter.notifyDataSetChanged();
                mAdapterTop3.notifyDataSetChanged();

            } else {
                mPage--;
                srfLayout.setEnableLoadMore(false);
            }
            return;
        }

        if (mPage == 1) {
            mDatas.clear();
            mDatasTop3.clear();
            if(mAdapterTop3!=null){
                mAdapterTop3.removeAllHeaderView();
            }
            if(mAdapter!=null){
                mAdapter.removeAllHeaderView();
            }
            if (bean.size() < 20) {
                srfLayout.setEnableLoadMore(false);
            }
        }

        //开始做数据拆分,首先如果数据是第一页,并且数据小于等于3个,因为涉及到头3个数据的不同以及后面数据的添加,需要根据数据量来

        if (mPage == 1) {
            if (bean.size() <= 3) {


                mDatasTop3.addAll(bean);
                mAdapterTop3.addHeaderView(initTop3Header());
                mAdapterTop3.notifyDataSetChanged();
                return;
            } else {

                //如果数据大于3个,那么前3个添加到头部,其他的添加正常序列
                for (int i = 0; i < 3; i++) {
                    mDatasTop3.add(bean.get(i));
                }
                mAdapterTop3.addHeaderView(initTop3Header());

                mAdapterTop3.notifyDataSetChanged();
                for (int i = 3; i < bean.size(); i++) {
                    mDatas.add(bean.get(i));
                }

                mAdapter.addHeaderView(initHeader());
                mAdapter.notifyDataSetChanged();
            }
        } else {
            mAdapter.addData(bean);

        }

    }



    private View initTop3Header(){
        View v = getLayoutInflater().inflate(R.layout.guardian_item_head, rvListTop3, false);
        ImageView imageView = v.findViewById(R.id.iv_title);
        imageView.setImageResource(R.drawable.ic_guardian_rank_week);

        return v;
    }
    private View initHeader(){
        View v = getLayoutInflater().inflate(R.layout.guardian_item_head, rvListTop3, false);
        ImageView imageView = v.findViewById(R.id.iv_title);
        imageView.setImageResource(R.drawable.ic_guardian_rank_member);

        return v;
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
        mDatasTop3 = new ArrayList<>();

        mAdapter = new GuardianRankListAdapter(mDatas, 2);
        mAdapterTop3 = new GuardianRankListAdapter(mDatasTop3, 1);

        rvList.setLayoutManager(AdapterLayoutUtils.getLin(getContext()));
        rvList.setAdapter(mAdapter);
        mAdapter.bindToRecyclerView(rvList);
        rvListTop3.setLayoutManager(AdapterLayoutUtils.getLin(getContext()));
        rvListTop3.setAdapter(mAdapterTop3);
        mAdapterTop3.bindToRecyclerView(rvListTop3);
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
        return R.layout.guardian_rank_list_layout;
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
