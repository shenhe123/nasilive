### Socket文档：
+ v-4.0.0

    Socket传输实体结构（json格式）

    > action:
    > > SocketActionJoinRoom: 加入直播间
    > >
    > > SocketActionLeaveRoom: 退出直播间
    > >
    > > SocketActionRoomMessage：直播间消息
    > >
    > > SocketActionGiftAnimation：礼物动画
    > >
    > > SocketActionSystemMessage：系统消息
    > >
    > > SocketActionLiveGroupMemberJoinExit：有观众进入或离开直播间
    > >
    > > SocketActionBroadcastStreamer：横幅推送
    > >
    > > SocketActionRoomNotification：直播间通知消息
    >
    > roomId：房间id（主播id）
    >
    > chat：直播间消息（json格式）
    >
    > > message：消息文本内容
    > >
    > > textColor：消息文本颜色（非必需）
    > >
    > > nameColor：消息昵称颜色（非必需）
    > >
    > > isManager：发送者是否为管理员（1-是）
    > >
    > > isGuardian：发送者是否为守护（1-是）
    > >
    > > chatType：消息类型（1-系统消息 0-用户消息）
    > >
    > > sender：发送者（结构同用户model，json格式）
    >
    > gift：礼物（结构同礼物model，json格式）
    >
    > streamer：横幅（json格式）
    >
    > > type：横幅类型 1-VIP 2-全屏礼物
    > >
    > > user：触发用户（结构同用户model，json格式）
    > >
    > > anchor：被动触发用户（如全屏礼物，则此处为主播对象，结构同用户model，json格式）
    > >
    > > gift：礼物（结构同礼物model，json格式）
    > >
    > > vip：贵族（结构同贵族vip model，json格式）
    >
    > notify：直播间通知（json格式）
    >
    > > type：通知类型
    > >
    > > > RoomNotifyTypeSetManager：设置管理员
    > > >
    > > > RoomNotifyTypeCancelManager：取消管理员
    > > >
    > > > RoomNotifyTypeLinkOn：连麦功能开启
    > > >
    > > > RoomNotifyTypeLinkOff：连麦功能关闭
    > > >
    > > > RoomNotificationReciveLinkRequest：收到连麦请求
    > > >
    > > > RoomNotificationAcceptLinkRequest：接受连麦请求
    > > >
    > > > RoomNotificationRefuseLinkRequest：拒绝连麦请求
    > > >
    > > > RoomNotificationStopLink：结束连麦
    > > >
    > > > RoomNotifyTypeGuardAnchor：守护主播
    > > >
    > > > RoomNotifyTypePkStart：PK开始
    > > >
    > > > RoomNotifyTypePkEnd：PK结束
    > > >
    > > > RoomNotifyTypePkScoreChange：PK比分变化
    > > >
    > > > RoomNotifyTypeLiveFinished：直播结束
    > > >
    > > > RoomNotifyTypeExplainingGoods：开始讲解商品
    > > >
    > > > RoomNotifyTypeUserBeBanned：用户被禁言
    > >
    > > roomId：房间id（主播id）
    > >
    > > fromUser：消息来源/操作来源用户
    > >
    > > toUser：消息通知用户（ex: 被设置为房管的用户）
    > >
    > > displayMessage：直播间聊天框展示文字
    > >
    > > linkAccUrl：连麦加速播流地址
    > >
    > > pkLive：PK对手直播（结构同直播model，json格式）
    > >
    > > pkInfo：PK信息（结构同PK信息model，json格式）
    > >
    > > goods：商品（结构同商品model，json格式）

