package com.feicui365.live.shop.entity;

import java.io.Serializable;

//订单物品详情
public class OrderGoods implements Serializable {
    String color;
    int count;
    int evaluate_status;//评论
    int goodsid;
    int id;
    int inventoryid;
    String price;
    int return_status;//退货
    int shopid;
    Shop shop;
    String size;
    int suborderid;
    int uid;
    String visits_time;
    Good goods;
    Suborder suborder;
    boolean is_chose;

    public boolean isIs_chose() {
        return is_chose;
    }

    public void setIs_chose(boolean is_chose) {
        this.is_chose = is_chose;
    }

    public Suborder getSuborder() {
        return suborder;
    }

    public void setSuborder(Suborder suborder) {
        this.suborder = suborder;
    }

    public OrderGoods() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getEvaluate_status() {
        return evaluate_status;
    }

    public void setEvaluate_status(int evaluate_status) {
        this.evaluate_status = evaluate_status;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInventoryid() {
        return inventoryid;
    }

    public void setInventoryid(int inventoryid) {
        this.inventoryid = inventoryid;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getSize() {
        if(size==null){
            return "";
        }
        return size;
    }

    public void setSize(String size) {
        this.size = size;
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

    public int getReturn_status() {
        return return_status;
    }

    public void setReturn_status(int return_status) {
        this.return_status = return_status;
    }

    public Good getGoods() {
        return goods;
    }

    public void setGoods(Good goods) {
        this.goods = goods;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getVisits_time() {
        return visits_time;
    }

    public void setVisits_time(String visits_time) {
        this.visits_time = visits_time;
    }
}
