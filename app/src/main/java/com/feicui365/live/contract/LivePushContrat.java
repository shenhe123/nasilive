package com.feicui365.live.contract;

import com.feicui365.live.base.BaseView;
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
import com.feicui365.live.shop.entity.Good;


import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;
import retrofit2.http.Body;

public interface LivePushContrat {
    interface Model {
        Flowable<BaseResponse<QCloudData>> getTempKeysForCos(FormBody body);
        Flowable<BaseResponse<HotLive>> startLive(FormBody body);
        Flowable<BaseResponse<ArrayList<GiftInfo>>> getGiftList();
        Flowable<BaseResponse> sendGift(FormBody body);
        Flowable<BaseResponse<ArrayList<ContributeRank>>> getContributeRank(FormBody body);
        Flowable<BaseResponse<EndLiveInfo>> endlive(FormBody body);
        Flowable<BaseResponse> publish(FormBody body);

        Flowable<BaseResponse> setLinkOnOff(FormBody body);

        Flowable<BaseResponse<LinkInfo>> requestMlvbLink(FormBody body);
        Flowable<BaseResponse> stopMlvbLink(FormBody body);
        Flowable<BaseResponse> refuseMlvbLink(FormBody body);
        Flowable<BaseResponse<LinkInfo>> acceptMlvbLink(FormBody body);
        Flowable<BaseResponse> mergeStream(FormBody body);

        Flowable<BaseResponse> enterPkMode(FormBody body);
        Flowable<BaseResponse<UserRegist>> getUserBasicInfo(FormBody body);
        Flowable<BaseResponse> endPk(FormBody body);
        Flowable<BaseResponse<GuardianInfo>> getGuardInfo(FormBody body);
        Flowable<BaseResponse<Count>> getGuardianCount(FormBody body);
        Flowable<BaseResponse<BaseLiveInfo>> getLiveBasicInfo(FormBody body);

        Flowable<BaseResponse<UserRegist>> getUserInfoById(@Body FormBody body);
        Flowable<BaseResponse<CheckAttend>> checkAttent(@Body FormBody body);
        Flowable<BaseResponse> attentAnchor(FormBody body);
        Flowable<BaseResponse<LiveInfo>> getAnchorLiveInfo(FormBody body);
        Flowable<BaseResponse> setRoomMgr(@Body FormBody body);
        Flowable<BaseResponse<ArrayList<UserRegist>>> getBannedUserList(@Body FormBody body);
        Flowable<BaseResponse<AllUser>> getAllUserList(@Body FormBody body);

        Flowable<BaseResponse<BanStatus>> banUser(@Body FormBody body);
        Flowable<BaseResponse<ArrayList<RoomManager>>> getMgrList(@Body FormBody body);
        Flowable<BaseResponse<ArrayList<Guardian>>> getGuardianList(FormBody body);
        Flowable<BaseResponse<BuyGuard>> buyGuard(FormBody body);
        Flowable<BaseResponse<UserRegist>> getUserInfo(FormBody body);

        Flowable<BaseResponse<HotLive>> mlvbStartLive(@Body FormBody body);
        Flowable<BaseResponse<LiveHistoryBean>> getLiveHistoryTitle(@Body FormBody body);

        Flowable<BaseResponse> sendGift2(@Body FormBody body);
        Flowable<BaseResponse<LiveConsume>> liveChosume(@Body FormBody build);
        Flowable<BaseResponse<LivePoster>>  livePoster(@Body FormBody build);
        Flowable<BaseResponse<IsMgr>> checkIsMgr2(@Body FormBody body);

        Flowable<BaseResponse<ArrayList<Good>>> getGoodsList(FormBody body);
        Flowable<BaseResponse<ArrayList<LiveAudienceListBean>>> getLiveViewerList(FormBody body);



    }
    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        default void setTempKeysForCos(QCloudData data){

        }
        default void startSuccess(HotLive data){

        }
        default void setGiftList(ArrayList<GiftInfo> giftList){

        }

        default void sendGiftSuccess(){

        }
        default void setContributeRank(BaseResponse<ArrayList<ContributeRank>> contributeRank){

        }

        default void endlive(EndLiveInfo baseResponse){

        }
        default void publish(){}
        default void showMgs(String msg){}


        default void requestMlvbLink(LinkInfo baseResponse){

        }

        default void stopMlvbLink(BaseResponse baseResponse){

        }


        default void refuseMlvbLink(BaseResponse baseResponse){

        }

        default void acceptMlvbLink(LinkInfo baseResponse){

        }
        default void setLinkOnOff(BaseResponse baseResponse){

        }
        default void mergeStream(BaseResponse baseResponse){

        }
        default void enterPkMode(BaseResponse baseResponse){

        }
        default void endPk(BaseResponse baseResponse){

        }
        default void getUserBasicInfo(BaseResponse<UserRegist> baseResponse){

        }
        default void getGuardInfo(GuardianInfo baseResponse){

        }
        default void getGuardianCount(Count baseResponse){

        }
        default void getLiveBasicInfo(BaseResponse<BaseLiveInfo> baseResponse){

        }
        default void getUserInfoById(UserRegist bean, int type) {

        }

        default void checkAttent(CheckAttend response, int type) {
        }

        default void attentAnchor(BaseResponse response) {
        }

        default void getAnchorLiveInfo(LiveInfo response) {
        }

        default void setRoomMgr(BaseResponse bean) {

        }

        default void getBannedUserList(ArrayList<UserRegist> bean) {

        }


        default void getAllUserList(AllUser bean) {

        }

        default void  banUser(String userid,BanStatus bean) {

        }

        default void getMgrList(ArrayList<RoomManager> bean) {

        }

        default void getGuardianList(ArrayList<Guardian> response) {

        }

        default void buyGuard(BuyGuard response) {

        }

        default void getUserInfo(UserRegist response) {

        }

        default void mlvbStartLive(HotLive bean) {

        }

        default void getLiveHistoryTitle (LiveHistoryBean bean) {

        }

        default void sendGift2(BaseResponse bean) {

        }

        default void getGiftList(ArrayList<GiftInfo> bean) {

        }



        default void liveChosume(LiveConsume bean) {

        }

        default void LivePoster(LivePoster bean) {

        }

        default void checkIsMgr2(IsMgr bean) {

        }
        default void getGoodsList(ArrayList<Good> bean){

        }

        default void getLiveViewerList (ArrayList<LiveAudienceListBean> bean) {

        }

    }

    interface Presenter {
        void getTempKeysForCos();
        void startLive( String mlvb_token,String cateid, String thumb, String title, String orientation,String room_type,String price,String pwd);
        void getGiftList();
        void sendGift(String uid,String token,String anchorid,String livetid,String giftid);
        void getContributeRank(String liveid);
        void endlive(String liveid);
        void publish(String type,String title,String image_url,String blur_image_url,String video_url,String single_display_type,String unlock_price,String topic);
        void requestMlvbLink(String anchorid);
        void stopMlvbLink(String anchorid,String linkerid);
        void refuseMlvbLink(String userid);
        void acceptMlvbLink(String userid);
        void setLinkOnOff(String type);
        void mergeStream(String linkerid);
        void enterPkMode();
        void getUserBasicInfo(String id);
        void endPk();
        void getGuardInfo(String anchorid);
        void getGuardianCount(String anchorid);
        void getLiveBasicInfo(String liveid);

        void getUserInfoById(String id, int type);

        void checkAttent(String uid, int type);

        void attentAnchor(String anchorid, String type);

        void getAnchorLiveInfo(String anchorid);

        void setRoomMgr(String mgrid, int type);

        void getBannedUserList(String anchorid, int page);

        void getAllUserList();

        void banUser(String anchorid, String userid, int type,String ban_type);

        void getMgrList();

        void getGuardianList(String anchorid, String page);

        void buyGuard(String anchorid, String type, String renew);

        void getUserInfo();

        void mlvbStartLive(String cateid, String thumb, String title, String room_type, String price, String pwd,int is_hide_name);

        void sendGift2(String anchorid, String giftid, String liveid, int count, String pkid, String pkto);


        void liveChosume(String liveid);

        void checkIsMgr2(String anchorid);

        void getGoodsList(String anchorid);

        void getLivePoster(String liveid);

        void getLiveViewerList(String liveid);

        void getLiveHistoryTitle();

    }
}
