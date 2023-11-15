package com.feicui365.live.model.entity;

import java.io.Serializable;

/**
 * 登录所需的配置类
 */
public class LoginConfig implements Serializable {
    private String qcloud_appid;
    private int im_sdkappid;
    private String cos_bucket;
    private String cos_region;
    private String wx_appid;
    private String universal_link;
    private String socket_server;
    private String cos_folder_image;
    private String cos_folder_blurimage;
    private String room_notice;
    private int beauty_channel;
    private String txim_admin;
    private String tisdk_key;
    private String txim_broadcast;
    private String share_live_url;
    private String share_shortvideo_url;
    private String share_moment_url;
    private String version_android;
    private String update_info_android;
    private String dl_web_url;
    private String txim_notify_attent;
    private String txim_notify_comment;
    private String txim_notify_zan;
    private String site_domain;
    private String shop_commission;

    public String getTxim_notify_zan() {
        return txim_notify_zan;
    }

    public void setTxim_notify_zan(String txim_notify_zan) {
        this.txim_notify_zan = txim_notify_zan;
    }

    public String getTxim_notify_attent() {
        return txim_notify_attent;
    }

    public void setTxim_notify_attent(String txim_notify_attent) {
        this.txim_notify_attent = txim_notify_attent;
    }

    public String getTxim_notify_comment() {
        return txim_notify_comment;
    }

    public void setTxim_notify_comment(String txim_notify_comment) {
        this.txim_notify_comment = txim_notify_comment;
    }

    public String getSite_domain() {
        return site_domain;
    }

    public void setSite_domain(String site_domain) {
        this.site_domain = site_domain;
    }

    public String getTisdk_key() {
        return tisdk_key;
    }

    public void setTisdk_key(String tisdk_key) {
        this.tisdk_key = tisdk_key;
    }

    public String getDl_web_url() {
        return dl_web_url;
    }

    public void setDl_web_url(String dl_web_url) {
        this.dl_web_url = dl_web_url;
    }

    public String getVersion_android() {
        return version_android;
    }

    public void setVersion_android(String version_android) {
        this.version_android = version_android;
    }

    public String getUpdate_info_android() {
        return update_info_android;
    }

    public void setUpdate_info_android(String update_info_android) {
        this.update_info_android = update_info_android;
    }

    private String exchange_rate;
    private String withdraw_min;
    private String agent_ratio;

    public String getAgent_ratio() {
        return agent_ratio;
    }

    public void setAgent_ratio(String agent_ratio) {
        this.agent_ratio = agent_ratio;
    }

    public String getShare_live_url() {
        return share_live_url;
    }

    public void setShare_live_url(String share_live_url) {
        this.share_live_url = share_live_url;
    }

    public String getShare_shortvideo_url() {
        return share_shortvideo_url;
    }

    public void setShare_shortvideo_url(String share_shortvideo_url) {
        this.share_shortvideo_url = share_shortvideo_url;
    }

    public String getShare_moment_url() {
        return share_moment_url;
    }

    public void setShare_moment_url(String share_moment_url) {
        this.share_moment_url = share_moment_url;
    }

    public String getTxim_admin() {
        if(txim_admin==null){
            return "admin";
        }
        return txim_admin;
    }

    public void setTxim_admin(String txim_admin) {
        this.txim_admin = txim_admin;
    }

    public String getTxim_broadcast() {
        if(txim_broadcast==null){
            return "broadcast";
        }
        return txim_broadcast;
    }

    public void setTxim_broadcast(String txim_broadcast) {
        this.txim_broadcast = txim_broadcast;
    }

    public int getBeauty_channel() {

        return beauty_channel;
    }

    public void setBeauty_channel(int beauty_channel) {
        this.beauty_channel = beauty_channel;
    }


    public String getWithdraw_min() {
        return withdraw_min;
    }

    public void setWithdraw_min(String withdraw_min) {
        this.withdraw_min = withdraw_min;
    }

    public String getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(String exchange_rate) {
        this.exchange_rate = exchange_rate;
    }

    public String getQcloud_appid() {
        return qcloud_appid;
    }

    public void setQcloud_appid(String qcloud_appid) {
        this.qcloud_appid = qcloud_appid;
    }

    public int getIm_sdkappid() {
        return im_sdkappid;
    }

    public void setIm_sdkappid(int im_sdkappid) {
        this.im_sdkappid = im_sdkappid;
    }

    public String getCos_bucket() {
        return cos_bucket;
    }

    public void setCos_bucket(String cos_bucket) {
        this.cos_bucket = cos_bucket;
    }

    public String getCos_region() {
        return cos_region;
    }

    public void setCos_region(String cos_region) {
        this.cos_region = cos_region;
    }

    public String getWx_appid() {
        return wx_appid;
    }

    public void setWx_appid(String wx_appid) {
        this.wx_appid = wx_appid;
    }

    public String getUniversal_link() {
        return universal_link;
    }

    public void setUniversal_link(String universal_link) {
        this.universal_link = universal_link;
    }

    public String getSocket_server() {
        return socket_server;
    }

    public void setSocket_server(String socket_server) {
        this.socket_server = socket_server;
    }

    public String getCos_folder_image() {
        return cos_folder_image;
    }

    public void setCos_folder_image(String cos_folder_image) {
        this.cos_folder_image = cos_folder_image;
    }

    public String getCos_folder_blurimage() {
        return cos_folder_blurimage;
    }

    public void setCos_folder_blurimage(String cos_folder_blurimage) {
        this.cos_folder_blurimage = cos_folder_blurimage;
    }

    public String getRoom_notice() {
        return room_notice;
    }

    public void setRoom_notice(String room_notice) {
        this.room_notice = room_notice;
    }

    public String getShop_commission() {
        return shop_commission;
    }

    public void setShop_commission(String shop_commission) {
        this.shop_commission = shop_commission;
    }
}
