package com.feicui365.live.shop.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.activity.SellerOrderInfoActivity;
import com.feicui365.live.shop.activity.SellerRefundOrderInfoActivity;
import com.feicui365.live.shop.activity.SendGoodActivity;
import com.feicui365.live.shop.adapter.SellerOrderAdapter;
import com.feicui365.live.shop.adapter.SellerRefundOrderAdapter;
import com.feicui365.live.shop.entity.SellerOrderInfo;
import com.feicui365.live.shop.entity.RefundOrderGoods;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class SellerOrderFragment extends BaseMvpFragment<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv_orders)
    RecyclerView rv_orders;

    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;


    ArrayList<SellerOrderInfo> list_orders = new ArrayList<>();
    ArrayList<RefundOrderGoods> list_refund_orders = new ArrayList<>();
    String type = "0";
    int page = 1;

    SellerOrderAdapter orderAdapter;
    SellerRefundOrderAdapter refundOrderAdapter;

    public SellerOrderFragment() {
    }

    @Override
    protected void lazyLoad() {

    }


    public SellerOrderFragment(String type) {
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

        switch (type) {
            case "4":
                refundOrderAdapter = new SellerRefundOrderAdapter(list_refund_orders);
                rv_orders.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_orders.setAdapter(refundOrderAdapter);
                refundOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(getContext(), SellerRefundOrderInfoActivity.class);
                        intent.putExtra(Constants.SELLER_ORDER_TYPE, type);
                        intent.putExtra(Constants.ORDER_ID, list_refund_orders.get(position).getOrdergoodsid());
                        startActivity(intent);
                    }
                });
                break;
            default:
                orderAdapter = new SellerOrderAdapter(list_orders, type);
                rv_orders.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_orders.setAdapter(orderAdapter);
                orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(getContext(), SellerOrderInfoActivity.class);
                        intent.putExtra(Constants.ORDER_ID, list_orders.get(position).getId());

                        intent.putExtra(Constants.SELLER_ORDER_TYPE, type);


                        startActivity(intent);
                    }
                });

                orderAdapter.setControlListener(new SellerOrderAdapter.ControlListener() {
                    @Override
                    public void onChatUser(SellerOrderInfo item) {
                      //  MyUserInstance.getInstance().startChatActivity(getContext(), String.valueOf(item.getUid()));

                    }

                    @Override
                    public void onSendGood(SellerOrderInfo item) {
                        Intent intent = new Intent(getActivity(), SendGoodActivity.class);
                        Bundle bundleObject = new Bundle();

                        bundleObject.putSerializable(Constants.ORDER_GOODS, item.getGoods());
                        bundleObject.putSerializable(Constants.ORDER_ID, String.valueOf(item.getId()));
                        intent.putExtras(bundleObject);
                        startActivity(intent);
                    }
                });

                break;

        }


    }

    private void initData() {
        if (type.equals("4")) {
            mPresenter.getSellerRefundOrderList(type, String.valueOf(page));
        } else {
            mPresenter.shopOrderList(type, String.valueOf(page));
        }

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
    public void shopOrderList(ArrayList<SellerOrderInfo> baseResponse) {
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
            list_orders.clear();
            rl_nothing.setVisibility(View.GONE);
        } else {
            if (baseResponse == null) {
                return;
            }
            if (baseResponse.size() == 0) {
                return;
            }

        }


        list_orders.addAll(baseResponse);
        orderAdapter.notifyDataSetChanged();
    }


    @Override
    public void getRefundOrderList(ArrayList<RefundOrderGoods> baseResponse) {
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
            list_refund_orders.clear();
            rl_nothing.setVisibility(View.GONE);
        } else {
            if (baseResponse == null) {
                return;
            }
            if (baseResponse.size() == 0) {
                return;
            }

        }


        list_refund_orders.addAll(baseResponse);
        refundOrderAdapter.notifyDataSetChanged();
    }


    @Override
    public void onError(Throwable throwable) {

    }
}
