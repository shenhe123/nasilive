package cn.tillusory.tiui.custom;

import android.content.Context;
import android.content.SharedPreferences;

public class TiSharePreferences {
    private static TiSharePreferences instance;
    private SharedPreferences sharedPreferences;

    private static final String SP_KEY_QUICK_BEAUTY_STANDARD = "SP_KEY_QUICK_BEAUTY_STANDARD";
    private static final String SP_KEY_QUICK_BEAUTY_DELICATE = "SP_KEY_QUICK_BEAUTY_DELICATE";
    private static final String SP_KEY_QUICK_BEAUTY_CUTE = "SP_KEY_QUICK_BEAUTY_CUTE";
    private static final String SP_KEY_QUICK_BEAUTY_CELEBRITY = "SP_KEY_QUICK_BEAUTY_CELEBRITY";
    private static final String SP_KEY_QUICK_BEAUTY_NATURAL = "SP_KEY_QUICK_BEAUTY_NATURAL";

    private static final String SP_KEY_MAKEUP_ENABLE = "SP_KEY_MAKEUP_ENABLE";

//    private static final String SP_KEY_MAKEUP_PURE = "SP_KEY_MAKEUP_PURE";
//    private static final String SP_KEY_MAKEUP_JAPANESE = "SP_KEY_MAKEUP_JAPANESE";
//    private static final String SP_KEY_MAKEUP_SWEET = "SP_KEY_MAKEUP_SWEET";
//    private static final String SP_KEY_MAKEUP_ELEGANT = "SP_KEY_MAKEUP_ELEGANT";
//    private static final String SP_KEY_MAKEUP_INTOXICATE = "SP_KEY_MAKEUP_INTOXICATE";
//    private static final String SP_KEY_MAKEUP_HEARTBEAT = "SP_KEY_MAKEUP_HEARTBEAT";

    public static TiSharePreferences getInstance() {
        if (instance == null) {
            synchronized (TiSharePreferences.class) {
                if (instance == null) {
                    instance = new TiSharePreferences();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences("TiSharePreferences", Context.MODE_PRIVATE);
    }

    public void putBooleanVal(String key, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putIntVal(String key, int value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 一键美颜
     */
    public void putStandardVal(int value) {
        putIntVal(SP_KEY_QUICK_BEAUTY_STANDARD, value);
    }

    public int getStandardVal() {
        return sharedPreferences.getInt(SP_KEY_QUICK_BEAUTY_STANDARD, 100);
    }

    public void putDelicateVal(int value) {
        putIntVal(SP_KEY_QUICK_BEAUTY_DELICATE, value);
    }

    public int getDelicateVal() {
        return sharedPreferences.getInt(SP_KEY_QUICK_BEAUTY_DELICATE, 100);
    }

    public void putCuteVal(int value) {
        putIntVal(SP_KEY_QUICK_BEAUTY_CUTE, value);
    }

    public int getCuteVal() {
        return sharedPreferences.getInt(SP_KEY_QUICK_BEAUTY_CUTE, 100);
    }

    public void putCelebrityVal(int value) {
        putIntVal(SP_KEY_QUICK_BEAUTY_CELEBRITY, value);
    }

    public int getCelebrityVal() {
        return sharedPreferences.getInt(SP_KEY_QUICK_BEAUTY_CELEBRITY, 100);
    }

    public void putNaturalVal(int value) {
        putIntVal(SP_KEY_QUICK_BEAUTY_NATURAL, value);
    }

    public int getNaturalVal() {
        return sharedPreferences.getInt(SP_KEY_QUICK_BEAUTY_NATURAL, 100);
    }

    /**
     * 美妆
     */
    public void putMakeupEnable(boolean value) {
        putBooleanVal(SP_KEY_MAKEUP_ENABLE, value);
    }

    public boolean isMakeupEnable() {
        return sharedPreferences.getBoolean(SP_KEY_MAKEUP_ENABLE, true);
    }

    public void putBlusherVal(String key, int value) {
        putIntVal(key, value);
    }

    public int getBlusherVal(String key) {
        return sharedPreferences.getInt(key, 100);
    }

    public void putEyelashVal(String key, int value) {
        putIntVal(key, value);
    }

    public int getEyelashVal(String key) {
        return sharedPreferences.getInt(key, 100);
    }

    public void putEyebrowVal(String key, int value) {
        putIntVal(key, value);
    }

    public int getEyebrowVal(String key) {
        return sharedPreferences.getInt(key, 100);
    }

}
