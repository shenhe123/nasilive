package com.feicui365.live.shop.entity;

import java.util.ArrayList;

public class EvaluateList {

    int good_count;
    int img_count;
    int total_count;
    ArrayList<Evaluate> list;

    public EvaluateList() {
    }

    public int getGood_count() {
        return good_count;
    }

    public void setGood_count(int good_count) {
        this.good_count = good_count;
    }

    public int getImg_count() {
        return img_count;
    }

    public void setImg_count(int img_count) {
        this.img_count = img_count;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public ArrayList<Evaluate> getList() {
        return list;
    }

    public void setList(ArrayList<Evaluate> list) {
        this.list = list;
    }
}
