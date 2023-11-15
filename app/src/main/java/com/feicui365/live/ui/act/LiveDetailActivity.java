package com.feicui365.live.ui.act;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.LiveRoomDetailBean;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.LiveRoomDetailAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LiveDetailActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View {


    @BindView(R.id.rv_live_detail)
    RecyclerView rv_live_detail;

    LiveRoomDetailAdapter adapter;

    int page = 1;
    private List<LiveRoomDetailBean> lists = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_detail;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setTitle("直播详情");
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        String liveid = getIntent().getStringExtra("liveid");
        loadData(liveid);

    }
    private void loadData(String liveid) {
        mPresenter.getLiveRoomDetail(liveid);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void getLiveRoomDetail(ArrayList<LiveRoomDetailBean> beans) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(LiveDetailActivity.this);
        rv_live_detail.setLayoutManager(linearLayoutManager);
        adapter = new LiveRoomDetailAdapter(beans,context);
        rv_live_detail.setAdapter(adapter);
    }
}
