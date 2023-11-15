package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kyleduo.switchbutton.SwitchButton;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.dialog.WheelDialog;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.entity.Address;
import com.feicui365.live.shop.entity.RefundInfo;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class SellerRefundActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {


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

    @BindView(R.id.tv_refund_price)
    TextView tv_refund_price;

    @BindView(R.id.rl_reason)
    RelativeLayout rl_reason;

    @BindView(R.id.tv_reason)
    TextView tv_reason;

    @BindView(R.id.tv_time)
    TextView tv_time;


    @BindView(R.id.sb_refund)
    SwitchButton sb_refund;

    @BindView(R.id.rl_address_info)
    RelativeLayout rl_address_info;

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @Nullable
    @BindView(R.id.tv_chose_address)
    TextView tv_chose_address;

    @BindView(R.id.rl_address)
    RelativeLayout rl_address;

    Address chose_address;
    RefundInfo refundInfo;
    WheelDialog wheelDialog_reason;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seller_refund_2;
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {
        setTitle("申请退款");
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);

        Bundle bundleObject = getIntent().getExtras();
        if (bundleObject == null) {
            finish();
            return;
        }
        if (bundleObject.getSerializable(Constants.REFUND_INFO) == null) {
            finish();
            return;
        }

        refundInfo = (RefundInfo) bundleObject.getSerializable(Constants.REFUND_INFO);

        initPage();
    }

    private void initPage() {
        String[] pic = refundInfo.getOrdergoods().getGoods().getThumb_urls().split(",");
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(pic[0]).into(iv_pic);

        tv_price.setText("￥ " + refundInfo.getOrdergoods().getPrice());
        tv_count.setText("x" + refundInfo.getOrdergoods().getCount());
        tv_good_title.setText(refundInfo.getOrdergoods().getGoods().getTitle());
        if (refundInfo.getOrdergoods().getSize() != null) {
            tv_color.setText(refundInfo.getOrdergoods().getColor() + refundInfo.getOrdergoods().getSize());
        } else {
            tv_color.setText(refundInfo.getOrdergoods().getColor());
        }

        tv_refund_price.setText("￥ " + (Double.parseDouble(refundInfo.getOrdergoods().getPrice()) * refundInfo.getOrdergoods().getCount()));
        tv_time.setText(refundInfo.getReturn_info().getCreate_time());
        tv_reason.setText(refundInfo.getReturn_info().getReason());
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @OnClick({R.id.rl_reason, R.id.tv_submit, R.id.rl_swtich, R.id.rl_address_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_reason:
                showReason();

                break;

            case R.id.tv_submit:


                if (sb_refund.isChecked()) {
                    if (chose_address == null) {
                        ToastUtils.showT("请选择地址");
                        return;
                    }
                    showLoading();
                    mPresenter.operateReturn("1", chose_address.getId(), "1", String.valueOf(refundInfo.getReturn_info().getId()));
                } else {
                    showLoading();
                    mPresenter.operateReturn("1", "", "2", String.valueOf(refundInfo.getReturn_info().getId()));
                }


                break;

            case R.id.rl_swtich:
                if (sb_refund.isChecked()) {
                    sb_refund.setChecked(false);
                    rl_address_info.setVisibility(View.GONE);
                } else {
                    sb_refund.setChecked(true);
                    rl_address_info.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_address_info:
                Intent intent = new Intent(this, MyAddressActivity.class);
                startActivityForResult(intent, Constants.GET_ADDRESS);
                break;

        }
    }

    @Override
    public void operateReturn(BaseResponse baseResponse) {

        ToastUtils.showT("退款完成");
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();

    }


    private void showReason() {
        if (wheelDialog_reason == null) {
            wheelDialog_reason = new WheelDialog(this);
            wheelDialog_reason.setLabels(MyUserInstance.getInstance().reasons());
            wheelDialog_reason.setOnWheelSelectListener(new WheelDialog.OnWheelSelectListener() {
                @Override
                public void onClickOk(int index, String selectLabel) {

                    tv_reason.setText(selectLabel);
                    wheelDialog_reason.cancel();
                }
            });
            wheelDialog_reason.show();
        } else {
            if (wheelDialog_reason.isShowing()) {
                return;
            } else {
                wheelDialog_reason.show();
            }
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
                    tv_chose_address.setVisibility(View.GONE);
                    rl_address.setVisibility(View.VISIBLE);

                    tv_name.setText(address.getName());
                    tv_phone.setText(address.getMobile());
                    tv_address.setText(address.getProvince() + address.getCity() + address.getDistrict() + address.getAddress());
                }
            }
        }

    }
}
