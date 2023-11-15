package com.feicui365.live.shop.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.shop.entity.SellerWalletInfo;
import com.feicui365.live.ui.act.WalletManagerActivity;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class SellerWithDrawActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {

    @BindView(R.id.rl_not_allow)
    RelativeLayout rl_not_allow;

    @BindView(R.id.rl_allow)
    RelativeLayout rl_allow;

    @BindView(R.id.tv_true_name)
    TextView tv_true_name;
    @BindView(R.id.tv_ali_num)
    TextView tv_ali_num;

    @BindView(R.id.tv_withdraw_num)
    TextView tv_withdraw_num;

    @BindView(R.id.tv_service_price)
    TextView tv_service_price;


    @BindView(R.id.iv_manage)
    ImageView iv_manage;

    @BindView(R.id.et_cash_out)
    EditText et_cash_out;

    String ali_name = "", ali_num = "";
    String service_price = MyUserInstance.getInstance().getUserConfig().getConfig().getShop_commission();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seller_withdraw;
    }

    @Override
    protected void initData() {
        if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("0")) {
            rl_not_allow.setVisibility(View.VISIBLE);
            rl_allow.setVisibility(View.GONE);
        } else {
            rl_not_allow.setVisibility(View.GONE);
            rl_allow.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void initView() {
        mPresenter = new ShopPresenter();
        mPresenter.attachView(this);
        setTitle("立即提现");
        setOther("提现记录");
        hideOther(false);
        tv_service_price.setText("服务费(费率" + service_price + "%) :0.00");
        initEdit();
    }

    private void initEdit() {
        et_cash_out.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!"".equals(tv_withdraw_num.getText())) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals("")) {
                    tv_service_price.setText("服务费(费率" + service_price + "%) :" + "0.00");
                    return;
                }

                if (Double.parseDouble(s.toString()) > Double.parseDouble(tv_withdraw_num.getText().toString())) {
                    tv_service_price.setText("服务费(费率" + service_price + "%) :" + computer(Double.parseDouble(tv_withdraw_num.getText().toString()), Double.parseDouble(service_price)));
                    et_cash_out.setText(tv_withdraw_num.getText().toString());
                } else {
                    tv_service_price.setText("服务费(费率" + service_price + "%) :" + computer(Double.parseDouble(s.toString()), Double.parseDouble(service_price)));
                }


            }
        });
    }


    private String computer(Double param_1, Double param_2) {

        Double result_double = param_1 / 100 * param_2;
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(result_double);
    }


    @Override
    protected void onResume() {
        super.onResume();

        mPresenter.walletInfo();
    }

    @OnClick({R.id.iv_manage, R.id.tv_submit, R.id.tv_other})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_manage:
                Intent i = new Intent(this, WalletManagerActivity.class);
                i.putExtra("ali_name", ali_name);
                i.putExtra("ali_num", ali_num);
                startActivity(i);
                break;

            case R.id.tv_submit:
                if (ali_num.equals("")) {
                    ToastUtils.showT("请完善账户信息");
                    return;
                }

                if (ali_name.equals("")) {
                    ToastUtils.showT("请完善账户信息");
                    return;
                }

                if (et_cash_out.getText().toString().equals("")) {
                    ToastUtils.showT("请输入提现金额");
                    return;
                }


                mPresenter.Sellerwithdraw(ali_num, ali_name, et_cash_out.getText().toString());
                break;

            case R.id.tv_other:
                Intent i2 = new Intent(this, SellerCashOutHistoryActivity.class);
                i2.putExtra("Type", "3");
                startActivity(i2);
                break;
        }

    }

    @Override
    public void walletInfo(SellerWalletInfo bean) {
        tv_withdraw_num.setText(bean.getProfit());
        if (bean.getCash_account() != null) {

            if (bean.getCash_account().getAlipay_account() != null) {
                tv_ali_num.setText(bean.getCash_account().getAlipay_account());
                ali_num = bean.getCash_account().getAlipay_account();
            }
            if (bean.getCash_account().getAlipay_name() != null) {
                tv_true_name.setText(bean.getCash_account().getAlipay_name());
                ali_name = bean.getCash_account().getAlipay_name();
            }


            rl_not_allow.setVisibility(View.GONE);
            rl_allow.setVisibility(View.VISIBLE);
        } else {
            rl_not_allow.setVisibility(View.VISIBLE);
            rl_allow.setVisibility(View.GONE);
        }

    }

    @Override
    public void Sellerwithdraw(BaseResponse baseResponse) {
        ToastUtils.showT(baseResponse.getMsg());
        mPresenter.walletInfo();
    }

    @Override
    public void onError(Throwable throwable) {
        ToastUtils.showT(throwable.getMessage());
    }
}
