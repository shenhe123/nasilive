package com.feicui365.live.bean;

import java.io.Serializable;

public class ImMessage implements Serializable {
    private String action;
    private MessageData data;

    public ImMessage() {
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public MessageData getData() {
        return data;
    }

    public void setData(MessageData data) {
        this.data = data;
    }
}
