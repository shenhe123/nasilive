package com.feicui365.live.shop.entity;

import java.util.ArrayList;

public class Confirm {
    ArrayList<ConfirmGood> goods;
    String remark;
    int shopid;
    String total_price;

    public Confirm() {
    }

    public ArrayList<ConfirmGood> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<ConfirmGood> goods) {
        this.goods = goods;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }



}
