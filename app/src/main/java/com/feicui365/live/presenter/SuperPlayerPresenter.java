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
package com.feicui365.live.presenter;

import com.google.gson.internal.LinkedTreeMap;
import com.feicui365.live.contract.SuperPlayerContrat;
import com.feicui365.live.live.bean.IsMgr;
import com.feicui365.live.live.bean.LinkInfo;
import com.feicui365.live.model.SuperPlayerModel;
import com.feicui365.live.model.entity.BaseLiveInfo;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.CheckAttend;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.model.entity.Count;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.LiveInfo;

import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.net.RxScheduler;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.util.MyUserInstance;


import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import okhttp3.FormBody;

public class SuperPlayerPresenter extends BasePresenter<SuperPlayerContrat.View> implements SuperPlayerContrat.Presenter {

    private SuperPlayerContrat.Model model;


    public SuperPlayerPresenter() {
        model = new SuperPlayerModel();
    }

    public void getGiftList() {
        if (!isViewAttached()) {
            return;
        }
        model.getGiftList().compose(RxScheduler.<BaseResponse<ArrayList<GiftInfo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<GiftInfo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<GiftInfo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<GiftInfo>> bean) throws Exception {
                        mView.setGiftList(bean.getData());
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });


    }


    public void getTimeBilling(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        model.timeBilling(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("liveid", liveid)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {

                            mView.timeBilling(bean);

                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });

    }


    public void sendGift(String count, String anchorid, String livetid, String giftid,String pkid,String pkto) {
        if (!isViewAttached()) {
            return;
        }
        model.sendGift(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("count", count)
                .add("anchorid", anchorid)
                .add("liveid", livetid)
                .add("pkid", pkid)
                .add("pkto", pkto)
                .add("giftid", giftid).build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) bean.getData();
                            int gold = (new Double(Double.valueOf(linkedTreeMap.get("gold").toString()))).intValue();

                            mView.sendSuccess(gold + "");

                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });

    }

    public void getAnchorInfo(String anchorid) {
        if (!isViewAttached()) {
            return;
        }
        model.getAnchorInfo(new FormBody.Builder().add("anchorid", anchorid).build())
                .compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {
                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        mView.setAnchorInfo(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    public void getContributeRank(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        model.getContributeRank(new FormBody.Builder().add("liveid", liveid).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ContributeRank>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ContributeRank>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ContributeRank>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ContributeRank>> bean) throws Exception {
                        mView.setContributeRank(bean);
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void requestMlvbLink(String anchorid, String mlvb_token) {
        if (!isViewAttached()) {
            return;
        }
        model.requestMlvbLink(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .add("mlvb_token", mlvb_token)
                .build()).compose(RxScheduler.<BaseResponse<LinkInfo>>Flo_io_main())
                .as(mView.<BaseResponse<LinkInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<LinkInfo>>() {
                    @Override
                    public void accept(BaseResponse<LinkInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.requestMlvbLink(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void stopMlvbLink(String anchorid, String linkerid) {
        if (!isViewAttached()) {
            return;
        }
        model.stopMlvbLink(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .add("linkerid", linkerid)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.stopMlvbLink(bean);
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void refuseMlvbLink(String userid) {
        if (!isViewAttached()) {
            return;
        }
        model.refuseMlvbLink(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("userid", userid)

                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.refuseMlvbLink(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void acceptMlvbLink(String userid) {
        if (!isViewAttached()) {
            return;
        }
        model.acceptMlvbLink(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("userid", userid)
                .build()).compose(RxScheduler.<BaseResponse<LinkInfo>>Flo_io_main())
                .as(mView.<BaseResponse<LinkInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<LinkInfo>>() {
                    @Override
                    public void accept(BaseResponse<LinkInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.acceptMlvbLink(bean.getData());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void checkIsMgr(String anchorid) {
        if (!isViewAttached()) {
            return;
        }

        model.checkIsMgr(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .build()).compose(RxScheduler.<BaseResponse<IsMgr>>Flo_io_main())
                .as(mView.<BaseResponse<IsMgr>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<IsMgr>>() {
                    @Override
                    public void accept(BaseResponse<IsMgr> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.checkIsMgr(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void checkAttent(String anchorid) {
        if (!isViewAttached()) {
            return;
        }
        model.checkAttent(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .build()).compose(RxScheduler.<BaseResponse<CheckAttend>>Flo_io_main())
                .as(mView.<BaseResponse<CheckAttend>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<CheckAttend>>() {
                    @Override
                    public void accept(BaseResponse<CheckAttend> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.checkAttent(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });

    }

    @Override
    public void getGoodsList(String anchorid) {
        if (!isViewAttached()) {
            return;
        }
        model.getGoodsList(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .build()).compose(RxScheduler.<BaseResponse<ArrayList<Good>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Good>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Good>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Good>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGoodsList(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getUserBasicInfo(String id) {
        if (!isViewAttached()) {
            return;
        }
        model.getUserBasicInfo(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("id", id)
                .build()).compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {
                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getUserBasicInfo(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getGuardInfo(String anchorid) {
        if (!isViewAttached()) {
            return;
        }
        model.getGuardInfo(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .build()).compose(RxScheduler.<BaseResponse<GuardianInfo>>Flo_io_main())
                .as(mView.<BaseResponse<GuardianInfo>> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<GuardianInfo>>() {
                    @Override
                    public void accept(BaseResponse<GuardianInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGuardInfo(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getGuardianCount(String anchorid) {
        if (!isViewAttached()) {
            return;
        }
        model.getGuardianCount(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .build()).compose(RxScheduler.<BaseResponse<Count>>Flo_io_main())
                .as(mView.<BaseResponse<Count>> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<Count>>() {
                    @Override
                    public void accept(BaseResponse<Count> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGuardianCount(bean.getData());
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getLiveInfo(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        model.getLiveInfo(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("liveid", liveid)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getLiveInfo(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getAnchorLiveInfo(String anchorid) {
        if (!isViewAttached()) {
            return;
        }
        model.getAnchorLiveInfo(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .build()).compose(RxScheduler.<BaseResponse<LiveInfo>>Flo_io_main())
                .as(mView.<BaseResponse<LiveInfo>> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<LiveInfo>>() {
                    @Override
                    public void accept(BaseResponse<LiveInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getLiveInfo(bean);
                        }

                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getLiveBasicInfo(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        model.getLiveBasicInfo(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("liveid", liveid)
                .build()).compose(RxScheduler.<BaseResponse<BaseLiveInfo>>Flo_io_main())
                .as(mView.<BaseResponse<BaseLiveInfo>> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<BaseLiveInfo>>() {
                    @Override
                    public void accept(BaseResponse<BaseLiveInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getLiveBasicInfo(bean);
                        }

                        mView.hideLoading();
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
