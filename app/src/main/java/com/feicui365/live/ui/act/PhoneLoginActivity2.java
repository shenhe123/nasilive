package com.feicui365.live.ui.act;

import android.app.Dialog;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.orhanobut.hawk.Hawk;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.LoginContract;
import com.feicui365.live.eventbus.LoginChangeBus;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.UserConfig;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LoginPresenter;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.nasinet.utils.AppManager;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class PhoneLoginActivity2 extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {


    private Dialog dialog;
    @BindView(R.id.phone_number_et)
    EditText etPhoneNum;
    @BindView(R.id.verification_code_et)
    EditText etVerificationCode;
    @BindView(R.id.et_invite_code)
    EditText etInviteCode;
    @BindView(R.id.verification_code)
    TextView tvSendCode;


    CountDownTimer countDownTimer;

    @OnClick({R.id.tv_confirm, R.id.register_tv, R.id.rl_back2, R.id.verification_code})
    public void onClick(View view) {
        if (isFastClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_confirm:
                String phone = etPhoneNum.getText().toString();
                String verificationCode = etVerificationCode.getText().toString();
                String inviteCode = etInviteCode.getText().toString();
                if (!ArmsUtils.isStringEmpty(phone)) {
                    ToastUtils.showT(WordUtil.getString(R.string.Enter_phone_number));
                    return;
                }
                if (!ArmsUtils.isStringEmpty(verificationCode)) {
                    ToastUtils.showT(WordUtil.getString(R.string.Enter_verification_code));
                    return;
                }

                mPresenter.phoneLogin(phone, verificationCode, inviteCode);


                break;
            case R.id.register_tv:
                startActivity(new Intent(this, PhoneRegeditActivity2.class));
                finish();
                break;

            case R.id.rl_back2:
                finish();
                break;
            case R.id.verification_code:
                if (!ArmsUtils.isStringEmpty(etPhoneNum.getText().toString())) {
                    ToastUtils.showT(WordUtil.getString(R.string.Enter_phone_number));
                    return;
                }

                tvSendCode.setEnabled(false);
                countDownTimer = new CountDownTimer(60000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if (isFinishing()) {
                            return;
                        }
                        tvSendCode.setText(new SimpleDateFormat("ss", Locale.CHINA).
                                format(new Date(millisUntilFinished)));
                    }

                    @Override
                    public void onFinish() {
                        if (isFinishing()) {
                            return;
                        }
                        tvSendCode.setText(WordUtil.getString(R.string.Send_code));
                        tvSendCode.setEnabled(true);
                    }
                };
                countDownTimer.start();
                mPresenter.getCode(etPhoneNum.getText().toString());
                break;

        }


    }

    @Override
    protected void onDestroy() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_login_2;
    }

    @Override
    protected void initData() {
        JSONObject userConfig_string = (JSONObject) JSON.parse(Hawk.get("USER_CONFIG"));
        UserConfig userConfig = JSON.toJavaObject(userConfig_string, UserConfig.class);
        MyUserInstance.getInstance().setUserConfig(userConfig);

    }

    @Override
    protected void initView() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        hideTitle(true);
        AppManager.getAppManager().finishOthersActivity(PhoneLoginActivity.class);
    }

    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }


    @Override
    public void phoneLogin(UserRegist bean) {


        if (MyUserInstance.getInstance().loginMode()) {
            MyUserInstance.getInstance().setUserInfo(bean);
            String temp = JSON.toJSONString(bean);
            Hawk.put("USER_REGIST", temp);
            Intent intent = new Intent();
            intent.putExtra("login_sucess", "1");
            setResult(1111, intent);
            AppManager.getAppManager().startActivity(HomeActivity.class);
            AppManager.getAppManager().finishActivity();


        } else {
            MyUserInstance.getInstance().setUserInfo(bean);
            String temp = JSON.toJSONString(bean);
            Hawk.put("USER_REGIST", temp);
            AppManager.getAppManager().startActivity(HomeActivity.class);
            AppManager.getAppManager().finishAllActivity();

        }
        EventBus.getDefault().post(LoginChangeBus.getInstance(""));

    }

    @Override
    public void finish() {
        super.finish();

    }
}
