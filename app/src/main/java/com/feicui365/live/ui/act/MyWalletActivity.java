package com.feicui365.live.ui.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.MyUserInstance;

import java.math.BigDecimal;

public class MyWalletActivity extends OthrBase2Activity implements View.OnClickListener {

    TextView tv_dimen, tv_use_dimen, tv_come, tv_ali_num, tv_true_name, tv_sumbit;
    EditText et_limit;
    ImageView iv_manage;
    RelativeLayout rl_not_allow, rl_allow;
    int temp_coin = 0;
    StringCallback withdraw, getAccount;
    String ali_name = "", ali_num = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的钱包");
        initStringCallBack();
        initView();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.my_wallet_activity;
    }

    private void initView() {
        tv_dimen = findViewById(R.id.tv_dimen);
        tv_use_dimen = findViewById(R.id.tv_use_dimen);
        tv_come = findViewById(R.id.tv_come);
        tv_ali_num = findViewById(R.id.tv_ali_num);
        tv_true_name = findViewById(R.id.tv_true_name);
        et_limit = findViewById(R.id.et_limit);
        iv_manage = findViewById(R.id.iv_manage);
        tv_dimen.setText(MyUserInstance.getInstance().getUserinfo().getDiamond_total());
        tv_use_dimen.setText(MyUserInstance.getInstance().getUserinfo().getDiamond());
        rl_not_allow = findViewById(R.id.rl_not_allow);
        rl_allow = findViewById(R.id.rl_allow);
        tv_sumbit = findViewById(R.id.tv_sumbit);
        tv_other.setVisibility(View.VISIBLE);
        tv_other.setText("提现记录");

        et_limit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    temp_coin = 0;
                    tv_come.setText("¥  " + temp_coin);
                    return;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!et_limit.getText().toString().equals("")) {

                    if (Integer.parseInt(et_limit.getText().toString()) > Integer.parseInt(tv_use_dimen.getText().toString())) {
                        et_limit.setText(tv_use_dimen.getText().toString());
                        temp_coin = Integer.parseInt(et_limit.getText().toString());
                        tv_come.setText("¥  " + div(Double.parseDouble(tv_use_dimen.getText().toString()), Double.parseDouble(MyUserInstance.config.getConfig().getExchange_rate()), 2) + "");
                    } else {
                        temp_coin = Integer.parseInt(et_limit.getText().toString());
                        tv_come.setText("¥  " + div(Double.parseDouble(et_limit.getText().toString()), Double.parseDouble(MyUserInstance.config.getConfig().getExchange_rate()), 2) + "");
                    }
                }
            }
        });





        if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("0")) {
            rl_not_allow.setVisibility(View.VISIBLE);
            rl_allow.setVisibility(View.GONE);
            iv_manage.setVisibility(View.GONE);
        } else {
            rl_not_allow.setVisibility(View.GONE);
            iv_manage.setVisibility(View.VISIBLE);
            rl_allow.setVisibility(View.VISIBLE);

            HttpUtils.getInstance().getAccount(getAccount);

        }

        rl_not_allow.setOnClickListener(this);
        iv_manage.setOnClickListener(this);
        tv_sumbit.setOnClickListener(this);
        tv_other.setOnClickListener(this);
    }

    public double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    protected void initData() {

    }


    private void initStringCallBack() {

        withdraw = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                JSONObject data = HttpUtils.getInstance().check(response);
                if (HttpUtils.getInstance().swtichStatus(data)) {
                    ToastUtils.showT("申请提现成功，工作人员将在24小时内处理您的提现申请");
                }
            }


            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
            }
        };

        /*_______________*/

        getAccount = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                JSONObject data = JSON.parseObject(response.body());
                if (HttpUtils.getInstance().swtichStatus(data)) {
                    if (data.getString("data") != null) {
                        tv_ali_num.setText(data.getJSONObject("data").getString("alipay_account"));
                        ali_num = data.getJSONObject("data").getString("alipay_account");
                        tv_true_name.setText(data.getJSONObject("data").getString("alipay_name"));
                        ali_name = data.getJSONObject("data").getString("alipay_name");
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
            }
        };
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.tv_other:
                i = new Intent(MyWalletActivity.this, CashOutHistoryActivity.class);
                i.putExtra("Type", "1");
                startActivity(i);
                break;

            case R.id.iv_manage:
                i = new Intent(MyWalletActivity.this, WalletManagerActivity.class);
                i.putExtra("ali_name", ali_name);
                i.putExtra("ali_num", ali_num);
                startActivity(i);
                break;

            case R.id.rl_not_allow:

                i = new Intent(MyWalletActivity.this, ApplyAnchorActivity.class);
                i.putExtra("TYPE", 1);
                startActivity(i);

                break;

            case R.id.tv_sumbit:
                if (temp_coin < Integer.valueOf(MyUserInstance.getInstance().getUserConfig().getConfig().getWithdraw_min())) {
                    ToastUtils.showT("提现金额不能小于" + MyUserInstance.getInstance().getUserConfig().getConfig().getWithdraw_min() +"元");
                    return;
                }

                if (!et_limit.getText().toString().equals("")) {
                    if (Integer.valueOf(et_limit.getText().toString()) > Integer.valueOf(tv_use_dimen.getText().toString())) {

                        ToastUtils.showT("提现钻石不能大于可用钻石");
                        return;
                    }
                } else {
                    return;
                }

                if (tv_ali_num.getText().toString().equals("") | tv_true_name.getText().toString().equals("")) {

                    ToastUtils.showT("支付宝账号有误");
                    return;
                }

                showLoading();
                HttpUtils.getInstance().withdraw(temp_coin + "", et_limit.getText().toString(), MyUserInstance.getInstance().getUserinfo().getAlipay_account(), MyUserInstance.getInstance().getUserinfo().getAlipay_name(), withdraw);
                break;

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
