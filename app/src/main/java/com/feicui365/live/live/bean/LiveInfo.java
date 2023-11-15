package com.feicui365.live.live.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import com.feicui365.live.model.entity.Pkinfo;
import com.feicui365.live.model.entity.UserRegist;

import java.io.Serializable;

/**
 *
 */
public class LiveInfo implements Serializable, MultiItemEntity {

    private String anchorid;
    private String liveid;
    private String title;
    private String thumb;
    private int isvideo;
    private String stream;
    private String pull_url;
    private String push_url;
    private String acc_pull_url;
    private String categoryid;
    private int orientation;
    private int status;
    private String start_stamp;
    private String end_stamp;
    private String start_time;
    private String end_time;
    private int profit;
    private int hot;
    private String chatroomid;
    private UserRegist anchor;
    private String token;
    private String nick_name;
    private String password;
    private String wx_unionid;
    private String online_status;
    private String avatar;
    private int gold;
    private String diamond;
    private String diamond_total;
    private String tags;
    private int price;
    private int room_type;

    private int link_on;
    private int link_status;
    private String pkid;
    private int pk_status;
    private Pkinfo pkinfo;
    private LiveInfo pklive;

    public LiveInfo() {
    }

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
    }

    public String getLiveid() {
        return liveid;
    }

    public void setLiveid(String liveid) {
        this.liveid = liveid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getIsvideo() {
        return isvideo;
    }

    public void setIsvideo(int isvideo) {
        this.isvideo = isvideo;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getPull_url() {
        return pull_url;
    }

    public void setPull_url(String pull_url) {
        this.pull_url = pull_url;
    }

    public String getPush_url() {
        return push_url;
    }

    public void setPush_url(String push_url) {
        this.push_url = push_url;
    }

    public String getAcc_pull_url() {
        return acc_pull_url;
    }

    public void setAcc_pull_url(String acc_pull_url) {
        this.acc_pull_url = acc_pull_url;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStart_stamp() {
        return start_stamp;
    }

    public void setStart_stamp(String start_stamp) {
        this.start_stamp = start_stamp;
    }

    public String getEnd_stamp() {
        return end_stamp;
    }

    public void setEnd_stamp(String end_stamp) {
        this.end_stamp = end_stamp;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getChatroomid() {
        return chatroomid;
    }

    public void setChatroomid(String chatroomid) {
        this.chatroomid = chatroomid;
    }

    public UserRegist getAnchor() {
        return anchor;
    }

    public void setAnchor(UserRegist anchor) {
        this.anchor = anchor;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWx_unionid() {
        return wx_unionid;
    }

    public void setWx_unionid(String wx_unionid) {
        this.wx_unionid = wx_unionid;
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

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRoom_type() {
        return room_type;
    }

    public void setRoom_type(int room_type) {
        this.room_type = room_type;
    }

    public int getLink_on() {
        return link_on;
    }

    public void setLink_on(int link_on) {
        this.link_on = link_on;
    }

    public int getLink_status() {
        return link_status;
    }

    public void setLink_status(int link_status) {
        this.link_status = link_status;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public int getPk_status() {
        return pk_status;
    }

    public void setPk_status(int pk_status) {
        this.pk_status = pk_status;
    }

    public Pkinfo getPkinfo() {
        return pkinfo;
    }

    public void setPkinfo(Pkinfo pkinfo) {
        this.pkinfo = pkinfo;
    }

    public LiveInfo getPklive() {
        return pklive;
    }

    public void setPklive(LiveInfo pklive) {
        this.pklive = pklive;
    }

    @Override
    public int getItemType() {
        if (room_type > -1) {
            return 1;
        } else {
            return 0;
        }

    }
}
