package com.feicui365.live.socket;

import com.alibaba.fastjson.JSONObject;

public interface SocketMessageListener {


    /*action 直播间动作*/

    /**
     * 加入直播间  连接成功socket后调用
     */
    void onJoinRoom(MessageBean successConn);

    /**
     * 退出直播间
     */
    void onLeaveRoom(MessageBean successConn);

    /**
     * 直播间消息
     */
    void onRoomMessage(MessageBean successConn);

    /**
     * 礼物动画
     */
    void onGiftAnimation(JSONObject successConn);

    /**
     * 系统消息
     */
    void onSystemMessage(JSONObject successConn);

    /**
     * 有观众进入或离开直播间
     */
    void onLiveGroupMemberJoinExit(JSONObject successConn);

    /**
     * 横幅推送
     */
    void onBroadcastStreamer(JSONObject successConn);

    /**
     * 直播间通知消息
     */
    void onRoomNotification(JSONObject successConn);


}
