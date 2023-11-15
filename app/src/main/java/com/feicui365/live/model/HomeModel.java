package com.feicui365.live.model;

import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.model.entity.AnchorHistory;
import com.feicui365.live.model.entity.Banners;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.BuyGuard;
import com.feicui365.live.model.entity.CashOutHistory;
import com.feicui365.live.model.entity.CheckAttend;
import com.feicui365.live.model.entity.Comment;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.Guardian;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.HomeAd;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.Invite;
import com.feicui365.live.model.entity.LiveRoomDetailBean;
import com.feicui365.live.model.entity.MatchList;
import com.feicui365.live.model.entity.MomentDetail;
import com.feicui365.live.model.entity.PersonalAnchorInfo;
import com.feicui365.live.model.entity.ProfitLog;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.model.entity.RoomManager;
import com.feicui365.live.model.entity.ShortVideo;
import com.feicui365.live.model.entity.SwiftMessageBean;
import com.feicui365.live.model.entity.Topic;
import com.feicui365.live.model.entity.Trend;
import com.feicui365.live.model.entity.UserInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.net.RetrofitClient;


import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class HomeModel implements HomeContract.Model {
    @Override
    public Flowable<BaseResponse<ArrayList<Banners>>> getHomeScrollAd() {
        return RetrofitClient.getInstance().getApi().getHomeScrollAd();
    }

    @Override
    public Flowable<BaseResponse<ArrayList<HotLive>>> getHotLives(RequestBody body) {
        return RetrofitClient.getInstance().getApi().getHotLives(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<HotLive>>> getLivesByCategory(RequestBody body) {
        return  RetrofitClient.getInstance().getApi().getLivesByCategory(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<ShortVideo>>> getRandomList(RequestBody body) {
        return RetrofitClient.getInstance().getApi().getRandomList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Comment>>> getComments(RequestBody body) {
        return RetrofitClient.getInstance().getApi().getComments(body);
    }

    @Override
    public Flowable<BaseResponse<MatchList>> getMatchInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getMatchInfo(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Trend>>> getMomentAttent(FormBody body) {
        return RetrofitClient.getInstance().getApi().getMomentAttent(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Trend>>> getMomentHot(FormBody body) {
        return RetrofitClient.getInstance().getApi().getMomentHot(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<MomentDetail>>> getMomentDetail(FormBody body) {
        return RetrofitClient.getInstance().getApi().getMomentDetail(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<MomentDetail>>> getMomentCommentReplys(FormBody body) {
        return RetrofitClient.getInstance().getApi().getMomentCommentReplys(body);
    }


    @Override
    public Flowable<BaseResponse<UserInfo>> getShortUserInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getShortUserInfo(body);
    }

    @Override
    public Flowable<BaseResponse<UserRegist>> getUserInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getUserInfo(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<ShortVideo>>> getAnchorWorks(FormBody body) {
        return RetrofitClient.getInstance().getApi().getAnchorWorks(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<ShortVideo>>> getMyshort(FormBody body) {

            return RetrofitClient.getInstance().getApi().getMyVideo(body);

    }

    @Override
    public Flowable<BaseResponse<ArrayList<ShortVideo>>> getUserLike(FormBody body) {
        return RetrofitClient.getInstance().getApi().getUserLike(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Trend>>> getListByUser(FormBody body) {
        return RetrofitClient.getInstance().getApi().getListByUser(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Trend>>> getMyTrendList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getMyTrendList(body);
    }

    @Override
    public Flowable<BaseResponse<PersonalAnchorInfo>> getPersonalAnchorInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getPersonalAnchorInfo(body);
    }
    @Override
    public Flowable<BaseResponse<ArrayList<GiftInfo>>> getGiftList() {
        return RetrofitClient.getInstance().getApi().getGiftList();
    }

    @Override
    public Flowable<BaseResponse> sendGift(FormBody body) {
        return RetrofitClient.getInstance().getApi().sendGift(body);
    }

    @Override
    public Flowable<BaseResponse> unlockMoment(FormBody body) {
        return RetrofitClient.getInstance().getApi().unlockMoment(body);
    }

    @Override
    public Flowable<BaseResponse> collectMoment(FormBody body) {
        return RetrofitClient.getInstance().getApi().collectMoment(body);
    }

    @Override
    public Flowable<BaseResponse> likeMoment(FormBody body) {
        return RetrofitClient.getInstance().getApi().likeMoment(body);
    }

    @Override
    public Flowable<BaseResponse> likeComment(FormBody body) {
        return RetrofitClient.getInstance().getApi().likeComment(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Trend>>> getCollection(FormBody body) {
        return RetrofitClient.getInstance().getApi().getCollection(body);
    }

    @Override
    public Flowable<BaseResponse<HomeAd>> getHomePopAd() {
        return RetrofitClient.getInstance().getApi().getHomePopAd();
    }


    @Override
    public Flowable<BaseResponse<ArrayList<ShortVideo>>> getCollectionShort(FormBody body) {
        return RetrofitClient.getInstance().getApi().getCollectionShort(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<UserRegist>>> getAttentAnchors(FormBody body) {
        return RetrofitClient.getInstance().getApi().getAttentAnchors(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<UserRegist>>> getFans(FormBody body) {
        return RetrofitClient.getInstance().getApi().getFans(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<LiveRoomDetailBean>>> getLiveRoomDetail(FormBody body) {
        return RetrofitClient.getInstance().getApi().getLiveRoomDetail(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Trend>>> searchMoment(FormBody body) {
        return RetrofitClient.getInstance().getApi().searchMoment(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<UserRegist>>> searchAnchor(FormBody body) {
        return RetrofitClient.getInstance().getApi().searchAnchor(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<HotLive>>> searchLive(FormBody body) {
        return RetrofitClient.getInstance().getApi().searchLive(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<ShortVideo>>> searchShort(FormBody body) {
        return RetrofitClient.getInstance().getApi().searchShort(body);
    }


    @Override
    public Flowable<BaseResponse<ArrayList<CashOutHistory>>> withdrawlog_agent(FormBody body) {
        return RetrofitClient.getInstance().getApi().withdrawlog_agent(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<CashOutHistory>>> withdrawlog(FormBody body) {
        return RetrofitClient.getInstance().getApi().withdrawlog(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<AnchorHistory>>> livelog(FormBody body) {
        return RetrofitClient.getInstance().getApi().livelog(body);
    }
    @Override
    public Flowable<BaseResponse<ArrayList<ProfitLog>>> getProfitLog(FormBody body) {
        return RetrofitClient.getInstance().getApi().getProfitLog(body);
    }



    @Override
    public Flowable<BaseResponse<Invite>> getInviteList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getInviteList(body);
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
    public Flowable<BaseResponse<ArrayList<UserRegist>>> getBannedUserList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getBannedUserList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<RoomManager>>> getMgrList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getMgrList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<UserRegist>>> getManagedRooms(FormBody body) {
        return RetrofitClient.getInstance().getApi().getManagedRooms(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Topic>>> getTopicList(FormBody body) {
        return RetrofitClient.getInstance().getApi().getTopicList(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<ShortVideo>>> videoListInTopic(FormBody body) {
        return RetrofitClient.getInstance().getApi().videoListInTopic(body);
    }

    @Override
    public Flowable<BaseResponse<ArrayList<Trend>>> momentListInTopic(FormBody body) {
        return RetrofitClient.getInstance().getApi().momentListInTopic(body);
    }

    @Override
    public Flowable<BaseResponse<Topic>> getTopicInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getTopicInfo(body);
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
    public Flowable<BaseResponse<GuardianInfo>> getGuardInfo(FormBody body) {
        return RetrofitClient.getInstance().getApi().getGuardInfo(body);
    }

    @Override
    public Flowable<BaseResponse<QCloudData>> getTempKeysForCos(FormBody body) {
        return RetrofitClient.getInstance().getApi().getTempKeysForCos(body);
    }

    @Override
    public Flowable<BaseResponse> bindAgent(FormBody body) {
        return RetrofitClient.getInstance().getApi().bindAgent(body);
    }

    @Override
    public Flowable<BaseResponse<SwiftMessageBean>> getListOfSwiftMessage(FormBody body) {
        return RetrofitClient.getInstance().getApi().getListOfSwiftMessage(body);
    }
/*
    @Override
    public Flowable<BaseResponse<ArrayList<CashOutHistory>>> SellerwithdrawLog(FormBody body) {
        return RetrofitClient.getInstance().getApi().SellerwithdrawLog(body);
    }*/
}
