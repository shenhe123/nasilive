package com.feicui365.live.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.presenter.SuperPlayerPresenter;
import com.feicui365.live.ui.adapter.ContributionAdapter;


import java.util.ArrayList;

import butterknife.BindView;

public class ContributionFragment extends BaseMvpFragment<SuperPlayerPresenter> implements SuperPlayerContrat.View {


    @BindView(R.id.rl_rank)
    RecyclerView rl_rank;

    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;
    private String liveid;
    private ContributionAdapter contributionAdapter;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        if(getArguments()!=null){
            liveid = getArguments().getString("id");
        }
        mPresenter = new SuperPlayerPresenter();
        mPresenter.attachView(this);
        mPresenter.getContributeRank(liveid);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contribution;
    }


    @Override
    public void setContributeRank(BaseResponse<ArrayList<ContributeRank>> contributeRank) {
        if (contributeRank.isSuccess()){
            ArrayList<ContributeRank> contributeRanks=contributeRank.getData();
            if(contributeRanks==null){
                rl_nothing.setVisibility(View.VISIBLE);
                return;
            }
            if(contributeRanks.size()==0){
                rl_nothing.setVisibility(View.VISIBLE);
                return;
            }
            rl_nothing.setVisibility(View.GONE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rl_rank.setLayoutManager(linearLayoutManager);
            contributionAdapter=new ContributionAdapter(contributeRanks, getContext());
            rl_rank.setAdapter(contributionAdapter);
        }



    }


    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {
        rl_nothing.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
