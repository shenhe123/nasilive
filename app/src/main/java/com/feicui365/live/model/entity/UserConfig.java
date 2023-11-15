package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 用户配置信息类
 */
public class UserConfig implements Serializable {
    private LoginConfig config;
    private ArrayList<UserTag> user_tag;
    private LaunchAd launch_ad;
    private ArrayList<LiveCategory> live_category;
    private GuardPrice guard_price;

    public GuardPrice getGuard_price() {
        return guard_price;
    }

    public void setGuard_price(GuardPrice guard_price) {
        this.guard_price = guard_price;
    }
    public LoginConfig getConfig() {
        return config;
    }

    public void setConfig(LoginConfig config) {
        this.config = config;
    }

    public ArrayList<UserTag> getUser_tag() {
        return user_tag;
    }

    public void setUser_tag(ArrayList<UserTag> user_tag) {
        this.user_tag = user_tag;
    }

    public LaunchAd getLaunch_ad() {
        return launch_ad;
    }

    public void setLaunch_ad(LaunchAd launch_ad) {
        this.launch_ad = launch_ad;
    }

    public ArrayList<LiveCategory> getLive_category() {
        return live_category;
    }

    public void setLive_category(ArrayList<LiveCategory> live_category) {
        this.live_category = live_category;
    }
}
