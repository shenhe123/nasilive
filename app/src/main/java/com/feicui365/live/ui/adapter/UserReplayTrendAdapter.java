package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.util.FormatCurrentData;
import com.feicui365.live.model.entity.MomentDetail;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.TextRender;

import java.util.ArrayList;

import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserReplayTrendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private MomentDetail trend_Item;
    //传进来的列表
    ArrayList<MomentDetail> comments;
    private String now_chose = "";
    private int level = 1;
    MomentDetail now_chose_one;

    public void setNow_chose_one(MomentDetail now_chose_one) {
        this.now_chose_one = now_chose_one;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    Context context;
    BGANinePhotoLayout.Delegate delegate;
    private OnItemClick onItemClick;

    public void setNow_chose(String now_chose) {
        this.now_chose = now_chose;
    }

    public OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(MomentDetail comment);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    static class TopViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_content, tv_time, tv_remessage_num, tv_zan_num,age_tv, tv_comment_count;
        CircleImageView civ_avatar;
        ImageView iv_zan,iv_user_level;

        RelativeLayout rl_center, ll_zan,rl_back;

        public TopViewHolder(View view) {
            super(view);

            civ_avatar = view.findViewById(R.id.civ_avatar);
            tv_name = view.findViewById(R.id.tv_name);
            tv_content = view.findViewById(R.id.tv_content);
            tv_remessage_num = view.findViewById(R.id.tv_remessage_num);
            tv_time = view.findViewById(R.id.tv_time);
            tv_zan_num = view.findViewById(R.id.tv_zan_num);
            ll_zan = view.findViewById(R.id.ll_zan);
            rl_center = view.findViewById(R.id.rl_center);
            iv_zan = view.findViewById(R.id.iv_zan);
            age_tv= view.findViewById(R.id.age_tv);
            iv_user_level= view.findViewById(R.id.iv_user_level);
            tv_comment_count = view.findViewById(R.id.tv_comment_count);
        }

    }


    static class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_content, tv_time, tv_remessage_num, tv_zan_num,age_tv;
        CircleImageView civ_avatar;
        ImageView iv_zan,iv_user_level;

        RelativeLayout rl_center, ll_zan;

        public ListViewHolder(View view) {
            super(view);

            civ_avatar = view.findViewById(R.id.civ_avatar);
            tv_name = view.findViewById(R.id.tv_name);
            tv_content = view.findViewById(R.id.tv_content);
            tv_remessage_num = view.findViewById(R.id.tv_remessage_num);
            tv_time = view.findViewById(R.id.tv_time);
            tv_zan_num = view.findViewById(R.id.tv_zan_num);
            ll_zan = view.findViewById(R.id.ll_zan);
            rl_center = view.findViewById(R.id.rl_center);
            iv_zan = view.findViewById(R.id.iv_zan);
            age_tv= view.findViewById(R.id.age_tv);
            iv_user_level= view.findViewById(R.id.iv_user_level);
        }

    }

    public UserReplayTrendAdapter(MomentDetail trend_Item, ArrayList<MomentDetail> comment, Context context, BGANinePhotoLayout.Delegate delegate) {
        this.context = context;
        this.trend_Item = trend_Item;
        this.delegate = delegate;
        this.comments = comment;
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_chose_item, parent, false);
            UserReplayTrendAdapter.TopViewHolder holder = new UserReplayTrendAdapter.TopViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
            ListViewHolder holder = new ListViewHolder(view);
            return holder;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

                MomentDetail comment = comments.get(position);
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop().placeholder(R.mipmap.moren)).load(comment.getUser().getAvatar()).into(((ListViewHolder) holder).civ_avatar);
                ((ListViewHolder) holder).age_tv.setText(comment.getUser().getProfile().getAge());
                if (comment.getUser().getProfile().getGender()==1) {
                    ((ListViewHolder) holder).age_tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);

                    ((ListViewHolder) holder).age_tv.setBackgroundResource(R.drawable.shape_corner_age_boy);
                }else{
                    ((ListViewHolder) holder).age_tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.gir_transparentl), null, null, null);

                    ((ListViewHolder) holder).age_tv.setBackgroundResource(R.drawable.gender_bg);
                }
                Glide.with(context).load(LevelUtils.getUserLevel(comment.getUser().getUser_level())).into(((ListViewHolder) holder).iv_user_level);
                ((ListViewHolder) holder).tv_name.setText(comment.getUser().getNick_name());
                ((ListViewHolder) holder).tv_time.setText(FormatCurrentData.getTimeRange2(comment.getCreate_time()));

                if (null != comment.getTouser()) {
                    if (!comment.getTocommentid().equals(comment.getRootid())) {
                        ((ListViewHolder) holder).tv_content.setText(TextRender.renderChatMessage2(TextRender.repOther(comment.getTouser().getNick_name(), comment.getContent())));
                    } else {
                        ((ListViewHolder) holder).tv_content.setText(TextRender.renderChatMessage(comment.getContent()));
                    }

                } else {
                    ((ListViewHolder) holder).tv_content.setText(TextRender.renderChatMessage(comment.getContent()));
                }

                ((ListViewHolder) holder).rl_center.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != onItemClickListener) {
                            onItemClickListener.onClick(comment);
                        }
                    }
                });


                if (Integer.parseInt(comment.getLike_count()) > 0) {
                    ((ListViewHolder) holder).tv_zan_num.setText(comment.getLike_count());

                }


                if (Integer.parseInt(comment.getReply_count()) > 0) {
                    ((ListViewHolder) holder).tv_remessage_num.setVisibility(View.VISIBLE);
                    ((ListViewHolder) holder).tv_remessage_num.setText(comment.getReply_count() +"条回复");
                }
                if(null==comment.getLiked()){
                    ((ListViewHolder) holder).ll_zan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            HttpUtils.getInstance().likeMomentComment(comment.getId(), new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    JSONObject data = HttpUtils.getInstance().check(response);
                                    if( HttpUtils.getInstance().swtichStatus(data)){

                                        ((ListViewHolder) holder).iv_zan.setImageDrawable(context.getResources().getDrawable(R.mipmap.short_zan2));
                                        ((ListViewHolder) holder).tv_zan_num.setText(data.getJSONObject("data").getString("like_count"));
                                        for(int i=0;i<comments.size();i++){
                                            if(comments.get(i).getId().equals(comment.getId())){
                                                comments.get(i).setLiked("1");
                                                comments.get(i).setLike_count(data.getJSONObject("data").getString("like_count"));
                                                break;
                                            }

                                        }
                                    }
                                }
                            });
                        }
                    });
                }else{
                   ((ListViewHolder) holder).iv_zan.setImageDrawable(context.getResources().getDrawable(R.mipmap.short_zan2));
                }





    }


    @Override
    public int getItemCount() {
        return comments.size() ;
    }



    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


    public interface OnItemClick {
        void onItemclickListener(int position);
    }






}
