package cn.tillusory.tiui.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import cn.tillusory.tiui.R;

public enum TiQuickBeauty {
    STANDARD(R.string.standard, R.drawable.ic_ti_standard),
    DELICATE(R.string.delicate, R.drawable.ic_ti_delicate),
    CUTE(R.string.cute, R.drawable.ic_ti_cute),
    CELEBRITY(R.string.celebrity, R.drawable.ic_ti_celebrity),
    NATURAL(R.string.natural, R.drawable.ic_ti_natural);

    private int stringId;
    private int imageId;

    TiQuickBeauty(@StringRes int stringId, @DrawableRes int imageId) {
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
