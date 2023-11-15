package com.feicui365.live.presenter;

import com.feicui365.live.model.entity.BuyGuard;
import com.feicui365.live.model.entity.CheckAttend;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.Guardian;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.LiveRoomDetailBean;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.model.entity.RoomManager;
import com.feicui365.live.model.entity.SwiftMessageBean;
import com.google.gson.internal.LinkedTreeMap;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.HomeModel;
import com.feicui365.live.model.entity.AnchorHistory;
import com.feicui365.live.model.entity.Banners;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.CashOutHistory;
import com.feicui365.live.model.entity.ChatGiftBean;
import com.feicui365.live.model.entity.Comment;
import com.feicui365.live.model.entity.HomeAd;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.Invite;
import com.feicui365.live.model.entity.MatchList;
import com.feicui365.live.model.entity.MomentDetail;
import com.feicui365.live.model.entity.PersonalAnchorInfo;
import com.feicui365.live.model.entity.ProfitLog;
import com.feicui365.live.model.entity.ShortVideo;
import com.feicui365.live.model.entity.Topic;
import com.feicui365.live.model.entity.Trend;
import com.feicui365.live.model.entity.UserInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.net.RxScheduler;
import com.feicui365.live.ui.act.LoginActivity;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;


import java.util.ArrayList;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private HomeContract.Model model;

    public HomePresenter() {
        model = new HomeModel();
    }


    @Override
    public void getHomeScrollAd() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getHomeScrollAd()
                .compose(RxScheduler.<BaseResponse<ArrayList<Banners>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Banners>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Banners>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Banners>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getHomeScrollAd(bean);
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
    public void getHomePopAd() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getHomePopAd()
                .compose(RxScheduler.<BaseResponse<HomeAd>>Flo_io_main())
                .as(mView.<BaseResponse<HomeAd>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<HomeAd>>() {
                    @Override
                    public void accept(BaseResponse<HomeAd> bean) throws Exception {
                        mView.getHomePopAd(bean);
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
    public void getMomentAttent(int page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getMomentAttent(new FormBody.Builder().add("page", String.valueOf(page))
                .add("size", "20").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Trend>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Trend>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Trend>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Trend>> bean) throws Exception {
                        mView.setMoment(bean.getData());
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
    public void getMomentHot(int page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getMomentHot(new FormBody.Builder().add("page", String.valueOf(page))
                .add("size", "20").add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Trend>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Trend>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Trend>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Trend>> bean) throws Exception {
                        mView.setMoment(bean.getData());
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
    public void getMomentDetail(String uid, String token, String momentid, String lastid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getMomentDetail(new FormBody.Builder().add("uid", uid)
                .add("token", token)
                .add("lastid", lastid)
                .add("size", "20")
                .add("momentid", momentid).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<MomentDetail>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<MomentDetail>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<MomentDetail>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<MomentDetail>> bean) throws Exception {
                        mView.setMomentDetail(bean.getData());
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
    public void getMomentCommentReplys(String uid, String token, String commentid, String lastid, String size) {
        model.getMomentCommentReplys(new FormBody.Builder().add("uid", uid)
                .add("token", token)
                .add("lastid", lastid)
                .add("size", size)
                .add("commentid", commentid).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<MomentDetail>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<MomentDetail>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<MomentDetail>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<MomentDetail>> bean) throws Exception {
                        mView.setMomentDetail(bean.getData());
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
    public void getHotLives(String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }

        //具体实现
        model.getHotLives(new FormBody.Builder().add("page", page)
                .add("size", "20").add("uid", MyUserInstance.getInstance().getUserinfo().getId()).add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<HotLive>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<HotLive>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<HotLive>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<HotLive>> bean) throws Exception {
                        mView.getHotLives(bean);
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
    public void getLivesByCategory(String page, String categoryid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }

        //具体实现
        model.getLivesByCategory(new FormBody.Builder().add("page", page).add("categoryid", categoryid)
                .add("size", "20").add("uid", MyUserInstance.getInstance().getUserinfo().getId()).add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<HotLive>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<HotLive>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<HotLive>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<HotLive>> bean) throws Exception {
                        if(bean.isSuccess()){
                            mView.getHotLives(bean);
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
    public void getRandomList(String page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }

        //具体实现
        model.getRandomList(new FormBody.Builder().add("page", page)
                .add("size", "20").add("uid", MyUserInstance.getInstance().getUserinfo().getId()).add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ShortVideo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ShortVideo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ShortVideo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ShortVideo>> bean) throws Exception {
                        mView.getRandomList(bean);
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
    public void getComments(String lastid, String videoid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        FormBody formBody;
        if (!lastid.equals("")) {
            formBody = new FormBody.Builder().add("videoid", videoid).add("size", "20").add("lastid", lastid).add("uid", MyUserInstance.getInstance().getUserinfo().getId()).add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build();
        } else {
            formBody = new FormBody.Builder().add("videoid", videoid).add("size", "20").add("uid", MyUserInstance.getInstance().getUserinfo().getId()).add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build();
        }


        //具体实现
        model.getComments(formBody)
                .compose(RxScheduler.<BaseResponse<ArrayList<Comment>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Comment>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Comment>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Comment>> bean) throws Exception {
                        mView.getComments(bean);
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
    public void getShortUserInfo(String authorid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getShortUserInfo(new FormBody.Builder().add("authorid", authorid).build())
                .compose(RxScheduler.<BaseResponse<UserInfo>>Flo_io_main())
                .as(mView.<BaseResponse<UserInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserInfo>>() {
                    @Override
                    public void accept(BaseResponse<UserInfo> bean) throws Exception {
                        mView.setShortUserInfo(bean.getData());
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
        model.getUserInfo(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build())
                .compose(RxScheduler.<BaseResponse<UserRegist>>Flo_io_main())
                .as(mView.<BaseResponse<UserRegist>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<UserRegist>>() {
                    @Override
                    public void accept(BaseResponse<UserRegist> bean) throws Exception {
                        mView.hideLoading();
                        if(bean.isSuccess()){
                            mView.setUserInfo(bean.getData());

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

    @Override
    public void collectMoment(String momentid, String type) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.collectMoment(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("momentid", momentid)
                .add("type", type)
                .build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.collectMoment(bean);
                        } else {
                            mView.showMgs(bean.getMsg());
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

    @Override
    public void likeMoment(String momentid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.likeMoment(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("momentid", momentid)
                .build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.likeMoment(bean);
                        } else {
                            mView.showMgs(bean.getMsg());
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

    @Override
    public void likeComment(String momentid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.likeComment(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("commentid", momentid)
                .build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.likeComment(bean);
                        } else {
                            mView.showMgs(bean.getMsg());
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

    @Override
    public void getAnchorWorks(String authorid, int page) {
//View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getAnchorWorks(new FormBody.Builder()
                .add("userid", authorid)
                .add("page", String.valueOf(page))
                .add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("size", "20")
                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ShortVideo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ShortVideo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ShortVideo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ShortVideo>> bean) throws Exception {
                        if (bean != null) {
                            if (bean.isSuccess()) {

                                mView.setAnchorWorks(bean.getData());
                                mView.hideLoading();
                            }
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

    @Override
    public void getMyshort(String status, int page) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getMyshort(new FormBody.Builder()
                .add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("status", status)
                .add("page", String.valueOf(page))

                .add("size", "20")
                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ShortVideo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ShortVideo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ShortVideo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ShortVideo>> bean) throws Exception {
                        if (bean != null) {
                            if (bean.isSuccess()) {

                                mView.setAnchorWorks(bean.getData());
                                mView.hideLoading();
                            }
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

    @Override
    public void getUserLike(String authorid, int page) {
//View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getUserLike(new FormBody.Builder()
                .add("userid", authorid)
                .add("page", String.valueOf(page))
                .add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("size", "20")
                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ShortVideo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ShortVideo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ShortVideo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ShortVideo>> bean) throws Exception {
                        mView.setAnchorWorks(bean.getData());
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
    public void getListByUser(String authorid, int page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getListByUser(new FormBody.Builder()
                .add("authorid", authorid)
                .add("page", String.valueOf(page))
                .add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("size", "20")
                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Trend>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Trend>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Trend>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Trend>> bean) throws Exception {
                        mView.setMoment(bean.getData());
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
    public void getMyTrendList(String status, int page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getMyTrendList(new FormBody.Builder()
                .add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("page", String.valueOf(page))
                .add("status", status)
                .add("size", "20")
                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Trend>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Trend>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Trend>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Trend>> bean) throws Exception {
                        mView.setMoment(bean.getData());
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
    public void getPersonalAnchorInfo(String anchorid) {
//View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getPersonalAnchorInfo(new FormBody.Builder().add("anchorid", anchorid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid()).build())
                .compose(RxScheduler.<BaseResponse<PersonalAnchorInfo>>Flo_io_main())
                .as(mView.<BaseResponse<PersonalAnchorInfo>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<PersonalAnchorInfo>>() {
                    @Override
                    public void accept(BaseResponse<PersonalAnchorInfo> bean) throws Exception {
                        mView.setPersonalAnchorInfo(bean.getData());
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


    public void getMatchInfo(boolean timerTask, String matchid, String uid, String token) {
        //具体实现
        model.getMatchInfo(new FormBody.Builder().add("matchid", matchid).add("uid", uid)
                .add("token", token).build()).subscribeOn(Schedulers.io())
                .compose(RxScheduler.<BaseResponse<MatchList>>Flo_io_main())
                .as(mView.<BaseResponse<MatchList>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<MatchList>>() {
                    @Override
                    public void accept(BaseResponse<MatchList> bean) throws Exception {
                        mView.setMatchInfo(bean.getData());
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

    public void sendGift(String uid, String token, String anchorid, String livetid, String giftid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.sendGift(new FormBody.Builder().add("uid", uid)
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


    public void sendGift(String count, String anchorid, String livetid, String giftid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        model.sendGift(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("count", count)
                .add("anchorid", anchorid)
                .add("liveid", livetid)
                .add("giftid", giftid).build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) bean.getData();
                            int gold = (new Double(Double.valueOf(linkedTreeMap.get("gold").toString()))).intValue();

                            mView.sendSuccess(gold + "");

                        } else if (bean.getStatus() == 2) {
                            Hawk.remove("USER_REGIST");
                            MyUserInstance.getInstance().setUserInfo(null);
                            AppManager.getAppManager().startActivity(LoginActivity.class);
                            AppManager.getAppManager().finishAllActivity();
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


    public void sendGift(String count, String anchorid, String livetid, String giftid, ChatGiftBean giftbean) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        model.sendGift(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("count", count)
                .add("anchorid", anchorid)
                .add("liveid", livetid)
                .add("giftid", giftid).build()).compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            LinkedTreeMap linkedTreeMap = (LinkedTreeMap) bean.getData();
                            int gold = (new Double(Double.valueOf(linkedTreeMap.get("gold").toString()))).intValue();

                            mView.sendSuccess(gold + "", giftbean);

                        } else if (bean.getStatus() == 2) {
                            Hawk.remove("USER_REGIST");
                            MyUserInstance.getInstance().setUserInfo(null);
                            AppManager.getAppManager().startActivity(LoginActivity.class);
                            AppManager.getAppManager().finishAllActivity();
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
    public void unlockMoment(String momentid) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.unlockMoment(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getProfile().getUid())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("momentid", momentid).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.unlockMoment();
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
    public void getCollection(String page) {
        //具体实现
        model.getCollection(new FormBody.Builder().add("page", page)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Trend>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Trend>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Trend>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Trend>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setMoment(bean.getData());
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
    public void getCollectionShort(String page) {
        //具体实现
        model.getCollectionShort(new FormBody.Builder().add("page", page)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ShortVideo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ShortVideo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ShortVideo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ShortVideo>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setAnchorWorks(bean.getData());
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
    public void getAttentAnchors(int page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getAttentAnchors(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<UserRegist>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<UserRegist>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<UserRegist>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<UserRegist>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setAttentUser(bean.getData());
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
    public void getFans(int page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getFans(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<UserRegist>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<UserRegist>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<UserRegist>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<UserRegist>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setAttentUser(bean.getData());
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
    public void getLiveRoomDetail(String liveid) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getLiveRoomDetail(new FormBody.Builder().add("liveid", liveid)
                        .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                        .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                        .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<LiveRoomDetailBean>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<LiveRoomDetailBean>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<LiveRoomDetailBean>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<LiveRoomDetailBean>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getLiveRoomDetail(bean.getData());
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
    public void searchMoment(int page, String keyword) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.searchMoment(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("keyword", keyword)
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Trend>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Trend>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Trend>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Trend>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setMoment(bean.getData());
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
    public void searchAnchor(int page, String keyword) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.searchAnchor(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("keyword", keyword)
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<UserRegist>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<UserRegist>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<UserRegist>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<UserRegist>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setAttentUser(bean.getData());
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
    public void searchLive(int page, String keyword) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.searchLive(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("keyword", keyword)
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<HotLive>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<HotLive>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<HotLive>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<HotLive>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getHotLives(bean);
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
    public void searchShort(int page, String keyword) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.searchShort(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("keyword", keyword)
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ShortVideo>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ShortVideo>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ShortVideo>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ShortVideo>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getCollectionShort(bean.getData());
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
    public void withdrawlog_agent(int page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.withdrawlog_agent(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<CashOutHistory>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<CashOutHistory>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<CashOutHistory>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<CashOutHistory>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setWithdrawlog(bean.getData());
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
    public void withdrawlog(int page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.withdrawlog(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<CashOutHistory>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<CashOutHistory>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<CashOutHistory>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<CashOutHistory>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setWithdrawlog(bean.getData());
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
    public void livelog(int page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.livelog(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<AnchorHistory>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<AnchorHistory>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<AnchorHistory>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<AnchorHistory>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setLivelog(bean.getData());
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
    public void getProfitLog(int page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getProfitLog(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<ArrayList<ProfitLog>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<ProfitLog>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<ProfitLog>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<ProfitLog>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setProfitLog(bean.getData());
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
    public void getInviteList(int page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getInviteList(new FormBody.Builder().add("page", page + "")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20").build())
                .compose(RxScheduler.<BaseResponse<Invite>>Flo_io_main())
                .as(mView.<BaseResponse<Invite>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<Invite>>() {
                    @Override
                    public void accept(BaseResponse<Invite> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setInviteList(bean.getData());
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
    public void checkAttent(String uid) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.checkAttent(new FormBody.Builder().add("anchorid", uid)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build())
                .compose(RxScheduler.<BaseResponse<CheckAttend>>Flo_io_main())
                .as(mView.<BaseResponse<CheckAttend>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<CheckAttend>>() {
                    @Override
                    public void accept(BaseResponse<CheckAttend> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.checkAttent(bean.getData());
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
        model.attentAnchor(new FormBody.Builder().add("anchorid", anchorid)
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
    public void getBannedUserList(String anchorid, String page) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getBannedUserList(new FormBody.Builder()
                .add("anchorid",anchorid)
                .add("page", page)
                .add("size", "20")
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<UserRegist>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<UserRegist>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<UserRegist>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<UserRegist>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setNotalk(bean.getData());
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
    public void getMgrList() {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getMgrList(new FormBody.Builder()
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<RoomManager>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<RoomManager>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<RoomManager>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<RoomManager>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setRoomManager(bean.getData());
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
    public void getManagedRooms() {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getManagedRooms(new FormBody.Builder()
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<UserRegist>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<UserRegist>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<UserRegist>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<UserRegist>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setManagedRooms(bean.getData());
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
    public void getTopicList(String page, String keyword) {
        if (!isViewAttached()) {
            return;
        }
        //具体实现
        model.getTopicList(new FormBody.Builder()
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20")
                .add("keyword",keyword)
                .add("page", page)

                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Topic>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Topic>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Topic>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Topic>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getTopicList(bean.getData());
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
    public void videoListInTopic(String topic, String type,String page) {
        if (!isViewAttached()) {
            return;
        }
            model.videoListInTopic(new FormBody.Builder()
                    .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                    .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                    .add("page", page)
                    .add("size", "20")
                    .add("topic",topic)
                    .add("type", type)
                    .build())
                    .compose(RxScheduler.<BaseResponse<ArrayList<ShortVideo>>>Flo_io_main())
                    .as(mView.<BaseResponse<ArrayList<ShortVideo>>>bindAutoDispose())
                    .subscribe(new Consumer<BaseResponse<ArrayList<ShortVideo>>>() {
                        @Override
                        public void accept(BaseResponse<ArrayList<ShortVideo>> bean) throws Exception {
                            if (bean.isSuccess()) {
                                mView.setAnchorWorks(bean.getData());
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
    public void momentListInTopic(String topic, String type,String page) {
        if (!isViewAttached()) {
            return;
        }
        model.momentListInTopic(new FormBody.Builder()
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("size", "20")
                .add("topic",topic)
                .add("page", page)
                .add("type", type)
                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Trend>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Trend>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Trend>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Trend>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.setMoment(bean.getData());
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
    public void getTopicInfo(String topic) {
        if (!isViewAttached()) {
            return;
        }
        model.getTopicInfo(new FormBody.Builder()
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("topic",topic)

                .build())
                .compose(RxScheduler.<BaseResponse<Topic>>Flo_io_main())
                .as(mView.<BaseResponse<Topic>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<Topic>>() {
                    @Override
                    public void accept(BaseResponse<Topic> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getTopicInfo(bean.getData());
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
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("page",page)
                .add("size","20")
                .add("anchorid",anchorid)

                .build())
                .compose(RxScheduler.<BaseResponse<ArrayList<Guardian>>>Flo_io_main())
                .as(mView.<BaseResponse<ArrayList<Guardian>>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<ArrayList<Guardian>>>() {
                    @Override
                    public void accept(BaseResponse<ArrayList<Guardian>> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.getGuardianList(bean);
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
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .add("anchorid",anchorid)
                .add("type",type)
                .add("renew",renew)

                .build())
                .compose(RxScheduler.<BaseResponse<BuyGuard>>Flo_io_main())
                .as(mView.<BaseResponse<BuyGuard>>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<BuyGuard>>() {
                    @Override
                    public void accept(BaseResponse<BuyGuard> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.buyGuard(bean);
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
                        mView.getTempKeysForCos(bean.getData());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.onError(throwable);

                    }
                });
    }

    @Override
    public void bindAgent(String invite_code) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        //具体实现

        model.bindAgent(new FormBody.Builder()
                .add("platform", "2")
                .add("invite_code", invite_code)
                .add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .add("token", MyUserInstance.getInstance().getUserinfo().getToken()).build())
                .compose(RxScheduler.<BaseResponse>Flo_io_main())
                .as(mView.<BaseResponse>bindAutoDispose())
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse bean) throws Exception {
                      if (bean.isSuccess()) {
                            mView.bindAgent(bean);
                        }else{
                          mView.onError(new Throwable(bean.getMsg()));
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
    public void getListOfSwiftMessage() {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        model.getListOfSwiftMessage(new FormBody.Builder().add("uid", MyUserInstance.getInstance().getUserinfo().getId())
                        .add("token", MyUserInstance.getInstance().getUserinfo().getToken())
                        .build()).compose(RxScheduler.<BaseResponse<SwiftMessageBean>>Flo_io_main())
                .as(mView.<BaseResponse<SwiftMessageBean>> bindAutoDispose())
                .subscribe(new Consumer<BaseResponse<SwiftMessageBean>>() {
                    @Override
                    public void accept(BaseResponse<SwiftMessageBean> bean) throws Exception {
                        if (bean.isSuccess()) {
                            mView.swiftMessage(bean);
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
