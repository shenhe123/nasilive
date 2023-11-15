/*
package com.feicui365.live.socket;


import com.google.gson.Gson;
import com.feicui365.live.base.Constants;
import com.feicui365.live.ui.act.Chat;
import com.feicui365.live.util.MyUserInstance;

import org.json.JSONException;
import org.json.JSONObject;

public class SocketSendUtils {
    static Gson gson=new Gson();

    public static void joinRoom( String roomId) {
        if (SocketClient.getInstance().getSocket() == null) {
            return;
        }



        try {




            Chat chat=new Chat();
            chat.setChatType(0);
            chat.setIsGuardian(0);
            chat.setIsManager(0);
            chat.setMessage("进入直播间");
            Chat.Sender sender=  new Chat.Sender();
            sender.setAgentid(MyUserInstance.getInstance().getUserinfo().getAgentid());
            sender.setAttentCount(MyUserInstance.getInstance().getUserinfo().getAttent_count());
            sender.setDiamond(MyUserInstance.getInstance().getUserinfo().getDiamond());
            sender.setFansCount(MyUserInstance.getInstance().getUserinfo().getFans_count());
            sender.setGiftSpend(MyUserInstance.getInstance().getUserinfo().getGift_spend());
            sender.setGold(MyUserInstance.getInstance().getUserinfo().getGold());
            sender.setGuildid(MyUserInstance.getInstance().getUserinfo().getGuildid());
            sender.setId(MyUserInstance.getInstance().getUserinfo().getId());
            sender.setAvatar(MyUserInstance.getInstance().getUserinfo().getAvatar());
            sender.setPoint(MyUserInstance.getInstance().getUserinfo().getPoint());
            sender.setVisitorCount(MyUserInstance.getInstance().getUserinfo().getVisitor_count());
            sender.setProfile(MyUserInstance.getInstance().getUserinfo().getProfile());
            chat.setSender(sender);

            MessageBean messageBean=new MessageBean();
            messageBean.setAction("SocketActionJoinRoom");
            messageBean.setRoomId(roomId);
            messageBean.setChat(chat);
            JSONObject jsonObject4=new JSONObject(gson.toJson(messageBean));

         //   SocketClient.getInstance().getSocket().emit(Constants.SOCKET_BROADCAST, jsonObject4);

        } catch (JSONException e) {
            e.printStackTrace();
        }







    }

    public static void leaveRoom( String roomId) {
        if (SocketClient.getInstance().getSocket() == null) {
            return;
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("action", "SocketActionLeaveRoom");
            jsonObject.put("roomId", roomId);
        } catch (JSONException e) {
            e.printStackTrace();
        }


     //   SocketClient.getInstance().getSocket().emit(Constants.SOCKET_BROADCAST, jsonObject);

    }


    public static void sendRoomMessage( String roomId,String message) {
        if (SocketClient.getInstance().getSocket() == null) {
            return;
        }

        try {



            Chat chat=new Chat();
            chat.setChatType(0);
            chat.setIsGuardian(0);
            chat.setIsManager(0);
            chat.setMessage("德玛西亚");
            Chat.Sender sender=  new Chat.Sender();
            sender.setAgentid(MyUserInstance.getInstance().getUserinfo().getAgentid());
            sender.setAttentCount(MyUserInstance.getInstance().getUserinfo().getAttent_count());
            sender.setDiamond(MyUserInstance.getInstance().getUserinfo().getDiamond());
            sender.setFansCount(MyUserInstance.getInstance().getUserinfo().getFans_count());
            sender.setGiftSpend(MyUserInstance.getInstance().getUserinfo().getGift_spend());
            sender.setGold(MyUserInstance.getInstance().getUserinfo().getGold());
            sender.setGuildid(MyUserInstance.getInstance().getUserinfo().getGuildid());
            sender.setId(MyUserInstance.getInstance().getUserinfo().getId());
            sender.setAvatar(MyUserInstance.getInstance().getUserinfo().getAvatar());
            sender.setPoint(MyUserInstance.getInstance().getUserinfo().getPoint());
            sender.setVisitorCount(MyUserInstance.getInstance().getUserinfo().getVisitor_count());
            sender.setProfile(MyUserInstance.getInstance().getUserinfo().getProfile());
            chat.setSender(sender);


            MessageBean messageBean=new MessageBean();
            messageBean.setAction("SocketActionRoomMessage");
            messageBean.setRoomId(roomId);
            messageBean.setChat(chat);
            JSONObject jsonObject4=new JSONObject(gson.toJson(messageBean));


          //  SocketClient.getInstance().getSocket().emit(Constants.SOCKET_BROADCAST, jsonObject4);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
*/
