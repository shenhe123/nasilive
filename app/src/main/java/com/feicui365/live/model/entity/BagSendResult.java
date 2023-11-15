package com.feicui365.live.model.entity;

import java.io.Serializable;


public class BagSendResult implements Serializable {
    String gift_id;
    int gift_num;

    public String getGift_id() {
        return gift_id;
    }

    public void setGift_id(String gift_id) {
        this.gift_id = gift_id;
    }

    public int getGift_num() {
        return gift_num;
    }

    public void setGift_num(int gift_num) {
        this.gift_num = gift_num;
    }

    public BagSendResult() {
    }
}
