package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.List;

public class AccountDetailBean implements Serializable {
    /**
     * title : 账户充值
     * price : 1000.00
     * type : 1
     * pay_time : 2022-09-27 15:58:18
     * start_time :
     */

    private String title;
    private String price;
    private int type;
    private String pay_time;
    private String start_time;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

}
