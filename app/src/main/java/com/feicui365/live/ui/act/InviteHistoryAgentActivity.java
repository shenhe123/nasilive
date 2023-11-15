package com.feicui365.live.ui.act;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.Invite;
import com.feicui365.live.model.entity.InviteAgent;

import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.InviteHistoryAgentAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
//邀请记录
public class InviteHistoryAgentActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {
    @BindView(R.id.rv_my_income)
    RecyclerView rv_my_income;
    @BindView(R.id.tv_day_num)
    TextView tv_day_num;
    @BindView(R.id.tv_total_num)
    TextView tv_total_num;
    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    List<InviteAgent> myIncomes=new ArrayList<>();
    InviteHistoryAgentAdapter adapter;
    int page=1;
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
                if(adapter!=null){
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

    private void loadData(){
        mPresenter.getInviteList(page);
    }


    @Override
    protected void initView() {
        setTitle("邀请记录");
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void setInviteList(Invite bean) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();

        if (page == 1) {
            myIncomes.clear();
            if (bean.getList().size() == 0) {

                        rl_nothing.setVisibility(View.VISIBLE);

                return;
            }
        } else if (page > 1) {
            if (bean.getList().size() == 0) {
                page--;
                refreshLayout.setEnableLoadMore(false);
                return;
            }
        }
        rl_nothing.setVisibility(View.GONE);
        tv_day_num.setText(bean.getCount_today());
        tv_total_num.setText(bean.getCount_total());
        myIncomes.addAll(bean.getList());
        if (adapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            rv_my_income.setLayoutManager(linearLayoutManager);
            adapter = new InviteHistoryAgentAdapter(myIncomes,context);
            rv_my_income.setAdapter(adapter);

        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }
}


