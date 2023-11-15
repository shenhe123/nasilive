package com.feicui365.live.live.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.eventbus.UserListBus;
import com.feicui365.live.interfaces.OnCancelClickLinstener;
import com.feicui365.live.interfaces.OnGroupClickLinstener;
import com.feicui365.live.live.adapter.LiveBanAdapter;
import com.feicui365.live.live.adapter.LiveUserAdapter;
import com.feicui365.live.live.utils.AdapterLayoutUtils;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.AllUser;
import com.feicui365.live.model.entity.BanStatus;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.RoomManager;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.imsdk.v2.V2TIMGroupMemberFullInfo;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfoResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tuicore.util.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class LiveUserFragment extends BaseMvpFragment<LivePushPresenter> implements LivePushContrat.View {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.srf_layout)
    SmartRefreshLayout srfLayout;
    int mPage = 0;
    ArrayList<AllUser.ListBean> mDatas;
    LiveUserAdapter mAdapter;
    String mAnchorId;
    private int banStatus;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void lazyRefresh(UserListBus event) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showT(R.string.kick_success);
                lazyLoad();
            }
        }, 1000);
    }

    public LiveUserFragment(String mAnchorId) {
        this.mAnchorId = mAnchorId;
    }


    @Override
    protected void lazyLoad() {
        mPresenter.getAllUserList();
//        V2TIMManager.getGroupManager().getGroupMemberList(ArmsUtils.initLiveGroupId(mAnchorId),
//                V2TIMGroupMemberFullInfo.V2TIM_GROUP_MEMBER_FILTER_ALL, mPage, new V2TIMValueCallback<V2TIMGroupMemberInfoResult>() {
//                    @Override
//                    public void onSuccess(V2TIMGroupMemberInfoResult result) {
//                        srfLayout.finishRefresh();
//                        srfLayout.finishLoadMore();
//                        if (mPage == 0) {
//                            mDatas.clear();
//                        }
//                        if (mAdapter.getEmptyViewCount() == 0) {
//                            mAdapter.setEmptyView(R.layout.view_nothing_layout);
//                        }
//                        mPage = new Long(result.getNextSeq()).intValue();
//                        for (int i = 0; i < result.getMemberInfoList().size(); i++) {
//                            if (ArmsUtils.isStringEmpty(result.getMemberInfoList().get(i).getUserID())) {
//                                UserRegist userRegist = new UserRegist();
//                                userRegist.setNick_name(result.getMemberInfoList().get(i).getNickName());
//                                userRegist.setId(result.getMemberInfoList().get(i).getUserID());
//                                userRegist.setAvatar(result.getMemberInfoList().get(i).getFaceUrl());
//                                mDatas.add(userRegist);
//                            }
//                        }
//                        mAdapter.notifyDataSetChanged();
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//
//                        srfLayout.finishRefresh();
//                        srfLayout.finishLoadMore();
//                    }
//                });

    }


    @Override
    protected void initView(View view) {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initList();
        initRefresh();
    }

    private void initList() {
        mDatas = new ArrayList<>();
        mAdapter = new LiveUserAdapter();
        mAdapter.setNewData(mDatas);
        rvList.setLayoutManager(AdapterLayoutUtils.getLin(getContext()));
        mAdapter.bindToRecyclerView(rvList);
        rvList.setAdapter(mAdapter);
        mAdapter.setOnGroupClickLinstener(new OnGroupClickLinstener() {
            @Override
            public void onBanClick(String id, int type, String ban_type) {
                Log.e("111111", "开始: " + mAnchorId);
                mPresenter.banUser(mAnchorId, id, type, ban_type);
            }

            @Override
            public void onSetManagerClick(String id, int type) {
                mPresenter.setRoomMgr(id, type);
            }
        });

    }


    public void refreshData() {
        mPage = 0;
        lazyLoad();
    }

    private void initRefresh() {
        srfLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                mPage = 0;
                lazyLoad();
            }
        });
        srfLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {

                lazyLoad();
            }
        });
    }

    @Override
    public void banUser(String userID, BanStatus bean) {
        banStatus = bean.getBanStatus();
        //  lazyLoad();
        Map<String, Object> map = new HashMap<>();
        map.put("banStatus", banStatus);
        JSONObject jsonObject = new JSONObject(map);
        String s = jsonObject.toString();
//        String id = MyUserInstance.getInstance().getUserinfo().getId();
//        String id = MyUserInstance.getInstance().getUserinfo().getId();
//        V2TIMManager.getInstance().sendGroupCustomMessage(s.getBytes(),ArmsUtils.initLiveGroupId(id), V2TIMMessage.V2TIM_PRIORITY_NORMAL, new V2TIMValueCallback<V2TIMMessage>() {
//            @Override
//            public void onSuccess(V2TIMMessage message) {
//                // 发送群聊自定义消息成功
//                //EventBus.getDefault().post(new UserListBus());
//
//            }
//
//            @Override
//            public void onError(int code, String desc) {
//                // 发送群聊自定义消息失败
//
//            }
//        });
        Log.e("111111", "开始: " + s + "===" + userID);
        V2TIMManager.getInstance().sendC2CCustomMessage(s.getBytes(), userID, new V2TIMValueCallback<V2TIMMessage>() {
            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                Log.e("111111", "onSuccess: " + v2TIMMessage);

            }

            @Override
            public void onError(int i, String s) {
                Log.e("111111", "onError: " + i + "     " + s);
            }
        });
    }


    @Override
    public void getAllUserList(AllUser bean) {
        Log.e("liudanua", "===" + bean + "===" + bean.getList().size());
        srfLayout.finishRefresh();
        srfLayout.finishLoadMore();
        mDatas.clear();

        if (mAdapter.getEmptyViewCount() == 0) {
            mAdapter.setEmptyView(R.layout.view_nothing_layout);
        }
        if (bean != null && bean.getList() != null && bean.getList().size() > 0) {
            mDatas.addAll(bean.getList());
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.list_layout;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onDestroyView() {
        HttpUtils.getInstance().cleanAll();
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }
}
