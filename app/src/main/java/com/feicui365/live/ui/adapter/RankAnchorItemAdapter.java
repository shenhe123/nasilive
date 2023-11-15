package com.feicui365.live.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.feicui365.live.R;
import com.feicui365.live.interfaces.OnUserInfoClickListener;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.ToastUtils;
import com.feicui365.live.model.entity.RankAnchorItem;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.util.MyUserInstance;
import com.feicui365.nasinet.utils.DipPxUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankAnchorItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<RankAnchorItem> rankItems;
    Context context;
    private OnUserInfoClickListener onUserInfoClickListener;
    RequestOptions requestOptions;
    String type;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_top, tv_name, tv_gold_2;
        CircleImageView civ_avatar;
        ImageView iv_user_level, tv_attention,iv_vip_level;

        public ViewHolder(View view) {
            super(view);
            tv_top = view.findViewById(R.id.tv_top);
            tv_name = view.findViewById(R.id.tv_name);
            tv_gold_2 = view.findViewById(R.id.tv_gold_2);
            civ_avatar = view.findViewById(R.id.civ_avatar);
            iv_user_level = view.findViewById(R.id.iv_user_level);
            tv_attention = view.findViewById(R.id.tv_attention);

            iv_vip_level=view.findViewById(R.id.iv_vip_level);
        }

    }

    static class TopViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_top1_vip_level, iv_top2_vip_level, iv_top3_vip_level;
        CircleImageView avatar_iv_3, avatar_iv_2, avatar_iv_1;
        TextView tv_name_1, tv_name_2, tv_name_3;
        ImageView iv_top1_level, iv_top2_level, iv_top3_level;
        TextView tv_gold_1, tv_gold_2, tv_gold_3;

        public TopViewHolder(View view) {
            super(view);

            avatar_iv_3 = view.findViewById(R.id.avatar_iv_3);
            avatar_iv_2 = view.findViewById(R.id.avatar_iv_2);
            avatar_iv_1 = view.findViewById(R.id.avatar_iv_1);

            tv_name_1 = view.findViewById(R.id.tv_name_1);
            tv_name_2 = view.findViewById(R.id.tv_name_2);
            tv_name_3 = view.findViewById(R.id.tv_name_3);

            iv_top1_level = view.findViewById(R.id.iv_top1_level);
            iv_top2_level = view.findViewById(R.id.iv_top2_level);
            iv_top3_level = view.findViewById(R.id.iv_top3_level);


            tv_gold_1 = view.findViewById(R.id.tv_gold_1);
            tv_gold_2 = view.findViewById(R.id.tv_gold_2);
            tv_gold_3 = view.findViewById(R.id.tv_gold_3);

            iv_top1_vip_level = view.findViewById(R.id.iv_top1_vip_level);
            iv_top2_vip_level = view.findViewById(R.id.iv_top2_vip_level);
            iv_top3_vip_level = view.findViewById(R.id.iv_top3_vip_level);


        }

    }


    public RankAnchorItemAdapter(Context context, List<RankAnchorItem> rankItems, String type) {
        this.context = context;
        this.rankItems = rankItems;
        this.type = type;
        requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.moren);
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == 1) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item_3_layout, parent, false);
            RankAnchorItemAdapter.TopViewHolder holder = new RankAnchorItemAdapter.TopViewHolder(view);
            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item_other, parent, false);
            RankAnchorItemAdapter.ViewHolder holder = new RankAnchorItemAdapter.ViewHolder(view);
            return holder;
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if (position == 0) {
            //头像
            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(rankItems.get(0).getAnchor().getAvatar()).into(((TopViewHolder) holder).avatar_iv_1);
            ((TopViewHolder) holder).avatar_iv_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onUserInfoClickListener != null) {
                        onUserInfoClickListener.onAnchorAvatarClick(rankItems.get(0));
                    }
                }
            });
            ((TopViewHolder) holder).tv_name_1.setText(rankItems.get(0).getAnchor().getNick_name());
            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(LevelUtils.getAnchorLevel(rankItems.get(0).getAnchor().getAnchor_level())).into(((TopViewHolder) holder).iv_top1_level);

            if(rankItems.get(0).getAnchor().getVip_level()!=0) {

                    if(rankItems.get(0).getAnchor().getVip_date()==null){
                        ((TopViewHolder) holder).iv_top3_vip_level.setVisibility(View.GONE);
                    }else  if(!MyUserInstance.getInstance().OverTime(rankItems.get(0).getAnchor().getVip_date())) {

                        ((TopViewHolder) holder).iv_top3_vip_level.setVisibility(View.GONE);
                    }
                    Glide.with(context).load(ArmsUtils.getVipLevelIcon(rankItems.get(0).getAnchor().getVip_level(),rankItems.get(0).getAnchor().getVip_date())).into(((TopViewHolder) holder).iv_top1_vip_level);
                } else {
                    ((TopViewHolder) holder).iv_top3_vip_level.setVisibility(View.GONE);

            }

            ((TopViewHolder) holder).tv_gold_1.setText(rankItems.get(0).getIncome());


            //第二位
            if(rankItems.size()>1) {
                Glide.with(context).applyDefaultRequestOptions(requestOptions).load(rankItems.get(1).getAnchor().getAvatar()).into(((TopViewHolder) holder).avatar_iv_2);
                ((TopViewHolder) holder).avatar_iv_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onUserInfoClickListener != null) {
                            onUserInfoClickListener.onAnchorAvatarClick(rankItems.get(1));
                        }
                    }
                });
                ((TopViewHolder) holder).tv_name_2.setText(rankItems.get(1).getAnchor().getNick_name());
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(LevelUtils.getAnchorLevel(rankItems.get(1).getAnchor().getAnchor_level())).into(((TopViewHolder) holder).iv_top2_level);
                if(rankItems.get(1).getAnchor().getVip_level()!=0) {

                        if (rankItems.get(1).getAnchor().getVip_date() == null) {
                            ((TopViewHolder) holder).iv_top2_vip_level.setVisibility(View.GONE);
                        } else if (!MyUserInstance.getInstance().OverTime(rankItems.get(1).getAnchor().getVip_date())) {

                            ((TopViewHolder) holder).iv_top2_vip_level.setVisibility(View.GONE);
                        }
                        Glide.with(context).load(ArmsUtils.getVipLevelIcon(rankItems.get(1).getAnchor().getVip_level(),rankItems.get(1).getAnchor().getVip_date())).into(((TopViewHolder) holder).iv_top2_vip_level);
                    } else {
                        ((TopViewHolder) holder).iv_top2_vip_level.setVisibility(View.GONE);

                }
                ((TopViewHolder) holder).tv_gold_2.setText(rankItems.get(1).getIncome());

            }

            if(rankItems.size()>2) {
                Glide.with(context).applyDefaultRequestOptions(requestOptions).load(rankItems.get(2).getAnchor().getAvatar()).into(((TopViewHolder) holder).avatar_iv_3);
                ((TopViewHolder) holder).avatar_iv_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onUserInfoClickListener != null) {
                            onUserInfoClickListener.onAnchorAvatarClick(rankItems.get(2));
                        }
                    }
                });

                //名字
                ((TopViewHolder) holder).tv_name_3.setText(rankItems.get(2).getAnchor().getNick_name());
                //等级
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(LevelUtils.getAnchorLevel(rankItems.get(2).getAnchor().getAnchor_level())).into(((TopViewHolder) holder).iv_top3_level);
                //VIP等级
                if(rankItems.get(2).getAnchor().getVip_level()!=0) {

                        if (rankItems.get(2).getAnchor().getVip_date() == null) {
                            ((TopViewHolder) holder).iv_top3_vip_level.setVisibility(View.GONE);
                        } else if (!MyUserInstance.getInstance().OverTime(rankItems.get(2).getAnchor().getVip_date())) {

                            ((TopViewHolder) holder).iv_top3_vip_level.setVisibility(View.GONE);
                        }
                        Glide.with(context).load(ArmsUtils.getVipLevelIcon(rankItems.get(2).getAnchor().getVip_level(),rankItems.get(2).getAnchor().getVip_date())).into(((TopViewHolder) holder).iv_top3_vip_level);
                    } else {
                        ((TopViewHolder) holder).iv_top3_vip_level.setVisibility(View.GONE);
                    }

                //金币数量
                ((TopViewHolder) holder).tv_gold_3.setText(rankItems.get(2).getIncome());
            }
        } else if (position < 3 & position > 0) {
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) ((ViewHolder) holder).itemView.getLayoutParams();
            param.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            ((ViewHolder) holder).itemView.setVisibility(View.GONE);
            param.height = 0;
            param.width = 0;
            ((ViewHolder) holder).itemView.setLayoutParams(param);

        } else if (position > 2&rankItems.size()>3) {
            Log.e("rankItems",rankItems.get(position).toString());
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) ((ViewHolder) holder).itemView.getLayoutParams();
            param.height = DipPxUtils.dip2px(context,60);
            param.width = LinearLayout.LayoutParams.MATCH_PARENT;
            ((ViewHolder) holder).itemView.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).itemView.setLayoutParams(param);
            if(position>2 & position < 9){
                ((ViewHolder) holder).tv_top.setText("0" + (position + 1));
            }else{
                ((ViewHolder) holder).tv_top.setText(position + 1 + "");
            }

            if(rankItems.get(position ).getAnchor().getVip_level()!=0) {


                if (rankItems.get(position ).getAnchor().getVip_date() == null) {
                    ((RankAnchorItemAdapter.ViewHolder) holder).iv_vip_level.setVisibility(View.GONE);
                } else if (!MyUserInstance.getInstance().OverTime(rankItems.get(position ).getAnchor().getVip_date())) {
                    ((RankAnchorItemAdapter.ViewHolder) holder).iv_vip_level.setVisibility(View.GONE);
                }
                Glide.with(context).load(ArmsUtils.getVipLevelIcon(rankItems.get(position ).getAnchor().getVip_level(),
                        rankItems.get(position ).getAnchor().getVip_date())
                ).into(((RankAnchorItemAdapter.ViewHolder) holder).iv_vip_level);
                ((RankAnchorItemAdapter.ViewHolder) holder).iv_vip_level.setVisibility(View.VISIBLE);
            } else {
                ((RankAnchorItemAdapter.ViewHolder) holder).iv_vip_level.setVisibility(View.GONE);
            }
            ((ViewHolder) holder).tv_name.setText(rankItems.get(position).getAnchor().getNick_name());
            ((ViewHolder) holder).tv_gold_2.setText(rankItems.get(position).getIncome() + "金币");
            Glide.with(context).applyDefaultRequestOptions(requestOptions).load(rankItems.get(position).getAnchor().getAvatar()).into(((ViewHolder) holder).civ_avatar);


            Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(LevelUtils.getAnchorLevel(rankItems.get(position).getAnchor().getAnchor_level())).into(((ViewHolder) holder).iv_user_level);


            if (rankItems.get(position).getAnchor().getIsattent() != 0) {
            //    Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(R.mipmap.guanzhu).into(((ViewHolder) holder).tv_attention);
                ((ViewHolder) holder).tv_attention.setImageDrawable(context.getResources().getDrawable(R.mipmap.guanzhu));
            } else {
               // Glide.with(context).applyDefaultRequestOptions(new RequestOptions().centerCrop()).load(R.mipmap.yiguanzhu).into(((ViewHolder) holder).tv_attention);
                ((ViewHolder) holder).tv_attention.setImageDrawable(context.getResources().getDrawable(R.mipmap.yiguanzhu));
            }
            ((ViewHolder) holder).tv_attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((rankItems.get(position).getAnchor().getId() + "").equals(MyUserInstance.getInstance().getUserinfo().getId())) {

                        ToastUtils.showT("不能自己关注自己");
                        return;
                    }
                    follow(rankItems.get(position), (ViewHolder) holder);
                }
            });
            ((ViewHolder) holder).civ_avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onUserInfoClickListener!=null){
                        onUserInfoClickListener.onAnchorAvatarClick(rankItems.get(position));
                    }
                }
            });
        }


    }

    private void follow(RankAnchorItem rankItem, ViewHolder viewHolder) {
        int attent = 0;
        if (rankItem.getAnchor().getIsattent() != 0 ) {
            attent = 1;
            rankItem.getAnchor().setIsattent(1);
        } else {
            attent = 0;
            rankItem.getAnchor().setIsattent(0);
        }

        HttpUtils.getInstance().attentAnchor(rankItem.getAnchorid() + "", attent + "", new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                JSONObject data = HttpUtils.getInstance().check(response);
                if (data.get("status").toString().equals("0")) {
                    if (rankItem.getAnchor().getIsattent()==0) {
                        viewHolder.tv_attention.setImageDrawable(context.getDrawable(R.mipmap.guanzhu));
                    }
                    if (rankItem.getAnchor().getIsattent()==1) {
                        viewHolder.tv_attention.setImageDrawable(context.getDrawable(R.mipmap.yiguanzhu));
                    }
                    if (rankItem.getAnchor().getIsattent()==0) {
                        rankItem.getAnchor().setIsattent(0);
                    } else {
                        rankItem.getAnchor().setIsattent(1);
                    }
                } else {
                    if (rankItem.getAnchor().getIsattent()==0) {

                        rankItem.getAnchor().setIsattent(1);
                    }
                    if (rankItem.getAnchor().getIsattent()==1) {

                        rankItem.getAnchor().setIsattent(0);
                    }
                }
            }
        });
    }

    public void setRankItems(List<RankAnchorItem> rankItems) {
        this.rankItems = rankItems;
    }

    @Override
    public int getItemCount() {

        return rankItems.size();
    }

    public void setOnUserInfoClickListener(OnUserInfoClickListener onUserInfoClickListener) {
        this.onUserInfoClickListener = onUserInfoClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else {
            return 2;
        }

    }

}