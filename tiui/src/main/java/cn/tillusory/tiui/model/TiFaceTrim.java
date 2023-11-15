package cn.tillusory.tiui.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import cn.tillusory.tiui.R;

/**
 * Created by Anko on 2018/11/25.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public enum TiFaceTrim {
    EYE_MAGNIFYING(cn.tillusory.sdk.R.string.eye_magnifying, R.drawable.ic_ti_eye_magnifying),
    CHIN_SLIMMING(cn.tillusory.sdk.R.string.chin_slimming, R.drawable.ic_ti_chin_slimming),
    FACE_NARROWING(cn.tillusory.sdk.R.string.face_narrowing, R.drawable.ic_ti_face_narrowing),
    JAW_TRANSFORMING(cn.tillusory.sdk.R.string.jaw_transforming, R.drawable.ic_ti_jaw_transforming),
    FOREHEAD_TRANSFORMING(cn.tillusory.sdk.R.string.forehead_transforming, R.drawable.ic_ti_forehead_transforming),
    MOUTH_TRANSFORMING(cn.tillusory.sdk.R.string.mouth_transforming, R.drawable.ic_ti_mouth_transforming),
    NOSE_MINIFYING(cn.tillusory.sdk.R.string.nose_minifying, R.drawable.ic_ti_nose_minifying),
    TEETH_WHITENING(cn.tillusory.sdk.R.string.teeth_whitening, R.drawable.ic_ti_teeth_whitening),
    EYE_SPACING(cn.tillusory.sdk.R.string.eye_spacing, R.drawable.ic_ti_eye_spacing),
    NOSE_ELONGATING(cn.tillusory.sdk.R.string.nose_elongating, R.drawable.ic_ti_nose_elongating),
    EYE_CORNERS(cn.tillusory.sdk.R.string.eye_corners, R.drawable.ic_ti_eye_corners);

    private int stringId;
    private int imageId;

    TiFaceTrim(@StringRes int stringId, @DrawableRes int imageId) {
        this.stringId = stringId;
        this.imageId = imageId;
    }

    public String getString(@NonNull Context context) {
        return context.getResources().getString(stringId);
    }

    public Drawable getImageDrawable(@NonNull Context context) {
        return context.getResources().getDrawable(imageId);
    }
}