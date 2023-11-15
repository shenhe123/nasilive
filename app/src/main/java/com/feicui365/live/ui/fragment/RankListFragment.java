package com.feicui365.live.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
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
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.ui.adapter.ContributionAdapter;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class RankListFragment extends OtherBaseFragment {

    List<ContributeRank> all_lists = new ArrayList<>();
    RelativeLayout rl_nothing;
    RecyclerView rv_follow;
    RefreshLayout refreshLayout;
    private int type;

    private int page = 1;
    ContributionAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_follow, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");

        }
        rv_follow = view.findViewById(R.id.rv_follow);
        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(true);
        rl_nothing = view.findViewById(R.id.rl_nothing);



        loadData();


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                all_lists.clear();
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

    }

    private void loadData() {
        if (type == 1) {
            HttpUtils.getInstance().getWeekIntimacyRank(MyUserInstance.getInstance().getUserinfo().getProfile().getUid(), page + "", new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();
                    JSONObject data = HttpUtils.getInstance().check(response);
                    if( HttpUtils.getInstance().swtichStatus(data)){
                        if (null != data.getJSONArray("data")) {
                            JSONArray datas = data.getJSONArray("data");
                            List<ContributeRank> lists = JSON.parseArray(datas.toJSONString(), ContributeRank.class);
                            if (lists.size() == 0) {
                                if(page==1){
                                    rl_nothing.setVisibility(View.VISIBLE);
                                }
                                if(page>1){
                                    page--;
                                    refreshLayout.setEnableLoadMore(false);
                                }
                                return;
                            }
                            rl_nothing.setVisibility(View.GONE);
                            all_lists.addAll(lists);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            rv_follow.setLayoutManager(linearLayoutManager);
                            if(adapter==null){
                                adapter=new ContributionAdapter(all_lists, getActivity());
                            }else{
                                adapter.notifyDataSetChanged();
                            }

                            rv_follow.setAdapter(adapter);
                        } else {
                            if(page>1){
                                page--;
                                refreshLayout.setEnableLoadMore(false);
                            }
                        }
                    } else {
                        if(page>1){
                            page--;
                        }

                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();
                    if(page>1){
                        page--;
                    }
                }
            });
        } else {
            HttpUtils.getInstance().getTotalIntimacyRank(MyUserInstance.getInstance().getUserinfo().getProfile().getUid(), page + "", new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();
                    JSONObject data = HttpUtils.getInstance().check(response);
                    if( HttpUtils.getInstance().swtichStatus(data)){
                        if (null != data.getJSONArray("data")) {
                            JSONArray datas = data.getJSONArray("data");
                            List<ContributeRank> lists = JSON.parseArray(datas.toJSONString(), ContributeRank.class);
                            if (lists.size() == 0) {
                                if(page==1){
                                    rl_nothing.setVisibility(View.VISIBLE);
                                }

                                if(page>1){
                                    page--;
                                    refreshLayout.setEnableLoadMore(false);
                                }
                                return;
                            }
                            rl_nothing.setVisibility(View.GONE);
                            all_lists.addAll(lists);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                            rv_follow.setLayoutManager(linearLayoutManager);
                            if(adapter==null){
                                adapter=new ContributionAdapter(all_lists, getActivity());
                            }else{
                                adapter.notifyDataSetChanged();
                            }
                            rv_follow.setAdapter(adapter);
                        } else {
                            if(page>1){
                                page--;
                                refreshLayout.setEnableLoadMore(false);
                            }
                        }
                    } else {
                        if(page>1){
                            page--;
                        }
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    refreshLayout.finishLoadMore();
                    refreshLayout.finishRefresh();
                    if(page>1){
                        page--;
                    }
                }
            });
        }

    }
}
