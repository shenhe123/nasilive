/*
package com.feicui365.live.util;

import android.app.Dialog;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import com.feicui365.live.widget.Dialogs;


import java.util.ArrayList;

public class TxPushUtils {
    Context context;
    //签名
    String signature;
    //视频路径
    String videoPath;
    //图片路径
    ArrayList picPath;
    //视频封面路径
    String coverPath;
    //腾讯上传框架
    TXUGCPublish mVideoPublish;
    String  mCosSignature="";
    private android.os.Handler mHandler = new android.os.Handler();

    public OnFinishListener onFinishListener;

    public interface OnFinishListener {
        void onClick(TXUGCPublishTypeDef.TXPublishResult result);

    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    public void setmVideoPublish(TXUGCPublish mVideoPublish) {
        this.mVideoPublish = mVideoPublish;
    }

    public TxPushUtils(Context context) {
        this.context = context;
         mVideoPublish = new TXUGCPublish(context.getApplicationContext(), "independence_android");
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public void setPicPath(ArrayList picPath) {
        this.picPath = picPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }


    private void pushVideo(String videoPath,String coverPath){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                showLoading();

                mVideoPublish.setListener(new TXUGCPublishTypeDef.ITXVideoPublishListener() {
                    @Override
                    public void onPublishProgress(long uploadBytes, long totalBytes) {
                        //   mProgress.setProgress((int) (100*uploadBytes/totalBytes));
                    }

                    @Override
                    public void onPublishComplete(TXUGCPublishTypeDef.TXPublishResult result) {
                        //mResultMsg.setText(result.retCode + " Msg:" + (result.retCode == 0 ? result.videoURL : result.descMsg));
                        if(null!=onFinishListener){
                            onFinishListener.onClick(result);
                            hideLoading();
                        }
                    }

                });
                TXUGCPublishTypeDef.TXPublishParam param = new TXUGCPublishTypeDef.TXPublishParam();

                param.signature = mCosSignature;
                param.videoPath = videoPath;
                param.coverPath = coverPath;
                int publishCode = mVideoPublish.publishVideo(param);
            }
        });

    }


    private Dialog dialog;

    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(context);
        dialog.show();
    }


    public void hideLoading() {
        if (null != dialog){
            dialog.dismiss();
        }
    }


    */
/**
     * Step1:获取签名
     *//*

    public void fetchSignature(String videoPath,String coverPath) {

        showLoading();
        TCUserMgr.getInstance().getVodSig2(MyUserInstance.getInstance().getUserinfo().getId(), MyUserInstance.getInstance().getUserinfo().getToken(), new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                com.alibaba.fastjson.JSONObject data  = JSON.parseObject(response.body());


                if (data.get("status").toString().equals("0")) {
                    mCosSignature=data.getJSONObject("data").getString("signature");
                    pushVideo(videoPath,coverPath);
                    LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_VIDEO_SIGN, TCUserMgr.SUCCESS_CODE, "获取签名成功");
                }

            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                hideLoading();
                LogReport.getInstance().uploadLogs(LogReport.ELK_ACTION_VIDEO_SIGN, -1, "获取签名失败");
            }
        });


    }



}
*/
