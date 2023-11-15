package com.feicui365.live.wxapi;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.base.Constants;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;



public class WxPayBuilder {

    private Context mContext;
    private String mAppId;
    private PayCallback mPayCallback;
    private String mOrderParams;//订单获取订单需要的参数

    public WxPayBuilder(Context context, String appId) {
        mContext = context;
        mAppId = appId;
        WxApiWrapper.getInstance().setAppID(appId);
        EventBus.getDefault().register(this);
    }

    public WxPayBuilder setOrderParams(String orderParams) {
        mOrderParams = orderParams;
        return this;
    }

    public WxPayBuilder setPayCallback(PayCallback callback) {
        mPayCallback = new WeakReference<>(callback).get();
        return this;
    }

    public void pay(String itemid) {
        HttpUtils.getInstance().getWxPayOrder(itemid, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject data = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
                if (null != data) {
                    JSONObject obj =data;
                    String partnerId = obj.getString("partnerid");
                    String prepayId = obj.getString("prepayid");
                    String packageValue = obj.getString("package");
                    String nonceStr = obj.getString("noncestr");
                    String timestamp = obj.getString("timestamp");
                    String sign = obj.getString("sign");
                    if (TextUtils.isEmpty(partnerId) ||
                            TextUtils.isEmpty(prepayId) ||
                            TextUtils.isEmpty(packageValue) ||
                            TextUtils.isEmpty(nonceStr) ||
                            TextUtils.isEmpty(timestamp) ||
                            TextUtils.isEmpty(sign)) {

                        ToastUtils.showT(Constants.PAY_WX_NOT_ENABLE);
                        return;
                    }
                    PayReq req = new PayReq();
                    req.appId = mAppId;
                    req.partnerId = partnerId;
                    req.prepayId = prepayId;
                    req.packageValue = packageValue;
                    req.nonceStr = nonceStr;
                    req.timeStamp = timestamp;
                    req.sign = sign;
                    IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
                    if (wxApi == null) {
                        ToastUtils.showT("充值失败");
                        return;
                    }
                    boolean result = wxApi.sendReq(req);
                    if (!result) {
                        ToastUtils.showT("充值失败");
                    }
                }
            }
        });

    }



    public void payDeposit(String pay_channel) {
        HttpUtils.getInstance().payDeposit(pay_channel, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject data = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
                if (null != data) {
                    JSONObject obj =data;
                    String partnerId = obj.getString("partnerid");
                    String prepayId = obj.getString("prepayid");
                    String packageValue = obj.getString("package");
                    String nonceStr = obj.getString("noncestr");
                    String timestamp = obj.getString("timestamp");
                    String sign = obj.getString("sign");
                    if (TextUtils.isEmpty(partnerId) ||
                            TextUtils.isEmpty(prepayId) ||
                            TextUtils.isEmpty(packageValue) ||
                            TextUtils.isEmpty(nonceStr) ||
                            TextUtils.isEmpty(timestamp) ||
                            TextUtils.isEmpty(sign)) {

                        ToastUtils.showT(Constants.PAY_WX_NOT_ENABLE);
                        ToastUtils.showT("充值失败");
                        return;
                    }
                    PayReq req = new PayReq();
                    req.appId = mAppId;
                    req.partnerId = partnerId;
                    req.prepayId = prepayId;
                    req.packageValue = packageValue;
                    req.nonceStr = nonceStr;
                    req.timeStamp = timestamp;
                    req.sign = sign;
                    IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
                    if (wxApi == null) {
                        ToastUtils.showT("充值失败");
                        return;
                    }
                    boolean result = wxApi.sendReq(req);
                    if (!result) {
                        ToastUtils.showT("充值失败");
                    }
                }
            }
        });

    }


    public void getWxShopPayOrder(String order_no,String total_fee) {
        HttpUtils.getInstance().getWxShopPayOrder(order_no,total_fee, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject data = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
                if (null != data) {
                    JSONObject obj =data;
                    String partnerId = obj.getString("partnerid");
                    String prepayId = obj.getString("prepayid");
                    String packageValue = obj.getString("package");
                    String nonceStr = obj.getString("noncestr");
                    String timestamp = obj.getString("timestamp");
                    String sign = obj.getString("sign");
                    if (TextUtils.isEmpty(partnerId) ||
                            TextUtils.isEmpty(prepayId) ||
                            TextUtils.isEmpty(packageValue) ||
                            TextUtils.isEmpty(nonceStr) ||
                            TextUtils.isEmpty(timestamp) ||
                            TextUtils.isEmpty(sign)) {

                        ToastUtils.showT(Constants.PAY_WX_NOT_ENABLE);
                        return;
                    }
                    PayReq req = new PayReq();
                    req.appId = mAppId;
                    req.partnerId = partnerId;
                    req.prepayId = prepayId;
                    req.packageValue = packageValue;
                    req.nonceStr = nonceStr;
                    req.timeStamp = timestamp;
                    req.sign = sign;
                    IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
                    if (wxApi == null) {
                        ToastUtils.showT("充值失败");
                        return;
                    }
                    boolean result = wxApi.sendReq(req);
                    if (!result) {

                        ToastUtils.showT("充值失败");
                    }
                }
            }
        });

    }

    public void payVip(String level) {
        HttpUtils.getInstance().getVipWxPayOrder(level, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject data = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
                if (null != data) {
                    JSONObject obj =data;
                    String partnerId = obj.getString("partnerid");
                    String prepayId = obj.getString("prepayid");
                    String packageValue = obj.getString("package");
                    String nonceStr = obj.getString("noncestr");
                    String timestamp = obj.getString("timestamp");
                    String sign = obj.getString("sign");
                    if (TextUtils.isEmpty(partnerId) ||
                            TextUtils.isEmpty(prepayId) ||
                            TextUtils.isEmpty(packageValue) ||
                            TextUtils.isEmpty(nonceStr) ||
                            TextUtils.isEmpty(timestamp) ||
                            TextUtils.isEmpty(sign)) {

                        ToastUtils.showT(Constants.PAY_WX_NOT_ENABLE);
                        return;
                    }
                    PayReq req = new PayReq();
                    req.appId = mAppId;
                    req.partnerId = partnerId;
                    req.prepayId = prepayId;
                    req.packageValue = packageValue;
                    req.nonceStr = nonceStr;
                    req.timeStamp = timestamp;
                    req.sign = sign;
                    IWXAPI wxApi = WxApiWrapper.getInstance().getWxApi();
                    if (wxApi == null) {
                        ToastUtils.showT("充值失败");
                        return;
                    }
                    boolean result = wxApi.sendReq(req);
                    if (!result) {

                        ToastUtils.showT("充值失败");
                    }
                }
            }
        });
    }

    public void wxLogin() {
        //判断是否安装了微信客户端
        if (!WxApiWrapper.getInstance().getWxApi().isWXAppInstalled()) {

            ToastUtils.showT("您还未安装微信客户端");
            return;
        }
        // 发送授权登录信息，来获取code
        SendAuth.Req req = new SendAuth.Req();
        // 应用的作用域，获取个人信息
        req.scope = "snsapi_userinfo";
        /**   * 用于保持请求和回调的状态，授权请求后原样带回给第三方  * 为了防止csrf攻击（跨站请求伪造攻击），后期改为随机数加session来校验   */
        req.state = "app_wechat";
        WxApiWrapper.getInstance().getWxApi().sendReq(req);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPayResponse(BaseResp resp) {

        if (mPayCallback != null) {
            if (0 == resp.errCode) {//支付成功
                mPayCallback.onSuccess();
            } else {//支付失败
                mPayCallback.onFailed();
            }
        }
        mContext = null;
        mPayCallback = null;
        EventBus.getDefault().unregister(this);
    }


}
