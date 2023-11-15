package com.feicui365.live.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;

import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.HomePresenter;

import com.feicui365.live.ui.adapter.AttendUserAdatper;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AttentUserFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {

    int page = 1;
    List<UserRegist> all_lists = new ArrayList<>();
    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;
    @BindView(R.id.rv_follow)
    RecyclerView rv_follow;
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    AttendUserAdatper followAdapter;

    String type = "";
    private String keyword="";

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    public RefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                page++;
                loadData();
                //传入false表示加载失败
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                all_lists.clear();

                if (followAdapter != null) {
                    followAdapter.notifyDataSetChanged();
                }
                refreshLayout.setEnableLoadMore(true);
                loadData();
            }
        });
        if(!type.equals("3")){
            loadData();
        }

    }


    public AttentUserFragment(String type) {
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user_follow;
    }

    private void loadData() {

        switch (type) {
            case "1":
                mPresenter.getAttentAnchors(page);
                break;
            case "2":
                mPresenter.getFans(page);
                break;
            case "3":
                rl_nothing.setVisibility(View.VISIBLE);
                mPresenter.searchAnchor(page,keyword);
                break;
        }

    }

    @Override
    public void setAttentUser(ArrayList<UserRegist> bean) {
        if (page == 1) {
            refreshLayout.finishRefresh();
            all_lists.clear();
        } else {
            refreshLayout.finishLoadMore();
        }
        if (bean.size() == 0 & page == 1) {
            rl_nothing.setVisibility(View.VISIBLE);
            return;
        }
        if (bean.size() == 0 & page > 1) {
            page--;
            refreshLayout.setEnableLoadMore(false);
            return;
        }

        List<UserRegist> lists =bean;

        rl_nothing.setVisibility(View.GONE);
        all_lists.addAll(lists);
        if (followAdapter == null) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            rv_follow.setLayoutManager(linearLayoutManager);
            followAdapter = new AttendUserAdatper(all_lists, getActivity());
            rv_follow.setAdapter(followAdapter);
        } else {
            followAdapter.notifyDataSetChanged();
        }


    }

    public void search(String word){
        keyword=word;
        page = 1;
        all_lists.clear();

        if (followAdapter != null) {
            followAdapter.notifyDataSetChanged();
        }
        refreshLayout.setEnableLoadMore(true);
        loadData();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {
        if (page > 1) {
            page--;
        }
        refreshLayout.finishLoadMore(true);
        refreshLayout.finishRefresh(true);
    }

    @Override
    public void showMgs(String msg) {
        if(msg.contains("参数")){
            refreshLayout.finishLoadMore(true);
            refreshLayout.finishRefresh(true);
            return;
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
    }
}
