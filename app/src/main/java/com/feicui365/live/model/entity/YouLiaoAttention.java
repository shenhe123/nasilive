package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class YouLiaoAttention implements Serializable {
    private ArrayList<YouLiaoAttented> attented;
    private ArrayList<YouLiaoAttented> rec;

    public ArrayList<YouLiaoAttented> getAttented() {
        return attented;
    }

    public void setAttented(ArrayList<YouLiaoAttented> attented) {
        this.attented = attented;
    }

    public ArrayList<YouLiaoAttented> getRec() {
        return rec;
    }

    public void setRec(ArrayList<YouLiaoAttented> rec) {
        this.rec = rec;
    }
}
