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

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseDialogFragment;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.net.APIService;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.ui.act.WebShopActivity;
import com.feicui365.live.util.MyUserInstance;

import butterknife.BindView;
import butterknife.OnClick;


@SuppressLint("ValidFragment")
public class ShowShopItemDialog extends BaseDialogFragment implements LivePushContrat.View {

    @BindView(R.id.iv_item_acvatar)
    ImageView ivItemAvatar;

    @BindView(R.id.tv_goods_price)
    TextView mTvGoodsPrice;

    Good mGood;

    public ShowShopItemDialog(Good mGood) {
        this.mGood = mGood;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.live_show_item_dialog;
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
    protected void setWindowAttributes(Window window) {

        window.setWindowAnimations(R.style.bottomToTopAnim);
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL, WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.setFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;

        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.x = 25;
        params.y = 400;
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GlideUtils.setImage(getContext(), mGood.getThumb_urls(), ivItemAvatar);
        mTvGoodsPrice.setText("¥:" + mGood.getPrice());
    }

    @OnClick({R.id.iv_shop_close, R.id.rl_shop_tumb})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_shop_close:
                dismiss();
                break;
            case R.id.rl_shop_tumb:
                if (MyUserInstance.getInstance().visitorIsLogin(getActivity())) {
                    Intent i = new Intent(getContext(), WebShopActivity.class);
                    i.putExtra("jump_url", APIService.Goods + mGood.getId() + "?token=" + MyUserInstance.getInstance().getUserinfo().getToken() + "&uid=" + MyUserInstance.getInstance().getUserinfo().getId());
                    getContext().startActivity(i);
                }
                break;
        }

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
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }
}
