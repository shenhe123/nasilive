package com.feicui365.live.live.bean;

/**
 *
 */
public class BaseGuardianTipsInfo {
    String type;
    String tips;
    int icon;
    boolean check;


    public BaseGuardianTipsInfo(String type, String tips, int icon, boolean check) {
        this.type = type;
        this.tips = tips;
        this.icon = icon;
        this.check = check;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public BaseGuardianTipsInfo() {
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

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
