package com.feicui365.live.ui.adapter;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.ui.act.TopicShortVideoActivity;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.model.entity.ShortVideo;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.dialog.ShareDialog;
import com.feicui365.live.widget.listvideo.ListVideoView;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.nasinet.utils.DipPxUtils;


import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author ArcherYc
 * @date on 2018/9/12  下午3:19
 * @mail 247067345@qq.com
 */
public class ShortVideoAdapter extends RecyclerView.Adapter<ShortVideoAdapter.VideoViewHolder> {

    public List<ShortVideo> dataList = new ArrayList<>();
    private Activity mContext;
    private RecyclerView recyclerView;
    private CommentListener commentListener;


    public interface CommentListener {
        void onClick(ShortVideo videoEntity);

        void onVideoClick();

        void onZanClick(String count);

        void onAvatarClick(UserRegist author);

        void onAttend(String author_id, String type);

    }

    public void setCommentListener(CommentListener commentListener) {
        this.commentListener = commentListener;
    }

    public ShortVideoAdapter(Activity context, RecyclerView recyclerView) {
        this.mContext = context;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_video, parent, false);
        return new VideoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        ShortVideo videoEntity = dataList.get(position);
        VideoViewHolder viewHolder = (VideoViewHolder) holder;


        Glide.with(mContext).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(videoEntity.getThumb_url()).into(viewHolder.sdvCover);

        if (null != videoEntity.getAuthor().getAvatar()) {
            Glide.with(mContext).load(videoEntity.getAuthor().getAvatar()).into(viewHolder.civ_avatar);
        }
        viewHolder.civ_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != commentListener) {
                    commentListener.onAvatarClick(videoEntity.getAuthor());
                }
            }
        });

        viewHolder.tv_zan_num.setText(videoEntity.getLike_count());
        viewHolder.tv_pinglun_num.setText(videoEntity.getComment_count());
        viewHolder.tv_zhuan_num.setText(videoEntity.getShare_count());
        viewHolder.tv_name.setText("@" + videoEntity.getAuthor().getNick_name()+position);
        viewHolder.tv_title.setText(videoEntity.getTitle());

        if (videoEntity.getTopic() != null) {

            SpannableString spString = new SpannableString(videoEntity.getTopic());
            spString.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent=new Intent(mContext, TopicShortVideoActivity.class);
                    intent.putExtra("topic",spString.toString());
                    mContext.startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setUnderlineText(false);
                    ds.setTextSize(DipPxUtils.sp2px(mContext, 15));//设置字体大小
                    ds.setColor(mContext.getResources().getColor(R.color.color_FF6273));//设置字体颜色
                }
            }, 0, videoEntity.getTopic().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.tv_title.setText(spString);
            viewHolder.tv_title.setMovementMethod(LinkMovementMethod.getInstance());
            viewHolder.tv_title.append(" "+videoEntity.getTitle());

        } else {
            viewHolder.tv_title.setText(videoEntity.getTitle());
        }


        viewHolder.ll_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    if (null != commentListener) {
                        commentListener.onClick(videoEntity);
                    }
                }
            }
        });


        if (null == videoEntity.getIs_like()) {
            Glide.with(mContext).load(R.mipmap.zan).into(holder.iv_zan);
        } else {
            if (videoEntity.getIs_like().equals("0")) {
                Glide.with(mContext).load(R.mipmap.zan).into(holder.iv_zan);
            } else {
                Glide.with(mContext).load(R.mipmap.zan_pre).into(holder.iv_zan);
                holder.tv_zan_num.setText(videoEntity.getLike_count());
            }
        }


            if (videoEntity.getAuthor().getIsattent()==0) {

                Glide.with(mContext).load(R.mipmap.short_guanzhu).into(viewHolder.im_guanzhu);
            } else {

                Glide.with(mContext).load(R.mipmap.short_yiguanzhu).into(viewHolder.im_guanzhu);

        }

        viewHolder.im_guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUserInstance.getInstance().visitorIsLogin()) {

                        if (videoEntity.getAuthor().getIsattent()==0) {
                            attendAnchor(videoEntity.getAuthor().getId(), "1", holder);
                        } else { attendAnchor(videoEntity.getAuthor().getId(), "0", holder);

                    }
                }
            }
        });

        holder.ll_zhuanfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    Glide.with(mContext).load(videoEntity.getThumb_url()).into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                            ShareDialog.Builder builder = new ShareDialog.Builder(mContext);
                            builder.setShare_url(MyUserInstance.getInstance().getUserConfig().getConfig().getShare_shortvideo_url() + videoEntity.getId());
                            builder.create().show();
                            builder.showBottom2();
                            builder.hideDown(false);
                            builder.setImage_url(videoEntity.getThumb_url());
                            builder.setContent(videoEntity.getTitle());
                            builder.setTitle("推荐你这个动态");
                            builder.setVideo_id(videoEntity.getId());
                            builder.setTumb(drawableToBitmap(resource));
                            builder.setVideo_url(videoEntity.getPlay_url());
                            builder.setId(videoEntity.getId());
                            if (videoEntity.getCollected() == null || videoEntity.getCollected().equals("0")) {
                                builder.setIs_collect("0");
                            } else {
                                builder.setIs_collect("1");
                            }
                            builder.setOnCollectListener(new ShareDialog.OnCollectListener() {
                                @Override
                                public void collect(String type) {
                                    videoEntity.setCollected(type);
                                    if (type.equals("1")) {
                                        ToastUtils.showT("收藏成功");
                                    } else {
                                        ToastUtils.showT("取消收藏");
                                    }

                                }
                            });
                            builder.setType("3");

                        }
                    });

                }
            }
        });


        viewHolder.ll_zan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyUserInstance.getInstance().visitorIsLogin()) {
                    String is_like = videoEntity.getIs_like();
                    String temp = "0";
                    if (is_like == null || is_like.equals("0")) {
                        temp = "1";
                    } else {
                        temp = "0";

                    }
                    String finalTemp = temp;
                    HttpUtils.getInstance().likeVideo(videoEntity.getId(), temp, new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            JSONObject data = HttpUtils.getInstance().check(response);
                            if (data.get("status").toString().equals("0")) {
                                if (null != commentListener) {
                                    if (finalTemp.equals("0")) {

                                        Glide.with(mContext).load(R.mipmap.zan).into(holder.iv_zan);
                                        holder.tv_zan_num.setText(data.getJSONObject("data").getString("like_count"));
                                        dataList.get(position).setLike_count(data.getJSONObject("data").getString("like_count"));
                                        dataList.get(position).setIs_like("0");
                                        if (commentListener != null) {
                                            commentListener.onZanClick(data.getJSONObject("data").getString("like_count"));
                                        }
                                    } else {
                                        Glide.with(mContext).load(R.mipmap.zan_pre).into(holder.iv_zan);
                                        holder.tv_zan_num.setText(data.getJSONObject("data").getString("like_count"));
                                        dataList.get(position).setLike_count(data.getJSONObject("data").getString("like_count"));
                                        dataList.get(position).setIs_like("1");
                                        if (commentListener != null) {
                                            commentListener.onZanClick(data.getJSONObject("data").getString("like_count"));
                                        }
                                    }

                                }
                            }
                        }
                    });

                }
            }
        });

        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentListener != null) {
                    commentListener.onVideoClick();
                }
            }
        });

    }

    private void attendAnchor(String anchorid, String type, VideoViewHolder holder) {

        HttpUtils.getInstance().attentAnchor(anchorid, type, new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                JSONObject data = HttpUtils.getInstance().check(response);
                if (data.get("status").toString().equals("0")) {
                    if (null != commentListener) {
                        commentListener.onAttend(anchorid, type);
                    }
                }
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addData(List<ShortVideo> newDataList) {
        this.dataList.addAll(newDataList);
    }

    public ShortVideo getDataByPosition(int position) {
        return dataList.get(position);
    }

    public void cleanData() {
        dataList.clear();
    }

    public int getVideoSize() {
        return dataList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public ListVideoView videoView;
        public ImageView sdvCover;
        public CircleImageView civ_avatar;
        public ImageView im_guanzhu, iv_zan, iv_pause;
        public TextView tv_zan_num, tv_pinglun_num, tv_zhuan_num, tv_name, tv_title;
        public LinearLayout ll_comment, ll_zan, ll_zhuanfa;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            sdvCover = itemView.findViewById(R.id.sdv_cover);
            civ_avatar = itemView.findViewById(R.id.civ_avatar);
            im_guanzhu = itemView.findViewById(R.id.im_guanzhu);
            tv_zan_num = itemView.findViewById(R.id.tv_zan_num);
            tv_pinglun_num = itemView.findViewById(R.id.tv_pinglun_num);
            tv_zhuan_num = itemView.findViewById(R.id.tv_zhuan_num);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_title = itemView.findViewById(R.id.tv_title);
            ll_comment = itemView.findViewById(R.id.ll_comment);
            iv_zan = itemView.findViewById(R.id.iv_zan);
            ll_zan = itemView.findViewById(R.id.ll_zan);
            ll_zhuanfa = itemView.findViewById(R.id.ll_zhuanfa);
            iv_pause = itemView.findViewById(R.id.iv_pause);

        }
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
