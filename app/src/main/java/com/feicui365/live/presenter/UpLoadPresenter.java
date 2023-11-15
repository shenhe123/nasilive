package com.feicui365.live.presenter;

import com.feicui365.live.contract.UploadContract;
import com.feicui365.live.model.UploadModel;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.net.RxScheduler;
import com.feicui365.live.util.MyUserInstance;

import io.reactivex.functions.Consumer;
import okhttp3.FormBody;

public class UpLoadPresenter extends BasePresenter<UploadContract.View> implements UploadContract.Presenter {


    private UploadContract.Model model;

    public UpLoadPresenter() {
        model = new UploadModel();
    }

    @Override
    public void publish(String title,String thumb_url,String play_url,String topic) {
        if (!isViewAttached()) {
            return;
        }

        mView.showLoading();
        model.publish(new FormBody.Builder().add("title",title)
                .add("thumb_url",thumb_url)
                .add("play_url",play_url)
                .add("topic",topic)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {

                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if(bean.isSuccess()){
                            mView.publish(bean);
                            mView.hideLoading();
                        }

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
