package com.feicui365.live.ui.act;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.model.entity.SystemMeaasge;
import com.feicui365.live.ui.adapter.SystemMessageAdapter;
import com.feicui365.live.util.HttpUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class SystemMessageActivity extends OthrBase2Activity {

    RecyclerView cv_system_message;
    String lastId = "";
    RelativeLayout rl_nothing;
    RefreshLayout refreshLayout;
    List<SystemMeaasge> all_lists = new ArrayList<>();
    SystemMessageAdapter systemMessageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.system_message_activity;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("系统消息");
        cv_system_message = findViewById(R.id.cv_system_message);

        refreshLayout = findViewById(R.id.refreshLayout);
        rl_nothing = findViewById(R.id.rl_nothing);


        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadData();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                lastId = "";
                loadData();
            }
        });
        loadData();

        V2TIMManager.getMessageManager().markC2CMessageAsRead("admin", new V2TIMCallback() {
            @Override
            public void onSuccess() {
                Log.i("imsdk", "success");
            }

            @Override
            public void onError(int code, String desc) {
                Log.i("imsdk", "failure, code:" + code + ", desc:" + desc);
            }
        });
    }

    private void loadData() {

        HttpUtils.getInstance().getSystemMsg(lastId, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
                JSONObject data = HttpUtils.getInstance().check(response);
                if (HttpUtils.getInstance().swtichStatus(data)) {
                    if (null != data.getJSONArray("data")) {
                        List<SystemMeaasge> lists = JSON.parseArray(data.getJSONArray("data").toJSONString(), SystemMeaasge.class);
                        if (lists.size() == 0 & lastId.equals("")) {
                            rl_nothing.setVisibility(View.VISIBLE);
                            return;
                        }
                        if (lists.size() == 0 & !lastId.equals("")) {
                            return;
                        }
                        rl_nothing.setVisibility(View.GONE);
                        all_lists.addAll(lists);
                        if (systemMessageAdapter != null) {
                            systemMessageAdapter.notifyDataSetChanged();
                        } else {
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SystemMessageActivity.this);
                            cv_system_message.setLayoutManager(linearLayoutManager);
                            systemMessageAdapter = new SystemMessageAdapter(all_lists, SystemMessageActivity.this);
                            cv_system_message.setAdapter(systemMessageAdapter);
                        }
                        lastId = lists.get(lists.size() - 1).getId();

                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                refreshLayout.finishRefresh(true);
                refreshLayout.finishLoadMore(true);
                rl_nothing.setVisibility(View.VISIBLE);
            }
        });


    }


}
