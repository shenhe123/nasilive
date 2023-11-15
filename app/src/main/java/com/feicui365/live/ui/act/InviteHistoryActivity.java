package com.feicui365.live.ui.act;

import android.content.Intent;
import android.view.View;
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
//邀请好友提现
public class InviteHistoryActivity extends OthrBase2Activity implements View.OnClickListener {

    TextView et_money, tv_get, tv_get_history, tv_ali_num, tv_true_name, tv_can_get, tv_go;
    RelativeLayout rl_not_allow, rl_allow;
    ImageView iv_manage;
    StringCallback getDraw, withdraw, getAccount;
    String ali_name = "", ali_num = "", coin = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invite_history;
    }

    @Override
    protected void initData() {
        initStringCallBack();
        initView();
    }

    private void initView() {
        setTitle("提现");
        coin = getIntent().getStringExtra("coin");
        tv_can_get = findViewById(R.id.tv_can_get);
        tv_go = findViewById(R.id.tv_go);
        et_money = findViewById(R.id.et_money);
        tv_get = findViewById(R.id.tv_get);
        iv_manage = findViewById(R.id.iv_manage);
        rl_not_allow = findViewById(R.id.rl_not_allow);
        rl_allow = findViewById(R.id.rl_allow);
        tv_ali_num = findViewById(R.id.tv_ali_num);
        tv_true_name = findViewById(R.id.tv_true_name);
        tv_get_history = findViewById(R.id.tv_get_history);

        tv_go.setOnClickListener(this);
        tv_get.setOnClickListener(this);
        iv_manage.setOnClickListener(this);
        rl_not_allow.setOnClickListener(this);
        tv_get_history.setOnClickListener(this);

        if (MyUserInstance.getInstance().getUserinfo().getIs_anchor().equals("0")) {
            rl_not_allow.setVisibility(View.VISIBLE);
            rl_allow.setVisibility(View.GONE);
            iv_manage.setVisibility(View.GONE);
        } else {
            rl_not_allow.setVisibility(View.GONE);
            iv_manage.setVisibility(View.VISIBLE);
            rl_allow.setVisibility(View.VISIBLE);
            showLoading();
            HttpUtils.getInstance().getAccount(getAccount);
        }
        tv_can_get.setText(coin);
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.iv_manage:
                i = new Intent(InviteHistoryActivity.this, WalletManagerActivity.class);
                i.putExtra("ali_name", ali_name);
                i.putExtra("ali_num", ali_num);
                startActivity(i);
                break;

            case R.id.tv_get_history:
                i = new Intent(InviteHistoryActivity.this, CashOutHistoryActivity.class);
                i.putExtra("Type", "2");
                startActivity(i);

                break;
            case R.id.tv_get:
                if (et_money.getText().toString().equals("")) {
                    ToastUtils.showT("请输入需要提现的金额");
                    return;
                }
                showLoading();
                HttpUtils.getInstance().withDraw(ali_num, ali_name, et_money.getText().toString(), getDraw);
                break;

            case R.id.tv_go:
                i = new Intent(InviteHistoryActivity.this, ApplyAnchorActivity.class);
                i.putExtra("TYPE", 2);
                startActivity(i);
                break;
        }
    }

    private void initStringCallBack() {

        getDraw = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                JSONObject data = HttpUtils.getInstance().check(response);
                if(data.getJSONObject("data")==null){
                    return;
                }
                if (HttpUtils.getInstance().swtichStatus(data)) {
                    tv_can_get.setText(data.getJSONObject("data").getString("profit"));
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
            }
        };

        /*_______________*/
        withdraw = new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                hideLoading();
                JSONObject data = HttpUtils.getInstance().check(response);
                if(data.getJSONObject("data")==null){
                    return;
                }
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
                    if(data.getJSONObject("data")==null){
                        return;
                    }
                    tv_ali_num.setText(data.getJSONObject("data").getString("alipay_account"));
                    ali_num = data.getJSONObject("data").getString("alipay_account");
                    tv_true_name.setText(data.getJSONObject("data").getString("alipay_name"));
                    ali_name = data.getJSONObject("data").getString("alipay_name");
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
            }
        };

    }
}
