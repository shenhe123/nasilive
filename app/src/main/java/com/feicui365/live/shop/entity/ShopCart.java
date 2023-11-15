package com.feicui365.live.shop.entity;

import java.util.ArrayList;

public class ShopCart {
    ArrayList<CartGoods> data;

    public ShopCart() {
    }

    public ArrayList<CartGoods> getData() {
        return data;
    }

    public void setData(ArrayList<CartGoods> data) {
        this.data = data;
    }
}
