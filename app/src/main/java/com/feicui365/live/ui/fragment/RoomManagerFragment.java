package com.feicui365.live.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.RoomManager;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.RoomManagerAdapter;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.ToastUtils;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

public class RoomManagerFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {


    @BindView(R.id.rl_mange)
    RecyclerView rl_mange;

    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    ArrayList<RoomManager> lists = new ArrayList<>();
    private RoomManagerAdapter roomManagerAdapter;
    int page = 1;


    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
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


        initData();
    }

    private void initData() {
        mPresenter.getMgrList();
    }


    @Override
    public void setRoomManager(ArrayList<RoomManager> bean) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (page == 1) {
            lists.clear();
            if (bean.size() == 0) {

                        rl_nothing.setVisibility(View.VISIBLE);

                return;
            }
        } else if (page > 1) {
            if (bean.size() == 0) {
                page--;
                return;
            }
        }

        rl_nothing.setVisibility(View.GONE);
        lists.addAll(bean);
        if (roomManagerAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rl_mange.setLayoutManager(linearLayoutManager);
            roomManagerAdapter = new RoomManagerAdapter(lists, getContext());
            rl_mange.setAdapter(roomManagerAdapter);
            roomManagerAdapter.setCancelListener(new RoomManagerAdapter.CancelListener() {
                @Override
                public void Cancel(UserRegist userRegist) {
                    HttpUtils.getInstance().setRoomMgr(userRegist.getId(), "0", new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject jsonObject = JSON.parseObject(response.body());
                            if (jsonObject.getString("status").equals("0")) {
                                ToastUtils.showT("解除房管");
                                page = 1;
                                lists.clear();
                                roomManagerAdapter.notifyDataSetChanged();
                                initData();
                            }
                        }
                    });
                }


            });
        } else {
            roomManagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_room_manager;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {
        String error = throwable.toString();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
