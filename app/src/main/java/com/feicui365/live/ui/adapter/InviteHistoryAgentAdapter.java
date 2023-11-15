package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.InviteAgent;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class InviteHistoryAgentAdapter extends RecyclerView.Adapter<InviteHistoryAgentAdapter.MyViewHolder> {

    private List<InviteAgent> giftIncomeList;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_time;
        TextView tv_name;
        CircleImageView iv_avatar;

        public MyViewHolder(View view) {
            super(view);
            iv_avatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
        }

    }

    public InviteHistoryAgentAdapter(List<InviteAgent> giftIncomeList, Context context) {
        this.giftIncomeList = giftIncomeList;
        this.context = context;
    }

    @Override

    public InviteHistoryAgentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        InviteHistoryAgentAdapter.MyViewHolder holder = new InviteHistoryAgentAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.my_invite_agent_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(InviteHistoryAgentAdapter.MyViewHolder holder, int position) {

        InviteAgent giftIncome = giftIncomeList.get(position);
        holder.tv_name.setText(giftIncome.getNick_name());
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().placeholder(R.mipmap.logo)).load(giftIncome.getAvatar()).into(holder.iv_avatar);
        holder.tv_time.setText(giftIncome.getRegist_time());
    }

    @Override
    public int getItemCount() {
        return giftIncomeList.size();
    }
}
