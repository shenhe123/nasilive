package com.feicui365.live.model.entity;

import java.io.Serializable;

public class YouLiaoUser implements Serializable {
    private String uid;
    private String level;
    private String introduction;
    private String plan_count;
    private String plan_correct_count;
    private String plan_error_count;
    private String plan_correct_continuous;
    private String plan_correct_in;
    private String regist_time;
    private String profit;
    private String profit_total;
    private String status;
    private String reject_reason;
    private String check_time;
    private String attented;
    private YouLiaoUserInfo user;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPlan_count() {
        return plan_count;
    }

    public void setPlan_count(String plan_count) {
        this.plan_count = plan_count;
    }

    public String getPlan_correct_count() {
        return plan_correct_count;
    }

    public void setPlan_correct_count(String plan_correct_count) {
        this.plan_correct_count = plan_correct_count;
    }

    public String getPlan_error_count() {
        return plan_error_count;
    }

    public void setPlan_error_count(String plan_error_count) {
        this.plan_error_count = plan_error_count;
    }

    public String getPlan_correct_continuous() {
        return plan_correct_continuous;
    }

    public void setPlan_correct_continuous(String plan_correct_continuous) {
        this.plan_correct_continuous = plan_correct_continuous;
    }

    public String getPlan_correct_in() {
        return plan_correct_in;
    }

    public void setPlan_correct_in(String plan_correct_in) {
        this.plan_correct_in = plan_correct_in;
    }

    public String getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(String regist_time) {
        this.regist_time = regist_time;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getProfit_total() {
        return profit_total;
    }

    public void setProfit_total(String profit_total) {
        this.profit_total = profit_total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReject_reason() {
        return reject_reason;
    }

    public void setReject_reason(String reject_reason) {
        this.reject_reason = reject_reason;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getAttented() {
        return attented;
    }

    public void setAttented(String attented) {
        this.attented = attented;
    }

    public YouLiaoUserInfo getUser() {
        return user;
    }

    public void setUser(YouLiaoUserInfo user) {
        this.user = user;
    }
}
