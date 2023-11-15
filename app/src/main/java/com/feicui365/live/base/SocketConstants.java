package com.feicui365.live.base;


/**
 * 注释
 * 保留字段
 */
public class SocketConstants {


 /*   public static final String  SOCKET_ACTION_JOINROOM ="SocketActionJoinRoom" ;//加入房间
    public static final String  SOCKET_ACTION_LEAVEROOM ="SocketActionLeaveRoom" ;//离开房间
    public static final String  SOCKET_ACTION_ROOMMESSAGE ="SocketActionRoomMessage" ;//直播间用户消息
    public static final String  SOCKET_ACTION_BROADCASTSTREAMER ="SocketActionBroadcastStreamer" ;//横幅推送
    public static final String  SOCKET_ACTION_ROOMNOTIFICATION ="SocketActionRoomNotification" ;//直播间系统通知消息

*/

    //讲解商品


    //设置管理员
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeSetManager="RoomNotifyTypeSetManager";
    //取消管理
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeCancelManager="RoomNotifyTypeCancelManager";
    //守护主播
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeGuardAnchor="RoomNotifyTypeGuardAnchor";
    //连麦申请
    public static final String  SOCKET_NOTIFY_RoomNotificationReciveLinkRequest="RoomNotificationReciveLinkRequest";
    //主播接受连麦
    public static final String  SOCKET_NOTIFY_RoomNotificationAcceptLinkRequest="RoomNotificationAcceptLinkRequest";
    //拒绝连麦
    public static final String  SOCKET_NOTIFY_RoomNotificationRefuseLinkRequest="RoomNotificationRefuseLinkRequest";
    //结束连麦
    public static final String  SOCKET_NOTIFY_RoomNotificationStopLink="RoomNotificationStopLink";
    //主播开启连麦
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeLinkOn="RoomNotifyTypeLinkOn";
    //主播关闭连麦
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeLinkOff="RoomNotifyTypeLinkOff";
    //PK比分变化
    public static final String  SOCKET_NOTIFY_RoomNotifyTypePkScoreChange="RoomNotifyTypePkScoreChange";
    //发起pk
    public static final String  SOCKET_NOTIFY_RoomNotifyTypePkStart="RoomNotifyTypePkStart";
    //PK结束
    public static final String  SOCKET_NOTIFY_RoomNotifyTypePkEnd="RoomNotifyTypePkEnd";



   /* //直播结束
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeLiveFinished="RoomNotifyTypeLiveFinished";
    //发送礼物
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeGift="RoomNotifyTypeGift";*/

/*
    //直播间人数变化
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeMemberCountChanged="RoomNotifyTypeMemberCountChanged";
    //直播间人数变化
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeViewerEnter="RoomNotifyTypeViewerEnter";


    //禁言
    public static final String  SOCKET_NOTIFY_RoomNotifyTypeUserBeBanned="RoomNotifyTypeUserBeBanned";
*/




   public static final String  SOCKET_NOTIFY_RoomNotifyNotice="RoomNotifyNotice";


    public static final int  LIVE_GIFT_ANIMATE=100001;
    public static final int  LIVE_ADD_CHAT=100002;
    public static final int  LIVE_SET_WATCHER_NUM=100003;
    public static final int  LIVE_GET_LIVE_INFO=100004;
    public static final int  LIVE_STREAMER_VIP=100005;

    public static final int  LIVE_PK_START=100006;
    public static final int  LIVE_PK_END=100007;
    public static final int  LIVE_PK_SCORE_CHANGE=100008;
    public static final int  LIVE_BEAUTY=100009;
    public static final int  LIVE_LINK_ON=100010;
    public static final int  LIVE_LINK_OFF=100011;
    public static final int  LIVE_SHOP_ITEM=100020;

    //拒绝
    public static final int  LIVE_LINK_REFUSE=100012;
    //同意
    public static final int  LIVE_LINK_ACCEPT=100013;
    //停止
    public static final int  LIVE_LINK_STOP=100014;
    //停止
    public static final int  LIVE_LINK_REVICE=100015;

    public static final int  LIVE_SET_MANAGER=100016;
    public static final int  LIVE_CANCEL_MANAGER=100017;
    public static final int  UPDATE_LIVE_INFO=100018;
    public static final int  LIVE_GUARDIAN_ANCHOR=100019;

    public static final String  LIVE_ACTION_RoomMessage ="RoomMessage" ;//直播间用户消息
    public static final String  LIVE_ACTION_LiveGroupMemberJoinExit ="LiveGroupMemberJoinExit" ;//直播间用户消息
    public static final String  LIVE_ACTION_LiveFinished ="LiveFinished" ;//直播间用户消息
    public static final String  LIVE_ACTION_RoomAttentAnchor ="RoomAttentAnchor" ;//直播间用户消息
    public static final String  LIVE_ACTION_GiftAnimation ="GiftAnimation" ;//直播间用户消息
    public static final String  LIVE_ACTION_RoomNotification ="RoomNotification" ;//直播间用户消息
    public static final String  LIVE_ACTION_ExplainingGoods ="ExplainingGoods" ;//商品信息
    public static final String  LIVE_ACTION_userOutGroup ="userOutGroup" ;//踢人

}
