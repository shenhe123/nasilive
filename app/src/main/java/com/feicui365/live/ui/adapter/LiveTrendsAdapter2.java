package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.dueeeke.videoplayer.player.VideoView;
import com.feicui365.live.R;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.Trend;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.ui.act.AnchorCenterActivity;
import com.feicui365.live.ui.act.PhotoInfoActivity;
import com.feicui365.live.util.FormatCurrentData;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.widget.MyBGANinePhotoLayout;
import com.feicui365.live.widget.MyStandardVideoController;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class LiveTrendsAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<Trend> trendsItem;
    Context context;
    BGANinePhotoLayout.Delegate delegate;
    private OnItemClick onItemClick;
    private ShowPriceDialogClick showPriceDialogClick;
    UserRegist anchorInfo;
    public void setAnchorInfo(UserRegist anchorInfo) {
        this.anchorInfo = anchorInfo;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_single_pic, civ_avatar, iv_single_pic_fufei, iv_user_level;
        MyBGANinePhotoLayout npl_item_moment_photos;
        View itemView;
        RelativeLayout ll_others,rl_share;
        VideoView player;
        RelativeLayout rl_single_pic;
        LinearLayout ll_zan, ll_liuyan;
        TextView tv_name, age_tv, tv_content, tv_time, tv_zan, tv_liuyan, iv_single_pic_z_tv, tv_content_title;
        ImageView iv_zan, iv_liuyan;
        RelativeLayout rl_content_title;
        public ViewHolder(View view) {
            super(view);
            npl_item_moment_photos = view.findViewById(R.id.npl_item_moment_photos);
            iv_single_pic = view.findViewById(R.id.iv_single_pic);
            civ_avatar = view.findViewById(R.id.civ_avatar);
            tv_name = view.findViewById(R.id.tv_name);

            tv_content = view.findViewById(R.id.tv_content);

            tv_zan = view.findViewById(R.id.tv_zan);
            tv_time = view.findViewById(R.id.tv_time);
            tv_liuyan = view.findViewById(R.id.tv_liuyan);
            ll_others = view.findViewById(R.id.ll_others);
            player = view.findViewById(R.id.player);
            iv_single_pic_fufei = view.findViewById(R.id.iv_single_pic_fufei);
            iv_single_pic_z_tv = view.findViewById(R.id.iv_single_pic_z_tv);
            rl_single_pic = view.findViewById(R.id.rl_single_pic);
            rl_share=view.findViewById(R.id.rl_share);
            ll_zan = view.findViewById(R.id.ll_zan);
            ll_liuyan = view.findViewById(R.id.ll_liuyan);
            iv_zan = view.findViewById(R.id.iv_zan);
            iv_liuyan = view.findViewById(R.id.iv_liuyan);

            iv_user_level = view.findViewById(R.id.iv_user_level);
            age_tv = view.findViewById(R.id.age_tv);
            rl_content_title = view.findViewById(R.id.rl_content_title);
            tv_content_title = view.findViewById(R.id.tv_content_title);
            itemView = view;
        }

    }
    static class TopViewHolder extends RecyclerView.ViewHolder {

        CircleImageView civ_avatar;
        TextView tv_name;
        ImageView iv_vip;
        ImageView iv_anchor_level;
        TextView tv_sex_age;
        TextView tv_room_id;
        TextView tv_sign;

        public TopViewHolder(View view) {
            super(view);

            civ_avatar = view.findViewById(R.id.civ_avatar);
            tv_name = view.findViewById(R.id.tv_name);
            iv_vip = view.findViewById(R.id.iv_vip);
            iv_anchor_level = view.findViewById(R.id.iv_anchor_level);
            tv_sex_age = view.findViewById(R.id.tv_sex_age);
            tv_room_id = view.findViewById(R.id.tv_room_id);
            tv_sign = view.findViewById(R.id.tv_sign);

        }

    }

    public LiveTrendsAdapter2(List trendsItem, Context context, BGANinePhotoLayout.Delegate delegate) {
        this.context = context;
        this.trendsItem = trendsItem;
        this.delegate = delegate;
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_trend_top, parent, false);
            LiveTrendsAdapter2.TopViewHolder holder = new LiveTrendsAdapter2.TopViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trends_item, parent, false);
            LiveTrendsAdapter2.ViewHolder holder = new LiveTrendsAdapter2.ViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position == 0) {
            if (anchorInfo == null) {
                return;
            }
            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.zhanwei)).load(anchorInfo.getAvatar()).into(((TopViewHolder) holder).civ_avatar);
            ((TopViewHolder) holder).tv_name.setText(anchorInfo.getNick_name());
            Glide.with(context).load(LevelUtils.getUserLevel(anchorInfo.getAnchor_level())).into(((TopViewHolder) holder).iv_anchor_level);


            ((TopViewHolder) holder).tv_sex_age.setText(anchorInfo.getProfile().getAge());
            if (anchorInfo.getProfile().getGender()==1) {
                ((TopViewHolder) holder).tv_sex_age.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);
                ((TopViewHolder) holder).tv_sex_age.setBackgroundResource(R.drawable.shape_corner_age_boy);
            }
            ((TopViewHolder) holder).tv_sign.setText(anchorInfo.getProfile().getSignature());

            if (anchorInfo.getVip_level() != 0 ) {


                    if (MyUserInstance.getInstance().OverTime(anchorInfo.getVip_date())) {
                        Glide.with(context).load(ArmsUtils.getVipLevelIcon(anchorInfo.getVip_level(),anchorInfo.getVip_date())).into(((TopViewHolder) holder).iv_vip);
                        ((TopViewHolder) holder).iv_vip.setVisibility(View.VISIBLE);
                    } else {
                        ((TopViewHolder) holder).iv_vip.setVisibility(View.GONE);
                    }
                } else {
                ((TopViewHolder) holder).iv_vip.setVisibility(View.GONE);
            }

        } else {
            if (trendsItem.size() == 0) {
                return;
            }
            holder.setIsRecyclable(false);
            ((ViewHolder) holder).tv_name.setText(trendsItem.get(position-1).getUser().getNick_name());


            ((ViewHolder) holder).tv_time.setText(FormatCurrentData.getTimeRange2(trendsItem.get(position-1).getCreate_time()));


            ((ViewHolder) holder).tv_zan.setText(trendsItem.get(position-1).getLike_count());
            ((ViewHolder) holder).tv_liuyan.setText(trendsItem.get(position-1).getComment_count());

            if (trendsItem.get(position-1).getUid().equals(MyUserInstance.getInstance().getUserinfo().getId())) {
                trendsItem.get(position-1).setUnlocked("1");
                ((ViewHolder) holder).rl_share.setVisibility(View.GONE);
            }

            ((ViewHolder) holder).rl_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MyUserInstance.getInstance().visitorIsLogin()) {
                        Glide.with(context).load(trendsItem.get(position - 1).getUser().getAvatar()).into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                ShareDialog.Builder builder = new ShareDialog.Builder(context);
                                builder.setShare_url(MyUserInstance.getInstance().getUserConfig().getConfig().getShare_moment_url() + trendsItem.get(position - 1).getId());
                                builder.create().show();
                                builder.showBottom2();
                                //builder.setImage_url(my_trend.getUser());
                                builder.setContent(trendsItem.get(position - 1).getTitle());
                                builder.setTitle("推荐你这个动态");
                                builder.hideCollect();
                                builder.setTumb(drawableToBitmap(resource));
                                builder.setType("2");
                                builder.setId(trendsItem.get(position - 1).getId());
                            }
                        });
                    }

                }
            });


            switch (trendsItem.get(position-1).getType()) {
                //文字
                case "1":
                    ((ViewHolder) holder).rl_single_pic.setVisibility(View.GONE);
                    ((ViewHolder) holder).ll_others.setVisibility(View.GONE);
                    ((ViewHolder) holder).rl_content_title.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).tv_content_title.setText(trendsItem.get(position-1).getTitle());
                    if (trendsItem.get(position-1).getContent() != null) {
                        ((ViewHolder) holder).tv_content.setText(trendsItem.get(position-1).getContent());
                    }

                    break;
                //图片
                case "2":
                    //图片合集
                    if (trendsItem.get(position-1).getTitle() != null) {
                        ((ViewHolder) holder).tv_content.setText(trendsItem.get(position-1).getTitle());
                    }
                    if (((ViewHolder) holder).tv_content.getText().toString().equals("")) {
                        ((ViewHolder) holder).tv_content.setVisibility(View.GONE);
                    }
                    if (trendsItem.get(position-1).getImage_url() == null) {
                        ((ViewHolder) holder).itemView.setVisibility(View.GONE);
                        return;
                    }


                    ArrayList pic_unlock = new ArrayList();
                    String[] images = trendsItem.get(position-1).getImage_url().split(",");
                    for (String image : images) {
                        pic_unlock.add(image);
                    }


                    if (pic_unlock.size() == 1) {

                        Log.e("TAG_POSTION", position-1 + "");
                        ((ViewHolder) holder).rl_single_pic.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).ll_others.setVisibility(View.VISIBLE);
                        ArrayList pic_lock = new ArrayList();


                        if ((TextUtils.isEmpty(trendsItem.get(position-1).getUnlocked()) || trendsItem.get(position-1).getUnlocked().equals("0")) &&
                                !trendsItem.get(position-1).getUnlock_price().equals("0")) {
                            if (null == trendsItem.get(position-1).getBlur_image_url()) {
                                Glide.with(((ViewHolder) holder).itemView)
                                        .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                                        .load(R.mipmap.logo)
                                        .into(new SimpleTarget<Drawable>() {
                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                ((ViewHolder) holder).iv_single_pic_fufei.setVisibility(View.VISIBLE);
                                                ((ViewHolder) holder).iv_single_pic_z_tv.setVisibility(View.VISIBLE);
                                                ((ViewHolder) holder).iv_single_pic_z_tv.setText(trendsItem.get(position-1).getUnlock_price() + "金币");
                                                ((ViewHolder) holder).iv_single_pic_fufei.setImageResource(R.mipmap.nofufei2);
                                                setViewInfo(resource, ((ViewHolder) holder).iv_single_pic, null, ((ViewHolder) holder).rl_single_pic);
                                            }


                                        });
                            } else {
                                String[] images_lock = trendsItem.get(position-1).getBlur_image_url().split(",");
                                for (String image : images_lock) {
                                    pic_lock.add(image);
                                }
                                Glide.with(((ViewHolder) holder).itemView)
                                        .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                                        .load(pic_lock.get(0))
                                        .into(new SimpleTarget<Drawable>() {
                                            @Override
                                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                                ((ViewHolder) holder).iv_single_pic_fufei.setVisibility(View.VISIBLE);
                                                ((ViewHolder) holder).iv_single_pic_z_tv.setVisibility(View.VISIBLE);
                                                ((ViewHolder) holder).iv_single_pic_z_tv.setText(trendsItem.get(position-1).getUnlock_price() + "金币");
                                                ((ViewHolder) holder).iv_single_pic_fufei.setImageResource(R.mipmap.nofufei2);
                                                setViewInfo(resource, ((ViewHolder) holder).iv_single_pic, null, ((ViewHolder) holder).rl_single_pic);
                                            }


                                        });
                            }


                        } else {
                            Glide.with(((ViewHolder) holder).itemView)
                                    .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                                    .load(pic_unlock.get(0))
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            ((ViewHolder) holder).iv_single_pic_fufei.setVisibility(View.GONE);
                                            ((ViewHolder) holder).iv_single_pic_z_tv.setVisibility(View.GONE);
                                            setViewInfo(resource, ((ViewHolder) holder).iv_single_pic, null, ((ViewHolder) holder).rl_single_pic);
                                        }


                                    });
                        }


                    } else if (pic_unlock.size() > 1) {
                        ((ViewHolder) holder).ll_others.setVisibility(View.VISIBLE);
                        ((ViewHolder) holder).rl_single_pic.setVisibility(View.GONE);

                        if ((TextUtils.isEmpty(trendsItem.get(position-1).getUnlocked()) || trendsItem.get(position-1).getUnlocked().equals("0")) &&
                                !trendsItem.get(position-1).getUnlock_price().equals("0")) {
                            ArrayList pic_lock = new ArrayList();

                            if (null == trendsItem.get(position-1).getBlur_image_url()) {
                                for (int i = 0; i < pic_unlock.size(); i++) {
                                    pic_lock.add("");
                                }
                                ((ViewHolder) holder).npl_item_moment_photos.setData(pic_lock, true, trendsItem.get(position-1).getUnlock_price());
                            } else {

                                String[] images_lock = trendsItem.get(position-1).getBlur_image_url().split(",");
                                for (String image : images_lock) {
                                    pic_lock.add(image);
                                }

                                if (pic_lock.size() < pic_unlock.size()) {
                                    for (int i = 0; i < (pic_lock.size() - pic_unlock.size()); i++) {
                                        pic_lock.add("");
                                    }
                                }


                                ((ViewHolder) holder).npl_item_moment_photos.setData(pic_lock, true, trendsItem.get(position-1).getUnlock_price());
                            }

                        } else {
                            ((ViewHolder) holder).npl_item_moment_photos.setData(pic_unlock, false, "");
                        }
                    }

                    break;
                //视频
                case "3":
                    if (trendsItem.get(position-1).getTitle() != null) {
                        ((ViewHolder) holder).tv_content.setText(trendsItem.get(position-1).getTitle());
                    }
                    if (((ViewHolder) holder).tv_content.getText().toString().equals("")) {
                        ((ViewHolder) holder).tv_content.setVisibility(View.GONE);
                    }
                    ((ViewHolder) holder).ll_others.setVisibility(View.VISIBLE);

                    //2,如果未付费,显示付费相关界面
                    if ((TextUtils.isEmpty(trendsItem.get(position-1).getUnlocked()) || trendsItem.get(position-1).getUnlocked().equals("0")) &&
                            !trendsItem.get(position-1).getUnlock_price().equals("0")) {

                        Glide.with(((ViewHolder) holder).itemView)
                                .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                                .load(trendsItem.get(position-1).getImage_url())
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        ((ViewHolder) holder).rl_single_pic.setVisibility(View.VISIBLE);
                                        //显示锁,显示金额
                                        ((ViewHolder) holder).iv_single_pic_fufei.setVisibility(View.VISIBLE);
                                        ((ViewHolder) holder).iv_single_pic_z_tv.setVisibility(View.VISIBLE);
                                        ((ViewHolder) holder).iv_single_pic_z_tv.setText(trendsItem.get(position-1).getUnlock_price() + "金币");
                                        ((ViewHolder) holder).iv_single_pic_fufei.setImageResource(R.mipmap.nofufei2);
                                        //调整大小
                                        setViewInfo(resource, ((ViewHolder) holder).iv_single_pic, null, ((ViewHolder) holder).rl_single_pic);
                                    }
                                });


                    } else {
                        //3,如果付费,直接显示基本界面

                        Glide.with(((ViewHolder) holder).itemView)
                                .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                                .load(trendsItem.get(position-1).getImage_url())
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                        //已经付费屏蔽外层界面
                                        ((ViewHolder) holder).rl_single_pic.setVisibility(View.GONE);
                                        ((ViewHolder) holder).iv_single_pic_z_tv.setVisibility(View.GONE);
                                        ((ViewHolder) holder).iv_single_pic_fufei.setVisibility(View.GONE);

                                        //调整大小
                                        setViewInfo(resource, ((ViewHolder) holder).iv_single_pic, ((ViewHolder) holder).player, ((ViewHolder) holder).rl_single_pic);

                                        //显示播放界面
                                        ((ViewHolder) holder).player.setVisibility(View.VISIBLE);
                                        ((ViewHolder) holder).player.setUsingSurfaceView(false);
                                        MyStandardVideoController mb = new MyStandardVideoController(context);
                                        mb.setIs_speclie(true);
                                        mb.getmFullScreenButton().setVisibility(View.GONE);
                                        mb.getThumb().setImageDrawable(resource);
                                        ((ViewHolder) holder).player.setVideoController(mb);
                                        ((ViewHolder) holder).player.setUrl(trendsItem.get(position-1).getVideo_url());
                                        mb.setOnPlayClickListener(new MyStandardVideoController.OnPlayClickListener() {
                                            @Override
                                            public void onPlayClick() {
                                                if (onItemClick != null) {
                                                    onItemClick.onItemclickListener(position-1, trendsItem.get(position-1));
                                                }

                                            }

                                        });
                                    }
                                });

                    }

                    break;
            }


            ((ViewHolder) holder).rl_single_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MyUserInstance.getInstance().visitorIsLogin()) {
                        if ((TextUtils.isEmpty(trendsItem.get(position - 1).getUnlocked()) || trendsItem.get(position - 1).getUnlocked().equals("0")) &&
                                !trendsItem.get(position - 1).getUnlock_price().equals("0")) {
                            if (null != showPriceDialogClick) {
                                showPriceDialogClick.showPriceDialog(position - 1);
                            }

                        } else {

                            context.startActivity(new Intent(context, PhotoInfoActivity.class).putExtra("data", trendsItem.get(position - 1).getPhotos()));
                        }
                    }
                }
            });
            ((ViewHolder) holder).age_tv.setText(trendsItem.get(position-1).getUser().getProfile().getAge());
            if (trendsItem.get(position-1).getUser().getProfile().getGender()==1) {
                ((ViewHolder) holder).age_tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);

                ((ViewHolder) holder).age_tv.setBackgroundResource(R.drawable.shape_corner_age_boy);
            }
            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(LevelUtils.getUserLevel(trendsItem.get(position-1).getUser().getUser_level())).into(((ViewHolder) holder).iv_user_level);


            ((ViewHolder) holder).npl_item_moment_photos.setDelegate(new BGANinePhotoLayout.Delegate() {
                @Override
                public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int positions, String model, List<String> models) {
                    if(MyUserInstance.getInstance().visitorIsLogin()) {
                        if ((TextUtils.isEmpty(trendsItem.get(position - 1).getUnlocked()) || trendsItem.get(position - 1).getUnlocked().equals("0")) &&
                                !trendsItem.get(position - 1).getUnlock_price().equals("0")) {
                            if (null != showPriceDialogClick) {
                                showPriceDialogClick.showPriceDialog(position - 1);
                            }
                        } else {
                            ArrayList<String> arrayList = (ArrayList<String>) models;
                            context.startActivity(new Intent(context, PhotoInfoActivity.class).putExtra("data", arrayList).putExtra("positions", positions));

                        }
                    }
                }
            });

            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(trendsItem.get(position-1).getUser().getAvatar()).into(((ViewHolder) holder).civ_avatar);
            ((ViewHolder) holder).civ_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, AnchorCenterActivity.class)
                            .putExtra("data", trendsItem.get(position-1).getUid()));
                }
            });

            ((ViewHolder) holder).tv_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.onItemclickListener(position-1, trendsItem.get(position-1));
                    }

                }
            });

            if (trendsItem.get(position-1).getLiked() == null) {
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(R.mipmap.ic_zan).into(((ViewHolder) holder).iv_zan);
            } else {
                if (trendsItem.get(position-1).getLiked().equals("0")) {
                    Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(R.mipmap.ic_zan).into(((ViewHolder) holder).iv_zan);
                } else {
                    Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(R.mipmap.ic_zan_pre).into(((ViewHolder) holder).iv_zan);
                }
            }

            //通过tag将View ((ViewHolder)holder)和itemView绑定
            ((ViewHolder) holder).itemView.setTag(this);
            ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClick != null) {
                        onItemClick.onItemclickListener(position-1, trendsItem.get(position-1));
                    }

                }
            });

            ((ViewHolder) holder).ll_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(MyUserInstance.getInstance().visitorIsLogin()) {
                        String is_like = trendsItem.get(position - 1).getLiked();
                        if (is_like == null || is_like.equals("0")) {
                        } else {
                            return;
                        }
                        HttpUtils.getInstance().likeMoment(trendsItem.get(position - 1).getId(), new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                JSONObject data = HttpUtils.getInstance().check(response);
                                if (HttpUtils.getInstance().swtichStatus(data)) {
                                    Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(R.mipmap.ic_zan_pre).into(((ViewHolder) holder).iv_zan);
                                    ((ViewHolder) holder).tv_zan.setText(data.getJSONObject("data").getString("like_count"));
                                    trendsItem.get(position - 1).setLike_count(data.getJSONObject("data").getString("like_count"));
                                    trendsItem.get(position - 1).setLiked("1");


                                }
                            }
                        });
                    }
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return trendsItem.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void showPriceDialogClick(ShowPriceDialogClick showPriceDialogClick) {
        this.showPriceDialogClick = showPriceDialogClick;
    }


    public interface OnItemClick {
        void onItemclickListener(int position, Trend trend);

    }

    public interface ShowPriceDialogClick {
        void showPriceDialog(int position);
    }

    public int px2dip(float dipValue) {
        if (context != null) {


            float m = context.getResources().getDisplayMetrics().density;

            return (int) (dipValue * m + 0.5f);
        } else {
            return 0;
        }

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
            view.getLayoutParams().width = px2dip(224);
            view.getLayoutParams().height = px2dip(224);
            rl_single_pic.getLayoutParams().width = px2dip(224);
            rl_single_pic.getLayoutParams().height = px2dip(224);
            if (null != videoView) {
                videoView.getLayoutParams().width = px2dip(224);
                videoView.getLayoutParams().height = px2dip(224);
            }
            return;
        }

        if (srcWidth > srcHeight) {
            Log.e("TAG", "长方形横");

            view.getLayoutParams().width = px2dip(345);
            view.getLayoutParams().height = px2dip(201);
            rl_single_pic.getLayoutParams().width = px2dip(345);
            rl_single_pic.getLayoutParams().height = px2dip(201);
            if (null != videoView) {
                videoView.getLayoutParams().width = px2dip(345);
                videoView.getLayoutParams().height = px2dip(201);
            }
            return;
        }

        if (srcWidth < srcHeight) {
            Log.e("TAG", "长方形竖");
            view.getLayoutParams().width = px2dip(224);
            view.getLayoutParams().height = px2dip(300);
            rl_single_pic.getLayoutParams().width = px2dip(224);
            rl_single_pic.getLayoutParams().height = px2dip(300);
            if (null != videoView) {
                videoView.getLayoutParams().width = px2dip(224);
                videoView.getLayoutParams().height = px2dip(300);
            }
            return;
        }
    }

    public  final Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap( drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}
