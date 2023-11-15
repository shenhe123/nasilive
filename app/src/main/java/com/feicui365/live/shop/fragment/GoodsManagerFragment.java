package com.feicui365.live.shop.fragment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.GoodManagerAdapter;
import com.feicui365.live.shop.entity.GoodManager;
import com.feicui365.live.util.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class GoodsManagerFragment extends BaseMvpFragment<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv_orders)
    RecyclerView rv_orders;

    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;


    ArrayList<GoodManager> list_goods = new ArrayList<>();

    String type = "0";
    int page = 1;

    GoodManagerAdapter goodManagerAdapter;
    GoodManager choose_good;

    public GoodsManagerFragment() {
    }

    @Override
    protected void lazyLoad() {

    }


    public GoodsManagerFragment(String type) {
        this.type = type;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        initList();
        initData();
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                initData();
            }
        });
    }

    private void initList() {


        goodManagerAdapter = new GoodManagerAdapter(list_goods,type);
        rv_orders.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_orders.setAdapter(goodManagerAdapter);
        goodManagerAdapter.setGoodClickListener(new GoodManagerAdapter.GoodClickListener() {
            @Override
            public void onEdit(GoodManager item) {

            }

            @Override
            public void onUp(GoodManager item) {
                choose_good=item;
                mPresenter.setGoodsSaleStatus("1",String.valueOf(item.getId()));
            }

            @Override
            public void onDown(GoodManager item) {
                choose_good=item;
                mPresenter.setGoodsSaleStatus("2",String.valueOf(item.getId()));
            }
        });
    }

    @Override
    public void setGoodsSaleStatus(BaseResponse baseResponse) {

        for (int i=0;i<goodManagerAdapter.getData().size();i++){
            if(goodManagerAdapter.getData().get(i).getId()==choose_good.getId()){
                goodManagerAdapter.remove(i);
                ToastUtils.showT("操作成功");
                break;
            }
        }
    }

    private void initData() {

        mPresenter.getShopGoods(type, String.valueOf(page));


    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void getShopGoods(ArrayList<GoodManager> baseResponse) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();
        if (page == 1) {
            if (baseResponse == null) {
                rl_nothing.setVisibility(View.VISIBLE);
                return;
            }
            if (baseResponse.size() == 0) {
                rl_nothing.setVisibility(View.VISIBLE);
                return;
            }
            list_goods.clear();
            rl_nothing.setVisibility(View.GONE);
        }else{
            if (baseResponse == null) {
                return;
            }
            if (baseResponse.size() == 0) {
                return;
            }

        }



        list_goods.addAll(baseResponse);
        goodManagerAdapter.notifyDataSetChanged();
    }


    @Override
    public void onError(Throwable throwable) {

    }
}
