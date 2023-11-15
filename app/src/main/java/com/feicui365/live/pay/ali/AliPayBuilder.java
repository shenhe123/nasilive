package com.feicui365.live.pay.ali;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.PayTask;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.wxapi.PayCallback;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.Map;



public class AliPayBuilder {

    private Activity mActivity;
    private String mPartner;// 商户ID
    private String mSellerId; // 商户收款账号
    private String mPrivateKey; // 商户私钥，pkcs8格式
    private String mPayInfo;//支付宝订单信息 包括 商品信息，订单签名，签名类型
    private PayHandler mPayHandler;

    public AliPayBuilder(Activity activity) {
        mActivity = new WeakReference<>(activity).get();

    }



    public AliPayBuilder setPayCallback(PayCallback callback) {
        mPayHandler = new PayHandler(callback);
        return this;
    }


    /**
     * 从服务器端获取订单号,即下单
     */
    public void pay(String itemid) {

        HttpUtils.getInstance().getAliPayOrder(itemid, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                JSONObject data = HttpUtils.getInstance().check(response);
                if (data.get("status").toString().equals("0")) {
                    JSONObject obj = data.getJSONObject("data");
                    String orderInfo=obj.getString("paystr");

                    invokeAliPay(  orderInfo);
                }

            }
        });
    }

    public void payDeposit(String itemid) {

        HttpUtils.getInstance().payDeposit(itemid, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                JSONObject data = HttpUtils.getInstance().check(response);
                if (data.get("status").toString().equals("0")) {
                    JSONObject obj = data.getJSONObject("data");
                    String orderInfo=obj.getString("paystr");

                    invokeAliPay(  orderInfo);
                }

            }
        });
    }


    public void getAliShopPayOrder(String order_no,String total_fee) {

        HttpUtils.getInstance().getAliShopPayOrder(order_no,total_fee, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                String a=response.body();
                JSONObject data = HttpUtils.getInstance().check(response);
                if (data.get("status").toString().equals("0")) {
                    JSONObject obj = data.getJSONObject("data");
                    String orderInfo=obj.getString("paystr");

                    invokeAliPay(  orderInfo);
                }

            }
        });
    }
    public void payVip(String level) {

        HttpUtils.getInstance().getVipAliPayOrder(level, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                JSONObject data = HttpUtils.getInstance().check(response);
                if (data.get("status").toString().equals("0")) {
                    JSONObject obj = data.getJSONObject("data");
                    String orderInfo = obj.getString("paystr");

                    invokeAliPay(orderInfo);
                }

            }
        });
    }

    /**
     * 根据订单信息生成订单的签名
     *
     * @param orderInfo 订单信息
     * @return
     */
    private String getOrderSign(String orderInfo) {
        return SignUtils.sign(orderInfo, mPrivateKey);
    }

    /**
     * 对订单签名进行urlencode转码
     *
     * @param sign 签名
     * @return
     */
    private String urlEncode(String sign) {
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 调用支付宝sdk
     */
    private void invokeAliPay(  String orderInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                //执行支付，这是一个耗时操作，最后返回支付的结果，用handler发送到主线程
                Map<String, String> result = alipay.payV2(orderInfo, true);

                if (mPayHandler != null) {
                    Message msg = Message.obtain();
                    msg.obj = result;
                    mPayHandler.sendMessage(msg);
                }
            }
        }).start();
    }


    private static class PayHandler extends Handler {

        private PayCallback mPayCallback;

        public PayHandler(PayCallback payCallback) {
            mPayCallback = new WeakReference<>(payCallback).get();
        }

        @Override
        public void handleMessage(Message msg) {
            if (mPayCallback != null) {
                @SuppressWarnings("unchecked")
                Map<String, String> result = (Map<String, String>) msg.obj;
                if ("9000".equals(result.get("resultStatus"))) {
                    mPayCallback.onSuccess();
                } else {
                    mPayCallback.onFailed();
                }
            }
            mPayCallback = null;
        }
    }

}
