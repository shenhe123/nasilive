package com.feicui365.live.model;

import com.feicui365.live.contract.LoginContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.CodeMsg;
import com.feicui365.live.model.entity.UserConfig;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.net.RetrofitClient;

import io.reactivex.Flowable;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class LoginModel implements LoginContract.Model {


    @Override
    public Flowable<BaseResponse<UserConfig>> getCommonConfig() {
        return RetrofitClient.getInstance().getApi().getCommonConfig();
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> userLogin(RequestBody body) {
        return RetrofitClient.getInstance().getApi().userLogin(body);
    }


    @Override
    public Flowable<BaseResponse<CodeMsg>> getCode(RequestBody phone) {
        return RetrofitClient.getInstance().getApi().getCode(phone);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> userRegist(RequestBody body) {
        return RetrofitClient.getInstance().getApi().userRegist(body);
    }
    @Override
    public Flowable<BaseResponse<UserRegist>> changePwd(RequestBody body) {
        return RetrofitClient.getInstance().getApi().changePwd(body);
    }
    @Override
    public Flowable<BaseResponse> bindPhone(FormBody body) {
        return RetrofitClient.getInstance().getApi().bindPhone(body);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> qqlogin(FormBody body) {
        return RetrofitClient.getInstance().getApi().qqlogin(body);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> phoneLogin(FormBody body) {
        return RetrofitClient.getInstance().getApi().phoneLogin(body);
    }

}
