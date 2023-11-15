package com.feicui365.live.model.entity;

import java.io.Serializable;

public class MatchDate implements Serializable {
    private String date;
    private String week;
    private boolean tag = false;
    private String uiDate;

    public String getUiDate() {
        return uiDate;
    }

    public void setUiDate(String uiDate) {
        this.uiDate = uiDate;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
