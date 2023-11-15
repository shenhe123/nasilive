package com.feicui365.live.model;

import com.feicui365.live.contract.UploadContract;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.net.RetrofitClient;

import io.reactivex.Flowable;
import okhttp3.RequestBody;

public class UploadModel implements UploadContract.Model {



    @Override
    public Flowable<BaseResponse> publish(RequestBody body) {
        return RetrofitClient.getInstance().getApi().publish(body);
    }
}
