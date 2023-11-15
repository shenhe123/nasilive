package com.feicui365.live.shop.entity;

import java.io.Serializable;

public class AddInventory implements Serializable {
    String color;
    int left_count;
    String price;
    String size;

    public AddInventory() {
    }

    public String getColor() {
        if(color==null){
            return "";
        }
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getLeft_count() {
        return left_count;
    }

    public void setLeft_count(int left_count) {
        this.left_count = left_count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSize() {
        if(size==null){
            return "";
        }
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
