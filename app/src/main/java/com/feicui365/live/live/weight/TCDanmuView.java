package com.feicui365.live.live.weight;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;

import java.util.Random;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 *
 */
public class TCDanmuView extends DanmakuView {
    private Context mContext;
    private DanmakuContext mDanmakuContext;
    private boolean mShowDanmu;
    private HandlerThread mHandlerThread;
//    private DanmuHandler mDanmuHandler;

    public TCDanmuView(Context context) {
        super(context);
        init(context);
    }

    public TCDanmuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TCDanmuView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        enableDanmakuDrawingCache(true);
        setCallback(new DrawHandler.Callback() {
            @Override
            public void prepared() {
                mShowDanmu = true;
                start();
                generateDanmaku();
            }

            @Override
            public void updateTimer(DanmakuTimer timer) {

            }

            @Override
            public void danmakuShown(BaseDanmaku danmaku) {

            }

            @Override
            public void drawingFinished() {

            }
        });
        mDanmakuContext = DanmakuContext.create();
        prepare(mParser, mDanmakuContext);
    }

    @Override
    public void release() {
        super.release();
        mShowDanmu = false;
//        if (mDanmuHandler != null) {
//            mDanmuHandler.removeCallbacksAndMessages(null);
//            mDanmuHandler = null;
//        }
        if (mHandlerThread != null) {
            mHandlerThread.quit();
            mHandlerThread = null;
        }
    }

    private BaseDanmakuParser mParser = new BaseDanmakuParser() {
        @Override
        protected IDanmakus parse() {
            return new Danmakus();
        }
    };

    /**
     * 随机生成一些弹幕内容以供测试
     */
    private void generateDanmaku() {
        mHandlerThread = new HandlerThread("Danmu");
        mHandlerThread.start();
//        mDanmuHandler = new DanmuHandler(mHandlerThread.getLooper());
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    public void addDanmaku(String content, boolean withBorder) {
        BaseDanmaku danmaku = mDanmakuContext.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL);
        if (danmaku != null) {
            danmaku.text = content;
            danmaku.padding = 5;
            danmaku.textSize = 60;
            danmaku.textColor = Color.WHITE;
            danmaku.setTime(getCurrentTime());
            if (withBorder) {
                danmaku.borderColor = Color.GREEN;
            }
            addDanmaku(danmaku);
        }
    }

    public void toggle(boolean on) {

        if (on) {
//            mDanmuHandler.sendEmptyMessageAtTime(DanmuHandler.MSG_SEND_DANMU, 100);
        } else {
//            mDanmuHandler.removeMessages(DanmuHandler.MSG_SEND_DANMU);
        }
    }


    public class DanmuHandler extends Handler {
        public static final int MSG_SEND_DANMU = 1001;

        public DanmuHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SEND_DANMU:
                    sendDanmu();
                    int time = new Random().nextInt(1000);
//                    mDanmuHandler.sendEmptyMessageDelayed(MSG_SEND_DANMU, time);
                    break;
            }
        }

        private void sendDanmu() {
            int time = new Random().nextInt(300);
            String content = "弹幕" + time + time;
            addDanmaku(content, false);
        }
    }
}
