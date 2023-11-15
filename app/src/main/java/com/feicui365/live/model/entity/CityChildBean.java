package com.feicui365.live.model.entity;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;

/**
 *
 */
public class CityChildBean implements IPickerViewData {
    ArrayList<CityChildBean> child;
    String id;
    String name;

    public CityChildBean() {
    }

    public ArrayList<CityChildBean> getChild() {
        return child;
    }

    public void setChild(ArrayList<CityChildBean> child) {
        this.child = child;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getPickerViewText() {
        return name;
    }
}
