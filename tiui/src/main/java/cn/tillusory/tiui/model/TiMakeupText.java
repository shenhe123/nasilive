package cn.tillusory.tiui.model;

import android.content.Context;
import android.support.annotation.StringRes;

import cn.tillusory.tiui.R;

public enum TiMakeupText {
    NO_MAKEUP(R.string.none),
    QINGSE(R.string.qingse),
    RIZA(R.string.riza),
    TIANCHENG(R.string.tiancheng),
    YOUYA(R.string.youya),
    WEIXUN(R.string.weixun),
    XINDONG(R.string.xindong),
    BIAOZHUN(R.string.biaozhunmei),
    JIAN(R.string.jianmei),
    LIUYE(R.string.liuyemei),
    PINGZHI(R.string.pingzhimei),
    LIUXING(R.string.liuxingmei),
    OUSHI(R.string.oushimei),
    ZIRAN(R.string.ziran),
    ROUHE(R.string.rouhe),
    NONGMI(R.string.nongmi),
    MEIHUO(R.string.meihuo),
    BABI(R.string.babi),
    WUMEI(R.string.wumei);

    private int stringId;

    TiMakeupText(@StringRes int stringId) {
        this.stringId = stringId;
    }

    public String getString(Context context) {
        return context.getResources().getString(stringId);
    }
}
