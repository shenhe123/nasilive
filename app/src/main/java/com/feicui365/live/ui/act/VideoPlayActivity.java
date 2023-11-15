package com.feicui365.live.ui.act;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;

import com.dueeeke.videoplayer.player.VideoView;
import com.tencent.qcloud.ugckit.UGCKitConstants;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.contract.LoginContract;
import com.feicui365.live.presenter.LoginPresenter;
import com.feicui365.live.widget.MyStandardVideoController;


import butterknife.BindView;

public class VideoPlayActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.View {
    VideoView video_player;
    String video_path;

    @BindView(R.id.rl_back2)
    RelativeLayout rl_back;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_play;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //加载so文件
        hideTitle(true);
        video_path = getIntent().getStringExtra(UGCKitConstants.VIDEO_PATH);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        video_player = findViewById(R.id.video_player);


        loadVideo(video_path);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != video_player) {
                    video_player.release();

                }
                finish();
            }
        });
    }

    @Override
    public void onError(Throwable throwable) {

    }

    public void loadVideo(String path) {
        video_player.setUrl(path);
        MyStandardVideoController mb = new MyStandardVideoController(VideoPlayActivity.this);

        mb.getmFullScreenButton().setVisibility(View.GONE);

        video_player.setVideoController(mb);
        video_player.setScreenScale(VideoView.SCREEN_SCALE_DEFAULT);
        video_player.setUsingSurfaceView(false);
        video_player.setUrl(path);
        video_player.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        video_player.pause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != video_player) {
            video_player.release();
        }

    }
}
