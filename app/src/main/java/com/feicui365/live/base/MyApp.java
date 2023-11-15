package com.feicui365.live.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;


import com.facebook.drawee.backends.pipeline.Fresco;
import com.mob.MobSDK;
import com.feicui365.live.interfaces.OnGetUnRead;

import com.feicui365.live.util.TxPicturePushUtils;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;

import com.tencent.bugly.crashreport.CrashReport;

import com.tencent.qcloud.ugckit.UGCKit;
import com.tencent.qcloud.ugckit.utils.TCUserMgr;
import com.tencent.rtmp.TXLiveBase;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.ugc.TXUGCBase;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;


public class MyApp extends Application {
    private static Context mContext;
    //腾讯短视频KEY
    String ugcLicenceUrl = "https://license.vod2.myqcloud.com/license/v2/1314028597_1/v_cube.license"; //您从控制台申请的 licence url
    String ugcKey = "6e924bf2c11fcd253172bf37222aa06d";
    private boolean login_mode = false;
    public static int mopiLevel = 8;
    public static int meibaiLevel = 5;
    public static int hongrunLevel = 3;
    public boolean isLogin_mode() {
        return login_mode;
    }

    public void setLogin_mode(boolean login_mode) {
        this.login_mode = login_mode;
    }

    public boolean license = false;

    private OnGetUnRead onGetUnRead;

    public void setOnGetUnRead(OnGetUnRead onGetUnRead) {
        this.onGetUnRead = onGetUnRead;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化X5内核
        QbSdk.initX5Environment(this, new QbSdk.PreInitCallback() {
            @Override
            public void onCoreInitFinished() {
            }

            @Override
            public void onViewInitFinished(boolean b) {

                Log.e("@@", "加载内核是否成功:" + b);
            }
        });
        AppManager.getAppManager().setmApplication(this);
        Hawk.init(this).build();
        mContext = getApplicationContext();
        Fresco.initialize(this);
        TCConfigManager.init(this);

        TXUGCBase.getInstance().setLicence(this, ugcLicenceUrl, ugcKey);

        TxPicturePushUtils.getInstance().setmContext(mContext);
        //移动分享SDK
        MobSDK.submitPolicyGrantResult(false, null);
        MobSDK.init(this);

          initSDK();

        // Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());

        initUGCKit();
    }

    private void initUGCKit() {
        //初始化腾讯短视频
        TCConfigManager.init(this);
        // 短视频licence设置
        TXUGCBase.getInstance().setLicence(this, ugcLicenceUrl, ugcKey);
        TCUserMgr.getInstance().initContext(getApplicationContext());
        UGCKit.init(this);
    }

    public static Context getInstance() {
        return mContext;
    }

    public Application getApplication() {
        return this;
    }


    /**
     * 初始化SDK，包括Bugly，LiteAVSDK等
     */
    public void initSDK() {
        //启动bugly组件，bugly组件为腾讯提供的用于crash上报和分析的开放组件，如果您不需要该组件，可以自行移除
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(TXLiveBase.getSDKVersionStr());
        CrashReport.initCrashReport(getApplicationContext(), "9f09d9a052", true, strategy);

       // TCUserMgr.getInstance().initContext(getApplicationContext());

    }

    public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            final Writer result = new StringWriter();
            final PrintWriter printWriter = new PrintWriter(result);
            //如果异常时在AsyncTask里面的后台线程抛出的
            //那么实际的异常仍然可以通过getCause获得
            Throwable cause = ex;
            while (null != cause) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            //stacktraceAsString就是获取的carsh堆栈信息
            final String stacktraceAsString = result.toString();
            printWriter.close();
            AppManager.getAppManager().AppExit(getApplicationContext());
        }
    }


    @Override
    public void startActivity(Intent intent) {
        if (checkDoubleClick(intent)) {
            super.startActivity(intent);
        }

    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        if (checkDoubleClick(intent)) {
            super.startActivity(intent, options);
        }
    }

    private String mActivityJumpTag;        //activity跳转tag
    private long mClickTime;                //activity跳转时间

    /**
     * 检查是否重复跳转，不需要则重写方法并返回true
     */
    protected boolean checkDoubleClick(Intent intent) {

        // 默认检查通过
        boolean result = true;
        // 标记对象
        String tag;
        if (intent.getComponent() != null) { // 显式跳转
            tag = intent.getComponent().getClassName();
        } else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction();
        } else {
            return true;
        }

        if (tag.equals(mActivityJumpTag) && mClickTime >= SystemClock.uptimeMillis() - 500) {
            // 检查不通过
            result = false;
        }

        // 记录启动标记和时间
        mActivityJumpTag = tag;
        mClickTime = SystemClock.uptimeMillis();
        return result;
    }

}


