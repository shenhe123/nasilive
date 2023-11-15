package com.feicui365.live.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.feicui365.live.R;
import com.feicui365.live.model.entity.LiveAudienceListBean;
import com.feicui365.live.model.entity.LiveRoomDetailBean;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class LiveAudienceDetailAdapter extends RecyclerView.Adapter<LiveAudienceDetailAdapter.ViewHolder> {

    private final Context mContext;
    private final ArrayList<LiveAudienceListBean> mList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live_audience, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public LiveAudienceDetailAdapter(ArrayList<LiveAudienceListBean> list, Context context) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
         Glide.with(mContext).setDefaultRequestOptions(new RequestOptions()).load(mList.get(i).getAvatar()).into(holder.avatar_iv);
         holder.city_tv.setText(mList.get(i).getCity());
         holder.nick_name_tv.setText(mList.get(i).getNick_name());
         holder.watchTimes_tv.setText(mList.get(i).getWatchTimes());
         holder.liveTimes_tv.setText(mList.get(i).getLiveTimes());
         holder.latelyTimes_tv.setText(mList.get(i).getLatelyTimes());
         holder.totalTimes_tv.setText(mList.get(i).getTotalTimes());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView totalTimes_tv,liveTimes_tv, nick_name_tv, city_tv, watchTimes_tv,latelyTimes_tv;
        CircleImageView avatar_iv;

        public ViewHolder(View view) {
            super(view);

            nick_name_tv = view.findViewById(R.id.nick_name_tv);
            liveTimes_tv = view.findViewById(R.id.liveTimes_tv);
            totalTimes_tv = view.findViewById(R.id.totalTimes_tv);
            latelyTimes_tv = view.findViewById(R.id.latelyTimes_tv);
            city_tv = view.findViewById(R.id.city_tv);
            watchTimes_tv = view.findViewById(R.id.watchTimes_tv);
            avatar_iv = view.findViewById(R.id.avatar_iv);

        }

    }
}
