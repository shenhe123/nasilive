package com.feicui365.live.ui.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.OthrBase2Activity;
import com.feicui365.live.util.CountDownUtil;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.MyUserInstance;

public class WalletManagerActivity extends OthrBase2Activity {

    EditText et_ali_name,et_ali_account,et_code;
    TextView tv_getcode,tv_sumbit,et_phone;
    CountDownUtil countDownUtil;
    String ali_name,ali_num;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            initView();
            setTitle("管理提现账户");
    }



    private void initView() {
        ali_name=getIntent().getStringExtra("ali_name");
        ali_num=getIntent().getStringExtra("ali_num");
        et_ali_name=findViewById(R.id.et_ali_name);
        et_ali_name.setText(ali_name);
        et_ali_account=findViewById(R.id.et_ali_account);
        et_ali_account.setText(ali_num);
        et_phone=findViewById(R.id.et_phone);
        et_phone.setText(MyUserInstance.getInstance().getUserinfo().getAccount());
        et_code=findViewById(R.id.et_ali_name);
        tv_getcode=findViewById(R.id.tv_getcode);
        tv_sumbit=findViewById(R.id.tv_sumbit);
        countDownUtil = new CountDownUtil(60000, 1000, tv_getcode);
        tv_getcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_phone.getText().toString().equals("")) {

                    ToastUtils.showT("请输入手机号码");
                    return;
                }

                if (!et_phone.getText().toString().equals(MyUserInstance.getInstance().getUserinfo().getAccount())) {

                    ToastUtils.showT("请输入当前账号绑定的手机");
                    return;
                }
                HttpUtils.getInstance().sendVerifyCode(et_phone.getText().toString(), new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if( HttpUtils.getInstance().swtichStatus(jsonObject)){
                            countDownUtil.start();
                        }

                    }
                });
            }
        });


        tv_sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_ali_name.getText().toString().equals("")) {

                    ToastUtils.showT("请输入支付宝姓名");
                    return;
                }

                if (et_ali_account.getText().toString().equals("")) {

                    ToastUtils.showT("请输入支付宝账号");
                    return;
                }

                if (et_code.getText().toString().equals("")) {

                    ToastUtils.showT("请输入验证码");
                    return;
                }

                HttpUtils.getInstance().editCashAccount(et_ali_name.getText().toString(), et_ali_account.getText().toString(), et_code.getText().toString(), new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject jsonObject = JSON.parseObject(response.body());
                        if( HttpUtils.getInstance().swtichStatus(jsonObject)){
                            ToastUtils.showT("绑定成功");
                            finish();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.wallet_manager_activity;
    }

    @Override
    protected void initData() {

    }
}
