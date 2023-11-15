package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.dialog.MessageDialog;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.entity.Address;
import com.feicui365.live.shop.entity.RefundInfo;
import com.feicui365.nasinet.userconfig.AppStatusManager;

import butterknife.BindView;
import butterknife.OnClick;

public class SellerRefundOrderInfoActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.rl_address_info)
    RelativeLayout rl_address_info;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;


    @BindView(R.id.tv_refund_remark)
    TextView tv_refund_remark;
    @BindView(R.id.tv_refund_price)
    TextView tv_refund_price;
    @BindView(R.id.tv_refund_creat_time)
    TextView tv_refund_creat_time;
    @BindView(R.id.tv_refund_num)
    TextView tv_refund_num;

    @BindView(R.id.tv_submit)
    TextView tv_submit;


    @BindView(R.id.rl_address)
    RelativeLayout rl_address;


    //商品信息
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;

    @BindView(R.id.tv_status)
    TextView tv_status;


    @BindView(R.id.iv_pic)
    ImageView iv_pic;


    @BindView(R.id.tv_price)
    TextView tv_price;


    @BindView(R.id.tv_count)
    TextView tv_count;


    @BindView(R.id.tv_good_title)
    TextView tv_good_title;


    @BindView(R.id.tv_color)
    TextView tv_color;


    @BindView(R.id.rl_bottom_button)
    RelativeLayout rl_bottom_button;

    @BindView(R.id.ll_logistics)
    LinearLayout ll_logistics;

    @BindView(R.id.tv_logistics_num)
    TextView tv_logistics_num;


    @BindView(R.id.tv_button_1)
    TextView tv_button_1;
    @BindView(R.id.tv_button_2)
    TextView tv_button_2;


    //商品信息

    Address chose_address;
    int orderid;
    int refund_type = 0;
    RefundInfo refundInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seller_refund_order_info;
    }

    @Override
    protected void initData() {
        showLoading();
        mPresenter.refundOrderInfo(String.valueOf(orderid));

    }


    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("退款订单");
        orderid = getIntent().getIntExtra(Constants.ORDER_ID, 0);


    }


    @Override
    public void onError(Throwable throwable) {

    }


    @Override
    public void refundOrderInfo(RefundInfo baseResponse) {
        if (baseResponse == null) {
            return;
        }
        refundInfo = baseResponse;
        initPage();

    }

    private void initPage() {
        String[] images_pic = refundInfo.getOrdergoods().getGoods().getThumb_urls().split(",");
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.zhanwei))
                .load(images_pic[0]).into(iv_pic);
        tv_order_num.setText("订单编号:" + refundInfo.getOrdergoods().getSuborder().getOrder_no());

        tv_price.setText(refundInfo.getOrdergoods().getPrice());
        tv_count.setText(String.valueOf(refundInfo.getOrdergoods().getCount()));
        tv_good_title.setText(refundInfo.getOrdergoods().getGoods().getTitle());
        tv_color.setText(refundInfo.getOrdergoods().getColor() + refundInfo.getOrdergoods().getSize());


        tv_refund_remark.setText(refundInfo.getReturn_info().getReason());
        tv_refund_price.setText("￥ " + refundInfo.getReturn_info().getAmount());
        tv_refund_creat_time.setText(refundInfo.getReturn_info().getCreate_time());
        tv_refund_num.setText(String.valueOf(refundInfo.getOrdergoods().getSuborderid()));

        tv_name.setText(refundInfo.getOrdergoods().getSuborder().getReceiver_name());
        tv_phone.setText(refundInfo.getOrdergoods().getSuborder().getReceiver_mobile());
        tv_address.setText(refundInfo.getOrdergoods().getSuborder().getReceiver_address());

        Log.e("initPage1", refundInfo.getReturn_info().getStatus());
        initStatus();
    }

    private void initStatus() {
        //status 1-发起退货 2-卖家同意退货 3-卖家拒绝退货 4-退货完成 5-买家取消退货
        switch (refundInfo.getReturn_info().getStatus()) {

            case "1":
                tv_status.setText("申请退款");
                rl_bottom_button.setVisibility(View.VISIBLE);
                tv_button_1.setVisibility(View.VISIBLE);
                tv_button_2.setVisibility(View.VISIBLE);

                tv_button_2.setText("接受退款");
                tv_button_1.setText("拒绝退款");
                tv_submit.setVisibility(View.GONE);
                break;
            case "2":
                tv_status.setText("退款中");
                if (refundInfo.getReturn_info().getExpress_no() != null) {
                    ll_logistics.setVisibility(View.VISIBLE);
                    tv_logistics_num.setText(refundInfo.getReturn_info().getExpress_no());

                } else {
                    ll_logistics.setVisibility(View.GONE);
                //   tv_submit.setVisibility(View.GONE);
                }
                tv_submit.setVisibility(View.VISIBLE);
                break;
            case "3":
                tv_status.setText("已拒绝");
                tv_submit.setVisibility(View.GONE);
                break;
            case "4":
                tv_status.setText("已完成");
                tv_submit.setVisibility(View.GONE);
                break;
            case "5":
                tv_status.setText("买家取消退款");
                tv_submit.setVisibility(View.GONE);
                break;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.GET_ADDRESS) {
                Address address = (Address) data.getSerializableExtra("data");
                if (address != null) {
                    chose_address = address;
                    rl_address.setVisibility(View.VISIBLE);
                    tv_name.setText(address.getName());
                    tv_phone.setText(address.getMobile());
                    tv_address.setText(address.getProvince() + address.getCity() + address.getDistrict() + address.getAddress());
                }
            }

            if (requestCode == Constants.GET_REFUND) {
                Log.e("退款完成", "退款完成");

                refund_type = 1;
                showLoading();
                mPresenter.refundOrderInfo(String.valueOf(orderid));
            }
        }

    }

    @OnClick({R.id.rl_address_info, R.id.tv_submit, R.id.tv_button_2, R.id.tv_button_1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_address_info:
                Intent intent = new Intent(this, MyAddressActivity.class);
                startActivityForResult(intent, Constants.GET_ADDRESS);
                break;

            case R.id.tv_submit:

                //以收到退款货物
                refund_type = 2;

                initMessageDialog("确认退款", "是否确定已经收货并立刻退款?", new MessageDialog.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        showLoading();
                        mPresenter.shopReceiveReturn(String.valueOf(refundInfo.getReturn_info().getId()));
                    }
                });

                break;
            case R.id.tv_button_1:
                //拒绝退款
                refund_type = 3;
                mPresenter.operateReturn("2", "", "", String.valueOf(refundInfo.getReturn_info().getId()));
                break;

            case R.id.tv_button_2:

                Intent intent_refund = new Intent(this, SellerRefundActivity.class);
                Bundle bundleObject = new Bundle();
                bundleObject.putSerializable(Constants.REFUND_INFO, refundInfo);
                intent_refund.putExtras(bundleObject);
                startActivityForResult(intent_refund,Constants.GET_REFUND);


                break;
        }
    }

    @Override
    public void shopReceiveReturn(BaseResponse baseResponse) {
        showLoading();
        mPresenter.refundOrderInfo(String.valueOf(orderid));
    }

    @Override
    public void operateReturn(BaseResponse baseResponse) {
        Log.e("退款完成", "退款完成");
        showLoading();
        mPresenter.refundOrderInfo(String.valueOf(orderid));
    }
}
