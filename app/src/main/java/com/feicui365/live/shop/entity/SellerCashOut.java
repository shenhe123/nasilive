package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class SellerCashOut implements Serializable {
    String alipay_account;
    String alipay_name;
    String apply_cash;
    String commission_cash;
    String create_time;
    int id;
    int shopid;
    int status;
    String trade_cash;
    String trade_no;

    public SellerCashOut() {
    }

    public String getAlipay_account() {
        return alipay_account;
    }

    public void setAlipay_account(String alipay_account) {
        this.alipay_account = alipay_account;
    }

    public String getAlipay_name() {
        return alipay_name;
    }

    public void setAlipay_name(String alipay_name) {
        this.alipay_name = alipay_name;
    }

    public String getApply_cash() {
        return apply_cash;
    }

    public void setApply_cash(String apply_cash) {
        this.apply_cash = apply_cash;
    }

    public String getCommission_cash() {
        return commission_cash;
    }

    public void setCommission_cash(String commission_cash) {
        this.commission_cash = commission_cash;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTrade_cash() {
        return trade_cash;
    }

    public void setTrade_cash(String trade_cash) {
        this.trade_cash = trade_cash;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }
}
