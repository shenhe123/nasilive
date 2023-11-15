package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class RefundOrderGoods implements Serializable {
    String amount;
    String create_time;
    String desc;
    OrderGoods goods;
    int id;
    int ordergoodsid;
    String reason;
    Shop shop;
    int shopid;
    String status;
    Suborder suborder;
    int suborderid;
    int uid;


    public RefundOrderGoods() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public OrderGoods getGoods() {
        return goods;
    }

    public void setGoods(OrderGoods goods) {
        this.goods = goods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdergoodsid() {
        return ordergoodsid;
    }

    public void setOrdergoodsid(int ordergoodsid) {
        this.ordergoodsid = ordergoodsid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Suborder getSuborder() {
        return suborder;
    }

    public void setSuborder(Suborder suborder) {
        this.suborder = suborder;
    }

    public int getSuborderid() {
        return suborderid;
    }

    public void setSuborderid(int suborderid) {
        this.suborderid = suborderid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
