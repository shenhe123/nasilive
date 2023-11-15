package com.feicui365.live.shop.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.GoodsCommentAdapter;
import com.feicui365.live.shop.entity.Evaluate;
import com.feicui365.live.shop.entity.EvaluateList;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

public class GoodsCommentActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    GoodsCommentAdapter goodsCommentAdapter;

    @BindView(R.id.rv_goods_comment)
    public RecyclerView rv_goods_comment;
    ArrayList<Evaluate> evaluates_list = new ArrayList<>();

    int page = 1;
    String good_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_goods_comment;
    }

    @Override
    protected void initData() {
        mPresenter.goodsEvaluateList(good_id, String.valueOf(page));
    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("商品评论");
        initList();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.setEnableLoadMore(true);
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
        AppStatusManager.getInstance().setAppStatus(1);

        if(getIntent().getStringExtra(Constants.GOODS_ID)==null){
            finish();
        }

        good_id=getIntent().getStringExtra(Constants.GOODS_ID);
    }

    private void initList() {
        goodsCommentAdapter = new GoodsCommentAdapter(evaluates_list);
        rv_goods_comment.setLayoutManager(new LinearLayoutManager(this));
        rv_goods_comment.setAdapter(goodsCommentAdapter);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void goodsEvaluateList(EvaluateList bean) {
        refreshLayout.finishLoadMore(true);
        refreshLayout.finishRefresh(true);
        if (page == 1) {
            evaluates_list.clear();
        }
        if (bean == null) {
            return;
        }
        if (bean.getList() == null) {
            if (page > 1) {
                page--;
            }
            refreshLayout.setEnableLoadMore(false);
            return;
        }


        if (bean.getList().size() == 0) {
            if (page > 1) {
                page--;
                refreshLayout.setEnableLoadMore(false);
            }
            return;
        }

        for (int i = 0; i < bean.getList().size(); i++) {

            workImage(bean.getList().get(i));
        }
        evaluates_list.addAll(bean.getList());


        goodsCommentAdapter.notifyDataSetChanged();
    }

    private void workImage(Evaluate evaluate) {
        if(evaluate.getImg_urls()==null){
            return;

        }
        if(evaluate.getImg_urls().equals("")){
            return;

        }
        ArrayList<String> pics = new ArrayList<>();
        String[] images = evaluate.getImg_urls().split(",");
        for (int i = 0; i < images.length; i++) {
            pics.add(images[i]);

        }
        evaluate.setImg_list(pics);
    }
}
