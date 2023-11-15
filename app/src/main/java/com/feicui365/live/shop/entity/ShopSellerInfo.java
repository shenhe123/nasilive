package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class ShopSellerInfo implements Serializable {
    String create_time;
    int goods_count;
    int id;
    String income_today;
    int order_count_refund;
    int order_count_undelivery;
    int order_count_unpay;
    String profit;
    int status;
    String total_profit;
    User user;

    public class User implements Serializable {
        String account;
        String avatar;
        String id;
        String nick_name;

        public User() {
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }
    }

    public ShopSellerInfo() {
    }


    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIncome_today() {
        return income_today;
    }

    public void setIncome_today(String income_today) {
        this.income_today = income_today;
    }

    public int getOrder_count_refund() {
        return order_count_refund;
    }

    public void setOrder_count_refund(int order_count_refund) {
        this.order_count_refund = order_count_refund;
    }

    public int getOrder_count_undelivery() {
        return order_count_undelivery;
    }

    public void setOrder_count_undelivery(int order_count_undelivery) {
        this.order_count_undelivery = order_count_undelivery;
    }

    public int getOrder_count_unpay() {
        return order_count_unpay;
    }

    public void setOrder_count_unpay(int order_count_unpay) {
        this.order_count_unpay = order_count_unpay;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTotal_profit() {
        return total_profit;
    }

    public void setTotal_profit(String total_profit) {
        this.total_profit = total_profit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
