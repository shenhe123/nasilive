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
import com.feicui365.live.model.entity.Comment;
import com.feicui365.live.ui.act.HomeActivity;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.TextRender;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {


    private List<Comment> homeItem;
    Context context;
    private int level=1;
    private String  now_chose="";
    public CommentAdapter.OnItemClickListener onItemClickListener;
    HomeActivity homeActivity;
    public interface OnItemClickListener {
        void onClick(Comment comment);

    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setNow_chose(String now_chose) {
        this.now_chose = now_chose;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_content, tv_time, tv_remessage_num, tv_zan_num,age_tv;
        CircleImageView civ_avatar;
        ImageView iv_zan,iv_user_level;

        RelativeLayout rl_center,ll_zan;

        public ViewHolder(View view) {
            super(view);

            civ_avatar = view.findViewById(R.id.civ_avatar);
            tv_name = view.findViewById(R.id.tv_name);
            tv_content = view.findViewById(R.id.tv_content);
            tv_remessage_num = view.findViewById(R.id.tv_remessage_num);
            tv_time = view.findViewById(R.id.tv_time);
            tv_zan_num = view.findViewById(R.id.tv_zan_num);
            ll_zan = view.findViewById(R.id.ll_zan);
            rl_center=view.findViewById(R.id.rl_center);
            iv_zan=view.findViewById(R.id.iv_zan);
            iv_user_level=view.findViewById(R.id.iv_user_level);
            age_tv=view.findViewById(R.id.age_tv);
        }

    }

    public CommentAdapter(List<Comment> homeItem, Context context) {
        this.context = context;
        this.homeItem = homeItem;

    }

    @Override

    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
        CommentAdapter.ViewHolder holder = new CommentAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Comment comment = homeItem.get(position);
        Glide.with(context).load(comment.getUser().getAvatar()).into(holder.civ_avatar);
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(LevelUtils.getUserLevel(comment.getUser().getUser_level())).into( holder.iv_user_level);
        holder.tv_name.setText(comment.getUser().getNick_name());
        holder.tv_time.setText(   FormatCurrentData.getTimeRange2(comment.getCreate_time()));
         holder.age_tv.setText(comment.getUser().getProfile().getAge());
        if (comment.getUser().getProfile().getGender()==1) {
            holder.age_tv.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(R.mipmap.boy_transparent), null, null, null);

             holder.age_tv.setBackgroundResource(R.drawable.shape_corner_age_boy);
        }
        if(null!=comment.getTouser()){
        /*    if(!now_chose.equals(comment.getTouser().getId()+"")){
                holder.tv_content.setText(TextRender.renderChatMessage2(TextRender.repOther(comment.getTouser().getNick_name(),comment.getContent())));
            }else{
                holder.tv_content.setText(TextRender.renderChatMessage(comment.getContent()));
            }
*/
            if (!comment.getTocommentid().equals(comment.getRootid())) {
                 holder.tv_content.setText(TextRender.renderChatMessage2(TextRender.repOther(comment.getTouser().getNick_name(), comment.getContent())));
            } else {
            holder.tv_content.setText(TextRender.renderChatMessage(comment.getContent()));
            }

          //  holder.tv_content.setText(TextRender.renderChatMessage2(TextRender.repOther(comment.getTouser().getNick_name(),comment.getContent())));
        }else{
            holder.tv_content.setText(TextRender.renderChatMessage(comment.getContent()));
        }

        holder.rl_center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onItemClickListener) {
                    onItemClickListener.onClick(comment);
                }
            }
        });


        if (Integer.parseInt(comment.getLike_count()) > 0) {
            holder.tv_zan_num.setText(comment.getLike_count());

        }
        if(level==1){
            if (Integer.parseInt(comment.getReply_count()) > 0) {
                holder.tv_remessage_num.setVisibility(View.VISIBLE);
                holder.tv_remessage_num.setText(comment.getReply_count() +"条回复");
            }
        }else{
            holder.tv_remessage_num.setVisibility(View.GONE);
        }

        if(null==comment.getLiked()){
            holder.ll_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    HttpUtils.getInstance().likeComment(comment.getId(), new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            JSONObject data = HttpUtils.getInstance().check(response);
                            if (data.get("status").toString().equals("0")) {
                                Glide.with(context).load(R.mipmap.short_zan2).into(holder.iv_zan);
                                holder.tv_zan_num.setText(data.getJSONObject("data").getString("like_count"));
                                for(int i=0;i<homeItem.size();i++){
                                    if(homeItem.get(i).getId().equals(comment.getId())){
                                        homeItem.get(i).setLiked("1");
                                        homeItem.get(i).setLike_count(data.getJSONObject("data").getString("like_count"));


                                        break;
                                    }

                                }
                            }
                        }
                    });
                }
            });
        }else{
            Glide.with(context).load(R.mipmap.short_zan2).into(holder.iv_zan);
        }

    }

    @Override
    public int getItemCount() {
        return homeItem.size();
    }
    public void setLevel(int level){
        this.level=level;
    }
}