package cn.tillusory.tiui.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import cn.tillusory.sdk.bean.TiFilterEnum;
import cn.tillusory.tiui.R;

/**
 * Created by Anko on 2018/11/26.
 * Copyright (c) 2018-2020 拓幻科技 - tillusory.cn. All rights reserved.
 */
public enum TiFilter {
    NO_FILTER(TiFilterEnum.NO_FILTER, R.drawable.ic_ti_filter_0),
    DJANGO_FILTER(TiFilterEnum.DJANGO_FILTER, R.drawable.ic_ti_filter_50),
    CLOUSEAU_FILTER(TiFilterEnum.CLOUSEAU_FILTER, R.drawable.ic_ti_filter_46),
    CONTRAIL_FILTER(TiFilterEnum.CONTRAIL_FILTER, R.drawable.ic_ti_filter_48),
    COBI_FILTER(TiFilterEnum.COBI_FILTER, R.drawable.ic_ti_filter_47),
    CHEMICAL_FILTER(TiFilterEnum.CHEMICAL_FILTER, R.drawable.ic_ti_filter_44),
    BYERS_FILTER(TiFilterEnum.BYERS_FILTER, R.drawable.ic_ti_filter_43),
    AZREAL_FILTER(TiFilterEnum.AZREAL_FILTER, R.drawable.ic_ti_filter_41),
    VIGNETTE_FILTER(TiFilterEnum.VIGNETTE_FILTER, R.drawable.ic_ti_filter_32),
    RED_FILTER(TiFilterEnum.RED_FILTER, R.drawable.ic_ti_filter_26),
    LOLITA_FILTER(TiFilterEnum.LOLITA_FILTER, R.drawable.ic_ti_filter_20),
    KISS_FILTER(TiFilterEnum.KISS_FILTER, R.drawable.ic_ti_filter_19),
    NASHVILLE_FILTER(TiFilterEnum.NASHVILLE_FILTER, R.drawable.ic_ti_filter_9),
    NORMAL_FILTER(TiFilterEnum.NORMAL_FILTER, R.drawable.ic_ti_filter_23),
    CHOCOLATE_FILTER(TiFilterEnum.CHOCOLATE_FILTER, R.drawable.ic_ti_filter_11),
    OXGEN_FILTER(TiFilterEnum.OXGEN_FILTER, R.drawable.ic_ti_filter_24),
    FOREST_FILTER(TiFilterEnum.FOREST_FILTER, R.drawable.ic_ti_filter_15),
    FIRSTLOVE_FILTER(TiFilterEnum.FIRSTLOVE_FILTER, R.drawable.ic_ti_filter_14),
    GRASS_FILTER(TiFilterEnum.GRASS_FILTER, R.drawable.ic_ti_filter_17),
    HOLIDAY_FILTER(TiFilterEnum.HOLIDAY_FILTER, R.drawable.ic_ti_filter_18),
    CLAYTON_FILTER(TiFilterEnum.CLAYTON_FILTER, R.drawable.ic_ti_filter_45),
    BOURBON_FILTER(TiFilterEnum.BOURBON_FILTER, R.drawable.ic_ti_filter_42),
    AVA_FILTER(TiFilterEnum.AVA_FILTER, R.drawable.ic_ti_filter_40),
    PLATYCODON_FILTER(TiFilterEnum.PLATYCODON_FILTER, R.drawable.ic_ti_filter_25),
    CUBICLE_FILTER(TiFilterEnum.CUBICLE_FILTER, R.drawable.ic_ti_filter_49),
    GLOSSY_FILTER(TiFilterEnum.GLOSSY_FILTER, R.drawable.ic_ti_filter_16),
    ARABICA_FILTER(TiFilterEnum.ARABICA_FILTER, R.drawable.ic_ti_filter_39),
    COFFEE_FILTER(TiFilterEnum.COFFEE_FILTER, R.drawable.ic_ti_filter_10),
    COCO_FILTER(TiFilterEnum.COCO_FILTER, R.drawable.ic_ti_filter_12),
    DELICIOUS_FILTER(TiFilterEnum.DELICIOUS_FILTER, R.drawable.ic_ti_filter_13),
    MOUSSE_FILTER(TiFilterEnum.MOUSSE_FILTER, R.drawable.ic_ti_filter_22),
    SUNLESS_FILTER(TiFilterEnum.SUNLESS_FILTER, R.drawable.ic_ti_filter_27),
    MEMORY_FILTER(TiFilterEnum.MEMORY_FILTER, R.drawable.ic_ti_filter_21),
    KUWAHARA_FILTER(TiFilterEnum.KUWAHARA_FILTER, R.drawable.ic_ti_filter_29),
    INK_WASH_PAINTING_FILTER(TiFilterEnum.INK_WASH_PAINTING_FILTER, R.drawable.ic_ti_filter_38),
    POSTERIZE_FILTER(TiFilterEnum.POSTERIZE_FILTER, R.drawable.ic_ti_filter_30),
    FILM_FILTER(TiFilterEnum.FILM_FILTER, R.drawable.ic_ti_filter_5),
    SOLARIZE_FILTER(TiFilterEnum.SOLARIZE_FILTER, R.drawable.ic_ti_filter_37),
    GLASS_SPHERE_REFRACTION_FILTER(TiFilterEnum.GLASS_SPHERE_REFRACTION_FILTER, R.drawable.ic_ti_filter_36),
    POLAR_PIXELLATE_FILTER(TiFilterEnum.POLAR_PIXELLATE_FILTER, R.drawable.ic_ti_filter_35),
    POLKA_DOT_FILTER(TiFilterEnum.POLKA_DOT_FILTER, R.drawable.ic_ti_filter_34),
    ZOOM_BLUR_FILTER(TiFilterEnum.ZOOM_BLUR_FILTER, R.drawable.ic_ti_filter_33),
    SWIRL_DISTORTION_FILTER(TiFilterEnum.SWIRL_DISTORTION_FILTER, R.drawable.ic_ti_filter_31),
    PINCH_DISTORTION_FILTER(TiFilterEnum.PINCH_DISTORTION_FILTER, R.drawable.ic_ti_filter_28),
    CROSSHATCH_FILTER(TiFilterEnum.CROSSHATCH_FILTER, R.drawable.ic_ti_filter_8),
    HALFTONE_FILTER(TiFilterEnum.HALFTONE_FILTER, R.drawable.ic_ti_filter_7),
    PIXELATION_FILTER(TiFilterEnum.PIXELATION_FILTER, R.drawable.ic_ti_filter_6),
    EMBOSS_FILTER(TiFilterEnum.EMBOSS_FILTER, R.drawable.ic_ti_filter_4),
    CARTOON_FILTER(TiFilterEnum.CARTOON_FILTER, R.drawable.ic_ti_filter_3),
    SOBEL_EDGE_FILTER(TiFilterEnum.SOBEL_EDGE_FILTER, R.drawable.ic_ti_filter_2),
    SKETCH_FILTER(TiFilterEnum.SKETCH_FILTER, R.drawable.ic_ti_filter_1);

    private TiFilterEnum filterEnum;
    private int imageId;

    TiFilter(TiFilterEnum filterEnum, @DrawableRes int imageId) {
        this.filterEnum = filterEnum;
        this.imageId = imageId;
    }

    public TiFilterEnum getFilterEnum() {
        return filterEnum;
    }

    public String getString(@NonNull Context context) {
        return context.getResources().getString(filterEnum.getStringId());
    }

    public Drawable getImageDrawable(@NonNull Context context) {
        return context.getResources().getDrawable(imageId);
    }
}
