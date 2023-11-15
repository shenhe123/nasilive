package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.ui.act.AnchorCenterActivity;
import com.feicui365.live.util.HttpUtils;

import com.feicui365.live.util.LevelUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttendUserAdatper extends RecyclerView.Adapter<AttendUserAdatper.ViewHolder> {

    private List<UserRegist> attentAnchors;
    Context context;
    RequestOptions options;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_nickname,tv_sign;
        CircleImageView civ_avatar;
        ImageView iv_follow;
        ImageView iv_user_level,iv_anchor_level;
        View v_1;

        public ViewHolder (View view)
        {
            super(view);
            tv_nickname = (TextView) view.findViewById(R.id.tv_nickname);
            tv_sign = (TextView) view.findViewById(R.id.tv_sign);
            civ_avatar = view.findViewById(R.id.civ_avatar);
            iv_follow =  view.findViewById(R.id.iv_follow);
            iv_user_level =  view.findViewById(R.id.iv_user_level);
            iv_anchor_level=view.findViewById(R.id.iv_anchor_level);

        }

    }

    public AttendUserAdatper(List <UserRegist> attentAnchors, Context context){
        this.context=context;
        this.attentAnchors = attentAnchors;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_info_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        options = new RequestOptions()
                .placeholder(R.mipmap.moren)
                .centerCrop();
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        UserRegist attentAnchor = attentAnchors.get(position);
        holder.tv_nickname.setText(attentAnchor.getNick_name());
        if(attentAnchor.getProfile().getSignature()==null){
            holder.tv_sign.setText("当前用户暂无签名");
        }else
        if(attentAnchor.getProfile().getSignature().equals("")){
            holder.tv_sign.setText("当前用户暂无签名");
        }else{


        }
          holder.tv_sign.setText(attentAnchor.getProfile().getSignature());

        Glide.with(context).applyDefaultRequestOptions(options).load(attentAnchor.getAvatar()).into(holder.civ_avatar);
        holder.civ_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AnchorCenterActivity.class)
                        .putExtra("data", attentAnchor.getId()));
            }
        });


        if(attentAnchor.getIsattent()==1){
            holder.iv_follow.setImageDrawable(context.getDrawable(R.mipmap.yiguanzhu));
        } else {
            holder.iv_follow.setImageDrawable(context.getDrawable(R.mipmap.guanzhu));
        }


        holder.iv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int attent=0;
                if(attentAnchor.getIsattent()==0){
                    attent=1;
                    attentAnchor.setIsattent(1);
                } else {
                    attent=0;
                    attentAnchor.setIsattent(0);
                }
                HttpUtils.getInstance().attentAnchor(attentAnchor.getProfile().getUid()+"", attent+"", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject data = HttpUtils.getInstance().check(response);
                        if (data.get("status").toString().equals("0")) {
                            if(attentAnchor.getIsattent()==0){
                                holder.iv_follow.setImageDrawable(context.getDrawable(R.mipmap.guanzhu));
                            }
                            if(attentAnchor.getIsattent()==1){
                                holder.iv_follow.setImageDrawable(context.getDrawable(R.mipmap.yiguanzhu));
                            }
                            if(attentAnchor.getIsattent()==0){
                                attentAnchor.setIsattent(0);
                            } else {
                                attentAnchor.setIsattent(1);
                            }
                        }else{
                            if(attentAnchor.getIsattent()==0){

                                attentAnchor.setIsattent(1);
                            }
                            if(attentAnchor.getIsattent()==1){

                                attentAnchor.setIsattent(0);
                            }
                        }
                    }
                });

            }
        });
        Glide.with(context).load(LevelUtils.getUserLevel(attentAnchor.getUser_level())).into(holder.iv_user_level);
        if(attentAnchor.getAnchor_level()!=0){
            Glide.with(context).load(LevelUtils.getAnchorLevel(attentAnchor.getAnchor_level())).into(holder.iv_anchor_level);
            holder.iv_anchor_level.setVisibility(View.VISIBLE);
        }else{
            holder.iv_anchor_level.setVisibility(View.GONE);
        }

    }



    @Override
    public int getItemCount(){
        return attentAnchors.size();
    }
}