package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.feicui365.live.model.entity.BanStatus;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.ToastUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 */
public class LiveUserManageDialog extends BaseDialogFragment implements LivePushContrat.View {

    @BindView(R.id.tv_set_manager)
    TextView mTvSetManager;

    @BindView(R.id.tv_name)
    TextView mTvNickName;


    private String mAnchorid;
    private UserRegist mUserInfo;
    private boolean isAnchor;

    private LivePushPresenter mPresenter;
    OnSetManagerClick onSetManagerClick;

    public interface OnSetManagerClick {
        void onSetManager(int status);
    }

    public void setOnSetManagerClick(OnSetManagerClick onSetManagerClick) {
        this.onSetManagerClick = onSetManagerClick;
    }

    @SuppressLint("ValidFragment")
    public LiveUserManageDialog(String mAnchorid, UserRegist mUserInfo, boolean isAnchor) {
        this.mAnchorid = mAnchorid;
        this.mUserInfo = mUserInfo;
        this.isAnchor = isAnchor;
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
        return R.layout.live_manager_dialog;
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
        params.width = WindowManager.LayoutParams.MATCH_PARENT;

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        if(isAnchor){
            mTvSetManager.setVisibility(View.VISIBLE);
        }
        if (mUserInfo.getIs_mgr() == 1) {
            mTvSetManager.setText(getString(R.string.st_cancel_manager));
        }else{
            mTvSetManager.setText(getString(R.string.st_set_manager));
        }
        mTvNickName.setText(mUserInfo.getNick_name());
    }

    @OnClick({R.id.tv_ban, R.id.tv_set_manager, R.id.tv_cancel})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();

                break;

            case R.id.tv_ban:
                mPresenter.banUser(mAnchorid, mUserInfo.getId(), 1,1+"");

                break;
            case R.id.tv_set_manager:
                if (mUserInfo.getIs_mgr() == 1) {
                    mPresenter.setRoomMgr(mUserInfo.getId(),0);
                    mUserInfo.setIs_mgr(0);
                }else{
                    mPresenter.setRoomMgr(mUserInfo.getId(),1);
                    mUserInfo.setIs_mgr(1);

                }
                if (mUserInfo.getIs_mgr() == 1) {
                    mTvSetManager.setText(getString(R.string.st_cancel_manager));
                }else{
                    mTvSetManager.setText(getString(R.string.st_set_manager));
                }

                break;

        }
    }

    @Override
    public void banUser(String userID,BanStatus bean) {
        ToastUtils.showT(getString(R.string.st_success));
        dismiss();
    }

    @Override
    public void setRoomMgr(BaseResponse bean) {
        ToastUtils.showT(getString(R.string.st_success));
        dismiss();
        if(onSetManagerClick!=null){
            onSetManagerClick.onSetManager(mUserInfo.getIs_mgr());
        }
    }
}
