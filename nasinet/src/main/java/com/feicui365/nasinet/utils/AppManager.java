package com.feicui365.nasinet.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.SupportActivity;
import android.util.Log;


import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class AppManager {
    private static Stack<Activity> activityStack;
    private static AppManager instance;
    public Context mApplication;

    private AppManager() {
    }

    public  void setmApplication(Context mApplication) {
        this.mApplication = mApplication;
    }

    /**
     * 单实例 , UI无需考虑多线程同步问题
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();

        }
        return instance;
    }

    /**
     * 添加Activity到栈
     */
    public void addActivity(SupportActivity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（栈顶Activity）
     */
    public Activity currentActivity() {
        if (activityStack == null || activityStack.isEmpty()) {
            return null;
        }
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 获取当前Activity（栈顶Activity） 没有找到则返回null
     */
    public Activity findActivity(Class<?> cls) {
        Activity activity = null;
        for (Activity aty : activityStack) {
            if (aty.getClass().equals(cls)) {
                activity = aty;
                break;
            }
        }
        return activity;
    }

    /**
     * 结束当前Activity（栈顶Activity）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Activity activity) {

        if (activity != null) {
            // 为与系统Activity栈保持一致，且考虑到手机设置项里的"不保留活动"选项引起的Activity生命周期调用onDestroy()方法所带来的问题,此处需要作出如下修正
            if(activity.isFinishing()){
                activityStack.remove(activity);
                //activity.finish();
                activity = null;
            }else{
                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        }

    }

    /**
     * 结束指定的Activity(重载)
     */
    public void finishActivity(Class<?> cls) {
        //   activityStack.iterator();
        if(activityStack==null){
            return;
        }
        if(activityStack.iterator()==null){
            return;
        }

        Iterator<Activity> iterator=activityStack.iterator();
        while (iterator.hasNext()){
            Activity supportActivity=iterator.next();
            if (supportActivity.getClass().equals(cls)) {
                if(supportActivity!=null){
                    if(supportActivity.isFinishing()){
                        iterator.remove();
                        supportActivity=null;
                    }else{
                        iterator.remove();
                        supportActivity.finish();
                        supportActivity = null;
                    }
                }
            }
        }

    /*    for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }*/
    }

    /**
     * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
     *
     * @param cls
     */
    public void finishOthersActivity(Class<?> cls) {
        if(activityStack==null){
            return;

        }

        Iterator<Activity> iterator=activityStack.iterator();
        while (iterator.hasNext()){
            Activity supportActivity=iterator.next();

            if (!supportActivity.getClass().equals(cls)) {

                if(supportActivity!=null){
                    if(supportActivity.isFinishing()){
                        iterator.remove();

                    }else{
                        supportActivity.finish();
                        iterator.remove();

                    }
                }
            }
        }


    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if(activityStack==null){
            return;
        }
        if(activityStack.iterator()==null){
            return;
        }
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
            Log.e("finishAllActivity",activityStack.get(i).toString());
        }
        Log.e("finishAllActivity",activityStack.size()+"");
        activityStack.clear();
    }

    /**
     * 应用程序退出
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            System.exit(0);
        }
    }


    /**
     * 让在栈顶的 {@link Activity} ,打开指定的 {@link Activity}
     *
     * @param intent
     */
    public void startActivity(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        mApplication.startActivity(intent);
    }
    public void startActivity2(Class activityClass) {
        startActivity(new Intent(mApplication, activityClass));
    }

    /**
     * 让在栈顶的 {@link Activity} ,打开指定的 {@link Activity}
     *
     * @param activityClass
     */
    public void startActivity(Class activityClass) {
        startActivity(new Intent(mApplication, activityClass).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }


    public void startActivity(Context context,Class activityClass) {
        startActivity(new Intent(context, activityClass).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public void killAll() {
//        while (getActivityList().size() != 0) { //此方法只能兼容LinkedList
//            getActivityList().remove(0).finish();
//        }
        synchronized (AppManager.class) {
            Iterator<Activity> iterator = getActivityList().iterator();
            while (iterator.hasNext()) {
                Activity next = iterator.next();
                iterator.remove();
                next.finish();
            }
        }
    }
    public void appExit() {
        try {
            killAll();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Activity> getActivityList() {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        return activityStack;
    }

    public Activity getTopActivity() {
        if (activityStack == null) {

            return null;
        }
        return activityStack.size() > 0 ? activityStack.get(activityStack.size() - 1) : null;
    }
}