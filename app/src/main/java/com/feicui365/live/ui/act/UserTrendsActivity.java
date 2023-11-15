package com.feicui365.live.ui.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dueeeke.videoplayer.player.VideoView;
import com.dueeeke.videoplayer.player.VideoViewManager;
import com.lxj.xpopup.XPopup;
import com.feicui365.live.R;
import com.feicui365.live.base.BaseMvpActivity;
import com.feicui365.live.base.Constants;
import com.feicui365.live.contract.HomeContract;
import com.feicui365.live.dialog.ShareDialog2;
import com.feicui365.live.dialog.UserTrendCommentDialog;
import com.feicui365.live.interfaces.OnFaceClickListener;
import com.feicui365.live.model.entity.BaseResponse;
import com.feicui365.live.model.entity.MomentDetail;
import com.feicui365.live.model.entity.Trend;
import com.feicui365.live.presenter.HomePresenter;
import com.feicui365.live.ui.adapter.ImChatFacePagerAdapter;
import com.feicui365.live.ui.adapter.UserTrendAdapter;
import com.feicui365.live.util.FormatCurrentData;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.dialog.UnlockDialog;
import com.feicui365.live.widget.MyBGANinePhotoLayout;
import com.feicui365.live.widget.MyStandardVideoController;
import com.feicui365.nasinet.utils.DipPxUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserTrendsActivity extends BaseMvpActivity<HomePresenter> implements View.OnClickListener, HomeContract.View, BGANinePhotoLayout.Delegate, UserTrendAdapter.OnItemClickListener {
    @BindView(R.id.cv_trends)
    RecyclerView cv_trends;
    @BindView(R.id.rl_back2)
    RelativeLayout rl_back;

    @BindView(R.id.civ_avatar)
    CircleImageView civ_avatar;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.iv_user_level)
    ImageView iv_user_level;

    @BindView(R.id.age_tv)
    TextView age_tv;


    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_comment)
    TextView tv_comment;
    @BindView(R.id.iv_emoji)
    ImageView iv_emoji;
    Trend my_trend;

    UserTrendAdapter userTrendAdapter;
    //传进来的列表
    ArrayList<MomentDetail> into_comments;


    @BindView(R.id.rl_zan)
    RelativeLayout rl_zan;
    @BindView(R.id.iv_zan)
    ImageView iv_zan;
    @BindView(R.id.rl_collection)
    RelativeLayout rl_collection;
    @BindView(R.id.iv_collection)
    ImageView iv_collection;
    @BindView(R.id.rl_share)
    RelativeLayout rl_share;
    @BindView(R.id.iv_share)
    ImageView iv_share;

    public boolean is_show_input = false;
    @BindView(R.id.tv_trend_title)
    TextView tv_trend_title;

    @BindView(R.id.iv_single_pic)
    ImageView iv_single_pic;
    @BindView(R.id.iv_single_pic_fufei)
    ImageView iv_single_pic_fufei;
    @BindView(R.id.npl_item_moment_photos)
    MyBGANinePhotoLayout npl_item_moment_photos;
    @BindView(R.id.ll_others)
    RelativeLayout ll_others;
    @BindView(R.id.player)
    VideoView player;
    @BindView(R.id.rl_single_pic)
    RelativeLayout rl_single_pic;

    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.iv_single_pic_z_tv)
    TextView iv_single_pic_z_tv;
    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;


    String lastid = "";
    public UserTrendCommentDialog inputDialogFragment3;
    public int mFaceViewHeight;//表情控件高度
    private String collect = "0";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_trend;
    }

    @Override
    protected void initData() {
        tv_name.setText(my_trend.getUser().getNick_name());
        age_tv.setText(my_trend.getUser().getProfile().getAge());
        if (my_trend.getUser().getProfile().getGender()==1) {
            age_tv.setCompoundDrawablesWithIntrinsicBounds(this.getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);
            age_tv.setBackgroundResource(R.drawable.shape_corner_age_boy);
        }
        Glide.with(this).load(my_trend.getUser().getAvatar()).into(civ_avatar);
        Glide.with(this).load(LevelUtils.getUserLevel(my_trend.getUser().getUser_level())).into(iv_user_level);


        if (my_trend.getTitle().equals("")) {
            tv_trend_title.setVisibility(View.GONE);
        } else {



            if(my_trend.getTopic()!=null){
                SpannableString spString = new SpannableString(my_trend.getTopic());
                spString.setSpan(new ClickableSpan() {
                    @Override
                    public void onClick(@NonNull View widget) {
                        Intent intent=new Intent(context, TopicMomentVideoActivity.class);
                        intent.putExtra("topic",spString.toString());
                        startActivity(intent);
                    }

                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                        super.updateDrawState(ds);
                        ds.setUnderlineText(false);
                        ds.setTextSize(DipPxUtils.sp2px(context,15));//设置字体大小
                        ds.setColor(context.getResources().getColor(R.color.color_FF6273));//设置字体颜色
                    }
                }, 0, my_trend.getTopic().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_trend_title.setText(spString);
                tv_trend_title.setMovementMethod(LinkMovementMethod.getInstance());
                tv_trend_title.append(" "+my_trend.getTitle());
            }else {
                tv_trend_title.setText(my_trend.getTitle());
            }




        }

        tv_time.setText(FormatCurrentData.getTimeRange2(my_trend.getCreate_time()));
        switch (my_trend.getType()) {
            //文字
            case "1":
                rl_single_pic.setVisibility(View.GONE);
                ll_others.setVisibility(View.GONE);
                break;
            //图片
            case "2":
                //图片合集
                if (my_trend.getImage_url() == null) {
                    return;
                }
                ArrayList pic_unlock = new ArrayList();
                String[] images = my_trend.getImage_url().split(",");
                for (String image : images) {
                    pic_unlock.add(image);
                }

                if (pic_unlock.size() == 1) {

                    rl_single_pic.setVisibility(View.VISIBLE);
                    ll_others.setVisibility(View.VISIBLE);
                    ArrayList pic_lock = new ArrayList();

                    if ((TextUtils.isEmpty(my_trend.getUnlocked()) || my_trend.getUnlocked().equals("0")) &&
                            !my_trend.getUnlock_price().equals("0")) {
                        if (null == my_trend.getBlur_image_url()) {
                            Glide.with(this)
                                    .load(R.mipmap.logo)
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            iv_single_pic_fufei.setVisibility(View.VISIBLE);
                                            iv_single_pic_z_tv.setVisibility(View.VISIBLE);
                                            iv_single_pic_z_tv.setText(my_trend.getUnlock_price() + "金币");
                                            iv_single_pic_fufei.setImageResource(R.mipmap.nofufei2);
                                            setViewInfo(resource, iv_single_pic, null, rl_single_pic);
                                        }


                                    });
                        } else {
                            String[] images_lock = my_trend.getBlur_image_url().split(",");
                            for (String image : images_lock) {
                                pic_lock.add(image);
                            }
                            Glide.with(this)
                                    .load(pic_lock.get(0))
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            iv_single_pic_fufei.setVisibility(View.VISIBLE);
                                            iv_single_pic_z_tv.setVisibility(View.VISIBLE);
                                            iv_single_pic_z_tv.setText(my_trend.getUnlock_price() + "金币");
                                            iv_single_pic_fufei.setImageResource(R.mipmap.nofufei2);
                                            setViewInfo(resource, iv_single_pic, null, rl_single_pic);
                                        }


                                    });
                        }

                    } else {
                        Glide.with(this)
                                .load(pic_unlock.get(0))
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        iv_single_pic_fufei.setVisibility(View.GONE);
                                        iv_single_pic_z_tv.setVisibility(View.GONE);
                                        setViewInfo(resource, iv_single_pic, null, rl_single_pic);
                                    }


                                });
                    }


                } else if (pic_unlock.size() > 1) {
                    ll_others.setVisibility(View.VISIBLE);
                    rl_single_pic.setVisibility(View.GONE);

                    if ((TextUtils.isEmpty(my_trend.getUnlocked()) || my_trend.getUnlocked().equals("0")) &&
                            !my_trend.getUnlock_price().equals("0")) {
                        ArrayList pic_lock = new ArrayList();
                        if (null == my_trend.getBlur_image_url()) {
                            for (int i = 0; i < pic_unlock.size(); i++) {
                                pic_lock.add("");
                            }
                            npl_item_moment_photos.setData(pic_lock, true, my_trend.getUnlock_price());


                        } else {
                            String[] images_lock = my_trend.getBlur_image_url().split(",");
                            for (String image : images_lock) {
                                pic_lock.add(image);
                            }

                            if (pic_lock.size() < pic_unlock.size()) {
                                for (int i = 0; i < (pic_lock.size() - pic_unlock.size()); i++) {
                                    pic_lock.add("");
                                }
                            }


                            npl_item_moment_photos.setData(pic_lock, true, my_trend.getUnlock_price());
                        }

                    } else {
                        npl_item_moment_photos.setData(pic_unlock, false, "");
                    }
                }

                break;
            //视频
            case "3":

                ll_others.setVisibility(View.VISIBLE);

                //2,如果未付费,显示付费相关界面
                if ((TextUtils.isEmpty(my_trend.getUnlocked()) || my_trend.getUnlocked().equals("0")) &&
                        !my_trend.getUnlock_price().equals("0")) {

                    Glide.with(this)
                            .load(my_trend.getImage_url())
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    rl_single_pic.setVisibility(View.VISIBLE);
                                    //显示锁,显示金额
                                    iv_single_pic_fufei.setVisibility(View.VISIBLE);
                                    iv_single_pic_z_tv.setVisibility(View.VISIBLE);
                                    iv_single_pic_z_tv.setText(my_trend.getUnlock_price() + "金币");
                                    iv_single_pic_fufei.setImageResource(R.mipmap.nofufei2);
                                    //调整大小
                                    setViewInfo(resource, iv_single_pic, null, rl_single_pic);
                                }
                            });


                } else {
                    //3,如果付费,直接显示基本界面
                    Glide.with(this)
                            .load(my_trend.getImage_url())
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    //已经付费屏蔽外层界面
                                    rl_single_pic.setVisibility(View.GONE);
                                    iv_single_pic_z_tv.setVisibility(View.GONE);
                                    iv_single_pic_fufei.setVisibility(View.GONE);
                                    //调整大小
                                    setViewInfo(resource, iv_single_pic, player, rl_single_pic);
                                    //显示播放界面
                                    player.setVisibility(View.VISIBLE);
                                    player.setUsingSurfaceView(false);
                                    MyStandardVideoController mb = new MyStandardVideoController(UserTrendsActivity.this);
                                    mb.getmFullScreenButton().setVisibility(View.GONE);
                                    mb.getThumb().setImageDrawable(resource);
                                    player.setVideoController(mb);
                                    player.setUrl(my_trend.getVideo_url());

                                }
                            });
                }
                break;
        }


        rl_single_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((TextUtils.isEmpty(my_trend.getUnlocked()) || my_trend.getUnlocked().equals("0")) &&
                        !my_trend.getUnlock_price().equals("0")) {
                    if(MyUserInstance.getInstance().visitorIsLogin()) {
                        showUnlockDialog();
                    }
                } else {
                    startActivity(new Intent(UserTrendsActivity.this, PhotoInfoActivity.class).putExtra("data", my_trend.getPhotos()));
                }

            }
        });


        npl_item_moment_photos.setDelegate(new BGANinePhotoLayout.Delegate() {
            @Override
            public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int positions, String model, List<String> models) {
                if ((TextUtils.isEmpty(my_trend.getUnlocked()) || my_trend.getUnlocked().equals("0")) &&
                        !my_trend.getUnlock_price().equals("0")) {
                    if(MyUserInstance.getInstance().visitorIsLogin()) {
                        showUnlockDialog();
                    }
                } else {
                    ArrayList<String> arrayList = (ArrayList<String>) models;
                    startActivity(new Intent(UserTrendsActivity.this, PhotoInfoActivity.class).putExtra("data", arrayList).putExtra("positions", positions));

                }
            }
        });


        tv_comment_count.setText("最新评论 (" + my_trend.getComment_count() + ")");


    }


    @Override
    protected void initView() {
        mPresenter = new HomePresenter();
        mPresenter.attachView(this);
        my_trend = (Trend) getIntent().getSerializableExtra("momentData");
        if (my_trend == null) {
            finish();
        }
        hideTitle(true);
        refreshLayout.setEnableRefresh(false);
        iv_emoji.setOnClickListener(this);
        tv_comment.setOnClickListener(this);

        rl_back.setOnClickListener(this);
        rl_collection.setOnClickListener(this);
        iv_share.setOnClickListener(this);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        mPresenter.getMomentDetail(MyUserInstance.getInstance().getUserinfo().getId(), MyUserInstance.getInstance().getUserinfo().getToken(), my_trend.getId(), lastid);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.getMomentDetail(MyUserInstance.getInstance().getUserinfo().getId(), MyUserInstance.getInstance().getUserinfo().getToken(), my_trend.getId(), lastid);
            }

        });


        if (my_trend.getLiked() == null) {
            rl_zan.setOnClickListener(this);
        } else if (my_trend.getLiked().equals("1")) {
            iv_zan.setImageDrawable(this.getResources().getDrawable(R.mipmap.ic_zan_pre));
        } else {
            rl_zan.setOnClickListener(this);

        }
        if (my_trend.getCollected() == null) {
            collect = "0";

        } else {
            if (my_trend.getCollected().equals("0")) {
                collect = "0";
            } else {
                collect = "1";
                Glide.with(this).load(R.mipmap.ic_yishoucang_trend).into(iv_collection);
            }
        }

        rl_collection.setOnClickListener(this);

        civ_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserTrendsActivity.this, AnchorCenterActivity.class)
                        .putExtra("data", my_trend.getUid()));
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
                        if (inputDialogFragment3 != null) {
                            is_show_input = true;
                            inputDialogFragment3.openInput();
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
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("data", my_trend);
        setResult(1004, intent);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.putExtra("data", my_trend);
        setResult(1004, intent);
        VideoViewManager.instance().release();
        super.onDestroy();
    }

    @Override
    public void collectMoment(BaseResponse response) {
        if (collect.equals("0")) {
            Glide.with(this).load(R.mipmap.ic_yishoucang_trend).into(iv_collection);
            collect = "1";
            my_trend.setCollected("1");
        } else {
            Glide.with(this).load(R.mipmap.ic_shoucang_trend).into(iv_collection);
            collect = "0";
            my_trend.setCollected("0");
        }
    }

    @Override
    public void likeMoment(BaseResponse response) {
        Glide.with(this).load(R.mipmap.ic_zan_pre).into(iv_zan);
        my_trend.setLiked("1");
        my_trend.setLike_count((Integer.parseInt(my_trend.getLike_count()) + 1) + "");
    }


    @Override
    public void unlockMoment() {
        my_trend.setUnlocked("1");
        userTrendAdapter.setTrend_Item(my_trend);
        userTrendAdapter.notifyDataSetChanged();

        initData();
        initView();

    }

    @Override
    public void showMgs(String msg) {
        Toast.makeText(UserTrendsActivity.this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setMomentDetail(ArrayList<MomentDetail> bean) {
        refreshLayout.finishLoadMore();
        if (bean == null) {
            return;
        }

        if (bean.size() == 0 & !lastid.equals("")) {

            refreshLayout.setEnableLoadMore(false);
            return;
        }
        //获取评论
        if (lastid.equals("")) {
            into_comments = bean;
            userTrendAdapter = new UserTrendAdapter(my_trend, into_comments, this, this);
            cv_trends.setLayoutManager(new LinearLayoutManager(this));
            cv_trends.setAdapter(userTrendAdapter);
            cv_trends.setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
            userTrendAdapter.setOnItemClickListener(this);

            lastid = bean.get(bean.size() - 1).getId();
        } else {

            into_comments.addAll(bean);
            userTrendAdapter.notifyDataSetChanged();

            lastid = bean.get(bean.size() - 1).getId();
        }

    }


    @Override
    public void onError(Throwable throwable) {
        refreshLayout.finishLoadMore();
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {

    }

    @Override
    public void onClick(MomentDetail comment) {
        if (MyUserInstance.getInstance().visitorIsLogin()) {
            startActivityForResult(new Intent(UserTrendsActivity.this, ReplayTrendActivity.class).putExtra("MomentDetail", comment), 1005);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_emoji:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    showInput(true);
                }
                break;

            case R.id.tv_comment:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    showInput(false);
                }
                break;

            case R.id.rl_back:
                Intent intent = new Intent();
                intent.putExtra("data", my_trend);
                setResult(1004, intent);
                finish();
                break;
            case R.id.rl_zan:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    mPresenter.likeMoment(my_trend.getId());
                }
                break;
            case R.id.rl_collection:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    if (collect.equals("0")) {
                        mPresenter.collectMoment(my_trend.getId(), "1");
                    } else {
                        mPresenter.collectMoment(my_trend.getId(), "0");
                    }
                }
                break;
            case R.id.iv_share:
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    Glide.with(UserTrendsActivity.this).load(my_trend.getUser().getAvatar()).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
  /*                          ShareDialog.Builder builder = new ShareDialog.Builder(UserTrendsActivity.this);
                            builder.setShare_url(MyUserInstance.getInstance().getUserConfig().getConfig().getShare_moment_url() + my_trend.getId());
                            builder.create().show();
                            builder.showBottom2();
                            //builder.setImage_url(my_trend.getUser());
                            builder.setContent(my_trend.getTitle());
                            builder.setTitle("推荐你这个动态");
                            builder.hideCollect();
                            builder.setTumb(drawableToBitmap(resource));
                            builder.setType("2");
                            builder.setId(my_trend.getId());
*/


                            ShareDialog2 shareDialog2=new ShareDialog2(context);
                            shareDialog2.setHide_collect(true);
                            shareDialog2.setShare_url(MyUserInstance.getInstance().getUserConfig().getConfig().getShare_moment_url() + my_trend.getId());
                            shareDialog2.setContent(my_trend.getTitle());
                            shareDialog2.setTitle("推荐你这个动态");
                            shareDialog2.setTumb(drawableToBitmap(resource));
                            shareDialog2.setType("2");
                            shareDialog2.setId(my_trend.getId());

                            new XPopup.Builder(context)
                                    .dismissOnBackPressed(true)
                                    .dismissOnTouchOutside(true)
                                    .moveUpToKeyboard(false) //如果不加这个，评论弹窗会移动到软键盘上面
                                    .asCustom(shareDialog2/*.enableDrag(false)*/)

                                    .show();
                        }
                    });
                }
                break;

        }


    }


    private void showInput(boolean is_face) {
        if (null != inputDialogFragment3) {
            inputDialogFragment3.dismiss();
        }
        UserTrendCommentDialog dialogFragment = new UserTrendCommentDialog(my_trend.getId());
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.VIDEO_FACE_OPEN, is_face);
        dialogFragment.setArguments(bundle);
        inputDialogFragment3 = dialogFragment;
        inputDialogFragment3.show(getSupportFragmentManager(), "myAlert");
        inputDialogFragment3.setOnComentSendListener(new UserTrendCommentDialog.OnComentSendListener() {
            @Override
            public void onSendSucess(MomentDetail comment) {

                into_comments.add(0, comment);
                my_trend.setComment_count(((Integer.parseInt(my_trend.getComment_count()) + 1) + ""));
                tv_comment_count.setText("最新评论 (" + my_trend.getComment_count() + ")");
                userTrendAdapter.notifyDataSetChanged();
                inputDialogFragment3.dismiss();
            }
        });
    }

    /**
     * 初始化表情控件
     */
    public View initFaceView() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.view_chat_face, null);
        v.measure(0, 0);
        mFaceViewHeight = v.getMeasuredHeight();
        v.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputDialogFragment3 != null) {
                    inputDialogFragment3.sendMessage();
                }
            }
        });
        final RadioGroup radioGroup = v.findViewById(R.id.radio_group);
        ViewPager viewPager = v.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(10);
        ImChatFacePagerAdapter adapter = new ImChatFacePagerAdapter(this, new OnFaceClickListener() {
            @Override
            public void onFaceClick(String str, int faceImageRes) {
                if (inputDialogFragment3 != null) {
                    inputDialogFragment3.onFaceClick(str, faceImageRes);
                }
            }

            @Override
            public void onFaceDeleteClick() {
                if (inputDialogFragment3 != null) {
                    inputDialogFragment3.onFaceDeleteClick();
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

    /**
     * Drawable转换成一个Bitmap
     *
     * @param drawable drawable对象
     * @return
     */
    public final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private void setViewInfo(Drawable resource, ImageView view, VideoView videoView, RelativeLayout rl_single_pic) {

        int srcWidth = resource.getIntrinsicWidth();     // 源图宽度
        int srcHeight = resource.getIntrinsicHeight();    // 源图高度
        if (srcWidth == 0 | srcHeight == 0) {
            return;
        }

        view.setVisibility(View.VISIBLE);
        view.setImageDrawable(resource);


        if (srcWidth == srcHeight) {
            Log.e("TAG", "正方形");
            view.getLayoutParams().width =  DipPxUtils.dip2px(this,224);
            view.getLayoutParams().height =  DipPxUtils.dip2px(this,224);
            rl_single_pic.getLayoutParams().width =  DipPxUtils.dip2px(this,224);
            rl_single_pic.getLayoutParams().height =  DipPxUtils.dip2px(this,224);
            if (null != videoView) {
                videoView.getLayoutParams().width =  DipPxUtils.dip2px(this,224);
                videoView.getLayoutParams().height =  DipPxUtils.dip2px(this,224);
            }
            return;
        }

        if (srcWidth > srcHeight) {
            Log.e("TAG", "长方形横");

            view.getLayoutParams().width =  DipPxUtils.dip2px(this,345);
            view.getLayoutParams().height =  DipPxUtils.dip2px(this,201);
            rl_single_pic.getLayoutParams().width =  DipPxUtils.dip2px(this,345);
            rl_single_pic.getLayoutParams().height =  DipPxUtils.dip2px(this,201);
            if (null != videoView) {
                videoView.getLayoutParams().width =  DipPxUtils.dip2px(this,345);
                videoView.getLayoutParams().height =  DipPxUtils.dip2px(this,201);
            }
            return;
        }

        if (srcWidth < srcHeight) {
            Log.e("TAG", "长方形竖");
            view.getLayoutParams().width =  DipPxUtils.dip2px(this,224);
            view.getLayoutParams().height =  DipPxUtils.dip2px(this,300);
            rl_single_pic.getLayoutParams().width =  DipPxUtils.dip2px(this,224);
            rl_single_pic.getLayoutParams().height =  DipPxUtils.dip2px(this,300);
            if (null != videoView) {
                videoView.getLayoutParams().width =  DipPxUtils.dip2px(this,224);
                videoView.getLayoutParams().height =  DipPxUtils.dip2px(this,300);
            }
            return;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        VideoViewManager.instance().pause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1005) {
            MomentDetail my_data = (MomentDetail) data.getSerializableExtra("data");
            if (my_data == null) {
                return;
            }

            for (int i = 0; i < into_comments.size(); i++) {
                if (into_comments.get(i).getId().equals(my_data.getId())) {
                    into_comments.set(i, my_data);
                    userTrendAdapter.notifyDataSetChanged();
                    break;
                }
            }


        }
    }

    private void showUnlockDialog() {
        UnlockDialog unlockDialog = new UnlockDialog(UserTrendsActivity.this, my_trend);
        unlockDialog.show();
        unlockDialog.setUnLockListener(new UnlockDialog.UnLockListener() {
            @Override
            public void unLockTrend(String id) {
                mPresenter.unlockMoment(id);
            }
        });
    }
}
