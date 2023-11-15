package com.feicui365.live.shop.activity;

import android.view.View;
import android.widget.TextView;

import com.lxj.xpopup.XPopup;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.CommentZhifu;
import com.feicui365.live.wxapi.PayCallback;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.feicui365.nasinet.utils.AppManager;

import butterknife.BindView;
import butterknife.OnClick;

public class DepositActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {
    @BindView(R.id.tv_submit)
    TextView tv_submit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deposit;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        AppStatusManager.getInstance().setAppStatus(1);
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("我要开店");
    }

    @Override
    public void onError(Throwable throwable) {

    }


    @OnClick({R.id.tv_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                toPay();
                break;
        }
    }


    private void toPay() {
        CommentZhifu commentZhifu = new CommentZhifu(this);
        commentZhifu.setToPayListener(new CommentZhifu.ToPayListener() {
            @Override
            public void ali() {
                MyUserInstance.getInstance().getAliPayBuilder(DepositActivity.this).payDeposit("2");
                MyUserInstance.getInstance().getAliPayBuilder(DepositActivity.this).setPayCallback(new PayCallback() {
                    @Override
                    public void onSuccess() {

                        AppManager.getAppManager().startActivity(PaySuccessActivity.class);
                        finish();
                    }

                    @Override
                    public void onFailed() {
                        ToastUtils.showT("支付失败");

                    }
                });
            }

            @Override
            public void creditcard() {
                MyUserInstance.getInstance().getWxPayBuilder(DepositActivity.this).payDeposit("1");
                MyUserInstance.getInstance().getWxPayBuilder(DepositActivity.this).setPayCallback(new PayCallback() {
                    @Override
                    public void onSuccess() {

                        AppManager.getAppManager().startActivity(PaySuccessActivity.class);
                        finish();
                    }

                    @Override
                    public void onFailed() {
                        ToastUtils.showT("支付失败");
                    }
                });
            }
        });
        new XPopup.Builder(DepositActivity.this)
                .hasShadowBg(true)
                .asCustom(commentZhifu)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyUserInstance.getInstance().getWxPayBuilder(this).setPayCallback(null);
        MyUserInstance.getInstance().getAliPayBuilder(this).setPayCallback(null);
    }
}
