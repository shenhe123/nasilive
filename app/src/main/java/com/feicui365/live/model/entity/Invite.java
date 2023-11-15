package com.feicui365.live.model.entity;

import java.util.ArrayList;

public class Invite {

    String count_today;
    String count_total;
    ArrayList<InviteAgent> list;

    public Invite() {
    }

    public String getCount_today() {
        return count_today;
    }

    public void setCount_today(String count_today) {
        this.count_today = count_today;
    }

    public String getCount_total() {
        return count_total;
    }

    public void setCount_total(String count_total) {
        this.count_total = count_total;
    }

    public ArrayList<InviteAgent> getList() {
        return list;
    }

    public void setList(ArrayList<InviteAgent> list) {
        this.list = list;
    }
}
