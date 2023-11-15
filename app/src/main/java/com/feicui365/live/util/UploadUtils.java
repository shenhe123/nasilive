package com.feicui365.live.util;

import android.arch.lifecycle.Lifecycle;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.base.MyApp;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.interfaces.OnPicUpLoadPicsListener;
import com.feicui365.live.interfaces.OnVideoThumbUpLoadPicsListener;
import com.feicui365.live.interfaces.OnVideoUpLoadPicsListener;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.presenter.HomePresenter;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class UploadUtils implements HomeContract.View {
    private QCloudData qCloudData;
    private HomePresenter mPresenter;
    private BaseMvpActivity mActivity;
    private String bucketAdmin;
    private String cosPathAdmin;
    private String cosPathBlurAdmin;
    private CosXmlService cosXmlService;
    private HashMap<Integer, File> mFiles = new HashMap();

    public UploadUtils(BaseMvpActivity activity) {
        this.mActivity = activity;
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);

        bucketAdmin = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_bucket(); //存储桶，格式：BucketName-APPID
        cosPathAdmin = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_folder_image();
        cosPathBlurAdmin =MyUserInstance.getInstance().getUserConfig().getConfig().getCos_folder_blurimage(); //对象在存储桶中的位置标识符，即称对象键
    }

    public void getConfig() {
        mPresenter.getTempKeysForCos();
    }

    @Override
    public void getTempKeysForCos(QCloudData bean) {
        initQCloud(bean);
    }


    public void initQCloud(QCloudData data) {
        String appid =  MyUserInstance.getInstance().getUserConfig().getConfig().getQcloud_appid();
        String region =  MyUserInstance.getInstance().getUserConfig().getConfig().getCos_region();
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .isHttps(true)
                .builder();
        QCloudCredentialProvider credentialProvider = new MyCredentialProvider(data.getCredentials().getTmpSecretId(),
                data.getCredentials().getTmpSecretKey(),
                data.getCredentials().getSessionToken(),
                data.getExpiredTime(), data.getStartTime());
        cosXmlService = new CosXmlService(mActivity, serviceConfig, credentialProvider);

    }


    public void uploadPics(ArrayList<String> mFileUrls, boolean isBlur, OnPicUpLoadPicsListener onPicUpLoadPicsListener) {
        if (cosXmlService == null) {
            ToastUtils.showT("上传工具出错请稍后再试");
            if (onPicUpLoadPicsListener != null) {
                onPicUpLoadPicsListener.onError();
            }
            return;
        }
        for (int i = 0; i < mFileUrls.size(); i++) {
            String tempName = "Android" + System.currentTimeMillis() + getFileType(mFileUrls.get(i));
            String cosPath = cosPathAdmin + "/" + tempName;
            String cosPathBlur = cosPathBlurAdmin + "/" + tempName; //对象在存储桶中的位置标识符，即称对象键
            Set<String> headerKeys = new HashSet<>();
            headerKeys.add("Host");
            PutObjectRequest porUpload = new PutObjectRequest(bucketAdmin, cosPath, mFileUrls.get(i));
            porUpload.setSignParamsAndHeaders(null, headerKeys);
            if (isBlur) {
                porUpload.setXCOSMeta("Pic-Operations", "{\"is_pic_info\":0,\"rules\":[{\"fileid\":\"" + "/" + cosPathBlur + "\",\"rule\":\"imageMogr2/blur/50x25\"}]}");

            }
            Log.e("TUTUTU", "getFileType: =  "+getFileType(mFileUrls.get(i) +"mFileUrls.get(i) ="+mFileUrls.get(i)
                  +  "   cosPath = "+cosPath + "    cosPathBlur = "+cosPathBlur + "   bucketAdmin="+bucketAdmin ));
            cosXmlService.putObjectAsync(porUpload, new CosXmlResultListener() {
                @Override
                public void onSuccess(CosXmlRequest request, CosXmlResult result) {

                    if (onPicUpLoadPicsListener != null) {
                        if (isBlur) {
                            String accessUrl = result.accessUrl;
                            Log.e("TUTUTU", "onSuccess: isBlur"+result.accessUrl );
                            //模糊地址
                            StringBuilder stringBuilder = new StringBuilder(accessUrl);
                            int index = accessUrl.indexOf("images/");
                            String blurUrl = stringBuilder.replace(index, accessUrl.length(), cosPathBlur).toString();
                            Log.e("TUTUTU", "onSuccess: isBlur blurUrl"+blurUrl );
                            onPicUpLoadPicsListener.onPicUrls(accessUrl, blurUrl);
                        } else {
                            Log.e("TUTUTU", "onSuccess: "+result.accessUrl );
                            onPicUpLoadPicsListener.onPicUrls(result.accessUrl, null);
                        }

                    }
                }

                @Override
                public void onFail(CosXmlRequest request, CosXmlClientException
                        exception, CosXmlServiceException serviceException) {


                    if (onPicUpLoadPicsListener != null) {
                        onPicUpLoadPicsListener.onError();
                    }

                }
            });
        }


    }


    public void uploadVideo(String mFileUrls, OnVideoUpLoadPicsListener onVideoUpLoadPicsListener) {
        if (cosXmlService == null) {
            ToastUtils.showT("上传工具出错请稍后再试");
            if (onVideoUpLoadPicsListener != null) {
                onVideoUpLoadPicsListener.onError();
            }
            return;
        }

        String tempName = "Android" + System.currentTimeMillis() + getFileType(mFileUrls);
        String cosPath = cosPathAdmin + "/" + tempName;
        Set<String> headerKeys = new HashSet<>();
        headerKeys.add("Host");
        PutObjectRequest porUpload = new PutObjectRequest(bucketAdmin, cosPath, mFileUrls);
        porUpload.setSignParamsAndHeaders(null, headerKeys);
        cosXmlService.putObjectAsync(porUpload, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                String accessUrl = result.accessUrl;
                if (onVideoUpLoadPicsListener != null) {
                    onVideoUpLoadPicsListener.onVideoUrls(accessUrl);
                }
            }

            @Override
            public void onFail(CosXmlRequest request, CosXmlClientException
                    exception, CosXmlServiceException serviceException) {


                if (onVideoUpLoadPicsListener != null) {
                    onVideoUpLoadPicsListener.onError();
                }

            }
        });
    }

    public void uploadVideoThumb(String mFileUrls, OnVideoThumbUpLoadPicsListener onVideoThumbUpLoadPicsListener){
        if (cosXmlService == null) {
            ToastUtils.showT("上传工具出错请稍后再试");
           return;
        }

        GlideUtils.getImageDrawable(MyApp.getInstance(), mFileUrls, new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull @NotNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                File mFile= ArmsUtils.drawableToFile(resource);
                String tempName = "Android" + System.currentTimeMillis() + getFileType(mFile);
                String cosPath = cosPathAdmin + "/" + tempName;
                Set<String> headerKeys = new HashSet<>();
                headerKeys.add("Host");
                PutObjectRequest porUpload = new PutObjectRequest(bucketAdmin, cosPath, mFile.getAbsolutePath());
                porUpload.setSignParamsAndHeaders(null, headerKeys);

                cosXmlService.putObjectAsync(porUpload, new CosXmlResultListener() {
                    @Override
                    public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                        String accessUrl = result.accessUrl;
                        if(onVideoThumbUpLoadPicsListener!=null){
                            onVideoThumbUpLoadPicsListener.onThumbUrls(accessUrl);
                        }
                    }

                    @Override
                    public void onFail(CosXmlRequest request, CosXmlClientException
                            exception, CosXmlServiceException serviceException) {
                        if(onVideoThumbUpLoadPicsListener!=null){
                            onVideoThumbUpLoadPicsListener.onError();
                        }

                    }
                });
            }

            @Override
            public void onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                if(onVideoThumbUpLoadPicsListener!=null){
                    onVideoThumbUpLoadPicsListener.onError();
                }
            }
        });




    }


    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(mActivity, Lifecycle.Event.ON_DESTROY));
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(Throwable throwable) {

    }



    /**
     * 注释
     * 创建文件通过URL
     */
    private File createFileFormUrl(String url) {
        try {
            File path = new File(Constants.PIC_PATH);
            if (!path.exists()) {
                path.mkdir();
            }
            File mTempFile = new File(url);
            String mTempName = "Android" + System.currentTimeMillis();
            String mTempType = getFileType(mTempFile);
            File mTempFileCopy = mTempFile.createTempFile(mTempName, mTempType, path);
            copyfile(mTempFile, mTempFileCopy, true);
            return mTempFileCopy;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 注释
     * 获取文件后缀
     */
    private String getFileType(File file) {
        String path;
        String[] reslut = file.getName().split("\\.");
        path = "." + reslut[reslut.length - 1];

        return path;
    }

    private String getFileType(String file) {
        String path;
        String[] reslut = file.split("\\.");
        path = "." + reslut[reslut.length - 1];

        return path;
    }

    /**
     * 注释
     * 复制文件
     */
    public void copyfile(File fromFile, File toFile, Boolean rewrite) {
        if (!fromFile.exists()) {
            return;
        }
        if (!fromFile.isFile()) {
            return;
        }

        if (!fromFile.canRead()) {
            return;
        }

        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }

        if (toFile.exists() && rewrite) {
            toFile.delete();
        }
        //当文件不存时，canWrite一直返回的都是false
        try {
            java.io.FileInputStream fosfrom = new java.io.FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c); //将内容写到新文件当中
            }
            fosfrom.close();
            fosto.close();
        } catch (Exception ex) {
            Log.e("readfile", ex.getMessage());
        }
    }

}
