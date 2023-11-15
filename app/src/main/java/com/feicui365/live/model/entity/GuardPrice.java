package com.feicui365.live.model.entity;

public class GuardPrice {
    String id;
    String month_price;
    String week_price;
    String year_price;

    public GuardPrice() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth_price() {
        return month_price;
    }

    public void setMonth_price(String month_price) {
        this.month_price = month_price;
    }

    public String getWeek_price() {
        return week_price;
    }

    public void setWeek_price(String week_price) {
        this.week_price = week_price;
    }

    public String getYear_price() {
        return year_price;
    }

    public void setYear_price(String year_price) {
        this.year_price = year_price;
    }
}
