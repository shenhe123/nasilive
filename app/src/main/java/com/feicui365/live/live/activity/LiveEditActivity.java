package com.feicui365.live.live.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.feicui365.live.model.entity.LiveHistoryBean;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;

import com.lxj.xpopup.XPopup;
import com.feicui365.live.interfaces.OnPicUpLoadPicsListener;
import com.feicui365.live.util.UploadUtils;
import com.feicui365.nasinet.utils.AppManager;
import com.tbruyelle.rxpermissions2.Permission;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePusher;
import com.tencent.live2.V2TXLivePusherObserver;
import com.tencent.live2.impl.V2TXLivePusherImpl;
import com.tencent.rtmp.ui.TXCloudVideoView;

import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.interfaces.CommonPickerListener;
import com.feicui365.live.interfaces.OnRoomInfoInputClickListenr;
import com.feicui365.live.live.dialog.CommonPickerPopup;
import com.feicui365.live.live.dialog.NormalBeautylDialog;
import com.feicui365.live.live.dialog.RoomTypeDialog;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.live.utils.GlideUtils;
import com.feicui365.live.model.entity.HotLive;
import com.feicui365.live.model.entity.LiveCategory;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.widget.DrawableTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.tillusory.sdk.TiSDK;
import cn.tillusory.sdk.TiSDKManager;
import cn.tillusory.sdk.bean.InitStatus;
import cn.tillusory.sdk.bean.TiRotation;
import cn.tillusory.tiui.TiPanelLayout;
import io.reactivex.functions.Consumer;


public class LiveEditActivity extends BaseMvpActivity<LivePushPresenter> implements LivePushContrat.View {

    @BindView(R.id.tcv_edit)
    TXCloudVideoView tcvEdit;

    @BindView(R.id.dtv_room_type)
    TextView mTvRoomType;
    @BindView(R.id.tv_category)
    DrawableTextView mTvCategoryTitle;


    @BindView(R.id.rl_live_root)
    RelativeLayout mLiveRoot;

    @BindView(R.id.et_title)
    EditText mTitle;

    @BindView(R.id.cover_iv)
    ImageView mIvCover;


    @BindView(R.id.lo_default_cb)
    CheckBox mLoDefaultCb;


    private int mPerCount;
    //private UploadUtils mUploadUtils;
    private UploadUtils mUpLoadUtils;
    private V2TXLivePusher mLivePusher;
    private ArrayList<LiveCategory> mCategorys;
    private LiveCategory mLiveCategory;
    private NormalBeautylDialog normalBeautylDialog;
    private int mBeautyChannel;
    private String mThumbUrl;
    private final int INIT_BEAUTY = 10001;
    private final int START_LIVE = 10002;
    private TiPanelLayout mPanelLayout;
    private int mRoomType = 0;//0普通,1密码,2付费
    private String mRoomPrice;
    private String mRoomPassword;
    int default_status = 2;   //1隐藏 2显示

    private HotLive mLiveInfo;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case INIT_BEAUTY:
                    mPanelLayout = new TiPanelLayout(context).init(TiSDKManager.getInstance());
                    addContentView(mPanelLayout,
                            new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                    mLivePusher.setObserver(new V2TXLivePusherObserver() {
                        @Override
                        public int onProcessVideoFrame(V2TXLiveDef.V2TXLiveVideoFrame srcFrame, V2TXLiveDef.V2TXLiveVideoFrame dstFrame) {
                            int textureId = TiSDKManager.getInstance().renderTexture2D(srcFrame.texture.textureId, srcFrame.width, srcFrame.height,
                                    TiRotation.CLOCKWISE_ROTATION_0, false);
                            dstFrame.texture.textureId = textureId;
                            return 0;
                        }
                    });

                    break;
                case START_LIVE:
                    String thumb = (String) msg.obj;
                    showLoading();
                    mPresenter.mlvbStartLive(mLiveCategory.getId(), thumb, mTitle.getText().toString(),
                            String.valueOf(mRoomType), mRoomPrice, mRoomPassword, default_status);
                    break;

            }
        }
    };


    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.living_edit_activity;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLivePusher != null) {
            mLivePusher.release();
        }
    }

    @Override
    protected void initView() {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        mPresenter.getLiveHistoryTitle();
        setActionBarTextColor(false);
        hideTitle(true);

        mUpLoadUtils = new UploadUtils(this);
        mUpLoadUtils.getConfig();

        //初始化腾讯播放器
        initEditPlayer();
        mLoDefaultCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    default_status = 1;
                } else {
                    default_status = 2;
                }
            }
        });

    }

    private void initEditPlayer() {
        mLivePusher = new V2TXLivePusherImpl(this, V2TXLiveDef.V2TXLiveMode.TXLiveMode_RTMP);
        mLivePusher.setRenderView(tcvEdit);
        mLivePusher.startCamera(true);

        if (MyUserInstance.getInstance().getBeautyChannel() == 1) {
            mLivePusher.enableCustomVideoProcess(true, V2TXLiveDef.V2TXLivePixelFormat.V2TXLivePixelFormatTexture2D, V2TXLiveDef.V2TXLiveBufferType.V2TXLiveBufferTypeTexture);

        }
        mLivePusher.startMicrophone();
        initBeauty();
        initCategory();
    }

    private void initBeauty() {
        if (MyUserInstance.getInstance().getBeautyChannel() == 1) {
            //高级美颜
            mBeautyChannel = 1;
            TiSDK.init(MyUserInstance.getInstance().getBeautyKey(), this, new TiSDK.TiInitCallback() {

                @Override
                public void finish(InitStatus initStatus) {
                    if (initStatus.getCode() == 100) {
                        if (mPanelLayout == null) {
                            Message message = new Message();
                            message.what = INIT_BEAUTY;
                            mHandler.sendMessage(message);

                        }
                    }
                }
            });
        } else {
            //普通美颜
            normalBeautylDialog = new NormalBeautylDialog();
            normalBeautylDialog.setLivePusher(mLivePusher);
            mBeautyChannel = 0;
        }


    }

    public  void initCategory(){
        if (mCategorys == null) {
            mCategorys = MyUserInstance.getInstance().getLiveCategory();
        }

        if (!ArmsUtils.isArrEmpty(mCategorys)) {
            return;
        }
        if (mCategorys!=null&&mCategorys.size()>0){
            mLiveCategory = mCategorys.get(0);
        }
    }


    @OnClick({R.id.cover_iv, R.id.tv_category, R.id.iv_beauty, R.id.iv_camera,
            R.id.tv_start_live, R.id.dtv_room_type, R.id.iv_close})
    public void onClick(View view) {
        if (!ArmsUtils.canClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.cover_iv:
                openPictures();
                break;
            case R.id.iv_close:
                finish();
                break;
            case R.id.iv_camera:
                if (mLivePusher.getDeviceManager().isFrontCamera()) {
                    mLivePusher.getDeviceManager().switchCamera(false);
                } else {
                    mLivePusher.getDeviceManager().switchCamera(true);
                }

                break;
            case R.id.tv_category:
//                selectCategory();
                break;
            case R.id.dtv_room_type:
       /*         RoomTypeDialog roomTypeDialog = new RoomTypeDialog(mRoomType);
                roomTypeDialog.show(getSupportFragmentManager(), "");
                roomTypeDialog.setOnRoomInfoInputClickListenr(new OnRoomInfoInputClickListenr() {
                    @Override
                    public void onResult(int type ,String mPassword,String mPrice) {
                        switch (type) {
                            case 0:
                                mRoomPassword = null;
                                mRoomPrice = null;
                                mTvRoomType.setText(getString(R.string.st_normal_room));
                                break;
                            case 1:
                                mRoomPassword = mPassword;
                                mRoomPrice = null;
                                mTvRoomType.setText(getString(R.string.st_password_room));
                                break;
                            case 2:
                                mRoomPassword = null;
                                mRoomPrice = mPrice;
                                mTvRoomType.setText(getString(R.string.st_price_room));
                                break;
                        }
                        Log.e("mTvRoomType", mTvRoomType.getText().toString());
                        mRoomType = type;

                    }
                });*/
                break;
            case R.id.iv_beauty:
                switch (mBeautyChannel) {
                    case 1:

                        if (mPanelLayout != null) {
                            //mLiveRoot.addView(mPanelLayout);

                            mPanelLayout.showView();

                        }


                        break;
                    default:
                        if (normalBeautylDialog == null) {
                            return;
                        }
                        if (normalBeautylDialog.isAdded()) {
                            return;
                        }

                        normalBeautylDialog.show(getSupportFragmentManager(), "");
                        break;

                }
                break;
            case R.id.tv_start_live:
                if (!ArmsUtils.isStringEmpty(mThumbUrl)) {
                    ToastUtils.showT(getString(R.string.st_select_thumb));
                    return;
                }

                if (mLiveCategory == null) {
                    ToastUtils.showT(getString(R.string.st_select_category));
                    return;
                }

                if (mTitle.getText().toString().length() == 0) {
                    ToastUtils.showT(getString(R.string.st_input_live_title));
                    return;
                }


                ArrayList<String> list = new ArrayList<>();
                list.add(mThumbUrl);
                showLoading();
                if (mThumbUrl.indexOf("https") == -1) {
                    mUpLoadUtils.uploadPics(list, false, new OnPicUpLoadPicsListener() {
                        @Override
                        public void onPicUrls(String result, String blurResult) {

                            if (result == null & blurResult == null) {
                                return;
                            }
                            Message message = new Message();
                            message.what = START_LIVE;
                            message.obj = result;
                            mHandler.sendMessage(message);
                        }
                        @Override
                        public void onError() {

                        }
                    });
                } else {
                    Message message = new Message();
                    message.what = START_LIVE;
                    message.obj = mThumbUrl;
                    mHandler.sendMessage(message);
                }
                break;

        }
    }

    @Override
    public void getLiveHistoryTitle(LiveHistoryBean bean) {
        GlideUtils.setImage(context, bean.getThumb(), mIvCover);
        mThumbUrl = bean.getThumb() ;
        mTitle.setText(bean.getTitle());
        int is_hide_name = bean.getIs_hide_name();
        //是否隐藏用户昵称【1隐藏 2显示】
        if (is_hide_name == 1) {
            mLoDefaultCb.setChecked(true);
        } else {
            mLoDefaultCb.setChecked(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case Constants.CHOSE_PICS:
                mThumbUrl = PictureSelector.obtainMultipleResult(data).get(0).getCutPath();
                GlideUtils.setImage(context, mThumbUrl, mIvCover);

                break;

        }


    }

    private void openPictures() {
        mPerCount = 0;
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.requestEach(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {

                        if (permission.granted) {
                            mPerCount++;
                            if (mPerCount == 3) {
                                PictureSelector.create((Activity) context)
                                        .openGallery(PictureMimeType.ofImage())
                                        .maxSelectNum(1)
                                        .enableCrop(true)
                                        .freeStyleCropEnabled(true)
                                        .showCropGrid(true)
                                        .forResult(Constants.CHOSE_PICS);
                            }
                        } else {
                            ToastUtils.showT("没有读写权限");
                        }

                    }
                });
    }

    private void selectCategory() {
        if (mCategorys == null) {
            mCategorys = MyUserInstance.getInstance().getLiveCategory();
        }

        if (!ArmsUtils.isArrEmpty(mCategorys)) {
            ToastUtils.showT(getString(R.string.st_error));
            return;
        }
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < mCategorys.size(); i++) {
            list.add(mCategorys.get(i).getTitle());
        }


        CommonPickerPopup mCategoryPicker = new CommonPickerPopup(this);
        mCategoryPicker.setPickerData(list);
        mCategoryPicker.setCommonPickerListener(new CommonPickerListener() {
            @Override
            public void onItemSelected(int index, String data) {
                mLiveCategory = mCategorys.get(index);
                mTvCategoryTitle.setText(mLiveCategory.getTitle());
            }
        });
        new XPopup.Builder(context)
                .asCustom(mCategoryPicker)
                .show();


    }

    @Override
    public void mlvbStartLive(HotLive bean) {

        mLiveInfo = bean;
        goLive();
    }

    private void goLive() {

        Intent intent = new Intent(context, LivePushActivity2.class);
        intent.putExtra(Constants.LIVE_INFO, mLiveInfo);
        intent.putExtra(Constants.LIVE_MEMBER, 0);
        AppManager.getAppManager().startActivity(intent);

        finish();
        mLivePusher.release();
        if (MyUserInstance.getInstance().getBeautyChannel() == 1) {
            TiSDKManager.getInstance().destroy();
        }
    }
}
