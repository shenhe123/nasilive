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

import android.text.TextUtils;

import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.live.bean.EndLiveInfo;
import com.feicui365.live.live.bean.IsMgr;
import com.feicui365.live.live.bean.LinkInfo;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.LivePushModel;
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
import com.feicui365.live.net.RxScheduler;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;


import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import okhttp3.FormBody;


public class LivePushPresenter extends BasePresenter<LivePushContrat.View> implements LivePushContrat.Presenter {
    private LivePushContrat.Model model;

    public LivePushPresenter() {
        model = new LivePushModel();
    }

    public void getTempKeysForCos() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.getTempKeysForCos(new FormBody.Builder()
                .add("platform", "2")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId()).add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<QCloudData>>Flo_io_main())
                .as(mView.<BaseResponse<QCloudData>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<QCloudData>>() {
                    @Override
                    public void accept(BaseResponse<QCloudData> bean) throws Exception {
                        mView.setTempKeysForCos(bean.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    public void startLive(String mlvb_token, String cateid, String thumb, String title, String orientation, String room_type, String price, String pwd) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        if (room_type.equals("1") & pwd.equals("")) {

            return;
        }

        if (room_type.equals("2") & price.equals("") & Integer.parseInt(price) < 0) {

            return;
        }
        //具体实现
        model.startLive(new FormBody.Builder()
                .add("platform", "2")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("cateid", cateid).add("thumb", thumb)
                .add("room_type", room_type).add("price", price)
                .add("pwd", pwd)
                .add("title", title)
                .add("mlvb_token", mlvb_token)

                .add("orientation", orientation).build())
                .compose(RxScheduler.<BaseResponse<HotLive>>Flo_io_main())
                .as(mView.<BaseResponse<HotLive>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<HotLive>>() {
                    @Override
                    public void accept(BaseResponse<HotLive> bean) throws Exception {
                        mView.startSuccess(bean.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    public void getGiftList() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getGiftList()
                .compose(RxScheduler.<BaseResponse<ArrayList<GiftInfo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<GiftInfo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<GiftInfo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<GiftInfo>> bean) throws Exception {
                        mView.setGiftList(bean.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    public void sendGift(String uid, String token, String anchorid, String livetid, String giftid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.sendGift(new FormBody.Builder().add("uid", uid)
                .add("platform", "2")
                .add("token", token)
                .add("anchorid", anchorid)
                .add("liveid", livetid)
                .add("giftid", giftid).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        mView.sendGiftSuccess();

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }


    public void getContributeRank(String liveid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getContributeRank(new FormBody.Builder().add("platform", "2").add("liveid", liveid).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ContributeRank>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ContributeRank>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ContributeRank>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ContributeRank>> bean) throws Exception {
                        mView.setContributeRank(bean);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }


    public void endlive(String liveid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.endlive(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).add("liveid", liveid).build())
                .compose(RxScheduler.<BaseResponse<EndLiveInfo>>Flo_io_main())
                .as(mView.<BaseResponse<EndLiveInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<EndLiveInfo>>() {
                    @Override
                    public void accept(BaseResponse<EndLiveInfo> bean) throws Exception {
                        mView.endlive(bean.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    @Override
    public void publish(String type, String title, String image_url, String blur_image_url, String video_url, String single_display_type, String unlock_price,String topic) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.publish(new FormBody.Builder()
                .add("platform", "2")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("type", type)
                .add("title", title)
                .add("image_url", image_url)
                .add("blur_image_url", blur_image_url)
                .add("video_url", video_url)
                .add("topic", topic)
                .add("single_display_type", single_display_type)
                .add("unlock_price", unlock_price).build())

                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.publish();
                        } else {
                            mView.showMgs(bean.getMsg());
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }


    @Override
    public void requestMlvbLink(String anchorid) {
        if (!isViewAttached()) {
            return;
        }
        model.requestMlvbLink(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)

                .build()).compose(RxScheduler.<BaseResponse<LinkInfo>>Flo_io_main())
                .as(mView.<BaseResponse<LinkInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<LinkInfo>>() {
                    @Override
                    public void accept(BaseResponse<LinkInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.requestMlvbLink(bean.getData());
                        } else {
                            mView.showMgs(bean.getMsg());
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
        model.stopMlvbLink(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
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
        model.refuseMlvbLink(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
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
        model.acceptMlvbLink(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
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
    public void setLinkOnOff(String type) {
        if (!isViewAttached()) {
            return;
        }
        model.setLinkOnOff(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("type", type)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setLinkOnOff(bean);
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
    public void mergeStream(String linkerid) {
        if (!isViewAttached()) {
            return;
        }
        model.mergeStream(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("linkerid", linkerid)
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.mergeStream(bean);
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
    public void enterPkMode() {
        if (!isViewAttached()) {
            return;
        }
        model.enterPkMode(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())

                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.enterPkMode(bean);
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
        model.getUserBasicInfo(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
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
    public void endPk() {
        if (!isViewAttached()) {
            return;
        }
        model.endPk(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.endPk(bean);
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
        model.getGuardInfo(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .build()).compose(RxScheduler.<BaseResponse<GuardianInfo>>Flo_io_main())
                .as(mView.<BaseResponse<GuardianInfo>> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<GuardianInfo>>() {
                    @Override
                    public void accept(BaseResponse<GuardianInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGuardInfo(bean.getData());
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
        model.getGuardianCount(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
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
    public void getLiveBasicInfo(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        model.getLiveBasicInfo(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
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
    @Override
    public void getUserInfoById(String id, int type) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());

        builder.add("id", id);


        model.getUserInfoById(builder.build()).compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {
                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getUserInfoById(bean.getData(), type);
                        } else {
                            mView.onError(null);
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
    public void checkAttent(String uid, int type) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.checkAttent(new FormBody.Builder().add("platform", "2").add("anchorid", uid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build())
                .compose(RxScheduler.<BaseResponse<CheckAttend>>Flo_io_main())
                .as(mView.<BaseResponse<CheckAttend>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<CheckAttend>>() {
                    @Override
                    public void accept(BaseResponse<CheckAttend> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.checkAttent(bean.getData(), type);
                        } else {
                            mView.showMgs(bean.getMsg());
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
    public void attentAnchor(String anchorid, String type) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.attentAnchor(new FormBody.Builder().add("platform", "2").add("anchorid", anchorid)
                .add("type", type)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.attentAnchor(bean);
                        } else {
                            mView.showMgs(bean.getMsg());
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
                .as(mView.<BaseResponse<LiveInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<LiveInfo>>() {
                    @Override
                    public void accept(BaseResponse<LiveInfo> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getAnchorLiveInfo(bean.getData());
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
    public void setRoomMgr(String mgrid, int type) {
        if (!isViewAttached()) {
            return;
        }

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());
        builder.add("type", String.valueOf(type));
        builder.add("mgrid", mgrid);


        model.setRoomMgr(builder.build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setRoomMgr(bean);
                        } else {
                            mView.onError(null);
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
    public void getBannedUserList(String anchorid, int page) {
        if (!isViewAttached()) {
            return;
        }

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());
        builder.add("anchorid", anchorid);
        builder.add("page", String.valueOf(page));
        builder.add("size", "20");

        model.getBannedUserList(builder.build()).compose(RxScheduler.<BaseResponse<ArrayList<UserRegist>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<UserRegist>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<UserRegist>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<UserRegist>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getBannedUserList(bean.getData());
                        } else {
                            mView.onError(null);
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
    public void getAllUserList() {
        if (!isViewAttached()) {
            return;
        }

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());

        model.getAllUserList(builder.build()).compose(RxScheduler.<BaseResponse<AllUser>>Flo_io_main())
                .as(mView.<BaseResponse<AllUser>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<AllUser>>() {
                    @Override
                    public void accept(BaseResponse<AllUser> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getAllUserList(bean.getData());
                        } else {
                            mView.onError(null);
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
    public void banUser(String anchorid, String userid, int type,String ban_type) {
        if (!isViewAttached()) {
            return;
        }

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());
        builder.add("type", String.valueOf(type));
        builder.add("anchorid", anchorid);
        builder.add("userid", userid);
        builder.add("ban_type", ban_type);


        model.banUser(builder.build()).compose(RxScheduler.<BaseResponse<BanStatus>>Flo_io_main())
                .as(mView.<BaseResponse<BanStatus>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<BanStatus>>() {
                    @Override
                    public void accept(BaseResponse<BanStatus> bean) throws Exception {
                        if (bean.isSuccess()) {
                            if (!TextUtils.isEmpty(bean.getMsg())){
                                ToastUtils.showT(bean.getMsg());
                            }
                            mView.banUser(userid,bean.getData());
                        } else {
                            mView.onError(null);
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
    public void getMgrList() {
        if (!isViewAttached()) {
            return;
        }

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());


        model.getMgrList(builder.build()).compose(RxScheduler.<BaseResponse<ArrayList<RoomManager>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<RoomManager>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<RoomManager>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<RoomManager>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getMgrList(bean.getData());
                        } else {
                            mView.onError(null);
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
    public void getGuardianList(String anchorid, String page) {
        if (!isViewAttached()) {
            return;
        }
        model.getGuardianList(new FormBody.Builder()
                .add("platform", "2")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("page", page)
                .add("size", "20")
                .add("anchorid", anchorid)

                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Guardian>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Guardian>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Guardian>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Guardian>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGuardianList(bean.getData());
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
    public void buyGuard(String anchorid, String type, String renew) {
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();
        model.buyGuard(new FormBody.Builder()
                .add("platform", "2")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid", anchorid)
                .add("type", type)
                .add("renew", renew)

                .build())
                .compose(RxScheduler.<BaseResponse<BuyGuard>>Flo_io_main())
                .as(mView.<BaseResponse<BuyGuard>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<BuyGuard>>() {
                    @Override
                    public void accept(BaseResponse<BuyGuard> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.buyGuard(bean.getData());
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
    public void getUserInfo() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getUserInfo(new FormBody.Builder().add("platform", "2").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build())
                .compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {
                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        mView.getUserInfo(bean.getData());
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
    public void mlvbStartLive(String cateid, String thumb, String title, String room_type, String price, String pwd,int is_hide_name) {
        if (!isViewAttached()) {
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());
        builder.add("orientation", "2");
        builder.add("cateid", cateid);
        builder.add("thumb", thumb);
        builder.add("title", title);

        builder.add("room_type", room_type);
        builder.add("is_hide_name",is_hide_name+"");
        if (ArmsUtils.isStringEmpty(price)) {
            builder.add("price", price);
        }

        if (ArmsUtils.isStringEmpty(pwd)) {
            builder.add("pwd", pwd);
        }


        model.mlvbStartLive(builder.build())
                .compose(RxScheduler.<BaseResponse<HotLive>>Flo_io_main())
                .as(mView.<BaseResponse<HotLive>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<HotLive>>() {
                    @Override
                    public void accept(BaseResponse<HotLive> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.mlvbStartLive(bean.getData());
                        } else {
                            mView.onError(null);
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
    public void sendGift2(String anchorid, String giftid, String liveid, int count, String pkid, String pkto) {
        if (!isViewAttached()) {
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());
        builder.add("anchorid", anchorid);
        builder.add("giftid", giftid);

        if(ArmsUtils.isStringEmpty(liveid)){
            builder.add("liveid", liveid);
        }
        builder.add("count", String.valueOf(count));
        if (ArmsUtils.isStringEmpty(pkid)) {
            builder.add("pkid", pkid);
        }
        if (ArmsUtils.isStringEmpty(pkto)) {
            builder.add("pkto", pkto);
        }

        model.sendGift2(builder.build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.sendGift2(bean);
                        } else {
                            mView.onError(null);
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
    public void liveChosume(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());

        builder.add("liveid", liveid);


        model.liveChosume(builder.build()).compose(RxScheduler.<BaseResponse<LiveConsume>>Flo_io_main())
                .as(mView.<BaseResponse<LiveConsume>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<LiveConsume>>() {
                    @Override
                    public void accept(BaseResponse<LiveConsume> bean) throws Exception {
                        if (bean.isSuccess()) {

                            mView.liveChosume(bean.getData());

                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.liveChosume(null);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void checkIsMgr2(String anchorid) {
        if (!isViewAttached()) {
            return;
        }

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());
        builder.add("anchorid", anchorid);


        model.checkIsMgr2(builder.build()).compose(RxScheduler.<BaseResponse<IsMgr>>Flo_io_main())
                .as(mView.<BaseResponse<IsMgr>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<IsMgr>>() {
                    @Override
                    public void accept(BaseResponse<IsMgr> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.checkIsMgr2(bean.getData());
                        } else {
                            mView.onError(null);
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

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("platform", "2");
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());
        builder.add("anchorid", anchorid);


        model.getGoodsList(builder.build()).compose(RxScheduler.<BaseResponse<ArrayList<Good>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Good>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Good>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Good>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGoodsList(bean.getData());
                        } else {
                            mView.onError(null);
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
    public void getLivePoster(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());

        builder.add("liveid", liveid);


        model.livePoster(builder.build()).compose(RxScheduler.<BaseResponse<LivePoster>>Flo_io_main())
                .as(mView.<BaseResponse<LivePoster>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<LivePoster>>() {
                    @Override
                    public void accept(BaseResponse<LivePoster> bean) throws Exception {
                        if (bean.isSuccess()) {

                            mView.LivePoster(bean.getData());

                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.liveChosume(null);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getLiveViewerList(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());
        builder.add("liveid", liveid);


        model.getLiveViewerList(builder.build()).compose(RxScheduler.<BaseResponse<ArrayList<LiveAudienceListBean>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<LiveAudienceListBean>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<LiveAudienceListBean>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<LiveAudienceListBean>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getLiveViewerList(bean.getData());
                        }
                        mView.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);
                        mView.liveChosume(null);
                        mView.hideLoading();
                    }
                });
    }

    @Override
    public void getLiveHistoryTitle() {
        if (!isViewAttached()) {
            return;
        }
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("uid", MyUserInstance.getInstance().getUserinfo().getId());
        builder.add("token", MyUserInstance.getInstance().getUserinfo().getToken());

        model.getLiveHistoryTitle(builder.build())
                .compose(RxScheduler.<BaseResponse<LiveHistoryBean>>Flo_io_main())
                .as(mView.<BaseResponse<LiveHistoryBean>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<LiveHistoryBean>>() {
                    @Override
                    public void accept(BaseResponse<LiveHistoryBean> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getLiveHistoryTitle(bean.getData());
                        } else {
                            mView.onError(null);
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
