/*
package com.feicui365.live.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.ContributeRank;

import com.feicui365.live.ui.act.LivePushActivity;
import com.feicui365.live.ui.adapter.ContributionAdapter;
import com.feicui365.live.ui.adapter.ShopAnchorItemAdapter;

import java.util.List;


public class CommentContributionList extends BottomPopupView {

    private RecyclerView rv_contribution_list;
    private List<ContributeRank> contributeRanks;
    private Context context;
    private ContributionAdapter contributionAdapter;

    //表情结束
    public CommentContributionList(@NonNull Context context, List<ContributeRank> contributeRanks) {
        super(context);
        this.contributeRanks = contributeRanks;
        this.context = context;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_contribution_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        if (contributeRanks == null) {
            dismiss();
            return;
        }
        if (contributeRanks.size() == 0) {
            dismiss();
            return;
        }
        rv_contribution_list = findViewById(R.id.rv_contribution_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        rv_contribution_list.setLayoutManager(linearLayoutManager);
        contributionAdapter = new ContributionAdapter(contributeRanks, context);
        rv_contribution_list.setAdapter(contributionAdapter);
    }


}*/
