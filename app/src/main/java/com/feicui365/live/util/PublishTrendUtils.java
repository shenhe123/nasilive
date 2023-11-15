package com.feicui365.live.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.feicui365.live.ui.act.PublishTrendActivity;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class PublishTrendUtils {

    PublishTrendActivity context;

    String bucket_admin ="";
    String cosPath_admin ="";
    String cosPath_blur_admin = "";

    public PublishTrendUtils(PublishTrendActivity context) {
        this.context = context;
         bucket_admin = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_bucket(); //存储桶，格式：BucketName-APPID
         cosPath_admin = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_folder_image();
         cosPath_blur_admin = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_folder_blurimage(); //对象在存储桶中的位置标识符，即称对象键

    }




    public void startPushPic() {
        if (context.mPhotosSnpl.getData().size()==0) {
            ToastUtils.showT("请选择图片后再发布");
            return;
        }

        if (context.trend_price > 0) {
            upLoadImage(context.mPhotosSnpl.getData(), true);
        } else {
            upLoadImage(context.mPhotosSnpl.getData(), false);
        }

    }


    public void startPushVideo() {
        if (context.video_url.equals("")) {
            ToastUtils.showT("请选择视频后再发布");
            return;
        }

        if (context.trend_price > 0) {
            upLoadVideoPic(context.video_url, true);
        } else {
            upLoadVideoPic(context.video_url, false);
        }


    }

    private void upLoadVideoPic(String video_url, boolean is_dim) {
        if (context.cosXmlService == null) {
            return;
        }
        context.showLoading();
        context.urls.clear();
        context.blur_urls.clear();
        File imageFile_video = new File(video_url);
        Glide.with(context).load(video_url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                //保存视频封面
                File imageFile = saveMyBitmap(((BitmapDrawable) resource).getBitmap(), getFileNameNoEx(imageFile_video.getName()));

                String bucket = bucket_admin; //存储桶，格式：BucketName-APPID
                String cosPath = cosPath_admin + "/" + imageFile.getName();
                String cosPath_blur = cosPath_blur_admin + "/" + imageFile.getName(); //对象在存储桶中的位置标识符，即称对象键
                String srcPath = imageFile.getAbsolutePath(); //本地文件的绝对路径
                Set<String> headerKeys = new HashSet<>();
                headerKeys.add("Host");
                
                
                
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
                putObjectRequest.setSignParamsAndHeaders(null, headerKeys);
                context.cosXmlService.putObjectAsync(putObjectRequest, new CosXmlResultListener() {
                    @Override
                    public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult result) {
                        PutObjectResult putObjectResult = (PutObjectResult) result;
                        context.urls.add(putObjectResult.accessUrl);
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(context).load(context.urls.get(0)).into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            context.setSingle_display_type(context.getViewInfo(resource));
                                            if (context.single_display_type.equals("")) {
                                                return;
                                            }
                                            if(is_dim){
                                                upLoadVideo(context.video_url, true);
                                            }else{
                                                upLoadVideo(context.video_url, false);
                                            }

                                        }

                                        @Override
                                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                            super.onLoadFailed(errorDrawable);
                                        }
                                    });
                                }
                            });
                    }

                    @Override
                    public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException) {

                    }
                });
            }
        });

    }

    private void upLoadVideo(String imagePath, boolean is_dim) {

        if (context.cosXmlService == null) {
            return;
        }

        
        File imageFile = new File(imagePath);
        String bucket = bucket_admin; //存储桶，格式：BucketName-APPID
        String cosPath = cosPath_admin + "/" + imageFile.getName();
        String srcPath = imagePath; //本地文件的绝对路径
        
        Set<String> headerKeys = new HashSet<>();
        headerKeys.add("Host");
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
        putObjectRequest.setSignParamsAndHeaders(null, headerKeys);
        context.cosXmlService.putObjectAsync(putObjectRequest, new CosXmlResultListener() {

            @Override
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult result) {
                PutObjectResult putObjectResult = (PutObjectResult) result;
                context.video_url = putObjectResult.accessUrl;
                
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (is_dim) {
                            context.starPush(7);
                        } else {
                            context.starPush(8);
                        }
                    }
                });

            }

            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException) {

            }
        });
    }



    private void upLoadImage(ArrayList<String> imagePath, boolean is_dim) {

        if (context.cosXmlService == null) {
            return;
        }
        context.showLoading();
        context.urls.clear();
        context.blur_urls.clear();

        for (int i = 0; i < imagePath.size(); i++) {

            String name_temp="IMG_ANDROID_"+getTime()+"_"+new Random().nextInt(99999);
            String bucket = bucket_admin; //存储桶，格式：BucketName-APPID
            String cosPath = cosPath_admin + "/" + name_temp;
            String cosPath_blur = cosPath_blur_admin + "/" + name_temp; //对象在存储桶中的位置标识符，即称对象键
            String srcPath = imagePath.get(i); //本地文件的绝对路径

            Set<String> headerKeys = new HashSet<>();
            headerKeys.add("Host");


            if (is_dim) {

                PutObjectRequest putObjectRequest_blur = new PutObjectRequest(bucket, cosPath, srcPath);
                putObjectRequest_blur.setSignParamsAndHeaders(null, headerKeys);
                putObjectRequest_blur.setXCOSMeta("Pic-Operations", "{\"is_pic_info\":0,\"rules\":[{\"fileid\":\"" +"/"+cosPath_blur+ "\",\"rule\":\"imageMogr2/blur/50x25\"}]}");
                context.cosXmlService.putObjectAsync(putObjectRequest_blur, new CosXmlResultListener() {
                    @Override
                    public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult result) {
                        PutObjectResult putObjectResult = (PutObjectResult) result;
                        String accessUrl=putObjectResult.accessUrl;
                        context.urls.add(accessUrl);

                        StringBuilder stringBuilder=new StringBuilder(accessUrl);
                        int index= accessUrl.indexOf("images/");
                        String blur=  stringBuilder.replace(index,accessUrl.length(),cosPath_blur).toString();
                        context.blur_urls.add(blur);
                        //收费开启模糊
                        if (context.urls.size() == context.mPhotosSnpl.getData().size()) {
                            
                            if (context.urls.size() == 1) {
                                //如果是一张图
                                getGlideForPush(5);
                            } else {
                                //如果多张图
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        context.starPush(6);
                                    }
                                });

                            }

                        }
                    }

                    @Override
                    public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException) {
                        context.urls.add("");
                        context.blur_urls.add("");
                        //收费开启模糊
                        if (context.urls.size() == context.mPhotosSnpl.getData().size()) {

                            if (context.urls.size() == 1) {
                                //如果是一张图
                                ToastUtils.showT("图片上传失败,请再次尝试");
                                context.urls.clear();
                                context.blur_urls.clear();
                            } else {
                                //如果多张图
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        context.starPush(6);
                                    }
                                });

                            }

                        }
                    }
                });

            }else{
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
                putObjectRequest.setSignParamsAndHeaders(null, headerKeys);
                context.cosXmlService.putObjectAsync(putObjectRequest, new CosXmlResultListener() {
                    @Override
                    public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult result) {
                        PutObjectResult putObjectResult = (PutObjectResult) result;
                        context.urls.add(putObjectResult.accessUrl);
                            //免费,不开模糊
                            if (context.urls.size() == context.mPhotosSnpl.getData().size()) {
                                if (context.urls.size() == 1) {
                                    getGlideForPush(3);
                                } else {
                                    //如果多张图
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            context.starPush(4);
                                        }
                                    });

                                }
                            }


                    }

                    @Override
                    public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException) {
                        context.urls.add("");

                        //收费开启模糊
                        if (context.urls.size() == context.mPhotosSnpl.getData().size()) {

                            if (context.urls.size() == 1) {
                                //如果是一张图
                                ToastUtils.showT("图片上传失败,请再次尝试");
                                context.urls.clear();

                            } else {
                                //如果多张图
                                context.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        context.starPush(4);
                                    }
                                });

                            }

                        }
                    }
                });



            }

        }
    }

    private void getGlideForPush(int type){
        //如果是一张图
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Glide.with(context).load(context.urls.get(0)).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        context.single_display_type = context.getViewInfo(resource);
                        if (context.single_display_type.equals("")) {
                            return;
                        }

                        context.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                context.starPush(type);
                            }
                        });
                    }
                });
            }
        });


    }


    //保存文件到指定路径
    public File saveMyBitmap(Bitmap bitmap, String name) {
        String sdCardDir = Environment.getExternalStorageDirectory() + "/DCIM/";
        File appDir = new File(sdCardDir, name);
        if (!appDir.exists()) {//不存在
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
       // Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
        return file;
    }

    /*
     * Java文件操作 获取不带扩展名的文件名
     * */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


    private String  getTime(){

        Date date = new Date();

        String time = date.toLocaleString();

        Log.i("md", "时间time为： "+time);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        String sim = dateFormat.format(date);
        return sim;
    }
}
