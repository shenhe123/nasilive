package com.feicui365.live.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpFragment;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.presenter.HomePresenter;

import butterknife.BindView;

public class PhotoFragment extends BaseMvpFragment<HomePresenter> implements HomeContract.View {


    @BindView(R.id.photo_iv)
    PhotoView photo_iv;

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void initView(View view) {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        if (null != getArguments()){
            Bundle arguments = getArguments();
            Glide.with(getContext()).load(arguments.getString("url")).into(photo_iv);
        }
        photo_iv.enable();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
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
}
