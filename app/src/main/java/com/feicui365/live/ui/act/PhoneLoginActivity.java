package com.feicui365.live.ui.act;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.LoginContract;
import com.feicui365.live.eventbus.LoginChangeBus;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.UserConfig;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LoginPresenter;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;


import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

public class PhoneLoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    private static int REGISTER_TAG = 1;
    private static int FORGE_PASSWORD_TAG = 2;
    private Dialog dialog;
    @BindView(R.id.phone_number_et)
    EditText phone_number_et;
    @BindView(R.id.pwd_et)
    EditText pwd_et;


    @OnClick({R.id.qq_login, R.id.wecha_login, R.id.rl_back2, R.id.forge_password, R.id.register_tv, R.id.login_button})
    public void onClick(View view) {
        if(isFastClick()){
            return;
        }
        switch (view.getId()) {
            case R.id.login_button:
                if (TextUtils.isEmpty(phone_number_et.getText())) {
                    ToastUtils.showT(WordUtil.getString(R.string.Enter_phone_number));
                    return;
                }
                if (TextUtils.isEmpty(pwd_et.getText())) {
                    ToastUtils.showT(WordUtil.getString(R.string.Enter_new_password));
                    return;
                }

                mPresenter.userLogin(phone_number_et.getText().toString(), pwd_et.getText().toString());


                break;
            case R.id.register_tv:
                startActivity(new Intent(this, RegisterAndPasswordActivity.class).putExtra("tag", REGISTER_TAG));
               // finish();
                break;
            case R.id.forge_password:
                startActivity(new Intent(this, RegisterAndPasswordActivity.class).putExtra("tag", FORGE_PASSWORD_TAG));
              //  finish();
                break;
            case R.id.rl_back2:
                finish();
                break;
            case R.id.wecha_login:
                MyUserInstance.getInstance().getWxPayBuilder(this).wxLogin();
                break;
            case R.id.qq_login:
                Platform plat = ShareSDK.getPlatform(QQ.NAME);
                plat.removeAccount(true);
                plat.SSOSetting(false);
                plat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        PlatformDb db = platform.getDb();
                        String gender = "";

                        switch (hashMap.get("gender").toString()) {
                            case "男":
                                gender = "1";
                                break;
                            case "女":
                                gender = "0";
                                break;
                            default:
                                gender = "0";
                                break;
                        }
                        mPresenter.qqlogin(db.get("unionid"), hashMap.get("nickname").toString(), gender, hashMap.get("figureurl_qq").toString());


                    }


                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                       
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {
                    }
                });
                plat.showUser(null);
                break;
        }


    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_phone_login;
    }

    @Override
    protected void initData() {
        JSONObject userConfig_string = (JSONObject) JSON.parse(Hawk.get("USER_CONFIG"));
        UserConfig userConfig = JSON.toJavaObject(userConfig_string, UserConfig.class);
        MyUserInstance.getInstance().setUserConfig(userConfig);
        phone_number_et.setFocusable(true);
        phone_number_et.setFocusableInTouchMode(true);
        phone_number_et.requestFocus();

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
    public void userLogin(BaseResponse<UserRegist> bean) {
        if (bean.isSuccess()) {

          if(MyUserInstance.getInstance().loginMode()){
              MyUserInstance.getInstance().setUserInfo(bean.getData());
              String temp = JSON.toJSONString(bean.getData());
              Hawk.put("USER_REGIST", temp);
              Intent intent = new Intent();
              intent.putExtra("login_sucess", "1");
              setResult(1111, intent);
              AppManager.getAppManager().startActivity(HomeActivity.class);
              AppManager.getAppManager().finishActivity();



          }else{
              MyUserInstance.getInstance().setUserInfo(bean.getData());
              String temp = JSON.toJSONString(bean.getData());
              Hawk.put("USER_REGIST", temp);
              AppManager.getAppManager().startActivity(HomeActivity.class);
              AppManager.getAppManager().finishAllActivity();

          }
            EventBus.getDefault().post(LoginChangeBus.getInstance(""));
        } else {
            ToastUtils.showT(bean.getMsg());
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }
}
