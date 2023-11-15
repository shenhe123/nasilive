package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class Good implements Serializable {
    int categoryid;
    String create_time;
    int delivery;
    String desc;
    String desc_img_urls;
    int freight;
    String id;
    String live_explaining;
    String price;
    int sale_count;
    String shopid;
    Shop shop;
    int status;
    String thumb_urls;
    String title;
    String address;
    String[] thumb_urls_list;


    public Good() {
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc_img_urls() {
        return desc_img_urls;
    }

    public void setDesc_img_urls(String desc_img_urls) {
        this.desc_img_urls = desc_img_urls;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLive_explaining() {
        return live_explaining;
    }

    public void setLive_explaining(String live_explaining) {
        this.live_explaining = live_explaining;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getSale_count() {
        return sale_count;
    }

    public void setSale_count(int sale_count) {
        this.sale_count = sale_count;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getThumb_urls() {
        return thumb_urls;
    }

    public void setThumb_urls(String thumb_urls) {
        this.thumb_urls = thumb_urls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getThumb_urls_list() {
        return thumb_urls_list;
    }

    public void setThumb_urls_list(String[] thumb_urls_list) {
        this.thumb_urls_list = thumb_urls_list;
    }
}
