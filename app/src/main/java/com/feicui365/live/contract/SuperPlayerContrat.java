package com.feicui365.live.contract;

import android.support.annotation.NonNull;

import com.feicui365.live.base.BaseView;
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
import com.feicui365.live.shop.entity.Good;

import java.util.ArrayList;
import io.reactivex.Flowable;
import okhttp3.FormBody;

public interface SuperPlayerContrat {

    interface Model {
        Flowable<BaseResponse<ArrayList<GiftInfo>>> getGiftList();
        Flowable<BaseResponse> sendGift(FormBody body);
        Flowable<BaseResponse> timeBilling(FormBody body);
        Flowable<BaseResponse<UserRegist>> getAnchorInfo(FormBody body);
        Flowable<BaseResponse<ArrayList<ContributeRank>>> getContributeRank(FormBody body);

        Flowable<BaseResponse<LinkInfo>> requestMlvbLink(FormBody body);
        Flowable<BaseResponse> stopMlvbLink(FormBody body);
        Flowable<BaseResponse> refuseMlvbLink(FormBody body);
        Flowable<BaseResponse<LinkInfo>> acceptMlvbLink(FormBody body);

        Flowable<BaseResponse<IsMgr>> checkIsMgr(FormBody body);
        Flowable<BaseResponse<CheckAttend>> checkAttent(FormBody body);

        Flowable<BaseResponse<ArrayList<Good>>> getGoodsList(FormBody body);
        Flowable<BaseResponse<UserRegist>> getUserBasicInfo(FormBody body);
        Flowable<BaseResponse<GuardianInfo>> getGuardInfo(FormBody body);
        Flowable<BaseResponse<Count>> getGuardianCount(FormBody body);
        Flowable<BaseResponse<LiveInfo>> getLiveInfo(FormBody body);
        Flowable<BaseResponse<LiveInfo>> getAnchorLiveInfo(FormBody body);
        Flowable<BaseResponse<BaseLiveInfo>> getLiveBasicInfo(FormBody body);
    }

    interface View extends BaseView {
        @Override
        void showLoading();

        @Override
        void hideLoading();

        @Override
        void onError(Throwable throwable);

        default void setGiftList(ArrayList<GiftInfo> giftList){

        }

        default void sendSuccess(String gold){

        }


        default void timeBilling(BaseResponse baseResponse){

        }
        default void setAnchorInfo(BaseResponse<UserRegist> anchorInfo){

        }

        default void setContributeRank(BaseResponse<ArrayList<ContributeRank>> contributeRank){

        }

        default void showMessage(@NonNull String message) {

        }

        default void requestMlvbLink(LinkInfo baseResponse){

        }

        default void stopMlvbLink(BaseResponse baseResponse){

        }


        default void refuseMlvbLink(BaseResponse baseResponse){

        }

        default void acceptMlvbLink(LinkInfo baseResponse){

        }

        default void checkIsMgr(IsMgr baseResponse){

        }
        default void checkAttent(CheckAttend baseResponse){

        }
        default void getGoodsList(BaseResponse<ArrayList<Good>> baseResponse){

        }
        default void getUserBasicInfo(BaseResponse<UserRegist> baseResponse){

        }
        default void getGuardInfo(BaseResponse<GuardianInfo> baseResponse){

        }
        default void getGuardianCount(Count baseResponse){

        }
        default void getLiveInfo(BaseResponse<LiveInfo> baseResponse){

        }
        default void getLiveBasicInfo(BaseResponse<BaseLiveInfo> baseResponse){

        }


    }

    interface Presenter {
        void requestMlvbLink(String anchorid,String mlvb_token);
        void stopMlvbLink(String anchorid,String linkerid);
        void refuseMlvbLink(String userid);
        void acceptMlvbLink(String userid);

        void checkIsMgr(String anchorid);
        void checkAttent(String anchorid);
        void getGoodsList(String anchorid);
        void getUserBasicInfo(String id);
        void getGuardInfo(String anchorid);
        void getGuardianCount(String anchorid);
        void getLiveInfo(String liveid);
        void getAnchorLiveInfo(String anchorid);
        void getLiveBasicInfo(String liveid);
    }


}
