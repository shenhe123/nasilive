package com.feicui365.live.live.bean;

/**
 *
 */
public class GroupUserData {

    int is_admin;
    int is_forbid_send_msg;
    String user_ip;
    String area ;

    public GroupUserData() {
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public int getIs_forbid_send_msg() {
        return is_forbid_send_msg;
    }

    public void setIs_forbid_send_msg(int is_forbid_send_msg) {
        this.is_forbid_send_msg = is_forbid_send_msg;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
