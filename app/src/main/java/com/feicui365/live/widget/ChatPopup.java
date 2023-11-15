/*
package com.feicui365.live.widget;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.lxj.xpopup.core.BottomPopupView;
import com.feicui365.live.R;
import com.feicui365.live.bean.ConversationInfo;
import com.feicui365.live.ui.act.SystemMessageActivity;
import com.feicui365.live.util.MyUserInstance;
import com.tencent.imsdk.v2.V2TIMConversation;
import com.tencent.imsdk.v2.V2TIMConversationResult;
import com.tencent.imsdk.v2.V2TIMManager;
import com.tencent.imsdk.v2.V2TIMValueCallback;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationLayout;
import com.tencent.qcloud.tim.uikit.modules.conversation.ConversationListLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class ChatPopup extends BottomPopupView implements View.OnClickListener {

    LinearLayout system_news;

    public String txim_notify_zan = MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_notify_zan();
    private String txim_notify_comment = MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_notify_comment();
    private String txim_notify_attent = MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_notify_attent();
    ConversationLayout conversation_layout;

    private ArrayList<V2TIMConversation> uiConvList = new ArrayList();


    public ChatPopup(@NonNull Context context) {
        super(context);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.system_news:
                getContext().startActivity(new Intent( getContext(), SystemMessageActivity.class));
                dismiss();
                break;
        }
    }
    public void goSys(){
        getContext().startActivity(new Intent( getContext(),SystemMessageActivity.class));
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_chat;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        system_news=findViewById(R.id.system_news);
        conversation_layout=findViewById(R.id.conversation_layout);
        initData();
    }

    private void initData(){
        V2TIMManager.getConversationManager().getConversationList(0, 100, new V2TIMValueCallback<V2TIMConversationResult>() {
            @Override
            public void onError(int code, String desc) {
                Log.v("V2TIMManager", "loadConversation getConversationList error, code = " + code + ", desc = " + desc);
            }

            @Override
            public void onSuccess(V2TIMConversationResult v2TIMConversationResult) {
                ArrayList<ConversationInfo> infos = new ArrayList<>();
                List<V2TIMConversation> v2TIMConversationList = v2TIMConversationResult.getConversationList();
                updateConversation(v2TIMConversationList,true);
            }
        });

        conversation_layout.initDefault3(MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_admin(), MyUserInstance.getInstance().getUserConfig().getConfig().getTxim_broadcast(), txim_notify_zan, txim_notify_comment, txim_notify_attent);
        conversation_layout.getTitleBar().setVisibility(View.GONE);


        conversation_layout.getConversationList().setOnItemClickListener(new ConversationListLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, com.tencent.qcloud.tim.uikit.modules.conversation.base.ConversationInfo messageInfo) {
                if(position==0){
                    goSys();
                }else {
                    MyUserInstance.getInstance().startChatActivity( getContext(),messageInfo, ChatActivity.class);
                }

            }
        });
    }

    private void updateConversation(List<V2TIMConversation> convList, boolean needSort) {
        for (int i = 0; i < convList.size(); i++) {
            V2TIMConversation conv = convList.get(i);
            boolean isExit = false;
            for (int j = 0; j < uiConvList.size(); j++) {
                V2TIMConversation uiConv = uiConvList.get(j);
                // UI 会话列表存在该会话，则替换
                if (uiConv.getConversationID().equals(conv.getConversationID())) {
                    uiConvList.set(j, conv);
                    isExit = true;
                    break;
                }
            }
            // UI 会话列表没有该会话，则新增
            if (!isExit) {
                uiConvList.add(conv);
            }
        }
        // 4. 按照会话 lastMessage 的 timestamp 对 UI 会话列表做排序并更新界面
        if (needSort) {
            Collections.sort(uiConvList, new Comparator<V2TIMConversation>() {
                @Override
                public int compare(V2TIMConversation o1, V2TIMConversation o2) {
                    if (o1.getLastMessage().getTimestamp() > o2.getLastMessage().getTimestamp()) {
                        return -1;
                    } else {
                        return 1;
                    }
                }
            });
        }
    }
}*/
