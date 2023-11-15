package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.dialog.WheelDialog;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.adapter.UserOrderInfoChildAdapter;
import com.feicui365.live.shop.entity.OrderGoods;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.nasinet.userconfig.AppStatusManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SendGoodActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {




    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.tv_send_company)
    TextView tv_send_company;

    @BindView(R.id.tv_submit)
    TextView tv_submit;

    @BindView(R.id.rv_shop_goods)
    RecyclerView rv_shop_goods;


    String code;
    String company;
    ArrayList<OrderGoods> orderGoods;
    String order_id;
    WheelDialog wheelDialog_reason;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_send_good;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("填写发货信息");
        Bundle bundleObject = getIntent().getExtras();
        if (bundleObject == null) {
            finish();
            return;
        }
        if (bundleObject.getSerializable(Constants.ORDER_GOODS) == null) {
            finish();
            return;
        }
        if (bundleObject.getSerializable(Constants.ORDER_ID) == null) {
            finish();
            return;
        }
        orderGoods= (ArrayList<OrderGoods>) bundleObject.getSerializable(Constants.ORDER_GOODS);
        order_id= (String) bundleObject.getSerializable(Constants.ORDER_ID);


        initList();
    }

    private void initList() {
        UserOrderInfoChildAdapter orderChildAdapter = new UserOrderInfoChildAdapter(orderGoods,0);
        rv_shop_goods.setAdapter(orderChildAdapter);
        rv_shop_goods.setLayoutManager(new LinearLayoutManager(this));
        rv_shop_goods.setLayoutFrozen(true);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @OnClick({R.id.rl_code,R.id.rl_send_company,R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_code:
                Intent intent_code = new Intent(this, ScanActivity.class);
                startActivityForResult(intent_code, Constants.GET_ZXING);
                break;
            case R.id.rl_send_company:
                showCompany();
                break;
            case R.id.tv_submit:

                if(code==null&et_code.getText().toString().equals("")){
                    ToastUtils.showT("请输入物流单号");
                    return;
                }

                if(company==null){
                    ToastUtils.showT("请选择物流公司");
                    return;
                }
                showLoading();
                if(code==null){
                    code= et_code.getText().toString();
                }
                mPresenter.deliveryOrder(order_id,code,company);

                break;
        }
    }


    @Override
    public void deliveryOrder(BaseResponse baseResponse) {
        ToastUtils.showT("发货完成");
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK & requestCode == Constants.GET_ZXING) {
            code = data.getStringExtra("code");
            et_code.setText(code);

        }
    }


    private void showCompany() {
        if (wheelDialog_reason == null) {
            wheelDialog_reason = new WheelDialog(this);
            wheelDialog_reason.setLabels(MyUserInstance.getInstance().company());
            wheelDialog_reason.setOnWheelSelectListener(new WheelDialog.OnWheelSelectListener() {
                @Override
                public void onClickOk(int index, String selectLabel) {
                    company=selectLabel;
                    tv_send_company.setText(selectLabel);
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
