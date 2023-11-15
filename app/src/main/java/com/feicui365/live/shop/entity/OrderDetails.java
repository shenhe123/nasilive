package com.feicui365.live.shop.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderDetails implements Serializable {
    ArrayList<OrderGoods> goods;
    UserOrderInfo order;

    public OrderDetails() {
    }

    public ArrayList<OrderGoods> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<OrderGoods> goods) {
        this.goods = goods;
    }

    public UserOrderInfo getOrder() {
        return order;
    }

    public void setOrder(UserOrderInfo order) {
        this.order = order;
    }
}
