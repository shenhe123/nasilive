package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.live.utils.ArmsUtils;
import com.feicui365.live.model.entity.RoomManager;
import com.feicui365.live.model.entity.UserRegist;
import com.feicui365.live.util.LevelUtils;
import com.feicui365.live.util.MyUserInstance;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RoomManagerAdapter extends RecyclerView.Adapter<RoomManagerAdapter.ViewHolder> {

    private ArrayList<RoomManager> list;
    Context context;


    public CancelListener cancelListener;

    public interface CancelListener {
        void Cancel(UserRegist userRegist);
    }

    public CancelListener getCancelListener() {
        return cancelListener;
    }

    public void setCancelListener(CancelListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView iv_user_level, iv_vip, iv_cancel, iv_manager;
        CircleImageView civ_avatar;
        View v_1;

        public ViewHolder(View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            iv_user_level = view.findViewById(R.id.iv_user_level);
            civ_avatar = view.findViewById(R.id.civ_avatar);
            iv_cancel = view.findViewById(R.id.iv_cancel);
            iv_manager = view.findViewById(R.id.iv_manager);
            iv_vip = view.findViewById(R.id.iv_vip);
        }

    }

    public RoomManagerAdapter(ArrayList<RoomManager> list, Context context) {
        this.context = context;
        this.list = list;

    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_manager_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        UserRegist userRegist = list.get(position).getUser();

            if (userRegist.getVip_level()==0) {
                holder.iv_vip.setVisibility(View.GONE);
            } else if (userRegist.getVip_date() != null) {
                if (MyUserInstance.getInstance().OverTime(userRegist.getVip_date())) {
                    Glide.with(context).load(ArmsUtils.getVipLevelIcon(userRegist.getVip_level(),userRegist.getVip_date())).into(holder.iv_vip);
                } else {
                    holder.iv_vip.setVisibility(View.GONE);
                }
            } else {
                holder.iv_vip.setVisibility(View.GONE);
            }

        Glide.with(context).setDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.moren)).load(userRegist.getAvatar()).into(holder.civ_avatar);
        Glide.with(context).load(LevelUtils.getUserLevel(userRegist.getUser_level())).into(holder.iv_user_level);
        holder.tv_name.setText(userRegist.getNick_name());

        holder.iv_manager.setVisibility(View.GONE);
        Glide.with(context).load(R.mipmap.cancel_manager).into(holder.iv_cancel);
        holder.iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancelListener != null) {
                    cancelListener.Cancel(userRegist);
                }
            }
        });


    }

    private String getintimacy(Float f) {
        String data = "";
        DecimalFormat format = new DecimalFormat(".0");
        data = format.format(f);
        return data;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}