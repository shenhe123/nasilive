package com.feicui365.live.model.entity;

import java.io.Serializable;

public class RankAnchorItem implements Serializable {
    String anchorid;

    String income;
    UserRegist anchor;

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public UserRegist getAnchor() {
        return anchor;
    }

    public void setAnchor(UserRegist anchor) {
        this.anchor = anchor;
    }
}
