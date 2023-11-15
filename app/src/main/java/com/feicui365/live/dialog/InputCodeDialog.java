package com.feicui365.live.dialog;

import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.Dialogs;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class InputCodeDialog extends BaseDialogFragment implements HomeContract.View {

    @BindView(R.id.et_input_code)
    EditText tvContent;

    private Dialog dialog;
    HomePresenter mPresenter;

    public InputCodeDialog() {
    }



    @Override
    protected int getLayoutId() {
        return R.layout.dialog_input_code;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.transparentBackgroundDiaolg;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter=new HomePresenter();
        mPresenter.attachView(this);
    }

    @OnClick({R.id.iv_close, R.id.rl_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();

                break;
            case R.id.rl_submit:
                String result=tvContent.getText().toString();
                if(!ArmsUtils.isStringEmpty(result)){
                    ToastUtils.showT("请输入验证码");
                    return;
                }
                showLoading();
                mPresenter.bindAgent(result);
                break;


        }
    }

    @Override
    public void bindAgent(BaseResponse response) {
        showLoading();

        mPresenter.getUserInfo();
    }

    @Override
    public void setUserInfo(UserRegist bean) {

        MyUserInstance.getInstance().setUserInfo(bean);
        dismiss();
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height =  WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }





    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(getContext());
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
ToastUtils.showT(throwable.getMessage());
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }
}
