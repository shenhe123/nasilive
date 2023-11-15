package com.feicui365.live.model.entity;

import java.io.Serializable;

public class MatchOdd2 implements Serializable {
     MatchOddData rang;
     MatchOddData daxiao;
     MatchOddData jingcai;
     String home_logo;
    String away_logo;
    String home_name;
    String away_name;
    String math_name;
    String match_id;

    public String getMatch_id() {
        return match_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public String getMath_name() {
        return math_name;
    }

    public void setMath_name(String math_name) {
        this.math_name = math_name;
    }

    public String getHome_logo() {
        return home_logo;
    }

    public void setHome_logo(String home_logo) {
        this.home_logo = home_logo;
    }

    public String getAway_logo() {
        return away_logo;
    }

    public void setAway_logo(String away_logo) {
        this.away_logo = away_logo;
    }

    public String getHome_name() {
        return home_name;
    }

    public void setHome_name(String home_name) {
        this.home_name = home_name;
    }

    public String getAway_name() {
        return away_name;
    }

    public void setAway_name(String away_name) {
        this.away_name = away_name;
    }

    public MatchOddData getRang() {
        return rang;
    }

    public void setRang(MatchOddData rang) {
        this.rang = rang;
    }

    public MatchOddData getDaxiao() {
        return daxiao;
    }

    public void setDaxiao(MatchOddData daxiao) {
        this.daxiao = daxiao;
    }

    public MatchOddData getJingcai() {
        return jingcai;
    }

    public void setJingcai(MatchOddData jingcai) {
        this.jingcai = jingcai;
    }
}
