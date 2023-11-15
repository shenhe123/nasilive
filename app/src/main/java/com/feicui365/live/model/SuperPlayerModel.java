/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feicui365.live.model;

import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.live.bean.IsMgr;
import com.feicui365.live.live.bean.LinkInfo;
import com.feicui365.live.model.entity.BaseLiveInfo;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.CheckAttend;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.model.entity.Count;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.LiveInfo;

import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.net.RetrofitClient;
import com.feicui365.live.shop.entity.Good;

import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;
public class SuperPlayerModel implements SuperPlayerContrat.Model {


    public Flowable<BaseResponse<ArrayList<GiftInfo>>> getGiftList() {
        return RetrofitClient.getInstance().getApi().getGiftList();
    }

    @Override
    public Flowable<BaseResponse> sendGift(FormBody body) {
        return RetrofitClient.getInstance().getApi().sendGift(body);
    }

    @Override
    public Flowable<BaseResponse> timeBilling(FormBody body) {
        return RetrofitClient.getInstance().getApi().getTimeBilling(body);
    }


    @Override
    public Flowable<BaseResponse<UserRegist>> getAnchorInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getAnchorInfo(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<ContributeRank>>> getContributeRank(FormBody body) {
        return RetrofitClient.getInstance().getApi().getContributeRank(body);
    }

    @Override
    public Flowable<BaseResponse<LinkInfo>> requestMlvbLink(FormBody body) {
        return RetrofitClient.getInstance().getApi().requestMlvbLink(body);
    }

    @Override
    public Flowable<BaseResponse> stopMlvbLink(FormBody body) {
        return RetrofitClient.getInstance().getApi().stopMlvbLink(body);
    }

    @Override
    public Flowable<BaseResponse> refuseMlvbLink(FormBody body) {
        return RetrofitClient.getInstance().getApi().refuseMlvbLink(body);
    }

    @Override
    public Flowable<BaseResponse<LinkInfo>> acceptMlvbLink(FormBody body) {
        return RetrofitClient.getInstance().getApi().acceptMlvbLink(body);
    }

    @Override
    public Flowable<BaseResponse<IsMgr>> checkIsMgr(FormBody body) {
        return RetrofitClient.getInstance().getApi().checkIsMgr(body);
    }

    @Override
    public Flowable<BaseResponse<CheckAttend>> checkAttent(FormBody body) {
        return RetrofitClient.getInstance().getApi().checkAttent(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Good>>> getGoodsList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getGoodsList(body);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> getUserBasicInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getUserBasicInfo(body);
    }

    @Override
    public Flowable<BaseResponse<GuardianInfo>> getGuardInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getGuardInfo(body);
    }

    @Override
    public Flowable<BaseResponse<Count>> getGuardianCount(FormBody body) {
        return RetrofitClient.getInstance().getApi().getGuardianCount(body);
    }

    @Override
    public Flowable<BaseResponse<LiveInfo>> getLiveInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getLiveInfo(body);
    }

    @Override
    public Flowable<BaseResponse<LiveInfo>> getAnchorLiveInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getAnchorLiveInfo(body);
    }

    @Override
    public Flowable<BaseResponse<BaseLiveInfo>> getLiveBasicInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getLiveBasicInfo(body);
    }


}
