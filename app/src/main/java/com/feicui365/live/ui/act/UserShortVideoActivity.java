package com.feicui365.live.ui.act;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.MyUserInstance;
import com.gyf.immersionbar.ImmersionBar;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.dialog.UserShortCommentDialog;
import com.feicui365.live.interfaces.OnFaceClickListener;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.Comment;
import com.feicui365.live.model.entity.ShortVideo;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.ImChatFacePagerAdapter;

import com.feicui365.live.ui.adapter.ShortVideoAdapter;
import com.feicui365.live.widget.CommentPopup;
import com.feicui365.live.widget.listvideo.ListVideoView;
import com.feicui365.live.widget.pagerlayoutmanager.OnViewPagerListener;
import com.feicui365.live.widget.pagerlayoutmanager.ViewPagerLayoutManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class UserShortVideoActivity extends BaseMvpActivity<HomePresenter> implements HomeContract.View, OnViewPagerListener, ShortVideoAdapter.CommentListener {
    @BindView(R.id.refreshLayout)
    RefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.iv_emoji)
    ImageView iv_emoji;
    @BindView(R.id.back_video)
    View back_video;
    @BindView(R.id.rl_back2)
    RelativeLayout rl_back2;

    private ViewPagerLayoutManager pagerLayoutManager;
    private ShortVideoAdapter videoAdapter;
    private int position;
    private ArrayList<ShortVideo> data;
    private int page;
    private String authorid;
    private String is_refresh = "";
    HashMap<String, ArrayList> hs_attend = new HashMap();
    public UserShortCommentDialog inputDialogFragment2;
    CommentPopup commentPopup;
    private String video_id="";
    public boolean is_show_input = false;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_user_short_video;
    }

    @Override
    protected void initData() {

    }


    private ZanListener zanListener;


    public interface ZanListener {

        void onZanClick();


    }

    public void setZanListener(ZanListener zanListener) {
        this.zanListener = zanListener;
    }

    @Override
    protected void initView() {

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 1);
        data = (ArrayList<ShortVideo>) intent.getSerializableExtra("data");
        page = intent.getIntExtra("page", 1);
        is_refresh = intent.getStringExtra("is_refresh");
        authorid = intent.getStringExtra("authorid");
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);

        rl_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("data", (Serializable) videoAdapter.dataList);
                setResult(1003, intent);
                finish();
            }
        });
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Fresco.initialize(this);

        rl_title.setVisibility(View.GONE);

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");


        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);


        recycler.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent e) {
                final int action = e.getActionMasked();
                switch (action) {
                    case MotionEvent.ACTION_DOWN://手指按下
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    case MotionEvent.ACTION_MOVE://手指移动（从手指按下到抬起 move多次执行）
                        break;
                    case MotionEvent.ACTION_UP://手指抬起
                        if (recycler.getScrollState() == RecyclerView.SCROLL_STATE_DRAGGING &&
                                pagerLayoutManager.findSnapPosition() == 0) {
                            if (recycler.getChildAt(0).getY() == 0 &&
                                    recycler.canScrollVertically(1)) {//下滑操作
                                recycler.stopScroll();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return false;
            }
        });


        pagerLayoutManager = new ViewPagerLayoutManager(this, LinearLayoutManager.VERTICAL);
        pagerLayoutManager.setOnViewPagerListener(this);
        videoAdapter = new ShortVideoAdapter(this, recycler);

        recycler.setLayoutManager(pagerLayoutManager);
        recycler.setAdapter(videoAdapter);
        videoAdapter.addData(data);
        setAttend(data);
        videoAdapter.setCommentListener(this);
        pagerLayoutManager.scrollToPosition(position);

        back_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyUserInstance.getInstance().visitorIsLogin()) {
                    showInput(false, data.get(position).getId());
                }
            }
        });

        iv_emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyUserInstance.getInstance().visitorIsLogin()) {
                    showInput(true, data.get(position).getId());
                }
            }
        });
        View decorView = getWindow().getDecorView();
        View contentView = findViewById(Window.ID_ANDROID_CONTENT);
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));

    }


    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                decorView.getWindowVisibleDisplayFrame(r);
                int height = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
                int diff = height - r.bottom;
                if (diff > 0) {
                    if(!is_show_input){
                        if (inputDialogFragment2 != null) {
                            is_show_input = true;
                            inputDialogFragment2.openInput();
                        }
                    }
                } else {
                    //这个时候是收进去的
                    //只有确定软键盘是打开的时候
                    if (is_show_input) {
                        is_show_input = false;
                    }
                }
            }
        };
    }


    @Override
    public void setAnchorWorks(ArrayList<ShortVideo> bean) {
        if (bean.size() > 0) {
            position++;
            videoAdapter.addData(bean);
            videoAdapter.notifyDataSetChanged();
            pagerLayoutManager.scrollToPosition(position);
            setAttend(videoAdapter.dataList);
        }
        refreshLayout.finishLoadMore(true);
    }

    @Override
    public void onError(Throwable throwable) {
        if (page > 1) {
            page--;
        }
        refreshLayout.finishLoadMore(false);
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitComplete() {
        ImmersionBar.with(this).statusBarDarkFont(false).init();
        playVideo(position);
    }

    @Override
    public void onPageSelected(int position, boolean isBottom) {
        this.position = position;
        playVideo(position);
    }

    @Override
    public void onPageRelease(boolean isNext, int position) {
        releaseVideo(position);
    }

    private void playVideo(int position) {
        if (recycler == null) {
            return;
        }
        final ShortVideoAdapter.VideoViewHolder viewHolder = (ShortVideoAdapter.VideoViewHolder) recycler.findViewHolderForLayoutPosition(position);
        ShortVideo videoEntity = videoAdapter.getDataByPosition(position);
        if (viewHolder != null && !viewHolder.videoView.isPlaying()) {
            viewHolder.videoView.setVideoPath(videoEntity.getPlay_url());
            viewHolder.videoView.getMediaPlayer().setOnInfoListener(new IMediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
                    if (what == IMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                        viewHolder.sdvCover.setVisibility(View.INVISIBLE);
                    }
                    return false;
                }
            });
            viewHolder.videoView.setOnVideoProgressUpdateListener(new ListVideoView.OnVideoProgressListener() {
                @Override
                public void onProgress(float progress, long currentTime) {
                    Log.d("youzai", "progresss---->" + progress + "\t" + "currentTime---->" + currentTime);
                }
            });
            viewHolder.videoView.setLooping(true);
            viewHolder.videoView.prepareAsync();
        }
    }

    private void releaseVideo(int position) {
        ShortVideoAdapter.VideoViewHolder viewHolder = (ShortVideoAdapter.VideoViewHolder) recycler.findViewHolderForLayoutPosition(position);
        if (viewHolder != null) {
            viewHolder.videoView.stopPlayback();
            viewHolder.sdvCover.setVisibility(View.VISIBLE);
        }
    }


    public boolean action = false;

    @Override
    protected void onPause() {
        super.onPause();
        pauseVideo();
        action = false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        restartVideo();

    }

    @Override
    protected void onDestroy() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        super.onDestroy();
        //页面销毁时去掉常亮flag

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data", (Serializable) videoAdapter.dataList);
        setResult(1003, intent);
        super.onBackPressed();
    }

    /**
     * 暂停视频
     */
    private void pauseVideo() {
        int snapPosition = pagerLayoutManager.findSnapPosition();
        if (snapPosition >= 0) {
            ShortVideoAdapter.VideoViewHolder viewHolder =
                    (ShortVideoAdapter.VideoViewHolder) recycler.findViewHolderForLayoutPosition(snapPosition);
            if (viewHolder != null) {
                viewHolder.videoView.pause();
            }
        }
    }

    /**
     * 暂停后重新播放视频
     */
    private void restartVideo() {
        int snapPosition = pagerLayoutManager.findSnapPosition();
        if (snapPosition >= 0) {
            ShortVideoAdapter.VideoViewHolder viewHolder =
                    (ShortVideoAdapter.VideoViewHolder) recycler.findViewHolderForLayoutPosition(snapPosition);
            if (viewHolder != null) {
                viewHolder.videoView.start();
            }
        }
    }

    String lastid = "";
    String comment_count = "0";

    @Override
    public void onClick(ShortVideo videoEntity) {
        showLoading();
        mPresenter.getComments(lastid, videoEntity.getId());
        comment_count = videoEntity.getComment_count();
        video_id=videoEntity.getId();
    }

    @Override
    public void onVideoClick() {
        clickVideo();
    }

    @Override
    public void onZanClick(String is) {
        if (is == null) {
            return;
        }
        if (is.equals("") || is.equals("0")) {
            return;
        }

        if (zanListener != null) {
            zanListener.onZanClick();
        }
    }

    @Override
    public void onAvatarClick(UserRegist author) {
        startActivityForResult(new Intent(this, ShortVideoCenterActivity.class).putExtra("authorInfo", author), 0x002);
    }

    /**
     * 点击暂停/回复
     */
    private void clickVideo() {
        int snapPosition = pagerLayoutManager.findSnapPosition();
        if (snapPosition >= 0) {
            ShortVideoAdapter.VideoViewHolder viewHolder =
                    (ShortVideoAdapter.VideoViewHolder) recycler.findViewHolderForLayoutPosition(snapPosition);
            if (viewHolder != null) {
                if (viewHolder.videoView.isPlaying()) {
                    viewHolder.videoView.pause();
                    viewHolder.iv_pause.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.videoView.start();
                    viewHolder.iv_pause.setVisibility(View.GONE);
                }

            }
        }
    }

    @Override
    public void getComments(BaseResponse<ArrayList<Comment>> bean) {

        if (bean == null) {
            return;
        }

        if (bean.isSuccess()) {
            ArrayList<Comment> comments = bean.getData();
            commentPopup = new CommentPopup(this, comments, comment_count,video_id);
            commentPopup.setUpDateNum(new CommentPopup.UpDateNum() {
                @Override
                public void onUpDateNum(String num) {
                    updateCommentNum(num);
                }
            });
            new XPopup.Builder(this)

                    .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                    .setPopupCallback(new XPopupCallback() {
                        @Override
                        public void onCreated() {

                        }

                        @Override
                        public void beforeShow() {

                        }

                        @Override
                        public void onShow() {

                        }

                        @Override
                        public void onDismiss() {
                            commentPopup = null;
                        }

                        @Override
                        public boolean onBackPressed() {
                            return false;
                        }
                    })
                    .asCustom(commentPopup/*.enableDrag(false)*/)
                    .show();
            comment_count = "0";
        }
    }


    @Override
    public void onAttend(String author_id, String type) {
        //iterating over keys only
        for (String key : hs_attend.keySet()) {
            if (key.equals(author_id)) {
                for (int i = 0; i < hs_attend.get(key).size(); i++) {
                    videoAdapter.dataList.get(Integer.parseInt(hs_attend.get(key).get(i).toString())).getAuthor().setIsattent(Integer.valueOf(type));
                }
                break;
            }
        }
        videoAdapter.notifyDataSetChanged();
    }

    private void setAttend(List<ShortVideo> beans) {
        hs_attend.clear();
        //初始化ID数组
        ArrayList temp_Ids = new ArrayList();
        for (int i = 0; i < beans.size(); i++) {
            temp_Ids.add(beans.get(i).getAuthor().getId());
        }
        //给ID去重
        HashSet set = new HashSet(temp_Ids);
        temp_Ids.clear();
        temp_Ids.addAll(set);
        //用去重的ID遍历 数据集
        for (int i = 0; i < temp_Ids.size(); i++) {
            ArrayList video_postion = new ArrayList();

            for (int y = 0; y < beans.size(); y++) {
                //当ID相同,加入对应的POSTION
                if (temp_Ids.get(i).equals(beans.get(y).getAuthor().getId())) {
                    video_postion.add(y);
                }
            }
            //加入HASHMAP,作为键值对储存
            hs_attend.put(temp_Ids.get(i).toString(), video_postion);

        }
    }

    public void updateCommentNum(String num) {
        View view = recycler.getLayoutManager().findViewByPosition(position);
        TextView tv_pinglun_num = view.findViewById(R.id.tv_pinglun_num);
        tv_pinglun_num.setText(num);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0x002 && resultCode == 0x001) {
            //当LoginActivity finish后，就会调用这里，data为值
            String author_id = data.getStringExtra("author_id");
            String type = data.getStringExtra("type");

            onAttend(author_id, type);


        }
        super.onActivityResult(requestCode, resultCode, data);
    }







    private void showInput(boolean is_face, String video_id) {
        if (null != inputDialogFragment2) {
            inputDialogFragment2.dismiss();
        }
        UserShortCommentDialog dialogFragment = new UserShortCommentDialog(video_id);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.VIDEO_FACE_OPEN, is_face);
        dialogFragment.setArguments(bundle);
        inputDialogFragment2 = dialogFragment;
        inputDialogFragment2.show(getSupportFragmentManager(), "myAlert");
        inputDialogFragment2.setOnComentSendListener(new UserShortCommentDialog.OnComentSendListener() {
            @Override
            public void onSendSucess(Comment comment) {
                data.get(position).setComment_count((Integer.parseInt(data.get(position).getComment_count()) + 1) + "");
                if (pagerLayoutManager != null) {
                    int snapPosition = pagerLayoutManager.findSnapPosition();
                    if (snapPosition >= 0) {
                        ShortVideoAdapter.VideoViewHolder viewHolder =
                                (ShortVideoAdapter.VideoViewHolder) recycler.findViewHolderForLayoutPosition(snapPosition);
                        viewHolder.tv_pinglun_num.setText((Integer.parseInt(data.get(position).getComment_count()) + 1) + "");
                    }
                }
                inputDialogFragment2.dismiss();
            }
        });
    }


    /**
     * 初始化表情控件
     */
    public int mFaceViewHeight;//表情控件高度

    public View initFaceView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.view_chat_face, null);
        v.measure(0, 0);
        mFaceViewHeight = v.getMeasuredHeight();
        v.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputDialogFragment2 != null) {
                    inputDialogFragment2.sendMessage();
                }
            }
        });
        final RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(10);
        ImChatFacePagerAdapter adapter = new ImChatFacePagerAdapter(this, new OnFaceClickListener() {
            @Override
            public void onFaceClick(String str, int faceImageRes) {
                if (inputDialogFragment2 != null) {
                    inputDialogFragment2.onFaceClick(str, faceImageRes);
                }
            }

            @Override
            public void onFaceDeleteClick() {
                if (inputDialogFragment2 != null) {
                    inputDialogFragment2.onFaceDeleteClick();
                }

            }
        });
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0, pageCount = adapter.getCount(); i < pageCount; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.view_chat_indicator, radioGroup, false);
            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioGroup.addView(radioButton);
        }
        return v;
    }
}
