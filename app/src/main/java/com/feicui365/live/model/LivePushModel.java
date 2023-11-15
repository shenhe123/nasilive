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


import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.live.bean.EndLiveInfo;
import com.feicui365.live.live.bean.IsMgr;
import com.feicui365.live.live.bean.LinkInfo;
import com.feicui365.live.model.entity.AllUser;
import com.feicui365.live.model.entity.BanStatus;
import com.feicui365.live.model.entity.BaseLiveInfo;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.BuyGuard;
import com.feicui365.live.model.entity.CheckAttend;
import com.feicui365.live.model.entity.ContributeRank;

import com.feicui365.live.model.entity.Count;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.Guardian;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveAudienceListBean;
import com.feicui365.live.model.entity.LiveConsume;
import com.feicui365.live.model.entity.LiveHistoryBean;
import com.feicui365.live.model.entity.LiveInfo;
import com.feicui365.live.model.entity.LivePoster;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.model.entity.RoomManager;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.net.RetrofitClient;
import com.feicui365.live.shop.entity.Good;

import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;


public class LivePushModel implements LivePushContrat.Model {


    public Flowable<BaseResponse<QCloudData>> getTempKeysForCos(FormBody body) {
        return RetrofitClient.getInstance().getApi().getTempKeysForCos(body);
    }

    @Override
    public Flowable<BaseResponse<HotLive>> startLive(FormBody body) {
        return RetrofitClient.getInstance().getApi().startLive(body);
    }

    public Flowable<BaseResponse<ArrayList<GiftInfo>>> getGiftList() {
        return RetrofitClient.getInstance().getApi().getGiftList();
    }

    @Override
    public Flowable<BaseResponse> sendGift(FormBody body) {
        return RetrofitClient.getInstance().getApi().sendGift(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<ContributeRank>>> getContributeRank(FormBody body) {
        return RetrofitClient.getInstance().getApi().getContributeRank(body);
    }

    @Override
    public Flowable<BaseResponse<EndLiveInfo>> endlive(FormBody body) {
        return RetrofitClient.getInstance().getApi().endlive(body);
    }

    @Override
    public Flowable<BaseResponse> publish(FormBody body) {
        return RetrofitClient.getInstance().getApi().publish(body);
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
    public Flowable<BaseResponse> mergeStream(FormBody body) {
        return RetrofitClient.getInstance().getApi().mergeStream(body);
    }

    @Override
    public Flowable<BaseResponse> enterPkMode(FormBody body) {
        return RetrofitClient.getInstance().getApi().enterPkMode(body);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> getUserBasicInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getUserBasicInfo(body);
    }

    @Override
    public Flowable<BaseResponse> endPk(FormBody body) {
        return RetrofitClient.getInstance().getApi().endPk(body);
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
    public Flowable<BaseResponse<BaseLiveInfo>> getLiveBasicInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getLiveBasicInfo(body);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> getUserInfoById(FormBody body) {
        return RetrofitClient.getInstance().getApi().getUserInfoById(body);
    }

    @Override
    public Flowable<BaseResponse<CheckAttend>> checkAttent(FormBody body) {
        return RetrofitClient.getInstance().getApi().checkAttent(body);
    }

    @Override
    public Flowable<BaseResponse> attentAnchor(FormBody body) {
        return RetrofitClient.getInstance().getApi().attentAnchor(body);
    }

    @Override
    public Flowable<BaseResponse<LiveInfo>> getAnchorLiveInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getAnchorLiveInfo(body);
    }

    @Override
    public Flowable<BaseResponse> setRoomMgr(FormBody body) {
        return RetrofitClient.getInstance().getApi().setRoomMgr(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<UserRegist>>> getBannedUserList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getBannedUserList(body);
    }

    @Override
    public Flowable<BaseResponse<AllUser>> getAllUserList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getAllUserList(body);
    }


    @Override
    public Flowable<BaseResponse<BanStatus>> banUser(FormBody body) {
        return RetrofitClient.getInstance().getApi().banUser(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<RoomManager>>> getMgrList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getMgrList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Guardian>>> getGuardianList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getGuardianList(body);
    }

    @Override
    public Flowable<BaseResponse<BuyGuard>> buyGuard(FormBody body) {
        return RetrofitClient.getInstance().getApi().buyGuard(body);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> getUserInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getUserInfo(body);
    }

    @Override
    public Flowable<BaseResponse<HotLive>> mlvbStartLive(FormBody body) {
        return RetrofitClient.getInstance().getApi().mlvbStartLive(body);
    }

    @Override
    public Flowable<BaseResponse<LiveHistoryBean>> getLiveHistoryTitle(FormBody body) {
        return RetrofitClient.getInstance().getApi().getLiveHistoryTitle(body);
    }



    @Override
    public Flowable<BaseResponse> sendGift2(FormBody body) {
        return RetrofitClient.getInstance().getApi().sendGift(body);
    }

    @Override
    public Flowable<BaseResponse<LiveConsume>> liveChosume(FormBody body) {
        return RetrofitClient.getInstance().getApi().liveChosume(body);
    }

    @Override
    public Flowable<BaseResponse<LivePoster>> livePoster(FormBody body) {
        return RetrofitClient.getInstance().getApi().livePoster(body);
    }


    @Override
    public Flowable<BaseResponse<IsMgr>> checkIsMgr2(FormBody body) {
        return RetrofitClient.getInstance().getApi().checkIsMgr2(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Good>>> getGoodsList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getGoodsList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<LiveAudienceListBean>>> getLiveViewerList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getLiveViewerList(body);
    }

    @Override
    public Flowable<BaseResponse> setLinkOnOff(FormBody body) {
        return RetrofitClient.getInstance().getApi().setLinkOnOff(body);
    }
}
