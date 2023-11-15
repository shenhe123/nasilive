package com.feicui365.live.live.bean;

import java.io.Serializable;

/**
 *
 */
public class VipInfo implements Serializable {
    int level;
    String price;
    String gold;

    public VipInfo() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }
}
