package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.interfaces.OnDialogDissmissLinstener;
import com.feicui365.live.interfaces.OnLinkDialogLinstener;
import com.feicui365.live.live.bean.LinkInfo;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class LinkUserInfoDialog extends BaseDialogFragment implements LivePushContrat.View{

    @BindView(R.id.civ_avatar)
    CircleImageView mAvatar;

    @BindView(R.id.tv_nickname)
    TextView mTvNickName;
    @BindView(R.id.tv_id)
    TextView mTvId;

    UserRegist mUserBean;
    LivePushPresenter mPresenter;
    OnLinkDialogLinstener onLinkDialogLinstener;
    OnDialogDissmissLinstener onDialogDissmissLinstener;

    public void setOnDialogDissmissLinstener(OnDialogDissmissLinstener onDialogDissmissLinstener) {
        this.onDialogDissmissLinstener = onDialogDissmissLinstener;
    }

    public void setOnLinkDialogLinstener(OnLinkDialogLinstener onLinkDialogLinstener) {
        this.onLinkDialogLinstener = onLinkDialogLinstener;
    }

    public LinkUserInfoDialog(UserRegist mUserBean) {
        this.mUserBean = mUserBean;
    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_link_user_info_dialog;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.transparentBackgroundDiaolg;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter=new LivePushPresenter();
        mPresenter.attachView(this);
        mTvNickName.setText(mUserBean.getNick_name());
        GlideUtils.setImage(getContext(),mUserBean.getAvatar(),R.mipmap.moren,mAvatar);
        mTvId.setText(getString(R.string.st_mine_id,mUserBean.getId()));


    }

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(onDialogDissmissLinstener!=null){
            onDialogDissmissLinstener.onDismiss();
        }
    }

    @OnClick({R.id.tv_cancel,R.id.tv_submit})
    public void onClick(View view) {
            if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_submit:
                mPresenter.acceptMlvbLink(mUserBean.getId());
                break;
            case R.id.tv_cancel:
                mPresenter.refuseMlvbLink(mUserBean.getId());
                break;
        }
    }

    @Override
    public void refuseMlvbLink(BaseResponse bean) {
        dismiss();
    }



    @Override
    public void acceptMlvbLink(LinkInfo bean) {
        if(onLinkDialogLinstener!=null){
            onLinkDialogLinstener.onAccpet(bean.getPlay_url());
        }
        dismiss();
    }
}
