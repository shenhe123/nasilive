package com.feicui365.live.contract;

import com.feicui365.live.base.BaseView;
import com.feicui365.live.model.entity.AnchorHistory;
import com.feicui365.live.model.entity.BuyGuard;
import com.feicui365.live.model.entity.CashOutHistory;
import com.feicui365.live.model.entity.CheckAttend;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.Guardian;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.Invite;
import com.feicui365.live.model.entity.LiveRoomDetailBean;
import com.feicui365.live.model.entity.ProfitLog;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.model.entity.RoomManager;
import com.feicui365.live.model.entity.Banners;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.ChatGiftBean;
import com.feicui365.live.model.entity.Comment;
import com.feicui365.live.model.entity.HomeAd;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.MatchList;
import com.feicui365.live.model.entity.MomentDetail;
import com.feicui365.live.model.entity.PersonalAnchorInfo;
import com.feicui365.live.model.entity.ShortVideo;
import com.feicui365.live.model.entity.SwiftMessageBean;
import com.feicui365.live.model.entity.Topic;
import com.feicui365.live.model.entity.Trend;
import com.feicui365.live.model.entity.UserInfo;
import com.feicui365.live.model.entity.UserRegist;


import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;

public interface HomeContract {
    interface Model {
        Flowable<BaseResponse<ArrayList<Banners>>> getHomeScrollAd();

        Flowable<BaseResponse<ArrayList<HotLive>>> getHotLives(RequestBody body);

        Flowable<BaseResponse<ArrayList<HotLive>>> getLivesByCategory(RequestBody body);

        Flowable<BaseResponse<ArrayList<ShortVideo>>> getRandomList(RequestBody body);

        Flowable<BaseResponse<ArrayList<Comment>>> getComments(RequestBody body);

        Flowable<BaseResponse<MatchList>> getMatchInfo(FormBody body);

        Flowable<BaseResponse<ArrayList<Trend>>> getMomentAttent(FormBody body);

        Flowable<BaseResponse<ArrayList<Trend>>> getMomentHot(FormBody body);

        Flowable<BaseResponse<ArrayList<MomentDetail>>> getMomentDetail(FormBody body);

        Flowable<BaseResponse<ArrayList<MomentDetail>>> getMomentCommentReplys(FormBody body);

        Flowable<BaseResponse<UserInfo>> getShortUserInfo(FormBody body);

        Flowable<BaseResponse<UserRegist>> getUserInfo(FormBody body);

        Flowable<BaseResponse<ArrayList<ShortVideo>>> getAnchorWorks(FormBody body);

        Flowable<BaseResponse<ArrayList<ShortVideo>>> getMyshort(FormBody body);

        Flowable<BaseResponse<ArrayList<ShortVideo>>> getUserLike(FormBody body);

        Flowable<BaseResponse<ArrayList<Trend>>> getListByUser(FormBody body);

        Flowable<BaseResponse<ArrayList<Trend>>> getMyTrendList(FormBody body);

        Flowable<BaseResponse<PersonalAnchorInfo>> getPersonalAnchorInfo(FormBody body);

        Flowable<BaseResponse<ArrayList<GiftInfo>>> getGiftList();

        Flowable<BaseResponse> sendGift(FormBody body);

        Flowable<BaseResponse> unlockMoment(FormBody body);

        Flowable<BaseResponse> collectMoment(FormBody body);

        Flowable<BaseResponse> likeMoment(FormBody body);

        Flowable<BaseResponse> likeComment(FormBody body);

        Flowable<BaseResponse<ArrayList<Trend>>> getCollection(FormBody body);

        Flowable<BaseResponse<HomeAd>> getHomePopAd();

        Flowable<BaseResponse<ArrayList<ShortVideo>>> getCollectionShort(FormBody body);


        Flowable<BaseResponse<ArrayList<UserRegist>>> getAttentAnchors(FormBody body);

        Flowable<BaseResponse<ArrayList<UserRegist>>> getFans(FormBody body);

        Flowable<BaseResponse<ArrayList<LiveRoomDetailBean>>> getLiveRoomDetail(FormBody body);

        Flowable<BaseResponse<ArrayList<Trend>>> searchMoment(FormBody body);

        Flowable<BaseResponse<ArrayList<UserRegist>>> searchAnchor(FormBody body);

        Flowable<BaseResponse<ArrayList<HotLive>>> searchLive(FormBody body);

        Flowable<BaseResponse<ArrayList<ShortVideo>>> searchShort(FormBody body);

        Flowable<BaseResponse<ArrayList<CashOutHistory>>> withdrawlog_agent(FormBody body);

        Flowable<BaseResponse<ArrayList<CashOutHistory>>> withdrawlog(FormBody body);

        Flowable<BaseResponse<ArrayList<AnchorHistory>>> livelog(FormBody body);



        Flowable<BaseResponse<ArrayList<ProfitLog>>> getProfitLog(FormBody body);

        Flowable<BaseResponse<Invite>> getInviteList(FormBody body);
        Flowable<BaseResponse<CheckAttend>> checkAttent(FormBody body);

        Flowable<BaseResponse> attentAnchor(FormBody body);

        Flowable<BaseResponse<ArrayList<UserRegist>>> getBannedUserList(FormBody body);
        Flowable<BaseResponse<ArrayList<RoomManager>>> getMgrList(FormBody body);

        Flowable<BaseResponse<ArrayList<UserRegist>>> getManagedRooms(FormBody body);

        Flowable<BaseResponse<ArrayList<Topic>>> getTopicList(FormBody body);

        Flowable<BaseResponse<ArrayList<ShortVideo>>> videoListInTopic(FormBody body);

        Flowable<BaseResponse<ArrayList<Trend>>> momentListInTopic(FormBody body);

        Flowable<BaseResponse<Topic>> getTopicInfo(FormBody body);
        Flowable<BaseResponse<ArrayList<Guardian>>> getGuardianList(FormBody body);
        Flowable<BaseResponse<BuyGuard>> buyGuard(FormBody body);

        Flowable<BaseResponse<GuardianInfo>> getGuardInfo(FormBody body);

        Flowable<BaseResponse<QCloudData>> getTempKeysForCos(FormBody body);


        Flowable<BaseResponse> bindAgent(@Body FormBody body);

        Flowable<BaseResponse<SwiftMessageBean>> getListOfSwiftMessage(FormBody body);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        default void getHomePopAd(BaseResponse<HomeAd> bean) {
        }

        default void getHomeScrollAd(BaseResponse<ArrayList<Banners>> bean) {
        }

        default void getHotLives(BaseResponse<ArrayList<HotLive>> bean) {
        }

        default void getLivesByCategory(BaseResponse<ArrayList<HotLive>> bean) {
        }

        default void getRandomList(BaseResponse<ArrayList<ShortVideo>> bean) {
        }

        default void getComments(BaseResponse<ArrayList<Comment>> bean) {
        }

        default void setMatchInfo(MatchList bean) {
        }

        default void setMoment(ArrayList<Trend> bean) {
        }

        default void setMomentDetail(ArrayList<MomentDetail> bean) {
        }

        default void setMomentCommentReplys(ArrayList<MomentDetail> bean) {
        }

        default void setShortUserInfo(UserInfo bean) {
        }

        default void setUserInfo(UserRegist bean) {
        }



        default void setAnchorWorks(ArrayList<ShortVideo> bean) {
        }

        default void setMyshort(ArrayList<ShortVideo> bean) {
        }

        default void setUserLike(ArrayList<ShortVideo> bean) {
        }

        default void setListByUser(ArrayList<Trend> bean) {
        }

        default void setPersonalAnchorInfo(PersonalAnchorInfo bean) {
        }

        default void setGiftList(ArrayList<GiftInfo> bean) {
        }

        default void sendGiftSuccess() {
        }

        default void unlockMoment() {
        }

        default void showMgs(String msg) {
        }

        default void getCollection(ArrayList<Trend> bean) {
        }

        default void getCollectionShort(ArrayList<ShortVideo> bean) {
        }

        default void sendSuccess(String gold, ChatGiftBean bean) {
        }

        default void sendSuccess(String gold) {
        }

        default void collectMoment(BaseResponse response) {
        }

        default void likeMoment(BaseResponse response) {
        }

        default void likeComment(BaseResponse response) {
        }

        default void setAttentUser(ArrayList<UserRegist> bean) {
        }

        default void setWithdrawlog(ArrayList<CashOutHistory> bean) {
        }

        default void setLivelog(ArrayList<AnchorHistory> bean) {
        }

        default void setProfitLog(ArrayList<ProfitLog> bean) {
        }

        default void setInviteList(Invite bean) {
        }

        default void checkAttent(CheckAttend response) {
        }
        default void attentAnchor(BaseResponse response) {
        }

        default void setManagedRooms(ArrayList<UserRegist> bean) {
        }

        default void setNotalk(ArrayList<UserRegist> bean) {
        }
        default void setRoomManager(ArrayList<RoomManager> bean) {
        }
        default void getTopicList(ArrayList<Topic> bean) {
        }

        default void getLiveRoomDetail(ArrayList<LiveRoomDetailBean> beans){

        }

        default void videoListInTopic(ArrayList<ShortVideo> bean){

        }

        default void momentListInTopic(ArrayList<Trend> bean){

        }

        default void getTopicInfo(Topic bean){

        }
        default void getGuardianList(BaseResponse<ArrayList<Guardian>> response){

        }
        default void buyGuard(BaseResponse<BuyGuard> response){

        }

        default void getGuardInfo(BaseResponse<GuardianInfo> baseResponse){

        }

        default void getTempKeysForCos(QCloudData data) {

        }

        default void bindAgent(BaseResponse response){

        }

        default void swiftMessage(BaseResponse<SwiftMessageBean> bean) {

        }

    }

    interface Presenter {
        /**
         * 获取用户配置信息
         */
        void getHomeScrollAd();

        void getHomePopAd();

        void getHotLives(String page);

        void getLivesByCategory(String page, String categoryid);

        void getMomentAttent(int page);

        void getMomentHot(int page);

        void getMomentDetail(String uid, String token, String momentid, String lastid);

        void getMomentCommentReplys(String uid, String token, String commentid, String lastid, String size);

        void getRandomList(String page);

        void getComments(String lastid, String videoid);

        void getShortUserInfo(String authorid);

        void getUserInfo();

        void collectMoment(String momentid, String type);

        void likeMoment(String momentid);

        void likeComment(String momentid);

        void getAnchorWorks(String authorid, int page);

        void getMyshort(String status, int page);

        void getUserLike(String authorid, int page);

        void getListByUser(String authorid, int page);

        void getMyTrendList(String status, int page);

        void getPersonalAnchorInfo(String authorid);

        void getGiftList();

        void sendGift(String uid, String token, String anchorid, String livetid, String giftid);

        void unlockMoment(String momentid);

        void getCollection(String page);

        void getCollectionShort(String page);

        void getAttentAnchors(int page);

        void getFans(int page);

        void getLiveRoomDetail(String liveid);

        void searchMoment(int page, String keyword);

        void searchAnchor(int page, String keyword);

        void searchLive(int page, String keyword);

        void searchShort(int page, String keyword);

        void withdrawlog_agent(int page);

        void withdrawlog(int page);

        void livelog(int page);

        void getProfitLog(int page);

        void getInviteList(int page);

         void checkAttent(String uid) ;
        void attentAnchor(String anchorid,String type) ;

        void getBannedUserList(String anchorid,String page);
        void getMgrList();
        void getManagedRooms();
        void getTopicList(String page,String keyword);
        void videoListInTopic(String topic,String type,String page);
        void momentListInTopic(String topic,String type,String page);
        void getTopicInfo(String topic);
        void getGuardianList(String anchorid,String page);
        void buyGuard(String anchorid,String type,String renew);
        void getGuardInfo(String anchorid);
        void getTempKeysForCos();

         void bindAgent(String invite_code);

         void getListOfSwiftMessage();
    }
}
