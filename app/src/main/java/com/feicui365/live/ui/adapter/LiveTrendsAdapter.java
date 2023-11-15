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
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dueeeke.videoplayer.player.VideoView;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.Trend;
import com.feicui365.live.ui.act.AnchorCenterActivity;
import com.feicui365.live.ui.act.PhotoInfoActivity;
import com.feicui365.live.ui.act.TopicMomentVideoActivity;
import com.feicui365.live.util.FormatCurrentData;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.widget.MyBGANinePhotoLayout;
import com.feicui365.live.widget.MyStandardVideoController;
import com.feicui365.nasinet.utils.DipPxUtils;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class LiveTrendsAdapter extends BaseMultiItemQuickAdapter<Trend, BaseViewHolder> {



    Context context;
    BGANinePhotoLayout.Delegate delegate;
    private OnItemClick onItemClick;
    private ShowPriceDialogClick showPriceDialogClick;

    public LiveTrendsAdapter(Context context, @Nullable List<Trend> data, BGANinePhotoLayout.Delegate delegate) {
        super(data);
        this.context = context;
        this.delegate = delegate;
        addItemType(1, R.layout.trends_item_text);
        addItemType(2, R.layout.trends_item_pic);
        addItemType(3, R.layout.trends_item_video);
    }

    public LiveTrendsAdapter(@Nullable List<Trend> data) {
        super(data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, Trend item) {

        helper.setTag(R.id.ll_item, helper.getLayoutPosition());
        helper.setText(R.id.tv_name, item.getUser().getNick_name());
        if(item.getTopic()!=null){
            helper.setText(R.id.tv_content, "");
            SpannableString spString = new SpannableString(item.getTopic());
            spString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent=new Intent(mContext, TopicMomentVideoActivity.class);
                    intent.putExtra("topic",spString.toString());
                    mContext.startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                    ds.setTextSize(DipPxUtils.sp2px(context,15));//设置字体大小
                    ds.setColor(context.getResources().getColor(R.color.color_FF6273));//设置字体颜色
                }
            }, 0, item.getTopic().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            ((TextView)helper.getView(R.id.tv_content)).setText(spString);
            ((TextView)helper.getView(R.id.tv_content)).setMovementMethod(LinkMovementMethod.getInstance());
            ((TextView)helper.getView(R.id.tv_content)).append(" "+item.getTitle());
        }else {
            helper.setText(R.id.tv_content, item.getTitle());
        }
        if (item.getTitle() == null) {
            RecyclerView.LayoutParams param = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            param.height = 0;
            param.width = 0;
            helper.getView(R.id.tv_content).setVisibility(View.GONE);
            helper.getView(R.id.tv_content).setLayoutParams(param);
        }
        if (item.getTitle().equals("")) {
            RecyclerView.LayoutParams param = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            param.height = 0;
            param.width = 0;
            helper.getView(R.id.tv_content).setVisibility(View.GONE);
            helper.getView(R.id.tv_content).setLayoutParams(param);
        }


        helper.setText(R.id.tv_time, FormatCurrentData.getTimeRange2(item.getCreate_time()));
        helper.setText(R.id.tv_zan, item.getLike_count());
        helper.setText(R.id.tv_liuyan, item.getComment_count());
        if (item.getUid().equals(MyUserInstance.getInstance().getUserinfo().getId())) {
            item.setUnlocked("1");
            helper.setVisible(R.id.rl_share, false);
        }

        helper.getView(R.id.rl_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyUserInstance.getInstance().visitorIsLogin()) {
                    Glide.with(context).load(item.getUser().getAvatar()).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            ShareDialog.Builder builder = new ShareDialog.Builder(context);
                            builder.setShare_url(MyUserInstance.getInstance().getUserConfig().getConfig().getShare_moment_url() + item.getId());
                            builder.create().show();
                            builder.showBottom2();
                            builder.setContent(item.getTitle());
                            builder.setTitle("推荐你这个动态");
                            builder.hideCollect();
                            builder.setTumb(drawableToBitmap(resource));
                            builder.setType("2");
                            builder.setId(item.getId());

                        }
                    });
                }

            }
        });

        helper.setText(R.id.age_tv, item.getUser().getProfile().getAge());
        if (item.getUser().getProfile().getGender()==1) {

            ((TextView) helper.getView(R.id.age_tv)).setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);

            ((TextView) helper.getView(R.id.age_tv)).setBackgroundResource(R.drawable.shape_corner_age_boy);
        }
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(LevelUtils.getUserLevel(item.getUser().getUser_level())).into((ImageView) helper.getView(R.id.iv_user_level));
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate().placeholder(R.mipmap.moren)).load(item.getUser().getAvatar()).into((CircleImageView) helper.getView(R.id.civ_avatar));

        helper.getView(R.id.civ_avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AnchorCenterActivity.class)
                        .putExtra("data", item.getUid()));
            }
        });

 /*       helper.getView(R.id.tv_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.onItemclickListener(helper.getLayoutPosition(), item);
                }

            }
        });*/

        if (item.getLiked() == null) {
            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(R.mipmap.ic_zan).into((ImageView) helper.getView(R.id.iv_zan));
        } else {
            if (item.getLiked().equals("0")) {
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(R.mipmap.ic_zan).into((ImageView) helper.getView(R.id.iv_zan));
            } else {
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(R.mipmap.ic_zan_pre).into((ImageView) helper.getView(R.id.iv_zan));
            }
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    onItemClick.onItemclickListener(helper.getLayoutPosition(), item);
                }

            }
        });
        helper.getView(R.id.ll_zan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyUserInstance.getInstance().visitorIsLogin()) {
                    String is_like = item.getLiked();
                    if (is_like == null || is_like.equals("0")) {
                        HttpUtils.getInstance().likeMoment(item.getId(), new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                JSONObject data = HttpUtils.getInstance().check(response);
                                if (HttpUtils.getInstance().swtichStatus(data)) {
                                    Glide.with(context).applyDefaultRequestOptions(new RequestOptions().dontAnimate()).load(R.mipmap.ic_zan_pre).into((ImageView) helper.getView(R.id.iv_zan));
                                    helper.setText(R.id.tv_zan, data.getJSONObject("data").getString("like_count"));
                                    item.setLike_count(data.getJSONObject("data").getString("like_count"));
                                    item.setLiked("1");
                                }
                            }
                        });
                    }
                }

            }
        });
        switch (helper.getItemViewType()) {
            case 2:
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClick != null) {
                            onItemClick.onItemclickListener(helper.getLayoutPosition(), item);
                        }

                    }
                });


                ArrayList pic_unlock = new ArrayList();
                String[] images = item.getImage_url().split(",");
                for (String image : images) {
                    pic_unlock.add(image);
                }

                if (pic_unlock.size() == 1) {
                    //如果只有一张图
                    helper.setGone(R.id.rl_single_pic, false);
                    helper.setGone(R.id.npl_item_moment_photos, false);
                    if ((TextUtils.isEmpty(item.getUnlocked()) || item.getUnlocked().equals("0")) &&
                            !item.getUnlock_price().equals("0")) {
                        //判断模糊图
                        if (null == item.getBlur_image_url()) {
                            Glide.with(context)
                                    .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                                    .load(R.mipmap.zhanwei)
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            helper.setGone(R.id.iv_single_pic_fufei, true);
                                            helper.setGone(R.id.iv_single_pic_z_tv, true);
                                            helper.setGone(R.id.rl_single_pic, true);
                                            helper.setText(R.id.iv_single_pic_z_tv, item.getUnlock_price() + "金币");
                                            setViewInfo(resource, helper.getView(R.id.iv_single_pic), null, helper.getView(R.id.rl_single_pic));
                                        }

                                        @Override
                                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                            super.onLoadFailed(errorDrawable);
                                            helper.setGone(R.id.rl_single_pic, false);
                                        }
                                    });
                        } else {
                            ArrayList pic_lock = new ArrayList();
                            String[] images_lock = item.getBlur_image_url().split(",");
                            for (String image : images_lock) {
                                pic_lock.add(image);
                            }
                            Glide.with(context)
                                    .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                                    .load(pic_lock.get(0))
                                    .into(new SimpleTarget<Drawable>() {
                                        @Override
                                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                            helper.setGone(R.id.iv_single_pic_fufei, true);
                                            helper.setGone(R.id.iv_single_pic_z_tv, true);
                                            helper.setGone(R.id.rl_single_pic, true);
                                            helper.setText(R.id.iv_single_pic_z_tv, item.getUnlock_price() + "金币");
                                            setViewInfo(resource, helper.getView(R.id.iv_single_pic), null, helper.getView(R.id.rl_single_pic));
                                        }

                                        @Override
                                        public void onLoadFailed(@Nullable Drawable errorDrawable) {
                                            super.onLoadFailed(errorDrawable);

                                            helper.setGone(R.id.rl_single_pic, false);
                                        }
                                    });
                        }


                    } else {
                        helper.setGone(R.id.rl_single_pic, true);
                        helper.setGone(R.id.iv_single_pic_fufei, false);
                        helper.setGone(R.id.iv_single_pic_z_tv, false);

                        Glide.with(context)
                                .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                                .load(pic_unlock.get(0))
                                .into(new SimpleTarget<Drawable>() {
                                    @Override
                                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                                        setViewInfo(resource, helper.getView(R.id.iv_single_pic), null, helper.getView(R.id.rl_single_pic));
                                    }


                                });
                    }

                } else {
                    helper.setGone(R.id.rl_single_pic, false);
                    helper.setGone(R.id.npl_item_moment_photos, true);
                    if ((TextUtils.isEmpty(item.getUnlocked()) || item.getUnlocked().equals("0")) &&
                            !item.getUnlock_price().equals("0")) {
                        ArrayList pic_lock = new ArrayList();

                        if (null == item.getBlur_image_url()) {
                            for (int i = 0; i < pic_unlock.size(); i++) {
                                pic_lock.add("");
                            }

                            ((MyBGANinePhotoLayout) helper.getView(R.id.npl_item_moment_photos)).setData(pic_lock, true, item.getUnlock_price());
                        } else {

                            String[] images_lock = item.getBlur_image_url().split(",");
                            for (String image : images_lock) {
                                pic_lock.add(image);
                            }

                            if (pic_lock.size() < pic_unlock.size()) {
                                for (int i = 0; i < (pic_lock.size() - pic_unlock.size()); i++) {
                                    pic_lock.add("");
                                }
                            }


                            ((MyBGANinePhotoLayout) helper.getView(R.id.npl_item_moment_photos)).setData(pic_lock, true, item.getUnlock_price());
                        }

                    } else {

                        ((MyBGANinePhotoLayout) helper.getView(R.id.npl_item_moment_photos)).setData(pic_unlock, false, "");
                    }
                }

                ((MyBGANinePhotoLayout) helper.getView(R.id.npl_item_moment_photos)).setDelegate(new BGANinePhotoLayout.Delegate() {
                    @Override
                    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int positions, String model, List<String> models) {
                        if(MyUserInstance.getInstance().visitorIsLogin()) {
                            if ((TextUtils.isEmpty(item.getUnlocked()) || item.getUnlocked().equals("0")) &&
                                    !item.getUnlock_price().equals("0")) {
                                if (null != showPriceDialogClick) {
                                    showPriceDialogClick.showPriceDialog(helper.getLayoutPosition());
                                }
                            } else {
                                ArrayList<String> arrayList = (ArrayList<String>) models;
                                context.startActivity(new Intent(context, PhotoInfoActivity.class).putExtra("data", arrayList).putExtra("positions", positions));

                            }
                        }
                    }
                });
                helper.getView(R.id.rl_single_pic).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyUserInstance.getInstance().visitorIsLogin()) {
                            if ((TextUtils.isEmpty(item.getUnlocked()) || item.getUnlocked().equals("0")) &&
                                    !item.getUnlock_price().equals("0")) {
                                if (null != showPriceDialogClick) {
                                    showPriceDialogClick.showPriceDialog(helper.getLayoutPosition());
                                }
                            } else {
                                if (!TextUtils.isEmpty(item.getImage_url())) {
                                    ArrayList arrayList = new ArrayList<>();
                                    String[] images = item.getImage_url().split(",");
                                    for (String image : images) {
                                        arrayList.add(image);
                                    }
                                    context.startActivity(new Intent(context, PhotoInfoActivity.class).putExtra("data", arrayList));
                                }

                            }

                        }
                    }
                });

                break;
            case 3:

                if ((TextUtils.isEmpty(item.getUnlocked()) || item.getUnlocked().equals("0")) &&
                        !item.getUnlock_price().equals("0")) {

                    helper.setGone(R.id.player, false);
                    Glide.with(context)
                            .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                            .load(item.getImage_url())
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    helper.setGone(R.id.rl_single_pic, true);
                                    helper.setGone(R.id.iv_single_pic_fufei, true);
                                    helper.setGone(R.id.iv_single_pic_z_tv, true);
                                    helper.setText(R.id.iv_single_pic_z_tv, item.getUnlock_price() + "金币");
                                    setViewInfo(resource, helper.getView(R.id.iv_single_pic), null, helper.getView(R.id.rl_single_pic));
                                }
                            });
                } else {

                    helper.setGone(R.id.player, true);
                    Glide.with(context)
                            .applyDefaultRequestOptions(new RequestOptions().dontAnimate())
                            .load(item.getImage_url())
                            .into(new SimpleTarget<Drawable>() {
                                @Override
                                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                    helper.setGone(R.id.rl_single_pic, false);
                                    helper.setGone(R.id.iv_single_pic_z_tv, false);
                                    helper.setGone(R.id.iv_single_pic_fufei, false);

                                    //调整大小
                                    setViewInfo(resource, helper.getView(R.id.iv_single_pic), helper.getView(R.id.player), helper.getView(R.id.rl_single_pic));
                                    //显示播放界面
                                    helper.setGone(R.id.player, true);
                                    ((VideoView) helper.getView(R.id.player)).setUsingSurfaceView(false);
                                    MyStandardVideoController mb = new MyStandardVideoController(context);
                                    mb.setIs_speclie(true);
                                    mb.getmFullScreenButton().setVisibility(View.GONE);
                                    mb.getThumb().setImageDrawable(resource);
                                    ((VideoView) helper.getView(R.id.player)).setVideoController(mb);
                                    ((VideoView) helper.getView(R.id.player)).setUrl(item.getVideo_url());
                                    mb.setOnPlayClickListener(new MyStandardVideoController.OnPlayClickListener() {
                                        @Override
                                        public void onPlayClick() {
                                            if (onItemClick != null) {
                                                onItemClick.onItemclickListener(helper.getLayoutPosition(), item);
                                            }
                                        }
                                    });
                                }
                            });
                }
                helper.getView(R.id.rl_single_pic).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyUserInstance.getInstance().visitorIsLogin()) {
                            if ((TextUtils.isEmpty(item.getUnlocked()) || item.getUnlocked().equals("0")) &&
                                    !item.getUnlock_price().equals("0")) {
                                if (null != showPriceDialogClick) {
                                    showPriceDialogClick.showPriceDialog(helper.getLayoutPosition());
                                }
                            } else {
                                context.startActivity(new Intent(context, PhotoInfoActivity.class).putExtra("data", item.getPhotos()));
                            }

                        }
                    }
                });
                break;
        }

    }


    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
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


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}


