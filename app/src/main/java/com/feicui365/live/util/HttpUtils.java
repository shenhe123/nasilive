package com.feicui365.live.util;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.net.APIService;
import com.feicui365.live.ui.act.LoginActivity;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class HttpUtils {

    private static HttpUtils mSingleMode;
    private Context mContext;

    private HttpUtils() {

    }

    public static synchronized HttpUtils getInstance() {
        if (mSingleMode == null) {
            mSingleMode = new HttpUtils();
        }

        return mSingleMode;
    }




    public boolean swtichStatus(JSONObject data){
        switch (data.getString("status")){
            case "0":

                return true;

            case "2":
                ToastUtils.showT("当前账号在其他地方登陆");
                Hawk.remove("USER_REGIST");
                MyUserInstance.getInstance().setUserInfo(null);
                AppManager.getAppManager().startActivity(LoginActivity.class);
              //  AppManager.getAppManager().finishAllActivity();

                return false;
            default:
                ToastUtils.showT(data.getString("msg"));
                return false;
        }


    }


    //截取一个文件加载显示时传入的一个本地完整路径
    public static String subFileFullName(String fileName,String fileUrl){
        String cutName=fileName+fileUrl.substring(fileUrl.lastIndexOf("."),fileUrl.length());  //这里获取的是  恰似寒光遇骄阳.txt
        return cutName;
    }

    //获取用户信息
    public void getCommentReplys(String commentid,String lastid,StringCallback stringCallback) {
        List a=new ArrayList();
        if(lastid.equals("")){
            OkGo.<String>post(APIService.baseUrl + APIService.GetCommentReplys)
                    .tag(this)
                    .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                    .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                    .params("commentid",  commentid)

                    .params("size", "20")
                    .execute(stringCallback);
        }else{
            OkGo.<String>post(APIService.baseUrl + APIService.GetCommentReplys)
                    .tag(this)
                    .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                    .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                    .params("commentid",  commentid)
                    .params("lastid",  lastid)
                    .params("size", "20")
                    .execute(stringCallback);
        }

    }
    public  void cleanAll(){
        OkGo.getInstance().cancelAll();
    }
    //获取用户信息
    public void getTempUserKey(String temp_uid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetTempUserKey)
                .params("temp_uid", temp_uid)
                .tag(this)
                .execute(stringCallback);
    }


    //获取用户信息
    public void getCommentMomentReplys(String commentid,String lastid,StringCallback stringCallback) {
        if(lastid.equals("")){
            OkGo.<String>post(APIService.baseUrl + APIService.GetCommentMomentReplys)
                    .tag(this)
                    .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                    .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                    .params("commentid",  commentid)
                    .params("size", "20")
                    .execute(stringCallback);
        }else{
            OkGo.<String>post(APIService.baseUrl + APIService.GetCommentMomentReplys)
                    .tag(this)
                    .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                    .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                    .params("commentid",  commentid)
                    .params("lastid",  lastid)
                    .params("size", "20")
                    .execute(stringCallback);
        }

    }



    //获取用户信息
    public void getCommentShort(String videoid,String lastid,StringCallback stringCallback) {
        if(lastid.equals("")){
            OkGo.<String>post(APIService.baseUrl + APIService.GetCommentShort)
                    .tag(this)
                    .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                    .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                    .params("videoid",  videoid)
                    .params("size", "20")
                    .execute(stringCallback);
        }else{
            OkGo.<String>post(APIService.baseUrl + APIService.GetCommentShort)
                    .tag(this)
                    .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                    .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                    .params("videoid",  videoid)
                    .params("lastid",  lastid)
                    .params("size", "20")
                    .execute(stringCallback);
        }

    }


    //获取用户信息
    public void getAnchorInfo(String anchorid,StringCallback stringCallback) {

            OkGo.<String>post(APIService.baseUrl + APIService.GetAnchorInfo)
                    .tag(this)
                    .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                    .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                    .params("anchorid", anchorid)

                    .execute(stringCallback);


    }

    //获取用户信息
    public void getAnchorLiveInfo(String anchorid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.getAnchorLiveInfo)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("anchorid", anchorid)

                .execute(stringCallback);


    }

    public void explainingGoods(String goodsid,String type,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.ExplainingGoods)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .params("goodsid", goodsid)
                .params("type", type)
                .execute(stringCallback);


    }


    public void banUser(String anchorid,String userid,String type,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.BanUser)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .params("anchorid", anchorid)
                .params("userid", userid)
                .params("type", type)
                .execute(stringCallback);


    }

    public void setRoomMgr(String mgrid,String type,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.SetRoomMgr)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .params("mgrid", mgrid)
                .params("type", type)
                .execute(stringCallback);


    }




    //获取用户信息
    public void likeVideo(String videoid,String type,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.LikeVideo)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .params("videoid", videoid)
                .params("type", type)
                .execute(stringCallback);
    }


    public void likeMoment(String momentid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.LikeMoment)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .params("momentid", momentid)

                .execute(stringCallback);
    }



    public void buyDiamond(String itemid,String type,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.buyDiamond)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .params("itemid", itemid)
                .params("type", type)
                .execute(stringCallback);
    }

    public void buyVip(String level,String type,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.buyVip)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .params("level", level)
                .params("type", type)
                .execute(stringCallback);
    }

    public void shortCollect(String videoid,String type,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.Collect)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .params("videoid", videoid)
                .params("type", type)
                .execute(stringCallback);
    }

    public void getListByUser(String anchorid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetListByUser)
                .tag(this)
                .params("uid", "63621148")
                .params("authorid", anchorid)
                .params("page", 1)
                .params("size", "20")
                .execute(stringCallback);


    }



    //获取用户信息
    public void bindPhone(String mobile,String pwd,String code,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.BindPhone)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("mobile", mobile)
                .params("pwd", pwd)
                .params("smscode", code)
                .execute(stringCallback);


    }


    //获取用户信息
    public void reoprtAnchor(String relateid,String content,String title,String img_urls,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.ReoprtAnchor)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("relateid", relateid)
                .params("content", content)
                .params("title", title)
                .params("img_urls", img_urls)
                .execute(stringCallback);


    }


    //获取用户信息
    public void reoprtMoment(String relateid,String content,String title,String img_urls,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.ReoprtMoment)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("relateid", relateid)
                .params("content", content)
                .params("title", title)
                .params("img_urls", img_urls)
                .execute(stringCallback);


    }



    //获取用户信息
    public void reoprtShortvideo(String relateid,String content,String title,String img_urls,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.ReoprtShortvideo)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("relateid", relateid)
                .params("content", content)
                .params("title", title)
                .params("img_urls", img_urls)
                .execute(stringCallback);


    }


    //获取用户信息
    public void checkIsMgr(String anchorid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.CheckIsMgr)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("anchorid", anchorid)

                .execute(stringCallback);


    }


    //检查是否是管理员
    public void addShareCount(String videoid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.AddShareCount)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("videoid", videoid)

                .execute(stringCallback);


    }

    public void getGroupUserData(String user_id,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.getGroupUserData)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("user_id", user_id)

                .execute(stringCallback);


    }

    //获取用户信息
    public void searchAnchor(String keyword,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.SearchAnchor)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("keyword", keyword)
                .execute(stringCallback);


    }

    //编辑用户资料3
    public void editUserInfo(String avatar, String nick_name,String signature,String gender,String height,String weight,String constellation,String city,String age,String career,String photos, StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.EditUserInfo)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("nick_name", nick_name)
                .params("avatar", avatar)
                .params("gender", gender)
                .params("height", height)
                .params("weight", weight)
                .params("constellation", constellation)
                .params("city", city)
                .params("age", age)
                .params("career", career)
                .params("photos", photos)
                //个性签名
                .params("signature", signature)
                .execute(stringCallback);
    }




    //获取用户信息
    public void sendCode(String mobile,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.SendCode)
                .tag(this)
                .params("mobile", mobile)

                .execute(stringCallback);


    }

    //获取用户信息
    public void setComment(String videoid,String content,String rootid,String tocommentid,String touid,StringCallback stringCallback) {

            OkGo.<String>post(APIService.baseUrl + APIService.SetComment)
                    .tag(this)
                    .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                    .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                    .params("videoid",  videoid)
                    .params("content",  content)
                    .params("rootid",  rootid)
                    .params("tocommentid",  tocommentid)
                    .params("touid",  touid)
                    .execute(stringCallback);


    }

    //获取用户信息
    public void setMomentComment(String moment_id,String content,String rootid,String tocommentid,String touid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.SetMomentComment)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("momentid",  moment_id)
                .params("content",  content)
                .params("rootid",  rootid)
                .params("tocommentid",  tocommentid)
                .params("touid",  touid)
                .execute(stringCallback);


    }


    //贵族价格表
    public void getVipPriceList(StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetVipPriceList)
                .tag(this)
                .execute(stringCallback);


    }

    //贵族价格表
    public void getAgentInfo(StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetAgentInfo)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .execute(stringCallback);


    }
    //获取用户信息
    public void likeComment(String commentid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.LikeComment)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("commentid",  commentid)

                .execute(stringCallback);


    }



    public void likeMomentComment(String commentid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.LikeMomentComment)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("commentid",  commentid)

                .execute(stringCallback);


    }


    //获取用户信息
    public void attentAnchor(String anchorid,String type,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.AttentAnchor)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("anchorid",  anchorid)
                .params("type",  type)
                .execute(stringCallback);


    }

    //获取用户信息
    public void getUserInfo(StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.APP_GET_USER_INFO)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .execute(stringCallback);
    }

    //获取用户信息
    public void getUserBasicInfo(String uid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetUserBasicInfo)
                .tag(this)
                .params("id", uid)

                .execute(stringCallback);
    }

    //获取用户信息
    public void wxlogin(String code,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.Wxlogin)
                .tag(this)
                .params("code", code)
                .params("platform", "2")
                .execute(stringCallback);
    }

    public void qqlogin(String unionid,String nick_name,String gender,String icon,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.Qqlogin)
                .tag(this)
                .params("unionid", unionid)
                .params("nick_name", nick_name)
                .params("gender", gender)
                .params("icon", icon)
                .params("platform", "2")
                .execute(stringCallback);
    }



    //提现
    public void withDraw(String alipay_account,String alipay_name,String cash,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.WithDraw)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("alipay_account",  alipay_account)
                .params("alipay_name",  alipay_name)
                .params("cash",  cash)
                .execute(stringCallback);
    }

    //获取用户提现账户
    public void getAccount(StringCallback stringCallback) {
        String a= MyUserInstance.getInstance().getUserinfo().getId();
        String b= MyUserInstance.getInstance().getUserinfo().getToken();
        OkGo.<String>post(APIService.baseUrl + APIService.GET_Account)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .execute(stringCallback);
    }

    public void userAuthInfo(StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.UserAuthInfo)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())

                .execute(stringCallback);
    }

    public void withdraw(String cash, String diamond, String alipay_account, String alipay_name, StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.Withdraw)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("cash", cash)
                .params("diamond", diamond)
                .params("alipay_account", alipay_account)
                .params("alipay_name", alipay_name)
                .execute(stringCallback);
    }

    //发送验证码
    public void sendVerifyCode(String phone, StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.sendVerifyCode)
                .tag(this)
                .params("mobile", phone)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .execute(stringCallback);
    }

    public void editCashAccount(String alipay_name,String alipay_account,  String smscode, StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.EditCashAccount)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())

                .params("alipay_account", alipay_account)
                .params("alipay_name", alipay_name)
                .params("smscode", smscode)
                .execute(stringCallback);
    }

    public void withdrawlog(String page,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.Withdrawlog)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("page", page)
                .params("size", 10)

                .execute(stringCallback);
    }

    public void withdrawlog_agent(String page,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.Withdrawlog_agent)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("page", page)
                .params("size", 10)

                .execute(stringCallback);
    }

    public void getProfitLog(String page,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetProfitLog)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())

                .params("page", page)
                .params("size", 10)
                .execute(stringCallback);
    }


    public void getInviteList(String page,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetInviteList)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("page", page)
                .params("size", 10)

                .execute(stringCallback);
    }


    //获取充值价目表
    public void giftProfit(String page, StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GET_Profit)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("page", page)
                .params("size", 20 + "")
                .execute(stringCallback);
    }

    //获取充值价目表
    public void dongtaiProfit(String page, StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GET_dongtaiProfit)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("page", page)
                .params("size", 20 + "")
                .execute(stringCallback);
    }

    //腾讯上传零时
    public void getTempKeysForCos(StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GET_TempKeysForCos)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .tag(this)
                .execute(stringCallback);
    }

    //用户实名认证
    public void identityAuth(String real_name, String id_num, String id_card_url, StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.SET_IdentityAuth)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("real_name", real_name)
                .params("id_num", id_num)
//                .params("alipay_account", alipay_account)
//                .params("alipay_name", alipay_name)
                .params("id_card_url", id_card_url)
                .execute(stringCallback);
    }


    public void getUserAuthInfo(StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.getUserAuthInfo)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .execute(stringCallback);
    }




    //编辑用户资料2
    public void editUserInfo2(String gender, String age, String height, String weight, String constellation, String career, String city, String signature, StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.SET_EditUserInfo)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("gender", gender)
                .params("age", age)
                .params("height", height)
                .params("weight", weight)
                .params("constellation", constellation)
                .params("career", career)
                .params("city", city)
                //个性签名
                .params("signature", signature)
                .execute(stringCallback);
    }

    //直播记录
    public void getLiveLog(String page,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GET_GetLiveLog)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("page", page)
                .params("size", 10)
                .execute(stringCallback);
    }

    public void getAttentAnchors(int page,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetAttentAnchors)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("page", page)
                .params("size", 10)
                .execute(stringCallback);
    }

    public void getWeekIntimacyRank(String anchorid,String page,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetWeekIntimacyRank)
                .tag(this)
                .params("anchorid", anchorid)
                .params("page", page)
                .params("size", 20)
                .execute(stringCallback);
    }

    public void getTotalIntimacyRank(String anchorid,String page,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetTotalIntimacyRank)
                .tag(this)
                .params("anchorid", anchorid)
                .params("page", page)
                .params("size", 20)
                .execute(stringCallback);
    }

    public void getFans(String page,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GET_Fans)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("page", page)
                .params("size", 20)
                .execute(stringCallback);
    }

    public void checkAttent(String anchorid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.CheckAttent)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("anchorid", anchorid)

                .execute(stringCallback);
    }

    public void getLiveInfo(String liveid,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetLiveInfo)
                .tag(this)
                .params("liveid", liveid)
                .execute(stringCallback);
    }

    public void getSystemMsg(String lastid, StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GetSystemMsg)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("lastid", lastid)
                .params("size", 10)
                .execute(stringCallback);
    }

    public void getWxPayOrder(String itemid,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetWxPayOrder)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("itemid", itemid)

                .execute(stringCallback);
    }

    public void getAliPayOrder(String itemid,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetAliPayOrder)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("itemid", itemid)

                .execute(stringCallback);
    }


    public void getVipWxPayOrder(String level,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetVipWxPayOrder)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("level", level)

                .execute(stringCallback);
    }


    public void getVipAliPayOrder(String level,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetVipAliPayOrder)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("level", level)

                .execute(stringCallback);
    }

    //开店支付保证金
    public void payDeposit(String pay_channel,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.PayDeposit)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("pay_channel",pay_channel)
                .execute(stringCallback);
    }

    public void getAliShopPayOrder(String order_no,String total_fee,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetAliShopPayOrder)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("order_no",order_no)
                .params("total_fee",total_fee)
                .execute(stringCallback);
    }
    public void getuserlevelinfo(StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.Getuserlevelinfo)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())

                .execute(stringCallback);
    }

    public void getWxShopPayOrder(String order_no,String total_fee,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetWxShopPayOrder)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("order_no",order_no)
                .params("total_fee",total_fee)
                .execute(stringCallback);
    }


    public void getGoodsList(String anchorid,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetGoodsList)
                .tag(this)
                .params("anchorid", anchorid)

                .execute(stringCallback);
    }


    //获取充值价目表
    public void getPriceList(StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.GET_PriceList)
                .tag(this)
                .execute(stringCallback);
    }


    public void getQuickMessage(StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetListOfMessage)
                .tag(this)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .execute(stringCallback);
    }

    public void saveQuickMessage(String title,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.SaveQuickMessage)
                .tag(this)
                .params("title",title)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .execute(stringCallback);

    }


    public void deQuickMessage(String id,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.deQuickMessage)
                .tag(this)
                .params("id",id)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .execute(stringCallback);

    }

    public void userBalanceList(int type,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.UserBalanceList)
                .tag(this)
                .params("type",type)
                .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token", MyUserInstance.getInstance().getUserinfo().getToken())
                .execute(stringCallback);
    }



    public JSONObject check(Response<String> response) {
        if (response != null) {
            JSONObject jsonObject = JSON.parseObject(response.body());
            return jsonObject;

        }else{
            return null;
        }

    }


    public void getGiftList(StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetGiftList)
                .tag(this)
                .execute(stringCallback);
    }

    public void getUserRankList(String type,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetUserRankList)
                .params("type", type)
                .tag(this)
                .execute(stringCallback);
    }
    public void getAnchorRankList(String type,StringCallback stringCallback) {
        OkGo.<String>post(APIService.baseUrl + APIService.GetAnchorRankList)
                .params("type", type)
                .tag(this)
                .execute(stringCallback);
    }


    public void MLVBLogin(StringCallback stringCallback) {
        JSONObject json1 = new JSONObject();
        json1.put("sdkAppID",MyUserInstance.getInstance().getUserConfig().getConfig().getTisdk_key());
        json1.put("userID",MyUserInstance.getInstance().getUserinfo().getProfile().getUid());
        json1.put("userSig", MyUserInstance.getInstance().getUserinfo().getTxim_sign());
        json1.put("platform","Android");


        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, String.valueOf(json1));
        OkGo.<String>post(APIService.TXbaseUrl +"login")
                .tag(this)
                .upRequestBody(body)
                .execute(stringCallback);


    }

    //买家修改购物车商品数量
    public void editCartGoodsCount(String cartgoodsid,String count,StringCallback stringCallback) {

        OkGo.<String>post(APIService.baseUrl + APIService.EditCartGoodsCount)
                .tag(this)
               .params("uid", MyUserInstance.getInstance().getUserinfo().getId())
                .params("token",  MyUserInstance.getInstance().getUserinfo().getToken())
                .params("cartgoodsid", cartgoodsid)
                .params("count", count)
                .execute(stringCallback);


    }


}