package com.feicui365.live.model.entity;

import java.io.Serializable;

public class UnReadMessage implements Serializable {
    private String Action;
    private Object Data;

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }
}
