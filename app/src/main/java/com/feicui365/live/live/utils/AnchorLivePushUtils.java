package com.feicui365.live.live.utils;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;


import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePusher;
import com.tencent.live2.V2TXLivePusherObserver;
import com.tencent.live2.impl.V2TXLivePusherImpl;
import com.tencent.rtmp.ui.TXCloudVideoView;

import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.util.MyUserInstance;

/**
 * 主播推送用工具
 */
public class AnchorLivePushUtils {


    private V2TXLivePusher mLivePusher;
    private boolean isLive;
    private TXCloudVideoView mVideoView;
    V2TXLiveDef.V2TXLiveVideoEncoderParam mEncoderParam;

    public AnchorLivePushUtils() {


    }


    /**
     * 播放器需要做什么
     * 1,暂停
     * 2,恢复
     * 3,切换方向
     */

    public V2TXLivePusher startLive(Context context, HotLive data) {
        //1是0不是
        if (mVideoView == null) {
            return null;
        }
        mLivePusher = new V2TXLivePusherImpl(context, V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTC);
        mLivePusher.setRenderView(mVideoView);
        mLivePusher.startCamera(true);
        mLivePusher.startMicrophone();
        mEncoderParam = new V2TXLiveDef.V2TXLiveVideoEncoderParam(V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution1280x720);
        mEncoderParam.videoFps = 30;
        mLivePusher.setVideoQuality(mEncoderParam);
        if (MyUserInstance.getInstance().getUserConfig().getConfig().getBeauty_channel() == 1) {
            mLivePusher.enableCustomVideoProcess(true, V2TXLiveDef.V2TXLivePixelFormat.V2TXLivePixelFormatTexture2D, V2TXLiveDef.V2TXLiveBufferType.V2TXLiveBufferTypeTexture);

        }
        int a = mLivePusher.startPush(data.getPush_url());
        Log.e("TuiLiu", "startLive: "+a );
        mLivePusher.setObserver(new V2TXLivePusherObserver() {
            @Override
            public void onError(int code, String msg, Bundle extraInfo) {
                super.onError(code, msg, extraInfo);
            }

            @Override
            public int onProcessVideoFrame(V2TXLiveDef.V2TXLiveVideoFrame srcFrame, V2TXLiveDef.V2TXLiveVideoFrame dstFrame) {
                return super.onProcessVideoFrame(srcFrame, dstFrame);
            }
        });
        isLive = true;

        return mLivePusher;

    }


    public V2TXLivePusher startLive(Context context, String data) {
        //1是0不是
        if (mVideoView == null) {
            return null;
        }
        mLivePusher = new V2TXLivePusherImpl(context, V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTC);

        mLivePusher.setRenderView(mVideoView);
        mLivePusher.startCamera(true);
        mLivePusher.startMicrophone();
        mEncoderParam = new V2TXLiveDef.V2TXLiveVideoEncoderParam(V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution1280x720);
        mEncoderParam.videoFps = 30;
        mLivePusher.setVideoQuality(mEncoderParam);
        if (MyUserInstance.getInstance().getUserConfig().getConfig().getBeauty_channel() == 1) {
            mLivePusher.enableCustomVideoProcess(true, V2TXLiveDef.V2TXLivePixelFormat.V2TXLivePixelFormatTexture2D, V2TXLiveDef.V2TXLiveBufferType.V2TXLiveBufferTypeTexture);

        }
        int a = mLivePusher.startPush(data);
        mLivePusher.setObserver(new V2TXLivePusherObserver() {
            @Override
            public void onError(int code, String msg, Bundle extraInfo) {
                super.onError(code, msg, extraInfo);
            }

            @Override
            public int onProcessVideoFrame(V2TXLiveDef.V2TXLiveVideoFrame srcFrame, V2TXLiveDef.V2TXLiveVideoFrame dstFrame) {
                return super.onProcessVideoFrame(srcFrame, dstFrame);
            }
        });
        isLive = true;
        return mLivePusher;

    }
    public void pause() {

        if (mLivePusher != null) {
            mLivePusher.pauseVideo();
        }

    }

    public void stopMicrophone() {
        if (mLivePusher != null) {
            mLivePusher.stopMicrophone();
        }
    }


    public void startMicrophone() {
        if (mLivePusher != null) {
            mLivePusher.startMicrophone();
        }
    }




    public V2TXLivePusher getLivePusher() {
        return mLivePusher;
    }

    public void resume() {
        if (isLive) {
            if (mLivePusher != null) {
                mLivePusher.resumeVideo();
            }
        }
    }

    public void  swtichCamera(){
        if (mLivePusher != null) {
            if(mLivePusher.getDeviceManager().isFrontCamera()){
                mLivePusher.getDeviceManager().switchCamera(false);
            }else{
                mLivePusher.getDeviceManager().switchCamera(true);
            }

        }
    }


    public void release() {
        if (mLivePusher != null) {
            mLivePusher.release();

        }
        mVideoView.onDestroy();
    }

    public void setVideoView(TXCloudVideoView videoView) {
        mVideoView = videoView;

    }


    public void initMix(int type, HotLive pkLive, boolean isHome,String userId) {
        //1 PK,2连麦,0清除
        switch (type) {
            case 1:
                if (mLivePusher != null) {
                    initMixPK(pkLive, isHome);
                }
                break;
            case 2:
                if (mLivePusher != null) {
                    initMixLink(pkLive.getStream(), userId);
                }
                break;
            case 0:
                if (mLivePusher != null) {
                    mLivePusher.setMixTranscodingConfig(null);
                }
                break;


        }

    }

    private void initMixPK(HotLive pkLive, boolean isHome) {


        if (mLivePusher != null) {
            mLivePusher.setMixTranscodingConfig(LiveMixUtils.getPkConfig(pkLive.getStream(), pkLive.getAnchorid(),isHome));
        }
    }
    private void initMixLink(String linkStreamId,String userId) {


        if (mLivePusher != null) {
            mLivePusher.setMixTranscodingConfig(LiveMixUtils.getLinkConfig(linkStreamId, userId));
        }
    }
}
