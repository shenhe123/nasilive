package com.feicui365.live.ui.act;



import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.LoginContract;
import com.feicui365.live.presenter.LoginPresenter;
import com.tencent.smtt.export.external.interfaces.SslError;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;


public class WebViewActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.forum_context)
    WebView forum_context;

    String title = "";
    String url = "";

    private WebSettings mWebSettings;

    @Override
    protected int getLayoutId() {
        return R.layout.webview_activity;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        title = getIntent().getStringExtra("title");
        url = getIntent().getStringExtra("jump_url");
        if (null != title) {
            setTitle(title);
        } else {
            setTitle("网页");
        }
        if(title==null){
            hideTitle(true);
        }

        if (null != url) {
            webSettings();
            webView(url);
        } else {
            finish();
        }



    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setAndroidNativeLightStatusBar(true);
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
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

            }
        });
        forum_context.loadUrl(url);

        forum_context.addJavascriptInterface(new Object() {
            @JavascriptInterface
            public void postMessage(String msg) {

                JSONObject jsonObject = JSON.parseObject(msg);
                if (jsonObject.getJSONObject("data") == null) {
                    return;
                }
                JSONObject data = jsonObject.getJSONObject("data");
                switch (jsonObject.getString("method")) {



                    case "popWebView":

                        finish();
                        break;

                }
            }

        }, "jsCallNative");
    }

    /**
     * web的相关设置
     */
    private void webSettings() {
        mWebSettings = forum_context.getSettings();
        //让webview支持js
        mWebSettings.setJavaScriptEnabled(true);
        //设置是否支持缩放模式
        mWebSettings.setSupportZoom(true);
        mWebSettings.setBuiltInZoomControls(true);
        // 是否显示+ -
        mWebSettings.setDisplayZoomControls(false);
        mWebSettings.setDomStorageEnabled(true);
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
