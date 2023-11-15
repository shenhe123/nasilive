package cn.tillusory.tiui.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import cn.tillusory.sdk.bean.TiDistortionEnum;
import cn.tillusory.tiui.R;

/**
 * Created by Anko on 2018/11/26.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public enum TiDistortion {

    NO_DISTORTION(TiDistortionEnum.NO_DISTORTION, R.drawable.ic_ti_none),
    ET_DISTORTION(TiDistortionEnum.ET_DISTORTION, R.drawable.ic_ti_et),
    PEAR_FACE_DISTORTION(TiDistortionEnum.PEAR_FACE_DISTORTION, R.drawable.ic_ti_pear_face),
    SLIM_FACE_DISTORTION(TiDistortionEnum.SLIM_FACE_DISTORTION, R.drawable.ic_ti_slim_face),
    SQUARE_FACE_DISTORTION(TiDistortionEnum.SQUARE_FACE_DISTORTION, R.drawable.ic_ti_square_face);

    private TiDistortionEnum distortionEnum;
    private int imageId;

    TiDistortion(TiDistortionEnum distortionEnum, @DrawableRes int imageId) {
        this.distortionEnum = distortionEnum;
        this.imageId = imageId;
    }

    public TiDistortionEnum getDistortionEnum() {
        return distortionEnum;
    }

    public String getString(@NonNull Context context) {
        return context.getResources().getString(distortionEnum.getStringId());
    }

    public Drawable getImageDrawable(@NonNull Context context) {
        return context.getResources().getDrawable(imageId);
    }
}
