package com.feicui365.nasinet.userconfig;

public class AppStatusManager {
    public int appStatus = AppStatus.STATUS_RECYCLE;//APP状态 初始值被系统回收

    public static AppStatusManager appStatusManager;

    private AppStatusManager(){}

    //单例模式
    public static AppStatusManager getInstance() {
        if (appStatusManager == null) {
            appStatusManager = new AppStatusManager();
        }
        return appStatusManager;
    }
    //得到状态
    public int getAppStatus() {
        return appStatus;
    }
    //设置状态
    public void setAppStatus(int appStatus) {
        this.appStatus = appStatus;
    }
}
