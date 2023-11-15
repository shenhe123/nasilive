package com.feicui365.live.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.feicui365.live.model.entity.GiftIncome;
import com.feicui365.live.ui.adapter.MyIncomeAdapter;
import com.feicui365.live.util.HttpUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class MyIncomeFragment extends OtherBaseFragment {

    RecyclerView rv_my_youliao_plan;
    RelativeLayout rl_nothing;
    List<GiftIncome> myIncomes=new ArrayList<>();
    int type;
    int page=1;
    RefreshLayout refreshLayout;
    MyIncomeAdapter adapter;
    public MyIncomeFragment(int i) {
        type = i;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_income_fragment, container, false);
        rv_my_youliao_plan = view.findViewById(R.id.rv_my_income);
        rl_nothing = view.findViewById(R.id.rl_nothing);
        refreshLayout=view.findViewById(R.id.refreshLayout);


        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                initData(type);
                //传入false表示加载失败
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                myIncomes.clear();
                if(adapter!=null){
                    adapter.notifyDataSetChanged();
                }

                initData(type);
            }
        });
        initData(type);
        return view;
    }

    private void initData(int type) {

        switch (type) {

            case 1:
                HttpUtils.getInstance().giftProfit(page+"", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            if (page == 1) {
                                refreshLayout.finishRefresh();
                            } else {
                                refreshLayout.finishLoadMore();
                            }
                            JSONObject data = HttpUtils.getInstance().check(response);
                            if (data.get("status").toString().equals("0")) {
                                if (null != data.getJSONArray("data")) {
                                    JSONArray datas = data.getJSONArray("data");
                                    List<GiftIncome> lists = JSON.parseArray(datas.toJSONString(), GiftIncome.class);
                                    if (lists.size() == 0 & page == 1) {
                                        rl_nothing.setVisibility(View.VISIBLE);
                                        return;
                                    }
                                    if (lists.size() == 0 & page > 1) {
                                        return;
                                    }
                                    rl_nothing.setVisibility(View.GONE);
                                    myIncomes.addAll(lists);

                                    if (adapter == null) {
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                        rv_my_youliao_plan.setLayoutManager(linearLayoutManager);
                                        adapter = new MyIncomeAdapter(myIncomes, getActivity());
                                        rv_my_youliao_plan.setAdapter(adapter);
                                    } else {
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }

                        } catch (Exception e) {
                            Log.e("Exception", e.toString());
                        }

                    }
                });


                break;
            case 2:
                HttpUtils.getInstance().dongtaiProfit(page+"", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            if (page == 1) {
                                refreshLayout.finishRefresh();
                            } else {
                                refreshLayout.finishLoadMore();
                            }
                            JSONObject data = HttpUtils.getInstance().check(response);
                            if (data.get("status").toString().equals("0")) {
                                if (null != data.getJSONArray("data")) {
                                    JSONArray datas = data.getJSONArray("data");
                                    List<GiftIncome> lists = JSON.parseArray(datas.toJSONString(), GiftIncome.class);
                                    if (lists.size() == 0 & page == 1) {
                                        rl_nothing.setVisibility(View.VISIBLE);
                                        return;
                                    }
                                    if (lists.size() == 0 & page > 1) {
                                        return;
                                    }
                                    rl_nothing.setVisibility(View.GONE);
                                    myIncomes.addAll(lists);

                                    if (adapter == null) {
                                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                        rv_my_youliao_plan.setLayoutManager(linearLayoutManager);
                                        adapter = new MyIncomeAdapter(myIncomes, getActivity());
                                        rv_my_youliao_plan.setAdapter(adapter);
                                    } else {
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            }

                        } catch (Exception e) {
                            Log.e("Exception", e.toString());
                        }
                    }
                });
                break;

        }

    }
}
