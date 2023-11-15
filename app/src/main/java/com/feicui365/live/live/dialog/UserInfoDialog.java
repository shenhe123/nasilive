package com.feicui365.live.live.dialog;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Lifecycle;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.feicui365.nasinet.utils.AppManager;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.ui.act.AnchorCenterActivity;
import com.feicui365.live.util.LevelUtils;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 *
 */
@SuppressLint("ValidFragment")
public class UserInfoDialog extends BaseDialogFragment implements LivePushContrat.View {


    @BindView(R.id.civ_avatar)
    CircleImageView mAvatar;
    @BindView(R.id.tv_name)
    TextView mNickName;
    @BindView(R.id.iv_user_level)
    ImageView mUserLevel;
    @BindView(R.id.tv_age)
    TextView mAge;
    @BindView(R.id.tv_fans_count)
    TextView mFansCount;
    @BindView(R.id.tv_follow)
    TextView mTvFollow;

    @BindView(R.id.iv_manager)
    ImageView mIvManager;
    @BindView(R.id.iv_report)
    ImageView mIvReport;

    @BindView(R.id.iv_vip_backround)
    ImageView mIvVipBackRound;

    /**
     * 1,主要在直播间用
     * 2,需要主播ID
     * 3,区分是用户,管理,或者主播自己
     */
    private String mUserId;
    private String mAnchorId;
    private int mType;//0用户,1管理,2主播
    private UserRegist mUserInfo;

    private LivePushPresenter mPresenter;


    @SuppressLint("ValidFragment")
    public UserInfoDialog(String mUserId, String mAnchorId, int mType) {
        this.mUserId = mUserId;
        this.mAnchorId = mAnchorId;
        this.mType = mType;
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
        return R.layout.live_user_info_dialog;
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
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        mPresenter.getUserInfoById(mUserId,0);


        switch (mType) {
            case 0:
                mIvManager.setVisibility(View.GONE);

                    mIvReport.setVisibility(View.VISIBLE);

                break;
            case 1:
            case 2:
                mIvManager.setVisibility(View.VISIBLE);
                mIvReport.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public void getUserInfoById(UserRegist bean, int type) {
        mUserInfo = bean;
        GlideUtils.setImage(getContext(), bean.getAvatar(), R.mipmap.moren, mAvatar);
        mNickName.setText(bean.getNick_name());
        mUserLevel.setImageResource(LevelUtils.getUserLevel(bean.getUser_level()));
        mAge.setText(bean.getProfile().getAge());
        if (bean.getProfile().getGender() == 1) {
            //1男
            mAge.setCompoundDrawablesWithIntrinsicBounds(
                    getResources().getDrawable(R.mipmap.ic_boy_trans), null, null, null);
            mAge.setBackgroundResource(R.drawable.bg_gender_boy);
        } else {
            //2女
            mAge.setCompoundDrawablesWithIntrinsicBounds(
                    getResources().getDrawable(R.mipmap.ic_girl_trans), null, null, null);
            mAge.setBackgroundResource(R.drawable.bg_gender_girl);
        }

        mFansCount.setText(getString(R.string.st_fans_count, String.valueOf(bean.getFans_count())));
        if (bean.getIsattent() == 1) {
            mTvFollow.setText(getString(R.string.st_followed));
        } else {
            mTvFollow.setText(getString(R.string.st_follow));
        }

        mIvVipBackRound.setImageResource(ArmsUtils.getVipDialogBG(bean.getVip_level(),bean.getVip_date()));

    }

    @OnClick({R.id.tv_follow,R.id.iv_manager,R.id.iv_close,R.id.tv_home_page,R.id.tv_chat})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_follow:
                if (mUserInfo.getIsattent() == 1) {
                    mPresenter.attentAnchor(mUserId, "0");
                    mTvFollow.setText(getString(R.string.st_follow));
                    mUserInfo.setIsattent(0);
                } else {
                    mPresenter.attentAnchor(mUserId, "1");
                    mTvFollow.setText(getString(R.string.st_followed));
                    mUserInfo.setIsattent(1);
                }

                break;
            case R.id.iv_manager:
                initManagerDialog();
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_chat:
                TxImUtils.getSocketManager().startChat(mUserInfo);
                break;
            case R.id.tv_home_page:

                AppManager.getAppManager().startActivity(new Intent(getContext(), AnchorCenterActivity.class)
                        .putExtra("data", mUserId));
              dismiss();
                break;
        }
    }


    private void initManagerDialog(){
        if(mUserInfo==null){
            return;
        }
        LiveUserManageDialog dialog=new LiveUserManageDialog(mAnchorId,mUserInfo,isAnchor());
        dialog.show(getChildFragmentManager(),"");
        dialog.setOnSetManagerClick(new LiveUserManageDialog.OnSetManagerClick() {
            @Override
            public void onSetManager(int status) {
              mUserInfo.setIs_mgr(status);
            }
        });

    }

    private boolean isAnchor(){
        if(mType==2){
            return true;
        }else{
            return false;
        }
    }

}
