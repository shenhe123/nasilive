package com.feicui365.live.model.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Filters implements Serializable {
    private Bitmap filterBitmap;
    private int filterSrc;
    private int unFilterSrc;
    private int tag;
    private float specialRatio = 5f;

    public float getSpecialRatio() {
        return specialRatio;
    }

    public void setSpecialRatio(float specialRatio) {
        this.specialRatio = specialRatio;
    }

    public Bitmap getFilterBitmap() {
        return filterBitmap;
    }

    public void setFilterBitmap(Bitmap filterBitmap) {
        this.filterBitmap = filterBitmap;
    }

    public int getFilterSrc() {
        return filterSrc;
    }

    public void setFilterSrc(int filterSrc) {
        this.filterSrc = filterSrc;
    }

    public int getUnFilterSrc() {
        return unFilterSrc;
    }

    public void setUnFilterSrc(int unFilterSrc) {
        this.unFilterSrc = unFilterSrc;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
