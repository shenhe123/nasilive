package com.feicui365.live.im;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMMessage;
import com.tencent.imsdk.v2.V2TIMValueCallback;

import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.base.SocketConstants;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.bean.MessageData;
import com.feicui365.live.live.bean.Chat;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.Notify;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.util.MyUserInstance;

import org.greenrobot.eventbus.EventBus;


public class TxImSendUtils {


    public static void joinRoom(String anchorId, int type) {
        V2TIMManager.getInstance().joinGroup(ArmsUtils.initLiveGroupId(anchorId),
                "some reason", new V2TIMCallback() {
                    @Override
                    public void onError(int code, String desc) {
                        //接口返回了错误码 code 和错误描述 desc，可用于原因
                        //错误码 code 列表请参见错误码表
                        Log.e("IMTAG", "加群失败");

                    }

                    @Override
                    public void onSuccess() {
                        Log.e("IMTAG", "加群成功");
                        //如果加群成功,需要获取群组会话
                        if (type == 1) {

                            EventBus.getDefault().post(TxImSendUtils.sendMessage(MyApp.getInstance().getString(R.string.st_vip_enter_room), anchorId, 0, 0));
                        }
                    }
                });
    }


    public static void exitRoom(String anchorId) {
        V2TIMManager.getInstance().quitGroup(ArmsUtils.initLiveGroupId(anchorId),
                new V2TIMCallback() {
                    @Override
                    public void onError(int code, String desc) {
                        //接口返回了错误码 code 和错误描述 desc，可用于原因
                        //错误码 code 列表请参见错误码表
                        Log.e("IMTAG", "退群失败");
                    }

                    @Override
                    public void onSuccess() {
                        Log.e("IMTAG", "退群成功");
                    }
                });
    }

    public static void sendImMessage(String text, String id, V2TIMValueCallback callback) {
        V2TIMManager.getInstance().sendC2CTextMessage(text, id, callback);
    }

    public static ImMessage sendMessage(String text, String anchorId, int isManager, int isGuardian) {
        ImMessage message = new ImMessage();
        message.setAction("RoomMessage");
        MessageData messageData = new MessageData();

        Chat messageChat = new Chat();
        messageChat.setLevel(MyUserInstance.getInstance().getUserinfo().getUser_level());
        messageChat.setNick_name(MyUserInstance.getInstance().getUserinfo().getNick_name());
        messageChat.setMessage(text);
        messageChat.setSender(MyUserInstance.getInstance().getUserinfo());
        messageChat.setIs_guardian(isGuardian);
        messageChat.setIs_manager(isManager);
        messageData.setChat(messageChat);
        if (ArmsUtils.getVipLevel2(MyUserInstance.getInstance().getUserinfo().getVip_level(), MyUserInstance.getInstance().getUserinfo().getVip_date()) == 0) {
            messageChat.setVip(false);
        } else {
            messageChat.setVip(true);
        }
        message.setData(messageData);
        String result = JSON.toJSONString(message);
        V2TIMManager.getInstance().sendGroupTextMessage(result, ArmsUtils.initLiveGroupId(anchorId), V2TIMMessage.V2TIM_PRIORITY_NORMAL, new V2TIMValueCallback<V2TIMMessage>() {

            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                Log.e("IMTAG", "发消息成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("IMTAG", "发消息失败");
            }
        });
        return message;
    }

    public static ImMessage sendNotify() {
        ImMessage messageBean = new ImMessage();
        MessageData messageData = new MessageData();
        messageBean.setAction(SocketConstants.LIVE_ACTION_RoomNotification);
        Notify notify = new Notify();

        notify.setType(SocketConstants.SOCKET_NOTIFY_RoomNotifyNotice);

        messageData.setNotify(notify);
        messageBean.setData(messageData);
        return messageBean;
    }

    public static ImMessage getNotice() {
        ImMessage messageBean = new ImMessage();
        MessageData messageData = new MessageData();
        messageBean.setAction(SocketConstants.LIVE_ACTION_RoomNotification);
        Notify notify = new Notify();

        notify.setType(SocketConstants.SOCKET_NOTIFY_RoomNotifyNotice);

        messageData.setNotify(notify);
        messageBean.setData(messageData);
        return messageBean;
    }

    public static void sendGoodMessage(Good shopItem) {
        ImMessage message = new ImMessage();
        message.setAction("ExplainingGoods");
        MessageData messageData = new MessageData();
/*        Good goods = new Good();

        goods.setId(shopItem.getId());
        goods.setShopid(shopItem.getShopid());
        goods.setCategoryid(shopItem.getCategoryid());
        goods.setTitle(shopItem.getTitle());
        goods.setDesc(shopItem.getDesc());
        goods.setDesc_img_urls(shopItem.getDesc_img_urls());
        goods.setDelivery(shopItem.getDelivery());
        goods.setFreight(shopItem.getFreight());
        goods.setPrice(shopItem.getPrice());
        goods.setSale_count(shopItem.getSale_count());
        goods.setThumb_urls(shopItem.getThumb_urls());
        goods.setLive_explaining(shopItem.getLive_explaining());*/
        messageData.setGoods(shopItem);

        message.setData(messageData);
        String result = JSON.toJSONString(message);
        V2TIMManager.getInstance().sendGroupCustomMessage(result.getBytes(), ArmsUtils.initLiveGroupId(shopItem.getShopid()), V2TIMMessage.V2TIM_PRIORITY_NORMAL, new V2TIMValueCallback<V2TIMMessage>() {

            @Override
            public void onSuccess(V2TIMMessage v2TIMMessage) {
                Log.e("IMTAG", "发消息成功");
            }

            @Override
            public void onError(int i, String s) {
                Log.e("IMTAG", "发消息失败");
            }
        });

    }
}
