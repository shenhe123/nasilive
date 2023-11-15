package com.feicui365.live.live.bean;

/**
 *
 */
public class BaseGuardianBuyInfo {
    String title;
    String type;
    String tips;
    int gold;
    boolean check;


    public BaseGuardianBuyInfo(String title, String type, String tips, int gold, boolean check) {
        this.title = title;
        this.type = type;
        this.tips = tips;
        this.gold = gold;
        this.check = check;
    }



    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public BaseGuardianBuyInfo() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
