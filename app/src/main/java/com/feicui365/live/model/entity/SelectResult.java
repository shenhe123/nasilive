package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectResult implements Serializable {
    ArrayList<Screen> select_result;

    public SelectResult() {
    }

    public SelectResult(ArrayList<Screen> select_result) {
        this.select_result = select_result;
    }

    public ArrayList<Screen> getSelect_result() {
        return select_result;
    }

    public void setSelect_result(ArrayList<Screen> select_result) {
        this.select_result = select_result;
    }
}