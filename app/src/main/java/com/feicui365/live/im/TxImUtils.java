package com.feicui365.live.im;

import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.feicui365.nasinet.utils.AppManager;
import com.tencent.imsdk.v2.V2TIMAdvancedMsgListener;
import com.tencent.imsdk.v2.V2TIMCallback;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationListener;
import com.tencent.imsdk.v2.V2TIMGroupMemberInfo;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMSDKListener;
import com.tencent.imsdk.v2.V2TIMSimpleMsgListener;
import com.tencent.imsdk.v2.V2TIMUserFullInfo;
import com.tencent.imsdk.v2.V2TIMUserInfo;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tuicore.TUIConstants;
import com.tencent.qcloud.tuicore.TUICore;
import com.tencent.qcloud.tuicore.TUILogin;
import com.tencent.qcloud.tuicore.interfaces.TUICallback;
import com.tencent.qcloud.tuikit.tuiconversation.bean.ConversationInfo;

import com.feicui365.live.base.MyApp;
import com.feicui365.live.base.SocketConstants;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.eventbus.UnReadBus;
import com.feicui365.live.live.utils.StreamerUtils;
import com.feicui365.live.model.entity.GiftInfo;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.MyUserInstance;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;


public class TxImUtils {
    private static volatile TxImUtils mImClient;
    private V2TIMSimpleMsgListener defaultListener;

    private StreamerUtils mStreamerUtils;


    private V2TIMSimpleMsgListener mLiveMessageListener;
    private V2TIMAdvancedMsgListener mLiveAdvaceListener;
    private String mAnchorId;


    public static TxImUtils getSocketManager() {
        if (mImClient == null) {
            synchronized (AppManager.class) {
                if (mImClient == null) {
                    mImClient = new TxImUtils();
                }
            }
        }

        return mImClient;
    }

    public TxImUtils() {

        mStreamerUtils = new StreamerUtils();
    }


    public void imLogin() {
        if (!TUILogin.isUserLogined()) {
            int sdkAppId = MyUserInstance.getInstance().getUserConfig().getConfig().getIm_sdkappid();
            String userId = MyUserInstance.getInstance().getUserinfo().getId();
            String userSign = MyUserInstance.getInstance().getUserinfo().getTxim_sign();
            TUILogin.login(MyApp.getInstance(), sdkAppId, userId, userSign, new TUICallback() {
                @Override
                public void onSuccess() {
                    Log.e("IMTAG", "success");
                    updateImUserInfo();
                    initBaseUnReadListener();
                }

                @Override
                public void onError(int errorCode, String errorMessage) {
                    Log.e("IMTAG", "error");
                }
            });
            initBaseListener();

        }
    }

    public void imLogout() {

        TUILogin.logout(new TUICallback() {
            @Override
            public void onSuccess() {
                Log.e("IMTAG", "LoginOutsuccess");
            }

            @Override
            public void onError(int errorCode, String errorMessage) {
                Log.e("IMTAG", "LoginOuterror");
            }
        });
    }

    public void updateImUserInfo() {
        if (TUILogin.isUserLogined()) {
            // 设置个人资料
            V2TIMUserFullInfo info = new V2TIMUserFullInfo();
            info.setNickname(MyUserInstance.getInstance().getUserinfo().getNick_name());
            info.setFaceUrl(MyUserInstance.getInstance().getUserinfo().getAvatar());
            V2TIMManager.getInstance().setSelfInfo(info, new V2TIMCallback() {
                @Override
                public void onSuccess() {
                    // 设置个人资料成功.
                    Log.e("IMTAG", "Setsuccess");
                }

                @Override
                public void onError(int code, String desc) {
                    // 设置个人资料失败
                    Log.e("IMTAG", "Seterror");
                }
            });

// 监听个人资料变更回调
            V2TIMManager.getInstance().addIMSDKListener(new V2TIMSDKListener() {
                @Override
                public void onSelfInfoUpdated(V2TIMUserFullInfo info) {
                    // 收到个人资料变更回调
                }
            });
        }
    }

    public void startChat(UserRegist mBean) {


        if (TUILogin.isUserLogined()) {

            if (mBean == null) {
                return;
            }

            Bundle param = new Bundle();
            param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C);
            param.putString(TUIConstants.TUIChat.CHAT_ID, mBean.getId());
            param.putString(TUIConstants.TUIChat.CHAT_NAME, mBean.getNick_name());
            param.putBoolean(TUIConstants.TUIChat.IS_TOP_CHAT, false);
            param.putString(TUIConstants.TUIChat.FACE_URL, mBean.getAvatar());
            TUICore.startActivity("MyTUIC2CChatActivity", param);
        }
    }

    public void startChat(String id, String name, String avatar) {

        if (TUILogin.isUserLogined()) {

            if (id == null) {
                return;
            }

            Bundle param = new Bundle();
            param.putInt(TUIConstants.TUIChat.CHAT_TYPE, V2TIMConversation.V2TIM_C2C);
            param.putString(TUIConstants.TUIChat.CHAT_ID, id);
            param.putString(TUIConstants.TUIChat.CHAT_NAME, name);
            param.putBoolean(TUIConstants.TUIChat.IS_TOP_CHAT, false);
            param.putString(TUIConstants.TUIChat.FACE_URL, avatar);
            TUICore.startActivity("MyTUIC2CChatActivity", param);
        }
    }

    public void startChat(ConversationInfo conversationInfo) {


        Bundle param = new Bundle();
        param.putInt(TUIConstants.TUIChat.CHAT_TYPE, conversationInfo.isGroup() ? V2TIMConversation.V2TIM_GROUP : V2TIMConversation.V2TIM_C2C);
        param.putString(TUIConstants.TUIChat.CHAT_ID, conversationInfo.getId());
        param.putString(TUIConstants.TUIChat.CHAT_NAME, conversationInfo.getTitle());
        if (conversationInfo.getDraft() != null) {
            param.putString(TUIConstants.TUIChat.DRAFT_TEXT, conversationInfo.getDraft().getDraftText());
            param.putLong(TUIConstants.TUIChat.DRAFT_TIME, conversationInfo.getDraft().getDraftTime());
        }
        param.putBoolean(TUIConstants.TUIChat.IS_TOP_CHAT, conversationInfo.isTop());
        param.putString(TUIConstants.TUIChat.FACE_URL, conversationInfo.getIconPath());
        if (conversationInfo.isGroup()) {
            param.putString(TUIConstants.TUIChat.GROUP_TYPE, conversationInfo.getGroupType());
            param.putSerializable(TUIConstants.TUIChat.AT_INFO_LIST, (Serializable) conversationInfo.getGroupAtInfoList());
        }
        if (conversationInfo.isGroup()) {
            //  TUICore.startActivity(TUIConstants.TUIChat.GROUP_CHAT_ACTIVITY_NAME, param);
        } else {
            TUICore.startActivity("MyTUIC2CChatActivity", param);
        }
    }


    private void initBaseListener() {

        if (defaultListener == null) {
            defaultListener = new V2TIMSimpleMsgListener() {
                @Override
                public void onRecvC2CTextMessage(String msgID, V2TIMUserInfo sender, String text) {
                    super.onRecvC2CTextMessage(msgID, sender, text);
                }

                @Override
                public void onRecvC2CCustomMessage(String msgID, V2TIMUserInfo sender, byte[] customData) {
                    super.onRecvC2CCustomMessage(msgID, sender, customData);
                }

                @Override
                public void onRecvGroupTextMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, String text) {
                    super.onRecvGroupTextMessage(msgID, groupID, sender, text);
                }

                @Override
                public void onRecvGroupCustomMessage(String msgID, String groupID, V2TIMGroupMemberInfo sender, byte[] customData) {
                    super.onRecvGroupCustomMessage(msgID, groupID, sender, customData);
                    String result = new String(customData);
                    if (!result.contains("action")) {
                        return;
                    }
                    ImMessage bean = JSON.parseObject(result, ImMessage.class);


                    switch (bean.getAction()) {

                        case SocketConstants.LIVE_ACTION_GiftAnimation:
                            if (bean.getData().getGift() == null) {
                                return;
                            }
                            GiftInfo giftInfo = bean.getData().getGift();
                            if (giftInfo.getUse_type() == 1) {
                                mStreamerUtils.addTolist(bean);
                            }

                            break;

                    }
                }

            };
        }


        V2TIMManager.getInstance().addSimpleMsgListener(defaultListener);
    }

    private void initBaseUnReadListener() {
        if (TUILogin.isUserLogined()) {


            V2TIMManager.getConversationManager().getTotalUnreadMessageCount(new V2TIMValueCallback<Long>() {
                @Override
                public void onSuccess(Long aLong) {

                    EventBus.getDefault().post(new UnReadBus(aLong));
                }

                @Override
                public void onError(int i, String s) {

                }
            });

            V2TIMManager.getConversationManager().addConversationListener(new V2TIMConversationListener() {
                @Override
                public void onTotalUnreadMessageCountChanged(long totalUnreadCount) {
                    super.onTotalUnreadMessageCountChanged(totalUnreadCount);

                    EventBus.getDefault().post(new UnReadBus(totalUnreadCount));
                }
            });
        }
    }

    private void cleanBaseListener() {
        if (defaultListener != null) {
            V2TIMManager.getInstance().removeSimpleMsgListener(defaultListener);
            defaultListener = null;
        }
    }


    public void initLiveListener(String anchorid, V2TIMSimpleMsgListener v2TIMSimpleMsgListener) {
        mAnchorId = anchorid;

        if (mLiveMessageListener == null) {
            mLiveMessageListener = v2TIMSimpleMsgListener;

            V2TIMManager.getInstance().addSimpleMsgListener(mLiveMessageListener);
        }


    }

    public void initLiveAdvaceListener(String anchorid, V2TIMAdvancedMsgListener v2TIMAdvanceMsgListener) {
        mAnchorId = anchorid;

        if (mLiveAdvaceListener == null) {
            mLiveAdvaceListener = v2TIMAdvanceMsgListener;
            V2TIMManager.getMessageManager().addAdvancedMsgListener(mLiveAdvaceListener);
        }


    }


    public void cleanLiveListener() {
        if (mLiveMessageListener != null) {
            V2TIMManager.getInstance().removeSimpleMsgListener(mLiveMessageListener);
        }


        if (mLiveAdvaceListener != null) {
            V2TIMManager.getMessageManager().removeAdvancedMsgListener(mLiveAdvaceListener);
        }

        mLiveMessageListener = null;
        mAnchorId = null;
    }


}
