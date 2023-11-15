package com.feicui365.live.model.entity;



import java.util.ArrayList;

/**
 * @author zwg
 * @description:
 * @date :2021/11/23 17:28
 */
public class CityBean{
    String code;
    ArrayList<CityChildBean> data;
    String message;

    public CityBean() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<CityChildBean> getData() {
        return data;
    }

    public void setData(ArrayList<CityChildBean> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}





