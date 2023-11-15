package com.feicui365.live.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.base.MyApp;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

public class ShareUtils {

    private static ShareUtils instance;
    public static Context context= MyApp.getInstance().getApplicationContext();
    private String short_video_id;
    private ShareUtils() {
    }


    public void setShort_video_id(String short_video_id) {
        this.short_video_id = short_video_id;
    }

    /**
     * 单实例 , UI无需考虑多线程同步问题
     */
    public static ShareUtils getInstance() {
        if (instance == null) {
            instance = new ShareUtils();

        }
        return instance;
    }




    public  void ShareWechat(String url,String title,String text,Bitmap rid,String image,String type){
        Platform.ShareParams sp = new Platform.ShareParams();
        if(title.equals("")){
            sp.setTitle("good直播-颜值主播等你来撩");
        }else{
            sp.setTitle(title);
        }
        sp.setTitleUrl(url); // 标题的超链接
        if(text.equals("")){
            sp.setText("邀您下载超好看的直播APP,颜值主播等你来撩");
        }else{
            sp.setText(text);
        }

        if(rid==null){
            rid = getBitmap(context, R.mipmap.logo);
        }
        sp.setImageData(rid);
        if(!image.equals("")){
            sp.setImageUrl(image);
        }

        sp.setUrl(url);
        sp.setSite("");
        sp.setShareType(Platform.SHARE_WEBPAGE);
        Platform pf= ShareSDK.getPlatform(type);

        pf.share(sp);

        pf.setPlatformActionListener(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
              if(short_video_id!=null){

                  if(!short_video_id.equals("")){


                      HttpUtils.getInstance().addShareCount(short_video_id, new StringCallback() {
                          @Override
                          public void onSuccess(Response<String> response) {
                              short_video_id="";
                              Log.e("TAG333","分享成功");
                          }

                          @Override
                          public void onError(Response<String> response) {
                              super.onError(response);
                              short_video_id="";
                              Log.e("TAG333","分享失败");
                          }
                      });
                  }else {
                      Log.e("TAG333","分享ID为空字符串");
                  }


              }else {
                  Log.e("TAG333","分享ID为空");
              }


            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            String a="";
            }

            @Override
            public void onCancel(Platform platform, int i) {
                String b="";
            }
        });
    }

    private  Bitmap getBitmap(Context context,int vectorDrawableId) {
        Bitmap bitmap=null;
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        }else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

}
