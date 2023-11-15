package com.feicui365.live.live.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePlayer;
import com.tencent.live2.V2TXLivePusher;
import com.tencent.live2.impl.V2TXLivePlayerImpl;
import com.tencent.live2.impl.V2TXLivePusherImpl;
import com.tencent.rtmp.ui.TXCloudVideoView;

import com.feicui365.live.R;

/**
 *
 */
public class LinkUtils {
    private View mLinkView;
    private TXCloudVideoView mTxcvLink;
    private ImageView mIvClose;
    private Context context;
    private String mUrl;
    private V2TXLivePusher mLivePusher;
    private V2TXLivePlayer mLivePlayer;
    private boolean isInit = false;
    private String linkUserId;
    private ViewGroup mRootView;
    OnStopClickListener onClickListener;

    public interface OnStopClickListener {
        void onStop(String id);
    }

    public void setOnClickListener(OnStopClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public LinkUtils(Context context, ViewGroup view, String linkUserId) {
        this.context = context;
        this.mRootView = view;
        this.linkUserId = linkUserId;
    }

    public void initLinkView(String url) {

        mLinkView = View.inflate(context, R.layout.item_link_layout, null);
        mTxcvLink = mLinkView.findViewById(R.id.txcv_link);
        mIvClose = mLinkView.findViewById(R.id.iv_close_link);
        mUrl = url;
        isInit = true;
        mIvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onClickListener != null) {
                    onClickListener.onStop(linkUserId);
                }
                initClose();
            }
        });


    }

    public void initClose() {
        isInit = false;

        if (mLivePusher != null) {
            mLivePusher.release();
            mLivePusher = null;
        }
        if (mLivePlayer != null) {
            mLivePlayer.stopPlay();
            mLivePlayer = null;
        }


        if (mTxcvLink != null) {
            mTxcvLink.onDestroy();
            mTxcvLink = null;
        }
        if (mIvClose != null) {
            mIvClose.setOnClickListener(null);
            mIvClose = null;
        }

        context = null;
        mUrl = null;
        linkUserId = null;
        if (mLinkView != null) {
            mRootView.removeView(mLinkView);
            mLinkView = null;
            mRootView = null;
        }

    }

    public void initPush() {
        if (!isInit) {
            return;
        }
        mLivePusher = new V2TXLivePusherImpl(context, V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTC);
        mLivePusher.setRenderView(mTxcvLink);
        mLivePusher.startCamera(true);
        mLivePusher.startMicrophone();
        V2TXLiveDef.V2TXLiveVideoEncoderParam mEncoderParam = new V2TXLiveDef.V2TXLiveVideoEncoderParam(V2TXLiveDef.V2TXLiveVideoResolution.V2TXLiveVideoResolution1280x720);
        mEncoderParam.videoFps = 30;
        mLivePusher.setVideoQuality(mEncoderParam);
      int result=  mLivePusher.startPush(mUrl);
        RelativeLayout.LayoutParams mParamsAway = new RelativeLayout.LayoutParams(ArmsUtils.dip2px(context, 115)
                , ArmsUtils.dip2px(context, 170));
        mParamsAway.setMargins(PkScreenUtils.widthPx - ArmsUtils.dip2px(context, 130), ArmsUtils.dip2px(context, 315), 0, 0);
        mLinkView.setLayoutParams(mParamsAway);
        mRootView.addView(mLinkView);
    }

    public void initPlay() {
        if (!isInit) {
            return;
        }
        mLivePlayer = new V2TXLivePlayerImpl(context);
        mLivePlayer.setRenderView(mTxcvLink);
        int result = mLivePlayer.startLivePlay(mUrl);
        RelativeLayout.LayoutParams mParamsAway = new RelativeLayout.LayoutParams(ArmsUtils.dip2px(context, 115)
                , ArmsUtils.dip2px(context, 170));
        mParamsAway.setMargins(PkScreenUtils.widthPx - ArmsUtils.dip2px(context, 130), ArmsUtils.dip2px(context, 315), 0, 0);
        mLinkView.setLayoutParams(mParamsAway);
        mRootView.addView(mLinkView);
    }


}
