package com.feicui365.live.shop.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class CartGoodInfo implements Serializable,Cloneable {
   ArrayList<CartGoods> cartgoods;
   int id;
   String operate_time;
   Shop shop;
   int shopid;
   int uid;
   boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public CartGoodInfo() {
    }



    public ArrayList<CartGoods> getCartgoods() {
        return cartgoods;
    }

    public void setCartgoods(ArrayList<CartGoods> cartgoods) {
        this.cartgoods = cartgoods;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(String operate_time) {
        this.operate_time = operate_time;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }


    @Override
    public Object clone()  {
        //重写clone

            Object o = null;
            try {
                o = super.clone();   //调用父类的clone
            } catch (CloneNotSupportedException e) {    //异常捕获
                e.printStackTrace();
            }
            return o;


    }
}
