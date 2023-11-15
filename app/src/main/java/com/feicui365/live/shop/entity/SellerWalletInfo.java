package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class SellerWalletInfo implements Serializable {
    String profit;
    int commission;
    CashAccount cash_account;

    public SellerWalletInfo() {
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public int getCommission() {
        return commission;
    }

    public void setCommission(int commission) {
        this.commission = commission;
    }

    public CashAccount getCash_account() {
        return cash_account;
    }

    public void setCash_account(CashAccount cash_account) {
        this.cash_account = cash_account;
    }

    public class CashAccount implements Serializable {
        String alipay_account;
        String alipay_name;
        int id;
        int uid;

        public CashAccount() {
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

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }

}
