package com.feicui365.live.shop.activity;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.nasinet.userconfig.AppStatusManager;

public class PaySuccessActivity  extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        setTitle("支付");
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
