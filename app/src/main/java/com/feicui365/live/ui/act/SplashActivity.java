package com.feicui365.live.ui.act;

import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.contract.LoginContract;
import com.feicui365.live.live.utils.PkScreenUtils;
import com.feicui365.live.util.CountDownUtil2;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.UserConfig;
import com.feicui365.live.presenter.LoginPresenter;
import com.feicui365.live.dialog.LivePriceDialog;
import com.feicui365.live.util.MyUserInstance;

import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.orhanobut.hawk.Hawk;

import butterknife.BindView;


public class SplashActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {

    CountDownUtil2 countDownUtil;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.iv_ad)
    ImageView iv_ad;


    @Override
    protected int getLayoutId() {

        return R.layout.splash_activity;
    }

    @Override
    protected void initData() {
        MyUserInstance.getInstance().initVistor();
        PkScreenUtils.getAndroiodScreenProperty(this);
        mPresenter.getCommonConfig();
    }


    @Override
    protected void initView() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        tv_time.setClickable(false);

        hideTitle(true);
        countDownUtil = new CountDownUtil2(3000, 1000, tv_time, this);
        ((MyApp) MyApp.getInstance()).setLogin_mode(false);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）
        int height = metric.heightPixels;  // 屏幕高度（像素）
        AppStatusManager.getInstance().setAppStatus(1);
        MyUserInstance.getInstance().setDevice_height(height);
        MyUserInstance.getInstance().setDevice_width(width);

    }


    @Override
    public void onError(Throwable throwable) {
        initDialog();
    }

    @Override
    public void getCommonConfig(BaseResponse<UserConfig> bean) {
        if (bean.isSuccess()) {
            tv_time.setVisibility(View.VISIBLE);
            UserConfig userConfig = bean.getData();
            userConfig.getConfig().setBeauty_channel(0);
            MyUserInstance.getInstance().setUserConfig(userConfig);
            Hawk.put("USER_CONFIG", JSON.toJSONString(userConfig));
            countDownUtil.start();
            if (null != userConfig.getLaunch_ad()) {
                if (null != userConfig.getLaunch_ad().getImage_url()) {
                    Glide.with(SplashActivity.this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.splash).dontAnimate()).load(userConfig.getLaunch_ad().getImage_url()).into(iv_ad);

                    iv_ad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            switch (userConfig.getLaunch_ad().getJump_type()) {
                                case "1":
                                    Intent i = new Intent(SplashActivity.this, WebViewActivity.class);
                                    i.putExtra("jump_url", (userConfig.getLaunch_ad().getJump_url()));
                                    i.putExtra("title", (userConfig.getLaunch_ad().getTitle()));
                                    startActivity(i);
                                    break;
                                case "2":
                                    Intent intent = new Intent();
                                    intent.setAction("android.intent.action.VIEW");
                                    Uri content_url = Uri.parse(userConfig.getLaunch_ad().getJump_url());
                                    intent.setData(content_url);
                                    startActivity(intent);
                                    break;
                            }

                        }
                    });
                }
            }

        } else {
            Toast.makeText(this, bean.getMsg(), Toast.LENGTH_LONG).show();
        }


    }


    private void initDialog() {
        LivePriceDialog.Builder builder = new LivePriceDialog.Builder(this);
        builder.create();
        builder.setContent("基础配置更新失败,请点击重试");
        builder.setTitle("获取配置失败");
        builder.setSubmitText("重试");
        builder.setOnSubmit(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getCommonConfig();
                builder.livePriceDialog.dismiss();
            }
        });
        builder.setCanCancel(false);
        builder.setCancelHide(true);
        builder.livePriceDialog.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }


}





