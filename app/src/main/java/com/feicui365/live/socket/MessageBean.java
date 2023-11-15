package com.feicui365.live.socket;

import com.feicui365.live.ui.act.Chat;

import java.io.Serializable;

public class MessageBean implements Serializable {
    String action;
    String roomId;
    Chat chat;

    public MessageBean() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
