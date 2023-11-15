package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Message implements Serializable {
    String action;
    String roomId;

    public Message() {
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
}
