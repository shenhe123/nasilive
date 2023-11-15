package com.feicui365.live.base;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;


import android.os.IBinder;
import android.os.LocaleList;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.gyf.immersionbar.ImmersionBar;
import com.feicui365.live.R;
import com.feicui365.live.dialog.MessageDialog;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.BasePresenter;
import com.feicui365.live.ui.act.SplashActivity;
import com.feicui365.live.util.DateUtil;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.nasinet.base.NasiBaseActivity;
import com.feicui365.nasinet.userconfig.AppStatus;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;

import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author azheng
 * @date 2018/4/24.
 * GitHub：https://github.com/RookieExaminer
 * Email：wei.azheng@foxmail.com
 * Description：
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends NasiBaseActivity implements BaseView {

    public T mPresenter;
    public long unReadMessage = 0;

    public boolean is_show_vip = false;



    VipInFinishListener vipInFinishListener;

    public void setVipInFinishListener(VipInFinishListener vipInFinishListener) {
        this.vipInFinishListener = vipInFinishListener;
    }

    public interface VipInFinishListener {
        void vipinfinish();
    }

    @Override
    public void finish() {
        super.finish();
        //注释掉activity本身的过渡动画

        overridePendingTransition(R.anim.activity_anim_in_right, R.anim.activity_anim_out_left);
        HttpUtils.getInstance().cleanAll();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断app状态
        if (!getRunningActivityName().contains("Splash")) {
            if (AppStatusManager.getInstance().getAppStatus() == AppStatus.STATUS_RECYCLE) {
                //被回收，跳转到启动页面
                Intent intent = new Intent(this, SplashActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }

        AppManager.getAppManager().addActivity(this);

        if(this.toString().contains("UserShopActivity")|this.toString().contains("SellerShopActivity")){
            ImmersionBar.with(this).init();
        }else{
            ImmersionBar.with(this).statusBarDarkFont(true).init();
        }


        Log.e("thisthisthis", this.toString());

    }

    public void setActionBarTextColor(boolean i) {

        ImmersionBar.with(this).statusBarDarkFont(i).titleBarMarginTop(rl_title).init();

    }
    public void setActionBarTextColor(View view) {

        ImmersionBar.with(this).statusBarDarkFont(true).titleBarMarginTop(view).init();

    }



    private Dialog dialog;

    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this);
        dialog.show();
    }

    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }




    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        super.onDestroy();
        // AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 绑定生命周期 防止MVP内存泄漏
     *
     * @param <T>
     * @return
     */
    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        String lang = Hawk.get("Language", "0");
        switch (lang) {
            case "1":
                newBase = updateResources(newBase, "zh");
                break;
            case "2":
                newBase = updateResources(newBase, "en");
                break;
        }


        super.attachBaseContext(newBase);
    }


    private Context updateResources(Context context, String language) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        Locale locale = new Locale(language);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocaleList defaultList = LocaleList.forLanguageTags(language);
            LocaleList.setDefault(defaultList);
            conf.setLocales(defaultList);
        } else {
            conf.locale = locale;
        }
        conf.locale = locale;
        conf.setLocale(locale);
        context = context.createConfigurationContext(conf);
        res.updateConfiguration(conf, dm);
        return context;
    }




    private static final int MIN_DELAY_TIME = 500;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    @SuppressLint("LongLogTag")
    public boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        Log.e("isFastClick_currentClickTime", currentClickTime + "");
        Log.e("isFastClick_lastClickTime", lastClickTime + "");
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        Log.e("isFastClick_flag", flag + "");
        return flag;
    }

    public boolean isVip(UserRegist sender) {

        if (sender.getVip_date() == null | sender.getVip_level() <= 0) {
            return false;
        }
        if (sender.getVip_date().equals("")) {
            return false;
        }

        if (System.currentTimeMillis() < DateUtil.date2TimeStamp(sender.getVip_date())) {
            return true;
        }
        return false;
    }


    public  void setAndroidNativeLightStatusBar( boolean dark) {
        //true黑false白
        View decor = getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    // -------------------------------------隐藏输入法-----------------------------------------------------
// 获取点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
// TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public void setStatusBarColor(int colorId) {
        Window window = getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(getResources().getColor(colorId));

        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }


    public void initMessageDialog(String title, String content, MessageDialog.OnFinishListener onFinishListener){

        MessageDialog.Builder builder = new MessageDialog.Builder(this);


        builder.create().show();
        builder.setTitle(title);
        builder.setContent(content);
        builder.setOnFinishListener(onFinishListener);
    }



    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String sim = dateFormat.format(date);
        return sim;
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        if (checkDoubleClick(intent)) {
            super.startActivityForResult(intent, requestCode, options);

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
        }else if (intent.getAction() != null) { // 隐式跳转
            tag = intent.getAction();
        }else {
            return true;
        }

        if (tag.equals(mActivityJumpTag) &&SystemClock.uptimeMillis()- mClickTime <  1000) {
            // 检查不通过
            result = false;
        }

        // 记录启动标记和时间
        mActivityJumpTag = tag;
        mClickTime = SystemClock.uptimeMillis();
        return result;
    }

}
