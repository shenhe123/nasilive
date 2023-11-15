package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.List;

public class SwiftMessageBean implements Serializable {

    /**
     * id : 2
     * title : 快捷语1
     * add_role : 2
     */

    private int id;
    private String title;
    private int add_role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAdd_role() {
        return add_role;
    }

    public void setAdd_role(int add_role) {
        this.add_role = add_role;
    }

}
