package com.feicui365.live.shop.entity;

public class ConfirmGood {
    int count;
    int id;
    int inventoryid;
    String price;

    public ConfirmGood() {
    }

    public ConfirmGood(int count, int id, int inventoryid, String price) {
        this.count = count;
        this.id = id;
        this.inventoryid = inventoryid;
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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
}