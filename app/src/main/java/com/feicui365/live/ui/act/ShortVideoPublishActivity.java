package com.feicui365.live.ui.act;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.UploadContract;
import com.feicui365.live.interfaces.OnVideoThumbUpLoadPicsListener;
import com.feicui365.live.interfaces.OnVideoUpLoadPicsListener;
import com.feicui365.live.model.entity.Topic;
import com.feicui365.live.util.StringUtil;
import com.feicui365.live.util.UploadUtils;
import com.feicui365.live.util.WordUtil;
import com.feicui365.live.widget.Dialogs;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.presenter.UpLoadPresenter;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.nasinet.utils.DipPxUtils;


import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class ShortVideoPublishActivity extends BaseMvpActivity<UpLoadPresenter> implements UploadContract.View {
    @BindView(R.id.iv_video)
    ImageView iv_video;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.iv_send)
    ImageView iv_send;

    @BindView(R.id.rl_not_chose_talk)
    RelativeLayout rl_not_chose_talk;
    @BindView(R.id.rl_show_talk)
    RelativeLayout rl_show_talk;
    @BindView(R.id.tv_talk)
    TextView tv_talk;
    @BindView(R.id.ic_topic_clear)
    ImageView ic_topic_clear;
    private static final int TOPIC_CHOSE = 33;
    String temp_video_url;
    String video_corver;
    String video_duration;

    String video_upload_path = "";
    String cover_upload_path = "";
    private RoundedCorners roundedCorners;
    UploadUtils mUploadUtils;
    private final int UPLOAD_VIDEO_FINISH = 10001;
    private final int UPLOAD_PIC_FINISH = 10002;
    private final int UPLOAD_ERROR = 10003;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPLOAD_VIDEO_FINISH:
                    hideLoading();
                    mPresenter.publish(et_content.getText().toString(), cover_upload_path, video_upload_path, tv_talk.getText().toString());

                    break;
                case UPLOAD_PIC_FINISH:
                    showLoading();
                    upLoadVideo(temp_video_url);
                    break;
                case UPLOAD_ERROR:
                    hideLoading();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish_short_video;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setTitle(WordUtil.getString(R.string.Publish) + WordUtil.getString(R.string.ShortVideo));
        mPresenter = new UpLoadPresenter();
        mPresenter.attachView(this);
        mUploadUtils = new UploadUtils(this);
        mUploadUtils.getConfig();
        temp_video_url = getIntent().getStringExtra(UGCKitConstants.VIDEO_PATH);

        video_duration = getIntent().getStringExtra(UGCKitConstants.VIDEO_RECORD_DURATION);
        roundedCorners = new RoundedCorners(DipPxUtils.dip2px(this, 5));
        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().bitmapTransform(roundedCorners).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL)).load(temp_video_url).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                final Bitmap bitmap = drawableToBitmap(resource);
                File imageFile = saveMyBitmap(((BitmapDrawable) resource).getBitmap(), StringUtil.getRandomName());
                video_corver = imageFile.getAbsolutePath();

                iv_video.setImageBitmap(bitmap);
            }
        });


    }


    @OnClick({R.id.iv_send, R.id.iv_video, R.id.rl_not_chose_talk, R.id.ic_topic_clear})
    public void onClick(View v) {
        if (isFastClick()) {
            return;
        }
        switch (v.getId()) {
            case R.id.iv_video:
                Intent intent = new Intent(ShortVideoPublishActivity.this, VideoPlayActivity.class);
                intent.putExtra(UGCKitConstants.VIDEO_PATH, temp_video_url);
                startActivity(intent);
                break;
            case R.id.iv_send:
                if (et_content.getText().toString().equals("")) {
                    ToastUtils.showT(WordUtil.getString(R.string.Input_Title));
                    return;
                }
                if (video_upload_path.equals("") & cover_upload_path.equals("")) {
                    //保存视频封面
                    showLoading();
                    upLoadCorver(video_corver);
                } else {
                    mPresenter.publish(et_content.getText().toString(), cover_upload_path, video_upload_path, tv_talk.getText().toString());
                }
                break;

            case R.id.rl_not_chose_talk:
                Intent intent2 = new Intent(context, SearchTalkActivity.class);
                startActivityForResult(intent2, TOPIC_CHOSE);
                break;
            case R.id.ic_topic_clear:
                rl_not_chose_talk.setVisibility(View.VISIBLE);
                rl_show_talk.setVisibility(View.GONE);
                tv_talk.setText("");
                break;
        }
    }

    private void upLoadCorver(String url) {
        if (mUploadUtils == null) {
            return;
        }
        showLoading();
        mUploadUtils.uploadVideoThumb(url, new OnVideoThumbUpLoadPicsListener() {
            @Override
            public void onThumbUrls(String result) {
                cover_upload_path = result;

                Message message = new Message();
                message.what = UPLOAD_PIC_FINISH;
                mHandler.sendMessage(message);
            }

            @Override
            public void onError() {
                Message message = new Message();
                message.what = UPLOAD_ERROR;
                mHandler.sendMessage(message);
            }
        });
    }

    private void upLoadVideo(String url) {
        if (mUploadUtils == null) {
            return;
        }

        mUploadUtils.uploadVideo(url, new OnVideoUpLoadPicsListener() {
            @Override
            public void onVideoUrls(String result) {
                video_upload_path = result;

                Message message = new Message();
                message.what = UPLOAD_VIDEO_FINISH;
                mHandler.sendMessage(message);


            }

            @Override
            public void onError() {
                Message message = new Message();
                message.what = UPLOAD_ERROR;
                mHandler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == TOPIC_CHOSE) {
            if (data == null) {
                return;
            }
            if (data.getSerializableExtra("TOPIC") != null) {
                Topic topic = (Topic) data.getSerializableExtra("TOPIC");
                rl_not_chose_talk.setVisibility(View.GONE);
                rl_show_talk.setVisibility(View.VISIBLE);
                tv_talk.setText("#" + topic.getTitle() + "#");
            }
        }
    }


    @Override
    public void onError(Throwable throwable) {
        ToastUtils.showT(WordUtil.getString(R.string.Publish_Fail));
        finish();
    }



/*
    @Override
    public void onClick(TXUGCPublishTypeDef.TXPublishResult result) {
        video_upload_path = result.videoURL;
        cover_upload_path = result.coverURL;

        mPresenter.publish(et_content.getText().toString(), cover_upload_path, video_upload_path,tv_talk.getText().toString());

    }*/

    @Override
    public void publish(BaseResponse bean) {
        ToastUtils.showT(WordUtil.getString(R.string.Publish_Success));
        finish();
    }

    private Dialog dialog;

    @Override
    public void showLoading() {
        hideLoading();
        dialog = Dialogs.createLoadingDialog(this);
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    //保存文件到指定路径
    public File saveMyBitmap(Bitmap bitmap, String name) {
        String sdCardDir = Environment.getExternalStorageDirectory() + "/DCIM/";
        File appDir = new File(sdCardDir, name);
        if (!appDir.exists()) {//不存在
            appDir.mkdir();
        }
        String fileName = name + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        sendBroadcast(intent);
        // Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
        return file;
    }


    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

}
