package com.feicui365.live.util;

import android.content.Context;


import com.feicui365.live.model.entity.QCloudData;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;

public class TxPicturePushUtils {
    private static TxPicturePushUtils instance;
    private CosXmlService cosXmlService;
    private Context mContext;

    private TxPicturePushUtils() {
    }

    /**
     * 单实例 , UI无需考虑多线程同步问题
     */
    public static TxPicturePushUtils getInstance() {
        if (instance == null) {
            instance = new TxPicturePushUtils();

        }
        return instance;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public CosXmlService initQCloud(QCloudData data) {
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


        cosXmlService = new CosXmlService(mContext, serviceConfig, credentialProvider);
        return cosXmlService;
    }



}
