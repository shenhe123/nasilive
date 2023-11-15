package com.feicui365.live.shop.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.SellerCashOutHistorydapter;
import com.feicui365.live.shop.entity.SellerCashOut;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SellerCashOutHistoryActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {
    /**
     * 提现记录
     **/

    int page = 1;
    List<SellerCashOut> cashOutHistories = new ArrayList<>();
    SellerCashOutHistorydapter sellerCashOutHistorydapter;
    @BindView(R.id.rv_cash_out_history)
    RecyclerView rv_cash_out_history;
    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.cash_out_history_activity;
    }

    @Override
    protected void initData() {

        mPresenter.SellerwithdrawLog(String.valueOf(page));


    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("提现记录");

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
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
            sellerCashOutHistorydapter.notifyDataSetChanged();
            rl_nothing.setVisibility(View.VISIBLE);
            refreshLayout.finishRefresh(true);
        } else {
            page--;
            refreshLayout.finishLoadMore(true);
        }
    }

    @Override
    public void SellerwithdrawLog(ArrayList<SellerCashOut> bean) {
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
        if (sellerCashOutHistorydapter == null) {

            rv_cash_out_history.setLayoutManager(new LinearLayoutManager(context));
            sellerCashOutHistorydapter = new SellerCashOutHistorydapter(cashOutHistories);
            rv_cash_out_history.setAdapter(sellerCashOutHistorydapter);
            rv_cash_out_history.smoothScrollToPosition(0);
        } else {
            sellerCashOutHistorydapter.notifyDataSetChanged();
        }
    }


}
