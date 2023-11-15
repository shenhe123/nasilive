package com.feicui365.live.ui.act;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.im.TxImUtils;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.live.wxapi.PayCallback;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.nasinet.utils.AppManager;
import com.orhanobut.hawk.Hawk;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;


public class WebShopActivity extends Activity implements PayCallback {
    @BindView(R.id.forum_context)
    WebView forum_context;
    private Dialog dialog;

    String url = "";

    private WebSettings mWebSettings;
    private ValueCallback<Uri> uploadFile;
    private ValueCallback<Uri[]> uploadFiles;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Bundle bundle = msg.getData();
                    String date = bundle.getString("msg");
                    switch (date) {
                        case "LIGHT":
                            setAndroidNativeLightStatusBar(false);
                            break;
                        case "DARK":
                            setAndroidNativeLightStatusBar(true);
                            break;
                    }
                    break;
                case 2:
                    Bundle bundle2 = msg.getData();
                    String date2 = bundle2.getString("msg");
                    setStatusBarColor(date2);
                    break;
                case 3:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity2);
        forum_context = findViewById(R.id.forum_context);
        url = getIntent().getStringExtra("jump_url");
        int i = getStatusBarHeight();
        if (null != url) {
            webSettings();
            webView(url);
        } else {
            finish();
        }
        MyUserInstance.getInstance().getWxPayBuilder(this).setPayCallback(this);


        MyUserInstance.getInstance().getAliPayBuilder(this).setPayCallback(this);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);// 创建参数对象，设置宽和高
        layoutParams.setMargins(0, i, 0, 0);
        setStatusBarColor("#ffffff");
        setAndroidNativeLightStatusBar(true);
        dialog = Dialogs.createLoadingDialog(this);
    }


    public int getStatusBarHeight() {
        Resources resources = getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
    private void openFileChooseProcess() {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        startActivityForResult(Intent.createChooser(i, "test"), 0);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    if (null != uploadFiles) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFiles.onReceiveValue(new Uri[]{result});
                        uploadFiles = null;
                    }
                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }
    }
    /**
     * webView的相关设置
     */
    private void webView(String url) {
        //为了更好的支持表单
        forum_context.setFocusable(true);
        forum_context.setFocusableInTouchMode(true);
        forum_context.requestFocus();
        forum_context.setWebViewClient(new WebViewClient() {
            //目的是要让我们应用自己来加载网页，而不是交给浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;
            }



            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                handler.proceed();
                super.onReceivedSslError(view, handler, error);
            }
        });

        forum_context.setWebChromeClient(new WebChromeClient() {
            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.i("test", "openFileChooser 1");
                WebShopActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                Log.i("test", "openFileChooser 2");
                WebShopActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.i("test", "openFileChooser 3");
                WebShopActivity.this.uploadFile = uploadFile;
                openFileChooseProcess();
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                Log.i("test", "openFileChooser 4:" + filePathCallback.toString());
                WebShopActivity.this.uploadFiles = filePathCallback;
                openFileChooseProcess();
                return true;
            }

        });

        forum_context.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void postMessage(String msg) {

                JSONObject jsonObject = JSON.parseObject(msg);
                if (jsonObject.getJSONObject("data") == null) {
                    return;
                }
                JSONObject data = jsonObject.getJSONObject("data");
                switch (jsonObject.getString("method")) {
                    case "setStatusBarBackColor":
                        //修改状态栏背景颜色

                        Bundle bundle = new Bundle();
                        bundle.putString("msg", data.getString("color"));
                        Message message = Message.obtain();
                        message.setData(bundle);   //message.obj=bundle  传值也行
                        message.what = 2;
                        handler.sendMessage(message);

                        break;
                    case "pay":

                        //调用支付
                        //  HttpUtils.getInstance().getVipAliPayOrder();
                        String order_no = data.getString("order_no");
                        String total_fee = data.getString("total_fee");
                        String pay_channel = data.getString("pay_channel");

                        if (TextUtils.isEmpty(order_no)) {
                            switch (pay_channel) {
                                case "1":

                                    MyUserInstance.getInstance().getWxPayBuilder(WebShopActivity.this).payDeposit(pay_channel);
                                    break;
                                case "2":
                                    MyUserInstance.getInstance().getAliPayBuilder(WebShopActivity.this).payDeposit(pay_channel);
                                    break;
                            }

                        } else {
                            switch (pay_channel) {
                                case "1":
                                    MyUserInstance.getInstance().getWxPayBuilder(WebShopActivity.this).getWxShopPayOrder(order_no,total_fee);

                                    break;
                                case "2":
                                    MyUserInstance.getInstance().getAliPayBuilder(WebShopActivity.this).getAliShopPayOrder(order_no,total_fee);

                                    break;
                            }
                        }

                        //单号为空

                        //单号不为空
                        break;
                    case "chat":

                       TxImUtils.getSocketManager().startChat(data.getString("uid"), data.getString("nick_name"),"");

                        //调用私信
                        break;
                    case "setStatusBarTintStyle":
                        //修改状态栏文字颜色


                        Bundle bundle2 = new Bundle();
                        bundle2.putString("msg", data.getString("style"));
                        Message message2 = Message.obtain();
                        message2.setData(bundle2);   //message.obj=bundle  传值也行
                        message2.what = 1;
                        handler.sendMessage(message2);
                        break;
                    case "popWebView":

                        finish();
                        //关闭webview
                        break;
                    case "logout":
                        Hawk.remove("USER_REGIST");
                        MyUserInstance.getInstance().setUserInfo(null);
                        AppManager.getAppManager().startActivity(LoginActivity.class);
                        AppManager.getAppManager().finishAllActivity();

                        finish();
                        //关闭webview
                        break;
                }
            }

        }, "jsCallNative");
        forum_context.loadUrl(url);
    }

    private void setAndroidNativeLightStatusBar(boolean dark) {
        View decor = getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    /**
     * web的相关设置
     */
    private void webSettings() {
        mWebSettings = forum_context.getSettings();
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //让webview支持js
        mWebSettings.setJavaScriptEnabled(true);
        //设置是否支持缩放模式
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        // 是否显示+ -
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setDomStorageEnabled(true);
    }


    public void setStatusBarColor(String colorId) {
        Window window = getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.parseColor(colorId));
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

    @Override
    public void onSuccess() {
        ToastUtils.showT("充值成功");
        forum_context.loadUrl("javascript:payCallback('SUCCESS')");
    }

    @Override
    public void onFailed() {
        ToastUtils.showT("充值失败");
        forum_context.loadUrl("javascript:payCallback('FAIL')");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && forum_context.canGoBack()) {
            forum_context.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
