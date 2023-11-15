package com.feicui365.live.shop.activity;

import android.Manifest;
import android.content.Intent;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.ShopContract;
import com.feicui365.live.presenter.ShopPresenter;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.nasinet.userconfig.AppStatusManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends BaseMvpActivity<ShopPresenter> implements ShopContract.View {
    @BindView(R.id.zx_view)
    ZXingView zx_view;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setTitleBackroundColor(0);
        setTitle("二维码扫描");
        AppStatusManager.getInstance().setAppStatus(1);
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                .subscribe(granted -> {
                    if (granted) {
                        zx_view.startCamera();
                        zx_view.startSpotAndShowRect();
                        zx_view.setDelegate(new QRCodeView.Delegate() {
                            @Override
                            public void onScanQRCodeSuccess(String result) {
                                Intent intent = new Intent();
                                intent.putExtra("code", result);
                                setResult(RESULT_OK, intent);
                                finish();

                            }

                            @Override
                            public void onCameraAmbientBrightnessChanged(boolean isDark) {

                            }

                            @Override
                            public void onScanQRCodeOpenCameraError() {

                            }
                        });

                    } else {
                        ToastUtils.showT("用户没有授权相机权限");
                    }
                });
    }


    @Override
    public void onError(Throwable throwable) {

    }
}
