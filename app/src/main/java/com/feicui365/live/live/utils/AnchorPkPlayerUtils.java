package com.feicui365.live.live.utils;

import android.content.Context;


import com.tencent.live2.V2TXLivePlayer;
import com.tencent.live2.impl.V2TXLivePlayerImpl;
import com.tencent.rtmp.TXVodPlayConfig;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import com.feicui365.live.model.entity.HotLive;

/**
 * 主播播放用工具
 */
public class AnchorPkPlayerUtils {

    private V2TXLivePlayer mLivePlayer;
    private TXVodPlayer mVodPlayer;
    private TXVodPlayConfig mVodConfig;
    private boolean isLive;
    private TXCloudVideoView mVideoView;

    public AnchorPkPlayerUtils() {


    }


    /**
     * 播放器需要做什么
     * 1,暂停
     * 2,恢复
     * 3,切换方向
     */

    public void startLive(Context context, HotLive data) {
        //1是0不是
        if (mVideoView == null) {
            return;
        }

        if (isLive(data.getAcc_pull_url())){
            isLive = false;
            mVodPlayer = new TXVodPlayer(context);
            mVodConfig = new TXVodPlayConfig();

            mVodPlayer.setConfig(mVodConfig);
            mVodPlayer.setLoop(true);
            mVodPlayer.setPlayerView(mVideoView);
            mVodPlayer.startVodPlay(data.getAcc_pull_url());

        } else{
            isLive = true;
            mLivePlayer = new V2TXLivePlayerImpl(context);
            mLivePlayer.setRenderView(mVideoView);

            int result = mLivePlayer.startLivePlay(data.getAcc_pull_url());
            int b = 0;

        }


    }

    private boolean isLive(String result) {
        if (result.contains(".HLS") | result.contains(".DASH") | result.contains(".MP4") | result.contains(".MP3")
                | result.contains(".hls") | result.contains(".dash") | result.contains(".mp4") | result.contains(".mp3")) {

            return true;
        } else {
            return false;
        }
    }

    public void startLive(Context context, String data) {
        //1是0不是
        if (mVideoView == null) {
            return;
        }


        isLive = true;
        mLivePlayer = new V2TXLivePlayerImpl(context);
        mLivePlayer.setRenderView(mVideoView);

        int result = mLivePlayer.startLivePlay(data);
        int b = 0;


    }

    public void pauseAudio() {
        if (isLive) {
            if (mLivePlayer != null) {
                mLivePlayer.pauseAudio();
            }
        } else {
            if (mVodPlayer != null) {
                mVodPlayer.pause();
            }
        }
    }

    public void resumeAudio() {
        if (isLive) {
            if (mLivePlayer != null) {
                mLivePlayer.resumeAudio();
            }
        } else {
            if (mVodPlayer != null) {
                mVodPlayer.resume();
            }
        }
    }


    public void pause() {
        if (isLive) {
            if (mLivePlayer != null) {
                mLivePlayer.pauseVideo();
            }
        } else {
            if (mVodPlayer != null) {
                mVodPlayer.pause();
            }
        }
    }


    public void resume() {
        if (isLive) {
            if (mLivePlayer != null) {
                mLivePlayer.resumeVideo();
            }
        } else {
            if (mVodPlayer != null) {
                mVodPlayer.resume();
            }
        }
    }


    public void release() {
        if (isLive) {
            if (mLivePlayer != null) {
                mLivePlayer.stopPlay();

            }
        } else {
            if (mVodPlayer != null) {
                mVodPlayer.stopPlay(true);
            }
        }
        mVideoView.onDestroy();
    }

    public void setVideoView(TXCloudVideoView videoView) {
        mVideoView = videoView;

    }
}
