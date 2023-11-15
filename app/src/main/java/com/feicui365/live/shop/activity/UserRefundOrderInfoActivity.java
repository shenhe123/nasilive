package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.UserRefundOrderInfoAdapter;
import com.feicui365.live.shop.entity.OrderGoods;
import com.feicui365.live.shop.entity.RefundInfo;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.MyRecyclerView;
import com.feicui365.nasinet.userconfig.AppStatusManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class UserRefundOrderInfoActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.nsv_root)
    NestedScrollView nsv_root;


    @BindView(R.id.rl_finish)
    RelativeLayout rl_finish;

    @BindView(R.id.rl_code)
    RelativeLayout rl_code;


    @BindView(R.id.et_code)
    EditText et_code;

    @BindView(R.id.tv_page_status)
    TextView tv_page_status;

    @BindView(R.id.iv_page_status)
    ImageView iv_page_status;

    @BindView(R.id.rl_address_info)
    RelativeLayout rl_address_info;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;

    @BindView(R.id.iv_step_4)
    ImageView iv_step_4;
    @BindView(R.id.iv_step_1)
    ImageView iv_step_1;
    @BindView(R.id.iv_step_2)
    ImageView iv_step_2;
    @BindView(R.id.iv_step_3)
    ImageView iv_step_3;

    @BindView(R.id.tv_all_price)
    TextView tv_all_price;


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

    @BindView(R.id.rv_orders)
    MyRecyclerView rv_orders;

    @BindView(R.id.rl_service)
    RelativeLayout rl_service;


    AlphaAnimation alphaAni;
    boolean animation_play = false;
    int orderid;
    String code;
    RefundInfo refundInfo;
    ArrayList<OrderGoods> orderGoods = new ArrayList<>();
    UserRefundOrderInfoAdapter userRefundOrderInfoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_order_info;
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
        hideTitle(true);
        orderid = getIntent().getIntExtra(Constants.ORDER_ID, 0);

        initAlphaIn();
        initAppBar();
        initList();
    }

    private void initList() {

        userRefundOrderInfoAdapter = new UserRefundOrderInfoAdapter(orderGoods);
        rv_orders.setAdapter(userRefundOrderInfoAdapter);
        rv_orders.setLayoutManager(new LinearLayoutManager(this));
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


    @OnClick({R.id.rl_code, R.id.tv_submit,R.id.rl_service})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_code:
                Intent intent = new Intent(this, ScanActivity.class);
                startActivityForResult(intent, Constants.GET_ZXING);
                break;

            case R.id.tv_submit:

                if (et_code.getText().toString().equals("")) {
                    ToastUtils.showT("请输入物流单号");
                    return;
                }
                showLoading();
                mPresenter.submitReturnExpress(String.valueOf(refundInfo.getReturn_info().getId()), et_code.getText().toString());
                break;
            case R.id.rl_service:

                TxImUtils.getSocketManager().startChat(refundInfo.getOrdergoods().getShop().getId(), refundInfo.getOrdergoods().getShop().getNick_name(),refundInfo.getOrdergoods().getShop().getAvatar());
                break;
        }
    }

    @Override
    public void submitReturnExpress(BaseResponse baseResponse) {
        ToastUtils.showT("提交成功");

        mPresenter.refundOrderInfo(String.valueOf(orderid));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & requestCode == Constants.GET_ZXING) {
            code = data.getStringExtra("code");
            et_code.setText(code);

        }
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

        switch (refundInfo.getReturn_info().getStatus()) {
            case "1":
                iv_page_status.setImageResource(R.mipmap.ic_order_examined);
                tv_page_status.setText("待审核");
                rl_address_info.setVisibility(View.GONE);
                tv_submit.setVisibility(View.GONE);
                break;
            case "2":
                iv_page_status.setImageResource(R.mipmap.ic_order_examined);
                tv_page_status.setText("退款中");
                rl_address_info.setVisibility(View.VISIBLE);
                initAddress();


                if (refundInfo.getReturn_info().getExpress_no() == null) {
                    rl_code.setVisibility(View.VISIBLE);
                    tv_submit.setVisibility(View.VISIBLE);
                } else {
                    rl_code.setVisibility(View.GONE);
                    tv_submit.setVisibility(View.GONE);

                }


                break;
            case "3":
                iv_page_status.setImageResource(R.mipmap.ic_order_examined);
                tv_page_status.setText("拒绝退款");
                rl_address_info.setVisibility(View.VISIBLE);
                tv_submit.setVisibility(View.GONE);
                initAddress();
                break;
            case "4":
                iv_page_status.setImageResource(R.mipmap.ic_refund_fail);
                tv_page_status.setText("已退款");
                rl_address_info.setVisibility(View.VISIBLE);
                tv_submit.setVisibility(View.GONE);
                initAddress();
                break;


        }
        initStatus();
        tv_all_price.setText("￥ " + refundInfo.getReturn_info().getAmount());
        tv_refund_remark.setText(refundInfo.getReturn_info().getReason());
        tv_refund_price.setText("￥ " + refundInfo.getReturn_info().getAmount());

        tv_refund_creat_time.setText(refundInfo.getReturn_info().getCreate_time());

        tv_refund_num.setText(String.valueOf(refundInfo.getOrdergoods().getSuborderid()));


        orderGoods.clear();

        orderGoods.add(refundInfo.getOrdergoods());
        userRefundOrderInfoAdapter.notifyDataSetChanged();

    }

    private void initAddress() {
        tv_name.setText(refundInfo.getOrdergoods().getSuborder().getReceiver_name());
        tv_phone.setText(refundInfo.getOrdergoods().getSuborder().getReceiver_mobile());
        tv_address.setText(refundInfo.getOrdergoods().getSuborder().getReceiver_address());
    }

    private void initStatus() {
        switch (refundInfo.getReturn_info().getStatus()) {
            case "1":
                iv_step_1.setImageResource(R.mipmap.ic_refund_now);
                iv_step_2.setImageResource(R.mipmap.ic_refund_computle);
                iv_step_3.setImageResource(R.mipmap.ic_refund_computle);
                iv_step_4.setImageResource(R.mipmap.ic_refund_computle);
                break;
            case "2":
                iv_step_2.setImageResource(R.mipmap.ic_refund_now);
                iv_step_3.setImageResource(R.mipmap.ic_refund_now);
                iv_step_4.setImageResource(R.mipmap.ic_refund_computle);
                break;
            case "3":
                iv_step_2.setImageResource(R.mipmap.ic_refund_now);
                iv_step_3.setImageResource(R.mipmap.ic_refund_now);
                iv_step_4.setImageResource(R.mipmap.ic_refund_computle);
                break;
            case "4":
                iv_step_2.setImageResource(R.mipmap.ic_refund_now);
                iv_step_3.setImageResource(R.mipmap.ic_refund_now);
                iv_step_4.setImageResource(R.mipmap.ic_refund_now);
                break;


        }

    }
}
