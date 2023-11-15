package com.feicui365.live.model.entity;

import java.io.Serializable;

public class MatchOddData implements Serializable {
    private String id;
    private String match_id;
    private String company_id;
    private String type;
    private String value1;
    private String value2;
    private String value3;
    private String isover;
    private long change_time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getIsover() {
        return isover;
    }

    public void setIsover(String isover) {
        this.isover = isover;
    }

    public long getChange_time() {
        return change_time;
    }

    public void setChange_time(long change_time) {
        this.change_time = change_time;
    }
}
