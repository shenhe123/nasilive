package com.feicui365.live.model.entity;

import java.util.ArrayList;

public class VippriceList {
    ArrayList<VipPrice> data;

    public VippriceList() {
    }

    public VippriceList(ArrayList<VipPrice> data) {
        this.data = data;
    }

    public ArrayList<VipPrice> getData() {
        return data;
    }

    public void setData(ArrayList<VipPrice> data) {
        this.data = data;
    }
}
