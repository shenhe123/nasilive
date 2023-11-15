package com.feicui365.live.contract;

import com.feicui365.live.base.BaseView;

public interface WalletContract {
    interface Model {
//        Flowable<BaseResponse<ArrayList<Banners>>> getHomeScrollAd();
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);
    }

    interface Presenter {

    }
}
