package com.feicui365.live.model.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class HotLive implements Serializable, MultiItemEntity {
    private String id;
    private String anchorid;
    private String title;
    private String thumb;
    private int isvideo;
    private String stream;
    private String pull_url;
    private String categoryid;
    private int orientation;
    private String status;
    private String start_stamp;
    private String end_stamp;
    private String start_time;
    private String end_time;
    private String gift_profit;
    private int hot;
    private String chatroomid;
    private UserRegist anchor;
    private String token;
    private String nick_name;
    private String account;
    private String password;
    private String wx_unionid;
    private String online_status;
    private String avatar;
    private String gold;
    private String diamond;
    private String diamond_total;
    private String tags;
    private String anchor_level;
    private String user_level;
    private String point;
    private String anchor_point;
    private String signature;
    private String video_price;
    private String voice_price;
    private String is_anchor;
    private String is_youliao;
    private String last_login;
    private String last_online;
    private String regist_time;
    private String auth_time;
    private String sharing_ratio;
    private String guildid;
    private String agentid;
    private String rec_weight;
    private String call_recive_count;
    private String call_accept_count;
    private String vip_date;
    private String svip_date;
    private String age;
    private String gender;
    private String career;
    private String height;
    private String weight;
    private String city;
    private String birthday;
    private String constellation;
    private String liveid;
    private String push_url;

    private String acc_pull_url;
    private int link_on;
    private int link_status;
    private String pkid;
    private int pk_status;
    private Pkinfo pkinfo;
    private HotLive pklive;
    private int profit;

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public String getAcc_pull_url() {
        return acc_pull_url;
    }

    public void setAcc_pull_url(String acc_pull_url) {
        this.acc_pull_url = acc_pull_url;
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

    public HotLive getPklive() {
        return pklive;
    }

    public void setPklive(HotLive pklive) {
        this.pklive = pklive;
    }

    public String getPush_url() {
        return push_url;
    }

    public void setPush_url(String push_url) {
        this.push_url = push_url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRoom_type() {
        return room_type;
    }

    public void setRoom_type(String room_type) {
        this.room_type = room_type;
    }

    private String price;
    private String room_type;
    private int type = 2;
    private HotLive live;

    public String getLiveid() {
        return liveid;
    }

    public void setLiveid(String liveid) {
        this.liveid = liveid;
    }

    public HotLive getLive() {
        return live;
    }

    public void setLive(HotLive live) {
        this.live = live;
    }

    public String getMyTitle() {
        return myTitle;
    }

    public void setMyTitle(String myTitle) {
        this.myTitle = myTitle;
    }

    private String myTitle;

    public int getType() {
        return type;
    }

    public void setType(int tag) {
        this.type = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnchorid() {
        return anchorid;
    }

    public void setAnchorid(String anchorid) {
        this.anchorid = anchorid;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getGift_profit() {
        return gift_profit;
    }

    public void setGift_profit(String gift_profit) {
        this.gift_profit = gift_profit;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getAnchor_level() {
        return anchor_level;
    }

    public void setAnchor_level(String anchor_level) {
        this.anchor_level = anchor_level;
    }

    public String getUser_level() {
        return user_level;
    }

    public void setUser_level(String user_level) {
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

    public String getIs_anchor() {
        return is_anchor;
    }

    public void setIs_anchor(String is_anchor) {
        this.is_anchor = is_anchor;
    }

    public String getIs_youliao() {
        return is_youliao;
    }

    public void setIs_youliao(String is_youliao) {
        this.is_youliao = is_youliao;
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

    public String getAuth_time() {
        return auth_time;
    }

    public void setAuth_time(String auth_time) {
        this.auth_time = auth_time;
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

    public String getVip_date() {
        return vip_date;
    }

    public void setVip_date(String vip_date) {
        this.vip_date = vip_date;
    }

    public String getSvip_date() {
        return svip_date;
    }

    public void setSvip_date(String svip_date) {
        this.svip_date = svip_date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
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

    @Override
    public int getItemType() {
        return 1;
    }
}
