package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.dialog.MessageDialog;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.UserOrderInfoAdapter;
import com.feicui365.live.shop.custom.PayDialog;
import com.feicui365.live.shop.entity.LogisticsInfo;
import com.feicui365.live.shop.entity.OrderDetails;
import com.feicui365.live.shop.entity.UserOrderInfo;
import com.feicui365.live.shop.entity.OrderGoods;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.MyRecyclerView;
import com.feicui365.nasinet.userconfig.AppStatusManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class UserOrderInfoActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.nsv_root)
    NestedScrollView nsv_root;

    @BindView(R.id.rl_finish)
    RelativeLayout rl_finish;
    @BindView(R.id.rv_orders)
    MyRecyclerView rv_orders;

    @BindView(R.id.tv_name)
    TextView tv_name;


    @BindView(R.id.tv_phone)
    TextView tv_phone;


    @BindView(R.id.tv_page_status)
    TextView tv_page_status;
    @BindView(R.id.iv_page_status)
    ImageView iv_page_status;


    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.tv_order_remark)
    TextView tv_order_remark;
    @BindView(R.id.tv_order_code)
    TextView tv_order_code;
    @BindView(R.id.tv_order_creat_time)
    TextView tv_order_creat_time;
    @BindView(R.id.tv_order_pay_type)
    TextView tv_order_pay_type;
    @BindView(R.id.tv_order_pay_time)
    TextView tv_order_pay_time;
    @BindView(R.id.tv_logistics)
    TextView tv_logistics;
    @BindView(R.id.rl_logistics)
    RelativeLayout rl_logistics;


    @BindView(R.id.rl_bottom)
    RelativeLayout rl_bottom;
    @BindView(R.id.tv_button_1)
    TextView tv_button_1;
    @BindView(R.id.tv_button_2)
    TextView tv_button_2;
    @BindView(R.id.tv_button_3)
    TextView tv_button_3;

    @BindView(R.id.ll_remark)
    LinearLayout ll_remark;


    OrderDetails orderDetails;
    LogisticsInfo logisticsInfo;
    AlphaAnimation alphaAni;
    boolean animation_play = false;
    PayDialog payDialog;
    PayDialog.Builder builder;
    int orderid;
    UserOrderInfoAdapter orderAdapter;
    ArrayList<UserOrderInfo> list_orders = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_order_info;
    }

    @Override
    protected void initData() {
        // showLoading();

      /*  mPresenter.getOrderInfo(String.valueOf(orderid));
        mPresenter.getExpressInfo(String.valueOf(orderid));*/
    }


    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        hideTitle(true);
        orderid = getIntent().getIntExtra(Constants.ORDER_ID, 0);

        initAlphaIn();
        initAppBar();
        initList();
    }

    private void initList() {
        orderAdapter = new UserOrderInfoAdapter(list_orders);
        rv_orders.setLayoutManager(new LinearLayoutManager(this));
        rv_orders.setAdapter(orderAdapter);

        orderAdapter.setOnOrderWordListener(new UserOrderInfoAdapter.OnOrderWordListener() {
            @Override
            public void refund(OrderGoods item) {
                initMessageDialog("申请退款", "该货物是否要申请退款?", new MessageDialog.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(context, UserRefundActivity.class);
                        Bundle bundleObject = new Bundle();
                        bundleObject.putSerializable(Constants.ORDER_GOODS, item);
                        intent.putExtras(bundleObject);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void comment(OrderGoods item) {
                Intent intent = new Intent(context, PublishGoodsCommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.ORDER_GOODS, item);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void initAppBar() {

        nsv_root.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int i, int i1, int i2, int i3) {
                if (i1 > 200) {
                    if (rl_finish.getVisibility() == View.GONE) {
                        rl_finish.setVisibility(View.VISIBLE);
                        playAlpha(rl_finish);
                    }

                } else {
                    if (rl_finish.getVisibility() == View.VISIBLE) {
                        rl_finish.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getOrderInfo(String.valueOf(orderid));
        mPresenter.getExpressInfo(String.valueOf(orderid));
    }

    @Override
    public void onError(Throwable throwable) {

    }


    public void initAlphaIn() {


        alphaAni = new AlphaAnimation(0.2f, 1);
        alphaAni.setDuration(300);
        alphaAni.setRepeatCount(0);
        alphaAni.setRepeatMode(Animation.REVERSE);
        alphaAni.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                animation_play = true;

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation_play = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    public void playAlpha(View v) {
        if (animation_play) {
            return;
        }
        v.startAnimation(alphaAni);
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
        initBottom();

    }


    private void initPage() {
        //列表相关
        list_orders.clear();
        orderDetails.getOrder().setGoods(orderDetails.getGoods());
        list_orders.add(orderDetails.getOrder());
        orderAdapter.notifyDataSetChanged();


        tv_name.setText(orderDetails.getOrder().getReceiver_name());
        tv_phone.setText(orderDetails.getOrder().getReceiver_mobile());
        tv_address.setText(orderDetails.getOrder().getReceiver_address());

        if (orderDetails.getOrder().getRemark() == null || "".equals(orderDetails.getOrder().getRemark())) {
            ll_remark.setVisibility(View.GONE);

        } else {
            ll_remark.setVisibility(View.VISIBLE);
            tv_order_remark.setText(orderDetails.getOrder().getRemark());
        }

        tv_order_code.setText(orderDetails.getOrder().getOrder_no());
        tv_order_creat_time.setText(orderDetails.getOrder().getCreate_time());
        switch (orderDetails.getOrder().getPay_channel()) {
            case 1:
                tv_order_pay_type.setText("微信支付");
                break;
            case 2:
                tv_order_pay_type.setText("支付宝");
                break;
            default:
                tv_order_pay_type.setText("未支付");
                break;

        }
        if (orderDetails.getOrder().getPay_time() == null || "".equals(orderDetails.getOrder().getPay_time())) {
            tv_order_pay_time.setText("未支付");
        } else {
            tv_order_pay_time.setText(orderDetails.getOrder().getPay_time());
        }


        //做个检测,如果全部都退款了,不显示下面的操作栏
        if (orderDetails.getOrder().getStatus() != 0) {


            boolean check = true;
            for (OrderGoods ordergoods : orderDetails.getGoods()) {
                if (ordergoods.getReturn_status() == 1) {
                    check = false;
                    break;
                }
            }
            if (check) {
                rl_bottom.setVisibility(View.GONE);
            } else {
                rl_bottom.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initBottom() {
        /***
         * status: 0-待支付 1-已支付 2-订单超时(关闭) 3-已确认收货
         *
         * delivery_status : 0-待发货 1-已发货 2-已签收
         *
         *
         *
         * 退款订单 status 1-发起退货 2-卖家同意退货 3-卖家拒绝退货 4-退货完成 5-买家取消退货
         * */

        switch (orderDetails.getOrder().getStatus()) {

            case 0:
                tv_page_status.setText("待付款");
                iv_page_status.setImageResource(R.mipmap.ic_order_unpay);
                tv_button_2.setText("取消订单");
                tv_button_3.setText("立即付款");
                tv_button_2.setVisibility(View.VISIBLE);
                tv_button_3.setVisibility(View.VISIBLE);
                rl_bottom.setVisibility(View.VISIBLE);

                break;
            case 1:

                switch (orderDetails.getOrder().getDelivery_status()) {

                    case 0:
                        tv_page_status.setText("待发货");
                        iv_page_status.setImageResource(R.mipmap.ic_order_unsend);
                        rl_bottom.setVisibility(View.GONE);
                        break;

                    case 1:
                        tv_page_status.setText("已发货");
                        iv_page_status.setImageResource(R.mipmap.ic_order_send);
                        rl_bottom.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        tv_page_status.setText("已签收");
                        iv_page_status.setImageResource(R.mipmap.ic_order_send);
                        rl_bottom.setVisibility(View.VISIBLE);
                        break;
                }


                tv_button_2.setText("查看物流");
                tv_button_3.setText("确认收货");
                tv_button_2.setVisibility(View.VISIBLE);
                tv_button_3.setVisibility(View.VISIBLE);
                break;
            case 2:
                tv_page_status.setText("已关闭");
                iv_page_status.setImageResource(R.mipmap.ic_finish);
                rl_bottom.setVisibility(View.GONE);
                break;
            case 3:
                tv_page_status.setText("已完成");
                iv_page_status.setImageResource(R.mipmap.ic_finish);
                tv_button_2.setText("查看物流");
                tv_button_3.setText("再次购买");
                tv_button_2.setVisibility(View.VISIBLE);
                tv_button_3.setVisibility(View.VISIBLE);
                rl_bottom.setVisibility(View.VISIBLE);

                break;

        }
    }

    @Override
    public void getExpressInfo(LogisticsInfo baseResponse) {
        if (baseResponse == null) {
            tv_logistics.setText("暂无物流信息");
        } else {
            if (baseResponse.getData() == null) {
                return;
            }
            if (baseResponse.getData().size() == 0) {
                return;
            }

            logisticsInfo = baseResponse;
            tv_logistics.setText(baseResponse.getData().get(0).getStatus());
        }

    }


    @OnClick({R.id.rl_logistics, R.id.tv_button_2, R.id.tv_button_3, R.id.rl_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_logistics:
                if (orderDetails.getOrder().getExpress_no() == null) {
                    ToastUtils.showT("当前暂无物流信息");
                    return;
                }


                Intent intent = new Intent(this, DeliveryActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(Constants.EXPRESS_NO, orderDetails.getOrder().getExpress_no());
                bundle.putSerializable(Constants.EXPRESS_COMPANY, orderDetails.getOrder().getExpress_company());
                bundle.putSerializable(Constants.ORDER_ID, orderid);

                String[] images = list_orders.get(0).getGoods().get(0).getGoods().getThumb_urls().split(",");
                bundle.putString(Constants.PIC_URL, images[0]);

                intent.putExtras(bundle);
                startActivity(intent);

                break;
            case R.id.tv_button_2:
                click(tv_button_2.getText().toString());
                break;
            case R.id.tv_button_3:
                click(tv_button_3.getText().toString());
                break;
            case R.id.rl_service:


                TxImUtils.getSocketManager().startChat(orderDetails.getOrder().getShop().getId(), orderDetails.getOrder().getShop().getNick_name(),orderDetails.getOrder().getShop().getAvatar());
                break;
        }
    }


    private void click(String text) {
        switch (text) {
            case "取消订单":

                mPresenter.cancelOrder(String.valueOf(orderDetails.getOrder().getId()));
                break;

            case "立即付款":
                payOrder(orderDetails.getOrder());
                break;
            case "查看物流":

                if (orderDetails.getOrder().getExpress_no() == null) {
                    ToastUtils.showT("当前暂无物流信息");
                    return;
                }


                Intent intent = new Intent(this, DeliveryActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable(Constants.EXPRESS_NO, orderDetails.getOrder().getExpress_no());
                bundle.putSerializable(Constants.EXPRESS_COMPANY, orderDetails.getOrder().getExpress_company());
                bundle.putSerializable(Constants.ORDER_ID, orderid);

                String[] images = list_orders.get(0).getGoods().get(0).getGoods().getThumb_urls().split(",");
                bundle.putString(Constants.PIC_URL, images[0]);

                intent.putExtras(bundle);
                startActivity(intent);

                break;

            case "确认收货":
                initMessageDialog("确认收货", "是否确定已经收货并立刻付款?", new MessageDialog.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        showLoading();
                        mPresenter.confirmReceipt(String.valueOf(orderDetails.getOrder().getId()));
                    }
                });

                break;

            case "再次购买":
                ToastUtils.showT("已添加至购物车");
                for (int i = 0; i < orderDetails.getGoods().size(); i++) {
                    mPresenter.addCartGoods(String.valueOf(orderDetails.getGoods().get(i).getInventoryid())
                            , orderDetails.getGoods().get(i).getGoods().getShopid(), String.valueOf(1));

                }

                break;
        }
    }


    private void payOrder(UserOrderInfo item) {
        if (builder == null) {
            builder = new PayDialog.Builder(this);
            builder.setOnFinishListener(new PayDialog.OnFinishListener() {
                @Override
                public void Success() {
                    ToastUtils.showT("支付成功");
                    mPresenter.getOrderInfo(String.valueOf(orderid));

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


    @Override
    public void cancelOrder(BaseResponse baseResponse) {
        mPresenter.getOrderInfo(String.valueOf(orderid));
    }

    @Override
    public void confirmReceipt(BaseResponse baseResponse) {
        ToastUtils.showT("确认收货");
        mPresenter.getOrderInfo(String.valueOf(orderid));
    }
}
