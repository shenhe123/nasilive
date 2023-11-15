/*
package com.feicui365.live.ui.act;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.icu.text.DecimalFormat;
import android.net.http.HttpResponseCache;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lxj.xpopup.XPopup;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.BuildConfig;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.bean.Message;
import com.feicui365.live.bean.MessageData;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.dialog.JoinInfoDialog;
import com.feicui365.live.dialog.MessageDialog;
import com.feicui365.live.dialog.RoomDialog;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.dialog.UserInfoDialog;
import com.feicui365.live.liveroom.MLVBHttpUtils;
import com.feicui365.live.liveroom.roomutil.http.HttpRequests;
import com.feicui365.live.liveroom.roomutil.http.HttpResponse;
import com.feicui365.live.model.entity.BaseLiveInfo;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.ChatReceiveGiftBean;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.model.entity.Filters;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveCategory;
import com.feicui365.live.model.entity.Notify;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.shop.entity.Good;
import com.feicui365.live.ui.adapter.FilterAdapter;
import com.feicui365.live.ui.adapter.LivePushChatVerticalAdapter;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyCredentialProvider;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.PhotoUtil;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.ChatPopup;
import com.feicui365.live.widget.CommentAnchorShopList;
import com.feicui365.live.widget.CommentContributionList;
import com.feicui365.live.widget.CommentGuardianList;
import com.feicui365.live.widget.CommentLiveBottomList;
import com.feicui365.live.widget.DrawableTextView;
import com.feicui365.live.widget.GiftAnimViewHolder;
import com.feicui365.live.widget.PkProgressBar;
import com.feicui365.live.widget.RoomMangePopup;
import com.nasinet.nasinet.utils.DipPxUtils;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMCustomElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMGroupMemberInfo;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMMessageListener;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.liteav.demo.play.view.HorizontalListView;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import butterknife.BindView;
import butterknife.OnClick;
import cn.tillusory.sdk.TiSDK;
import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.InitStatus;
import cn.tillusory.sdk.bean.TiRotation;
import cn.tillusory.tiui.TiPanelLayout;
import de.hdodenhof.circleimageview.CircleImageView;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class LivePushActivity2 extends BaseMvpActivity<LivePushPresenter> implements LivePushContrat.View {

    //直播开始界面
    @BindView(R.id.spv_main)
    TXCloudVideoView spv_main;
    @BindView(R.id.spv_sub_left)
    SuperPlayerView spv_sub_left;
    @BindView(R.id.spv_sub_right)
    SuperPlayerView spv_sub_right;
    @BindView(R.id.cover_iv)
    ImageView cover_iv;
    @BindView(R.id.channel_tv)
    TextView channel_tv;
    @BindView(R.id.title_et)
    EditText title_et;
    @BindView(R.id.camera_iv)
    ImageView camera_iv;
    @BindView(R.id.camera_iv_2)
    ImageView camera_iv_2;
    @BindView(R.id.beauty_iv)
    ImageView beauty_iv;
    @BindView(R.id.beauty_iv_2)
    ImageView beauty_iv_2;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.rl_time)
    RelativeLayout rl_time;
    @BindView(R.id.ll_room_type)
    LinearLayout ll_room_type;
    @BindView(R.id.rl_normal)
    RelativeLayout rl_normal;
    @BindView(R.id.rl_price)
    RelativeLayout rl_price;
    @BindView(R.id.rl_secret)
    RelativeLayout rl_secret;
    @BindView(R.id.dtv_room_type)
    DrawableTextView dtv_room_type;
    @BindView(R.id.dtv_normal)
    DrawableTextView dtv_normal;
    @BindView(R.id.dtv_price)
    DrawableTextView dtv_price;
    @BindView(R.id.dtv_secret)
    DrawableTextView dtv_secret;
    @BindView(R.id.live_title_ll)
    LinearLayout live_title_ll;
    @BindView(R.id.start_live_tv)
    TextView start_live_tv;
    @BindView(R.id.filter_rl)
    RelativeLayout filter_rl;
    @BindView(R.id.mopi_seek_bar)
    SeekBar mopi_seek_bar;
    @BindView(R.id.meibai_seek_bar)
    SeekBar meibai_seek_bar;
    @BindView(R.id.hongrun_seek_bar)
    SeekBar hongrun_seek_bar;
    @BindView(R.id.filter_seek_bar)
    SeekBar filter_seek_bar;
    @BindView(R.id.mopi_tv)
    TextView mopi_tv;
    @BindView(R.id.meibai_tv)
    TextView meibai_tv;
    @BindView(R.id.hongrun_tv)
    TextView hongrun_tv;
    @BindView(R.id.filter_list)
    HorizontalListView filter_list;
    @BindView(R.id.meiyan_tv)
    TextView meiyan_tv;
    @BindView(R.id.meiyan_view)
    View meiyan_view;
    @BindView(R.id.filter_tv)
    TextView filter_tv;
    @BindView(R.id.filter_view)
    View filter_view;
    @BindView(R.id.filter_ll)
    LinearLayout filter_ll;
    @BindView(R.id.meiyan_ll)
    LinearLayout meiyan_ll;
    @BindView(R.id.chat_ll)
    LinearLayout chat_ll;

    @BindView(R.id.chat_list_view)
    RecyclerView chat_list_view;


    @BindView(R.id.anchor_ll)
    LinearLayout anchor_ll;
    @BindView(R.id.head_iv)
    ImageView head_iv;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.hot_tv)
    TextView hot_tv;


    @BindView(R.id.tv_gift_info)
    TextView tv_gift_info;
    @BindView(R.id.group_1)
    RelativeLayout group_1;
    @BindView(R.id.rl_bottom)
    RelativeLayout rl_bottom;
    @BindView(R.id.tv_peoples)
    TextView tv_peoples;

    @BindView(R.id.root)
    RelativeLayout root;
    @BindView(R.id.iv_1)
    ImageView iv_1;
    @BindView(R.id.close_iv)
    ImageView close_iv;

    @BindView(R.id.close_iv_2)
    ImageView close_iv_2;
    @BindView(R.id.tv_income_dimen)
    TextView tv_income_dimen;


    @BindView(R.id.ll_room)
    LinearLayout ll_room;
    @BindView(R.id.txcv_join)
    SuperPlayerView txcv_join;
    @BindView(R.id.cv_join)
    CardView cv_join;


    //PK相关
    @BindView(R.id.rl_play)
    public RelativeLayout rl_play;
    @BindView(R.id.tv_time_pk)
    public TextView tv_time_pk;
    @BindView(R.id.ppb_live)
    public PkProgressBar ppb_live;
    @BindView(R.id.ll_play_view)
    public LinearLayout ll_play_view;
    @BindView(R.id.siv_center)
    public SVGAImageView siv_center;
    @BindView(R.id.siv_left)
    public SVGAImageView siv_left;
    @BindView(R.id.siv_right)
    public SVGAImageView siv_right;
    @BindView(R.id.siv_start)
    public SVGAImageView siv_start;
    public List<Good> shopItems;
    private AlertDialog.Builder builder;

    @BindView(R.id.ll_user_info_right)
    LinearLayout ll_user_info_right;
    @BindView(R.id.ll_user_info_left)
    LinearLayout ll_user_info_left;

    @BindView(R.id.civ_avatar_right)
    CircleImageView civ_avatar_right;
    @BindView(R.id.tv_name_right)
    TextView tv_name_right;
    @BindView(R.id.iv_attend_right)
    ImageView iv_attend_right;
    @BindView(R.id.civ_avatar_left)
    CircleImageView civ_avatar_left;
    @BindView(R.id.tv_name_left)
    TextView tv_name_left;
    @BindView(R.id.iv_attend_left)
    ImageView iv_attend_left;

    @BindView(R.id.rl_pk_status)
    CardView rl_pk_status;
    @BindView(R.id.gif_ok_status)
    GifImageView gif_ok_status;
    @BindView(R.id.tv_pk_status)
    TextView tv_pk_status;

    @BindView(R.id.garudian_tv)
    TextView garudian_tv;


    private TXLivePusher mLivePusher;
    private GifDrawable gifDrawable_pk_status;
    private int TAKE_PHOTO = 0x0001;
    private int TAKE_ALBUM = 0x0002;
    private String photoPath;
    private CosXmlService cosXmlService;
    private ArrayList<LiveCategory> live_category;
    private String cateid;
    private String thumb;
    private HotLive startLive;
    private int mopiLevel = 8;
    private int meibaiLevel = 5;
    private int hongrunLevel = 3;
    private ArrayList<Filters> filters = new ArrayList<>();
    private FilterAdapter filterAdapter;
    private int index = 0;
    private TIMConversation conversation;
    private ArrayList<Message> chatList;
    private LivePushChatVerticalAdapter chatListAdapter;

    private ConcurrentLinkedQueue<Message> mGifQueue2 = new ConcurrentLinkedQueue<>();
    private String room_type = "";
    private String room_pricre = "0";
    private String room_password = "";
    private long baseTimer;
    private boolean is_show = false;
    private GiftAnimViewHolder mGiftAnimViewHolder;
    private TiPanelLayout tiPanelLayout;
    private TIMMessageListener timMessageListener;
    private String MLVB_token = "";
    private String is_link = "0";

    private XPopup.Builder bottom_builder;
    private CommentLiveBottomList bottomList;
    //请求连麦者的ID
    private String join_id = "";
    //请求连麦者的推流地址
    private String join_push_url = "";

    private CountDownTimer pk_time;
    private int local_pk_second = 360000;
    private int local_punish_second = 120000;
    private int get_type = 1;
    private int mlvb_count = 0;

    private Handler myhandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:

                    if (0 == baseTimer) {
                        baseTimer = SystemClock.elapsedRealtime();
                    }

                    int time = (int) ((SystemClock.elapsedRealtime() - baseTimer) / 1000);
                    String hh = new DecimalFormat("00").format(time / 3600);
                    String mm = new DecimalFormat("00").format(time % 3600 / 60);
                    String ss = new DecimalFormat("00").format(time % 60);
                    if (null != tv_time) {
                        tv_time.setText(hh + ":" + mm + ":" + ss);
                    }
                    android.os.Message message = new android.os.Message();
                    message.what = 1;
                    sendMessageDelayed(message, 1000);
                    break;
                case 2:
                    if (null != startLive) {
                        mPresenter.getLiveBasicInfo(startLive.getLiveid());
                        android.os.Message message2 = new android.os.Message();
                        message2.what = 2;
                        sendMessageDelayed(message2, 60000);
                    }
                    break;
                case 3:
                    tiPanelLayout = new TiPanelLayout(context).init(TiSDKManager.getInstance());
                    addContentView(tiPanelLayout,
                            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    //todo --- tillusory start ---
                    mLivePusher.setVideoProcessListener(new TXLivePusher.VideoCustomProcessListener() {
                        @Override
                        public int onTextureCustomProcess(int i, int i1, int i2) {
                            return TiSDKManager.getInstance().renderTexture2D(i, i1, i2, TiRotation.CLOCKWISE_ROTATION_0, false);
                        }

                        @Override
                        public void onDetectFacePoints(float[] floats) {

                        }

                        @Override
                        public void onTextureDestoryed() {
                            TiSDKManager.getInstance().destroy();
                        }
                    });
                    break;

            }

        }
    };

    @Override
    public void getLiveBasicInfo(BaseResponse<BaseLiveInfo> baseResponse) {
        if (baseResponse.getData().getLive().getProfit() != 0) {
            tv_income_dimen.setText(baseResponse.getData().getLive().getProfit());
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_live_push;
    }

    @Override
    protected void initData() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        SVGAParser.Companion.shareParser().init(this);

        File httpCacheDir = new File(getCacheDir(), "http");
        long httpCacheSize = 1024 * 1024 * 128;
        try {
            HttpResponseCache.install(httpCacheDir, httpCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //初始化推流


        TXLiveBase.getInstance().setLicence(this, BuildConfig.licenceURL, BuildConfig.licenceKey);
        TXLivePushConfig mLivePushConfig = new TXLivePushConfig();
        mLivePusher = new TXLivePusher(this);

        mLivePusher.setConfig(mLivePushConfig);
        mLivePusher.setSpecialRatio(0.5f);

        //启动本地摄像头预览
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLivePusher.startCameraPreview(spv_main);
            }
        }, 500);


        initBeautifull();
        updateUserInfo();

        MLVBHttpUtils.sharedInstance().init();
        initMlvb();

    }

    private void initMlvb() {
        MLVBHttpUtils.sharedInstance().login(new HttpRequests.OnResponseCallback<HttpResponse.LoginResponse>() {
            @Override
            public void onResponse(int retcode, @Nullable String retmsg, @Nullable HttpResponse.LoginResponse data) {
                if (retcode == 0) {
                    if (data.token != null) {
                        MLVB_token = data.token;
                    }
                    hideLoading();
                } else {
                    mlvb_count++;
                    if (mlvb_count == 5) {
                        initDialog();
                    } else if (mlvb_count < 5) {
                        initMlvb();
                    }

                }
            }
        });
    }


    private void initBeautifull() {

        if (MyUserInstance.getInstance().getUserConfig().getConfig().getBeauty_channel().equals("1")) {
            TiSDK.init(MyUserInstance.getInstance().getUserConfig().getConfig().getTisdk_key(), this, new TiSDK.TiInitCallback() {
                @Override
                public void finish(InitStatus initStatus) {
                    if (initStatus.getCode() == 100) {
                        android.os.Message message = new android.os.Message();
                        message.what = 3;
                        myhandler.sendMessage(message);
                    }
                }
            });
            initRecyclerView();
            //todo --- tillusory end ---
        } else {
            mopi_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mopi_tv.setText(String.valueOf(progress));
                    mopiLevel = progress;
                    mLivePusher.setBeautyFilter(0, mopiLevel, meibaiLevel, hongrunLevel);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            meibai_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    meibai_tv.setText(String.valueOf(progress));
                    meibaiLevel = progress;
                    mLivePusher.setBeautyFilter(0, mopiLevel, meibaiLevel, hongrunLevel);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            hongrun_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    hongrun_tv.setText(String.valueOf(progress));
                    hongrunLevel = progress;
                    mLivePusher.setBeautyFilter(0, mopiLevel, meibaiLevel, hongrunLevel);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            filter_seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    float specialRatio = progress / 10f;
                    mLivePusher.setSpecialRatio(specialRatio);
                    filters.get(index).setSpecialRatio(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });

            initHorizontalList();
            initRecyclerView();

        }
    }


    public void updateUserInfo() {

        HttpUtils.getInstance().getUserInfo(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                try {
                    JSONObject jsonObject = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
                    if (HttpUtils.getInstance().swtichStatus(jsonObject)) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        if (null != data) {
                            UserRegist userRegist = (UserRegist) JSONObject.parseObject(data.toString(), UserRegist.class);
                            MyUserInstance.getInstance().setUserInfo(userRegist);
                        }
                    }
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }

            }
        });
    }

    @Override
    protected void initView() {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        rl_title.setVisibility(View.GONE);
        room_type = "0";
        room_pricre = "0";
        room_password = "";
        dtv_normal.setTopDrawable(getResources().getDrawable(R.mipmap.putong_pre));
        dtv_normal.setTextColor(getResources().getColor(R.color.room));

        dtv_price.setTopDrawable(getResources().getDrawable(R.mipmap.fufei));
        dtv_price.setTextColor(getResources().getColor(R.color.white));

        dtv_secret.setTopDrawable(getResources().getDrawable(R.mipmap.simi));
        dtv_secret.setTextColor(getResources().getColor(R.color.white));

        dtv_room_type.setText(WordUtil.getString(R.string.Normal));
        setVipInFinishListener(new VipInFinishListener() {
            @Override
            public void vipinfinish() {
                if (mGifQueue2.size() > 0) {
                    getVipIn(mGifQueue2.poll(), 3);
                }
            }
        });

        RelativeLayout.LayoutParams lp_pk_start = (RelativeLayout.LayoutParams) siv_start.getLayoutParams();
        lp_pk_start.height = MyUserInstance.getInstance().device_width / 4 * 3;
        lp_pk_start.width = MyUserInstance.getInstance().device_width / 4 * 3;
        siv_start.setLayoutParams(lp_pk_start);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        setAndroidNativeLightStatusBar(false);
    }

    private void initRecyclerView() {
        chatList = new ArrayList<>();
        chatList.add(new Message());
        chatListAdapter = new LivePushChatVerticalAdapter(chatList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        linearLayoutManager.scrollToPositionWithOffset(chatListAdapter.getItemCount() - 1, Integer.MIN_VALUE);

        chat_list_view.setLayoutManager(linearLayoutManager);
        chat_list_view.setAdapter(chatListAdapter);
        chatListAdapter.setChatClickListener(new LivePushChatVerticalAdapter.ChatClickListener() {
            @Override
            public void chatclick(String id) {
                mPresenter.getUserBasicInfo(id);
            }
        });
    }

    public void setCaht(Message chat) {
        int preEndIndex = 0;
        if (chatList.size() > 0) {
            preEndIndex = chatList.size();
        }
        chatList.add(chat);
        chatListAdapter.notifyItemRangeInserted(preEndIndex, 1);
        //报错炸出来点
        if (null != chat_list_view) {
            if (!chat_list_view.canScrollVertically(1)) {
                //滑动到底部了
                chat_list_view.scrollToPosition(chatList.size() - 1);
            }
        }
    }

    private void initHorizontalList() {
        //添加滤镜
        filters.addAll(MyUserInstance.getInstance().initFilterList(context));
        filterAdapter = new FilterAdapter(this);
        filter_list.setAdapter(filterAdapter);
        filterAdapter.setData(filters);
        filter_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Filters filter : filters) {
                    filter.setTag(2);
                }
                filters.get(position).setTag(1);
                index = position;
                mLivePusher.setFilter(filters.get(index).getFilterBitmap());
                mLivePusher.setSpecialRatio(filters.get(index).getSpecialRatio() / 10f);
                filter_seek_bar.setProgress((int) filters.get(index).getSpecialRatio());
                filterAdapter.notifyDataSetChanged();
            }
        });
    }

    boolean i = true;

    @OnClick({R.id.garudian_tv, R.id.tv_pk_status, R.id.iv_stop_join, R.id.close_iv, R.id.close_iv_2, R.id.camera_iv, R.id.beauty_iv, R.id.cover_iv, R.id.channel_tv, R.id.start_live_tv, R.id.filter_rl
            , R.id.meiyan_option, R.id.filter_option
            , R.id.contribution_tv, R.id.beauty_iv_2, R.id.camera_iv_2
            , R.id.rl_normal, R.id.rl_price, R.id.rl_secret, R.id.iv_share_2,*/
/* R.id.iv_message, R.id.iv_shop, *//*
R.id.ll_room,*/
/* R.id.iv_manager,*//*
 R.id.iv_more})
    public void onClick(View view) {
        if(isFastClick()){
            return;
        }
        switch (view.getId()) {

            case R.id.iv_stop_join:
                mPresenter.stopMlvbLink(MyUserInstance.getInstance().getUserinfo().getId(), join_id);
                stopPushJoin();

                break;

            case R.id.ll_room:
                if (ll_room_type.getVisibility() == View.VISIBLE) {
                    ll_room_type.setVisibility(View.GONE);
                } else if (ll_room_type.getVisibility() == View.GONE) {
                    ll_room_type.setVisibility(View.VISIBLE);
                }


                break;


            case R.id.close_iv:
                finishEndDialog();
                break;

            case R.id.close_iv_2:
                finishEndDialog();
                break;

            case R.id.camera_iv:
                mLivePusher.switchCamera();
                break;
            case R.id.beauty_iv:
                if (MyUserInstance.getInstance().getUserConfig().getConfig().getBeauty_channel().equals("1")) {
                    if (tiPanelLayout != null) {
                        tiPanelLayout.showView();
                    }
                } else {
                    filter_rl.setVisibility(View.VISIBLE);
                    ll_room.setVisibility(View.GONE);
                    start_live_tv.setVisibility(View.GONE);
                }


                break;
            case R.id.camera_iv_2:
                mLivePusher.switchCamera();
                break;
            case R.id.beauty_iv_2:

                if (MyUserInstance.getInstance().getUserConfig().getConfig().getBeauty_channel().equals("1")) {
                    if (tiPanelLayout != null) {
                        tiPanelLayout.showView();
                        chat_list_view.setVisibility(View.GONE);
                        rl_bottom.setVisibility(View.GONE);
                    }

                } else {
                    filter_rl.setVisibility(View.VISIBLE);
                    chat_list_view.setVisibility(View.GONE);
                    rl_bottom.setVisibility(View.GONE);
                }

                break;
            case R.id.cover_iv:

                takePhoto();
                break;
            case R.id.channel_tv:
                showPicker();
                break;
            case R.id.start_live_tv:

                if (null == startLive) {
                    if (TextUtils.isEmpty(title_et.getText())) {
                        ToastUtils.showT(WordUtil.getString(R.string.Input_Title));
                        return;
                    }
                    if (TextUtils.isEmpty(photoPath)) {
                        ToastUtils.showT(WordUtil.getString(R.string.Input_Tumb));
                        return;
                    }
                    if (TextUtils.isEmpty(cateid)) {
                        ToastUtils.showT(WordUtil.getString(R.string.Chose_Room_Type));
                        return;
                    }
                    showLoading();
                    start_live_tv.setClickable(false);
                    mPresenter.getTempKeysForCos();
                } else {
                    startSuccess(startLive);
                }
                break;
            case R.id.filter_rl:
                filter_rl.setVisibility(View.GONE);
                if (is_show) {
                    chat_list_view.setVisibility(View.VISIBLE);
                    rl_bottom.setVisibility(View.VISIBLE);
                } else {
                    ll_room.setVisibility(View.VISIBLE);
                    start_live_tv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.meiyan_option:
                meiyan_tv.setTextColor(getResources().getColor(R.color.color_F06E1E));
                meiyan_view.setVisibility(View.VISIBLE);
                meiyan_ll.setVisibility(View.VISIBLE);

                filter_tv.setTextColor(getResources().getColor(R.color.color_FFFFFF));
                filter_view.setVisibility(View.INVISIBLE);
                filter_ll.setVisibility(View.GONE);
                break;
            case R.id.filter_option:
                meiyan_tv.setTextColor(getResources().getColor(R.color.color_FFFFFF));
                meiyan_view.setVisibility(View.INVISIBLE);
                meiyan_ll.setVisibility(View.GONE);

                filter_tv.setTextColor(getResources().getColor(R.color.color_F06E1E));
                filter_view.setVisibility(View.VISIBLE);
                filter_ll.setVisibility(View.VISIBLE);
                break;


            case R.id.contribution_tv:
                mPresenter.getContributeRank(startLive.getLiveid());
                break;

            case R.id.rl_normal:
                room_type = "0";
                room_pricre = "0";
                room_password = "";
                dtv_normal.setTopDrawable(getResources().getDrawable(R.mipmap.putong_pre));
                dtv_normal.setTextColor(getResources().getColor(R.color.room));
                dtv_price.setTopDrawable(getResources().getDrawable(R.mipmap.fufei));
                dtv_price.setTextColor(getResources().getColor(R.color.white));
                dtv_secret.setTopDrawable(getResources().getDrawable(R.mipmap.simi));
                dtv_secret.setTextColor(getResources().getColor(R.color.white));
                dtv_room_type.setText(WordUtil.getString(R.string.Normal));
                ll_room_type.setVisibility(View.GONE);
                break;
            case R.id.rl_price:

                room_type = "2";
                room_password = "";
                dtv_normal.setTopDrawable(getResources().getDrawable(R.mipmap.putong));
                dtv_normal.setTextColor(getResources().getColor(R.color.white));
                dtv_price.setTopDrawable(getResources().getDrawable(R.mipmap.fufei_pre));
                dtv_price.setTextColor(getResources().getColor(R.color.room));
                dtv_secret.setTopDrawable(getResources().getDrawable(R.mipmap.simi));
                dtv_secret.setTextColor(getResources().getColor(R.color.white));
                dtv_room_type.setText(WordUtil.getString(R.string.Paid));
                showDialog(WordUtil.getString(R.string.Paid), WordUtil.getString(R.string.Input_Paid));
                break;
            case R.id.rl_secret:
                room_type = "1";
                room_pricre = "0";
                dtv_normal.setTopDrawable(getResources().getDrawable(R.mipmap.putong));
                dtv_normal.setTextColor(getResources().getColor(R.color.white));
                dtv_price.setTopDrawable(getResources().getDrawable(R.mipmap.fufei));
                dtv_price.setTextColor(getResources().getColor(R.color.white));
                dtv_secret.setTopDrawable(getResources().getDrawable(R.mipmap.simi_pre));
                dtv_secret.setTextColor(getResources().getColor(R.color.room));
                dtv_room_type.setText(WordUtil.getString(R.string.Password));
                showDialog(WordUtil.getString(R.string.Password), WordUtil.getString(R.string.Enter_new_password));

                break;
            case R.id.iv_share_2:

                Glide.with(LivePushActivity2.this).load(startLive.getThumb()).into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        ShareDialog.Builder builder = new ShareDialog.Builder(LivePushActivity2.this);
                        builder.setShare_url(MyUserInstance.getInstance().getUserConfig().getConfig().getShare_live_url() + startLive.getAnchorid());
                        builder.create().show();
                        builder.showBottom2();
                        //builder.setImage_url(my_trend.getUser());
                        builder.setContent(startLive.getTitle());
                        builder.setTitle(WordUtil.getString(R.string.live_recommend));
                        builder.hideCollect();
                        builder.setTumb(MyUserInstance.getInstance().drawableToBitmap(resource));
                        builder.setType("1");
                        builder.setId(startLive.getAnchorid());


                    }
                });

                break;


            case R.id.iv_more:
                // showXPop(new CommentLiveBottomList(context),true,false);
                if (bottomList == null) {
                    if (startLive.getRoom_type().equals("0")) {
                        bottomList = new CommentLiveBottomList(context, false);

                    } else {
                        bottomList = new CommentLiveBottomList(context);
                    }

                    bottom_builder = new XPopup.Builder(LivePushActivity2.this);
                    bottom_builder.autoDismiss(false)
                            .hasShadowBg(false)
                            .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                            .asCustom(bottomList);

                    bottomList.setBottomClickListener(new CommentLiveBottomList.BottomClickListener() {
                        @Override
                        public void messageClick() {
                            showXPop(new ChatPopup(context), true, true, context);
                        }

                        @Override
                        public void shopClick() {
                            if (shopItems == null) {
                                HttpUtils.getInstance().getGoodsList(MyUserInstance.getInstance().getUserinfo().getId(), new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        JSONObject data = HttpUtils.getInstance().check(response);
                                        if (HttpUtils.getInstance().swtichStatus(data)) {
                                            JSONArray datas = (JSONArray) data.getJSONArray("data");
                                            if (null != datas) {
                                                shopItems = JSON.parseArray(datas.toJSONString(), Good.class);
                                                if (shopItems == null) {
                                                    ToastUtils.showT(getString(R.string.live_no_shop));
                                                    return;
                                                }
                                                if (shopItems.size() == 0) {
                                                    ToastUtils.showT(getString(R.string.live_no_shop));
                                                    shopItems = null;
                                                    return;
                                                }
                                                CommentAnchorShopList commentShopList = new CommentAnchorShopList(LivePushActivity2.this, shopItems);
                                                showXPop(commentShopList, false, true, context);
                                            } else {
                                                ToastUtils.showT(getString(R.string.live_no_shop));
                                            }
                                        }


                                    }
                                });
                            } else {
                                CommentAnchorShopList commentShopList = new CommentAnchorShopList(LivePushActivity2.this, shopItems);
                                showXPop(commentShopList, false, true, context);

                            }
                        }

                        @Override
                        public void joinClick() {
                            switch (is_link) {
                                case "0":
                                    mPresenter.setLinkOnOff("1");
                                    break;
                                case "1":
                                    mPresenter.setLinkOnOff("0");
                                    break;
                            }

                        }

                        @Override
                        public void managerClick() {
                            showXPop(new RoomMangePopup(context, "1", startLive.getAnchorid()), true, true, context);
                        }

                        @Override
                        public void startPk() {

                            mPresenter.enterPkMode();
                        }
                    });
                    bottomList.show();

                } else {
                    bottomList.show();
                }
                break;


            case R.id.tv_pk_status:
                mPresenter.endPk();


                break;
            case R.id.garudian_tv:
                initGuardianlist();
                break;


        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void endlive(BaseResponse endlive) {
        LinkedTreeMap datas = (LinkedTreeMap) endlive.getData();
        Double d = (Double) datas.get("gift_profit");


        builder = new AlertDialog.Builder(LivePushActivity2.this).setTitle(WordUtil.getString(R.string.Live_Finish)).setCancelable(false)
                .setMessage("直播获得钻石数：" + new DecimalFormat("0").format(d) + "个").setPositiveButton(WordUtil.getString(R.string.Submit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //rl_normalToDo: 你想做的事情
                        cosXmlService.cancelAll();
                        stopCamera();
                        finish();
                    }
                });
        builder.create().show();


    }

    @Override
    public void enterPkMode(BaseResponse baseResponse) {
        if (startLive.getPk_status() == 0) {
            rl_pk_status.setVisibility(View.VISIBLE);
            tv_pk_status.setText("匹配中");
            try {
                gifDrawable_pk_status = new GifDrawable(getAssets(), "pk_finding.gif");
                gif_ok_status.setImageDrawable(gifDrawable_pk_status);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    public void sendShopItem(Good shopItem) {
        TIMMessage msg = new TIMMessage();
        TIMCustomElem elem = new TIMCustomElem();
        Message message = new Message();
        message.setAction("ExplainingGoods");

        MessageData messageData = new MessageData();

        Good goods = new Good();
        if (shopItem.getLive_explaining().equals("1")) {
            goods.setId(shopItem.getId());
            goods.setShopid(shopItem.getShopid());
            goods.setCategoryid(shopItem.getCategoryid());
            goods.setTitle(shopItem.getTitle());
            goods.setDesc(shopItem.getDesc());
            goods.setDesc_img_urls(shopItem.getDesc_img_urls());
            goods.setDelivery(shopItem.getDelivery());
            goods.setFreight(shopItem.getFreight());
            goods.setPrice(shopItem.getPrice());
            goods.setSale_count(shopItem.getSale_count());
            goods.setThumb_urls(shopItem.getThumb_urls());
            goods.setLive_explaining(shopItem.getLive_explaining());
        }


        messageData.setGoods(goods);
        message.setData(messageData);
        String json = new Gson().toJson(message);
        elem.setData(json.getBytes());
        if (msg.addElement(elem) != 0) {
            Log.d("addElement", "addElement failed");
            return;
        }
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {
            List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
            if (selectList.get(0).isCut()) {
                photoPath = selectList.get(0).getCutPath();
            } else {
                photoPath = selectList.get(0).getPath();

            }

            File imageFile = new File(photoPath);
            Glide.with(LivePushActivity2.this).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).load(imageFile).into(cover_iv);

        }
        if (requestCode == TAKE_ALBUM && resultCode == RESULT_OK) {
            photoPath = PhotoUtil.getRealPathFromUri(this, data.getData());
            File imageFile = new File(photoPath);
            Glide.with(LivePushActivity2.this).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).load(imageFile).into(cover_iv);
        }
    }

    private void upLoadImage(String imagePath) {

        File imageFile = new File(imagePath);
        String bucket = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_bucket(); //存储桶，格式：BucketName-APPID
        String cosPath = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_folder_image() + "/" + imageFile.getName(); //对象在存储桶中的位置标识符，即称对象键
        String srcPath = imagePath; //本地文件的绝对路径
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
        Set<String> headerKeys = new HashSet<>();
        headerKeys.add("Host");
        putObjectRequest.setSignParamsAndHeaders(null, headerKeys);
        // 使用异步回调上传
        cosXmlService.putObjectAsync(putObjectRequest, new CosXmlResultListener() {

            @Override
            public void onSuccess(CosXmlRequest cosXmlRequest, CosXmlResult result) {

                PutObjectResult putObjectResult = (PutObjectResult) result;
                thumb = putObjectResult.accessUrl;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (!MLVB_token.equals("")) {
                            mPresenter.startLive(MLVB_token, cateid, thumb, title_et.getText().toString(), "2", room_type, room_pricre, room_password);
                        } else {
                            MLVBHttpUtils.sharedInstance().login(new HttpRequests.OnResponseCallback<HttpResponse.LoginResponse>() {
                                @Override
                                public void onResponse(int retcode, @Nullable String retmsg, @Nullable HttpResponse.LoginResponse data) {
                                    if (retcode == 0) {
                                        if (data.token != null) {
                                            MLVB_token = data.token;
                                            mPresenter.startLive(MLVB_token, cateid, thumb, title_et.getText().toString(), "2", room_type, room_pricre, room_password);
                                        }
                                    } else {
                                        ToastUtils.showT("开播失败,请稍后再试");
                                    }
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException) {
                hideLoading();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (filter_rl.getVisibility() == View.VISIBLE) {
            filter_rl.setVisibility(View.GONE);
        } else {
            if (null != cosXmlService) {
                cosXmlService.cancelAll();
            }
            stopCamera();
            super.onBackPressed();
        }

    }

    public void stopCamera() {
        if (mLivePusher == null) {
            return;
        }


        mLivePusher.stopPusher();
        mLivePusher.stopCameraPreview(true);
    }

    @Override
    public void startSuccess(HotLive data) {

        startLive = data;

        //
        mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION, false, false);
        mPresenter.getGuardianCount(startLive.getAnchorid());
        int ret = mLivePusher.startPusher(startLive.getPush_url().trim());
        if (0 != ret) {
            ToastUtils.showT("推流失败");
            start_live_tv.setClickable(true);
            hideLoading();
            return;
        } else {
            is_show = true;
            camera_iv.setVisibility(View.GONE);
            beauty_iv.setVisibility(View.GONE);
            live_title_ll.setVisibility(View.GONE);
            start_live_tv.setVisibility(View.GONE);
            ll_room_type.setVisibility(View.GONE);
            close_iv.setVisibility(View.GONE);
            dtv_room_type.setVisibility(View.GONE);

            ll_room.setVisibility(View.GONE);
            chat_ll.setVisibility(View.VISIBLE);
            anchor_ll.setVisibility(View.VISIBLE);
            rl_bottom.setVisibility(View.VISIBLE);
            close_iv_2.setVisibility(View.VISIBLE);
            rl_time.setVisibility(View.VISIBLE);


            if (startLive.getGift_profit() != null) {
                tv_income_dimen.setText(startLive.getGift_profit());
            }

            if (tiPanelLayout != null) {
                tiPanelLayout.setOnDissMiss(new TiPanelLayout.onDissMiss() {
                    @Override
                    public void onDissmiss() {
                        chat_list_view.setVisibility(View.VISIBLE);
                        rl_bottom.setVisibility(View.VISIBLE);
                    }
                });
            }
            Glide.with(this).load(MyUserInstance.getInstance().getUserinfo().getAvatar()).into(head_iv);
            name_tv.setText(MyUserInstance.getInstance().getUserinfo().getNick_name());
            if (data.getHot() > 10000) {
                hot_tv.setText("热度：" + data.getHot() * 1f / 10000 + "万");
            } else {
                hot_tv.setText("热度：" + String.valueOf(data.getHot()));
            }
            //加入群组
            TIMGroupManager.getInstance().applyJoinGroup("LIVEROOM_" + data.getAnchorid(), "some reason", null);

            //群组 ID
            conversation = TIMManager.getInstance().getConversation(
                    TIMConversationType.Group,      //会话类型：群组
                    "LIVEROOM_" + data.getAnchorid());
            android.os.Message message = new android.os.Message();
            message.what = 1;
            myhandler.sendMessageDelayed(message, 1000);

            android.os.Message message2 = new android.os.Message();
            message2.what = 2;
            myhandler.sendMessageDelayed(message2, 1000);

            timMessageListener = new TIMMessageListener() {
                @Override
                public boolean onNewMessages(List<TIMMessage> list) {
                    for (TIMMessage msg : list) {
                        for (int i = 0; i < msg.getElementCount(); i++) {


                            if (msg.getElement(i).getType() != TIMElemType.GroupSystem) {

                                if (msg.getConversation().getPeer().equals("LIVEROOM_" + data.getAnchorid())) {
                                    handleRoomMessage(msg, data, i);
                                    break;
                                }


                            }

                        }

                    }
                    return false;
                }
            };

            TIMManager.getInstance().addMessageListener(timMessageListener);
            mPresenter.getGiftList();
            chatListAdapter.setStartLive(startLive);
            hideLoading();


        }

    }


    @Override
    public void setContributeRank(BaseResponse<ArrayList<ContributeRank>> contributeRank) {
        if (contributeRank.isSuccess()) {
            if (contributeRank.getData() == null) {
                ToastUtils.showT("当前主播贡献榜没有内容");
                return;
            }
            if (contributeRank.getData().size() == 0) {
                ToastUtils.showT("当前主播贡献榜没有内容");
                return;
            }

            showXPop(new CommentContributionList(context, contributeRank.getData()), true, false, context);
        }
    }

    private void initQCloud(QCloudData data) {
        String appid = MyUserInstance.getInstance().getUserConfig().getConfig().getQcloud_appid();
        String region = MyUserInstance.getInstance().getUserConfig().getConfig().getCos_region();

        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setAppidAndRegion(appid, region)
                .isHttps(true) // 使用 HTTPS 请求, 默认为 HTTP 请求
                .builder();

        QCloudCredentialProvider credentialProvider = new MyCredentialProvider(data.getCredentials().getTmpSecretId(),
                data.getCredentials().getTmpSecretKey(), data.getCredentials().getSessionToken(), data.getExpiredTime(), data.getStartTime());
        cosXmlService = new CosXmlService(this, serviceConfig, credentialProvider);
    }

    @Override
    public void setTempKeysForCos(QCloudData data) {
        initQCloud(data);
        upLoadImage(photoPath);
    }


    private void showPicker() {
        live_category = MyUserInstance.getInstance().getUserConfig().getLive_category();
        ArrayList<String> categorys = new ArrayList<>();
        for (LiveCategory liveCategory : live_category) {
            categorys.add(liveCategory.getTitle());
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                cateid = live_category.get(options1).getId();
                channel_tv.setText(live_category.get(options1).getTitle());
            }
        }).setSubmitColor(getResources().getColor(R.color.color_theme)).setCancelColor(getResources().getColor(R.color.color_theme)).build();
        pvOptions.setPicker(categorys);
        pvOptions.show();

    }


    private void takePhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .selectionMode(PictureConfig.SINGLE)
                .enableCrop(true)
                .freeStyleCropEnabled(true)
                .showCropGrid(true)
                .forResult(TAKE_PHOTO);
    }


    @Override
    public void onError(Throwable throwable) {
        hideLoading();
        start_live_tv.setClickable(true);
    }


    @Override
    public void finish() {
        super.finish();
        exit();
    }

    private void exit() {

        myhandler.removeMessages(1);
        myhandler.removeMessages(2);
        HttpResponseCache cache = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            cache = HttpResponseCache.getInstalled();
            if (cache != null) {
                cache.flush();
            }
        }
        if (timMessageListener != null) {
            TIMManager.getInstance().removeMessageListener(timMessageListener);
        }

        if (null != mGiftAnimViewHolder) {
            mGiftAnimViewHolder.cancelAllAnim();
        }
        if (mLivePusher != null) {
            mLivePusher.stopPusher();
            mLivePusher.stopCameraPreview(true);
            mLivePusher = null;
        }
        if (join_id != null) {
            if (!join_id.equals("")) {
                if (!this.isFinishing()) {
                    mPresenter.stopMlvbLink(MyUserInstance.getInstance().getUserinfo().getId(), join_id);

                }

            }
        }
        if (gifDrawable_pk_status != null && !gifDrawable_pk_status.isRecycled()) {
            gifDrawable_pk_status.stop();
            gifDrawable_pk_status.recycle();

        }
        if (gif_ok_status != null) {
            gif_ok_status.setImageDrawable(null);
        }
        if (pk_time != null) {
            pk_time.cancel();
        }
        if (spv_sub_left != null) {
            spv_sub_left.resetPlayer();
        }
        if (spv_sub_right != null) {
            spv_sub_right.resetPlayer();
        }
        stopPushJoin();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (!is_show) {
                if (null == cosXmlService) {
                    finish();
                } else {
                    if (null != startLive) {
                        mPresenter.endlive(startLive.getLiveid());
                    } else {
                        finish();
                    }
                }
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }


    }


    private void showDialog(String title, String hint) {
        RoomDialog.Builder builder = new RoomDialog.Builder(this, room_type);
        builder.setOnFinishListener(new RoomDialog.OnFinishListener() {
            @Override
            public void onFinish(String price) {
                switch (room_type) {
                    case "1":
                        room_password = price;
                        room_pricre = "0";
                        ll_room_type.setVisibility(View.GONE);
                        break;
                    case "2":
                        ll_room_type.setVisibility(View.GONE);
                        room_pricre = price;
                        room_password = "";
                        dtv_room_type.setText(WordUtil.getString(R.string.Paid) + " " + price + WordUtil.getString(R.string.time_minute) + "/" + WordUtil.getString(R.string.Gold_Coin));
                        break;
                }

            }
        });
        builder.create().show();
        builder.setTitle(title);
        builder.setHint(hint);
        builder.setInputType(room_type);
        builder.setCanCancel(false);
    }


    private void handleRoomMessage(TIMMessage msg, HotLive data, int i) {
        TIMCustomElem elem = (TIMCustomElem) msg.getElement(i);
        String json = new String(elem.getData());
        JSONObject jsonObject = JSON.parseObject(json);
        switch (jsonObject.getString("Action")) {
            case "LiveGroupMemberJoinExit":
                TIMGroupManager.getInstance().getGroupMembers("LIVEROOM_" + data.getAnchorid(), new TIMValueCallBack<List<TIMGroupMemberInfo>>() {
                    @Override
                    public void onError(int i, String s) {
                    }

                    @Override
                    public void onSuccess(List<TIMGroupMemberInfo> timGroupMemberInfos) {
                        if (null != tv_peoples) {
                            tv_peoples.setText(timGroupMemberInfos.size() + " " + WordUtil.getString(R.string.live_num));
                        }
                    }
                });
                break;
            case "LiveFinished":

                break;

            case "ExplainingGoods":

                break;
            default:
                Gson gson = new Gson();
                Message res = gson.fromJson(json, Message.class);
                if (res.getAction().equals("RoomMessage") || res.getAction().equals("RoomAttentAnchor")) {
                    if (res.getData().getChat().isVip() & res.getData().getChat().getMessage().equals("进入直播间")) {
                        mGifQueue2.offer(res);
                        if (mGifQueue2.size() == 1) {
                            if (!is_show_vip) {
                                getVipIn(mGifQueue2.poll(), 3);
                            }
                        }
                    }
                    if (!msg.isSelf()) {
                        setCaht(res);
                    }
                } else if (res.getAction().equals("GiftAnimation")) {
                    setCaht(res);
                    if (!LivePushActivity2.this.isFinishing()) {
                        showGift(initGift(res.getData().getGift()));
                    }
                } else if (res.getAction().equals("RoomNotification")) {
                    if (res.getData().getNotify() != null) {
                        Notify notify = res.getData().getNotify();
                        switch (res.getData().getNotify().getType()) {
                            case "RoomNotifyTypeSetManager":
                                //设置管理员
                                setCaht(res);
                                break;
                            case "RoomNotifyTypeCancelManager":
                                //取消管理员
                                setCaht(res);
                                break;
                            case "RoomNotificationReciveLinkRequest":
                                //小主播向大主播申请连麦

                                join_id = notify.getUser().getId() + "";
                                join_push_url = notify.getLink_acc_url();

                                JoinInfoDialog.Builder builder_join = new JoinInfoDialog.Builder(context);
                                builder_join.setUser(notify.getUser());
                                builder_join.setOnFinishListener(new JoinInfoDialog.OnFinishListener() {
                                    @Override
                                    public void onSubmit() {
                                        //同意连麦

                                        //请求同意连麦接口
                                        mPresenter.acceptMlvbLink(join_id);

                                    }

                                    @Override
                                    public void onCancel() {
                                        mPresenter.refuseMlvbLink(join_id);
                                        join_id = "";
                                        join_push_url = "";
                                    }
                                });
                                builder_join.create().show();

                                break;
                            case "RoomNotificationAcceptLinkRequest":
                                //通知用户连麦申请被接受

                                break;
                            case "RoomNotificationRefuseLinkRequest":
                                //通知用户连麦申请被拒接

                                break;
                            case "RoomNotificationStopLink":
                                //通知主播/用户连麦结束

                                if ((notify.getTouid() + "").equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                                    stopPushJoin();
                                }
                                break;
                            case "RoomNotifyTypeLinkOn":
                                //主播开启连麦功能通知

                                break;
                            case "RoomNotifyTypeLinkOff":
                                //主播关闭连麦功能通知

                                break;
                            case "RoomNotifyTypePkScoreChange":
                                //分数变化通知
                                if (notify.getPkinfo() == null) {
                                    return;
                                }

                                if (ppb_live.getVisibility() == View.VISIBLE) {
                                    ppb_live.cpmputerValue(notify.getPkinfo().getHome_score(), notify.getPkinfo().getAway_score());
                                }
                                startLive.getPkinfo().setAway_score(notify.getPkinfo().getAway_score());
                                startLive.getPkinfo().setHome_score(notify.getPkinfo().getHome_score());
                                break;
                            case "RoomNotifyTypePkStart":
                                //开启一轮新的PK
                                if (notify.getPkinfo() == null) {
                                    return;
                                }
                                if (notify.getPklive() == null) {
                                    return;
                                }
                                startLive.setPkinfo(notify.getPkinfo());
                                startLive.setPklive(notify.getPklive());
                                startLive.setPk_status(1);
                                startLive.setPkid(notify.getPkinfo().getId());
                                startPk();
                                break;

                            case "RoomNotifyTypePkEnd":
                                //开启一轮新的PK
                                endPk();
                                break;
                        }
                    }

                }
                break;
        }
    }

    //开启混流
    @Override
    public void mergeStream(BaseResponse baseResponse) {
        Log.e("mergeStream", "混流开启成功");
    }

    //拒绝
    @Override
    public void refuseMlvbLink(BaseResponse baseResponse) {

    }

    //申请
    @Override
    public void requestMlvbLink(BaseResponse baseResponse) {

    }

    //停止
    @Override
    public void stopMlvbLink(BaseResponse baseResponse) {

        stopPushJoin();
    }

    //同意
    @Override
    public void acceptMlvbLink(BaseResponse baseResponse) {
        //开启混流
        //  MlVBHttpUtils.sharedInstance().mergeStream();
        //打开本地预览播放对方播流
        playLinkUrl();

    }

    @Override
    public void setLinkOnOff(BaseResponse baseResponse) {
        switch (is_link) {
            case "0":
                is_link = "1";
                if (bottomList != null) {
                    bottomList.is_link(true);
                }
                break;
            case "1":
                is_link = "0";
                if (bottomList != null) {
                    bottomList.is_link(false);
                }
                break;
        }
    }

    //开始播放连麦者的推流
    private void playLinkUrl() {
        if (join_push_url == null) {
            ToastUtils.showT("获取连麦者推流地址失败,停止连麦");
            return;
        }
        if (join_push_url.equals("")) {
            ToastUtils.showT("获取连麦者推流地址失败,停止连麦");
            return;
        }
        cv_join.setVisibility(View.VISIBLE);
        SuperPlayerModel model = new SuperPlayerModel();
        model.url = join_push_url;
        model.title = startLive.getTitle();
        mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_LINKMIC_MAIN_PUBLISHER, false, false);
        txcv_join.playWithModel(model);
        txcv_join.setJoinListener(new SuperPlayerView.JoinListener() {
            @Override
            public void joinStart() {
                mPresenter.mergeStream(join_id);
            }

            @Override
            public void joinClose() {

                mPresenter.stopMlvbLink(MyUserInstance.getInstance().getUserinfo().getId(), join_id);
                stopPushJoin();
            }
        });

    }


    private void stopPushJoin() {
        if (txcv_join == null) {
            return;
        }
        cv_join.setVisibility(View.GONE);
        txcv_join.resetPlayer();
        txcv_join.setJoinListener(null);
        if (!this.isFinishing()) {
            if (mLivePusher == null) {
                return;
            }
            if (mLivePusher.isPushing()) {
                mLivePusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION, false, false);
            }
        }

        //请求连麦者的ID
        join_id = "";
        //请求连麦者的推流地址
        join_push_url = "";
    }


    //礼物开始
    //显示礼物动画
    public void showGift(ChatReceiveGiftBean giftBean) {

        if (mGiftAnimViewHolder == null) {
            mGiftAnimViewHolder = new GiftAnimViewHolder(this, root);
            mGiftAnimViewHolder.addToParent();
        }
        mGiftAnimViewHolder.showGiftAnim(giftBean);
    }
    //礼物结束

    */
/*
     * pk相关开始
     *//*

    private void pkNormalTime(String time) {
        if (pk_time != null) {
            pk_time.cancel();
        }

        long now_second = local_pk_second - MyUserInstance.getInstance().computerTime(time);
        if (now_second < 0) {
            pkPunishTime(local_pk_second + local_punish_second - MyUserInstance.getInstance().computerTime(time));

        } else {
            pk_time = new CountDownTimer(now_second, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    try {
                        tv_time_pk.setText("倒计时 " + MyUserInstance.getInstance().getTime((int) (millisUntilFinished / 1000)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFinish() {
                    pkPunishTime(120000);

                }
            };
            pk_time.start();
        }


    }

    private void pkPunishTime(long punishTime) {
        if (pk_time != null) {
            pk_time.cancel();
        }
        getResult(startLive.getPkinfo().getHome_score(), startLive.getPkinfo().getAway_score());
        pk_time = new CountDownTimer(punishTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                try {
                    tv_time_pk.setText("惩罚中 " + MyUserInstance.getInstance().getTime((int) (millisUntilFinished / 1000)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                if (!LivePushActivity2.this.isFinishing()) {
                    endPk();
                }
            }
        };
        pk_time.start();
    }

    private void endPk() {
        if (this.isFinishing()) {
            return;
        }

        //释放播放器,并隐藏
        if (spv_sub_left != null) {
            spv_sub_left.resetPlayer();
            spv_sub_left.setVisibility(View.GONE);
        }
        if (spv_sub_right != null) {
            spv_sub_right.resetPlayer();
            spv_sub_right.setVisibility(View.GONE);
        }
        if (gifDrawable_pk_status != null && !gifDrawable_pk_status.isRecycled()) {
            gifDrawable_pk_status.stop();
            gifDrawable_pk_status.recycle();

        }
        if (gif_ok_status != null) {
            gif_ok_status.setImageDrawable(null);
            rl_pk_status.setVisibility(View.GONE);
        }


        //调整界面,主舞台全显示,隐藏PK条,播放区全屏
        //播放区全屏
        RelativeLayout.LayoutParams lp_rl_root = (RelativeLayout.LayoutParams) rl_play.getLayoutParams();
        lp_rl_root.setMargins(0, 0, 0, 0);
        rl_play.setLayoutParams(lp_rl_root);

        RelativeLayout.LayoutParams lp_ll_play = (RelativeLayout.LayoutParams) ll_play_view.getLayoutParams();
        lp_ll_play.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        ll_play_view.setLayoutParams(lp_ll_play);
        //隐藏PK条,时间计时器
        tv_time_pk.setVisibility(View.GONE);
        ppb_live.setVisibility(View.GONE);
        ppb_live.rest();
        startLive.setPklive(null);
        startLive.setPkinfo(null);
        startLive.setPk_status(0);
        startLive.setPkid(0);
        if (pk_time != null) {
            pk_time.cancel();
        }
        siv_center.setVisibility(View.GONE);
        siv_left.setVisibility(View.GONE);
        siv_right.setVisibility(View.GONE);
        siv_center.stopAnimation(true);
        siv_left.stopAnimation(true);
        siv_right.stopAnimation(true);
        hidePkPlayerInfo();

    }


    private void startPk() {
        //清理计时器
        if (pk_time != null) {
            pk_time.cancel();
        }
        if (spv_sub_left != null) {
            spv_sub_left.resetPlayer();
            spv_sub_left.setVisibility(View.GONE);
        }
        if (spv_sub_right != null) {
            spv_sub_right.resetPlayer();
            spv_sub_right.setVisibility(View.GONE);
        }
        if (gifDrawable_pk_status != null && !gifDrawable_pk_status.isRecycled()) {
            gifDrawable_pk_status.stop();
            gifDrawable_pk_status.recycle();

        }
        if (gif_ok_status != null) {
            gif_ok_status.setImageDrawable(null);
        }

        Log.e("startPk", startLive.getPkinfo().getId() + "           " + startLive.getPkinfo().getHome_anchorid() + "           " + startLive.getAnchorid() + "           " + startLive.getLiveid());
        //重开计时器
        pkNormalTime(startLive.getPkinfo().getCreate_time());
        //隐藏胜败动画
        siv_center.setVisibility(View.GONE);
        siv_left.setVisibility(View.GONE);
        siv_right.setVisibility(View.GONE);
        siv_center.stopAnimation(true);
        siv_left.stopAnimation(true);
        siv_right.stopAnimation(true);


        //调整播放舞台
        RelativeLayout.LayoutParams lp_rl_play = (RelativeLayout.LayoutParams) rl_play.getLayoutParams();
        lp_rl_play.setMargins(0, DipPxUtils.dip2px(context, 140), 0, 0);
        rl_play.setLayoutParams(lp_rl_play);


        RelativeLayout.LayoutParams lp_ll_play = (RelativeLayout.LayoutParams) ll_play_view.getLayoutParams();
        lp_ll_play.height = (MyUserInstance.getInstance().device_width / 2) / 3 * 4;
        ll_play_view.setLayoutParams(lp_ll_play);

        //显示PK条,计时器
        ppb_live.setVisibility(View.VISIBLE);
        tv_time_pk.setVisibility(View.VISIBLE);

        //填充播放数据
        if (startLive.getPkinfo().getHome_anchorid() == Integer.parseInt(startLive.getAnchorid())) {
            spv_sub_left.setVisibility(View.GONE);


            SuperPlayerModel model_sub = new SuperPlayerModel();
            model_sub.url = startLive.getPklive().getAcc_pull_url();
            model_sub.title = startLive.getPklive().getTitle();
            spv_sub_right.playWithModel(model_sub);
            spv_sub_right.cleanClick();
            spv_sub_right.setVisibility(View.VISIBLE);

        } else if (startLive.getPkinfo().getAway_anchorid() == Integer.parseInt(startLive.getAnchorid())) {

            spv_sub_right.setVisibility(View.GONE);

            SuperPlayerModel model_sub = new SuperPlayerModel();
            model_sub.url = startLive.getPklive().getAcc_pull_url();
            model_sub.title = startLive.getPklive().getTitle();
            spv_sub_left.playWithModel(model_sub);
            spv_sub_left.cleanClick();
            spv_sub_left.setVisibility(View.VISIBLE);
        }
        pkNormalTime(startLive.getPkinfo().getCreate_time());
        ppb_live.cpmputerValue(startLive.getPkinfo().getHome_score(), startLive.getPkinfo().getAway_score());

        rl_pk_status.setVisibility(View.VISIBLE);
        tv_pk_status.setText("结束PK");
        try {
            gifDrawable_pk_status = new GifDrawable(getAssets(), "pk_finding.gif");
            gif_ok_status.setImageDrawable(gifDrawable_pk_status);
        } catch (IOException e) {
            e.printStackTrace();
        }


        SVGAParser.Companion.shareParser().decodeFromAssets("pk_start.svga", new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                siv_start.setVisibility(View.VISIBLE);
                siv_start.setImageDrawable(drawable);
                siv_start.startAnimation();
            }

            @Override
            public void onError() {

            }
        });
        showPkPlayerInfo();
    }

    public void showPkPlayerInfo() {
        ll_user_info_left.setVisibility(View.GONE);
        ll_user_info_right.setVisibility(View.GONE);
        if (startLive.getPkinfo().getHome_anchorid() == Integer.parseInt(startLive.getAnchorid())) {
            get_type = 2;
            mPresenter.getUserBasicInfo(String.valueOf(startLive.getPkinfo().getAway_anchorid()));

        } else {
            get_type = 3;
            mPresenter.getUserBasicInfo(String.valueOf(startLive.getPkinfo().getHome_anchorid()));

        }
    }

    public void hidePkPlayerInfo() {
        ll_user_info_left.setVisibility(View.GONE);
        ll_user_info_right.setVisibility(View.GONE);
    }

    private void getResult(int home, int away) {

        if (home > away) {
            siv_center.setVisibility(View.GONE);
            siv_left.setVisibility(View.VISIBLE);
            siv_right.setVisibility(View.VISIBLE);
            SVGAParser.Companion.shareParser().decodeFromAssets("pk_winner.svga", new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                    siv_left.setImageDrawable(drawable);
                    siv_left.startAnimation();
                }

                @Override
                public void onError() {

                }
            });
            siv_right.setImageDrawable(getDrawable(R.mipmap.pk_lose));

        } else if (home < away) {
            siv_center.setVisibility(View.GONE);
            siv_left.setVisibility(View.VISIBLE);
            siv_right.setVisibility(View.VISIBLE);

            SVGAParser.Companion.shareParser().decodeFromAssets("pk_winner.svga", new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity svgaVideoEntity) {
                    SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                    siv_right.setImageDrawable(drawable);
                    siv_right.startAnimation();
                }

                @Override
                public void onError() {

                }
            });
            siv_left.setImageDrawable(getDrawable(R.mipmap.pk_lose));
        } else if (home == away) {
            siv_center.setVisibility(View.VISIBLE);
            siv_left.setVisibility(View.GONE);
            siv_right.setVisibility(View.GONE);
            siv_center.setImageDrawable(getDrawable(R.mipmap.pk_draw));
        }


    }

    @Override
    public void endPk(BaseResponse baseResponse) {
        switch (startLive.getPk_status()) {
            case 0:
                if (gifDrawable_pk_status != null && !gifDrawable_pk_status.isRecycled()) {
                    gifDrawable_pk_status.stop();
                    gifDrawable_pk_status.recycle();
                }
                if (gif_ok_status != null) {
                    gif_ok_status.setImageDrawable(null);
                }
                rl_pk_status.setVisibility(View.GONE);
                break;

            case 1:
                endPk();
                break;
            case 3:

                break;
        }
    }

    */
/*
     * pk相关结束
     *//*


    @Override
    public void getUserBasicInfo(BaseResponse<UserRegist> baseResponse) {
        switch (get_type) {
            case 1:
                UserInfoDialog.Builder builder = new UserInfoDialog.Builder(context);
                builder.setType("1");
                builder.setStatus("2");
                builder.setAnchorid(startLive.getAnchorid());
                builder.setUserRegist(baseResponse.getData());
                builder.create().show();
                break;
            case 2:
                //表示主队是自己
                ll_user_info_right.setVisibility(View.VISIBLE);
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.moren)).load(baseResponse.getData().getAvatar()).into(civ_avatar_right);
                tv_name_right.setText(baseResponse.getData().getNick_name());
                HttpUtils.getInstance().checkAttent(baseResponse.getData().getId() + "", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject data = HttpUtils.getInstance().check(response);
                        if (data.get("status").toString().equals("0")) {
                            String temp = data.getJSONObject("data").getString("attented");
                            if (temp.equals("0")) {
                                iv_attend_right.setVisibility(View.VISIBLE);
                                iv_attend_right.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HttpUtils.getInstance().attentAnchor(baseResponse.getData().getId() + "", "1", new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {
                                                JSONObject data = HttpUtils.getInstance().check(response);
                                                if (data.get("status").toString().equals("0")) {
                                                    iv_attend_right.setVisibility(View.GONE);
                                                }

                                            }
                                        });
                                    }
                                });
                            } else {
                                iv_attend_right.setVisibility(View.GONE);
                            }
                        }
                    }
                });
                break;
            case 3:
                //表示客队是自己
                ll_user_info_left.setVisibility(View.VISIBLE);
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.moren)).load(baseResponse.getData().getAvatar()).into(civ_avatar_left);
                tv_name_left.setText(baseResponse.getData().getNick_name());
                HttpUtils.getInstance().checkAttent(baseResponse.getData().getId() + "", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject data = HttpUtils.getInstance().check(response);
                        if (data.get("status").toString().equals("0")) {
                            String temp = data.getJSONObject("data").getString("attented");
                            if (temp.equals("0")) {
                                iv_attend_left.setVisibility(View.VISIBLE);
                                iv_attend_left.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HttpUtils.getInstance().attentAnchor(baseResponse.getData().getId() + "", "1", new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {
                                                JSONObject data = HttpUtils.getInstance().check(response);
                                                if (data.get("status").toString().equals("0")) {
                                                    iv_attend_left.setVisibility(View.GONE);
                                                }
                                            }
                                        });
                                    }
                                });
                            } else {
                                iv_attend_left.setVisibility(View.GONE);
                            }
                        }
                    }
                });
                break;

        }

    }

    //关闭直播弹窗
    private void finishEndDialog() {
        if (null == cosXmlService) {
            finish();
        } else {

            if (null != startLive) {
                builder = new AlertDialog.Builder(LivePushActivity2.this).setTitle("结束直播").setCancelable(false)
                        .setMessage("是否要结束直播").setPositiveButton(WordUtil.getString(R.string.Submit), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mPresenter.endlive(startLive.getLiveid());
                            }
                        }).setNegativeButton(WordUtil.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            } else {
                finish();
            }
        }
    }

    //守护
    @Override
    public void getGuardianCount(BaseResponse baseResponse) {
        LinkedTreeMap result = (LinkedTreeMap) baseResponse.getData();
        int temp = ((Double) result.get("count")).intValue();
        if (temp > 0) {
            garudian_tv.setText("守护 " + temp + "人");
        } else {
            garudian_tv.setText("守护 0人");
        }
    }

    public void initGuardianlist() {
        CommentGuardianList commentGuardianList = new CommentGuardianList(context, startLive.getAnchorid());
        new XPopup.Builder(context)

                .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                .asCustom(commentGuardianList*/
/*.enableDrag(false)*//*
)
                .show();

    }

    private void initDialog() {

        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setOnFinishListener(new MessageDialog.OnFinishListener() {
            @Override
            public void onFinish() {
                finish();
            }
        });
        builder.create().show();
        builder.setTitle("提示");
        builder.setContent("初始化失败");
        builder.setHideCancel();
    }
}
*/
