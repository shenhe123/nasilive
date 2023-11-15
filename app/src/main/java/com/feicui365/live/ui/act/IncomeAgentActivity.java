package com.feicui365.live.ui.act;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.ProfitLog;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.MyIncomeAgentAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
//收益记录
public class IncomeAgentActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {

    @BindView(R.id.rv_my_income)
    RecyclerView rv_my_income;
    @BindView(R.id.ll_top)
    LinearLayout ll_top;
    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    ArrayList<ProfitLog> myIncomes = new ArrayList<>();
    MyIncomeAgentAdapter adapter;
    int page = 1;
    @Override
    protected int getLayoutId() {
        return R.layout.incom_agent_activity;
    }

    @Override
    protected void initData() {


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                myIncomes.clear();
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
        mPresenter.getProfitLog(page);
    }


    @Override
    public void setProfitLog(ArrayList<ProfitLog> bean) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();

        if (page == 1) {
            myIncomes.clear();
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
        myIncomes.addAll(bean);
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rv_my_income.setLayoutManager(linearLayoutManager);
            adapter = new MyIncomeAgentAdapter(myIncomes, context);
            rv_my_income.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void initView() {

        setTitle("收益记录");
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        ll_top.setVisibility(View.GONE);
    }

    @Override
    public void onError(Throwable throwable) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (page > 1) {
            page--;
        }
    }
}
