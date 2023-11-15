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
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.activity.DeliveryActivity;
import com.feicui365.live.shop.activity.PublishGoodsCommentActivity;
import com.feicui365.live.shop.activity.UserRefundOrderInfoActivity;
import com.feicui365.live.shop.activity.UserOrderInfoActivity;
import com.feicui365.live.shop.adapter.UserOrderAdapter;
import com.feicui365.live.shop.adapter.UserRefundOrderAdapter;
import com.feicui365.live.shop.custom.PayDialog;
import com.feicui365.live.shop.entity.UserOrderInfo;
import com.feicui365.live.shop.entity.RefundOrderGoods;
import com.feicui365.live.util.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class UserOrderFragment extends BaseMvpFragment<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.rv_orders)
    RecyclerView rv_orders;

    @BindView(R.id.rl_nothing)
    RelativeLayout rl_nothing;


    ArrayList<UserOrderInfo> list_orders = new ArrayList<>();
    ArrayList<RefundOrderGoods> list_refund_orders = new ArrayList<>();
    String type = "0";
    int page = 1;

    UserOrderAdapter orderAdapter;
    UserRefundOrderAdapter refundOrderAdapter;
    PayDialog payDialog;
    PayDialog.Builder builder;

    UserOrderInfo chose_cancel,chose_confirm;

    public UserOrderFragment() {
    }

    @Override
    protected void lazyLoad() {

    }


    public UserOrderFragment(String type) {
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
                refundOrderAdapter = new UserRefundOrderAdapter(list_refund_orders);
                rv_orders.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_orders.setAdapter(refundOrderAdapter);
                refundOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(getContext(), UserRefundOrderInfoActivity.class);
                        intent.putExtra(Constants.ORDER_ID, list_refund_orders.get(position).getOrdergoodsid());
                        startActivity(intent);
                    }
                });
                break;
            default:
                orderAdapter = new UserOrderAdapter(list_orders, 1);
                rv_orders.setLayoutManager(new LinearLayoutManager(getContext()));
                rv_orders.setAdapter(orderAdapter);
                orderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        Intent intent = new Intent(getContext(), UserOrderInfoActivity.class);
                        intent.putExtra(Constants.ORDER_ID, list_orders.get(position).getId());
                        startActivity(intent);
                    }
                });

                orderAdapter.setControlListener(new UserOrderAdapter.ControlListener() {
                    //支付
                    @Override
                    public void onPayListener(UserOrderInfo item) {
                        payOrder(item);
                    }


                    //取消
                    @Override
                    public void onCancelOrderListener(UserOrderInfo item) {
                        chose_cancel = item;
                        mPresenter.cancelOrder(String.valueOf(item.getId()));
                    }

                    //物流信息
                    @Override
                    public void onDeliveryinfoListener(UserOrderInfo item) {
                /*        Intent intent = new Intent(getContext(), UserOrderInfoActivity.class);
                        intent.putExtra(Constants.ORDER_ID, item.getId());
                        startActivity(intent);
*/

                        if (item.getExpress_no() == null) {
                            ToastUtils.showT("当前暂无物流信息");
                            return;
                        }


                        Intent intent = new Intent(getActivity(), DeliveryActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putSerializable(Constants.EXPRESS_NO, item.getExpress_no());
                        bundle.putSerializable(Constants.EXPRESS_COMPANY, item.getExpress_company());
                        bundle.putSerializable(Constants.ORDER_ID, item.getId());

                        String[] images = item.getGoods().get(0).getGoods().getThumb_urls().split(",");
                        bundle.putString(Constants.PIC_URL, images[0]);

                        intent.putExtras(bundle);
                        startActivity(intent);


                    }

                    //确认收货
                    @Override
                    public void onTakeDeliveryListener(UserOrderInfo item) {

                        chose_confirm = item;
                        mPresenter.confirmReceipt(String.valueOf(item.getId()));


                    }

                    //评论
                    @Override
                    public void onComment(UserOrderInfo item) {
                        Intent intent = new Intent(getContext(), PublishGoodsCommentActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable(Constants.ORDER_GOODS, item.getGoods().get(0));
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                break;
        }
    }

    @Override
    public void cancelOrder(BaseResponse baseResponse) {
        ToastUtils.showT("取消订单");
        if (chose_cancel != null) {
            for (int i = 0; i < orderAdapter.getData().size(); i++) {
                if (chose_cancel.getId() == orderAdapter.getData().get(i).getId()) {
                    orderAdapter.remove(i);
                    chose_cancel = null;
                    break;
                }
            }
            if (orderAdapter.getData().size() == 0) {
                rl_nothing.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void confirmReceipt(BaseResponse baseResponse) {
        ToastUtils.showT("确认收货");
        if (chose_confirm != null) {
            for (int i = 0; i < orderAdapter.getData().size(); i++) {
                if (chose_confirm.getId() == orderAdapter.getData().get(i).getId()) {
                    orderAdapter.getData().get(i).setDelivery_status(2);
                    orderAdapter.notifyDataSetChanged();
                    chose_confirm=null;
                    break;
                }
            }
        }
    }

    private void initData() {
        if (type.equals("4")) {
            mPresenter.getRefundOrderList(type, String.valueOf(page));
        } else {
            mPresenter.getOrderList(type, String.valueOf(page));
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
    public void getOrderList(ArrayList<UserOrderInfo> baseResponse) {
        refreshLayout.finishLoadMore();
        refreshLayout.finishRefresh();

        if (page == 1) {
            list_orders.clear();
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

    private void payOrder(UserOrderInfo item) {
        if (builder == null) {
            builder = new PayDialog.Builder(getActivity());
            builder.setOnFinishListener(new PayDialog.OnFinishListener() {
                @Override
                public void Success() {
                    ToastUtils.showT("支付成功");
                    for (int i = 0; i < orderAdapter.getData().size(); i++) {
                        if (item.getId() == orderAdapter.getData().get(i).getId()) {
                            orderAdapter.remove(i);
                            break;
                        }
                    }
                    if (orderAdapter.getData().size() == 0) {
                        rl_nothing.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void Fail() {
                    ToastUtils.showT("支付失败");
                }
            });
        }
        builder.setPrice(item.getTotal_price());
        builder.setOrder_num(item.getOrder_no());

        if (payDialog == null) {
            payDialog = builder.create();
            payDialog.show();
        }

        if (!payDialog.isShowing()) {
            payDialog.show();
        }


    }
}
