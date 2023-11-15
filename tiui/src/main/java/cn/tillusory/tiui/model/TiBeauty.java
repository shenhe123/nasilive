package cn.tillusory.tiui.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import cn.tillusory.tiui.R;

/**
 * Created by Anko on 2018/11/22.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public enum TiBeauty {
    WHITENING(cn.tillusory.sdk.R.string.skin_whitening, R.drawable.ic_ti_whitening),
    BLEMISH_REMOVAL(cn.tillusory.sdk.R.string.skin_blemish_removal, R.drawable.ic_ti_blemish_removal),
    BRIGHTNESS(cn.tillusory.sdk.R.string.skin_brightness, R.drawable.ic_ti_brightness),
    TENDERNESS(cn.tillusory.sdk.R.string.skin_tenderness, R.drawable.ic_ti_tenderness),
    SHARPNESS(cn.tillusory.sdk.R.string.skin_sharpness, R.drawable.ic_ti_sharpness);

    private int stringId;
    private int imageId;

    TiBeauty(@StringRes int stringId, @DrawableRes int imageId) {
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
