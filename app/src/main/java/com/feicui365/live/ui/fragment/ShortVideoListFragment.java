package com.feicui365.live.ui.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.ShortVideo;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.act.UserShortVideoActivity;
import com.feicui365.live.ui.adapter.AnchorWorksAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import butterknife.BindView;

public class ShortVideoListFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.rl_nothing)
    FrameLayout rl_nothing;

    private int page = 1;
    private ArrayList<ShortVideo> data = new ArrayList<>();
    private AnchorWorksAdapter anchorWorksAdapter;
    private int type = 1;
    private String authorId = "";
    private String keyWord = "";
    private String status = "";
    private String topic = "";
    private String topic_type = "";

    public ShortVideoListFragment() {
    }

    @Override
    protected void lazyLoad() {

    }

    public void reDate() {
        page = 1;
        getDate();
    }


    @Override
    protected void initView(View view) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");

            if (getArguments().getString("authorId") != null) {
                authorId = getArguments().getString("authorId");
            }
            if (getArguments().getString("keyword") != null) {
                keyWord = getArguments().getString("keyword");
            }
            if (getArguments().getString("status") != null) {
                status = getArguments().getString("status");
            }
            if (getArguments().getString("topic") != null) {
                topic = getArguments().getString("topic");
            }
            if (getArguments().getString("topic_type") != null) {
                topic_type = getArguments().getString("topic_type");
            }
        }


        mPresenter = new HomePresenter();
        mPresenter.attachView(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        recycler.setLayoutManager(gridLayoutManager);
        anchorWorksAdapter = new AnchorWorksAdapter(data, getContext());
        recycler.setAdapter(anchorWorksAdapter);


        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getDate();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                getDate();
            }
        });
        anchorWorksAdapter.setOnItemClickListener(new AnchorWorksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                startActivityForResult(new Intent(getContext(), UserShortVideoActivity.class)
                        .putExtra("position", position)
                        .putExtra("data", data)
                        .putExtra("page", page)
                        .putExtra("is_refresh", "0")
                        .putExtra("authorid", data.get(position).getAuthor().getId()), 1003);
            }
        });
        if(type!=3){
            getDate();
        }

    }

    private void getDate() {

        switch (type) {
            case 1:
                refreshLayout.setEnableRefresh(false);
                mPresenter.getAnchorWorks(authorId, page);
                break;
            case 2:
                mPresenter.getCollectionShort(page + "");
                break;
            case 3:
                rl_nothing.setVisibility(View.VISIBLE);
                mPresenter.searchShort(page, keyWord);
                break;
            case 4:

                mPresenter.getMyshort(status, page);
                break;
            case 5:

                mPresenter.getUserLike(authorId, page);
                break;
            case 6:

                mPresenter.videoListInTopic(topic, topic_type,page+"");
                break;
        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1003) {
            ArrayList<ShortVideo> data_back = (ArrayList<ShortVideo>) data.getSerializableExtra("data");
            this.data.clear();
            this.data.addAll(data_back);
            anchorWorksAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void setAnchorWorks(ArrayList<ShortVideo> bean) {
        refreshLayout.finishRefresh(true);
        refreshLayout.finishLoadMore(true);
        if (page == 1) {
            data.clear();
            if (bean.size() == 0) {
                rl_nothing.setVisibility(View.VISIBLE);
                return;
            }
        }
        if (page > 1) {
            if (bean.size() == 0) {
                page--;
                refreshLayout.setEnableLoadMore(false);
                return;
            }
        }
        rl_nothing.setVisibility(View.GONE);
        data.addAll(bean);
        anchorWorksAdapter.notifyDataSetChanged();


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_anchor_works;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    @Override
    public void onError(Throwable throwable) {
        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
        if (page == 1) {
            refreshLayout.finishRefresh(false);
            anchorWorksAdapter.notifyDataSetChanged();
        } else {
            page--;
            refreshLayout.finishLoadMore(false);
        }
    }


    public void search(String word) {
        keyWord = word;
        data.clear();
        anchorWorksAdapter.notifyDataSetChanged();
        refreshLayout.setEnableLoadMore(true);
        page = 1;
        getDate();
    }


}