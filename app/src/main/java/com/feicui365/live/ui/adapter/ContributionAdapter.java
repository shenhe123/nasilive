package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.icu.text.DecimalFormat;
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
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.util.HttpUtils;
import com.feicui365.live.dialog.UserInfoDialog;
import com.feicui365.live.model.entity.ContributeRank;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContributionAdapter extends RecyclerView.Adapter<ContributionAdapter.ViewHolder> {

    private List<ContributeRank> contributeRanks;
    Context context;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_heart, tv_top, tv_many;
        ImageView iv_user_level, iv_top, iv_vip;
        CircleImageView civ_avatar;
        View v_1;

        public ViewHolder(View view) {
            super(view);

            tv_name = view.findViewById(R.id.tv_name);
            tv_heart = view.findViewById(R.id.tv_heart);
            iv_user_level = view.findViewById(R.id.iv_user_level);
            civ_avatar = view.findViewById(R.id.civ_avatar);
            tv_top = view.findViewById(R.id.tv_top);
            tv_many = view.findViewById(R.id.tv_many);
            iv_top = view.findViewById(R.id.iv_top);
            iv_vip = view.findViewById(R.id.iv_vip);
        }

    }

    public ContributionAdapter(List<ContributeRank> contributeRanks, Context context) {
        this.context = context;
        this.contributeRanks = contributeRanks;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contribute_ranks_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ContributeRank contributeRank = contributeRanks.get(position);
        if (position == 0) {


            holder.tv_top.setVisibility(View.GONE);
            Glide.with(context).load(R.mipmap.vip1).into(holder.iv_top);
        }
        if (position == 1) {

            holder.tv_top.setVisibility(View.GONE);
            Glide.with(context).load(R.mipmap.vip2).into(holder.iv_top);

        }
        if (position == 2) {
            holder.tv_top.setVisibility(View.GONE);
            Glide.with(context).load(R.mipmap.vip3).into(holder.iv_top);

        }


        holder.tv_top.setText(position + 1 + "");

            if (contributeRank.getUser().getVip_level()==0) {
                holder.iv_vip.setVisibility(View.GONE);
            } else if (contributeRank.getUser().getVip_date() != null) {
                if (MyUserInstance.getInstance().OverTime(contributeRank.getUser().getVip_date())) {

                    Glide.with(context).load(
                            ArmsUtils.getVipLevelIcon(contributeRank.getUser().getVip_level(),contributeRank.getUser().getVip_date())
                            ).into(holder.iv_vip);
                } else {
                    holder.iv_vip.setVisibility(View.GONE);
                }

            } else {
                holder.iv_vip.setVisibility(View.GONE);
            }



        Glide.with(context).setDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.moren)).load(contributeRank.getUser().getAvatar()).into(holder.civ_avatar);


        holder.civ_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HttpUtils.getInstance().getUserBasicInfo(contributeRank.getUser().getId() + "", new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject data = (JSONObject) HttpUtils.getInstance().check(response).getJSONObject("data");
                        if (null != data) {
                            UserInfoDialog.Builder builder = new UserInfoDialog.Builder(context);
                            UserRegist userRegist = (UserRegist) JSONObject.parseObject(data.toString(), UserRegist.class);
                            builder.setType("1");
                            builder.setUserRegist(userRegist);
                            builder.create().show();
                        }
                    }
                });

            }
        });
        holder.tv_name.setText(contributeRank.getUser().getNick_name());
        holder.tv_heart.setText(String.valueOf(contributeRank.getIntimacy()));
        Glide.with(context).load(LevelUtils.getUserLevel(contributeRank.getUser().getUser_level())).into(holder.iv_user_level);

        if (Integer.valueOf(contributeRank.getIntimacy()) > 10000) {
            holder.tv_many.setText(getintimacy(Integer.valueOf(contributeRank.getIntimacy()) * 1f / 10000) + "万");
        } else {
            holder.tv_many.setText(contributeRank.getIntimacy()+"");
        }


    }

    private String getintimacy(Float f) {
        String data = "";
        DecimalFormat format = new DecimalFormat(".0");
        data=format.format(f);
        return data;
    }

    @Override
    public int getItemCount() {
        return contributeRanks.size();
    }
}
