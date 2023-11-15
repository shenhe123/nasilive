/*
package com.feicui365.live.widget;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;

import com.lxj.xpopup.core.AttachPopupView;
import com.feicui365.live.R;
import com.feicui365.live.util.ToastUtils;

import com.tencent.liteav.basic.log.TXCLog;

import java.io.File;


public class CommentShortPopup extends AttachPopupView implements View.OnClickListener {

    Context context;
    LinearLayout ll_1,ll_2,ll_3,ll_4,ll_5;
    //表情结束
    public CommentShortPopup(@NonNull Context context) {
        super(context);

        this.context=context;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.custom_short_popup;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        ll_1=findViewById(R.id.ll_1);
        ll_2=findViewById(R.id.ll_2);
        ll_3=findViewById(R.id.ll_3);
        ll_4=findViewById(R.id.ll_4);
        ll_5=findViewById(R.id.ll_5);

        ll_1.setOnClickListener(this);
        ll_2.setOnClickListener(this);
        ll_3.setOnClickListener(this);
        ll_4.setOnClickListener(this);
        ll_5.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_1:

                context.startActivity(new Intent(context, TCVideoRecordActivity.class));
                break;
            case  R.id.ll_2:

                prepareToDownload(false);


                break;
            case  R.id.ll_3:
                prepareToDownload(true);

                break;
            case  R.id.ll_4:
                //    prepareToDownload(false);
                Intent intent = new Intent(context, TCVideoPickerActivity.class);
                context.startActivity(intent);
                break;
            case  R.id.ll_5:
                Intent intent2 = new Intent(context, TCPicturePickerActivity.class);
                context. startActivity(intent2);
                break;

        }
    }
    private ProgressDialog mDownloadProgressDialog;
    private void prepareToDownload(final boolean isTriple) {
        if (context == null) {
            return;
        }
        File sdcardDir = context.getExternalFilesDir(null);
        if (sdcardDir == null) {
            TXCLog.e("TAG", "prepareToDownload sdcardDir is null");
            return;
        }
        File downloadFileFolder = new File(sdcardDir, UGCKitConstants.OUTPUT_DIR_NAME);
        File downloadFile = new File(downloadFileFolder, DownloadUtil.getNameFromUrl(UGCKitConstants.CHORUS_URL));
        if (downloadFile.exists()) {
            mDownloadProgressDialog.dismiss();
            if (isTriple) {
                startTripleActivity(downloadFile.getAbsolutePath());
            } else {
                startRecordActivity(downloadFile.getAbsolutePath());
            }
            return;
        }
        if (mDownloadProgressDialog != null) {
            mDownloadProgressDialog.show();
        }
        DownloadUtil.get(context).download(UGCKitConstants.CHORUS_URL, UGCKitConstants.OUTPUT_DIR_NAME, new DownloadUtil.DownloadListener() {
            @Override
            public void onDownloadSuccess(final String path) {
                BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadProgressDialog.dismiss();
                        if (isTriple) {
                            startTripleActivity(path);
                        } else {
                            startRecordActivity(path);
                        }
                    }
                });
            }

            @Override
            public void onDownloading(final int progress) {
                BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadProgressDialog.setMessage("正在下载..." + progress + "%");
                    }
                });
            }

            @Override
            public void onDownloadFailed() {
                BackgroundTasks.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDownloadProgressDialog.dismiss();

                        ToastUtils.showT("下载失败");
                    }
                });
            }
        });
    }

    private void startRecordActivity(String path) {
        Intent intent = new Intent(context, TCVideoFollowRecordActivity.class);
        intent.putExtra(UGCKitConstants.VIDEO_PATH, path);
        context.startActivity(intent);

    }

    private void startTripleActivity(String path) {
        Intent intent = new Intent(context, TCVideoTripleScreenActivity.class);//TCVideoPreviewActivity
        intent.putExtra(UGCKitConstants.VIDEO_PATH, path);
        context.startActivity(intent);

    }



    @Override
    public Drawable getPopupBackground() {
        return getResources().getDrawable(R.drawable.shape_corner_white);
    }
}*/
