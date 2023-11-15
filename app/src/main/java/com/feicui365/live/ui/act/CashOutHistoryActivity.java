package com.feicui365.live.ui.act;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;


import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.CashOutHistory;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.CashOutHistorydapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CashOutHistoryActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {
    /**
     * 提现记录
     **/

    int page = 1;
    List<CashOutHistory> cashOutHistories = new ArrayList<>();
    CashOutHistorydapter cashOutHistorydapter;
    @BindView(R.id.rv_cash_out_history)
    RecyclerView rv_cash_out_history;
    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    String type = "1";


    @Override
    protected int getLayoutId() {
        return R.layout.cash_out_history_activity;
    }

    @Override
    protected void initData() {
        switch (type) {
            case "1":
                mPresenter.withdrawlog(page);
                break;
            case "2":
                mPresenter.withdrawlog_agent(page);
                break;
        }


    }

    @Override
    protected void initView() {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        setTitle("提现记录");
        type = getIntent().getStringExtra("Type");
        if (type == null) {
            finish();
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                cashOutHistories.clear();
                if (cashOutHistorydapter != null) {
                    cashOutHistorydapter.notifyDataSetChanged();
                }
                initData();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();
            }
        });

    }

    @Override
    public void onError(Throwable throwable) {
        if (page == 1) {
            cashOutHistories.clear();
            cashOutHistorydapter.notifyDataSetChanged();
            rl_nothing.setVisibility(View.VISIBLE);
            refreshLayout.finishRefresh(true);
        } else {
            page--;
            refreshLayout.finishLoadMore(true);
        }
    }

    @Override
    public void setWithdrawlog(ArrayList<CashOutHistory> bean) {
        refreshLayout.finishLoadMore(true);
        refreshLayout.finishRefresh(true);
        if (page == 1) {
            cashOutHistories.clear();
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
        cashOutHistories.addAll(bean);
        if (cashOutHistorydapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashOutHistoryActivity.this);
            rv_cash_out_history.setLayoutManager(linearLayoutManager);
            cashOutHistorydapter = new CashOutHistorydapter(cashOutHistories, CashOutHistoryActivity.this);
            rv_cash_out_history.setAdapter(cashOutHistorydapter);
            rv_cash_out_history.smoothScrollToPosition(0);
        } else {
            cashOutHistorydapter.notifyDataSetChanged();
        }
    }
}
