package com.feicui365.live.util;

import android.os.Environment;

import com.dueeeke.videoplayer.util.L;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;



public class DownloadUtil2 {
    private static String SDPATH = "";

    public void download(String tag, String fileDir, String fileName, String url, final Callback callback) {
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        OkGo.<File>get(url).tag(tag).execute(new FileCallback(fileDir, fileName) {
            @Override
            public void onSuccess(Response<File> response) {
                //下载成功结束后的回调
                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void downloadProgress(Progress progress) {
                if (callback != null) {
                    int val = (int) (progress.currentSize * 100 / progress.totalSize);
                    L.e("下载进度--->" + val);
                    callback.onProgress(val);
                }
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                Throwable e = response.getException();
                L.e("下载失败--->" + e);
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    public static void download(String tag, File fileDir, String fileName, String url, final Callback callback) {
        OkGo.<File>get(url).tag(tag).execute(new FileCallback(fileDir.getAbsolutePath(), fileName) {
            @Override
            public void onSuccess(Response<File> response) {
                //下载成功结束后的回调
                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void downloadProgress(Progress progress) {
                if (callback != null) {
                    int val = (int) (progress.currentSize * 100 / progress.totalSize);
                    L.e("下载进度--->" + val);
                    callback.onProgress(val);
                }
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                Throwable e = response.getException();
                L.e("下载失败--->" + e);
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }


    public void download2(String tag, File fileDir, String fileName, String url, final Callback callback) {

        OkGo.<File>get(url).tag(tag).execute(new FileCallback(fileDir.getAbsolutePath(), fileName) {
            @Override
            public void onSuccess(Response<File> response) {
                //下载成功结束后的回调
                if (callback != null) {
                    callback.onSuccess(response.body());
                }
            }

            @Override
            public void downloadProgress(Progress progress) {
                if (callback != null) {
                    int val = (int) (progress.currentSize * 100 / progress.totalSize);
                    L.e("下载进度--->" + val);
                    callback.onProgress(val);
                }
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
                Throwable e = response.getException();
                L.e("下载失败--->" + e);
                if (callback != null) {
                    callback.onError(e);
                }
            }
        });
    }

    /**
     * 获取到sd卡的根目录，并以String形式返回
     *
     * @return
     */
    public static String getSDCardPath() {
        SDPATH = Environment.getExternalStorageDirectory() + "/";
        return SDPATH;
    }

    /**
     * 创建文件或文件夹
     *
     * @param fileName 文件名或问文件夹名
     */
    public static File createFile(String fileName) {
        File file = new File(getSDCardPath() + fileName);
        // 创建文件夹
        if(!file.exists()){
            file.mkdir();
        }

        System.out.println("创建了文件夹");
        return file;

    }

    public interface Callback {
        void onSuccess(File file);

        void onProgress(int progress);

        void onError(Throwable e);
    }
}
