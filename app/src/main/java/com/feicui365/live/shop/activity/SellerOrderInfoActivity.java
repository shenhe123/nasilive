package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.SellerOrderInfoAdapter;
import com.feicui365.live.shop.entity.OrderDetails;
import com.feicui365.live.shop.entity.UserOrderInfo;
import com.feicui365.live.widget.MyRecyclerView;
import com.feicui365.nasinet.userconfig.AppStatusManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SellerOrderInfoActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {


    @BindView(R.id.rv_seller_orderinfo)
    MyRecyclerView rv_seller_orderinfo;
    //商品信息
    @BindView(R.id.tv_order_code)
    TextView tv_order_code;

    @BindView(R.id.tv_creat_time)
    TextView tv_creat_time;


    @BindView(R.id.tv_pay_type)
    TextView tv_pay_type;


    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;

    @BindView(R.id.tv_submit)
    TextView tv_submit;

    @BindView(R.id.rl_address_info)
    RelativeLayout rl_address_info;


    //地址信息
    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_phone)
    TextView tv_phone;

    @BindView(R.id.tv_address)
    TextView tv_address;

    OrderDetails orderDetails;

    int orderid;
    String page_type = "0";


    SellerOrderInfoAdapter sellerOrderInfoAdapter;
    ArrayList<UserOrderInfo> list_orders = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seller_order_info;
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.getOrderInfo(String.valueOf(orderid));
    }


    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        orderid = getIntent().getIntExtra(Constants.ORDER_ID, 0);

        page_type = getIntent().getStringExtra(Constants.SELLER_ORDER_TYPE);

        initTitle();
        initList();

    }

    private void initTitle() {
        switch (page_type) {
            case "0":

                setTitle("待付款订单");
                tv_submit.setVisibility(View.GONE);
                break;
            case "1":

                setTitle("待发货订单");
                tv_submit.setText("发货");
                tv_submit.setVisibility(View.VISIBLE);
                break;
            case "2":

                setTitle("已发货订单");
                tv_submit.setVisibility(View.GONE);
                break;
            case "3":

                setTitle("已完成订单");
                tv_submit.setVisibility(View.GONE);
                break;
            case "4":

                setTitle("退款订单");
                tv_submit.setVisibility(View.GONE);
                break;
        }
    }

    private void initList() {
        sellerOrderInfoAdapter = new SellerOrderInfoAdapter(list_orders);
        rv_seller_orderinfo.setLayoutManager(new LinearLayoutManager(this));
        rv_seller_orderinfo.setAdapter(sellerOrderInfoAdapter);
    }


    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getOrderInfo(String.valueOf(orderid));
    }

    private void initPage() {
        list_orders.clear();
        orderDetails.getOrder().setGoods(orderDetails.getGoods());
        list_orders.add(orderDetails.getOrder());
        sellerOrderInfoAdapter.notifyDataSetChanged();

        tv_order_code.setText(orderDetails.getOrder().getOrder_no());
        tv_creat_time.setText(orderDetails.getOrder().getCreate_time());
        switch (orderDetails.getOrder().getPay_channel()) {
            case 1:
                tv_pay_type.setText("微信支付");
                break;
            case 2:
                tv_pay_type.setText("支付宝");

                break;
            default:
                tv_pay_type.setText("未支付");
                break;

        }
        if (orderDetails.getOrder().getPay_time() == null || "".equals(orderDetails.getOrder().getPay_time())) {
            tv_pay_time.setText("未支付");
        } else {
            tv_pay_time.setText(orderDetails.getOrder().getPay_time());
        }


        tv_name.setText(orderDetails.getOrder().getReceiver_name());
        tv_phone.setText(orderDetails.getOrder().getReceiver_mobile());
        tv_address.setText(orderDetails.getOrder().getReceiver_address());

        if(orderDetails.getOrder().getExpress_no()!=null){
            tv_submit.setVisibility(View.GONE);
        }

    }


    @Override
    public void getOrderInfo(OrderDetails bean) {
        if (bean == null) {
            return;
        }

        if (bean.getOrder() == null) {
            return;
        }

        if (bean.getGoods() == null) {
            return;
        }
        orderDetails = bean;

        initPage();


    }


    @OnClick({R.id.tv_submit,R.id.rl_chat})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:

                Intent intent = new Intent(this, SendGoodActivity.class);
                Bundle bundleObject = new Bundle();

                bundleObject.putSerializable(Constants.ORDER_GOODS, orderDetails.getGoods());
                bundleObject.putSerializable(Constants.ORDER_ID, String.valueOf(orderDetails.getOrder().getId()));
                intent.putExtras(bundleObject);
                startActivity(intent);
                break;
            case R.id.rl_chat:
                TxImUtils.getSocketManager().  startChat(orderDetails.getOrder().getUser().getId(),orderDetails.getOrder().getUser().getNick_name(),orderDetails.getOrder().getUser().getAvatar());

                break;
        }
    }


}
