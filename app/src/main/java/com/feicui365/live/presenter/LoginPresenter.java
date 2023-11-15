package com.feicui365.live.presenter;

import com.feicui365.live.contract.LoginContract;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.LoginModel;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.CodeMsg;
import com.feicui365.live.model.entity.UserConfig;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.net.RxScheduler;
import com.feicui365.live.util.MyUserInstance;

import io.reactivex.functions.Consumer;
import okhttp3.FormBody;

public class LoginPresenter extends BasePresenter<LoginContract.View> implements LoginContract.Presenter {


    private LoginContract.Model model;

    public LoginPresenter() {
        model = new LoginModel();
    }


    public void getCommonConfig() {
        if (!isViewAttached()) {
            return;
        }


        model.getCommonConfig()
                .compose(RxScheduler.<BaseResponse<UserConfig>>Flo_io_main())
                .as(mView.<BaseResponse<UserConfig>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserConfig>>() {
                    @Override
                    public void accept(BaseResponse<UserConfig> bean) throws Exception {
                        mView.getCommonConfig(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });


    }


    public void userLogin(String phone, String pwd) {
        if (!isViewAttached()) {
            return;
        }

        mView.showLoading();
        model.userLogin(new FormBody.Builder().add("account", phone)
                .add("platform", "2")
                .add("pwd", pwd).build())
                .compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {

                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        mView.userLogin(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }


    public void getCode(String phone) {
        if (!isViewAttached()) {
            return;
        }

        model.getCode(new FormBody.Builder().add("mobile", phone).build())
                .compose(RxScheduler.<BaseResponse<CodeMsg>>Flo_io_main())
                .as(mView.<BaseResponse<CodeMsg>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<CodeMsg>>() {

                    @Override
                    public void accept(BaseResponse<CodeMsg> bean) throws Exception {
                        mView.getCode(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }


    public void userRegist(String phone, String pwd, String smscode, String invite_code) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.userRegist(new FormBody.Builder()
                .add("account", phone)
                .add("pwd", pwd)
                .add("smscode", smscode)
                .add("invite_code", invite_code)

                .build())
                .compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {

                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        mView.userRegist(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    public void changePwd(String phone, String pwd, String smscode) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.changePwd(new FormBody.Builder().add("mobile", phone).add("pwd", pwd).add("smscode", smscode).build())
                .compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {

                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        mView.userRegist(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void bindPhone(String mobile, String pwd, String smscode) {
        if (!isViewAttached()) {
            return;
        }

        model.bindPhone(new FormBody.Builder().add("mobile", mobile)
                .add("pwd", pwd)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("smscode", smscode).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {

                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        mView.bindPhone(bean);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    @Override
    public void qqlogin(String unionid, String nick_name, String gender, String icon) {
        if (!isViewAttached()) {
            return;
        }

        model.qqlogin(new FormBody.Builder().add("unionid", unionid)
                .add("nick_name", nick_name)
                .add("gender", gender)
                .add("icon", icon).build())
                .compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {

                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        mView.userLogin(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void phoneLogin(String account, String smscode, String invite_code) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("account", account);
        builder.add("smscode", smscode);
        if (ArmsUtils.isStringEmpty(invite_code)) {
            builder.add("invite_code", invite_code);
        }

        model.phoneLogin(builder.build())
                .compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {

                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        if(bean.isSuccess()){
                            mView.phoneLogin(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }
}
