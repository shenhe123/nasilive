package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class PersonalAnchorInfo implements Serializable {
    private String id;
    private String nick_name;
    private String account;
    private String online_status;
    private String avatar;
    private String gold;
    private String diamond;
    private String diamond_total;
    private ArrayList<Tags> tags;
    private int anchor_level;
    private int user_level;
    private String point;
    private String anchor_point;
    private String signature;
    private String video_price;
    private String voice_price;
    private String age;
    private int gender;
    private String career;
    private String height;
    private String weight;
    private String city;
    private String birthday;
    private String constellation;
    private String is_anchor;
    private String status;
    private String last_login;
    private String last_online;
    private String regist_time;
    private String sharing_ratio;
    private String guildid;
    private String agentid;
    private String alipay_name;
    private String alipay_account;
    private String rec_weight;
    private String call_recive_count;
    private String call_accept_count;
    private String isattent;

    private String connect_rate;
    private String attent_count;
    private String fans_count;
    private String gift_spend;
    private ArrayList<GiftShow> gift_show;
    private ArrayList<Intimacys> intimacys;
    private  Profile profile;
    private int vip_level;
    private String vip_date;

    public String getVip_date() {
        return vip_date;
    }

    public void setVip_date(String vip_date) {
        this.vip_date = vip_date;
    }

    public int getVip_level() {
        return vip_level;
    }

    public void setVip_level(int vip_level) {
        this.vip_level = vip_level;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    private HotLive live;

    public HotLive getLive() {
        return live;
    }

    public void setLive(HotLive live) {
        this.live = live;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGold() {
        return gold;
    }

    public void setGold(String gold) {
        this.gold = gold;
    }

    public String getDiamond() {
        return diamond;
    }

    public void setDiamond(String diamond) {
        this.diamond = diamond;
    }

    public String getDiamond_total() {
        return diamond_total;
    }

    public void setDiamond_total(String diamond_total) {
        this.diamond_total = diamond_total;
    }

    public ArrayList<Tags> getTags() {
        return tags;
    }

    public void setTags(ArrayList<Tags> tags) {
        this.tags = tags;
    }

    public int getAnchor_level() {
        return anchor_level;
    }

    public void setAnchor_level(int anchor_level) {
        this.anchor_level = anchor_level;
    }

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getAnchor_point() {
        return anchor_point;
    }

    public void setAnchor_point(String anchor_point) {
        this.anchor_point = anchor_point;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getVideo_price() {
        return video_price;
    }

    public void setVideo_price(String video_price) {
        this.video_price = video_price;
    }

    public String getVoice_price() {
        return voice_price;
    }

    public void setVoice_price(String voice_price) {
        this.voice_price = voice_price;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getConstellation() {
        return constellation;
    }

    public void setConstellation(String constellation) {
        this.constellation = constellation;
    }

    public String getIs_anchor() {
        return is_anchor;
    }

    public void setIs_anchor(String is_anchor) {
        this.is_anchor = is_anchor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public String getLast_online() {
        return last_online;
    }

    public void setLast_online(String last_online) {
        this.last_online = last_online;
    }

    public String getRegist_time() {
        return regist_time;
    }

    public void setRegist_time(String regist_time) {
        this.regist_time = regist_time;
    }

    public String getSharing_ratio() {
        return sharing_ratio;
    }

    public void setSharing_ratio(String sharing_ratio) {
        this.sharing_ratio = sharing_ratio;
    }

    public String getGuildid() {
        return guildid;
    }

    public void setGuildid(String guildid) {
        this.guildid = guildid;
    }

    public String getAgentid() {
        return agentid;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public String getAlipay_name() {
        return alipay_name;
    }

    public void setAlipay_name(String alipay_name) {
        this.alipay_name = alipay_name;
    }

    public String getAlipay_account() {
        return alipay_account;
    }

    public void setAlipay_account(String alipay_account) {
        this.alipay_account = alipay_account;
    }

    public String getRec_weight() {
        return rec_weight;
    }

    public void setRec_weight(String rec_weight) {
        this.rec_weight = rec_weight;
    }

    public String getCall_recive_count() {
        return call_recive_count;
    }

    public void setCall_recive_count(String call_recive_count) {
        this.call_recive_count = call_recive_count;
    }

    public String getCall_accept_count() {
        return call_accept_count;
    }

    public void setCall_accept_count(String call_accept_count) {
        this.call_accept_count = call_accept_count;
    }

    public String getIsattent() {
        return isattent;
    }

    public void setIsattent(String isattent) {
        this.isattent = isattent;
    }



    public String getConnect_rate() {
        return connect_rate;
    }

    public void setConnect_rate(String connect_rate) {
        this.connect_rate = connect_rate;
    }

    public String getAttent_count() {
        return attent_count;
    }

    public void setAttent_count(String attent_count) {
        this.attent_count = attent_count;
    }

    public String getFans_count() {
        return fans_count;
    }

    public void setFans_count(String fans_count) {
        this.fans_count = fans_count;
    }

    public String getGift_spend() {
        return gift_spend;
    }

    public void setGift_spend(String gift_spend) {
        this.gift_spend = gift_spend;
    }

    public ArrayList<GiftShow> getGift_show() {
        return gift_show;
    }

    public void setGift_show(ArrayList<GiftShow> gift_show) {
        this.gift_show = gift_show;
    }

    public ArrayList<Intimacys> getIntimacys() {
        return intimacys;
    }

    public void setIntimacys(ArrayList<Intimacys> intimacys) {
        this.intimacys = intimacys;
    }
}
