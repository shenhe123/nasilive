package com.feicui365.live.model.entity;

import java.io.Serializable;

//有料MOD
public class OutsYouLiaoByMatch implements Serializable {
    private String id;
    private String uid;
    private String type;
    private String matchevent_id;
    private String match_id;
    private String homeid;
    private String awayid;
    private String value1;
    private String value2;
    private String value3;
    private String title;
    private String plan_value;
    private String plan_analyse;
    private String price;
    private String iscorrect;
    private String create_time;
    private String check_status;
    private String unlocked;
    private YouLiaoUser youliaouser;
    private MatchList match;

    public String getUnlocked() {
        return unlocked;
    }

    public void setUnlocked(String unlocked) {
        this.unlocked = unlocked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMatchevent_id() {
        return matchevent_id;
    }

    public void setMatchevent_id(String matchevent_id) {
        this.matchevent_id = matchevent_id;
    }

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getHomeid() {
        return homeid;
    }

    public void setHomeid(String homeid) {
        this.homeid = homeid;
    }

    public String getAwayid() {
        return awayid;
    }

    public void setAwayid(String awayid) {
        this.awayid = awayid;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlan_value() {
        return plan_value;
    }

    public void setPlan_value(String plan_value) {
        this.plan_value = plan_value;
    }

    public String getPlan_analyse() {
        return plan_analyse;
    }

    public void setPlan_analyse(String plan_analyse) {
        this.plan_analyse = plan_analyse;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getIscorrect() {
        return iscorrect;
    }

    public void setIscorrect(String iscorrect) {
        this.iscorrect = iscorrect;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getCheck_status() {
        return check_status;
    }

    public void setCheck_status(String check_status) {
        this.check_status = check_status;
    }

    public YouLiaoUser getYouliaouser() {
        return youliaouser;
    }

    public void setYouliaouser(YouLiaoUser youliaouser) {
        this.youliaouser = youliaouser;
    }

    public MatchList getMatch() {
        return match;
    }

    public void setMatch(MatchList match) {
        this.match = match;
    }
}
