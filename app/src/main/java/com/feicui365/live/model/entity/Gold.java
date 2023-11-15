package com.feicui365.live.model.entity;

import java.io.Serializable;

public class Gold implements Serializable {
    String id;
    String gold;
    String gold_added;
    String price;

    public Gold() {
    }

    public Gold(String id, String gold, String gold_added, String price) {
        this.id = id;
        this.gold = gold;
        this.gold_added = gold_added;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getGold_added() {
        return gold_added;
    }

    public void setGold_added(String gold_added) {
        this.gold_added = gold_added;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
