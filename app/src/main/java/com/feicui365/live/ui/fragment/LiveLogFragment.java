package com.feicui365.live.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.AnchorHistory;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.act.LiveDetailActivity;
import com.feicui365.live.ui.adapter.AnchorHistoryAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LiveLogFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.rv_anchor_history)
    RecyclerView rv_anchor_history;
    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    AnchorHistoryAdapter adapter;
    List<AnchorHistory> lists = new ArrayList<>();
    int page = 1;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {

        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                lists.clear();
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                loadData();
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                loadData();
            }
        });

        loadData();
    }

    private void loadData() {
        mPresenter.livelog(page);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.anchor_history_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {
        if (page == 1) {
            lists.clear();
            adapter.notifyDataSetChanged();
            rl_nothing.setVisibility(View.VISIBLE);
            refreshLayout.finishRefresh(true);
        } else {
            page--;
            refreshLayout.finishLoadMore(true);
        }
    }

    @Override
    public void setLivelog(ArrayList<AnchorHistory> bean) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (page == 1) {
            lists.clear();
            if (bean.size() == 0) {

                rl_nothing.setVisibility(View.VISIBLE);

                return;
            }
        } else if (page > 1) {
            if (bean.size() == 0) {
                page--;
                refreshLayout.setEnableLoadMore(false);
                return;
            }
        }
        rl_nothing.setVisibility(View.GONE);
        lists.addAll(bean);
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rv_anchor_history.setLayoutManager(linearLayoutManager);
            adapter = new AnchorHistoryAdapter(lists);
            rv_anchor_history.setAdapter(adapter);
            adapter.setOnAnchorHistoryClickListener(new AnchorHistoryAdapter.OnAnchorHistoryClickListener() {
                @Override
                public void onItemClick(String id) {
                    Intent intent = new Intent(getContext(), LiveDetailActivity.class);
                    intent.putExtra("liveid", id);
                    startActivity(intent);
                }
            });

        } else {
            adapter.notifyDataSetChanged();
        }

    }
}
