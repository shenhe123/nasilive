package cn.tillusory.tiui.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import cn.tillusory.sdk.bean.TiRockEnum;
import cn.tillusory.tiui.R;

/**
 * Created by Anko on 2018/11/28.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public enum TiRock {
    NO_FILTER(TiRockEnum.NO_ROCK, R.drawable.ic_ti_rock_0),
    DAZZLED_COLOR_ROCK(TiRockEnum.DAZZLED_COLOR_ROCK, R.drawable.ic_ti_rock_1),
    LIGHT_COLOR_ROCK(TiRockEnum.LIGHT_COLOR_ROCK, R.drawable.ic_ti_rock_2),
    DIZZY_GIDDY_ROCK(TiRockEnum.DIZZY_GIDDY_ROCK, R.drawable.ic_ti_rock_3),
    ASTRAL_PROJECTION_ROCK(TiRockEnum.ASTRAL_PROJECTION_ROCK, R.drawable.ic_ti_rock_4),
    BLACK_MAGIC_ROCK(TiRockEnum.BLACK_MAGIC_ROCK, R.drawable.ic_ti_rock_5),
    VIRTUAL_MIRROR_ROCK(TiRockEnum.VIRTUAL_MIRROR_ROCK, R.drawable.ic_ti_rock_6),
    DYNAMIC_SPLIT_SCREEN_ROCK(TiRockEnum.DYNAMIC_SPLIT_SCREEN_ROCK, R.drawable.ic_ti_rock_7),
    BLACK_WHITE_FILM_ROCK(TiRockEnum.BLACK_WHITE_FILM_ROCK, R.drawable.ic_ti_rock_8),
    GRAY_PETRIFACTION_ROCK(TiRockEnum.GRAY_PETRIFACTION_ROCK, R.drawable.ic_ti_rock_9),
    BULGE_DISTORTION_ROCK(TiRockEnum.BULGE_DISTORTION_ROCK, R.drawable.ic_ti_rock_10);

    private TiRockEnum rockEnum;
    private int imageId;

    TiRock(TiRockEnum rockEnum, @DrawableRes int imageId) {
        this.rockEnum = rockEnum;
        this.imageId = imageId;
    }

    public TiRockEnum getRockEnum() {
        return rockEnum;
    }

    public String getString(@NonNull Context context) {
        return context.getResources().getString(rockEnum.getStringId());
    }

    public Drawable getImageDrawable(@NonNull Context context) {
        return context.getResources().getDrawable(imageId);
    }
}
