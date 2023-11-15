package com.feicui365.live.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ScreenResult implements Serializable {
    ArrayList<Screen> select_math;

    public ScreenResult(ArrayList<Screen> select_math) {
        this.select_math = select_math;
    }

    public ArrayList<Screen> getSelect_math() {
        return select_math;
    }

    public void setSelect_math(ArrayList<Screen> select_math) {
        this.select_math = select_math;
    }
}
