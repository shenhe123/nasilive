package com.feicui365.live.util;

import com.feicui365.live.base.Constants;
import com.feicui365.live.interfaces.CommonCallback;

import java.io.File;



public class GifCacheUtil {

    public static void getFile(String fileName, String url, final CommonCallback<File> commonCallback) {
        if (commonCallback == null) {
            return;
        }
        File dir = new File(Constants.GIF_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, fileName);
        if (file.exists()) {
            commonCallback.callback(file);
        } else {
            DownloadUtil2 downloadUtil = new DownloadUtil2();
            downloadUtil.download(Constants.DOWNLOAD_GIF, dir, fileName, url, new DownloadUtil2.Callback() {
                @Override
                public void onSuccess(File file) {
                    commonCallback.callback(file);
                }

                @Override
                public void onProgress(int progress) {

                }

                @Override
                public void onError(Throwable e) {
                    commonCallback.callback(null);
                }
            });
        }
    }

}
