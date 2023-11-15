/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feicui365.live.live.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.feicui365.live.R;
import com.feicui365.live.base.Constants;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.bean.ImMessage;
import com.feicui365.live.dialog.InputCodeDialog;
import com.feicui365.live.interfaces.OnSendChatListener;
import com.feicui365.live.live.activity.LivePlayHorActivity2;
import com.feicui365.live.live.activity.LivePlayVerticalActivity2;
import com.feicui365.live.live.dialog.InputTipsDialog;
import com.feicui365.live.live.dialog.TipsDialog;
import com.feicui365.live.live.dialog.UserInfoDialog;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.nasinet.utils.AppManager;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * ================================================
 * 一些框架常用的工具
 * <p>
 * Created by JessYan on 2015/11/23.
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ArmsUtils {
    static public Toast mToast;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int TOP = 3;
    public static final int BOTTOM = 4;

    private static Dialog dialog2;

    private static int isEKYCSuccess = 0;

    public static boolean changeTheme = false;

    private ArmsUtils() {
        throw new IllegalStateException("you can't instantiate me!");
    }


    public static Map<Integer, Integer> mFanTasklist;
    public static Map<Integer, Integer> mUserTasklist;
    public static Map<Integer, Integer> mAnchorTsklist;

    public static String imToken;
    public static String accessToken;


    public static void setIsEKYCSuccess(int isEKYCSuccess) {
        ArmsUtils.isEKYCSuccess = isEKYCSuccess;
    }

    public static int getIsEKYCSuccess() {
        return isEKYCSuccess;
    }

    /**
     * 设置hint大小
     *
     * @param size
     * @param v
     * @param res
     */
    public static void setViewHintSize(Context context, int size, TextView v, int res) {
        SpannableString ss = new SpannableString(getResources(context).getString(
                res));
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(size, true);
        // 附加属性到文本  
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 设置hint  
        v.setHint(new SpannedString(ss)); // 一定要进行转换,否则属性会消失
    }


    /**
     * dp 转 px
     *
     * @param context {@link Context}
     * @param dpValue {@code dpValue}
     * @return {@code pxValue}
     */
    public static int dip2px(@NonNull Context context, float dpValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param context {@link Context}
     * @param pxValue {@code pxValue}
     * @return {@code dpValue}
     */
    public static int pix2dip(@NonNull Context context, int pxValue) {
        final float scale = getResources(context).getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param context {@link Context}
     * @param spValue {@code spValue}
     * @return {@code pxValue}
     */
    public static int sp2px(@NonNull Context context, float spValue) {
        final float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param context {@link Context}
     * @param pxValue {@code pxValue}
     * @return {@code spValue}
     */
    public static int px2sp(@NonNull Context context, float pxValue) {
        final float fontScale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 获得资源
     */
    public static Resources getResources(Context context) {
        return context.getResources();
    }

    /**
     * 得到字符数组
     */
    public static String[] getStringArray(Context context, int id) {
        return getResources(context).getStringArray(id);
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param id
     * @return
     */
    public static int getDimens(Context context, int id) {
        return (int) getResources(context).getDimension(id);
    }

    /**
     * 从 dimens 中获得尺寸
     *
     * @param context
     * @param dimenName
     * @return
     */
    public static float getDimens(Context context, String dimenName) {
        return getResources(context).getDimension(getResources(context).getIdentifier(dimenName, "dimen", context.getPackageName()));
    }

    /**
     * 从String 中获得字符
     *
     * @return
     */

    public static String getString(Context context, int stringID) {
        return getResources(context).getString(stringID);
    }

    public static String getString(Context context, int stringID, Object... s) {
        return getResources(context).getString(stringID, s);
    }


    /**
     * 从String 中获得字符
     *
     * @return
     */
    public static String getString(Context context, String strName) {
        return getString(context, getResources(context).getIdentifier(strName, "string", context.getPackageName()));
    }

    /**
     * findview
     *
     * @param view
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, View view, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        return view.findViewById(id);
    }

    /**
     * findview
     *
     * @param activity
     * @param viewName
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewByName(Context context, Activity activity, String viewName) {
        int id = getResources(context).getIdentifier(viewName, "id", context.getPackageName());
        return activity.findViewById(id);
    }

    /**
     * 根据 layout 名字获得 id
     *
     * @param layoutName
     * @return
     */
    public static int findLayout(Context context, String layoutName) {
        return getResources(context).getIdentifier(layoutName, "layout", context.getPackageName());
    }

    /**
     * 填充view
     *
     * @param detailScreen
     * @return
     */
    public static View inflate(Context context, int detailScreen) {
        return View.inflate(context, detailScreen, null);
    }

    /**
     * 单例 toast
     *
     * @param string
     */
    @SuppressLint("ShowToast")
    public static void makeText(Context context, String string) {
        if (string.contains("Unable to resolve host")) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        }
        mToast.setText(string);
        mToast.show();
    }

    @SuppressLint("ShowToast")
    public static void makeText(Context context, int ResId) {

        String string = getString(context, ResId);
        if (mToast == null) {
            mToast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        }
        mToast.setText(string);
        mToast.show();
    }


    /**
     * 通过资源id获得drawable
     *
     * @param rID
     * @return
     */
    public static Drawable getDrawablebyResource(Context context, int rID) {
        return getResources(context).getDrawable(rID);
    }

    /**
     * 跳转界面 1, 通过 {@link AppManager#startActivity(Class)}
     *
     * @param activityClass
     */
    public static void startActivity(Class activityClass) {
        AppManager.getAppManager().startActivity(activityClass);
    }

    /**
     * 跳转界面 2, 通过 {@link AppManager#startActivity(Intent)}
     *
     * @param
     */
    public static void startActivity(Intent content) {
        AppManager.getAppManager().startActivity(content);
    }

    /**
     * 跳转界面 3
     *
     * @param activity
     * @param homeActivityClass
     */
    public static void startActivity(Activity activity, Class homeActivityClass) {
        Intent intent = new Intent(activity.getApplicationContext(), homeActivityClass);
        activity.startActivity(intent);
    }

    /**
     * 跳转界面 4
     *
     * @param
     */
    public static void startActivity(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }

    /**
     * 获得屏幕的宽度
     *
     * @return
     */
    public static int getScreenWidth(Context context) {
        return getResources(context).getDisplayMetrics().widthPixels;
    }

    /**
     * 获得屏幕的高度
     *
     * @return
     */
    public static int getScreenHeidth(Context context) {
        return getResources(context).getDisplayMetrics().heightPixels;
    }

    /**
     * 获得颜色
     */
    public static int getColor(Context context, int rid) {
        return getResources(context).getColor(rid);
    }

    /**
     * 获得颜色
     */
    public static int getColor(Context context, String colorName) {
        return getColor(context, getResources(context).getIdentifier(colorName, "color", context.getPackageName()));
    }

    /**
     * 移除孩子
     *
     * @param view
     */
    public static void removeChild(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) parent;
            group.removeView(view);
        }
    }

    /*
     * 注释
     * 对象是否为空
     * */
    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    /*
     * 注释
     * 集合是否为空或者0数据
     * */
    public static boolean isArrEmpty(List obj) {
        if (obj == null) {
            return false;
        }
        if (obj.size() == 0) {
            return false;
        }
        return true;
    }

    /*
     * 注释
     * 字符串是否为空,或者空字符串
     * */
    public static boolean isStringEmpty(String obj) {
        if (obj == null) {
            return false;
        }
        if (obj.equals("")) {
            return false;
        }
        return true;
    }

    public static boolean isStringIDEmpty(String obj) {
        if (obj == null) {
            return false;
        }
        if (obj.equals("")) {
            return false;
        }
        if (obj.equals("0")) {
            return false;
        }
        return true;
    }

    /*
     * 注释
     * 格式化电话
     * */
    public static String formatePhone(String obj) {
        if (obj == null) {
            return "";
        }
        if (obj.equals("")) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(obj);

        if (obj.length() > 9) {
            stringBuilder.replace(obj.length() - 8, obj.length() - 4, "****");
        } else {
            stringBuilder.replace(0, obj.length() / 2, "****");
        }

        return stringBuilder.toString();
    }

    /**
     * MD5
     *
     * @param string
     * @return
     * @throws Exception
     */
    public static String encodeToMD5(String string) {
        byte[] hash = new byte[0];
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                hash = MessageDigest.getInstance("MD5").digest(
                        string.getBytes(StandardCharsets.UTF_8));
            } else {
                hash = MessageDigest.getInstance("MD5").digest(
                        string.getBytes("UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 全屏,并且沉侵式状态栏
     *
     * @param activity
     */
    public static void statuInScreen(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     * @deprecated Use {@link #configRecyclerView(RecyclerView, RecyclerView.LayoutManager)} instead
     */
    @Deprecated
    public static void configRecycleView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 配置 RecyclerView
     *
     * @param recyclerView
     * @param layoutManager
     */
    public static void configRecyclerView(final RecyclerView recyclerView
            , RecyclerView.LayoutManager layoutManager) {
        recyclerView.setLayoutManager(layoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * 执行 {@link AppManager#killAll()}
     */
    public static void killAll() {
        AppManager.getAppManager().killAll();
    }

    /**
     * 执行 {@link AppManager#appExit()}
     */
    public static void exitApp() {
        AppManager.getAppManager().appExit();
    }


    private static long sLastClickTime;

    /*
     * 注释
     * 防止快速点击
     * */
    public static boolean canClick() {
        long curTime = System.currentTimeMillis();
        if (curTime - sLastClickTime < 500) {
            return false;
        }
        sLastClickTime = curTime;
        return true;
    }


    public static boolean canNet() {
        long curTime = System.currentTimeMillis();
        if (curTime - sLastClickTime < 3000) {
            return false;
        }
        sLastClickTime = curTime;
        return true;
    }


    private static long sLastPlayTime;

    /*
     * 注释
     * 防止快速点击
     * */
    public static boolean canReplay() {
        long curTime = System.currentTimeMillis();
        if (curTime - sLastPlayTime < 3000) {
            return false;
        }
        sLastPlayTime = curTime;
        return true;
    }

    /*
     * 注释
     * 设置Textview 四周绘画图标
     * */
    public static void setTextDrawable(Context context, TextView textview, int type, int resID, float width, float height) {
        if (resID == 0) {
            textview.setCompoundDrawables(null, null, null, null);
            return;
        }

        Drawable icon = context.getResources().getDrawable(resID);
        icon.setBounds(0, 0, dip2px(context, width), dip2px(context, height));

        switch (type) {
            //左上右下
            case LEFT:

                textview.setCompoundDrawables(icon, null, null, null);

                break;
            case RIGHT:
                textview.setCompoundDrawables(null, null, icon, null);
                break;
            case TOP:
                textview.setCompoundDrawables(null, icon, null, null);
                break;
            case BOTTOM:
                textview.setCompoundDrawables(null, null, null, icon);
                break;

        }

    }





    /*
     * 注释
     *是否是栈顶页面
     * */

    public static boolean isTopActivity(Activity activity) {
        ActivityManager manager = (ActivityManager) MyApp.getInstance().getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(1);
        ActivityManager.RunningTaskInfo cinfo = runningTasks.get(0);
        ComponentName component = cinfo.topActivity;
        String[] temp = component.getClassName().split("\\.");

        if (activity.toString().contains(temp[temp.length - 1])) {
            return true;
        } else {
            return false;
        }
    }


    public static Drawable getResDrawable(int resid) {
        return MyApp.getInstance().getDrawable(resid);
    }


    /*
     * 注释
     *获取用户粉丝俱乐部等级图标
     * */


    /*
     * 注释
     * 是否超时
     * true 表示没有超时,输入的时间,大于本地时间
     * */
    public static boolean OverTime(String time) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+7:00"));
        try {
            Date mDate = sdf.parse(time);
            long into_time = mDate.getTime();
            long now_time = System.currentTimeMillis();
            if (into_time > now_time) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * 注释
     * 对比超时,第一个时间大于第二个时间
     * */
    public static boolean OverTime(String time, String time2) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date mDate1 = sdf.parse(time);
            Date mDate2 = sdf.parse(time2);
            long into_time1 = mDate1.getTime();
            long into_time2 = mDate2.getTime();
            if (into_time1 > into_time2) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    /*
     * 注释
     * 反斜杠时间格式化
     * */
    public static String formatTime(String time) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date mDate = sdf.parse(time);
            long miTime = mDate.getTime();
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM HH:mm:ss");
            result = sdf2.format(miTime);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * 注释
     * 反斜杠时间格式化2
     * */

    public static String formatTime2(String time) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date mDate = sdf.parse(time);
            long miTime = mDate.getTime();
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            result = sdf2.format(miTime);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String formatTimeToYear(long time) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date mDate = sdf.parse(String.valueOf(time * 1000));
            long miTime = mDate.getTime();

            result = sdf.format(miTime);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * 注释
     * 反斜杠时间格式化3
     * */

    public static String formatTime3(String time) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date mDate = sdf.parse(time);
            long miTime = mDate.getTime();
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
            result = sdf2.format(miTime);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * 注释
     * 反斜杠时间格式化2
     * */

    public static String formatTime4(String time) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date mDate = sdf.parse(time);
            long miTime = mDate.getTime();
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            result = sdf2.format(miTime);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    //region namnv xử lý hiển thị thời gian ticket stringee
    public static String formatTime2(long time) {
        String result = "";
        try {
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            result = sdf2.format(time * 1000L);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String formatTime4(long time) {
        String result = "";
        try {
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            result = sdf2.format(time * 1000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    //endregion

    /*
     * 注释
     * 反斜杠时间格式化24斯奥士
     * */

    public static String formatTime24(String time) {
        String result = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date mDate = sdf.parse(time);
            long miTime = mDate.getTime();
            SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
            result = sdf2.format(miTime);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    /*
     * 注释
     * 获取当前年月
     * */
    public static String getNowTime() {

        Date d = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Log.e("Time", sdf.format(d));
        return sdf.format(d);
    }

    /*
     * 注释
     * 格式化时间
     * */
    public static String timeConversion(int time) {
        int hour = 0;
        int minutes = 0;
        int sencond = 0;
        int temp = time % 3600;
        if (time > 3600) {
            hour = time / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    minutes = temp / 60;
                    if (temp % 60 != 0) {
                        sencond = temp % 60;
                    }
                } else {
                    sencond = temp;
                }
            }
        } else {
            minutes = time / 60;
            if (time % 60 != 0) {
                sencond = time % 60;
            }
        }
        return (hour < 10 ? ("0" + hour) : hour) + ":" + (minutes < 10 ? ("0" + minutes) : minutes) + ":" + (sencond < 10 ? ("0" + sencond) : sencond);
    }


    public static void goOutWeb(Activity context, String url) {
        String urls = url.toLowerCase();
        if (!isStringEmpty(urls)) {
            return;
        }

        if (!urls.startsWith("https://") & !urls.startsWith("http://")) {
            return;
        }


        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(urls);
        intent.setData(content_url);
        context.startActivity(intent);
        //   AppManager.getAppManager().startActivity(intent);


    }

    /*
     * 注释
     * 时间格式化
     * */
    public static String timeToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        Date date;
        try {
            date = sdf.parse(sdf.format(new Date(time)));
            //Date date = sdf.parse(sdf.format(new Long(s)));// 等价于
            return sdf.format(date);
        } catch (NumberFormatException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 注释
     * 时间格式化2
     * */
    public static String timeToString2(long time) {
        int baseTime = 60 * 60;
        if (time < baseTime) {
            Date d = new Date(time * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.CHINA);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.format(d);
        } else {
            Date d = new Date(time * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.CHINA);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.format(d);
        }

    }

    /*
     * 注释
     * 时间格式化3
     * */
    public static String formateTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        Date date;
        try {
            date = sdf.parse(sdf.format(new Date(time)));
            //Date date = sdf.parse(sdf.format(new Long(s)));// 等价于
            return sdf.format(date);
        } catch (NumberFormatException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
        return null;
    }

    /*
     * 注释
     * MD5 加密解密
     * */

    public static String md5(String string) {
        if (TextUtils.isEmpty(string)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(string.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result.toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     * 注释
     * 格式化图片地址(原地址用,分割)
     * */
    public static String formatPicUrls(ArrayList<String> urls) {
        StringBuilder result = new StringBuilder("");
        for (int i = 0; i < urls.size(); i++) {
            if (i == 0) {
                result.append(urls.get(i));
            } else {
                result.append("," + urls.get(i));
            }
        }
        return result.toString();
    }


    /*
     * 注释
     * 格式化文字
     * */
    public static ArrayList<String> splitString(String text) {
        ArrayList<String> result = new ArrayList<>();
        String[] images = text.split("/");
        for (int i = 0; i < images.length; i++) {
            result.add(images[i]);
        }
        return result;
    }


    /*
     * 注释
     * 获取版本
     * */
    public static String getVersionCode(Activity activity) {
        // 包管理器 可以获取清单文件信息
        PackageManager packageManager = activity.getPackageManager();
        try {
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    activity.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
     * 注释
     * drawable 转文件
     * */
    public static File drawableToFile(Drawable drawable) {

        Bitmap bitmap = DrawableToBitmap(drawable);
        String name_temp = "Android" + System.currentTimeMillis();
        String type_temp = ".png";

        String defaultPath = Constants.PIC_PATH;
        File file = new File(defaultPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String defaultImgPath = defaultPath + "/" + name_temp + type_temp;
        file = new File(defaultImgPath);
        try {
            file.createNewFile();

            FileOutputStream fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 20, fOut);
//            is.close();
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /*
     * 注释
     * drawable 转 bitmap
     * */
    public static Bitmap DrawableToBitmap(Drawable drawable) {

        // 获取 drawable 长宽
        int width = drawable.getIntrinsicWidth();
        int heigh = drawable.getIntrinsicHeight();

        drawable.setBounds(0, 0, width, heigh);

        // 获取drawable的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 创建bitmap
        Bitmap bitmap = Bitmap.createBitmap(width, heigh, config);
        // 创建bitmap画布
        Canvas canvas = new Canvas(bitmap);
        // 将drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }

    /*
     * 注释
     * 获取文件大小
     * */
    public static String getBytesString(long bytes) {
        String[] quantifiers = new String[]{
                "KB", "MB", "GB", "TB"
        };
        double speedNum = bytes;
        for (int i = 0; ; i++) {
            if (i >= quantifiers.length) {
                return "";
            }
            speedNum /= 1024;
            if (speedNum < 512) {
                return String.format(Locale.US, "%.2f", speedNum) + " " + quantifiers[i];
            }
        }
    }


    /*
     * 注释
     * 是否是今天
     * */
    public static boolean isToday(String timeStamp1, String timeStamp2) {
        if (timeStamp1.equals(timeStamp2)) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean isSameString(String mOld, String mNew) {
        //如果新旧都是null,直接返回null
        if (mOld == null) {
            return false;
        } else {
            if (mOld.equals(mNew)) {
                return true;
            } else {
                return false;
            }


        }

    }


    public static int isSameInt(int mOld, int mNew) {
        //如果新旧都是null,直接返回null
        if (mOld == mNew) {
            return 0;
        } else {
            if (mOld != 0 & mNew == 0) {
                return mOld;
            } else if (mOld == 0 & mNew != 0) {
                return mNew;
            }
        }
        return 0;
    }

    public static boolean isChangeTheme() {
        return changeTheme;
    }

    public static void setChangeTheme(boolean c) {
        changeTheme = c;
    }


    public static void sortListByInteger(ArrayList<Integer> mList) {
        if (mList == null) {
            return;
        }
        if (mList.size() == 0) {
            return;
        }
        Collections.sort(mList, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                if (lhs > rhs) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });
    }


    public static boolean checkFile(String name) {
        File file = new File(name);
        if (file.exists()) {
            return true;
        } else {
            return false;
        }
    }


    public static Drawable getVideoDrawble(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        if (path.startsWith("http")) {
            media.setDataSource(path, new HashMap<>());
        } else {
            media.setDataSource(path);
        }


        Drawable drawable = new BitmapDrawable(media.getFrameAtTime());
        return drawable;
    }

    // str需要匹配的数据源，regexp正则匹配规则，
    // while 循环替换需要更改字体的字符
    // 返回处理好的SpannableString  String telRegex = "\\[(.*?)]";
    public static SpannableString getPatternList(String str, String regexp) {
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        String normalStr = str.replaceAll("\\~", "");
        SpannableString normalSpan = new SpannableString(normalStr);
        while (matcher.find()) {
            StyleSpan span = new StyleSpan(android.graphics.Typeface.BOLD);
            int startIndex = normalStr.indexOf(matcher.group(1));
            int endIndex = normalStr.indexOf(matcher.group(1)) + matcher.group(1).length();
            if (startIndex >= 0 && endIndex >= 0) {
                normalSpan.setSpan(span, startIndex, endIndex, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            } else {
                return normalSpan;
            }
        }
        return normalSpan;
    }


    public static boolean isEmail(String email) {
        if (null == email || "".equals(email)) return false;

        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");//复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }


    public static void showNotification(Context context, String content, Intent intent) {
/*        Notification.Builder builder3 = new Notification.Builder(context);
        builder3.setSmallIcon(R.drawable.ic_notification_cion);
        builder3.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_coin));
        builder3.setColor(getColor(context, R.color.color_1080EC));
        builder3.setAutoCancel(true);
        builder3.setContentTitle(getString(context, R.string.String_new_notify));
        builder3.setContentText(content);

        PendingIntent pendIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder3.setContentIntent(pendIntent);
        builder3.setFullScreenIntent(pendIntent, true);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0以上弹出通知状态栏
            String channelID = "2";
            String channelName = "notification";
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
                builder3.setChannelId(channelID);
            }
        }
        notificationManager.notify(2, builder3.build());*/
    }


    public static String initUploadPic(String pic) {
        if (pic.startsWith("/")) {
            return pic;
        } else {
            return "/" + pic;
        }


    }

    public static String initUrlEnd(String pic) {
        if (pic.endsWith("/")) {
            return pic;
        } else {
            return pic + "/";
        }


    }

    public static String initUrlHead(String pic) {
        StringBuilder stringBuilder = new StringBuilder(pic);
        if (pic.startsWith("/")) {
            stringBuilder.replace(0, 1, "");
            return stringBuilder.toString();
        } else {
            return pic;
        }


    }


    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        ArmsUtils.accessToken = accessToken;
    }

    //获取AndroidId
    public static String getAndroidId(Context context) {
        return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
    }

    //获取手机型号
    public static String getSystemModel() {
        return Build.MODEL;
    }

    //获取手机厂商
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    public static String roundingPrice(String title) {
        int price = (int) Float.parseFloat(title);
        if (price < 500) {
            return "0";
        }
        if (price >= 500 && price <= 1000) {
            return "1000";
        }
        String priceStr = String.valueOf(price);
        String frontStr = String.valueOf(price).substring(0, priceStr.length() - 3);
        String last = String.valueOf(price).substring(priceStr.length() - 3);
        int lastPrice = (int) Float.parseFloat(last);
        if (lastPrice == 0) {
            return priceStr;
        }
        if (lastPrice < 500) {
            return frontStr + "000";
        }
        if (lastPrice >= 500 && lastPrice < 1000) {
            String last2 = String.valueOf(price).substring(priceStr.length() - 4, priceStr.length() - 3);
            int last3 = (int) Float.parseFloat(last2) + 1;
            String frontStr2 = String.valueOf(price).substring(0, priceStr.length() - 4);
            return frontStr2 + last3 + "000";
        }
        return "";
    }

/*    public static String initRTCPushUrl(String uid, String usersig, String appid) {
        StringBuilder rtcUrl;
        rtcUrl = new StringBuilder();
        rtcUrl.append(Constants.RTC_PUSH_HEAD);
        rtcUrl.append("st" + uid + "?");
        rtcUrl.append("sdkappid=" + appid + "&");
        rtcUrl.append("userId=" + uid + "&");
        rtcUrl.append("usersig=" + usersig);
        Log.e("PushUrl", rtcUrl.toString());
        return rtcUrl.toString();
    }*/

    public static String initRTCPlayUrl(String streameId, String uid, String usersig, String appid) {
        StringBuilder rtcUrl;
        rtcUrl = new StringBuilder();
        rtcUrl.append("trtc://cloud.tencent.com/play/");
        rtcUrl.append("st" + streameId + "?");
        rtcUrl.append("sdkappid=" + appid + "&");

        rtcUrl.append("userId=" + uid + "&");
        rtcUrl.append("usersig=" + usersig);
        return rtcUrl.toString();
    }


    public static int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int screenPhoneWidth;
    public static int screenPhoneHeight;

    public static void getAndroiodScreenProperty(Activity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        screenPhoneWidth = (int) (width / density);  // 屏幕宽度(dp)
        screenPhoneHeight = (int) (height / density);// 屏幕高度(dp)


        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度(dp)：" + screenPhoneWidth);
        Log.d("h_bl", "屏幕高度(dp)：" + screenPhoneHeight);

    }


    public static int chu(Double d) {
        int result = 0;
        Double dd = new Double(screenPhoneWidth) / d;
        return dd.intValue();
    }


    public static int cheng(int result, Double d) {

        Double dd = new Double(result) * d;
        return dd.intValue();
    }

    public static int chu2(int result, Double d) {

        Double dd = new Double(result) / d;
        return dd.intValue();
    }


    public static String getAssetsToString(String name) {
        AssetManager assetManager = MyApp.getInstance().getAssets();

        InputStream inputStream = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        StringBuffer sb = new StringBuffer();
        try {
            inputStream = assetManager.open(name);
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);

            sb.append(br.readLine());
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append("\n" + line);
            }

            br.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();


    }

    public static void goLive(HotLive mLiveInfo,AppCompatActivity activity) {




        switch (mLiveInfo.getRoom_type()) {
            case "0":
                enterRoom(mLiveInfo);
                break;
            case "1":
                InputTipsDialog inputTipsDialog = new InputTipsDialog();
                inputTipsDialog.setmContent("请输入密码,进入密码房间");
                inputTipsDialog.setmTitle(MyApp.getInstance().getString(R.string.st_tips));
                inputTipsDialog.setHint("请输入房间密码");
                inputTipsDialog.setSubmitClick(new OnSendChatListener() {
                    @Override
                    public void sendText(String text) {
                        if (StringUtil.md5(text).equals(mLiveInfo.getPassword().toUpperCase())) {
                            enterRoom(mLiveInfo);
                            inputTipsDialog.dismiss();
                        } else {
                            ToastUtils.showT("密码错误");
                            inputTipsDialog.dismiss();
                        }
                    }
                });

                inputTipsDialog.show(activity.getSupportFragmentManager(), "");
                break;
            case "2":
                try {
                    TipsDialog tipsDialog = new TipsDialog();
                    tipsDialog.setmContent("是否要花费" + mLiveInfo.getPrice() + "金币/分,进入付费房间?");
                    tipsDialog.setmTitle(MyApp.getInstance().getString(R.string.st_tips));
                    tipsDialog.setSubmitClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            enterRoom(mLiveInfo);

                        }
                    });


                    tipsDialog.show(activity.getSupportFragmentManager(), "");

                } catch (Exception e) {
                    String a = "";
                }


                break;

        }


    }
    public static void goLive(HotLive mLiveInfo,FragmentActivity activity) {



        switch (mLiveInfo.getRoom_type()) {
            case "0":
                enterRoom(mLiveInfo);
                break;
            case "1":
                InputTipsDialog inputTipsDialog = new InputTipsDialog();
                inputTipsDialog.setmContent("请输入密码,进入密码房间");
                inputTipsDialog.setmTitle(MyApp.getInstance().getString(R.string.st_tips));
                inputTipsDialog.setHint("请输入房间密码");
                inputTipsDialog.setSubmitClick(new OnSendChatListener() {
                    @Override
                    public void sendText(String text) {
                        if (StringUtil.md5(text).equals(mLiveInfo.getPassword().toUpperCase())) {
                            enterRoom(mLiveInfo);
                            inputTipsDialog.dismiss();
                        } else {
                            ToastUtils.showT("密码错误");
                            inputTipsDialog.dismiss();
                        }
                    }
                });

                inputTipsDialog.show(activity.getSupportFragmentManager(), "");
                break;
            case "2":
                try {
                    TipsDialog tipsDialog = new TipsDialog();
                    tipsDialog.setmContent("是否要花费" + mLiveInfo.getPrice() + "金币/分,进入付费房间?");
                    tipsDialog.setmTitle(MyApp.getInstance().getString(R.string.st_tips));
                    tipsDialog.setSubmitClick(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            enterRoom(mLiveInfo);

                        }
                    });


                    tipsDialog.show(activity.getSupportFragmentManager(), "");

                } catch (Exception e) {
                    String a = "";
                }


                break;

        }


    }
    public static boolean showInputCodeDiaolog(AppCompatActivity activity) {
        boolean isShow = false;
        if(MyUserInstance.getInstance().getUserinfo().getIs_salesman()==1){
            return false;
        }
        if (MyUserInstance.getInstance().isAnchor()){
            return false;
        }

            if (!ArmsUtils.isStringEmpty(MyUserInstance.getInstance().getUserinfo().getAgentid())) {
            isShow = true;
        }

        if (MyUserInstance.getInstance().getUserinfo().getAgentid().equals("0")) {
            isShow = true;
        }

        if (isShow) {
            InputCodeDialog inputCodeDialog = new InputCodeDialog();


            inputCodeDialog.show(activity.getSupportFragmentManager(), "");

        }
        return isShow;
    }

    public static boolean showInputCodeDiaolog(FragmentActivity activity) {
        boolean isShow = false;
        if(MyUserInstance.getInstance().getUserinfo().getIs_salesman()==1){
            return false;
        }
        if (MyUserInstance.getInstance().isAnchor()){
            return false;
        }
        if (!ArmsUtils.isStringEmpty(MyUserInstance.getInstance().getUserinfo().getAgentid())) {
            isShow = true;
        }

        if (MyUserInstance.getInstance().getUserinfo().getAgentid().equals("0")) {
            isShow = true;
        }

        if (isShow) {
            InputCodeDialog inputCodeDialog = new InputCodeDialog();


            inputCodeDialog.show(activity.getSupportFragmentManager(), "");

        }
        return isShow;
    }

    private static void enterRoom(HotLive mLiveInfo) {
        if (AppManager.getAppManager().getTopActivity() == null) {
            return;
        }

        if (mLiveInfo.getRoom_type().equals("2")) {
            if (MyUserInstance.getInstance().getUserGold() < Integer.valueOf(mLiveInfo.getPrice())) {
                ToastUtils.showT("您的金币不足");
                return;
            }
        }

        Intent intent;

        //1横 2竖
        switch (mLiveInfo.getOrientation()) {
            case 2:
                intent = new Intent(AppManager.getAppManager().getTopActivity(), LivePlayVerticalActivity2.class);
                break;
            default:
                intent = new Intent(AppManager.getAppManager().getTopActivity(), LivePlayHorActivity2.class);
                break;

        }

        intent.putExtra(Constants.ANCHOR_ID, mLiveInfo.getAnchorid());
        AppManager.getAppManager().startActivity(intent);

    }


    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = MyApp.getInstance().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public static int getVipLevelIcon(int result, String time) {
        int res = 0;
        if (result == 0 | time == null) {
            return res;
        }


        if (!OverTime(time)) {
            return res;
        }
        switch (result) {
            case 1:
                res = R.drawable.ic_vip_1;
                break;
            case 2:
                res = R.drawable.ic_vip_2;
                break;
            case 3:
                res = R.drawable.ic_vip_3;
                break;
            case 4:
                res = R.drawable.ic_vip_4;
                break;

        }

        return res;
    }


    public static String getVipNameText(int level) {
        String name = "";
        switch (level) {
            case 1:
                name = MyApp.getInstance().getString(R.string.st_vip_1);
                break;
            case 2:
                name = MyApp.getInstance().getString(R.string.st_vip_2);
                break;
            case 3:
                name = MyApp.getInstance().getString(R.string.st_vip_3);
                break;
            case 4:
                name = MyApp.getInstance().getString(R.string.st_vip_4);
                break;
        }
        return name;
    }

    public static int getVipNameColor(int level) {
        int name = 0;
        switch (level) {
            case 1:
                name = R.color.color_streamer_vip_1;
                break;
            case 2:
                name = R.color.color_streamer_vip_2;
                break;
            case 3:
                name = R.color.color_streamer_vip_3;
                break;
            case 4:
                name = R.color.color_streamer_vip_4;
                break;
        }
        return name;
    }

    public static int getVipStreamerBG(int level) {
        int name = 0;
        switch (level) {
            case 1:
                name = R.drawable.bg_streamer_vip_1;
                break;
            case 2:
                name = R.drawable.bg_streamer_vip_2;
                break;
            case 3:
                name = R.drawable.bg_streamer_vip_3;
                break;
            case 4:
                name = R.drawable.bg_streamer_vip_4;
                break;
        }
        return name;
    }

    public static int getVipDialogBG(int result, String time) {
        int res = 0;
        if (result == 0 | time == null) {
            return res;
        }


        if (!OverTime(time)) {
            return res;
        }
        switch (result) {
            case 1:
                res = R.drawable.ic_dialog_vip1;
                break;
            case 2:
                res = R.drawable.ic_dialog_vip2;
                break;
            case 3:
                res = R.drawable.ic_dialog_vip3;
                break;
            case 4:
                res = R.drawable.ic_dialog_vip4;
                break;
        }
        return res;
    }

    /*
     * 注释
     *获取用户VIP等级
     * */
    public static int getVipLevel2(int result, String time) {

        if (result == 0 | time == null) {
            return 0;
        }
        if (OverTime(time)) {
            return result;
        } else {
            return 0;
        }
    }

    public static int getIsPkingByBean(HotLive liveInfo) {
        if (liveInfo.getPk_status() == 1) {
            if (liveInfo.getPklive() == null) {
                return 0;
            }
            if (liveInfo.getPkinfo() == null) {
                return 0;
            }
            if (!ArmsUtils.isStringEmpty(liveInfo.getPkid())) {
                return 0;
            }
            return 1;
        } else {
            return 0;
        }
    }


    public static int getIsPkingByMessage(ImMessage bean) {
        if (bean.getData() == null) {
            return 0;
        }
        if (bean.getData().getNotify() == null) {
            return 0;
        }
        if (bean.getData().getNotify().getPkinfo() == null) {
            return 0;
        }
        if (bean.getData().getNotify().getPklive() == null) {
            return 0;
        }
        return 1;

    }

    public static String getRankNum(int rank) {
        StringBuilder sb = new StringBuilder();
        if (rank < 8) {
            sb.append("0");
            sb.append(rank + 1);
            return sb.toString();

        } else {
            sb.append(rank + 1);
            return sb.toString();

        }
    }

    public static String formatNum(int num) {

        StringBuilder result = new StringBuilder();

        if (num < 10000) {
            result.append(num);
        } else if (num > 10000 & num < 1000000) {
            result.append(num / 10000);
            result.append("万");
        }


        return result.toString();
    }

    public static String initLiveGroupId(String anchorid) {
        StringBuilder sb = new StringBuilder();
        sb.append("LIVEROOM_");
        sb.append(anchorid);
        return sb.toString();
    }

    public static void initUserInfoDialog(String userId, int type, String anchorid, AppCompatActivity activity) {


        UserInfoDialog dialog = new UserInfoDialog(userId, anchorid, type);
        dialog.show(activity.getSupportFragmentManager(), "");
    }

    public static void initUserInfoDialog(String userId, int type, String anchorid, DialogFragment activity) {


        UserInfoDialog dialog = new UserInfoDialog(userId, anchorid, type);
        dialog.show(activity.getChildFragmentManager(), "");
    }


}
