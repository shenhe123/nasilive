package com.feicui365.live.base;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyCredentialProvider;
import com.feicui365.live.util.MyUserInstance;
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

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class OtherBaseFragment extends Fragment {

    private CosXmlService cosXmlService;
    private String thumb;
    private Dialog dialog;
    public RequestOptions options;
    private OnUploadFinshListener onUploadFinshListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        options = new RequestOptions()
                .centerCrop();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setOnItemListener(OnUploadFinshListener onUploadFinshListener) {
        this.onUploadFinshListener = onUploadFinshListener;
    }

    public interface OnUploadFinshListener {
        void onFinish(String url);
    }


    public void upLoadImage(QCloudData data, String imagePath) {


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


        cosXmlService = new CosXmlService(getContext(), serviceConfig, credentialProvider);


        File imageFile = new File(imagePath);
        String bucket = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_bucket(); //存储桶，格式：BucketName-APPID
        String cosPath = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_folder_image() + "/" + imageFile.getName(); //对象在存储桶中的位置标识符，即称对象键
        String srcPath = imagePath; //本地文件的绝对路径
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
        Set<String> headerKeys = new HashSet<>();
        headerKeys.add("Host");
        putObjectRequest.setSignParamsAndHeaders(null, headerKeys);
        // 使用异步回调上传
        cosXmlService.putObjectAsync(putObjectRequest, new CosXmlResultListener() {

            @Override
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult result) {

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


    public void updateUserInfo() {
        HttpUtils.getInstance().getUserInfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject data = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
                    if (null != data) {
                        UserRegist userRegist = (UserRegist) JSONObject.parseObject(data.toString(), UserRegist.class);
                        MyUserInstance.getInstance().setUserInfo(userRegist);
                        hideLoading();
                        getActivity().finish();
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }

            }
        });
    }

    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this.getContext());
        dialog.show();
    }

    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    public void showT(String string) {
        Toast.makeText(getContext(), string, Toast.LENGTH_SHORT).show();
    }

    private static final int MIN_DELAY_TIME= 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;
    @SuppressLint("LongLogTag")
    public  boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        Log.e("isFastClick_currentClickTime",currentClickTime+"");
        Log.e("isFastClick_lastClickTime",lastClickTime+"");
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        Log.e("isFastClick_flag",flag+"");
        return flag;
    }
}
