package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class RefundInfo implements Serializable {
    OrderGoods ordergoods;
    RefundOrderInfo return_info;

    public RefundInfo() {
    }

    public OrderGoods getOrdergoods() {
        return ordergoods;
    }

    public void setOrdergoods(OrderGoods ordergoods) {
        this.ordergoods = ordergoods;
    }

    public RefundOrderInfo getReturn_info() {
        return return_info;
    }

    public void setReturn_info(RefundOrderInfo return_info) {
        this.return_info = return_info;
    }
}
