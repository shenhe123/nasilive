package com.feicui365.live.ui.act;

import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.LoginContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.LoginPresenter;
import com.feicui365.live.util.CountDownUtil;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.WordUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {
    @BindView(R.id.tv_submit)
    TextView tv_submit;
    @BindView(R.id.tv_getcode)
    TextView tv_getcode;
    @BindView(R.id.ll_bind_phone)
    LinearLayout ll_bind_phone;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_code)
    EditText et_code;
    private CountDownUtil countDownUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.bind_phone_activity;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void initView() {
        setTitle(WordUtil.getString(R.string.Bind_Phone));
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        countDownUtil = new CountDownUtil(60000, 1000, tv_getcode);
    }


    @OnClick({R.id.tv_getcode, R.id.tv_submit})
    public void onClick(View view) {
        if(isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.tv_getcode:
                if (et_phone.getText().toString().equals("")) {
                    ToastUtils.showT(WordUtil.getString(R.string.Enter_phone_number));
                    return;
                }
                mPresenter.getCode(et_phone.getText().toString());
                countDownUtil.start();
                break;
            case R.id.tv_submit:

                if (MyUserInstance.getInstance().getUserinfo().getAccount().equals("")) {

                    if (et_phone.getText().toString().equals("")) {
                        ToastUtils.showT(WordUtil.getString(R.string.Enter_phone_number));
                        return;
                    }

                    if (et_password.getText().toString().equals("")) {
                        ToastUtils.showT(WordUtil.getString(R.string.Enter_new_password));
                        return;
                    }

                    if (et_code.getText().toString().equals("")) {
                        ToastUtils.showT(WordUtil.getString(R.string.Enter_verification_code));
                        return;
                    }

                    mPresenter.bindPhone(et_phone.getText().toString(), et_password.getText().toString(), et_code.getText().toString());
                }
                break;
        }
    }


    @Override
    public void bindPhone(BaseResponse response) {
        if (response.isSuccess()) {
            MyUserInstance.getInstance().getUserinfo().setAccount(et_phone.getText().toString());

            ToastUtils.showT(  WordUtil.getString(R.string.Bind_Success));
            finish();
        }
    }

    @Override
    public void onError(Throwable throwable) {
        ToastUtils.showT(  WordUtil.getString(R.string.Bind_Fail));
    }


}
