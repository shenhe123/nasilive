package com.feicui365.live.shop.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class LogisticsInfo implements Serializable {
    String com;
    String condition;
    ArrayList<Logistics> data;
    String ischeck;
    String message;

    String nu;
    String state;
    String status;


    public LogisticsInfo() {
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public ArrayList<Logistics> getData() {
        return data;
    }

    public void setData(ArrayList<Logistics> data) {
        this.data = data;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
