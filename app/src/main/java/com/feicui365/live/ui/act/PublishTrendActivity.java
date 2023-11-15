package com.feicui365.live.ui.act;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.LivePushContrat;
import com.feicui365.live.model.entity.QCloudData;
import com.feicui365.live.model.entity.Topic;
import com.feicui365.live.presenter.LivePushPresenter;
import com.feicui365.live.util.PublishTrendUtils;
import com.feicui365.live.dialog.PriceDialog;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.util.TxPicturePushUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.MyStandardVideoController;
import com.feicui365.nasinet.utils.DipPxUtils;
import com.tencent.cos.xml.CosXmlService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class PublishTrendActivity extends BaseMvpActivity<LivePushPresenter> implements LivePushContrat.View, EasyPermissions.PermissionCallbacks, BGASortableNinePhotoLayout.Delegate, View.OnClickListener {
    private static final int PRC_PHOTO_PICKER = 1;
    private static final int RC_CHOOSE_PHOTO = 1;
    private static final int RC_PHOTO_PREVIEW = 2;
    private static final int TOPIC_CHOSE = 33;
    @BindView(R.id.snpl_moment_add_photos)
    public BGASortableNinePhotoLayout mPhotosSnpl;
    @BindView(R.id.tv_other)
    TextView tv_other;


    @BindView(R.id.rl_video_into)
    RelativeLayout rl_video_into;
    @BindView(R.id.rl_pay)
    RelativeLayout rl_pay;
    @BindView(R.id.rl_set_pay)
    RelativeLayout rl_set_pay;

    @BindView(R.id.tv_price)
    TextView tv_price;

    @BindView(R.id.iv_2)
    ImageView iv_2;


    @BindView(R.id.rl_video)
    RelativeLayout rl_video;
    @BindView(R.id.et_title)
    EditText et_title;

    @BindView(R.id.video_player)
    VideoView video_player;
    @BindView(R.id.iv_delete)
    ImageView iv_delete;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.iv_pic)
    ImageView iv_pic;
    @BindView(R.id.iv_video)
    ImageView iv_video;
    @BindView(R.id.ll_type)
    LinearLayout ll_type;
    @BindView(R.id.rl_not_chose_talk)
    RelativeLayout rl_not_chose_talk;
    @BindView(R.id.rl_show_talk)
    RelativeLayout rl_show_talk;
    @BindView(R.id.tv_talk)
    TextView tv_talk;
    @BindView(R.id.ic_topic_clear)
    ImageView ic_topic_clear;


    String type = "1";
    public CosXmlService cosXmlService;
    public ArrayList<String> urls = new ArrayList();
    public ArrayList<String> blur_urls = new ArrayList();
    public int trend_price = 0;
    public String video_url = "";
    PublishTrendUtils faBuUtils;

    public void setSingle_display_type(String single_display_type) {
        this.single_display_type = single_display_type;
    }

    public String single_display_type = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_trend2;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy");
        if (video_player != null) {
            if (video_player.isPlaying()) {
                video_player.release();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        VideoViewManager.instance().pause();
    }


    @Override
    protected void initView() {
        mPresenter = new LivePushPresenter();
        mPresenter.attachView(this);
        setTitle(WordUtil.getString(R.string.Pulish_Moment));
        setOther(WordUtil.getString(R.string.Publish));
        hideOther(false);
        setOtherTextColor(R.color.white);
        TextPaint tp = tv_title.getPaint();
        tp.setFakeBoldText(true);


        setOtherBackRound(R.drawable.shape_corner_theme2);
        setOtherLayout(50, 25);

        mPhotosSnpl.setMaxItemCount(9);
        mPhotosSnpl.setDelegate(this);

        faBuUtils = new PublishTrendUtils(this);

        //进来时,不显示视频跟图片的选择框
        mPresenter.getTempKeysForCos();

    }


    @Override
    public void onError(Throwable throwable) {

    }


    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPhotosSnpl.removeItem(position);
        if(mPhotosSnpl.getData().size()==0){
            ll_type.setVisibility(View.VISIBLE);
            mPhotosSnpl.setVisibility(View.GONE);
            rl_pay.setVisibility(View.GONE);
            trend_price = 0;
        }

    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        Intent photoPickerPreviewIntent = new BGAPhotoPickerPreviewActivity.IntentBuilder(this)
                .previewPhotos(models) // 当前预览的图片路径集合
                .selectedPhotos(models) // 当前已选中的图片路径集合

                .maxChooseCount(mPhotosSnpl.getMaxItemCount()) // 图片选择张数的最大值
                .currentPosition(position) // 当前预览图片的索引
                .isFromTakePhoto(false) // 是否是拍完照后跳转过来
                .build();
        startActivityForResult(photoPickerPreviewIntent, RC_PHOTO_PREVIEW);
    }

    @Override
    public void onNinePhotoItemExchanged(BGASortableNinePhotoLayout sortableNinePhotoLayout, int fromPosition, int toPosition, ArrayList<String> models) {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == PRC_PHOTO_PICKER) {
            ToastUtils.showT("您拒绝了「图片选择」所需要的相关权限!");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @OnClick({R.id.tv_other, R.id.iv_delete, R.id.iv_pic, R.id.iv_video,R.id.rl_pay,R.id.rl_not_chose_talk,R.id.ic_topic_clear})
    public void onClick(View v) {
        if(isFastClick()){
            return;
        }
        switch (v.getId()) {

            //发布按钮
            case R.id.tv_other:
                if(mPhotosSnpl.getVisibility()==View.VISIBLE&mPhotosSnpl.getData().size()!=0){
                    type="2";
                }else if(rl_video.getVisibility()==View.VISIBLE&!video_url.equals("")){
                    type="3";
                }else{
                    type="1";
                }

                switch (type) {
                    case "1":
                        mPresenter.publish(type, et_title.getText().toString(), "", "", "", "", "0",tv_talk.getText().toString());
                        break;
                    case "2":

                        faBuUtils.startPushPic();

                        break;
                    case "3":
                        faBuUtils.startPushVideo();

                        break;
                }


                break;


             case R.id.rl_pay:
               PriceDialog.Builder builder = new PriceDialog.Builder(this);
                builder.setOnFinishListener(new PriceDialog.OnFinishListener() {
                    @Override
                    public void onFinish(String price) {
                        trend_price = Integer.parseInt(price);
                        if (trend_price >= 0) {

                            tv_price.setText(trend_price + " 金币");
                            iv_2.setVisibility(View.GONE);
                        }
                    }
                });
                builder.create().show();


                break;


            case R.id.iv_delete:
              //删除视频按钮,释放资源,隐藏播放器,清空视频地址,显示类型选择
                ll_type.setVisibility(View.VISIBLE);
                rl_video.setVisibility(View.GONE);
                rl_pay.setVisibility(View.GONE);
                trend_price = 0;
                video_player.release();
                video_url = "";
                break;

            case R.id.iv_pic:

                //点击图片选择,直接开始选择图片
                choicePhotoWrapper();

                break;
            case R.id.iv_video:
                //点击视频按钮后,隐藏下方类型选择,并开始选择视频

                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofVideo())
                        .maxSelectNum(1)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;

            case R.id.rl_not_chose_talk:
                Intent intent=new Intent(context,SearchTalkActivity.class);
                startActivityForResult(intent,TOPIC_CHOSE);
                break;
            case R.id.ic_topic_clear:
                rl_not_chose_talk.setVisibility(View.VISIBLE);
                rl_show_talk.setVisibility(View.GONE);
                tv_talk.setText("");
                break;
        }
    }


    @AfterPermissionGranted(PRC_PHOTO_PICKER)
    private void choicePhotoWrapper() {

        if (null == mPhotosSnpl) {
            return;
        }

        if (mPhotosSnpl.getData().size() >= 9) {
            ToastUtils.showT("最多只能选择9张图片");
            return;
        }

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "TakePhoto");
            int temp_count = 9 - mPhotosSnpl.getData().size();
            Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(this)
                    .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                    .maxChooseCount(temp_count) // 图片选择张数的最大值
                    .selectedPhotos(null) // 当前已选中的图片路径集合
                    .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                    .build();
            startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", PRC_PHOTO_PICKER, perms);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK & requestCode == RC_CHOOSE_PHOTO) {
            mPhotosSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedPhotos(data));
            //图片选择开始,显示图片选择器隐藏type
            if(mPhotosSnpl.getData().size()>0){
                ll_type.setVisibility(View.GONE);
                mPhotosSnpl.setVisibility(View.VISIBLE);
                rl_pay.setVisibility(View.VISIBLE);

            }

        } else if (requestCode == RC_PHOTO_PREVIEW) {
            mPhotosSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedPhotos(data));
            //图片预览,兼具编辑添加删除的地方
            if(mPhotosSnpl.getData().size()>0){
                ll_type.setVisibility(View.GONE);
                mPhotosSnpl.setVisibility(View.VISIBLE);
                rl_pay.setVisibility(View.VISIBLE);
            }else  if(mPhotosSnpl.getData().size()==0){
                ll_type.setVisibility(View.VISIBLE);
                mPhotosSnpl.setVisibility(View.GONE);
                rl_pay.setVisibility(View.GONE);
                trend_price = 0;
            }
        } else if (resultCode == RESULT_OK & requestCode == PictureConfig.CHOOSE_REQUEST) {
            List<LocalMedia> mediaList = PictureSelector.obtainMultipleResult(data);
          //  video_url = getRealPathFromUri(Uri.parse(mediaList.get(0).getPath()));
            if(mediaList.get(0).getPath().contains("content")){
                video_url = getRealPathFromUri(Uri.parse(mediaList.get(0).getPath()));
            }else{
                video_url=mediaList.get(0).getPath();
            }
            Glide.with(PublishTrendActivity.this).load(video_url).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    //选择完视频,并且点击确定后,隐藏type
                    ll_type.setVisibility(View.GONE);
                    rl_video.setVisibility(View.VISIBLE);
                    rl_video_into.setVisibility(View.VISIBLE);
                    rl_pay.setVisibility(View.VISIBLE);
                    MyStandardVideoController mb = new MyStandardVideoController(PublishTrendActivity.this);
                    mb.getThumb().setImageDrawable(resource);
                    mb.getmFullScreenButton().setVisibility(View.GONE);
                    setViewInfo(resource, video_player, rl_video_into);
                    video_player.setVideoController(mb);
                    video_player.setScreenScale(VideoView.SCREEN_SCALE_CENTER_CROP);
                    video_player.setUsingSurfaceView(false);
                    video_player.setUrl(video_url);
                }
            });
        }else if(requestCode == TOPIC_CHOSE){
            if(data==null){
                return;
            }
            if(data.getSerializableExtra("TOPIC")!=null){
                Topic topic= (Topic) data.getSerializableExtra("TOPIC");
                rl_not_chose_talk.setVisibility(View.GONE);
                rl_show_talk.setVisibility(View.VISIBLE);
                tv_talk.setText("#"+topic.getTitle()+"#");
            }
        }
    }

    @Override
    public void publish() {
        ToastUtils.showT("发布成功");
        showLoading();
        finish();
    }


    @Override
    public void setTempKeysForCos(QCloudData data) {
        cosXmlService = TxPicturePushUtils.getInstance().initQCloud(data);
    }

    private String getUrls(ArrayList<String> urls) {
        String temp = "";
        if (urls.size() == 0) {
            return "";
        }
        for (int i = 0; i < urls.size(); i++) {
            if (i < urls.size() - 1) {
                temp = temp + urls.get(i) + ",";
            } else {
                temp = temp + urls.get(i);
            }
        }
        return temp;
    }

    public String getViewInfo(Drawable resource) {
        int srcWidth = resource.getIntrinsicWidth();     // 源图宽度
        int srcHeight = resource.getIntrinsicHeight();    // 源图高度
        if (srcWidth == 0 | srcHeight == 0) {
            return "";
        }

        if (srcWidth == srcHeight) {
            return "3";
        }

        if (srcWidth > srcHeight) {
            return "1";
        }

        if (srcWidth < srcHeight) {
            return "2";
        }
        return "";
    }

    public String getRealPathFromUri(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = PublishTrendActivity.this.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    public void starPush(int i) {
        switch (i) {
            case 1:
                mPresenter.publish(type, et_title.getText().toString(), getUrls(urls), getUrls(blur_urls), "", single_display_type, trend_price + "",tv_talk.getText().toString());
                break;
            case 2:
                mPresenter.publish(type, et_title.getText().toString(), getUrls(urls), getUrls(blur_urls), "", "", trend_price + "",tv_talk.getText().toString());
                break;
            case 3:
                mPresenter.publish(type, et_title.getText().toString(), getUrls(urls), "", "", single_display_type, "0",tv_talk.getText().toString());
                break;
            case 4:
                mPresenter.publish(type, et_title.getText().toString(), getUrls(urls), "", "", "", "0",tv_talk.getText().toString());
                break;
            case 5:
                mPresenter.publish(type, et_title.getText().toString(), getUrls(urls), getUrls(blur_urls), "", single_display_type, trend_price + "",tv_talk.getText().toString());
                break;
            case 6:
                mPresenter.publish(type, et_title.getText().toString(), getUrls(urls), getUrls(blur_urls), "", "", trend_price + "",tv_talk.getText().toString());
                break;
            case 7:
                mPresenter.publish(type, et_title.getText().toString(), getUrls(urls), "", video_url, single_display_type, trend_price + "",tv_talk.getText().toString());
                break;
            case 8:
                mPresenter.publish(type, et_title.getText().toString(), getUrls(urls), "", video_url, single_display_type, trend_price + "",tv_talk.getText().toString());
                break;

        }
    }

    private void setViewInfo(Drawable resource, VideoView videoView, RelativeLayout rl_video_into) {

        int srcWidth = resource.getIntrinsicWidth();     // 源图宽度
        int srcHeight = resource.getIntrinsicHeight();    // 源图高度

        if (srcWidth == 0 | srcHeight == 0) {
            return;
        }

        if (srcWidth == srcHeight) {
            Log.e("TAG", "正方形");
            videoView.getLayoutParams().width = DipPxUtils.dip2px(this, 224);
            videoView.getLayoutParams().height = DipPxUtils.dip2px(this, 224);
            rl_video_into.getLayoutParams().width = DipPxUtils.dip2px(this, 224);
            rl_video_into.getLayoutParams().height = DipPxUtils.dip2px(this, 224);
            return;
        }

        if (srcWidth > srcHeight) {
            Log.e("TAG", "长方形横");
            videoView.getLayoutParams().width = DipPxUtils.dip2px(this, 345);
            videoView.getLayoutParams().height = DipPxUtils.dip2px(this, 201);
            rl_video_into.getLayoutParams().width = DipPxUtils.dip2px(this, 345);
            rl_video_into.getLayoutParams().height = DipPxUtils.dip2px(this, 201);
            return;
        }

        if (srcWidth < srcHeight) {
            Log.e("TAG", "长方形竖");
            videoView.getLayoutParams().width = DipPxUtils.dip2px(this, 224);
            videoView.getLayoutParams().height = DipPxUtils.dip2px(this, 300);
            rl_video_into.getLayoutParams().width = DipPxUtils.dip2px(this, 224);
            rl_video_into.getLayoutParams().height = DipPxUtils.dip2px(this, 300);
            return;
        }
    }



}
