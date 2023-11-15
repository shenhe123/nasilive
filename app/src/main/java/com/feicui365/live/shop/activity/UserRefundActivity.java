package com.feicui365.live.shop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.dialog.WheelDialog;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.entity.OrderGoods;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
;

import butterknife.BindView;
import butterknife.OnClick;

public class UserRefundActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {


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

    @BindView(R.id.et_order_desc)
    EditText et_order_desc;



    OrderGoods item;
    WheelDialog wheelDialog_reason;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund;
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
        if (bundleObject.getSerializable(Constants.ORDER_GOODS) == null) {
            finish();
            return;
        }

        item = (OrderGoods) bundleObject.getSerializable(Constants.ORDER_GOODS);

        initPage();
    }

    private void initPage() {
        String[] pic = item.getGoods().getThumb_urls().split(",");
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().centerCrop())
                .load(pic[0]).into(iv_pic);

        tv_price.setText("￥ " + item.getPrice());
        tv_count.setText("x" + item.getCount());
        tv_good_title.setText(item.getGoods().getTitle());
        if(item.getSize()!=null){
            tv_color.setText(item.getColor()+item.getSize());
        }else{
            tv_color.setText(item.getColor());
        }

        tv_refund_price.setText("￥ " + (Double.parseDouble(item.getPrice()) * item.getCount()));


    }

    @Override
    public void onError(Throwable throwable) {
        ToastUtils.showT(throwable.getMessage());
    }

    @OnClick({R.id.rl_reason, R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_reason:
                showReason();

                break;

            case R.id.tv_submit:
                if (tv_reason.getText().toString().equals("")) {
                    ToastUtils.showT("请选择退款理由");
                    return;
                }
                if (tv_reason.getText().toString().contains("请选择")) {
                    ToastUtils.showT("请选择退款理由");
                    return;
                }


                mPresenter.applyReturnGoods(String.valueOf(item.getId()), tv_reason.getText().toString(), String.valueOf(Double.parseDouble(item.getPrice()) * item.getCount()), et_order_desc.getText().toString());
                break;



        }
    }

    @Override
    public void applyReturnGoods(BaseResponse baseResponse) {

        if(baseResponse.isSuccess()){
            ToastUtils.showT("申请成功");
            finish();
        }
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


}
