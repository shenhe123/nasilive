package com.feicui365.live.shop.entity;

import java.util.ArrayList;

public class ShopGoodList {
    int count;
    ArrayList<Good> list;
    Shop shop;

    public ShopGoodList() {
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<Good> getList() {
        return list;
    }

    public void setList(ArrayList<Good> list) {
        this.list = list;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
