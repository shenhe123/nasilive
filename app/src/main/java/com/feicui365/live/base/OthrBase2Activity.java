package com.feicui365.live.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.LocaleList;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;
import com.feicui365.live.R;
import com.feicui365.live.ui.act.SplashActivity;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.nasinet.base.NasiOtherBaseActivity;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.util.MyCredentialProvider;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.nasinet.userconfig.AppStatus;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;

import com.tencent.qcloud.core.auth.QCloudCredentialProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public abstract class OthrBase2Activity extends NasiOtherBaseActivity {

    public TextView tv_title, tv_other;
    public RelativeLayout rl_back;
    public RelativeLayout rl_title;

    protected View rootView;
    private Dialog dialog;


    public Context context;
    private CosXmlService cosXmlService;
    public String thumb = "";

    private OnUploadFinshListener onUploadFinshListener;
    private OnUploadFinshListener onUploadFinshListener2;

    public void setOnUploadFinshListener(OnUploadFinshListener onUploadFinshListener) {
        this.onUploadFinshListener = onUploadFinshListener;
    }

    public interface OnUploadFinshListener {
        void onFinish(String url);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断app状态
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(getLayoutId());
        context = this;
        ImmersionBar.with(this).statusBarDarkFont(true).init();

        rootView = View.inflate(this, com.feicui365.nasinet.R.layout.other_base_2_activity, null);
        addContent();
        setContentView(rootView);
        initData();

    }


    @Override
    public void finish() {
        super.finish();
        //注释掉activity本身的过渡动画

        overridePendingTransition(R.anim.activity_anim_in_right, R.anim.activity_anim_out_left);
        HttpUtils.getInstance().cleanAll();
    }

    /**
     * 获取布局ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract void initData();

    public void addContent() {
        tv_title = rootView.findViewById(com.feicui365.nasinet.R.id.tv_title);
        rl_back = rootView.findViewById(com.feicui365.nasinet.R.id.rl_back);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rl_title = rootView.findViewById(com.feicui365.nasinet.R.id.rl_title);
        tv_other = rootView.findViewById(com.feicui365.nasinet.R.id.tv_other);
        FrameLayout flContent = (FrameLayout) rootView.findViewById(com.feicui365.nasinet.R.id.fl_content);
        View content = View.inflate(this, getLayoutId(), null);
        if (content != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.MATCH_PARENT);
            flContent.addView(content, params);

        }
        ;
    }


    public void setTitle(String title) {
        tv_title.setText(title);
    }


    public void hideTitle(boolean hide) {
        if (hide) {
            rl_title.setVisibility(View.GONE);
        }
    }

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


    public void setOnUploadFinshListener2(OnUploadFinshListener onUploadFinshListener2) {
        this.onUploadFinshListener2 = onUploadFinshListener2;
    }


    private String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String sim = dateFormat.format(date);
        return sim;
    }

    public void upLoadImage(QCloudData data, String imagePath) {

        String name_temp = "IMG_ANDROID_" + getTime() + "_" + new Random().nextInt(99999);
        String appid = MyUserInstance.getInstance().getUserConfig().getConfig().getQcloud_appid();
        String region = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_region();

        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .isHttps(true) // 使用 HTTPS 请求, 默认为 HTTP 请求
                .builder();
        /**
         * 初始化 {@link QCloudCredentialProvider} 对象，来给 SDK 提供临时密钥
         */
        QCloudCredentialProvider credentialProvider = new MyCredentialProvider(data.getCredentials().getTmpSecretId(),
                data.getCredentials().getTmpSecretKey(), data.getCredentials().getSessionToken(), data.getExpiredTime(), data.getStartTime());
        cosXmlService = new CosXmlService(this, serviceConfig, credentialProvider);
        String bucket = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_bucket(); //存储桶，格式：BucketName-APPID
        String cosPath = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_folder_image() + "/" + name_temp; //对象在存储桶中的位置标识符，即称对象键
        String srcPath = imagePath; //本地文件的绝对路径
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
        Set<String> headerKeys = new HashSet<>();
        headerKeys.add("Host");
        putObjectRequest.setSignParamsAndHeaders(null, headerKeys);
        // 使用异步回调上传
        cosXmlService.putObjectAsync(putObjectRequest, new CosXmlResultListener() {

            @Override
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult result) {
                hideLoading();
                PutObjectResult putObjectResult = (PutObjectResult) result;
                thumb = putObjectResult.accessUrl;
                if (onUploadFinshListener != null) {
                    onUploadFinshListener.onFinish(thumb);
                }
            }

            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException) {
                hideLoading();
                // todo Put object failed because of CosXmlClientException or CosXmlServiceException...
            }
        });
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        String lang= Hawk.get("Language","0");
        switch (lang){
            case "1":
                newBase=  updateResources(newBase,"zh");
                break;
            case "2":
                newBase=updateResources(newBase,"en");
                break;
        }


        super.attachBaseContext(newBase);
    }


    private Context  updateResources(Context context, String language) {
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

    private static final int MIN_DELAY_TIME= 500;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;
    public  boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
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
}
