package com.feicui365.live.net;


import com.feicui365.live.live.bean.EndLiveInfo;
import com.feicui365.live.live.bean.IsMgr;
import com.feicui365.live.live.bean.LinkInfo;
import com.feicui365.live.model.entity.AllUser;
import com.feicui365.live.model.entity.AnchorHistory;
import com.feicui365.live.model.entity.BanStatus;
import com.feicui365.live.model.entity.Banners;
import com.feicui365.live.model.entity.BaseLiveInfo;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.BuyGuard;
import com.feicui365.live.model.entity.CashOutHistory;
import com.feicui365.live.model.entity.CheckAttend;
import com.feicui365.live.model.entity.CodeMsg;
import com.feicui365.live.model.entity.Comment;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.model.entity.Count;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.Guardian;
import com.feicui365.live.model.entity.GuardianInfo;
import com.feicui365.live.model.entity.HomeAd;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.Invite;

import com.feicui365.live.model.entity.LiveAudienceListBean;
import com.feicui365.live.model.entity.LiveConsume;
import com.feicui365.live.model.entity.LiveHistoryBean;
import com.feicui365.live.model.entity.LiveInfo;
import com.feicui365.live.model.entity.LivePoster;
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
import com.feicui365.live.model.entity.UserConfig;
import com.feicui365.live.model.entity.UserInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.shop.entity.Address;
import com.feicui365.live.shop.entity.CartGoodInfo;
import com.feicui365.live.shop.entity.ClassIfy;
import com.feicui365.live.shop.entity.EvaluateList;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.shop.entity.GoodInfo;
import com.feicui365.live.shop.entity.GoodManager;
import com.feicui365.live.shop.entity.LogisticsInfo;
import com.feicui365.live.shop.entity.OrderDetails;
import com.feicui365.live.shop.entity.OrderGoods;
import com.feicui365.live.shop.entity.SellerCashOut;
import com.feicui365.live.shop.entity.SellerOrderInfo;
import com.feicui365.live.shop.entity.SellerWalletInfo;
import com.feicui365.live.shop.entity.UserOrderInfo;
import com.feicui365.live.shop.entity.RefundInfo;
import com.feicui365.live.shop.entity.RefundOrderGoods;
import com.feicui365.live.shop.entity.ShopGoodList;
import com.feicui365.live.shop.entity.ShopSellerInfo;
import com.feicui365.live.shop.entity.ShopUserInfo;
import com.feicui365.live.util.MyUserInstance;


import java.util.ArrayList;

import io.reactivex.Flowable;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public interface APIService {

    /**
     * 登陆
     *
     * @return
     */
     String baseUrl = "https://api.365feicui.com/";
    //   String baseUrl = "https://api.zhangduankeji.com/";

    String GetCommentReplys = "api/shortvideo/getCommentReplys";
    String GetCommentMomentReplys = "api/Moment/getCommentReplys";
    String GetCommentShort = "api/shortvideo/getComments";
    String SendCode = "api/user/sendCode";
    String BindPhone = "api/User/bindPhone";
    String GetHomePopAd = "api/config/getHomePopAd";
    String Collect = "api/Shortvideo/collect";
    String GetAnchorInfo = "api/Anchor/getAnchorInfo";
    String getAnchorLiveInfo = "api/Live/getAnchorLiveInfo";
    String ExplainingGoods = "api/live/explainingGoods";
    String BanUser = "api/live/banUser";
    String SetRoomMgr = "api/live/setRoomMgr";
    String LikeVideo = "api/shortvideo/likeVideo";
    String LikeMoment = "api/Moment/likeMoment";
    String buyVip = "api/vip/getEplutusOrder";
    String buyDiamond = "api/recharge/getEplutusOrder";
    String GetListByUser = "api/Moment/getListByUser";
    String ReoprtAnchor = "api/Anchor/reoprt";
    String ReoprtShortvideo = "api/shortvideo/reoprt";
    String AddShareCount = "api/Shortvideo/addShareCount";
    String CheckIsMgr = "api/live/checkIsMgr";
    String getGroupUserData = "api/live/getGroupUserData";
    String ReoprtMoment = "api/Moment/reoprt";
    String EditUserInfo = "api/User/editUserInfo";
    String SearchAnchor = "api/Anchor/searchAnchor";
    String SetComment = "api/shortvideo/setComment";
    String SetMomentComment = "api/Moment/publishComment";
    String LikeComment = "api/shortvideo/likeComment";
    String LikeMomentComment = "api/Moment/likeComment";
    String AttentAnchor = "api/Anchor/attentAnchor";
    String APP_GET_USER_INFO = "api/user/getUserInfo";
    String GetUserBasicInfo = "api/user/getUserBasicInfo";
    String Wxlogin = "api/user/wxlogin";
    String Qqlogin = "api/user/qqlogin";
    String WithDraw = "api/agent/withDraw";
    String UserAuthInfo = "api/User/userAuthInfo";
    String Withdraw = "api/withdraw/withdraw";
    String sendVerifyCode = "api/user/sendVerifyCode";
    String EditCashAccount = "api/withdraw/editCashAccount";
    String Withdrawlog = "api/withdraw/withdrawlog";
    String Withdrawlog_agent = "api/agent/withdrawlog";
    String GetProfitLog = "api/agent/getProfitLog";
    String GetInviteList = "api/agent/getInviteList";
    String GET_Profit = "api/User/giftProfit";
    String GET_dongtaiProfit = "api/User/momentProfit";
    String GET_TempKeysForCos = "api/Config/getTempKeysForCos";
    String SET_IdentityAuth = "api/auth/identityAuth";
    String getUserAuthInfo = "api/user/getUserAuthInfo";
    String SET_EditUserInfo = "api/User/editUserInfo";
    String GET_GetLiveLog = "api/User/getLiveLog";
    String GetAttentAnchors = "api/Anchor/getAttentAnchors";
    String CheckAttent = "api/Anchor/checkAttent";
    String GetLiveInfo = "api/Live/getLiveInfo";
    String GET_PriceList = "api/recharge/getPriceList";
    String GetGiftList = "api/Gift/getGiftList";
    String GetUserRankList = "api/Rank/getUserRankList";
    String GetAnchorRankList = "api/Rank/getAnchorRankList";
    String GET_Fans = "api/User/getFans";
    String GET_Account = "api/withdraw/getAccount";
    String GetSystemMsg = "api/Message/getSystemMsg";
    String GetWxPayOrder = "api/recharge/getWxPayOrder";
    String GetAliPayOrder = "api/recharge/getAliPayOrder";
    String GetVipWxPayOrder = "api/vip/getWxPayOrder";
    String GetVipAliPayOrder = "api/vip/getAliPayOrder";
    String PayDeposit = "api/shop/payDeposit";
    String EditCartGoodsCount = "shop/cart/editCartGoodsCount";

    String GetAliShopPayOrder = "api/shop/getAliPayOrder";
    String Getuserlevelinfo = "api/user/getuserlevelinfo";
    String GetWxShopPayOrder = "api/shop/getWxPayOrder";
    String GetGoodsList = "api/live/getGoodsList";
    String GetWeekIntimacyRank = "api/Intimacy/getWeekIntimacyRank";
    String GetTotalIntimacyRank = "api/Intimacy/getTotalIntimacyRank";
    String GetVipPriceList = "api/vip/getVipPriceList";
    String GetAgentInfo = "api/agent/getAgentInfo";


    String GetListOfMessage= "api/live/listQuickMessage";
    String SaveQuickMessage= "api/live/saveQuickMessage";
    String deQuickMessage= "api/live/delQuickMessage";
    String UserBalanceList= "api/user_balance/list";



    String TXbaseUrl = "https://liveroom.qcloud.com/weapp/live_room/";
    //    String baseUrl = "http://192.168.0.100/";
    String Guild= MyUserInstance.getInstance().getUserConfig().getConfig().getSite_domain() +"/h5/guild?";
    String Stores=MyUserInstance.getInstance().getUserConfig().getConfig().getSite_domain() +"/h5/stores/";
    String Goods=MyUserInstance.getInstance().getUserConfig().getConfig().getSite_domain() +"/h5/goods/";
    String Personal=MyUserInstance.getInstance().getUserConfig().getConfig().getSite_domain() +"/h5/personal?";
    String Business=MyUserInstance.getInstance().getUserConfig().getConfig().getSite_domain() +"/h5/cooperation";
    String About=MyUserInstance.getInstance().getUserConfig().getConfig().getSite_domain() +"/h5/about";
    String Privacy_2=MyUserInstance.getInstance().getUserConfig().getConfig().getSite_domain() +"/h5/privacy/2";
    String Privacy_1=MyUserInstance.getInstance().getUserConfig().getConfig().getSite_domain() +"/h5/privacy/1";
    String GetTempUserKey = "api/user/getTempUserKey";





    //用户提交购买订单
    String SubmitOrder = "shop/order/submitOrder";

    //商家发布商品
    String PublishGoods = "shop/goods/publishGoods";


    @POST("api/config/getCommonConfig")
    Flowable<BaseResponse<UserConfig>> getUserConfig();

    @POST("api/Ads/getHomeScrollAd")
    Flowable<BaseResponse<ArrayList<Banners>>> getHomeScrollAd();

    @POST("api/live/getHotLives")
    Flowable<BaseResponse<ArrayList<HotLive>>> getHotLives(@Body RequestBody body);

    @POST("api/live/getLivesByCategory")
    Flowable<BaseResponse<ArrayList<HotLive>>> getLivesByCategory(@Body RequestBody body);

    @POST("api/shortvideo/getRandomList")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getRandomList(@Body RequestBody body);

    @POST("api/shortvideo/getComments")
    Flowable<BaseResponse<ArrayList<Comment>>> getComments(@Body RequestBody body);

    @POST("api/config/getadd")
    Flowable<BaseResponse<Banners>> getUserPage();

    //登录
    @POST("/api/user/login")
    Flowable<BaseResponse<UserRegist>> userLogin(@Body RequestBody body);

    //登录
    @POST("/api/Moment/likeComment")
    Flowable<BaseResponse> likeComment(@Body RequestBody body);

    //登录
    @POST("/api/Moment/collectMoment")
    Flowable<BaseResponse> collectMoment(@Body RequestBody body);

    //登录
    @POST("/api/Moment/likeMoment")
    Flowable<BaseResponse> likeMoment(@Body RequestBody body);

    //发布短视频
    @POST("/api/shortvideo/publish")
    Flowable<BaseResponse> publish(@Body RequestBody body);

    //获取验证码
    @POST("/api/user/sendCode")
    Flowable<BaseResponse<CodeMsg>> getCode(@Body RequestBody phone);

    //用户注册
    @POST("/api/user/regist")
    Flowable<BaseResponse<UserRegist>> userRegist(@Body RequestBody body);

    //修改密码
    @POST("/api/user/changePwd")
    Flowable<BaseResponse<UserRegist>> changePwd(@Body RequestBody body);

    //用户配置
    @POST("/api/config/getCommonConfig")
    Flowable<BaseResponse<UserConfig>> getCommonConfig();

    @POST("/api/Gift/getGiftList")
    Flowable<BaseResponse<ArrayList<GiftInfo>>> getGiftList();

    @POST("/api/Gift/sendGift")
    Flowable<BaseResponse> sendGift(@Body RequestBody build);

    @POST("/api/Anchor/getAnchorBasicInfo")
    Flowable<BaseResponse<UserRegist>> getAnchorInfo(@Body RequestBody build);

    @POST("/api/live/getContributeRank")
    Flowable<BaseResponse<ArrayList<ContributeRank>>> getContributeRank(@Body RequestBody build);

    @POST("/api/Live/endLive")
    Flowable<BaseResponse<EndLiveInfo>> endlive(@Body FormBody build);


    @POST("/api/Football/getMatchInfo")
    Flowable<BaseResponse<MatchList>> getMatchInfo(@Body FormBody build);

    @POST("/api/Config/getTempKeysForCos")
    Flowable<BaseResponse<QCloudData>> getTempKeysForCos(@Body FormBody body);

    @POST("/api/Live/mlvbStartLive")
    Flowable<BaseResponse<HotLive>> startLive(@Body FormBody body);

    @POST("/api/Gift/sendGift")
    Flowable<BaseResponse> sendGift(@Body FormBody build);

    @POST("/api/Moment/getAttentList")
    Flowable<BaseResponse<ArrayList<Trend>>> getMomentAttent(@Body FormBody build);

    @POST("/api/Moment/getHotList")
    Flowable<BaseResponse<ArrayList<Trend>>> getMomentHot(@Body FormBody build);

    @POST("/api/Moment/getComments")
    Flowable<BaseResponse<ArrayList<MomentDetail>>> getMomentDetail(@Body FormBody build);


    @POST("/api/Moment/getCommentReplys")
    Flowable<BaseResponse<ArrayList<MomentDetail>>> getMomentCommentReplys(@Body FormBody build);

    @POST("/api/shortvideo/getUserInfo")
    Flowable<BaseResponse<UserInfo>> getShortUserInfo(@Body FormBody build);

    @POST("/api/User/getUserInfo")
    Flowable<BaseResponse<UserRegist>> getUserInfo(@Body FormBody build);

    @POST("/api/shortvideo/getListByUser")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getAnchorWorks(@Body FormBody build);

    @POST("/api/User/myVideo")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getMyVideo(@Body FormBody build);


    @POST("/api/shortvideo/getListUserLike")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getUserLike(@Body FormBody build);

    @POST("/api/Moment/getListByUser")
    Flowable<BaseResponse<ArrayList<Trend>>> getListByUser(@Body FormBody build);

    @POST("/api/User/myMoment")
    Flowable<BaseResponse<ArrayList<Trend>>> getMyTrendList(@Body FormBody build);

    @POST("/api/Anchor/getAnchorInfo")
    Flowable<BaseResponse<PersonalAnchorInfo>> getPersonalAnchorInfo(@Body FormBody build);

    @POST("/api/Moment/unlockMoment")
    Flowable<BaseResponse> unlockMoment(@Body FormBody build);

    @POST("/api/Moment/publish")
    Flowable<BaseResponse> publish(@Body FormBody build);

    @POST("/api/Moment/getCollection")
    Flowable<BaseResponse<ArrayList<Trend>>> getCollection(@Body FormBody build);

    @POST("/api/shortvideo/getCollection")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> getCollectionShort(@Body FormBody build);

    @POST("/api/live/timeBilling")
    Flowable<BaseResponse> getTimeBilling(@Body FormBody build);

    @POST("/api/config/getHomePopAd")
    Flowable<BaseResponse<HomeAd>> getHomePopAd();

    @POST("/api/Anchor/getAttentAnchors")
    Flowable<BaseResponse<ArrayList<UserRegist>>> getAttentAnchors(@Body FormBody build);

    @POST("/api/User/getFans")
    Flowable<BaseResponse<ArrayList<UserRegist>>> getFans(@Body FormBody build);

    @POST("/api/live/getLiveRoomLogDetail")
    Flowable<BaseResponse<ArrayList<LiveRoomDetailBean>>> getLiveRoomDetail(@Body FormBody build);


    @POST("/api/live/getLiveViewerList")
    Flowable<BaseResponse<ArrayList<LiveAudienceListBean>>> getLiveViewerList(@Body FormBody build);


    @POST("/api/Moment/search")
    Flowable<BaseResponse<ArrayList<Trend>>> searchMoment(@Body FormBody body);

    @POST("/api/Anchor/search")
    Flowable<BaseResponse<ArrayList<UserRegist>>> searchAnchor(@Body FormBody body);

    @POST("/api/live/search")
    Flowable<BaseResponse<ArrayList<HotLive>>> searchLive(@Body FormBody body);

    @POST("/api/shortvideo/search")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> searchShort(@Body FormBody body);

    @POST("/api/withdraw/withdrawlog")
    Flowable<BaseResponse<ArrayList<CashOutHistory>>> withdrawlog(@Body FormBody body);

    @POST("/api/agent/withdrawlog")
    Flowable<BaseResponse<ArrayList<CashOutHistory>>> withdrawlog_agent(@Body FormBody body);

    @POST("/api/live/getLiveRoomLogList")
    Flowable<BaseResponse<ArrayList<AnchorHistory>>> livelog(@Body FormBody body);

    @POST("/api/User/bindPhone")
    Flowable<BaseResponse> bindPhone(@Body FormBody body);

    @POST("/api/agent/getProfitLog")
    Flowable<BaseResponse<ArrayList<ProfitLog>>> getProfitLog(@Body FormBody body);

    @POST("/api/agent/getInviteList")
    Flowable<BaseResponse<Invite>> getInviteList(@Body FormBody body);

    @POST("/api/Anchor/attentAnchor")
    Flowable<BaseResponse> attentAnchor(@Body FormBody body);

    @POST("/api/user/qqlogin")
    Flowable<BaseResponse<UserRegist>> qqlogin(@Body FormBody body);

    @POST("/api/live/getBannedUserList")
    Flowable<BaseResponse<ArrayList<UserRegist>>> getBannedUserList(@Body FormBody body);


    @POST("/api/live_online/getGroupUsersOnline")
    Flowable<BaseResponse<AllUser>> getAllUserList(@Body FormBody body);


    @POST("/api/User/getManagedRooms")
    Flowable<BaseResponse<ArrayList<UserRegist>>> getManagedRooms(@Body FormBody body);

    @POST("/api/live/getMgrList")
    Flowable<BaseResponse<ArrayList<RoomManager>>> getMgrList(@Body FormBody body);


    @POST("/api/live/requestMlvbLink")
    Flowable<BaseResponse<LinkInfo>> requestMlvbLink(@Body FormBody body);

    @POST("/api/live/stopMlvbLink")
    Flowable<BaseResponse> stopMlvbLink(@Body FormBody body);

    @POST("/api/live/refuseMlvbLink")
    Flowable<BaseResponse> refuseMlvbLink(@Body FormBody body);

    @POST("/api/live/acceptMlvbLink")
    Flowable<BaseResponse<LinkInfo>> acceptMlvbLink(@Body FormBody body);


    @POST("/api/live/setLinkOnOff")
    Flowable<BaseResponse> setLinkOnOff(@Body FormBody body);

    @POST("/api/live/mergeStream")
    Flowable<BaseResponse> mergeStream(@Body FormBody body);

    @POST("/api/topic/getTopicList")
    Flowable<BaseResponse<ArrayList<Topic>>> getTopicList(@Body FormBody body);

    @POST("/api/shortvideo/listInTopic")
    Flowable<BaseResponse<ArrayList<ShortVideo>>> videoListInTopic(@Body FormBody body);

    @POST("/api/moment/listInTopic")
    Flowable<BaseResponse<ArrayList<Trend>>> momentListInTopic(@Body FormBody body);

    @POST("/api/topic/getTopicInfo")
    Flowable<BaseResponse<Topic>> getTopicInfo(@Body FormBody body);

    @POST("/api/live/checkIsMgr")
    Flowable<BaseResponse<IsMgr>> checkIsMgr(@Body FormBody body);

    @POST("/api/live/getGoodsList")
    Flowable<BaseResponse<ArrayList<Good>>> getGoodsList(@Body FormBody body);

    @POST("/api/user/getUserBasicInfo")
    Flowable<BaseResponse<UserRegist>> getUserBasicInfo(@Body FormBody body);

    @POST("/api/Anchor/getGuardInfo")
    Flowable<BaseResponse<GuardianInfo>> getGuardInfo(@Body FormBody body);

    @POST("/api/Anchor/getGuardianCount")
    Flowable<BaseResponse<Count>> getGuardianCount(@Body FormBody body);

    @POST("/api/Anchor/getGuardianList")
    Flowable<BaseResponse<ArrayList<Guardian>>> getGuardianList(@Body FormBody body);

    @POST("/api/Anchor/guard")
    Flowable<BaseResponse<BuyGuard>> buyGuard(@Body FormBody body);

    @POST("/api/Live/getLiveInfo")
    Flowable<BaseResponse<LiveInfo>> getLiveInfo(@Body FormBody body);

    @POST("/api/Live/getAnchorLiveInfo")
    Flowable<BaseResponse<LiveInfo>> getAnchorLiveInfo(@Body FormBody body);

    @POST("/api/Live/enterPkMode")
    Flowable<BaseResponse> enterPkMode(@Body FormBody body);

    @POST("/api/Live/endPk")
    Flowable<BaseResponse> endPk(@Body FormBody body);

    @POST("/api/Live/getLiveBasicInfo")
    Flowable<BaseResponse<BaseLiveInfo>> getLiveBasicInfo(@Body FormBody body);


    //商城相关
    @POST("/shop/goods/getGoodsInfo")
    Flowable<BaseResponse<GoodInfo>> getGoodsInfo(@Body FormBody body);
    @POST("/api/shop/payDeposit")
    Flowable<BaseResponse> payDeposit(@Body FormBody body);
    @POST("/shop/evaluate/goodsEvaluateList")
    Flowable<BaseResponse<EvaluateList>> goodsEvaluateList(@Body FormBody body);
    @POST("/shop/goods/getGoodsList")
    Flowable<BaseResponse<ShopGoodList>> getShopGoodsList(@Body FormBody body);
    @POST("/shop/cart/getCartGoodsList")
    Flowable<BaseResponse<ArrayList<CartGoodInfo>>> getCartGoodsList(@Body FormBody body);

    @POST("/shop/user/addAddress")
    Flowable<BaseResponse> addAddress(@Body FormBody body);

    @POST("/shop/user/editAddress")
    Flowable<BaseResponse> editAddress(@Body FormBody body);

    @POST("/shop/cart/delCartGoods")
    Flowable<BaseResponse> delCartGoods(@Body FormBody body);

    @POST("/shop/cart/addCartGoods")
    Flowable<BaseResponse> addCartGoods(@Body FormBody body);

    @POST("/shop/user/getUserAddressList")
    Flowable<BaseResponse<ArrayList<Address>>> getUserAddressList(@Body FormBody body);

    @POST("/shop/user/getUserInfo")
    Flowable<BaseResponse<ShopUserInfo>> getShopUserInfo(@Body FormBody body);


    @POST("/shop/user/getOrderList")
    Flowable<BaseResponse<ArrayList<UserOrderInfo>>> getOrderList(@Body FormBody body);



    @POST("/shop/user/getOrderList")
    Flowable<BaseResponse<ArrayList<RefundOrderGoods>>> getRefundOrderList(@Body FormBody body);

    @POST("/shop/order/getOrderInfo")
    Flowable<BaseResponse<OrderDetails>> getOrderInfo(@Body FormBody body);

    @POST("/shop/order/getExpressInfo")
    Flowable<BaseResponse<LogisticsInfo>> getExpressInfo(@Body FormBody body);

    @POST("/shop/order/cancelOrder")
    Flowable<BaseResponse> cancelOrder(@Body FormBody body);

    @POST("/shop/order/applyReturnGoods")
    Flowable<BaseResponse> applyReturnGoods(@Body FormBody body);


    @POST("/shop/order/returnOrderInfo")
    Flowable<BaseResponse<RefundInfo>> returnOrderInfo(@Body FormBody body);

    @POST("/shop/shop/shopHomePageData")
    Flowable<BaseResponse<ShopSellerInfo>> shopHomePageData(@Body FormBody body);

    @POST("/shop/shop/shopOrderList")
    Flowable<BaseResponse<ArrayList<SellerOrderInfo>>> shopOrderList(@Body FormBody body);

    @POST("/shop/shop/shopOrderList")
    Flowable<BaseResponse<ArrayList<RefundOrderGoods>>> getSellerRefundOrderList(@Body FormBody body);

    @POST("/shop/order/operateReturn")
    Flowable<BaseResponse> operateReturn(@Body FormBody body);

    @POST("/shop/goods/getCategoryList")
    Flowable<BaseResponse<ArrayList<ClassIfy>>> getShopCategoryList(@Body FormBody body);

    @POST("/shop/order/deliveryOrder")
    Flowable<BaseResponse> deliveryOrder(@Body FormBody body);



    @POST("/shop/shop/getShopGoods")
    Flowable<BaseResponse<ArrayList<GoodManager>>> getShopGoods(@Body FormBody body);

    @POST("/shop/shop/setGoodsSaleStatus")
    Flowable<BaseResponse> setGoodsSaleStatus(@Body FormBody body);

    @POST("/shop/shop/walletInfo")
    Flowable<BaseResponse<SellerWalletInfo>> walletInfo(@Body FormBody body);

    @POST("/shop/shop/withdrawLog")
    Flowable<BaseResponse<ArrayList<SellerCashOut>>> SellerwithdrawLog(@Body FormBody body);

    @POST("/shop/shop/withdraw")
    Flowable<BaseResponse> Sellerwithdraw(@Body FormBody body);

    @POST("/shop/user/getUserVisits")
    Flowable<BaseResponse<ArrayList<OrderGoods>>> getUserVisits(@Body FormBody body);


    @POST("/shop/user/delUserVisits")
    Flowable<BaseResponse> delUserVisits(@Body FormBody body);


    @POST("/shop/order/confirmReceipt")
    Flowable<BaseResponse> confirmReceipt(@Body FormBody body);

    @POST("/shop/evaluate/submit")
    Flowable<BaseResponse> submitComment(@Body FormBody body);



    @POST("/shop/order/submitReturnExpress")
    Flowable<BaseResponse> submitReturnExpress(@Body FormBody body);

    @POST("/shop/order/shopReceiveReturn")
    Flowable<BaseResponse> shopReceiveReturn(@Body FormBody body);


    @POST("/api/user/getUserBasicInfo")
    Flowable<BaseResponse<UserRegist>> getUserInfoById(@Body FormBody body);
    @POST("/api/Anchor/checkAttent")
    Flowable<BaseResponse<CheckAttend>> checkAttent(@Body FormBody body);

    @POST("/api/live/setRoomMgr")
    Flowable<BaseResponse> setRoomMgr(@Body FormBody body);
    @POST("/api/live/banUser")
    Flowable<BaseResponse<BanStatus>> banUser(@Body FormBody body);
    @POST("/api/user/phoneLogin")
    Flowable<BaseResponse<UserRegist>> phoneLogin(@Body FormBody body);

    @POST("/api/live/timeBilling")
    Flowable<BaseResponse<LiveConsume>> liveChosume(@Body FormBody build);
    @POST("/api/live/checkIsMgr")
    Flowable<BaseResponse<IsMgr>> checkIsMgr2(@Body FormBody body);

    @POST("/api/Live/mlvbStartLive")
    Flowable<BaseResponse<HotLive>> mlvbStartLive(@Body FormBody body);


    @POST("/api/live/getLiveHistoryTitle")
    Flowable<BaseResponse<LiveHistoryBean>> getLiveHistoryTitle(@Body FormBody body);




    @POST("api/ewm/generateLivePoster")
    Flowable<BaseResponse<LivePoster>> livePoster(@Body FormBody body);




    @POST("/api/user/bindAgent")
    Flowable<BaseResponse> bindAgent(@Body FormBody body);


    @POST("/api/live/listQuickMessage")
    Flowable<BaseResponse<SwiftMessageBean>> getListOfSwiftMessage(@Body FormBody body);




}
