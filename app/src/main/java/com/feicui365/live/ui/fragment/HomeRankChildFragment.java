package com.feicui365.live.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OtherBaseFragment;
import com.feicui365.live.interfaces.OnUserInfoClickListener;
import com.feicui365.live.model.entity.RankAnchorItem;
import com.feicui365.live.model.entity.RankItem;
import com.feicui365.live.ui.act.AnchorCenterActivity;
import com.feicui365.live.ui.adapter.RankAnchorItemAdapter;
import com.feicui365.live.ui.adapter.RankItemAdapter;
import com.feicui365.live.util.HttpUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

@SuppressLint("ValidFragment")
public class HomeRankChildFragment extends OtherBaseFragment implements OnUserInfoClickListener {


    RecyclerView rv_rank;
    public String page = "";
    SmartRefreshLayout refreshLayout ;
    private String type="0";
    public HomeRankChildFragment(String page) {
        this.page = page;
    }

    private RelativeLayout rl_nothing;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_rank_child, container, false);
        initView(view);
        initData(page, "0");
        refreshLayout=view.findViewById(R.id.refreshLayout);
        rl_nothing=view.findViewById(R.id.rl_nothing);
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initData(page, type);
            }
        });
        return view;
    }

    List<RankItem> rankItems;
    List<RankAnchorItem> rankAnchorItems;
    RankItemAdapter rankItemAdapter;
    RankAnchorItemAdapter rankAnchorItemAdapter;

    public HomeRankChildFragment() {
    }

    public void initData(String page, String type) {

     this.type=type;
        switch (page) {
            case "0":
                HttpUtils.getInstance().getUserRankList(type, new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        refreshLayout.finishRefresh();
                        JSONObject data = (JSONObject) HttpUtils.getInstance().check(response);
                        if (null != data) {
                            JSONArray jsonArray = data.getJSONArray("data");
                            if (null != rankItems) {
                                rankItems.clear();
                            }
                            if(jsonArray==null){
                                rl_nothing.setVisibility(View.VISIBLE);
                                return;
                            }

                            if(jsonArray.size()==0){
                                rl_nothing.setVisibility(View.VISIBLE);
                                return;
                            }
                            rl_nothing.setVisibility(View.GONE);
                            rankItems = JSON.parseArray(jsonArray.toJSONString(), RankItem.class);
                            if (rankItemAdapter == null) {
                                rankItemAdapter = new RankItemAdapter(getContext(), rankItems, page);
                                rankItemAdapter.setOnUserInfoClickListener(HomeRankChildFragment.this);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

                                rv_rank.setLayoutManager(layoutManager);
                                rv_rank.setAdapter(rankItemAdapter);
                            } else {
                                rankItemAdapter.setRankItems(rankItems);
                                rankItemAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
                break;
            case "1":
                HttpUtils.getInstance().getAnchorRankList(type, new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        refreshLayout.finishRefresh();
                        JSONObject data = (JSONObject) HttpUtils.getInstance().check(response);
                        if (null != data) {
                            JSONArray jsonArray = data.getJSONArray("data");
                            if (null != rankAnchorItems) {
                                rankAnchorItems.clear();
                            }
                            if(jsonArray==null){
                                rl_nothing.setVisibility(View.VISIBLE);
                                return;
                            }

                            if(jsonArray.size()==0){
                                rl_nothing.setVisibility(View.VISIBLE);
                                return;
                            }
                            rl_nothing.setVisibility(View.GONE);
                            rankAnchorItems = JSON.parseArray(jsonArray.toJSONString(), RankAnchorItem.class);
                            if (rankItemAdapter == null) {
                                rankAnchorItemAdapter = new RankAnchorItemAdapter(getContext(), rankAnchorItems, page);
                                rankAnchorItemAdapter.setOnUserInfoClickListener(HomeRankChildFragment.this);
                                LinearLayoutManager lr = new LinearLayoutManager(getContext());
                                lr.setOrientation(OrientationHelper.VERTICAL);
                                rv_rank.setLayoutManager(lr);
                                rv_rank.setAdapter(rankAnchorItemAdapter);
                            } else {
                                rankAnchorItemAdapter.setRankItems(rankAnchorItems);
                                rankAnchorItemAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
                break;

        }


    }

    private void initView(View view) {
        rv_rank = view.findViewById(R.id.rv_rank);

    }


    @Override
    public void onAnchorAvatarClick(RankAnchorItem live) {


        startActivity(new Intent(getContext(), AnchorCenterActivity.class)
                .putExtra("data",live.getAnchorid()));

    }

    @Override
    public void onAvatarClick(RankItem live) {
        startActivity(new Intent(getContext(), AnchorCenterActivity.class)
                .putExtra("data",live.getUid()));

    }
}
